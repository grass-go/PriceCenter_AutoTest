package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class VoteWitnesses {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List all the votes info of the witnesses")
  public void test01getVoteWitnesses() {
    //Get response
    response = TronscanApiList.getVoteWitnesses(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //Three key,total/totalVotes/fastestRise/data
    Integer total = responseContent.getInteger("total");
    Long totalVotes = responseContent.getLong("totalVotes");
    Integer dataSize = responseContent.getJSONArray("data").size();
    Assert.assertEquals(total, dataSize);
    Assert.assertTrue(totalVotes > 0);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    //Key data
    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("hasPage").isEmpty());

      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("address")).matches());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("changeVotes"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("lastRanking"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("change_cycle"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("realTimeRanking"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("lastCycleVotes"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("realTimeVotes"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("votesPercentage"));
      //annualizedRate
      Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).get("annualizedRate").toString()) >= 0);
      //包含非url形式
      String url_key = responseArrayContent.getJSONObject(i).get("url").toString();
      Assert.assertTrue(!url_key.isEmpty());
    }
    //Key fastestRise
    targetContent = responseContent.getJSONObject("fastestRise");
    Assert.assertTrue(targetContent.containsKey("hasPage"));
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("changeVotes"));
    Assert.assertTrue(targetContent.containsKey("lastRanking"));
    Assert.assertTrue(targetContent.containsKey("change_cycle"));
    Assert.assertTrue(targetContent.containsKey("realTimeRanking"));
    Assert.assertTrue(targetContent.containsKey("lastCycleVotes"));
    Assert.assertTrue(targetContent.containsKey("realTimeVotes"));
    Assert.assertTrue(targetContent.getLong("lastCycleVotes") >= 0);
    Assert.assertTrue(targetContent.getLong("realTimeVotes") > 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("votesPercentage").toString()) <= 100);
    String url_key = targetContent.get("url").toString();
    Assert.assertTrue(!url_key.isEmpty());

  }

  /**
   * constructor.
   * 投票中无查询地址，则查询数据为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "地址的witness查询")
  public void getVoteWitness() {
    //Get response
    String address = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getVoteWitness(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //data object
    Assert.assertTrue(responseContent.size() == 2);
    //success
    //Assert.assertTrue(Boolean.valueOf(responseContent.getString("success")));
    Assert.assertTrue(responseContent.containsKey("success"));

    targetContent = responseContent.getJSONObject("data");
    //address
    Assert.assertTrue(targetContent.containsKey("address"));
    Assert.assertTrue(targetContent.containsKey("hasPage"));
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("changeVotes"));
    Assert.assertTrue(targetContent.containsKey("change_cycle"));
    //realTimeRanking
    Assert.assertTrue(targetContent.containsKey("realTimeRanking"));
    Assert.assertTrue(targetContent.containsKey("lastCycleVotes"));
    Assert.assertTrue(targetContent.containsKey("realTimeVotes"));
    Assert.assertTrue(targetContent.containsKey("votesPercentage"));
    Assert.assertTrue(targetContent.containsKey("url"));
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
