package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class addAsset extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONObject object;

  private JSONArray array = new JSONArray();
  JSONObject jsonObject = new JSONObject();
  List<String> trc10tokenList = new ArrayList<>();
  Map<String, String> params = new HashMap<>();



  @Test(enabled = true)
  public void addAsset01(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
//    params.put("signature","7%2B%2F36luYNVcnean87VL9AaY4O1o%3D");
    params.put("signature","6uXyipER57diwY4P3bbT6pDluYo%3D");
    trc10tokenList.add("1002000");
//    jsonObject.put("address","41F985738AE54FD87ED6CD07065905EBEA355E66CD");
    jsonObject.put("address",addressNewAsset41);
    jsonObject.put("token10",trc10tokenList);
    response = TronlinkApiList.v2AddAsset(params,jsonObject);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    Assert.assertEquals(0,responseContent.getIntValue("code"));
    Assert.assertEquals("OK",responseContent.getString("message"));
    Assert.assertEquals(true,responseContent.getBooleanValue("data"));

    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","66f37xLdCz%2FV9geQGc%2FhYd98HR0%3D");
//    params.put("signature","YJ825gN23aqvE8izShZp3cWwVQw%3D");
    params.put("address",addressNewAsset41);
//    params.put("address","41F985738AE54FD87ED6CD07065905EBEA355E66CD");

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokenArray = dataContent.getJSONArray("token");
    boolean flag = false;
    JSONObject token;
    for(int i=0;i<tokenArray.size();i++){
      token = tokenArray.getJSONObject(i);
      if ("1002000".equals(token.getString("id"))){
        flag = true;
        log.info("1111");
      }
    }
    Assert.assertTrue(flag);

    params.clear();
    trc10tokenList.clear();
    jsonObject.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","6uXyipER57diwY4P3bbT6pDluYo%3D");
//    params.put("signature","7%2B%2F36luYNVcnean87VL9AaY4O1o%3D");
    trc10tokenList.add("1002000");
    jsonObject.put("address",addressNewAsset41);
//    jsonObject.put("address","41F985738AE54FD87ED6CD07065905EBEA355E66CD");
    jsonObject.put("token10Cancel",trc10tokenList);
    response = TronlinkApiList.v2AddAsset(params,jsonObject);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    Assert.assertEquals(0,responseContent.getIntValue("code"));
    Assert.assertEquals("OK",responseContent.getString("message"));
    Assert.assertEquals(true,responseContent.getBooleanValue("data"));

    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","66f37xLdCz%2FV9geQGc%2FhYd98HR0%3D");
//    params.put("signature","YJ825gN23aqvE8izShZp3cWwVQw%3D");
    params.put("address",addressNewAsset41);
//    params.put("address","41F985738AE54FD87ED6CD07065905EBEA355E66CD");

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    tokenArray = dataContent.getJSONArray("token");
    flag = false;
    for(int i=0;i<tokenArray.size();i++){
      token = tokenArray.getJSONObject(i);
      if ("1002000".equals(token.getString("id"))){
        flag = true;
        log.info("22222");
      }
    }
    Assert.assertFalse(flag);

  }


}
