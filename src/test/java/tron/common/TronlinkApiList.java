package tron.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.tron.protos.contract.AccountContract;
import org.tron.protos.contract.BalanceContract;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

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

import static org.tron.common.crypto.Hash.sha3omit12;

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
      //String requestUrl = "HttpNode" + "/api/wallet/multi/transaction";
      //String requestUrl = "http://123.56.3.74" + "/api/wallet/multi/transaction";
      //String requestUrl = "http://101.201.66.150:8885" + "/api/wallet/multi/transaction";
      String requestUrl = "https://niletest.tronlink.org" + "/api/wallet/multi/transaction";
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

  public static HttpResponse getDelegatedResource(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/v2/getDelegatedResource";
    response = v2CreateGetConnect(requestUrl, params);
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

  public static HttpResponse v2GetNoticeRemind(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/v1/wallet/getNoticeRemind";
    response = v2CreateGetConnect(requestUrl, params);
    return response;
  }

  public static HttpResponse v2GetDappHistory(JSONObject params) {
    final String requestUrl = HttpNode + "/api/activity/add";
    response = createPostConnectWithHeader(requestUrl, null,params,null);
    return response;
  }

  public static HttpResponse v2GetAnnouncement() {
    final String requestUrl = HttpNode + "/api/activity/announcement/reveal_v2";
    response = v2CreateGetConnect(requestUrl, null);
    return response;
  }


  public static HttpResponse v2GetNodes(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/v1/wallet/nodes";
    response = createConnect(requestUrl,null);
    return response;
  }

  public static HttpResponse v2GetBlacklist(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/activity/website/blacklist";
    response = v2CreateGetConnect(requestUrl,null);
    return response;
  }

  public static HttpResponse officialToken(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/wallet/official_token";
    response = v2CreateGetConnect(requestUrl,null);
    return response;
  }

  public static HttpResponse v2PlayScreenInfo(Map<String, String> params) {
    final String requestUrl = HttpNode + "/api/activity/play_screen/info";
    response = v2CreateGetConnect(requestUrl,null);
    return response;
  }

  public static HttpResponse v2PlayScreenDeal(String playId,HashMap<String,String> header) {
    final String requestUrl = HttpNode + "/api/activity/play_screen/deal";
    JSONObject body = new JSONObject();
    body.put("playId",playId );
    response = createPostConnectWithHeader(requestUrl,null,body,header);
    return response;
  }


  public static HttpResponse v2GetStartup(Map<String, String> params,Map<String,String> headerMap) {
    final String requestUrl = HttpNode + "/api/v1/wallet/startup";
    Map<String, String> header = getV2Header();
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Close");
    for(String key : headerMap.keySet()) {
      header.put(key,headerMap.get(key) );
    }
    response = createGetConnectWithHeader(requestUrl, params,null,header);
    return response;
  }

  public static HttpResponse v2UpdateUserCreateBNum(String updataNumber,String userhash,Map<String,String> headerMap) {
    final String requestUrl = HttpNode + "/api/v1/wallet/updateUserCreateBNum";
    Map<String, String> header = getV2Header();
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Close");
    for(String key : headerMap.keySet()) {
      header.put(key,headerMap.get(key) );
    }
    response = createPostConnectWithHeader(requestUrl, null,(JSONObject) JSONObject.parse("{\"userhash\":\"" + userhash + "\",\"number\":\"" + updataNumber + "\",\"access\":\"33572\"}"),header);
    return response;
  }

  public static HttpResponse v2UserCreateBlockNum(Map<String, String> params,Map<String,String> headerMap) {
    final String requestUrl = HttpNode + "/api/v1/wallet/userCreateBlockNum";
    Map<String, String> header = getV2Header();
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Close");
    for(String key : headerMap.keySet()) {
      header.put(key,headerMap.get(key) );
    }
    response = createGetConnectWithHeader(requestUrl, params,null,header);
    return response;
  }
  public static HttpResponse v2accountList(Map<String, String> params,JSONArray object) {
    try {
      String requestUrl = HttpNode + "/api/wallet/v2/account/list";
      Map<String, String> header = getV2Header();
      header.put("Content-type", "application/json; charset=utf-8");
      header.put("Connection", "Close");
      response = createPostConnectWithHeader(requestUrl,params, object,header);
      //response = createPostConnect(requestUrl,body);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
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
  public static HttpResponse v2DelAsset(Map<String, String> params,JSONObject object) {
    String requestUrl = HttpNode + "/api/wallet/v2/delAsset";
    Map<String, String> header = getV2Header();
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Close");
    response = createPostConnectWithHeader(requestUrl,params, object,header);
    return response;
  }

  public static HttpResponse v2Upgrade(Map<String, String> params) {
    String requestUrl = HttpNode + "/api/v1/wallet/v2/upgrade";
    Map<String, String> header = getV2Header();
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Close");
    response = createGetConnectWithHeader(requestUrl, null,null,params);
    return response;
  }

  public static HttpResponse v1Upgrade(Map<String, String> params) {
    String requestUrl = HttpNode + "/api/v1/wallet/upgrade";
    Map<String, String> header = getV2Header();
    header.put("Content-type", "application/json; charset=utf-8");
    header.put("Connection", "Close");
    response = createGetConnectWithHeader(requestUrl, null,null,params);
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
                                                         JSONArray requestBody,Map<String,String> header){
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
      if(header != null){
        for(String key: header.keySet()){
          httppost.setHeader(key,header.get(key));
          log.info(key+": "+header.get(key));
        }
      }
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toJSONString(), Charset.forName("UTF-8"));
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
          log.info("Add key to header: " + key+": "+header.get(key));
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

  public static GrpcAPI.Return accountPermissionUpdateForResponse(String permissionJson,
                                                                  byte[] owner, String priKey, WalletGrpc.WalletBlockingStub blockingStubFull) {
    Wallet.setAddressPreFixByte((byte) 0x41);
    ECKey temKey = null;
    try {
      BigInteger priK = new BigInteger(priKey, 16);
      temKey = ECKey.fromPrivate(priK);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    final ECKey ecKey = temKey;

    AccountContract.AccountPermissionUpdateContract.Builder builder = AccountContract.AccountPermissionUpdateContract.newBuilder();

    JSONObject permissions = JSONObject.parseObject(permissionJson);
    JSONObject ownerpermission = permissions.getJSONObject("owner_permission");
    JSONObject witnesspermission = permissions.getJSONObject("witness_permission");
    JSONArray activepermissions = permissions.getJSONArray("active_permissions");

    if (ownerpermission != null) {
      Protocol.Permission ownerPermission = json2Permission(ownerpermission);
      builder.setOwner(ownerPermission);
    }
    if (witnesspermission != null) {
      Protocol.Permission witnessPermission = json2Permission(witnesspermission);
      builder.setWitness(witnessPermission);
    }
    if (activepermissions != null) {
      List<Protocol.Permission> activePermissionList = new ArrayList<>();
      for (int j = 0; j < activepermissions.size(); j++) {
        JSONObject permission = activepermissions.getJSONObject(j);
        activePermissionList.add(json2Permission(permission));
      }
      builder.addAllActives(activePermissionList);
    }
    builder.setOwnerAddress(ByteString.copyFrom(owner));

    AccountContract.AccountPermissionUpdateContract contract = builder.build();

    GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.accountPermissionUpdate(contract);
    if (transactionExtention == null) {
      return null;
    }
    GrpcAPI.Return ret = transactionExtention.getResult();
    if (!ret.getResult()) {
      System.out.println("Code = " + ret.getCode());
      System.out.println("Message = " + ret.getMessage().toStringUtf8());
      return ret;
    }
    Protocol.Transaction transaction = transactionExtention.getTransaction();
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      System.out.println("Transaction is empty");
      return ret;
    }
    System.out.println(
            "Receive txid = " + ByteArray.toHexString(transactionExtention.getTxid().toByteArray()));
    transaction = signTransaction(ecKey, transaction);
    GrpcAPI.Return response = broadcastTransaction(transaction, blockingStubFull);

    return response;
  }
  private static Protocol.Permission json2Permission(JSONObject json) {
    Protocol.Permission.Builder permissionBuilder = Protocol.Permission.newBuilder();
    if (json.containsKey("type")) {
      int type = json.getInteger("type");
      permissionBuilder.setTypeValue(type);
    }
    if (json.containsKey("permission_name")) {
      String permissionName = json.getString("permission_name");
      permissionBuilder.setPermissionName(permissionName);
    }
    if (json.containsKey("threshold")) {
      //long threshold = json.getLong("threshold");
      long threshold = Long.parseLong(json.getString("threshold"));
      permissionBuilder.setThreshold(threshold);
    }
    if (json.containsKey("parent_id")) {
      int parentId = json.getInteger("parent_id");
      permissionBuilder.setParentId(parentId);
    }
    if (json.containsKey("operations")) {
      byte[] operations = ByteArray.fromHexString(json.getString("operations"));
      permissionBuilder.setOperations(ByteString.copyFrom(operations));
    }
    if (json.containsKey("keys")) {
      JSONArray keys = json.getJSONArray("keys");
      List<Protocol.Key> keyList = new ArrayList<>();
      for (int i = 0; i < keys.size(); i++) {
        Protocol.Key.Builder keyBuilder = Protocol.Key.newBuilder();
        JSONObject key = keys.getJSONObject(i);
        String address = key.getString("address");
        long weight = Long.parseLong(key.getString("weight"));
        //long weight = key.getLong("weight");
        //keyBuilder.setAddress(ByteString.copyFrom(address.getBytes()));
        keyBuilder.setAddress(ByteString.copyFrom(decode58Check(address)));
        keyBuilder.setWeight(weight);
        keyList.add(keyBuilder.build());
      }
      permissionBuilder.addAllKeys(keyList);
    }
    return permissionBuilder.build();
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
    long now = System.currentTimeMillis();
    //Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder().setExpiration(now+86400000L);
    Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder().setExpiration(now+86400000L);
    //Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder();
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

  public static Protocol.Transaction addTransactionSignWithPermissionIdOnly(Protocol.Transaction transaction,
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


  public static boolean urlCanVisited(String urlString,int timeOutMillSeconds) throws Exception{
    long lo = System.currentTimeMillis();
    URL url;
    URLConnection co;
    try {
      url = new URL(urlString);
      co =  url.openConnection();
      co.setConnectTimeout(timeOutMillSeconds);
      co.connect();

      System.out.println("连接可用");

    } catch (Exception e1) {
      System.out.println("连接打不开!");
      url = null;
      return false;
    }

    System.out.println(System.currentTimeMillis()-lo);
    System.out.println(co.getContent().toString());
    return true;
  }

  public static HashMap<String, String> getBycodeAbi(String solFile, String contractName) {
    final String compile = Configuration.getByPath("testng.conf")
            .getString("defaultParameter.solidityCompile");

    String dirPath = solFile.substring(solFile.lastIndexOf("/"), solFile.lastIndexOf("."));
    String outputPath = "src/test/resources/soliditycode" + dirPath;

    File binFile = new File(outputPath + "/" + contractName + ".bin");
    File abiFile = new File(outputPath + "/" + contractName + ".abi");
    if (binFile.exists()) {
      binFile.delete();
    }
    if (abiFile.exists()) {
      abiFile.delete();
    }

    HashMap<String, String> retMap = new HashMap<>();
    String absolutePath = System.getProperty("user.dir");
    log.debug("absolutePath: " + absolutePath);
    log.debug("solFile: " + solFile);
    log.debug("outputPath: " + outputPath);
    String cmd =
            compile + " --optimize --bin --abi --overwrite " + absolutePath + "/"
                    + solFile + " -o "
                    + absolutePath + "/" + outputPath;
    log.info("cmd: " + cmd);

    String byteCode = null;
    String abI = null;

    // compile solidity file
    try {
      exec(cmd);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // get byteCode and ABI
    try {
      byteCode = fileRead(outputPath + "/" + contractName + ".bin", false);
      retMap.put("byteCode", byteCode);
      log.debug("byteCode: " + byteCode);
      abI = fileRead(outputPath + "/" + contractName + ".abi", false);
      retMap.put("abI", abI);
      log.debug("abI: " + abI);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retMap;
  }

  public static String exec(String command) throws InterruptedException {
    String returnString = "";
    Process pro = null;
    Runtime runTime = Runtime.getRuntime();
    if (runTime == null) {
      log.error("Create runtime false!");
    }
    try {
      pro = runTime.exec(command);
      BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
      PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
      String line;
      while ((line = input.readLine()) != null) {
        returnString = returnString + line + "\n";
      }
      input.close();
      output.close();
      pro.destroy();
    } catch (IOException ex) {
      log.error(null, ex);
    }
    return returnString;
  }

  public static String fileRead(String filePath, boolean isLibrary) throws Exception {
    File file = new File(filePath);
    FileReader reader = new FileReader(file);
    BufferedReader breader = new BufferedReader(reader);
    StringBuilder sb = new StringBuilder();
    String s = "";
    if (!isLibrary) {
      if ((s = breader.readLine()) != null) {
        sb.append(s);
      }
      breader.close();
    } else {
      String fistLine = breader.readLine();
      breader.readLine();
      if ((s = breader.readLine()) != null && !s.equals("")) {
        s = s.substring(s.indexOf("-> ") + 3);
        sb.append(s + ":");
      } else {
        s = fistLine.substring(fistLine.indexOf("__") + 2, fistLine.lastIndexOf("__"));
        sb.append(s + ":");
      }
      breader.close();
    }
    return sb.toString();
  }
  public static SmartContractOuterClass.SmartContract.ABI jsonStr2Abi(String jsonStr) {
    if (jsonStr == null) {
      return null;
    }

    JsonParser jsonParser = new JsonParser();
    JsonElement jsonElementRoot = jsonParser.parse(jsonStr);
    JsonArray jsonRoot = jsonElementRoot.getAsJsonArray();
    SmartContractOuterClass.SmartContract.ABI.Builder abiBuilder = SmartContractOuterClass.SmartContract.ABI.newBuilder();
    for (int index = 0; index < jsonRoot.size(); index++) {
      JsonElement abiItem = jsonRoot.get(index);
      boolean anonymous =
              abiItem.getAsJsonObject().get("anonymous") != null ? abiItem.getAsJsonObject()
                      .get("anonymous").getAsBoolean() : false;
      final boolean constant =
              abiItem.getAsJsonObject().get("constant") != null ? abiItem.getAsJsonObject()
                      .get("constant").getAsBoolean() : false;
      final String name =
              abiItem.getAsJsonObject().get("name") != null ? abiItem.getAsJsonObject().get("name")
                      .getAsString() : null;
      JsonArray inputs =
              abiItem.getAsJsonObject().get("inputs") != null ? abiItem.getAsJsonObject().get("inputs")
                      .getAsJsonArray() : null;
      final JsonArray outputs =
              abiItem.getAsJsonObject().get("outputs") != null ? abiItem.getAsJsonObject()
                      .get("outputs").getAsJsonArray() : null;
      String type =
              abiItem.getAsJsonObject().get("type") != null ? abiItem.getAsJsonObject().get("type")
                      .getAsString() : null;
      final boolean payable =
              abiItem.getAsJsonObject().get("payable") != null ? abiItem.getAsJsonObject()
                      .get("payable").getAsBoolean() : false;
      final String stateMutability =
              abiItem.getAsJsonObject().get("stateMutability") != null ? abiItem.getAsJsonObject()
                      .get("stateMutability").getAsString() : null;
      if (type == null) {
        log.error("No type!");
        return null;
      }
      if (!type.equalsIgnoreCase("fallback") && null == inputs) {
        log.error("No inputs!");
        continue;
      }

      SmartContractOuterClass.SmartContract.ABI.Entry.Builder entryBuilder = SmartContractOuterClass.SmartContract.ABI.Entry.newBuilder();
      entryBuilder.setAnonymous(anonymous);
      entryBuilder.setConstant(constant);
      if (name != null) {
        entryBuilder.setName(name);
      }

      /* { inputs : optional } since fallback function not requires inputs*/
      if (inputs != null) {
        for (int j = 0; j < inputs.size(); j++) {
          JsonElement inputItem = inputs.get(j);
          if (inputItem.getAsJsonObject().get("name") == null
                  || inputItem.getAsJsonObject().get("type") == null) {
            log.error("Input argument invalid due to no name or no type!");
            return null;
          }
          String inputName = inputItem.getAsJsonObject().get("name").getAsString();
          String inputType = inputItem.getAsJsonObject().get("type").getAsString();
          SmartContractOuterClass.SmartContract.ABI.Entry.Param.Builder paramBuilder = SmartContractOuterClass.SmartContract.ABI.Entry.Param
                  .newBuilder();
          paramBuilder.setIndexed(false);
          paramBuilder.setName(inputName);
          paramBuilder.setType(inputType);
          entryBuilder.addInputs(paramBuilder.build());
        }
      }

      /* { outputs : optional } */
      if (outputs != null) {
        for (int k = 0; k < outputs.size(); k++) {
          JsonElement outputItem = outputs.get(k);

          SmartContractOuterClass.SmartContract.ABI.Entry.Param.Builder paramBuilder = SmartContractOuterClass.SmartContract.ABI.Entry.Param
                  .newBuilder();
          if (outputItem.getAsJsonObject().get("name") != null) {
            String outputName = outputItem.getAsJsonObject().get("name").getAsString();

            paramBuilder.setName(outputName);
          }
          if (outputItem.getAsJsonObject().get("type") == null) {

            String outputType = outputItem.getAsJsonObject().get("type").getAsString();
            paramBuilder.setType(outputType);
          }
          paramBuilder.setIndexed(false);
          entryBuilder.addOutputs(paramBuilder.build());
        }
      }

      entryBuilder.setType(getEntryType(type.toLowerCase(Locale.ROOT)));
      entryBuilder.setPayable(payable);
      if (stateMutability != null) {
        entryBuilder.setStateMutability(getStateMutability(stateMutability.toLowerCase(Locale.ROOT)));
      }

      abiBuilder.addEntrys(entryBuilder.build());
    }

    return abiBuilder.build();
  }

  public static SmartContractOuterClass.SmartContract.ABI.Entry.EntryType getEntryType(String type) {
    switch (type) {
      case "constructor":
        return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Constructor;
      case "function":
        return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Function;
      case "event":
        return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Event;
      case "fallback":
        return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Fallback;
      default:
        return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.UNRECOGNIZED;
    }
  }

  public static SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType getStateMutability(
          String stateMutability) {
    switch (stateMutability.toLowerCase(Locale.ROOT)) {
      case "pure":
        return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.Pure;
      case "view":
        return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.View;
      case "nonpayable":
        return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.Nonpayable;
      case "payable":
        return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.Payable;
      default:
        return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.UNRECOGNIZED;
    }
  }

  public static byte[] decode58Check(String input) {
    byte[] decodeCheck = org.tron.common.utils.Base58.decode(input);
    if (decodeCheck.length <= 4) {
      return null;
    }
    byte[] decodeData = new byte[decodeCheck.length - 4];
    System.arraycopy(decodeCheck, 0, decodeData, 0, decodeData.length);
    byte[] hash0 = Sha256Hash.hash(CommonParameter.getInstance().isECKeyCryptoEngine(), decodeData);
    byte[] hash1 = Sha256Hash.hash(CommonParameter.getInstance().isECKeyCryptoEngine(), hash0);
    if (hash1[0] == decodeCheck[decodeData.length] && hash1[1] == decodeCheck[decodeData.length + 1]
            && hash1[2] == decodeCheck[decodeData.length + 2] && hash1[3] == decodeCheck[
            decodeData.length + 3]) {
      return decodeData;
    }
    return null;
  }

  public static GrpcAPI.Return broadcastTransaction(Protocol.Transaction transaction,
                                                    WalletGrpc.WalletBlockingStub blockingStubFull) {
    int i = 10;
    GrpcAPI.Return response = blockingStubFull.broadcastTransaction(transaction);
    while (!response.getResult() && response.getCode() == GrpcAPI.Return.response_code.SERVER_BUSY
            && i > 0) {
      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      i--;
      response = blockingStubFull.broadcastTransaction(transaction);
      log.info("repeate times = " + (10 - i));
    }

    if (response.getResult() == false) {
      log.info("Code = " + response.getCode());
      log.info("Message = " + response.getMessage().toStringUtf8());
    }
    return response;
  }

  public static Boolean sendcoinDirectely(byte[] to, long amount, byte[] owner, String priKey,
                                 WalletGrpc.WalletBlockingStub blockingStubFull) {
    log.info("in PublicMethod: priKey is "+priKey.toString());
    Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
    //String priKey = testKey002;
    ECKey temKey = null;
    try {
      BigInteger priK = new BigInteger(priKey, 16);
      temKey = ECKey.fromPrivate(priK);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    final ECKey ecKey = temKey;
//    ecKey.getAddress();
//    log.info("-------key: "+ByteArray.toHexString(ecKey.getPrivateKey()));



    Integer times = 0;
    while (times++ <= 2) {

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
        continue;
      }
      transaction = signTransaction(ecKey, transaction);
      GrpcAPI.Return response = broadcastTransaction(transaction, blockingStubFull);
      return response.getResult();
    }
    return false;
  }
  public static Protocol.Transaction signTransaction(ECKey ecKey,
                                                     Protocol.Transaction transaction) {
    Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
    if (ecKey == null || ecKey.getPrivKey() == null) {
      //logger.warn("Warning: Can't sign,there is no private key !!");
      return null;
    }
    transaction = setTimestamp(transaction);
    log.info("Txid in sign is " + ByteArray.toHexString(Sha256Hash
            .hash(CommonParameter.getInstance().isECKeyCryptoEngine(),
                    transaction.getRawData().toByteArray())));
    return sign(transaction, ecKey);
  }
  public static Protocol.Transaction setTimestamp(Protocol.Transaction transaction) {
    long currentTime = System.currentTimeMillis();//*1000000 + System.nanoTime()%1000000;
    Protocol.Transaction.Builder builder = transaction.toBuilder();
    org.tron.protos.Protocol.Transaction.raw.Builder rowBuilder = transaction.getRawData()
            .toBuilder();
    rowBuilder.setTimestamp(currentTime);
    builder.setRawData(rowBuilder.build());
    return builder.build();
  }
  public static Protocol.Transaction sign(Protocol.Transaction transaction, ECKey myKey) {
    ByteString lockSript = ByteString.copyFrom(myKey.getAddress());
    Protocol.Transaction.Builder transactionBuilderSigned = transaction.toBuilder();

    byte[] hash = Sha256Hash.hash(CommonParameter
            .getInstance().isECKeyCryptoEngine(), transaction.getRawData().toByteArray());
    List<Protocol.Transaction.Contract> listContract = transaction.getRawData().getContractList();
    for (int i = 0; i < listContract.size(); i++) {
      ECKey.ECDSASignature signature = myKey.sign(hash);
      ByteString bsSign = ByteString.copyFrom(signature.toByteArray());
      transactionBuilderSigned.addSignature(
              bsSign);//Each contract may be signed with a different private key in the future.
    }

    transaction = transactionBuilderSigned.build();
    return transaction;
  }
}