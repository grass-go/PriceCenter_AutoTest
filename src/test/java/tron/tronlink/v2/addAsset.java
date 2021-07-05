package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
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
  List<String> trc721tokenList = new ArrayList<>();
  Map<String, String> params = new HashMap<>();


  //关注资产，assetList接口可见，取消关注，assetList不可见
  @Test(enabled = true)
  public void addAsset01() throws Exception{
    params.clear();
    trc10tokenList.clear();
    jsonObject.clear();
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

    Thread.sleep(500);

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

    Thread.sleep(500);

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

  //关注trc721资产，getAllCollections接口可见，取消关注，getAllCollections不可见

  @Test(enabled = false)
  public void addAsset02() throws Exception {
    params.clear();
    trc721tokenList.clear();
    jsonObject.clear();
    params.put("nonce", "12345");
    params.put("secretId", "SFSUIOJBFMLKSJIF");
    params.put("signature", "0MD5qghokR6tbCau0m%2BUfZzz45o%3D");

    trc721tokenList.add("TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99");//Pitaya (PITAYA)
    trc721tokenList.add("TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h");//TAHIGO KOHINAGI (TAHIGO)
    jsonObject.put("address", address721_Hex);
    jsonObject.put("token721", trc721tokenList);
    response = TronlinkApiList.v2AddAsset(params, jsonObject);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertEquals(0, responseContent.getIntValue("code"));
    Assert.assertEquals("OK", responseContent.getString("message"));
    Assert.assertEquals(true, responseContent.getBooleanValue("data"));

    Thread.sleep(500);

    params.clear();
    params.put("nonce", "12345");
    params.put("secretId", "SFSUIOJBFMLKSJIF");
    params.put("signature", "15sBsg%2B0R9FOdxGVrZr9K6XVpXI%3D");
    params.put("address", address721_B58);
    params.put("version","v2");
    response = TronlinkApiList.v2GetAllCollection(params);
    //Retry 2 time
    for(int i=0;i<2;i++){
      if (responseContent.getIntValue("code") == 4500){
        Thread.currentThread().sleep(5000);
        response = TronlinkApiList.v2GetAllCollection(params);
      }
      else{
        break;
      }
    }

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));

    Object actualName = JSONPath.eval(responseContent, "$.data[contractAddress='TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99'].name");
    System.out.println("actualName Object is: "+actualName.toString());
    JSONArray actualNameArray = (JSONArray) actualName;
    Assert.assertEquals(1, actualNameArray.size());
    Assert.assertEquals("Pitaya", actualNameArray.get(0));

    Object actualName2 = JSONPath.eval(responseContent, "$.data[contractAddress='TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h'].name");
    System.out.println("actualName2 Object is: "+actualName2.toString());
    JSONArray actualNameArray2 = (JSONArray) actualName2;
    Assert.assertEquals(1, actualNameArray2.size());
    Assert.assertEquals("TAHIGO KOHINAGI", actualNameArray2.get(0));

    //cancel focus 721
    params.clear();
    trc721tokenList.clear();
    jsonObject.clear();
    params.put("nonce", "12345");
    params.put("secretId", "SFSUIOJBFMLKSJIF");
    params.put("signature", "0MD5qghokR6tbCau0m%2BUfZzz45o%3D");

    trc721tokenList.add("TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99");//Pitaya (PITAYA)
    trc721tokenList.add("TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h");//TAHIGO KOHINAGI (TAHIGO)

    jsonObject.put("address", address721_Hex);
    jsonObject.put("token721Cancel", trc721tokenList);
    response = TronlinkApiList.v2AddAsset(params, jsonObject);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertEquals(0, responseContent.getIntValue("code"));
    Assert.assertEquals("OK", responseContent.getString("message"));
    Assert.assertEquals(true, responseContent.getBooleanValue("data"));

    Thread.sleep(500);

    params.clear();
    params.put("nonce", "12345");
    params.put("secretId", "SFSUIOJBFMLKSJIF");
    params.put("signature", "15sBsg%2B0R9FOdxGVrZr9K6XVpXI%3D");
    params.put("address", address721_B58);
    params.put("version","v2");

    response = TronlinkApiList.v2GetAllCollection(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));

    actualName = JSONPath.eval(responseContent, "$..data[contractAddress='TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99'].name");
    System.out.println("actualName Object is: "+actualName.toString());
    Assert.assertEquals(actualName.toString(), "[]");

    actualName2 = JSONPath.eval(responseContent, "$..data[contractAddress='TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h'].name");
    System.out.println("actualName2 Object is: "+actualName2.toString());
    Assert.assertEquals(actualName2.toString(),"[]");
  }


  //取消关注用例中关注的trc10
  @AfterClass(enabled = true)
  public void after(){
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
  }

}
