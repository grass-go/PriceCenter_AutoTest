package tron.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import tron.common.utils.Configuration;
import tron.priceCenter.base.priceBase;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
public class PriceCenterApiList {
    static HttpClient httpClient;
    static HttpGet httpget;
    static HttpGet httpGet;

    //public static String HttpNode = priceBase.priceUrl;
    public static String HttpNode = "https://c.tronlink.org";
    public static String TranscanHttpNode = priceBase.tronscanApiUrl;
    static HttpResponse response;
    static Integer connectionTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpConnectionTimeout");
    static Integer soTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpSoTimeout");
    static JSONObject responseContent;
    static Long requestTime = 0L;

    static {
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
        pccm.setDefaultMaxPerRoute(80);
        pccm.setMaxTotal(100);

        httpClient = new DefaultHttpClient(pccm);
    }

    public static HttpResponse createGetConnect(URI uri) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            log.info("url: " +uri.toString());
            httpGet = new HttpGet(uri);
            httpGet.setHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Connection", "Close");
            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
            httpGet.releaseConnection();
            return null;
        }
        return response;
    }


    public static HttpResponse getprice(Map<String, String> param) {
        try{
            String requestUrl = HttpNode + "/v1/cryptocurrency/getprice";
            URIBuilder builder = new URIBuilder(requestUrl);
            if(param !=null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI requestUri = builder.build();
            response = createGetConnect(requestUri);
            return response;
        } catch (Exception e){
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
    }

    public static HttpResponse searchtypeFromTranScan(Map<String, String> param) {
        try{
            String requestUrl = TranscanHttpNode + "/api/search-type";
            URIBuilder builder = new URIBuilder(requestUrl);
            if(param !=null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI requestUri = builder.build();
            response = createGetConnect(requestUri);
            return response;
        } catch (Exception e){
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
    }

    public static HttpResponse getallprice(){
        try {
            String requestUrl = HttpNode + "/v1/cryptocurrency/getallprice";
            URIBuilder builder = new URIBuilder(requestUrl);
            URI requestUri = builder.build();
            response = createGetConnect(requestUri);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
    }
}
