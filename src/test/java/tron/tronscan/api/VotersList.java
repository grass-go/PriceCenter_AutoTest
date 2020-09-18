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
public class VotersList {

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
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = " List all the trc10 tokens in the blockchain")
  public void test01getTokensList() {
    //Get response
    Map<String, String> params = new HashMap<>();
    int limit = 20;
    String address = "TGzz8gjYiYRqpfmDwnLxfgPuLVNmpCswVp";
    params.put("sort", "-votes");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("candidate", address);
    response = TronscanApiList.getVotersList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalVotes = Long.valueOf(responseContent.get("totalVotes").toString());
    Assert.assertTrue(totalVotes >= total);
    //object data
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(limit, responseArrayContent.size());
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      String candidateUrl = responseArrayContent.getJSONObject(i).get("candidateUrl").toString();
      Assert.assertTrue(!candidateUrl.isEmpty());
      HttpResponse httpResponse = TronscanApiList.getUrlkey(candidateUrl);
      Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);

      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("candidateName").toString().isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("voterAvailableVotes"));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("voterAddress")).matches());
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("candidateAddress")).matches());
      Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("candidateAddress"),address);
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("votes")) > 0);
    }
  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
