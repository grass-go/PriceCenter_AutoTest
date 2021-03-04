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
public class TransferListBetweenTimeRange {

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
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions between time range")
  public void test01getTransferListBetweenTimeRange() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("start_timestamp", "1548000000000");
    params.put("end_timestamp", "1548057645667");

    response = TronscanApiList.getTransferList(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(responseArrayContent.size(), limit);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("data"));
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("block").toString()) >= 1000000);
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("transactionHash")).matches());

      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      //timestamp
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String transferFromAddress = responseArrayContent.getJSONObject(i).getString("transferFromAddress");
      Assert.assertTrue(
              patternAddress.matcher(transferFromAddress).matches() && !transferFromAddress.isEmpty());
      String transferToAddress = responseArrayContent.getJSONObject(i).getString("transferToAddress");
      Assert.assertTrue(
              patternAddress.matcher(transferToAddress).matches() && !transferToAddress.isEmpty());
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
