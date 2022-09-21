package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;

public class hot_token {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private JSONObject jsonObject;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);

  @Test(enabled = true)
  public void hot_token(){

    response = TronlinkApiList.hot_token("TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(targetContent.containsKey("totalTRX"));
    Assert.assertTrue(targetContent.containsKey("token"));
    jsonObject = targetContent.getJSONObject("price");
    Assert.assertTrue(jsonObject.containsKey("priceCny"));
    Assert.assertTrue(jsonObject.containsKey("priceUSD"));
  }
}
