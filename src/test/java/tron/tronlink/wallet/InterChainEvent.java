package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class InterChainEvent {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true)
  public void Test000getInterChainEvent() throws Exception {
    param.put("address","TFDP1vFeSYPT6FUznL7zUjhg5X7p2AA8vw");
    param.put("contractAddress","TWaPZru6PR5VjgT4sJrrZ481Zgp3iJ8Rfo");
    param.put("limit","40");
    param.put("start","0");
    param.put("tokenAddress","_");
    param.put("type","deposit");
    response = TronlinkApiList.getInterChainEvent(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
     responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("amount"));
      Assert.assertTrue(jsonObject.containsKey("address"));
      Assert.assertTrue(jsonObject.containsKey("block"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("eventType"));
      Assert.assertTrue(jsonObject.containsKey("contract_address"));
      Assert.assertTrue(jsonObject.containsKey("nonce"));
      Assert.assertTrue(jsonObject.containsKey("transactionHash"));
      Assert.assertTrue(jsonObject.containsKey("timestamp"));
    }
  }
}
