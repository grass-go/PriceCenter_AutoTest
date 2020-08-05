package tron.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import tron.common.utils.Configuration;
import tron.trondata.base.TrondataBase;
import tron.tronlink.base.TronlinkBase;

import java.net.URI;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
public class TronlinkApiList {

  static HttpClient httpClient;
  static HttpPost httppost;
  static HttpGet httpget;

  static HttpGet httpGet;
  public static String HttpTronDataNode = TrondataBase.trondataUrl;
  public static String HttpNode = TronlinkBase.tronlinkUrl;
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

  public static HttpResponse classify() {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/classify";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse hot_recommend() {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/dapp/hot_recommend";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse multiTrxReword(HashMap<String, String> param) throws Exception{
    final String requestUrl = HttpNode + "/api/wallet/multi/trx_record";
    URIBuilder builder = new URIBuilder(requestUrl);
    if (param != null) {
      for (String key : param.keySet()) {
        builder.addParameter(key, param.get(key));
      }
    }
    URI uri = builder.build();
    //System.out.println(requestUrl);
    response = createGetConnect(uri);
    Assert.assertTrue(TronlinkApiList.verificationResult(response));
    return response;
  }

  public static HttpResponse trc20Info(HashMap<String, String> param) throws Exception{
    final String requestUrl = HttpNode + "/api/wallet/trc20_info";
    URIBuilder builder = new URIBuilder(requestUrl);
    if (param != null) {
      for (String key : param.keySet()) {
        builder.addParameter(key, param.get(key));
      }
    }
    URI uri = builder.build();
    //System.out.println(requestUrl);
    response = createGetConnect(uri);
    return response;
  }

  public static HttpResponse apiTransferTrx(HashMap<String, String> param) throws Exception{
    final String requestUrl = HttpNode + "/api/transfer/trx";
    URIBuilder builder = new URIBuilder(requestUrl);
    if (param != null) {
      for (String key : param.keySet()) {
        builder.addParameter(key, param.get(key));
      }
    }
    URI uri = builder.build();
    //System.out.println(requestUrl);
    response = createGetConnect(uri);
    return response;
  }

  public static HttpResponse apiTransferToken10(HashMap<String, String> param) throws Exception{
    final String requestUrl = HttpNode + "/api/transfer/token10";
    URIBuilder builder = new URIBuilder(requestUrl);
    if (param != null) {
      for (String key : param.keySet()) {
        builder.addParameter(key, param.get(key));
      }
    }
    URI uri = builder.build();
    //System.out.println(requestUrl);
    response = createGetConnect(uri);
    return response;
  }

  public static HttpResponse apiTransferTrc20(HashMap<String, String> param) throws Exception{
    final String requestUrl = HttpNode + "/api/transfer/trc20";
    URIBuilder builder = new URIBuilder(requestUrl);
    if (param != null) {
      for (String key : param.keySet()) {
        builder.addParameter(key, param.get(key));
      }
    }
    URI uri = builder.build();
    //System.out.println(requestUrl);
    response = createGetConnect(uri);
    return response;
  }

  public static HttpResponse votingV2Witness(Map<String, String> params) throws Exception{
    String requestUrl = HttpNode + "/api/voting/v2/witness";

    //System.out.println(requestUrl);
    response = createGetConnect(requestUrl, params);
    return response;
  }

  public static boolean getAllWitnessFromTronscan() {
//    String requestUrl = "https://apilist.tronscan.org/api/vote/witness";
    String requestUrl = TronlinkBase.tronscanApiUrl;
    response = createGetConnect(requestUrl);
//    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    if ((response.getStatusLine().getStatusCode() != 200)
            || responseContent == null
            || !(responseContent.containsKey("total"))
            || !(responseContent.getInteger("total") > 0)
            || !(responseContent.containsKey("totalVotes"))
            || !(responseContent.getInteger("totalVotes") > 0)
            || !(responseContent.containsKey("data"))
            || !(responseContent.getJSONArray("data").size() > 0)) {
      return false;
    }
    return true;
  }

