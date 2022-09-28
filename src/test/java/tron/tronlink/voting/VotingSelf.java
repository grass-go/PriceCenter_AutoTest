package tron.tronlink.voting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VotingSelf extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;

  @Test(enabled = true,description = "get self voting witness", groups={"NoSignature"})
  public void getVotingSelfLowVersionWithNoSig() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"); //sophia's address
    response = TronlinkApiList.votingV2SelfNoSig(params,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
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

  @Test(enabled = true,description = "get self voting witness")
  public void getVotingSelfHighVersionWithNoSig() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("address",quince_B58);
    Map<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = TronlinkApiList.votingV2SelfNoSig(params, header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true,description = "get self voting witness")
  public void Test000getVotingSelf() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("start","0");
    map.put("limit","0");
    map.put("voter","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
    boolean re=false;
    int count = 0;
    while (count < 5) {
      try {
        re = TronlinkApiList.getVoteSelfFromTronscan(map);
        break;
      } catch (Exception e){
        Thread.sleep(60000);
        count +=1;
      }
    }
    if(!re){
      System.out.println("* * * * * * * tronscan get vote self total * * * * * *");
    }

    Map<String, String> params = new HashMap<>();
    params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"); //sophia's address
    response = TronlinkApiList.votingV2Self(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
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

  //In old version, wrong address still can get witness.
  @Test(enabled = true,description = "get self vote with wrong address")
  public void Test001getVotingSelf() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("address","TXTNcgJHD9GPii9VcpEr"); //sophia's address
    response = TronlinkApiList.votingV2SelfNoSig(params,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertEquals(0,responseContent.getLongValue("total"));
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(2,responseArrayContent.size());

    params.clear();
    params.put("sort_type","1");
    params.put("has_all","0");
    params.put("page_size","5");
    params.put("page_num","1");
    params.put("address", quince_B58);
    response = TronlinkApiList.votingWitness(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONArray responseArrayContent1 = responseContent.getJSONArray("data");
    List<String> list = new ArrayList<>();
    list.add(responseArrayContent1.getJSONObject(0).getString("address"));
    list.add(responseArrayContent1.getJSONObject(1).getString("address"));

    String address = "";
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("voterAddress"));
      Assert.assertTrue(jsonObject.containsKey("candidateAddress"));
      address = jsonObject.getString("candidateAddress");
      Assert.assertTrue(list.contains(address));
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
