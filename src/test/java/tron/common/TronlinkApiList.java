package tron.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.parser.JSONParser;
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
import tron.common.utils.Configuration;
@Slf4j
public class TronlinkApiList {

  static HttpClient httpClient;
  static HttpPost httppost;
  static HttpGet httpget;
  public static String HttpNode = "https://list.tronlink.org";
  static HttpResponse response;
  static Integer connectionTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpConnectionTimeout");
  static Integer soTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpSoTimeout");
  static String transactionString;
  static String transactionSignString;
  static JSONObject responseContent;
  static JSONObject signResponseContent;
  static JSONObject transactionApprovedListContent;
  static Long requestTime = 0L;

  static {
    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
    pccm.setDefaultMaxPerRoute(80);
    pccm.setMaxTotal(100);

    httpClient = new DefaultHttpClient(pccm);
  }

  public static HttpResponse classify(String node) {
    try {
      String requestUrl = "http://" + node + "/api/dapp/v2/classify";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse hot_recommend(String node) {
    try {
      String requestUrl = "http://" + node + "api/dapp/v2/dapp/hot_recommend";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse head(String node) {
    try {
      String requestUrl = "http://" + node + "api/dapp/v2/head";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }
  public static HttpResponse hot_search(String node) {
    try {
      String requestUrl = "http://" + node + "api/dapp/v2/dapp/hot_search";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse dapp_list(String node, Map<String, String> params) {
    try {
      String requestUrl = "http://" + node + "api/dapp/v2/dapp";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }
public static HttpResponse search(String node, Map<String, String> params) {
    try {
      String requestUrl = "http://" + node + "api/dapp/v2/dapp/search";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse history(String node, Map<String, String> params) {
    try {
      String requestUrl = "http://" + node + "api/dapp/v2/dapp/history";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse allasset(String node,String address) {
    try {
      String requestUrl = "https://" + node + "api/wallet/class/allasset";
      System.out.println("requestUrl"+requestUrl);
      JsonObject body = new JsonObject();
      body.addProperty("address", address);
      response = createPostConnect(requestUrl,body);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse assetlist(String node,String address) {
    try {
      String requestUrl = "https://" + node + "api/wallet/assetlist";
      System.out.println("requestUrl"+requestUrl);
      JsonObject body = new JsonObject();
      body.addProperty("address", address);
      response = createPostConnect(requestUrl,body);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse hot_token(String node,String address) {
    try {
      String requestUrl = "https://" + node + "api/wallet/hot_token";
      System.out.println("requestUrl"+requestUrl);
      JsonObject body = new JsonObject();
      body.addProperty("address", address);
      response = createPostConnect(requestUrl,body);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse addasset(String node,String json) {
    try {
      String requestUrl = "https://" + node + "api/wallet/addasset";
      System.out.println("requestUrl"+requestUrl);
      response = createPostConnect(requestUrl,json);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse addAsset(String node ,JSONObject address) throws Exception {
    final String requestUrl ="https://" + node + "/api/wallet/addasset";
    response = createConnect(requestUrl, address);
    return response;
  }
  public static HttpResponse getAllClassAsset(String node ,JSONObject address) throws Exception {
    final String requestUrl = node + "/api/wallet/class/allasset";
    response = createConnect(requestUrl, address);
    return response;
  }

  public static List<String> getTrc10TokenIdList(JSONArray tokenArray) throws Exception {
    List<String> tokenIdList = new ArrayList<>();
    String id = "";
    for (int i = 0; i < tokenArray.size();i++) {
      id = tokenArray.getJSONObject(i).getString("id");
      if (id.isEmpty()){
        continue;
      }
      tokenIdList.add(id);
    }
    return tokenIdList;
  }

  public static List<String> getTrc20AddressList(JSONArray tokenArray) throws Exception {
    List<String> trc20ContractAddressList = new ArrayList<>();
    String contractAddress = "";
    for (int i = 0; i < tokenArray.size();i++) {
      contractAddress = tokenArray.getJSONObject(i).getString("contractAddress");
      if (contractAddress.isEmpty()){
        continue;
      }
      trc20ContractAddressList.add(contractAddress);
    }
    return trc20ContractAddressList;
  }

  /**
   * constructor.
   */
  public static HttpResponse getInviteCode(JSONObject param) throws Exception {
    final String requestUrl = HttpNode + "/api/wallet/invite/get_code";
    response = createConnect(requestUrl, param);
    return response;
  }

  public static HttpResponse createPostConnect(String url, String requestBody) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
      System.out.println("请求开始时间： " + formatter.format(new Date()));
      response = httpClient.execute(httppost);
      System.out.println("请求结束时间： " + formatter.format(new Date()));
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse insertInviteCode(JSONObject param) throws Exception {
    final String requestUrl = HttpNode + "/api/wallet/invite/code";
    response = createConnect(requestUrl, param);
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse createPostConnect(String url, JsonObject requestBody) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
      System.out.println("请求开始时间： " + formatter.format(new Date()));
      response = httpClient.execute(httppost);
      System.out.println("请求结束时间： " + formatter.format(new Date()));
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse createGetConnect(String url) {
    return createGetConnect(url, null);
  }

  /**
   * constructor.
   */
  public static HttpResponse createGetConnect(String url, Map<String, String> params) {
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
      httpget = new HttpGet(url);
      httpget.addHeader("Lang","1");
      httpget.setHeader("Content-type", "application/json; charset=utf-8");
      httpget.setHeader("Connection", "Keep-Alive");
//      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
//      System.out.println("请求开始时间： "+formatter.format(new Date()));
      Instant startTime = Instant.now();
      response = httpClient.execute(httpget);
      Instant endTime = Instant.now();
      requestTime = Duration.between(startTime, endTime).toMillis();
      System.out.println(url + " 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
//      System.out.println("请求结束时间： "+formatter.format(new Date()));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static JSONArray parseArrayResponseContent(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      System.out.println(result);
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      JSONArray obj = JSONArray.parseArray(result);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONArray parseResponseContent(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
//      result = result.substring(0, result.lastIndexOf("}"));
//      result = result + ",\"requestTime\":" + requestTime + "}";
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      JSONArray obj = JSONArray.parseArray(result);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static JSONObject parseJsonObResponseContent(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
//      result = result.substring(0, result.lastIndexOf("}"));
//      result = result + ",\"requestTime\":" + requestTime + "}";
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      JSONObject obj = JSONObject.parseObject(result);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * constructor.
   */
  public static void printJsonContent(JSONObject responseContent) {
    log.info("----------------------------Print JSON Start---------------------------");
    for (String str : responseContent.keySet()) {
      log.info(str + ":" + responseContent.get(str));
    }
    log.info("JSON content size are: " + responseContent.size());
    log.info("----------------------------Print JSON End-----------------------------");
  }

  /**
   * constructor.
   */
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
  public static HttpResponse createConnect(String url, JSONObject requestBody) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      System.out.println(httppost.toString());
      response = httpClient.execute(httppost);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }



  /**
   * constructor.
   */
  public static void disConnect() {
    httppost.releaseConnection();
  }

  /**
   * constructor.
   */
  public static void disGetConnect() {
    httpget.releaseConnection();
  }


}