  public static HttpResponse votingV2Search(Map<String, String> params) throws Exception{
    String requestUrl = HttpNode + "/api/voting/v2/search";

    response = createGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse votingV2Self(Map<String, String> params) throws Exception{
    String requestUrl = HttpNode + "/api/voting/v2/self";

    response = createGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse walletMarketBanner() {
    String requestUrl = HttpNode + "/api/wallet/market/banner";

    response = createGetConnect(requestUrl);
    return response;
  }

  public static HttpResponse walletMarketFavorite(Map<String, String> params) throws Exception{
    String requestUrl = HttpNode + "/api/wallet/market/favorite";

    response = createGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse walletMarketTrxUsdt(Map<String, String> params) throws Exception{
    String requestUrl = HttpNode + "/api/wallet/market/trx_usdt";

    response = createGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse head() {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/head";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }
  public static HttpResponse hot_search() {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/dapp/hot_search";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse dapp_list(Map<String, String> params) {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/dapp";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse dappBanner() {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/banner";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse dappId(Map<String, String> params) {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/dapp/id";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

public static HttpResponse search(Map<String, String> params) {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/dapp/search";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse history(Map<String, String> params) {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/dapp/history";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse allasset(String address) {
    try {
      String requestUrl = HttpNode + "/api/wallet/class/allasset";
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

  public static HttpResponse assetlist(String address) {
    try {
      String requestUrl = HttpNode + "/api/wallet/assetlist";
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

  public static HttpResponse lotteryData() throws Exception{
    final String requesturl = HttpNode + "/api/wallet/lottery/default_data";
    URIBuilder builder = new URIBuilder(requesturl);
    URI uri = builder.build();
    System.out.println(uri);
    response = createGetConnect(requesturl);
    return response;
  }

  public static HttpResponse hot_token(String address) {
    try {
      String requestUrl = HttpNode + "/api/wallet/hot_token";
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

  public static HttpResponse addasset(String json) {
    try {
      String requestUrl = HttpNode + "/api/wallet/addasset";
      System.out.println("requestUrl"+requestUrl);
      response = createPostConnect(requestUrl,json);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse addAsset(JSONObject address) throws Exception {
    final String requestUrl =HttpNode + "/api/wallet/addasset";
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

  public static HttpResponse getTransferTrx(Map<String, String> params) {
    try {
      String requestUrl = HttpTronDataNode + "/api/transfer/trx";
      response = createGetConnectNoHeader(requestUrl,params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getTransferToken10(Map<String, String> params) {
    try {
      String requestUrl = HttpTronDataNode + "/api/transfer/token10";
      response = createGetConnectNoHeader(requestUrl,params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getTransferTrc20(Map<String, String> params) {
    try {
      String requestUrl = HttpTronDataNode + "/api/transfer/trc20";
      response = createGetConnectNoHeader(requestUrl,params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getTrc20Holders(Map<String, String> params) {
    try {
      String requestUrl = HttpTronDataNode + "/api/trc20/holders";
      response = createGetConnectNoHeader(requestUrl,params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static Boolean verificationResult(HttpResponse response) {
    if (response.getStatusLine().getStatusCode() != 200) {
      return false;
    }
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    return true;
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
  public static HttpResponse createGetConnect(URI uri) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
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

  /**
   * constructor.
   */
  public static HttpResponse createGetConnectNoHeader(String url, Map<String, String> params) {
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
      Instant startTime = Instant.now();
      response = httpClient.execute(httpget);
      Instant endTime = Instant.now();
      requestTime = Duration.between(startTime, endTime).toMillis();
      System.out.println(url + " 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
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
      httpget.addHeader("Version","3.7.0");
      httpget.addHeader("DeviceID","1111111111");
      httpget.addHeader("chain","MainChain");
      httpget.addHeader("packageName","com.tronlinkpro.wallet");
      httpget.addHeader("System","Android");
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
      System.out.println("======");
      System.out.println(result);
      System.out.println("======");
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

  public static HttpResponse createGetConnect(URI uri,HashMap<String,String> header) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      httpGet = new HttpGet(uri);
      httpGet.setHeader("Content-type", "application/json; charset=utf-8");
      httpGet.setHeader("Connection", "Close");
      for (HashMap.Entry<String,String> entry : header.entrySet()){
        httpGet.setHeader(entry.getKey(),entry.getValue());
      }
      response = httpClient.execute(httpGet);
    } catch (Exception e) {
      e.printStackTrace();
      httpGet.releaseConnection();
      return null;
    }
    return response;
  }


  public static HttpResponse upgrade(HashMap<String,String> header) throws Exception{
    final String requestUrl = HttpNode + "/api/v1/wallet/upgrade";
    URIBuilder builder = new URIBuilder(requestUrl);
    if (header != null) {
      for (String key : header.keySet()) {
        builder.addParameter(key, header.get(key));
      }
    }
    URI uri = builder.build();
    response = createGetConnect(uri,header);
    Assert.assertTrue(TronlinkApiList.verificationResult(response));
    return response;
  }

}
