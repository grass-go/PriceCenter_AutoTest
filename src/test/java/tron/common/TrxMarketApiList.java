package tron.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import sun.plugin.dom.core.CoreConstants;
import tron.common.utils.Configuration;

import java.nio.charset.Charset;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Slf4j
public class TrxMarketApiList {

    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpGet httpget;
    static HttpResponse response;
    static Integer connectionTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpConnectionTimeout");
    static Integer soTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpSoTimeout");
    static Long requestTime = 0L;

    static {
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
        pccm.setDefaultMaxPerRoute(80);
        pccm.setMaxTotal(100);

        httpClient = new DefaultHttpClient(pccm);
    }


    public static HttpResponse getList(String trxMarketNode){
        try {
            String requestUrl = "https://" + trxMarketNode + "api/exchange/marketPair/list";
            System.out.println(requestUrl);
            response = createGetConnect(requestUrl);
        } catch (Exception e){
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;

    }

    public static HttpResponse createGetConnect(String url){
        return createGetConnect(url, null);

    }

    public static HttpResponse createGetConnect(String url, Map<String, String> params){
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,soTimeout);
            httpget = new HttpGet(url);
            httpget.setHeader("Content-type", "application/json; charset=utf-8");
            httpget.setHeader("Connection", "Close");
            Instant startTime = Instant.now();
            response = httpClient.execute(httpget);
            Instant endTime = Instant.now();
            requestTime = Duration.between(startTime, endTime).toMillis();
            System.out.println(url + " 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
        } catch (Exception e){
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static JSONObject parseResponseContent(HttpResponse response){
        try {
            String result = EntityUtils.toString(response.getEntity());
            StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
            response.setEntity(entity);
            JSONObject obj = JSONObject.parseObject(result);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printJsonContent(JSONObject responseContent){
        log.info("----------------------------Print JSON Start---------------------------");
        for (String str : responseContent.keySet()) {
            log.info(str + ":" + responseContent.get(str));
        }
        log.info("JSON content size are: " + responseContent.size());
        log.info("----------------------------Print JSON End-----------------------------");
    }

    public static void disGetConnect(){
        httpget.releaseConnection();
    }


}
