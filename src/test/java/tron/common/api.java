package tron.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.http.Header;
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
import tron.common.utils.TronlinkServerHttpClient;
import tron.tronlink.base.TronlinkBase;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class api extends TronlinkServerHttpClient {
  public static String HOME_HOST = "https://list.tronlink.org";//host
  public static String TEST_HOST = "https://testlist.tronlink.org";//test
  public static String PRE_HOST = "https://testpre.tronlink.org";//pre
  public static String HttpNode = TronlinkBase.tronlinkUrl;
  public static String testAddressBase58 = "TKpJUP4CCymphdug1XmGzDGDmGXZjLyf29";
  public static String testAddressBase64 = "416C0214C9995C6F3A61AB23F0EB84B0CDE7FD9C7C";
  public static String testAccountKey = "7400E3D0727F8A61041A8E8BF86599FE5597CE19DE451E59AED07D60967A5E25";
  static String transactionSignString;

  public static HttpResponse upgradeNoSig(HashMap<String,String> header) {
    final String requestUrl = HttpNode + "/api/v1/wallet/upgrade";
    response = createGetConnect(requestUrl,null,null,header);
    return response;
  }

  public static HttpResponse upgrade(HashMap<String,String> caseParams,HashMap<String,String> header) {
    final String curURI = "/api/v1/wallet/upgrade";
    response = createGetConnect(curURI,caseParams,null,header);
    return response;
  }



  public static HttpResponse trxTransferRecord(HashMap<String, String> param) {
    final String requestUrl = HttpNode + "/api/simple-transfer";
    response = createGetConnect(requestUrl,param,null,null);
    return response;
  }

  public static HttpResponse getAssetList(JSONObject address) throws Exception {
    final String requestUrl = HttpNode + "/api/wallet/assetlist";
    response = createGetConnect(requestUrl,null, address,null);
    return response;
  }

  public static HttpResponse lotteryRecord(HashMap<String,String> param) throws Exception{
    final String requesturl = HttpNode + "/api/wallet/lottery/record";
    response = createGetConnect(requesturl,param,null,null);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }



  public static HttpResponse getVersionLogNoSig(HashMap<String,String> param, HashMap<String,String> header) throws Exception{
    final String requesturl = HttpNode + "/api/v1/wallet/version_log";
    response = createGetConnect(requesturl,param,null, header);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse getVersionLog(HashMap<String,String> param, HashMap<String,String> header) throws Exception{
    final String curURI = "/api/v1/wallet/version_log";
    response = createGetConnectWithSignature(curURI,param,header);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse transactionHistory(HashMap<String,String> param) throws Exception{
    final String requesturl = HttpNode + "/api/simple-transaction";
    response = createGetConnect(requesturl,param,null,null);
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    return response;
  }

  public static HttpResponse trxPrice(){
    final String requesturl = HttpNode + "/api/v1/wallet/trxPrice";
    response = createGetConnect(requesturl,null,null,null);
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    return response;
  }
  public static HttpResponse tronweb(){
    final String requesturl = HttpNode + "/api/web/v1/tronweb";
    response = createGetConnect(requesturl,null,null,null);
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    return response;
  }

  public static HttpResponse getCoinCapTrxPriceNoSig(HashMap<String,String> header) {
    final String requesturl = HttpNode + "/api/v1/wallet/getCoinCapTrxPrice";
    response = createGetConnect(requesturl,null,null,header);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }
  public static HttpResponse getCoinCapTrxPrice(HashMap<String,String> params) {
    final String curURI = "/api/v1/wallet/getCoinCapTrxPrice";
    response = createGetConnectWithSignature(curURI,params,null);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse getLatestAPKNoSig(HashMap<String,String> header) throws Exception{
    final String requesturl = HttpNode + "/api/v1/wallet/getLatestAPK";
    response = createGetConnect(requesturl,null,null,header);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse getLatestAPK(HashMap<String,String> caseParams) throws Exception{
    final String curURI = "/api/v1/wallet/getLatestAPK";
    response = createGetConnectWithSignature(curURI,caseParams,null);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse communityNoSig(HashMap<String,String> header) throws Exception{
    final String requesturl = HttpNode + "/api/v1/wallet/community";
    response = createGetConnect(requesturl,null,null,header);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse community(HashMap<String,String> params) throws Exception{
    final String curURI = "/api/v1/wallet/community";
    response = createGetConnectWithSignature(curURI,params,null);
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse marketPairList(HashMap<String,String> header) throws Exception{
    final String requesturl = "https://list.tronlink.org/api/exchange/marketPair/list";
    response = createGetConnect(requesturl,null,null,header);
    Assert.assertNotEquals(null,response);
    System.out.println("status code: "+ response.getStatusLine().getStatusCode());
    Assert.assertTrue(api.verificationResult(response));
    return response;
  }

  public static HttpResponse feedBack(JSONObject param) throws Exception {
    final String requestUrl = HttpNode + "/api/v1/wallet/feedback";
    response = createGetConnect(requestUrl, null,param,null);
    return response;
  }

  public static Boolean verificationResult(HttpResponse response) {
    if (response.getStatusLine().getStatusCode() != 200) {
      return false;
    }
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    return true;
  }

  public static String gettransactionsign(String fullnodeNode, String transactionString,
      String privateKey) {
    try {
      String requestUrl = "http://" + fullnodeNode + "/wallet/gettransactionsign";
      System.out.println(requestUrl);
      JSONObject userBaseObj2 = new JSONObject();
      userBaseObj2.put("transaction", transactionString);
      userBaseObj2.put("privateKey", privateKey);
      response = createGetConnect(requestUrl,null, userBaseObj2,null);
      transactionSignString = EntityUtils.toString(response.getEntity());
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionSignString;
  }

}

