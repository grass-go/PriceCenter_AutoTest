package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class MarketBanner {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true)
  public void Test000getMarketBanner() throws Exception {
    response = TronlinkApiList.walletMarketBanner();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("title"));
      Assert.assertTrue(jsonObject.containsKey("icon"));
      Assert.assertTrue(jsonObject.containsKey("home_url"));
      Assert.assertTrue(jsonObject.containsKey("type"));
      Assert.assertTrue(jsonObject.containsKey("vote_reward"));
      Assert.assertTrue(jsonObject.containsKey("button_text"));
      Assert.assertTrue(jsonObject.containsKey("introduction"));
      Assert.assertTrue(jsonObject.containsKey("yield"));
      Assert.assertTrue(jsonObject.containsKey("lang"));
      Assert.assertTrue(jsonObject.containsKey("created_at"));
      Assert.assertTrue(jsonObject.containsKey("updated_at"));
    }
  }
}
