package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;

public class assetlist {
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
  public void assetlist(){

    response = TronlinkApiList.assetlist(node,"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(targetContent.containsKey("totalTRX"));
    jsonObject = targetContent.getJSONObject("price");
    Assert.assertTrue(jsonObject.containsKey("priceCny"));
    Assert.assertTrue(jsonObject.containsKey("priceUSD"));
    //token object
    for (Object json:targetContent.getJSONArray("token")
    ) {
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
      Assert.assertTrue(jsonObject.containsKey("inMainChain"));
      Assert.assertTrue(jsonObject.containsKey("inSideChain"));
    }
  }

  @Test(enabled = true, description = "Api /TronlinkApiList/wallet/assetlist test")
  public void test001Assetlist() throws Exception {
    response = TronlinkApiList.assetlist(node,"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    targetContent = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonContent(targetContent);

    Assert.assertTrue(targetContent.getFloat("totalTRX") >= 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceUSD") > 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceCny") > 0);

    JSONArray tokenArray = targetContent.getJSONArray("token");
    Assert.assertTrue(tokenArray.size() >= 2);
  }


  @Test(enabled = true, description = "Api /TronlinkApiList/wallet/assetlist exception test")
  public void test002AssetlistException() throws Exception {
    //Base58 decode address can't get right information
    response = TronlinkApiList.assetlist(node,"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");

    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    targetContent = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonContent(targetContent);

    Assert.assertTrue(targetContent.getFloat("totalTRX") == 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceUSD") > 0);
    Assert.assertTrue(targetContent.getJSONObject("price").getDouble("priceCny") > 0);

  }

  
}
