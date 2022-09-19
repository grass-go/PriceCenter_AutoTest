package tron.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TronlinkServerHttpClient {
    static HttpClient httpClient;
    static HttpClient httpClient2;
    static HttpGet httpGet;
    static HttpPost httppost;
    static HttpPut httpput;
    static HttpDeleteWithBody httpdelete;



    public static HttpResponse response;


    static Integer connectionTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpConnectionTimeout");
    static Integer soTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpSoTimeout");

    static {
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
        pccm.setDefaultMaxPerRoute(80);
        pccm.setMaxTotal(100);

        httpClient = new DefaultHttpClient(pccm);

        // if httpClient occur "javax.net.ssl.SSLException: Certificate for
        // <list.tronlink.org> doesn't match any of the subject alternative names", then
        // use below code
        TrustStrategy acceptingTrustStrategy = new TrustSelfSignedStrategy();
        try {
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            httpClient2 = HttpClients.custom().setSSLSocketFactory(scsf).setConnectionTimeToLive(10, TimeUnit.SECONDS)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public static HttpResponse createGetConnectWithHeader(String url, Map<String, String> params,
                                                          JSONObject requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                //此处有两种方式获取url，第一种是用URI来构建。第二种是string buffer拼接。此处用第二种。
                /*URIBuilder builder = new URIBuilder(url);
                if (params != null) {
                    for (String key : params.keySet()) {
                        builder.addParameter(key, params.get(key));
                    }
                }
                URI uri = builder.build();
                url = uri.toString();*/

                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httpGet = new HttpGet(url);
            if (header != null) {
                for (String key : header.keySet()) {
                    httpGet.setHeader(key, header.get(key));
                    log.info("Add key to header: " + key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                // SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
                // log.info("url: "+httpget.toString()+"\nparams: "+requestBody.toString());
            }

            log.info("" + httpGet);

            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
            httpGet.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createPostConnectWithHeader(String url, Map<String,String> params,
                                                           String requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httppost = new HttpPost(url);
            if (header != null) {
                for (String key : header.keySet()) {
                    httppost.setHeader(key, header.get(key));
                    log.info(key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody, Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // log.info("url: "+httppost.toString()+"\nparams: "+requestBody.toString());
            response = httpClient.execute(httppost);

        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createPostConnectWithHeader(String url, Map<String, String> params,
                                                           JSONArray requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httppost = new HttpPost(url);
            if (header != null) {
                for (String key : header.keySet()) {
                    httppost.setHeader(key, header.get(key));
                    log.info(key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toJSONString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // log.info("url: "+httppost.toString()+"\nparams: "+requestBody.toString());
            response = httpClient.execute(httppost);

        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;

    }

    public static HttpResponse createPostConnectWithHeader(String url, Map<String, String> params,
                                                           JSONObject requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httppost = new HttpPost(url);
            if (header != null) {
                for (String key : header.keySet()) {
                    httppost.setHeader(key, header.get(key));
                    log.info(key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            printHttpInfo(httppost, params, requestBody);
            response = httpClient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createPutConnect(String url, Map<String, String> params,
                                                JSONObject requestBody, Map<String, String> headers) {
        try {
            // if ssl issue occur, use httpClient.custome().setxxx to set timeout.
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

            httpput = new HttpPut(url);
            httpput.setHeader("Content-type", "application/json; charset=utf-8");
            httpput.setHeader("Connection", "Keep-Alive");
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpput.addHeader(key, headers.get(key));
                }
            }
            Header[] allHeaders = httpput.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                log.info("" + allHeaders[i]);
            }

            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            log.info("Put: "+url);

            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpput.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // log.info("url: " + httppost.toString() + "\n params:"+requestBody);
            response = httpClient.execute(httpput);
        } catch (Exception e) {
            e.printStackTrace();
            httpput.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createDeleteConnect(String url, Map<String, String> params,
                                                   JSONObject requestBody, Map<String, String> headers) {

        try {
            httpdelete = new HttpDeleteWithBody(url);
            // if ssl issue occur, use httpClient.custome().setxxx to set timeout.
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

            httpdelete.setHeader("Content-type", "application/json; charset=utf-8");
            httpdelete.setHeader("Connection", "Keep-Alive");
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpdelete.addHeader(key, headers.get(key));
                }
            }
            Header[] allHeaders = httpdelete.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                log.info("" + allHeaders[i]);
            }

            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            log.info("Delete: "+url);

            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpdelete.setEntity(entity);
            }

            response = httpClient.execute(httpdelete);
        } catch (Exception e) {
            e.printStackTrace();
            httpdelete.releaseConnection();
            return null;
        }
        return response;
    }

    public static void printHttpInfo(HttpPost httppost, Map<String, String> params, JSONObject requestbody) {
        log.info("begin print http info");
        if (httppost != null) {
            log.info("httppost = " + httppost);
        }
        if (params != null) {
            log.info("params = " + params);
        }
        if (requestbody != null) {
            log.info("requestbody = " + requestbody);
        }
        log.info("end print http info");
    }

    public static JSONArray parseResponse2Array(HttpResponse response) {
        try {
            String result = EntityUtils.toString(response.getEntity());
            log.info("======");
            log.info(result);
            log.info("======");
            StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
            response.setEntity(entity);
            JSONArray obj = JSONArray.parseArray(result);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject parseResponse2JsonObject(HttpResponse response) {
        try {
            String result = EntityUtils.toString(response.getEntity());
            log.info("======");
            log.info(result);
            log.info("======");
            StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
            response.setEntity(entity);
            JSONObject obj = JSONObject.parseObject(result);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseResponse2String(HttpResponse response) {
        try {
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printJsonObjectContent(JSONObject responseContent) {
        log.info("----------------------------Print JSON Start---------------------------");
        for (String str : responseContent.keySet()) {
            log.info(str + ":" + responseContent.get(str));
        }
        log.info("JSON content size are: " + responseContent.size());
        log.info("----------------------------Print JSON End-----------------------------");
    }

    public static void printJsonArrayContent(JSONArray responseContent) {
        log.info("----------------------------Print JSON Start---------------------------");
        for (int i = 0; i < responseContent.size(); i++) {
            for (String str : responseContent.getJSONObject(i).keySet()) {
                log.info(str + ":" + responseContent.get(i).toString());
            }
        }
        log.info("JSON content size are: " + responseContent.size());
        log.info("----------------------------Print JSON End-----------------------------");
    }

    public static void disConnect() {
        httppost.releaseConnection();
    }

    public static void disGetConnect() {
        httpGet.releaseConnection();
    }


}
