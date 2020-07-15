package tron.tronlink.voting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;
import java.util.Map;


public class VotingSelf {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject fastestRise;
  private HttpResponse response;


  @Test(enabled = false,description = "get self voting witness")
  public void Test000getVotingSelf() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6");

    response = TronlinkApiList.votingV2Self(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getInteger("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getInteger("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("fastestRise"));
    fastestRise = responseContent.getJSONObject("fastestRise");
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("lastRanking"));
      Assert.assertTrue(jsonObject.containsKey("realTimeRanking"));
      Assert.assertTrue(jsonObject.containsKey("address"));
      Assert.assertTrue(jsonObject.containsKey("name"));
      Assert.assertTrue(jsonObject.containsKey("url"));
      Assert.assertTrue(jsonObject.containsKey("hasPage"));
      Assert.assertTrue(jsonObject.containsKey("lastCycleVotes"));
      Assert.assertTrue(jsonObject.containsKey("realTimeVotes"));
      Assert.assertTrue(jsonObject.containsKey("changeVotes"));
      Assert.assertTrue(jsonObject.containsKey("brokerage"));
      Assert.assertTrue(jsonObject.containsKey("votesPercentage"));
      Assert.assertTrue(jsonObject.containsKey("change_cycle"));
    }
  }
}
