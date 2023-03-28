package tron.tronlink.old;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class playScreen extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  HashMap<String, String> headers = new HashMap<>();

  @Test(enabled = true,groups = {"NoSignature"})
  public void test01PlayScreenInfoLowVersionWithNoSigManual(){

    headers.clear();
    headers.put("Lang", "1");
    headers.put("Version", "V4.5.0");
    headers.put("DeviceID", "1111111111");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");


    response = TronlinkApiList.v1PlayScreenInfoNoSig(headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
  }

  @Test(enabled = true)
  public void test01PlayScreenInfoHighVersionWithNoSig(){

    /*params.put("System","Android");
    params.put("Version","4.11.0");*/

    headers.clear();
    headers.put("Lang", "1");
    headers.put("Version", "V4.10.0");
    headers.put("DeviceID", "1111111111");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");

    response = TronlinkApiList.v1PlayScreenInfoNoSig(headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
  }

  @Test(enabled = true)
  public void test01PlayScreenInfo(){
    params.clear();
    params.put("address",quince_B58);

    headers.clear();
    headers.put("Lang", "1");
    headers.put("Version", "3.7.0");
    headers.put("DeviceID", "1111111111");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");

    response = TronlinkApiList.v1PlayScreenInfo(headers,params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
  }




  @Test(enabled = true, groups = {"NoSignature"})
  public void test02PlayScreenDealLowVersionWithNoSig(){
    headers.clear();
    headers.put("Lang", "2");
    headers.put("Version", "V4.5.0");
    headers.put("DeviceID", "1111111111");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");

    Integer playId = 16;
    response = TronlinkApiList.v1PlayScreenDealNoSig(String.valueOf(playId),headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
  }

  @Test(enabled = true)
  public void test02PlayScreenDealHighVersionWithNoSig(){
    headers.put("DeviceID","hhkhkjhkj887");
    headers.put("System", "Android");
    headers.put("Lang", "1");
    headers.put("Version", String.valueOf("4.12.1"));
    headers.put("chain","MainChain" );
    headers.put("packageName","com.tronlinkpro.wallet" );

    Integer playId = 11;
    response = TronlinkApiList.v1PlayScreenDealNoSig(String.valueOf(playId),headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    org.testng.Assert.assertEquals(20004, responseContent.getIntValue("code"));
    org.testng.Assert.assertEquals("Error param.", responseContent.getString("message"));

  }

  @Test(enabled = true)
  public void test02PlayScreenDeal(){
    headers.put("DeviceID","hhkhkjhkj887");
    headers.put("System", "Android");
    headers.put("Lang", "1");
    headers.put("Version", String.valueOf("4.0.1"));
    headers.put("chain","MainChain" );
    headers.put("packageName","com.tronlinkpro.wallet" );
    //params.put("address",quince_B58);

    Integer playId = 11;
    response = TronlinkApiList.v1PlayScreenDeal(String.valueOf(playId),params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
  }


  @Test(enabled = true)
  public void test01PlayScreenInfoManual() throws InterruptedException {
    params.clear();
    params.put("address",quince_B58);

    headers.clear();
    headers.put("Lang", "1");
    headers.put("Version", "V4.13.0");
    headers.put("DeviceID", "1111111113");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");
    headers.put("channel","official");
    //headers.put("channel","googleplay");
    //headers.put("channel","samsunggalaxy");

    response = TronlinkApiList.v1PlayScreenInfo(headers,params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    //==============
    params.clear();
    params.put("address",quince_B58);

    headers.clear();
    headers.put("Lang", "1");
    headers.put("Version", "V4.11.0");
    headers.put("DeviceID", "1111111113");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "iOS");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");
    headers.put("channel","official");
    //headers.put("channel","samsunggalaxy");

    response = TronlinkApiList.v1PlayScreenInfo(headers,params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    //============
    params.clear();
    params.put("address",quince_B58);

    headers.clear();
    headers.put("Lang", "2");
    headers.put("Version", "V4.11.0");
    headers.put("DeviceID", "1111111113");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");
    headers.put("channel","googleplay");
    //headers.put("channel","samsunggalaxy");

    response = TronlinkApiList.v1PlayScreenInfo(headers,params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);

    //================================

    params.clear();
    params.put("address",quince_B58);

    headers.clear();
    headers.put("Lang", "2");
    headers.put("Version", "V4.11.0");
    headers.put("DeviceID", "1111111113");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");
    //headers.put("channel","googleplay");
    headers.put("channel","samsunggalaxy");

    response = TronlinkApiList.v1PlayScreenInfo(headers,params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);

  }

  @Test(enabled = true)
  public void test02PlayScreenDealManual(){
    /*headers.put("DeviceID","hhkhkjhkj887");
    headers.put("System", "Android");
    headers.put("Lang", "1");
    headers.put("Version", String.valueOf("4.0.1"));
    headers.put("chain","MainChain" );
    headers.put("packageName","com.tronlinkpro.wallet" );*/
    //params.put("address",quince_B58);
    params.clear();
    params.put("address",quince_B58);

    headers.clear();
    headers.put("Lang", "2");
    headers.put("Version", "4.11.0");
    headers.put("DeviceID", "1111111112");
    headers.put("chain", "MainChain");
    headers.put("packageName", "com.tronlinkpro.wallet");
    headers.put("System", "Android");
    headers.put("Content-type", "application/json; charset=utf-8");
    headers.put("Connection", "Keep-Alive");
    headers.put("channel","googleplay");


    Integer playId = 5;
    response = TronlinkApiList.v1PlayScreenDeal(String.valueOf(playId),params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");

    //=================================
      params.clear();
      params.put("address",quince_B58);

      headers.clear();
      headers.put("Lang", "2");
      headers.put("Version", "4.11.0");
      headers.put("DeviceID", "1111111112");
      headers.put("chain", "MainChain");
      headers.put("packageName", "com.tronlinkpro.wallet");
      headers.put("System", "iOS");
      headers.put("Content-type", "application/json; charset=utf-8");
      headers.put("Connection", "Keep-Alive");
      headers.put("channel","official");


      playId = 18;
      response = TronlinkApiList.v1PlayScreenDeal(String.valueOf(playId),params,headers);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      TronlinkApiList.printJsonObjectContent(responseContent);
      Assert.assertTrue(responseContent.getInteger("code") == 0);
      Assert.assertEquals(responseContent.getString("message"),"OK");

  }


}
