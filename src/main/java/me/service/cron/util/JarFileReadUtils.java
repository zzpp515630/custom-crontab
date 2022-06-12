package me.service.cron.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 描述：
 * 2021/12/21 11:52.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class JarFileReadUtils {

    /**
     * The ClassLoader to use for resolving migrations on the classpath. (default: Thread.currentThread().getContextClassLoader() )
     */
    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


    public Set<String> findResourceNames(String location) {
        Set<String> resourceNames = new TreeSet<>();
        List<URL> locationUrls = getLocationUrlsForPath(location);
        for (URL locationUrl : locationUrls) {
            String protocol = locationUrl.getProtocol();
            if ("file".equals(locationUrl.getProtocol())) {
                resourceNames.addAll(new FilePathLocationScanner().findResourceNames(location, locationUrl));
            }
            if ("jar".equals(protocol) || isTomcat(protocol) || isWebLogic(protocol) || isWebSphere(protocol)) {
                String separator = isTomcat(locationUrl.getProtocol()) ? "*/" : "!/";
                resourceNames.addAll(new JarPathLocationScanner(separator).findResourceNames(location, locationUrl));
            }

        }
        return resourceNames;
    }

    interface PathLocationScanner {

        /**
         * 扫描资源
         *
         * @param location
         * @param locationUrl
         * @return
         */
        Set<String> findResourceNames(String location, URL locationUrl);

    }

    static class FilePathLocationScanner implements PathLocationScanner {


        @Override
        public Set<String> findResourceNames(String location, URL locationUrl) {
            String filePath = toFilePath(locationUrl);
            File folder = new File(filePath);
            if (!folder.isDirectory()) {
                log.debug("Skipping path as it is not a directory: " + filePath);
                return new TreeSet<>();
            }

            String classPathRootOnDisk = filePath.substring(0, filePath.length() - location.length());
            if (!classPathRootOnDisk.endsWith(File.separator)) {
                classPathRootOnDisk = classPathRootOnDisk + File.separator;
            }
            log.debug("Scanning starting at classpath root in filesystem: " + classPathRootOnDisk);
            return findResourceNamesFromFileSystem(classPathRootOnDisk, location, folder);
        }

        public static String toFilePath(URL url) {
            String filePath = new File(decodeURL(url.getPath().replace("+", "%2b"))).getAbsolutePath();
            if (filePath.endsWith("/")) {
                return filePath.substring(0, filePath.length() - 1);
            }
            return filePath;
        }



        /**
         * Decodes this UTF-8 encoded URL.
         *
         * @param url The url to decode.
         * @return The decoded URL.
         */
        public static String decodeURL(String url) {
            try {
                return URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException("Can never happen", e);
            }
        }

        /**
         * Finds all the resource names contained in this file system folder.
         *
         * @param classPathRootOnDisk The location of the classpath root on disk, with a trailing slash.
         * @param scanRootLocation    The root location of the scan on the classpath, without leading or trailing slashes.
         * @param folder              The folder to look for resources under on disk.
         * @return The resource names;
         */
        /*private -> for testing*/
        @SuppressWarnings("ConstantConditions")
        Set<String> findResourceNamesFromFileSystem(String classPathRootOnDisk, String scanRootLocation, File folder) {
            log.debug("Scanning for resources in path: " + folder.getPath() + " (" + scanRootLocation + ")");

            Set<String> resourceNames = new TreeSet<>();

            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.canRead()) {
                    if (file.isDirectory()) {
                        resourceNames.addAll(findResourceNamesFromFileSystem(classPathRootOnDisk, scanRootLocation, file));
                    } else {
                        resourceNames.add(toResourceNameOnClasspath(classPathRootOnDisk, file));
                    }
                }
            }

            return resourceNames;
        }

        /**
         * Converts this file into a resource name on the classpath.
         *
         * @param classPathRootOnDisk The location of the classpath root on disk, with a trailing slash.
         * @param file                The file.
         * @return The resource name on the classpath.
         */
        private String toResourceNameOnClasspath(String classPathRootOnDisk, File file) {
            String fileName = file.getAbsolutePath().replace("\\", "/");

            //Cut off the part on disk leading to the root of the classpath
            //This leaves a resource name starting with the scanRootLocation,
            //   with no leading slash, containing subDirs and the fileName.
            return fileName.substring(classPathRootOnDisk.length());
        }

    }

    static class JarPathLocationScanner implements PathLocationScanner {


        private final String separator;

        public JarPathLocationScanner(String separator) {
            this.separator = separator;
        }

        @Override
        public Set<String> findResourceNames(String location, URL locationUrl) {
            JarFile jarFile;
            try {
                jarFile = getJarFromUrl(locationUrl);
            } catch (IOException e) {
                log.warn("Unable to determine jar from url (" + locationUrl + "): " + e.getMessage());
                return Collections.emptySet();
            }

            try {
                // For Tomcat and non-expanded WARs.
                String prefix = jarFile.getName().toLowerCase().endsWith(".war") ? "WEB-INF/classes/" : "";
                return findResourceNamesFromJarFile(jarFile, prefix, location);
            } finally {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }


        private JarFile getJarFromUrl(URL locationUrl) throws IOException {
            URLConnection con = locationUrl.openConnection();
            if (con instanceof JarURLConnection) {
                // Should usually be the case for traditional JAR files.
                JarURLConnection jarCon = (JarURLConnection) con;
                jarCon.setUseCaches(false);
                return jarCon.getJarFile();
            }

            // No JarURLConnection -> need to resort to URL file parsing.
            // We'll assume URLs of the format "jar:path!/entry", with the protocol
            // being arbitrary as long as following the entry format.
            // We'll also handle paths with and without leading "file:" prefix.
            String urlFile = locationUrl.getFile();

            int separatorIndex = urlFile.indexOf(separator);
            if (separatorIndex != -1) {
                String jarFileUrl = urlFile.substring(0, separatorIndex);
                if (jarFileUrl.startsWith("file:")) {
                    try {
                        return new JarFile(new URL(jarFileUrl).toURI().getSchemeSpecificPart());
                    } catch (URISyntaxException ex) {
                        // Fallback for URLs that are not valid URIs (should hardly ever happen).
                        return new JarFile(jarFileUrl.substring("file:".length()));
                    }
                }
                return new JarFile(jarFileUrl);
            }

            return new JarFile(urlFile);
        }


        private Set<String> findResourceNamesFromJarFile(JarFile jarFile, String prefix, String location) {
            String toScan = prefix + location + (location.endsWith("/") ? "" : "/");
            Set<String> resourceNames = new TreeSet<>();

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement().getName();
                if (entryName.startsWith(toScan)) {
                    resourceNames.add(entryName.substring(prefix.length()));
                }
            }

            return resourceNames;
        }
    }

    /**
     * Gets the physical location urls for this logical path on the classpath.
     *
     * @param location The location on the classpath.
     * @return The underlying physical URLs.
     */
    private List<URL> getLocationUrlsForPath(String location) {
        log.debug("Determining location urls for " + location + " using ClassLoader " + classLoader + " ...");

        List<URL> locationUrls = new ArrayList<>();
        Enumeration<URL> urls;
        try {
            urls = classLoader.getResources(location);
            while (urls.hasMoreElements()) {
                locationUrls.add(urls.nextElement());
            }
        } catch (IOException e) {
            log.warn("Unable to resolve location " + location + " (ClassLoader: " + classLoader + "): " + e.getMessage());
        }

        return locationUrls;
    }


    private boolean isTomcat(String protocol) {
        return "war".equals(protocol);
    }

    private boolean isWebSphere(String protocol) {
        return "wsjar".equals(protocol);
    }

    private boolean isWebLogic(String protocol) {
        return "zip".equals(protocol);
    }
}
