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
public class SingleProposalList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor
   * 委员会提议
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List a single proposal detail")
  public void test01getSingleProposalList() {
    //Get response
    Map<String, String> params = new HashMap<>();
    String id = "16";
    params.put("id", id);
    response = TronscanApiList.getSingleProposalList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertEquals(responseContent.getString("proposalId"),id);
    Assert.assertEquals(responseContent.getString("expirationTime"),"1547704800000");
    Assert.assertEquals(responseContent.getString("createTime"),"1547443404000");

    //approvals array
    responseArrayContent = responseContent.getJSONArray("approvals");
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //object approval
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("address")).matches());
      //name
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("name").toString().isEmpty());
      //url
      String url_key = responseArrayContent.getJSONObject(i).get("url").toString();
      Assert.assertTrue(!url_key.isEmpty());
      if (url_key.substring(0, 7)=="http://") {
        HttpResponse httpResponse = TronscanApiList.getUrlkey(url_key);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      } else {
        Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("url"));
      }
      //丢块最低为0
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getLong("missedTotal").toString()) >= 0);
      //产块最低为0
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getLong("producedTotal").toString()) >= 0);
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("producedTrx").toString()) == 0);
      Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("producePercentage")) <= 100);
      Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("votesPercentage")) <= 100);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("latestBlockNumber").isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("latestSlotNumber").isEmpty());
    }
    //object proposer
    targetContent = responseContent.getJSONObject("proposer");
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    //url
    String url_key = targetContent.get("url").toString();
    Assert.assertTrue(!url_key.isEmpty());
    if (url_key.substring(0, 7)=="http://") {
      HttpResponse httpResponse = TronscanApiList.getUrlkey(url_key);
      Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
    } else {
      Assert.assertTrue(targetContent.containsKey("url"));
    }
    Assert.assertTrue(Long.valueOf(targetContent.getLong("missedTotal").toString()) >= 0);
    Assert.assertTrue(Long.valueOf(targetContent.getLong("producedTotal").toString()) >= 0);
    Assert.assertTrue(Long.valueOf(targetContent.get("producedTrx").toString()) == 0);
    Assert.assertTrue(Long.valueOf(targetContent.get("votes").toString()) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.getString("producePercentage")) <= 100);
    Assert.assertTrue(Double.valueOf(targetContent.getString("votesPercentage")) <= 100);
    Assert.assertTrue(!targetContent.getString("latestBlockNumber").isEmpty());
    Assert.assertTrue(!targetContent.getString("latestSlotNumber").isEmpty());
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
