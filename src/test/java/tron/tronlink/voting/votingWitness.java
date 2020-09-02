package tron.tronlink.voting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;


public class votingWitness extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject fastestRise;
  private HttpResponse response;
  private String node = "list.tronlink.org";

  @Test(enabled = true,description = "get all witnesses")
  public void Test000getAllWitnesses() throws Exception {
    boolean re = TronlinkApiList.getAllWitnessFromTronscan();
    if(!re){
      System.out.println("* * * * * * * tronscan get witness error * * * * * *");
    }
    Map<String, String> params = new HashMap<>();
    params.put("sort_type","1");
    response = TronlinkApiList.votingV2Witness(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("lastRanking"));
      Assert.assertTrue(jsonObject.containsKey("ranking"));
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
      Assert.assertTrue(jsonObject.containsKey("producedTotal"));
      Assert.assertTrue(jsonObject.containsKey("annualized_income"));
      Assert.assertTrue(jsonObject.containsKey("totalVotes"));
    }
  }
}
