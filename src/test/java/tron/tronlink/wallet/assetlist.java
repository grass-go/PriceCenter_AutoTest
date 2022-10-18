package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class assetlist extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private JSONObject jsonObject;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);
  private JsonObject addressJson = new JsonObject();

  @Test(enabled = true)
  public void assetlistLowVersionWithNoSig(){
    response = TronlinkApiList.assetlistNoSig(address721_B58,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject data = responseContent.getJSONObject("data");
    JSONArray tokens = data.getJSONArray("token");
    Assert.assertTrue(tokens.size() > 0);
  }

  @Test(enabled = true)
  public void assetlistHighVersionWithNoSig(){
    HashMap<String,String> header = new HashMap();
    header.put("System","Android");
    header.put("Version","4.11.0");
    response = TronlinkApiList.assetlistNoSig(address721_B58,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    org.junit.Assert.assertEquals(20004, responseContent.getIntValue("code"));
    org.junit.Assert.assertEquals("Error param.", responseContent.getString("message"));
  }


  @Test(enabled = true)
  public void assetlist(){
    Map<String, String> paramsInURL = new HashMap<>();
    paramsInURL.put("address", address721_B58);
    response = TronlinkApiList.assetlist(address721_B58,paramsInURL);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(targetContent.containsKey("totalTRX"));
    Assert.assertTrue(targetContent.getDoubleValue("totalTRX") > 0);
    jsonObject = targetContent.getJSONObject("price");
    Assert.assertTrue(jsonObject.containsKey("priceCny"));
    Assert.assertTrue(jsonObject.containsKey("priceUSD"));
    Assert.assertTrue(jsonObject.getDoubleValue("priceCny") > 0);
    Assert.assertTrue(jsonObject.getDoubleValue("priceUSD") > 0);
    Assert.assertTrue(targetContent.getJSONArray("token").size()>1);
    //token object
    for (Object json:targetContent.getJSONArray("token")) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("type"));
      Assert.assertTrue(jsonObject.containsKey("top"));
      Assert.assertTrue(jsonObject.containsKey("isOfficial"));
      Assert.assertTrue(jsonObject.containsKey("name"));
      Assert.assertTrue(jsonObject.containsKey("shortName"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("contractAddress"));
      Assert.assertTrue(jsonObject.containsKey("balance"));
      Assert.assertTrue(jsonObject.containsKey("totalBalance"));
      Assert.assertTrue(jsonObject.containsKey("logoUrl"));
      Assert.assertTrue(jsonObject.containsKey("precision"));
      Assert.assertTrue(jsonObject.containsKey("marketId"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("trxCount"));
      String shortname = jsonObject.getString("shortName");
      //if ("WIN".equals(shortname) || "JST".equals(shortname)
      //        || "USDD".equals(shortname) || "BTT".equals(shortname)){
      if ("WIN".equals(shortname) || "USDD".equals(shortname) || "BTTOLD".equals(shortname)){

        Assert.assertTrue(jsonObject.getDoubleValue("price") > 0);
        Assert.assertTrue(jsonObject.getDoubleValue("trxCount") > 0);
        Assert.assertTrue(jsonObject.getDoubleValue("totalBalance") > 0);
      }
      int type = jsonObject.getIntValue("type");
      if(type == 0){
        Assert.assertTrue(jsonObject.getDoubleValue("totalBalance")>0);
        Assert.assertTrue(jsonObject.getDoubleValue("balance")>0);
        Assert.assertTrue(jsonObject.getDoubleValue("trxCount")>0);
        Assert.assertEquals(0,jsonObject.getIntValue("price"));
      }
      Assert.assertTrue(jsonObject.containsKey("inMainChain"));
      Assert.assertTrue(jsonObject.containsKey("inSideChain"));
    }
  }

  @Test(enabled = false, description = "Api /TronlinkApiList/wallet/assetlist test")
  public void test001Assetlist() throws Exception {
    Map<String, String> paramsInURL = new HashMap<>();
    paramsInURL.put("address", "TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    response = TronlinkApiList.assetlist("TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe",paramsInURL);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(targetContent);

    Assert.assertTrue(targetContent.getFloat("totalTRX") >= 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceUSD") > 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceCny") > 0);

    JSONArray tokenArray = targetContent.getJSONArray("token");
    Assert.assertTrue(tokenArray.size() >= 2);
  }


  @Test(enabled = false, description = "Api /TronlinkApiList/wallet/assetlist exception test,v2 is normal")
  public void test002AssetlistException() throws Exception {
    //Base58 decode address can't get right information
    Map<String, String> paramsInURL = new HashMap<>();
    paramsInURL.put("address", "TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    response = TronlinkApiList.assetlist("TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe",paramsInURL);

    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(targetContent);

    Assert.assertTrue(targetContent.getFloat("totalTRX") == 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceUSD") > 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceCny") > 0);

  }

  
}
