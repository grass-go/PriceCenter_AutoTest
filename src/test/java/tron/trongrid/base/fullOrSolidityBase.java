package tron.trongrid.base;

import com.alibaba.fastjson.JSONArray;
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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.utils.Configuration;

public class fullOrSolidityBase {

  public static String queryAddress = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddress");
  public static String winAddress = Configuration.getByPath("testng.conf").getString("tronGrid.winContractAddress");
  public static String queryAddressBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressBase64");
  public static String bttTokenId = Configuration.getByPath("testng.conf").getString("tronGrid.bttTokenId");
  public static Long feeLimit = Configuration.getByPath("testng.conf").getLong("tronGrid.feeLimit");
  public static String zeroBase64 = Configuration.getByPath("testng.conf").getString("tronGrid.zeroBase64");
  public static String usdjContractDSToken = Configuration.getByPath("testng.conf").getString("tronGrid.usdjContractDSToken");
  public static String usdjOriginAddress = Configuration.getByPath("testng.conf").getString("tronGrid.usdjOriginAddress");
  public static Integer bttVsTrxExchange = Configuration.getByPath("testng.conf").getInt("tronGrid.bttVsTrxExchange");
  public static Integer proposalId = Configuration.getByPath("testng.conf").getInt("tronGrid.proposalId");
  public static Integer maintenanceTimeInterval = Configuration.getByPath("testng.conf").getInt("tronGrid.maintenanceTimeInterval");
  public static String srAddress = Configuration.getByPath("testng.conf").getString("tronGrid.srAddress");
  public static String txid = Configuration.getByPath("testng.conf").getString("tronGrid.txid");
  public static Long txidBlockNum = Configuration.getByPath("testng.conf").getLong("tronGrid.txidBlockNum");
  public static String bttOwnerAddress = Configuration.getByPath("testng.conf").getString("tronGrid.bttOwnerAddress");
  public static String delegateResourceFromAddress = Configuration.getByPath("testng.conf").getString("tronGrid.delegateResourceFromAddress");
  public static String delegateResourceToAddress = Configuration.getByPath("testng.conf").getString("tronGrid.delegateResourceToAddress");
  public static String delegateResourceFromAddressSophia = Configuration.getByPath("testng.conf").getString("tronGrid.delegateResourceFromAddressSophia");
  public static String delegateResourceToAddressSophia = Configuration.getByPath("testng.conf").getString("tronGrid.delegateResourceToAddressSophia");
  public static String queryAddressSophia = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressSophia");

  public static String walletStr = "/wallet";
  public static String fullnode = "/wallet";
  public static String solidity = "/walletsolidity";
  public static JSONObject responseContent;
  public static HttpResponse response;
  //public static  String tronGridUrl = Configuration.getByPath("testng.conf").getString("tronGrid.tronGridUrl");
  public static  String tronGridUrl;
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
  @Parameters({"trongridUrl"})
  @BeforeTest()
  public void getMonitorUrl(String trongridUrl) {
    tronGridUrl = trongridUrl;
  }

  /**
   * compare jsonobject
   * @param first jsonobject
   * @param second jsonobject
   * @return true: if keys and values are same(order is not important), false: not same
   */
  public static boolean compareJsonObject(JSONObject first, JSONObject second) {
    if(first==null || second==null || (first.size() != second.size())){
      System.out.println("-------- json object is null or size not equal");
      return false;
    }

    for(String k:first.keySet()){
      if (!second.containsKey(k) || !(first.get(k).toString()).equals(second.get(k).toString())){
        if (!second.containsKey(k)){
          System.out.println("------- k: "+k+"  second does not contain ");
        }else {
          System.out.println("------- value not equal     first value"+first.get(k).toString()+"   second value : "
                  +second.get(k).toString());
        }
        return false;
      }
    }
    return true;
  }

  /**
   * compare jsonarray
   * @param firstArray  jsonarray
   * @param secondArray jsonarray
   * @return true: two jsonarray have same size, and jsonobjects are same(same keys and values,do not care key order ),
   */
  public static boolean compareJsonArray(JSONArray firstArray, JSONArray secondArray) {
    if(firstArray==null || secondArray==null || (firstArray.size() != secondArray.size()) ){
      System.out.println("-------json array is null or size not equal");
      return false;
    }
    for (int i=0;i<firstArray.size();i++){
      JSONObject jo = firstArray.getJSONObject(i);
      int j=0;
      boolean flag=false;
      for (j=0;j<secondArray.size();j++) {
        JSONObject jos = secondArray.getJSONObject(j);
        if (compareJsonObject(jo,jos)){
          flag = true;
          break;
        }
      }
      if (!flag){
        return false;
      }
    }
    return true;
  }

