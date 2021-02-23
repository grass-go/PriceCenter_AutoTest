package tron.trongrid.base;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.utils.Configuration;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public class V1Base {

  public static String queryAddressSophia = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressSophia");
  public static String queryAddressSophiaBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressSophiaBase64");
  public static String queryAddress = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddress");
  public static String eventTxid = Configuration.getByPath("testng.conf").getString("tronGrid.eventTxid");
  public static String queryAddressBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressBase64");
  public static String bttTokenId = Configuration.getByPath("testng.conf").getString("tronGrid.bttTokenId");
  public static Long feeLimit = Configuration.getByPath("testng.conf").getLong("tronGrid.feeLimit");
  public static String zeroBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.zeroBase64");
  public static String usdjContractDSToken = Configuration.getByPath("testng.conf").getString("tronGrid.usdjContractDSToken");
  public static String usdjContract = Configuration.getByPath("testng.conf").getString("tronGrid.usdjContract");
  public static String usdjContractBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.usdjContractBase64");
  public static Long eventBlockNumber = Configuration.getByPath("testng.conf").getLong("tronGrid.eventBlockNumber");
  public static String usdjOriginAddress = Configuration.getByPath("testng.conf").getString("tronGrid.usdjOriginAddress");
  public static Integer bttVsTrxExchange = Configuration.getByPath("testng.conf").getInt("tronGrid.bttVsTrxExchange");
  public static Integer proposalId = Configuration.getByPath("testng.conf").getInt("tronGrid.proposalId");
  public static Integer maintenanceTimeInterval = Configuration.getByPath("testng.conf").getInt("tronGrid.maintenanceTimeInterval");
  public static String srAddress = Configuration.getByPath("testng.conf").getString("tronGrid.srAddress");
  public static String txid = Configuration.getByPath("testng.conf").getString("tronGrid.txid");
  public static Long txidBlockNum = Configuration.getByPath("testng.conf").getLong("tronGrid.txidBlockNum");
  public static String bttOwnerAddress = Configuration.getByPath("testng.conf").getString("tronGrid.bttOwnerAddress");
  public static String bttOwnerAddressBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.bttOwnerAddressBase64");
  public static String delegateResourceFromAddress = Configuration.getByPath("testng.conf").getString("tronGrid.delegateResourceFromAddress");
  public static String delegateResourceToAddress = Configuration.getByPath("testng.conf").getString("tronGrid.delegateResourceToAddress");
  public static String queryAddressBase64With41Start = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressBase64With41Start");
  public static JSONObject responseContent;
  public static HttpResponse response;
  public static  String tronGridUrl = Configuration.getByPath("testng.conf").getString("tronGrid.tronGridUrl");
  static HttpClient httpClient;
  static HttpGet httpget;
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
  @Parameters({"trongridUrl"})
  @BeforeTest()
  public void getMonitorUrl(String trongridUrl) {
    tronGridUrl = trongridUrl;
  }

  /**
   * constructor.
   */
  public static JSONObject getAccountInfoByAddress(String queryAddress,Boolean is_only_confirmed) {
    try {
      String requestUrl = tronGridUrl + "v1/accounts/" + queryAddress;
      JsonObject userBaseObj2 = new JsonObject();
      //userBaseObj2.addProperty("address", queryAddress);
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject getAccountInfoByAddress(String queryAddress) {
    return getAccountInfoByAddress(queryAddress,true);
  }


  /**
   * constructor.
   */
  public static JSONObject getTrc20TokenAccountBalanceInfo(String contractAddress,
      Boolean is_only_confirmed,String orderBy,Integer limit) {
    try {
      String requestUrl = tronGridUrl + "v1/contracts/" + contractAddress + "/tokens" ;
      JsonObject userBaseObj2 = new JsonObject();
      if (!orderBy.isEmpty()) {
        userBaseObj2.addProperty("order_by",orderBy);
      }
      if (limit != 20) {
        userBaseObj2.addProperty("limit",limit);
      }
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject getTrc20TokenAccountBalanceInfo(String contractAddress) {
    return getTrc20TokenAccountBalanceInfo(contractAddress,true,"",20);
  }





  /**
   * constructor.
   */
  public static JSONObject getTransactionInfoByAddress(String queryAddress) {
    return getTransactionInfoByAddress(queryAddress,true,false,"");
  }

  /**
   * constructor.
   */
  public static JSONObject getTransactionInfoByAddress(String queryAddress,Boolean is_only_confirmed,Boolean only_vote_tx,String token_id) {
    try {
      String requestUrl = tronGridUrl + "v1/accounts/" + queryAddress + "/transactions";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      userBaseObj2.addProperty("vote_tx",only_vote_tx);
      userBaseObj2.addProperty("token_id",token_id);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject getEventByTransactionId(String txid) {
    return getEventByTransactionId(txid,true);
  }

  /**
   * constructor.
   */
  public static JSONObject getEventByTransactionId(String txid,Boolean is_only_confirmed) {
    try {
      String requestUrl = tronGridUrl + "v1/transactions/" + txid + "/events";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }



  /**
   * constructor.
   */
  public static JSONObject getEventByLatestBlockNumber() {
    return getEventByLatestBlockNumber(true);
  }

  /**
   * constructor.
   */
  public static JSONObject getEventByLatestBlockNumber(Boolean is_only_confirmed) {
    try {
      String requestUrl = tronGridUrl + "v1/blocks/latest/events";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }



  /**
   * constructor.
   */
  public static JSONObject getEventByContractAddress(String contractAddress) {
    return getEventByContractAddress(contractAddress,true,"",0L,0L,0L,"");
  }

  /**
   * constructor.
   */
  public static JSONObject getEventByContractAddress(String contractAddress,Boolean is_only_confirmed,
      String eventName, Long blockNumber,Long minBlockTimestamp,Long maxBlockTimestamp,
      String orderBy) {
    try {
      String requestUrl = tronGridUrl + "v1/contracts/" + contractAddress + "/events";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      if (!eventName.isEmpty()){
        userBaseObj2.addProperty("event_name",eventName);
      }
      if (blockNumber != 0) {
        userBaseObj2.addProperty("block_number",blockNumber);
      }
      if (minBlockTimestamp != 0 && maxBlockTimestamp != 0) {
        userBaseObj2.addProperty("min_block_timestamp",minBlockTimestamp);
        userBaseObj2.addProperty("max_block_timestamp",maxBlockTimestamp);
      }
      if (!orderBy.isEmpty()) {
        userBaseObj2.addProperty("order_by",orderBy);
      }
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject getEventByContractAddressWithKey(String contractAddress) {
    return getEventByContractAddressWithKey(contractAddress,true,"",0L,0L,0L,"");
  }

  /**
   * constructor.
   */
  public static JSONObject getEventByContractAddressWithKey(String contractAddress,Boolean is_only_confirmed,
                                                     String eventName, Long blockNumber,Long minBlockTimestamp,Long maxBlockTimestamp,
                                                     String orderBy) {
    try {
      String requestUrl = tronGridUrl + "v1/contracts/" + contractAddress + "/events";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      if (!eventName.isEmpty()){
        userBaseObj2.addProperty("event_name",eventName);
      }
      if (blockNumber != 0) {
        userBaseObj2.addProperty("block_number",blockNumber);
      }
      if (minBlockTimestamp != 0 && maxBlockTimestamp != 0) {
        userBaseObj2.addProperty("min_block_timestamp",minBlockTimestamp);
        userBaseObj2.addProperty("max_block_timestamp",maxBlockTimestamp);
      }
      if (!orderBy.isEmpty()) {
        userBaseObj2.addProperty("order_by",orderBy);
      }
      response = createConnectWithKey(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject getTransactionInformationByContractAddress(String contractAddress) {
    return getTransactionInformationByContractAddress(contractAddress,true,"",20,true,0L,0L);
  }

  /**
   * constructor.
   */
  public static JSONObject getTransactionInformationByContractAddress(String contractAddress,
      Boolean is_only_confirmed,String orderBy,Integer limit,Boolean searchInternal,
      Long minBlockTimestamp,Long maxBlockTimestamp) {
    try {
      String requestUrl = tronGridUrl + "v1/contracts/" + contractAddress + "/transactions";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      if (!orderBy.isEmpty()) {
        userBaseObj2.addProperty("order_by", orderBy);
      }
      if (limit != 20) {
        userBaseObj2.addProperty("limit",limit);
      }
      if (!searchInternal) {
        userBaseObj2.addProperty("search_internal",searchInternal);
      }
      if (minBlockTimestamp != 0 && maxBlockTimestamp != 0) {
        userBaseObj2.addProperty("min_block_timestamp",minBlockTimestamp);
        userBaseObj2.addProperty("max_block_timestamp",maxBlockTimestamp);
      }
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }



  /**
   * constructor.
   */
  public static JSONObject getEventByBlockNumber(String blockNumber) {
    return getEventByBlockNumber(blockNumber,true);
  }

  /**
   * constructor.
   */
  public static JSONObject getEventByBlockNumber(String blockNumber,Boolean is_only_confirmed) {
    try {
      String requestUrl = tronGridUrl + "v1/blocks/" + blockNumber + "/events";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }




  /**
   * constructor.
   */
  public static JSONObject getTrc20TransactionInfoByAddress(String queryAddress) {
    return getTrc20TransactionInfoByAddress(queryAddress,true,false);
  }

  /**
   * constructor.
   */
  public static JSONObject getTrc20TransactionInfoByAddress(String queryAddress,Boolean is_only_confirmed,Boolean get_detail) {
    try {
      String requestUrl = tronGridUrl + "v1/accounts/" + queryAddress + "/transactions/trc20";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      userBaseObj2.addProperty("get_detail",get_detail);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }



  /**
   * constructor.
   */
  public static JSONObject getAssetList(String order_by,Integer limit,String fingerprint) {
    try {
      String requestUrl = tronGridUrl + "v1/assets";
      JsonObject userBaseObj2 = new JsonObject();
      if (!order_by.isEmpty()) {
        userBaseObj2.addProperty("order_by", order_by);
      }
      if (limit != 20) {
        userBaseObj2.addProperty("limit", limit);
      }
      if (!fingerprint.isEmpty()) {
        userBaseObj2.addProperty("fingerprint", fingerprint);
      }
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject getAssetByName(String name) {
    return getAssetByName(name,20,"","",true);
  }

  /**
   * constructor.
   */
  public static JSONObject getAssetByName(String name, Integer limit, String fingerprint,String order_by,Boolean is_only_confirmed) {
    try {
      String requestUrl = tronGridUrl + "v1/assets/" + name + "/list";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      if (!order_by.isEmpty()) {
        userBaseObj2.addProperty("order_by", order_by);
      }
      if (limit != 20) {
        userBaseObj2.addProperty("limit", limit);
      }
      if (!fingerprint.isEmpty()) {
        userBaseObj2.addProperty("fingerprint", fingerprint);
      }
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject getAssetList() {
    return getAssetList("",20,"");
  }



  /**
   * constructor.
   */
  public static JSONObject getAssetByIdentifier(String identifier) {
    return getAssetByIdentifier(identifier,true);
  }

  /**
   * constructor.
   */
  public static JSONObject getAssetByIdentifier(String identifier,Boolean is_only_confirmed) {
    try {
      String requestUrl = tronGridUrl + "v1/assets/" + identifier;
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("only_confirmed",is_only_confirmed);
      response = createConnect(requestUrl, userBaseObj2);
      Assert.assertEquals(200,response.getStatusLine().getStatusCode());
      return convertStringToJSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
  }







  /**
   * constructor.
   */
  public static HttpResponse createConnect(String url, JsonObject requestBody) {
    try {
      httpClient.getParams()
          .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      httpget = new HttpGet(url);

      URIBuilder uriBuilder = new URIBuilder(url);
      uriBuilder.setCharset(Charset.forName("UTF-8"));
      if (requestBody != null) {
        Iterator iter = requestBody.entrySet().iterator();
        while (iter.hasNext()) {
          Map.Entry entry = (Map.Entry) iter.next();
          uriBuilder.setParameter(entry.getKey().toString(), entry.getValue().toString().replaceAll(
              "\"",""
          ));
        }
      }
      URI uri = uriBuilder.build();
      httpget.setURI(uri);
      System.out.println("---------- " + httpget.getURI() + " --------------");
      response = httpClient.execute(httpget);
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
  public static HttpResponse createConnectWithKey(String url, JsonObject requestBody) {
    try {
      httpClient.getParams()
              .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      httpget = new HttpGet(url);

      URIBuilder uriBuilder = new URIBuilder(url);
      uriBuilder.setCharset(Charset.forName("UTF-8"));
      if (requestBody != null) {
        Iterator iter = requestBody.entrySet().iterator();
        while (iter.hasNext()) {
          Map.Entry entry = (Map.Entry) iter.next();
          uriBuilder.setParameter(entry.getKey().toString(), entry.getValue().toString().replaceAll(
                  "\"",""
          ));
        }
      }
      URI uri = uriBuilder.build();
      httpget.setURI(uri);
      httpget.addHeader("TRON-PRO-API-KEY", "50446bd3-7b0e-40bb-85cf-34617bbe1bec");
      httpget.addHeader("User-Agent","a8iiu");
      httpget.addHeader("Origin","https://6ty.1trog.io");
      httpget.addHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjQ1OTI0OTdj" +
              "YjcxYTRhYjk4NDVhYzViZjA2ODFjYjYyIn0.eyJhdWQiOiJ0cm9uZ3JpZC5pbyJ9.MqyQcu5HtOcIeWSJ7Cez_x15Q26H2WlKEs" +
              "uKXccrWKV2QAz9N-3Ze9T2VsmPZTpojuf-RvG4MvO-RsF4QhGPsN25ZljRA4xH3I9Tlh7m_m9be70ZFG4IJzoNCIstEIlyCKGxOSW5" +
              "2u52rL5I63zf7GT_N2AIR4DQOsANP4J0TJsQfPgVNqDaNxpoOx01QihPlGiVctzsM-3r8z0Lx1ZrwHPr499fc75tg2gTXFg7wvCrUl-" +
              "vbzCND-W3zBT3KngqBTgoaNpFJGCSro1FczifeOQQs-cwMS3NoMiy8WKB7rfHZzFJ3t1JD7arXezoimFLbjKYx1HE3OtaUwccPZSkew");
      System.out.println("---------- " + httpget.getURI() + " --------------");
      response = httpClient.execute(httpget);
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
  public static JSONObject convertStringToJSONObject(String responseString) {
    try {
      JSONObject obj = JSONObject.parseObject(responseString);
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
    httpget.releaseConnection();
  }





}
