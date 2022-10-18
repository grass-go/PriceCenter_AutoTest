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
public class getStartup extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  Map<String, String> headers = new HashMap<>();

  @Test(enabled = true, groups = {"NoSignature"})
  public void test01GetStartupForAndroidLowerVersionWithNoSig(){
    params.put("height","1440");
    params.put("width","1440");
    headers.put("System","Android");
    headers.put("Version","3.7.5");
    response = TronlinkApiList.v1GetStartupNoSig(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());
  }

  @Test(enabled = true, groups = {"NoSignature"})
  public void test01GetStartupForAndroidHighVersionWithNoSig(){
    params.put("height","1440");
    params.put("width","1440");
    headers.put("System","Android");
    headers.put("Version","4.12.5");
    response = TronlinkApiList.v1GetStartupNoSig(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }




  @Test(enabled = true)
  public void test01GetStartupForAndroid(){
    params.put("height","1440");
    params.put("width","1440");
    params.put("address", quince_B58);
    headers.put("System","Android");
    headers.put("Version","4.11.0");

    response = TronlinkApiList.v1GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());
  }


  @Test(enabled = true)
  public void test02GetStartupForIos(){
    headers.put("System","iOS");
    headers.put("Version","3.7.5");

    params.put("height","640.0");
    params.put("width","1136.0");
    params.put("address", quince_B58);
    response = TronlinkApiList.v1GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());


    params.put("height","750.0");
    params.put("width","1334.0");
    params.put("address", quince_B58);
    response = TronlinkApiList.v1GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());

    params.put("height","828.0");
    params.put("width","1792.0");
    params.put("address", quince_B58);
    response = TronlinkApiList.v1GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());

    params.put("height","1125.0");
    params.put("width","2436.0");
    params.put("address", quince_B58);
    response = TronlinkApiList.v1GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());

    params.put("height","1242.0");
    params.put("width","2208.0");
    params.put("address", quince_B58);
    response = TronlinkApiList.v1GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());

    params.put("height","1242.0");
    params.put("width","2688.0");
    params.put("address", quince_B58);
    response = TronlinkApiList.v1GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());
  }



}
