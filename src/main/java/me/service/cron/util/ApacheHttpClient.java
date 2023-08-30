package me.service.cron.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

/**
 * 描述：
 * 2021/5/28 15:47.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class ApacheHttpClient {


    private ApacheHttpClient() {
    }

    /**
     * 编码格式。发送编码格式统一用UTF-8
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 设置连接超时时间，单位毫秒。
     */
    private static final int CONNECT_TIMEOUT = 10000;

    /**
     * 请求获取数据的超时时间(即响应时间)，单位毫秒。
     */
    private static final int SOCKET_TIMEOUT = 60 * 1000;

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";


    /**
     * 池化管理
     */
    private static PoolingHttpClientConnectionManager poolConnManager = null;

    /**
     * 它是线程安全的，所有的线程都可以使用它一起发送http请求
     */
    private static CloseableHttpClient httpClient;

    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", new SSLConnectionSocketFactory(builder.build())).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 同时最多连接数
            poolConnManager.setMaxTotal(640);
            // 设置最大路由
            poolConnManager.setDefaultMaxPerRoute(320);
            // 此处解释下MaxtTotal和DefaultMaxPerRoute的区别：
            // 1、MaxtTotal是整个池子的大小；
            // 2、DefaultMaxPerRoute是根据连接到的主机对MaxTotal的一个细分；比如：
            // MaxtTotal=400 DefaultMaxPerRoute=200
            // 而我只连接到http://www.abc.com时，到这个主机的并发最多只有200；而不是400；
            // 而我连接到http://www.bac.com 和
            // http://www.ccd.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；所以起作用的设置是DefaultMaxPerRoute
            // 初始化httpClient
            httpClient = getConnection();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static CloseableHttpClient getConnection() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
        return HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(poolConnManager)
                .setDefaultRequestConfig(config)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false)).build();
    }


    public static JSONObject httpGetJson(String url) throws IOException {
        log.info("http request httpGetJson url:{}", url);
        HttpGet httpGet = new HttpGet(URI.create(url));
        CloseableHttpResponse execute = httpClient.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        String result = EntityUtils.toString(entity, ENCODING);
        return JSON.parseObject(result);
    }

    public static String httpGet(String url, String params) throws IOException {
        log.info("http request httpGetJson url:{}", url);
        //组装参数
        if (StringUtils.isNotBlank(params)) {
            JSONObject jsonObject = JSON.parseObject(params);
            String collect = jsonObject.keySet().stream().map(k -> k + "=" + jsonObject.get(k)).collect(Collectors.joining("&"));
            url = url + "?" + collect;
        }
        HttpGet httpGet = new HttpGet(URI.create(url));
        CloseableHttpResponse execute = httpClient.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        return EntityUtils.toString(entity, ENCODING);
    }

    public static String httpPost(String url, String params) throws IOException {
        log.info("http request httpPostJson url:{} params :{}", url, params);
        HttpPost httpPost = new HttpPost(URI.create(url));
        httpPost.setHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
        if (StringUtils.isNotBlank(params)) {
            httpPost.setEntity(new StringEntity(params, ContentType.create(CONTENT_TYPE_JSON, ENCODING)));
        }
        CloseableHttpResponse entity = httpClient.execute(httpPost);
        return EntityUtils.toString(entity.getEntity(), ENCODING);
    }

    public static String httpPut(String url, String params) throws IOException {
        log.info("http request httpPutJson url:{} params :{}", url, params);
        HttpPut httpPut = new HttpPut(URI.create(url));
        httpPut.setHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
        if (StringUtils.isNotBlank(params)) {
            httpPut.setEntity(new StringEntity(params, ContentType.create(CONTENT_TYPE_JSON, ENCODING)));
        }
        CloseableHttpResponse entity = httpClient.execute(httpPut);
        return EntityUtils.toString(entity.getEntity(), ENCODING);
    }

    public static String httpDelete(String url, String params) throws IOException {
        log.info("http request httpDeleteJson url:{} params :{}", url, params);
        HttpDelete httpDelete = new HttpDelete(URI.create(url));
        httpDelete.setHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
        if (StringUtils.isNotBlank(params)) {
//            httpDelete.setParams(new StringEntity(params, ContentType.create(CONTENT_TYPE_JSON, ENCODING)));
        }
        CloseableHttpResponse entity = httpClient.execute(httpDelete);
        return EntityUtils.toString(entity.getEntity(), ENCODING);
    }


    public static byte[] httpGetByByteBuffer(String url) throws IOException {
        log.info("http request httpGetByByteBuffer url:{}", url);
        HttpGet httpGet = new HttpGet(URI.create(url));
        CloseableHttpResponse execute = httpClient.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        long contentLength = entity.getContentLength();
        return readInputStream(contentLength, entity.getContent());
    }

    public static byte[] httpPostByteBuffer(String url, String params) throws IOException {
        log.info("http request httpPostByteBuffer url:{} params:{}", url, params);
        HttpPost httpPost = new HttpPost(URI.create(url));
        httpPost.setHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
        httpPost.setEntity(new StringEntity(params, ContentType.create(CONTENT_TYPE_JSON, ENCODING)));
        CloseableHttpResponse execute = httpClient.execute(httpPost);
        HttpEntity entity = execute.getEntity();
        long contentLength = entity.getContentLength();
        return readInputStream(contentLength, entity.getContent());
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param contentLength
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(long contentLength, InputStream inputStream) throws IOException {
        long count = 0L;
        long progress = 0L;
        byte[] buffer = new byte[2048];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
            count += len;
            progress = progress(progress, count, contentLength);
        }
        bos.close();
        return bos.toByteArray();
    }


    public static long progress(long progress, long count, long contentLength) {
        BigDecimal length = BigDecimal.valueOf(contentLength / 100);
        if (length.compareTo(BigDecimal.ZERO) == 0) {
            return progress;
        }
        BigDecimal divide = BigDecimal.valueOf(count).divide(length, 1, RoundingMode.DOWN);
        if (divide.intValue() % 10 == 0 && progress < divide.intValue()) {
            log.info("download file buffer progress total_size:【{}byte】 now_size:【{}byte】,progress:【{}%】", contentLength, count, divide.intValue());
        }
        return divide.longValue();
    }

}
