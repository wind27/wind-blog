package com.wind.blog.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求工具类
 *
 * @author : qianchun 2016/12/2
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static PoolingHttpClientConnectionManager connMgr;

    private static RequestConfig requestConfig;

    private static final int MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // // 在提交请求之前 测试连接是否可用
        // configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 获取 header
     * 
     * @return 返回结果
     */
    public static Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
        headers.put("Accept-Encoding", "gzip, deflate, sdch");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8");
        return headers;
    }

    /**
     * http get
     * 
     * @param apiUrl 请求URL
     * @param headers headers
     * @return 返回结果
     */
    public static String doGet(String apiUrl, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpGet.setConfig(requestConfig);
            if (headers != null && headers.size() > 0) {
                for (String key : headers.keySet()) {
                    if (key != null) {
                        httpGet.addHeader(key, headers.get(key));
                    }
                }
            }
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            // logger.info("HTTP 请求成功，url={}", apiUrl);
        } catch (Exception e) {
            logger.error("HTTP 请求异常，url={}, errorMsg={}", apiUrl, e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("HTTP 请求异常，url={}, errorMsg={}", apiUrl, e);
                }
            }
        }
        return httpStr;
    }

    /**
     * http post
     * 
     * @param apiUrl url
     * @param headers headers
     * @param params 参数
     * @return 返回结果
     */
    public static String doPostSSL(String apiUrl, Map<String, String> headers, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            if (!CollectionUtils.isEmpty(headers)) {
                for (String key : headers.keySet()) {
                    if (key != null) {
                        httpPost.addHeader(key, headers.get(key));
                    }
                }
            }
            if (params == null) {
                params = new HashMap<>();
            }
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(params), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            logger.info("HTTP 请求成功，url={}, result={}", apiUrl, httpStr);
        } catch (Exception e) {
            logger.error("HTTP 请求异常，url={}, errorMsg={}", apiUrl, e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("HTTP 请求异常，url={}, errorMsg={}", apiUrl, e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     * 
     * @param apiUrl API接口URL
     * @param json JSON对象
     * @return 返回结果
     */
    public static String doPostSSL(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(json), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            logger.info("HTTP 请求成功，url={}, result={}", apiUrl, httpStr);
        } catch (Exception e) {
            logger.error("HTTP 请求异常，url={}, errorMsg={}", apiUrl, e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.error("HTTP 请求异常，url={}, errorMsg={}", apiUrl, e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return 返回结果
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return arg0.equalsIgnoreCase(arg1.getPeerHost()); // Compliant
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            logger.error("HTTP 创建 SSL 安全连接出错errorMsg={}", e);
        }
        return sslsf;
    }


    /**
     * check url is available
     * @param linkUrl 链接URL
     * @return 返回结果
     */
    public static boolean checkUrlEnable(String linkUrl) {
        if (StringUtils.isEmpty(linkUrl)) {
            return false;
        }

        URL url = null;
        try {
            url = new URL(linkUrl);
            InputStream in = url.openStream();
            return true;
        } catch (Exception e1) {
            if (url != null) {
                url = null;
            }
            return false;
        }
    }
}