package me.service.cron.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author cm
 * @description
 * @date 2021/05/21
 */
public class DynamicLoadUtils {

    public static void main(String[] args) throws Exception {
        String path = "F:\\commons-lang3-3.1.jar";
        //String path = "commons-lang3-3.1.jar";//jar文件需放在工程目录下

        loadJar(path);
        Class<?> aClass = Class.forName("org.apache.commons.lang3.StringUtils");
        Object instance = aClass.newInstance();
        Object strip = aClass.getDeclaredMethod("strip", String.class, String.class).invoke(instance, "[1,2,3,4,5,6,2,3,5,1]", "[]");
        System.out.println(strip);
    }

    public static void loadPath(File jarPath) {
        if (!jarPath.exists()) {
            return;
        }
        if (jarPath.isFile()) {
            return;
        }
        File[] files = jarPath.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".jar")) {
                loadJar(file);
            }
        }
    }

    /**
     * @param jarPath
     * @throws MalformedURLException
     */
    public static void loadJar(String jarPath) {
        System.out.println("====>> into loadJar. jarPath: " + jarPath);
        // 从URLClassLoader类中获取类所在文件夹的方法，jar也可以认为是一个文件夹
        File jarFile = new File(jarPath);

        if (!jarFile.exists()) {
            System.out.println("jar file not found.");
            return;
        }
        loadJar(jarFile);
    }


    /**
     * @param jarFile
     * @throws MalformedURLException
     */
    public static void loadJar(File jarFile) {
        System.out.println("====>> into loadJar. jarPath: " + jarFile.getAbsolutePath());
        //获取类加载器的addURL方法，准备动态调用
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        if (method == null) {
            return;
        }
        // 获取方法的访问权限，保存原始值
        boolean accessible = method.isAccessible();
        try {
            //修改访问权限为可写
            if (!accessible) {
                method.setAccessible(true);
            }
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            //获取jar文件的url路径
            URL url = jarFile.toURI().toURL();
            //jar路径加入到系统url路径里
            method.invoke(classLoader, url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //回写访问权限
            method.setAccessible(accessible);
        }

    }

}