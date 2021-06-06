package tron.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.spongycastle.util.encoders.Hex;
import org.tron.api.GrpcAPI;
import org.tron.api.WalletGrpc;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Hash;
import org.tron.common.parameter.CommonParameter;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Sha256Hash;
import org.tron.core.Wallet;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;

import org.tron.protos.contract.SmartContractOuterClass;
import tron.common.utils.Configuration;
import tron.trondata.base.TrondataBase;
import tron.tronlink.base.TronlinkBase;

import java.math.BigInteger;
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
  static byte ADD_PRE_FIX_BYTE_MAINNET = (byte) 0x41;

  static {
    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
    pccm.setDefaultMaxPerRoute(80);
    pccm.setMaxTotal(100);

    httpClient = new DefaultHttpClient(pccm);
  }

  public static HttpResponse classify() {
    try {
      String requestUrl = HttpNode + "/api/dapp/v2/classify";
      log.info(requestUrl);
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
      log.info(requestUrl);
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
    response = createGetConnect(uri);
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
    response = createGetConnect(uri);
    return response;
  }

  public static HttpResponse getInterChainEvent(HashMap<String, String> param) throws Exception{
    final String requestUrl = HttpNode + "/api/interchain-event";
    URIBuilder builder = new URIBuilder(requestUrl);
    if (param != null) {
      for (String key : param.keySet()) {
        builder.addParameter(key, param.get(key));
      }
    }
    URI uri = builder.build();
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
    response = createGetConnect(uri);
    return response;
  }

  public static HttpResponse votingV2Witness(Map<String, String> params) throws Exception{
    String requestUrl = HttpNode + "/api/voting/v2/witness";

    response = createGetConnect(requestUrl, params);
    return response;
  }

  public static boolean getAllWitnessFromTronscan() {
//    String requestUrl = "https://apilist.tronscan.org/api/vote/witness";
    String requestUrl = TronlinkBase.tronscanApiUrl+"/api/vote/witness";
    response = createGetConnect(requestUrl);
//    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    if ((response.getStatusLine().getStatusCode() != 200)
            || responseContent == null
            || !(responseContent.containsKey("total"))
            || !(responseContent.getLongValue("total") > 0)
            || !(responseContent.containsKey("totalVotes"))
            || !(responseContent.getLongValue("totalVotes") > 0)
            || !(responseContent.containsKey("data"))
            || !(responseContent.getJSONArray("data").size() > 0)) {
      return false;
    }
    return true;
  }

  public static boolean getVoteSelfFromTronscan(Map<String,String> params) {
    String requestUrl = TronlinkBase.tronscanApiUrl+"/api/vote";
    response = createGetConnect(requestUrl,params);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    if ((response.getStatusLine().getStatusCode() != 200)
            || responseContent == null
            || !(responseContent.containsKey("total"))
            || !(responseContent.getLongValue("total") > 0)
            || !(responseContent.containsKey("totalVotes"))
            || !(responseContent.getLongValue("totalVotes") > 0)
            || !(responseContent.containsKey("data"))) {
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
      log.info(requestUrl);
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
      log.info(requestUrl);
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
      log.info(requestUrl);
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
      log.info(requestUrl);
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
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse accountList(JSONArray body) {
    try {
      String requestUrl = HttpNode + "/api/wallet/account/list";
      response = createPostConnect(requestUrl,body);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse allasset(String address) {
    try {
      String requestUrl = HttpNode + "/api/wallet/class/allasset";
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
    response = createGetConnect(requesturl);
    return response;
  }

  public static HttpResponse hot_token(String address) {
    try {
      String requestUrl = HttpNode + "/api/wallet/hot_token";
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
      response = createPostConnect(requestUrl,json);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse ieo() {
    final String requestUrl =HttpNode + "/api/wallet/ieo";
    response = createGetConnect(requestUrl, null);
    return response;
  }

  public static HttpResponse getNodeInfo(JSONArray body) {
    final String requestUrl =HttpNode + "/api/wallet/node_info";
    response = createPostConnect(requestUrl,body);
    return response;
  }

  public static HttpResponse getConfig() {
    final String requestUrl =HttpNode + "/api/wallet/get_config";
    response = createGetConnect(requestUrl,null);
    return response;
  }

  public static HttpResponse dappToMainFee() {
    final String requestUrl =HttpNode + "/api/transfer/dappToMainFee";
    response = createGetConnect(requestUrl,null);
    return response;
  }

  public static HttpResponse addAsset(JSONObject address) throws Exception {
    final String requestUrl =HttpNode + "/api/wallet/addasset";
    response = createConnect(requestUrl, address);
    return response;
  }

  public static HttpResponse getAirdropTransaction(Map<String, String> params) {
    final String requestUrl =HttpNode + "/api/wallet/airdrop_transaction";
    response = createGetConnect(requestUrl, params);
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

  public static HttpResponse multiTransaction(JSONObject body) {
    try {
      String requestUrl = HttpNode + "/api/wallet/multi/transaction";
      response = createConnect(requestUrl,body);
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

  public static HttpResponse failTransfer(JSONObject body) {
    final String requestUrl =HttpNode + "/api/wallet/fail_transfer";
    response = createConnect(requestUrl, body);
    return response;
  }

  public static HttpResponse createPostConnect(String url, String requestBody) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Keep-Alive");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
      log.info("url: " + httppost.toString() + "\n params:"+requestBody);
      response = httpClient.execute(httppost);
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

  public static HttpResponse nilexGetAssetlist(String address,Map<String,String> header) {
    try {
      String requestUrl = "https://niletest.tronlink.org/api/wallet/assetlist";
      JSONObject body = new JSONObject();
      body.put("address", address);
      header.put("Content-type", "application/json; charset=utf-8");
      header.put("Connection", "Close");
      response = createPostConnectWithHeader(requestUrl,null,body,header);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse V2AllAssetList(Map<String, String> params) {
    try {
      String requestUrl = HttpNode +"/api/wallet/v2/allAssetList";
      response = v2CreateGetConnect(requestUrl,params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse v2AssetList(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/v2/assetList";
    log.info("requestUrl : " + requestUrl);
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2AssetList(Map<String, String> params,JSONObject body ) {
    final String requestUrl = HttpNode + "/api/wallet/v2/assetList";
    log.info("requestUrl : " + requestUrl);
    response = createPostConnectWithHeader(requestUrl, params, body, getV2Header());
    return response;
  }

  public static HttpResponse v2GetAllCollection(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/nft/getAllCollection";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2AllCollections(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/nft/allCollections";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2GetCollectionList(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/nft/getCollectionList";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2GetCollectionInfo(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/nft/getCollectionInfo";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2GetCollectionInfos(Map<String, String> params, JSONObject body) {
    final String requestUrl = HttpNode + "/api/wallet/nft/getCollectionInfos";
    response = createPostConnectWithHeader(requestUrl, params, body, getV2Header());
    return response;
  }

  public static HttpResponse v2NewAssetList(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/v2/newAssetList";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2SearchAsset(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/v2/search";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2Asset(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/v2/asset";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2AddAsset(Map<String, String> params,JSONObject object) {
    String requestUrl = HttpNode + "/api/wallet/v2/addAsset";
    Map<String, String> header = getV2Header();
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Close");
    response = createPostConnectWithHeader(requestUrl,params, object,header);
    return response;
  }


  public static Map<String, String> getV2Header(){
    Map<String, String> header = new HashMap<>();
    header.put("Lang","1");
    header.put("Version","v1.0.0");
    header.put("DeviceID","1:1:1:1");
    header.put("chain","MainChain");
    header.put("channel","official");
    header.put("ts", "1609302220000");
    header.put("packageName","com.tronlinkpro.wallet");
    header.put("System","AndroidTest");
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Keep-Alive");
    return header;
  }

  public static HttpResponse v2CreateGetConnect(String url, Map<String, String> params) {
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
      log.info(url);
      httpGet = new HttpGet(url);
      Map<String, String> header = getV2Header();
      if(header != null){
        for(String key: header.keySet()){
          httpGet.addHeader(key,header.get(key));
        }
      }
      Header[] allHeaders = httpGet.getAllHeaders();
      for (int i = 0; i < allHeaders.length; i++) {
        log.info(""+allHeaders[i]);
      }
      response = httpClient.execute(httpGet);
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
  public static HttpResponse createPostConnect(String url, JsonObject requestBody) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Keep-Alive");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      log.info("url: "+httppost.toString() + "\n params: "+requestBody.toString());
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
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
  public static HttpResponse createPostConnectWithHeader(String url, Map<String, String> params,
                                                         JSONObject requestBody,Map<String,String> header) {
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
//      httppost.setHeader("Content-type", "application/json; charset=utf-8");
//      httppost.setHeader("Connection", "Close");
      if(header != null){
        for(String key: header.keySet()){
          httppost.setHeader(key,header.get(key));
          log.info(key+": "+header.get(key));
        }
      }
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
      log.info("url: "+httppost.toString()+"\nparams: "+requestBody.toString());
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
  public static HttpResponse createGetConnectWithHeader(String url, Map<String, String> params,
      JSONObject requestBody,Map<String,String> header) {
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
      if(header != null){
        for(String key: header.keySet()){
          httpget.setHeader(key,header.get(key));
          log.info(key+": "+header.get(key));
        }
      }
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        log.info("url: "+httpget.toString()+"\nparams: "+requestBody.toString());
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

  /**
   * constructor.
   */
  public static HttpResponse createPostConnect(String url, JSONArray requestBody) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
              connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      httppost.addHeader("Lang","1");
      httppost.addHeader("Version","3.7.0");
      httppost.addHeader("DeviceID","1111111111");
      httppost.addHeader("chain","MainChain");
      httppost.addHeader("packageName","com.tronlinkpro.wallet");
      httppost.addHeader("System","Android");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toJSONString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      response = httpClient.execute(httppost);
      log.info("url: "+httppost.toString()+"\nparams: "+requestBody.toString());
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
      httpget.setHeader("Connection", "Keep-Alive");
      log.info("---url: "+url);
      Instant startTime = Instant.now();
      response = httpClient.execute(httpget);
      Instant endTime = Instant.now();
      requestTime = Duration.between(startTime, endTime).toMillis();
      log.info(" 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
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
      Header[] allHeaders = httpget.getAllHeaders();
      for (int i = 0; i < allHeaders.length; i++) {
        log.info(""+allHeaders[i]);
      }
      Instant startTime = Instant.now();
      response = httpClient.execute(httpget);
      Instant endTime = Instant.now();
      requestTime = Duration.between(startTime, endTime).toMillis();
      log.info(url + " 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
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
      log.info(result);
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

  public static String parseResponse2String(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static JSONObject parseJsonObResponseContent(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      log.info("======");
      log.info(result);
      log.info("======");
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
      httppost.setHeader("Connection", "keep-alive");
      httppost.addHeader("Lang","1");
      httppost.addHeader("Version","3.7.0");
      httppost.addHeader("DeviceID","1111111111");
      httppost.addHeader("chain","MainChain");
      httppost.addHeader("packageName","com.tronlinkpro.wallet");
      httppost.addHeader("System","Android");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      log.info("url:"+httppost.toString());
      if(requestBody != null){
        log.info("params: "+requestBody.toJSONString());
      }
      Header[] allHeaders = httppost.getAllHeaders();
      for (int i = 0; i < allHeaders.length; i++) {
        log.info(""+allHeaders[i]);
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
      log.info("url: " + uri.toString());
      httpGet = new HttpGet(uri);
      httpGet.setHeader("Content-type", "application/json; charset=utf-8");
      httpGet.setHeader("Connection", "Close");
      for (HashMap.Entry<String,String> entry : header.entrySet()){
        httpGet.setHeader(entry.getKey(),entry.getValue());
        log.info(entry.getKey() + ":" + entry.getValue());
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

  /**
   * constructor.
   */
  public static Protocol.Transaction sendcoin(byte[] to, long amount, byte[] owner,
                                               WalletGrpc.WalletBlockingStub blockingStubFull) {
    Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
    BalanceContract.TransferContract.Builder builder = BalanceContract.TransferContract.newBuilder();
    ByteString bsTo = ByteString.copyFrom(to);
    ByteString bsOwner = ByteString.copyFrom(owner);
    builder.setToAddress(bsTo);
    builder.setOwnerAddress(bsOwner);
    builder.setAmount(amount);

    BalanceContract.TransferContract contract = builder.build();
    Protocol.Transaction transaction = blockingStubFull.createTransaction(contract);
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      log.info("transaction ==null");
      return null;
    }
    return transaction;

  }
  /**
   * constructor.
   */

  public static byte[] getFinalAddress(String priKey) {
    ECKey temKey = null;
    try {
      BigInteger priK = new BigInteger(priKey, 16);
      temKey = ECKey.fromPrivate(priK);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return temKey.getAddress();
  }

  public static Protocol.Transaction addTransactionSignWithPermissionId(Protocol.Transaction transaction,
                                                                        String priKey, int permissionId, WalletGrpc.WalletBlockingStub blockingStubFull) {
    Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
    ECKey temKey = null;
    try {
      BigInteger priK = new BigInteger(priKey, 16);
      temKey = ECKey.fromPrivate(priK);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //transaction = setPermissionId(transaction, permissionId);
    Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder();
    Protocol.Transaction.Contract.Builder contract = raw.getContract(0).toBuilder()
            .setPermissionId(permissionId);
    raw.clearContract();
    raw.addContract(contract);
    transaction = transaction.toBuilder().setRawData(raw).build();

    Protocol.Transaction.Builder transactionBuilderSigned = transaction.toBuilder();
    byte[] hash = Sha256Hash.hash(CommonParameter.getInstance()
            .isECKeyCryptoEngine(), transaction.getRawData().toByteArray());
    ECKey ecKey = temKey;
    ECKey.ECDSASignature signature = ecKey.sign(hash);
    ByteString bsSign = ByteString.copyFrom(signature.toByteArray());
    transactionBuilderSigned.addSignature(bsSign);
    transaction = transactionBuilderSigned.build();
    return transaction;
  }

  public static Protocol.Transaction triggerContract(byte[] contractAddress, String method,
                                                     String argsStr, Boolean isHex, long callValue, long feeLimit, String tokenId, long tokenValue,
                                                     byte[] ownerAddress, WalletGrpc.WalletBlockingStub blockingStubFull) {
    Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
    if (argsStr.equalsIgnoreCase("#")) {
      log.info("argsstr is #");
      argsStr = "";
    }

    byte[] owner = ownerAddress;
    byte[] input = Hex.decode(parseMethod(method, argsStr, isHex));

    SmartContractOuterClass.TriggerSmartContract.Builder builder = SmartContractOuterClass.TriggerSmartContract.newBuilder();
    builder.setOwnerAddress(ByteString.copyFrom(owner));
    builder.setContractAddress(ByteString.copyFrom(contractAddress));
    builder.setData(ByteString.copyFrom(input));
    builder.setCallValue(callValue);
    builder.setTokenId(Long.parseLong(tokenId));
    builder.setCallTokenValue(tokenValue);
    SmartContractOuterClass.TriggerSmartContract triggerContract = builder.build();

    GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.triggerContract(triggerContract);
    if (transactionExtention == null || !transactionExtention.getResult().getResult()) {
      System.out.println("RPC create call trx failed!");
      System.out.println("Code = " + transactionExtention.getResult().getCode());
      System.out
              .println("Message = " + transactionExtention.getResult().getMessage().toStringUtf8());
      return null;
    }
    Protocol.Transaction transaction = transactionExtention.getTransaction();
    if (transaction.getRetCount() != 0 && transactionExtention.getConstantResult(0) != null
            && transactionExtention.getResult() != null) {
      byte[] result = transactionExtention.getConstantResult(0).toByteArray();
      System.out.println("message:" + transaction.getRet(0).getRet());
      System.out.println(
              ":" + ByteArray.toStr(transactionExtention.getResult().getMessage().toByteArray()));
      System.out.println("Result:" + Hex.toHexString(result));
      return null;
    }

    final GrpcAPI.TransactionExtention.Builder texBuilder = GrpcAPI.TransactionExtention.newBuilder();
    Protocol.Transaction.Builder transBuilder = Protocol.Transaction.newBuilder();
    Protocol.Transaction.raw.Builder rawBuilder = transactionExtention.getTransaction().getRawData()
            .toBuilder();
    rawBuilder.setFeeLimit(feeLimit);
    transBuilder.setRawData(rawBuilder);
    for (int i = 0; i < transactionExtention.getTransaction().getSignatureCount(); i++) {
      ByteString s = transactionExtention.getTransaction().getSignature(i);
      transBuilder.setSignature(i, s);
    }
    for (int i = 0; i < transactionExtention.getTransaction().getRetCount(); i++) {
      Protocol.Transaction.Result r = transactionExtention.getTransaction().getRet(i);
      transBuilder.setRet(i, r);
    }
    texBuilder.setTransaction(transBuilder);
    texBuilder.setResult(transactionExtention.getResult());
    texBuilder.setTxid(transactionExtention.getTxid());
    transactionExtention = texBuilder.build();
    if (transactionExtention == null) {
      return null;
    }
    GrpcAPI.Return ret = transactionExtention.getResult();
    if (!ret.getResult()) {
      System.out.println("Code = " + ret.getCode());
      System.out.println("Message = " + ret.getMessage().toStringUtf8());
      return null;
    }
    transaction = transactionExtention.getTransaction();
    return transaction;
  }

  //isHex must be true
  public static String parseMethod(String methodSign, String input, boolean isHex) {
    return parseSelector(methodSign) + input;
  }

  public static String parseSelector(String methodSign) {
    byte[] selector = new byte[4];
    System.arraycopy(Hash.sha3(methodSign.getBytes()), 0, selector, 0, 4);
    return Hex.toHexString(selector);
  }


}
