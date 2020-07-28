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


  @Test(enabled = true,description = "get self voting witness")
  public void Test000getVotingSelf() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("address","TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr"); //sophia's address
    response = TronlinkApiList.votingV2Self(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    System.out.println("========");
    System.out.println(responseContent.toJSONString());
    System.out.println("========");
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getInteger("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getInteger("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("voterAddress"));
      Assert.assertTrue(jsonObject.containsKey("candidateAddress"));
      Assert.assertTrue(jsonObject.containsKey("votes"));
      Assert.assertTrue(jsonObject.containsKey("candidateUrl"));
      Assert.assertTrue(jsonObject.containsKey("candidateName"));
      Assert.assertTrue(jsonObject.containsKey("candidateTotalVotes"));
      Assert.assertTrue(jsonObject.containsKey("voterAvailableVotes"));
      Assert.assertTrue(jsonObject.containsKey("annualized_income"));
      Assert.assertTrue(jsonObject.containsKey("brokerage"));
      Assert.assertTrue(jsonObject.containsKey("totalVotes"));
      Assert.assertTrue(jsonObject.containsKey("votesPercentage"));
      Assert.assertTrue(jsonObject.containsKey("producedTotal"));
    }
  }
}
