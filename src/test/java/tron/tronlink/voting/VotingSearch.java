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


public class VotingSearch extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;


  @Test(enabled = true,description = "search witness by keywords")
  public void Test000SearchWitness() throws Exception {
//    boolean re = TronlinkApiList.getAllWitnessFromTronscan();
//    if(!re){
//      System.out.println("* * * * * * * tronscan get witness error * * * * * *");
//    }
    Map<String, String> params = new HashMap<>();
    params.put("keyword","TK");
    response = TronlinkApiList.votingV2Search(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertTrue(responseContent.containsKey("data"));
    JSONObject ob = responseContent.getJSONObject("data");
    Assert.assertTrue(ob.containsKey("total"));
    Assert.assertTrue(ob.getInteger("total")>0);
    Assert.assertTrue(ob.containsKey("totalVotes"));
    Assert.assertTrue(ob.getLongValue("totalVotes")>0);
    Assert.assertTrue(ob.containsKey("data"));
    responseArrayContent = ob.getJSONArray("data");

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
