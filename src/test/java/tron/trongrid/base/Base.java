package tron.trongrid.base;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import tron.common.utils.Configuration;

public class Base {

  public static String queryAddress = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddress");
  public static String winAddress = Configuration.getByPath("testng.conf").getString("tronGrid.winContractAddress");
  public static String queryAddressBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressBase64");
  public static String bttTokenId = Configuration.getByPath("testng.conf").getString("tronGrid.bttTokenId");
  public static Long feeLimit = Configuration.getByPath("testng.conf").getLong("tronGrid.feeLimit");
  public static String zeroBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.zeroBase64");

  public static JSONObject responseContent;
  public static HttpResponse response;
  public static  String tronGridUrl = Configuration.getByPath("testng.conf").getString("tronGrid.tronGridUrl");
  static HttpClient httpClient;
  static HttpPost httppost;
  static Integer connectionTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpConnectionTimeout");
  static Integer soTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpSoTimeout");

  static {
    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
    pccm.setDefaultMaxPerRoute(80);
    pccm.setMaxTotal(100);

    httpClient = new DefaultHttpClient(pccm);
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccount(String queryAddress) {
    try {
      String requestUrl = tronGridUrl + "/wallet/getaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", queryAddress);
      userBaseObj2.addProperty("visible",true);
      response = createConnect(requestUrl, userBaseObj2);
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
  public static HttpResponse getAccountResource(String queryAddress) {
    try {
      String requestUrl = tronGridUrl + "/wallet/getaccountresource";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", queryAddress);
      userBaseObj2.addProperty("visible",true);
      response = createConnect(requestUrl, userBaseObj2);
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
  public static HttpResponse getAccountNet(String queryAddress) {
    try {
      String requestUrl = tronGridUrl + "/wallet/getaccountnet";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", queryAddress);
      userBaseObj2.addProperty("visible",true);
      response = createConnect(requestUrl, userBaseObj2);
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
  public static HttpResponse triggerConstantContract(String ownerAddress,
      String contractAddress, String functionSelector, String parameter, Long feeLimit) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/triggerconstantcontract";
      JsonObject userBaseObj2 = new JsonObject();

      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("function_selector", functionSelector);
      userBaseObj2.addProperty("parameter", parameter);
      userBaseObj2.addProperty("fee_limit", feeLimit);
      userBaseObj2.addProperty("visible", true);

      response = createConnect(requestUrl, userBaseObj2);
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
  public static HttpResponse createAddress(String password) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/createaddress";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", password);
      userBaseObj2.addProperty("visible", true);
      response = createConnect(requestUrl, userBaseObj2);
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
  public static HttpResponse generateAddress(Boolean visible) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/generateaddress";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
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
  public static HttpResponse createConnect(String url, JsonObject requestBody) {
    try {
      httpClient.getParams()
          .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
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
  public static Boolean verificationResult(HttpResponse response) {
    if (response.getStatusLine().getStatusCode() != 200) {
      return false;
    }
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    return Boolean.valueOf(responseContent.getString("result")).booleanValue();
  }

  /**
   * constructor.
   */
  public static JSONObject parseResponseContent(HttpResponse response) {
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

  /**
   * constructor.
   */
  public static void printJsonContent(JSONObject responseContent) {
    System.out.println("----------------------------Print JSON Start---------------------------");
    for (String str : responseContent.keySet()) {
      System.out.println(str + ":" + responseContent.get(str));
    }
    System.out.println("JSON content size are: " + responseContent.size());
    System.out.println("----------------------------Print JSON End-----------------------------");
  }

  /**
   * constructor.
   */
  public static void disConnect() {
    httppost.releaseConnection();
  }

}
