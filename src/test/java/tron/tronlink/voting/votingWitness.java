package tron.tronlink.voting;

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
  private HttpResponse response;

  @Test(enabled = true,description = "get 20 witnesses order by AnnualizedIncome desc")
  public void Test000getAllWitnesses() throws Exception {
    boolean re = TronlinkApiList.getAllWitnessFromTronscan();
    if(!re){
      System.out.println("* * * * * * * tronscan get witness error * * * * * *");
    }
    Map<String, String> params = new HashMap<>();
    params.put("sort_type","1");
    params.put("has_all","0");
    response = TronlinkApiList.votingV2Witness(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(20,responseArrayContent.size());

    for(int i=0;i<responseArrayContent.size();i++){
      if(i+1==responseArrayContent.size()){
        break;
      }
      JSONObject jsonObject = responseArrayContent.getJSONObject(i);
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
      String k = "AnnualizedIncome"; //AnnualizedIncome realTimeVotes
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getDoubleValue(k)>=responseArrayContent.getJSONObject(i+1).getDoubleValue(k));
    }
  }

  @Test(enabled = true,description = "get 20 witnesses order by AnnualizedIncome asc")
  public void Test001getAllWitnesses() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("sort_type","2");
    params.put("has_all","0");
    response = TronlinkApiList.votingV2Witness(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(20,responseArrayContent.size());

    for(int i=0;i<responseArrayContent.size();i++){
      if(i+1==responseArrayContent.size()){
        break;
      }
      JSONObject jsonObject = responseArrayContent.getJSONObject(i);
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
      String k = "AnnualizedIncome"; //AnnualizedIncome realTimeVotes
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getDoubleValue(k)<=responseArrayContent.getJSONObject(i+1).getDoubleValue(k));
    }
  }

  @Test(enabled = true,description = "get 20 witnesses order by realTimeVotes desc")
  public void Test002getAllWitnesses() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("sort_type","3");
    params.put("has_all","0");
    response = TronlinkApiList.votingV2Witness(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(20,responseArrayContent.size());

    for(int i=0;i<responseArrayContent.size();i++){
      if(i+1==responseArrayContent.size()){
        break;
      }
      JSONObject jsonObject = responseArrayContent.getJSONObject(i);
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
      String k = "realTimeVotes"; //AnnualizedIncome realTimeVotes
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getDoubleValue(k)>=responseArrayContent.getJSONObject(i+1).getDoubleValue(k));
    }
  }

  @Test(enabled = true,description = "get 20 witnesses order by realTimeVotes asc")
  public void Test003getAllWitnesses() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("sort_type","4");
    params.put("has_all","0");
    response = TronlinkApiList.votingV2Witness(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(20,responseArrayContent.size());

    for(int i=0;i<responseArrayContent.size();i++){
      if(i+1==responseArrayContent.size()){
        break;
      }
      JSONObject jsonObject = responseArrayContent.getJSONObject(i);
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
      String k = "realTimeVotes"; //AnnualizedIncome realTimeVotes
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getDoubleValue(k)<=responseArrayContent.getJSONObject(i+1).getDoubleValue(k));
    }
  }

  @Test(enabled = true,description = "get 21-40 witnesses order by AnnualizedIncome desc ")
  public void Test004getAllWitnesses() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("sort_type","1");
    params.put("has_all","0");
    params.put("page_size","20");
    params.put("page_num","2");

    response = TronlinkApiList.votingV2Witness(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.getLongValue("total")>0);
    Assert.assertTrue(responseContent.containsKey("totalVotes"));
    Assert.assertTrue(responseContent.getLongValue("totalVotes")>0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(20,responseArrayContent.size());

    for(int i=0;i<responseArrayContent.size();i++){
      if(i+1==responseArrayContent.size()){
        break;
      }
      JSONObject jsonObject = responseArrayContent.getJSONObject(i);
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
      String k = "AnnualizedIncome"; //AnnualizedIncome realTimeVotes
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getDoubleValue(k)>=responseArrayContent.getJSONObject(i+1).getDoubleValue(k));
    }
  }

  @Test(enabled = true,description = "get all witnesses order by AnnualizedIncome desc")
  public void Test005getAllWitnesses() throws Exception {
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
    Assert.assertTrue(responseArrayContent.size()>20);

    for(int i=0;i<responseArrayContent.size();i++){
      if(i+1==responseArrayContent.size()){
        break;
      }
      JSONObject jsonObject = responseArrayContent.getJSONObject(i);
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
      String k = "AnnualizedIncome"; //AnnualizedIncome realTimeVotes
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getDoubleValue(k)>=responseArrayContent.getJSONObject(i+1).getDoubleValue(k));
    }
  }
}
