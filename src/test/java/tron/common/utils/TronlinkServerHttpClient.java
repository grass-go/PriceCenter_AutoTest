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
import tron.tronlink.base.GetSign;
import tron.tronlink.base.TronlinkBase;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TronlinkServerHttpClient {
    public static HttpClient httpClient;
    public static HttpClient httpClient2;
    public static HttpGet httpget;
    public static HttpPost httppost;
    public static HttpPut httpput;
    public static HttpDeleteWithBody httpdelete;

    public static HttpResponse response;


    //business related
    public static String HttpNode = TronlinkBase.tronlinkUrl;
    //new sig needed parameter
    public static String defaultSys = "AndroidTest";
    public static String defaultLang = "1";
    public static String defaultPkg = "com.tronlinkpro.wallet";
    public static String defaultVersion = "4.11.0";
    //设置的老接口从本版本开始鉴权。对于chrome，从4.0.0开始,对于安卓从4.10.0开始
    public static String chromeUpdateVersion = "4.0.0";
    public static String androidUpdateVersion = "4.11.0";


    public static Integer connectionTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpConnectionTimeout");
    public static Integer soTimeout = Configuration.getByPath("testng.conf")
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

    public static HttpResponse createGetConnect(String url, Map<String, String> params,
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
            httpget = new HttpGet(url);
            if (header != null) {
                for (String key : header.keySet()) {
                    httpget.setHeader(key, header.get(key));
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

            log.info("" + httpget);

            response = httpClient.execute(httpget);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createPostConnect(String url, Map<String,String> params,
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

    public static HttpResponse createPostConnect(String url, Map<String, String> params,
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

    public static HttpResponse createPostConnect(String url, Map<String, String> params,
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
            log.info("===========result============");
            log.info(result);
            log.info("===========result============");
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
        httpget.releaseConnection();
    }

    //below are signature related
    public static Map<String, String> AddMap(Map<String, String> map1, Map<String, String> map2){
        Set myKeys = map2.keySet();
        for (Object o : myKeys){
            map1.put((String) o, map2.get(o));
        }
        return map1;
    }

    public static Map<String, String> getNewSigHeader(String needSys, String testVersion, String testLang, String testPkg) {
        Map<String, String> header = new HashMap<>();
        if (needSys.equals("chrome-extension-test")){
            header.put("System","chrome-extension-test");
        } else if(needSys.equals("chrome-extension")){
            header.put("System","chrome-extension");
        } else if(needSys.equals("Chrome")){
            header.put("System","Chrome");
        } else if(needSys.equals("AndroidTest")){
            header.put("System","AndroidTest");
        } else if(needSys.equals("Android")){
            header.put("System","Android");
        } else if(needSys.equals("iOSTest")){
            header.put("System","iOSTest");
        } else if(needSys.equals("iOS")){
            header.put("System","iOS");
        }
        header.put("Lang",testLang);
        header.put("Version",testVersion);
        header.put("DeviceID","fca5a022-5526-45f5-a7e7");
        header.put("chain","MainChain");
        header.put("channel","official");

        header.put("ts", java.lang.String.valueOf(System.currentTimeMillis()));
        //header.put("ts","1609302220000");

        //header.put("packageName", "com.tronlinkpro.wallet");
        header.put("packageName", testPkg);
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Keep-Alive");

        header.put("deviceName","wqqtest");
        header.put("osVersion","10.0.0");

        return header;
    }
    public static Map<String, String> getNewSigParams(String needSys) {
        Map<String, String> params = new HashMap<>();

        if (needSys.equals("chrome-extension-test")){
            params.put("secretId","8JKSO2PM4M2K45EL");
        } else if(needSys.equals("chrome-extension")){
            params.put("secretId","AE68A487AA919CAE");
        } else if(needSys.equals("Chrome")){
            params.put("secretId","AE68A487AA919CAE");
        } else if(needSys.equals("AndroidTest")){
            params.put("secretId","SFSUIOJBFMLKSJIF");
        } else if(needSys.equals("Android")){
            params.put("secretId","A4ADE880F46CA8D4");
        } else if(needSys.equals("iOSTest")){
            params.put("secretId","JSKLJKFJDFDSFER3");
        } else if(needSys.equals("iOS")){
            params.put("secretId","ED151200DD0B3B52");
        }
        params.put("nonce","12345");
        return params;
    }

    public static String getNewSignature(String curUri,String httpMethod, String address, Map<String, String> params, Map<String, String> headers) {
        //Map<String, String> params = getNewSigParams(needSys);
        //Map<String, String> headers = getNewSigHeader(needSys, testVersion, testLang, testPkg);
        GetSign getSign = new GetSign();
        String signature = "";
        try {
            signature = URLEncoder.encode(getSign.getSignature(
                    headers.get("channel"),
                    headers.get("chain"),
                    headers.get("Lang"),
                    address,
                    params.get("nonce"),
                    params.get("secretId"),
                    headers.get("System"),
                    headers.get("DeviceID"),
                    headers.get("ts"),
                    headers.get("Version"),
                    curUri,
                    httpMethod));
        }catch (Exception e) {
            log.info("getNewSignature Error!");
            return null;
        }
        return signature;
    }

    public static HttpResponse createGetConnectWithSignature(String curURI, Map<String, String> caseParams,Map<String, String> caseHeader, JSONObject object ) {

        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        String curSystem = defaultSys;
        String curVersion = defaultVersion;
        String curLang = defaultLang;
        String curPkg = defaultPkg;
        if (caseHeader != null && caseHeader.containsKey("System")){
            curSystem = caseHeader.get("System");
        }
        if (caseHeader != null && caseHeader.containsKey("Version")){
            curVersion = caseHeader.get("Version");
        }
        if (caseHeader != null && caseHeader.containsKey("Lang")){
            curLang = caseHeader.get("Lang");
        }
        if (caseHeader != null && caseHeader.containsKey("Package")){
            curPkg = caseHeader.get("packageName");
        }

        params = getNewSigParams(curSystem);
        if(caseParams != null){
            params = AddMap(params, caseParams);
        }
        headers = getNewSigHeader(curSystem, curVersion, curLang, curPkg);
        if(caseHeader != null) {
            headers = AddMap(headers, caseHeader);
        }

        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"GET", caseParams.get("address"), params, headers);
        params.put("signature", cursig);
        String requestUrl = HttpNode + curURI;
        response = createGetConnect(requestUrl, params, object, headers);
        return response;
    }

    public static HttpResponse createPostConnectWithSignature(String curURI,Map<String, String> caseParams, Map<String, String> caseHeader,JSONObject object ) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        String curSystem = defaultSys;
        String curVersion = defaultVersion;
        String curLang = defaultLang;
        String curPkg = defaultPkg;
        if (caseHeader != null && caseHeader.containsKey("System")){
            curSystem = caseHeader.get("System");
        }
        if (caseHeader != null && caseHeader.containsKey("Version")){
            curVersion = caseHeader.get("Version");
        }
        if (caseHeader != null && caseHeader.containsKey("Lang")){
            curLang = caseHeader.get("Lang");
        }
        if (caseHeader != null && caseHeader.containsKey("Package")){
            curPkg = caseHeader.get("packageName");
        }

        params = getNewSigParams(curSystem);
        if(caseParams != null){
            params = AddMap(params, caseParams);
        }
        headers = getNewSigHeader(curSystem, curVersion, curLang, curPkg);
        if(caseHeader != null) {
            headers = AddMap(headers, caseHeader);
        }

        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"POST", caseParams.get("address"), params, headers);
        params.put("signature", cursig);
        String requestUrl = HttpNode + curURI;
        response = createPostConnect(requestUrl, params, object, headers);
        return response;
    }

    public static HttpResponse createPostConnectWithSignature(String curURI,Map<String, String> caseParams, Map<String, String> caseHeader,JSONArray object ) {
        Map<String, String> params = getNewSigParams(defaultSys);
        if(caseParams != null){
            params = AddMap(params, caseParams);
        }

        Map<String, String> headers = getNewSigHeader(defaultSys, defaultVersion, defaultLang, defaultPkg);
        if(caseHeader != null) {
            headers = AddMap(headers, caseHeader);
        }
        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"POST", caseParams.get("address"), params, headers);
        params.put("signature", cursig);

        String requestUrl = HttpNode + curURI;
        response = createPostConnect(requestUrl, params, object, headers);
        return response;
    }

}