  /**
   * whether the jsonarray has the jsonobject(key order not care)
   * @param ja
   * @param jb
   * @return
   */
  public static boolean jsonarrayContainsJsonobject(JSONArray ja,JSONObject jb){
    for(int i=0;i<ja.size();i++){
      if(compareJsonObject(ja.getJSONObject(i),jb)){
        return true;
      }
    }
    return false;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccount(String queryAddress) {
    try {
      String requestUrl = tronGridUrl + fullnode + "/getaccount";
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
  public static HttpResponse getAccount(String queryAddress,Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getAccount(queryAddress);
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
  public static HttpResponse getNowBlock() {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/getnowblock";
      JsonObject userBaseObj2 = new JsonObject();
      //userBaseObj2.addProperty("visible", true);
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
  public static HttpResponse getNowBlock(Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getNowBlock();
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByNum(Long num) {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/getblockbynum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", num);
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
  public static HttpResponse getBlockByNum(Long num,Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getBlockByNum(num);
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockById(String id) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getblockbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", id);
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
  public static HttpResponse getBlockByLatestNum(Integer num) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getblockbylatestnum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", num);
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
  public static HttpResponse getBlockByLimitNext(Long startNum,Long endNum) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getblockbylimitnext";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("startNum", startNum);
      userBaseObj2.addProperty("endNum", endNum);
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
  public static HttpResponse getContract(String contracrt) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getcontract";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", contracrt);
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
  public static HttpResponse listExchanges() {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/listexchanges";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse getExchangeById(Integer exchangeId) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getexchangebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("id", exchangeId);
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
  public static HttpResponse getDelegateResource(String fromAddress,String toAddress) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getdelegatedresource";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("fromAddress", fromAddress);
      userBaseObj2.addProperty("toAddress", toAddress);
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
  public static HttpResponse getDelegateResourceIndex(String address) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getdelegatedresourceaccountindex";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", address);
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
  public static HttpResponse listNodes() {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/listnodes";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse getNodeInfo() {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getnodeinfo";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse listProposals() {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/listproposals";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse getProposalById(Integer proposalId) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getproposalbyid";
      JsonObject userBaseObj2 = new JsonObject();
      //userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("id", proposalId);
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
  public static HttpResponse getChainParameters() {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getchainparameters";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse listWitnesses() {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/listwitnesses";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse listWitnesses(Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }

    return listWitnesses();
  }

  /**
   * constructor.
   */
  public static HttpResponse getNextMaintenanceTime() {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getnextmaintenancetime";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse getBrokerage(String srAddress) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("address", srAddress);
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
  public static HttpResponse getReward(String srAddress) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getReward";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("address", srAddress);
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
  public static HttpResponse getTransactionById(String txid) {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/gettransactionbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("value", txid);
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
  public static HttpResponse getTransactionById(String txid,Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getTransactionById(txid);
  }



  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoById(String txid) {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/gettransactioninfobyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("value", txid);
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
  public static HttpResponse getTransactionInfoById(String txid,Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getTransactionInfoById(txid);
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoByBlockNum(Long blockNum) {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/gettransactioninfobyblocknum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("num", blockNum);
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
  public static HttpResponse getTransactionInfoByBlockNum(Long blockNum,Boolean isSolidity) {
    if (isSolidity) {
      fullnode =solidity;
    }else {
      fullnode = walletStr;
    }
    return getTransactionInfoByBlockNum(blockNum);
  }


  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueById(Integer assetId) {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/getassetissuebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("value", assetId);
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
  public static HttpResponse getAssetIssueById(Integer assetId,Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getAssetIssueById(assetId);
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueByAccount(String accountAddress) {
    try {
      final String requestUrl = tronGridUrl  + "/wallet/getassetissuebyaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("address", accountAddress);
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
  public static HttpResponse getAssetIssueList() {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/getassetissuelist";
      JsonObject userBaseObj2 = new JsonObject();
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
  public static HttpResponse getAssetIssueList(Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getAssetIssueList();
  }


  /**
   * constructor.
   */
  public static HttpResponse getPaginatedAssetIssueList(Integer offset, Integer limit) {
    try {
      final String requestUrl = tronGridUrl  + fullnode + "/getpaginatedassetissuelist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", true);
      userBaseObj2.addProperty("offset", offset);
      userBaseObj2.addProperty("limit", limit);
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
  public static HttpResponse getPaginatedAssetIssueList(Integer offset, Integer limit,Boolean isSolidity) {
    if (isSolidity) {
      fullnode = solidity;
    }else {
      fullnode = walletStr;
    }
    return getPaginatedAssetIssueList(offset,limit);
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
  public static JSONArray parseResponseContentToArray(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      JSONArray obj = JSONObject.parseArray(result);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static int getSubStringCount(String orgine, String find) {
    int count=0;
    for (int i =0;i+5<orgine.length();i++){
      if(orgine.substring(i,i+5).equals(find)){
        count++;
      }
    }
    return count;
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
