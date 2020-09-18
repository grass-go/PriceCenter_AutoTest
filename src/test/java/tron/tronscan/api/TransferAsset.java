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
public class TransferAsset {

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
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail() {
    //Get response
    String address = "TSbhZijH2t7Qn1UAHAu7PBHQdVAvRwSyYr";
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("name", "IGG");
    params.put("issueAddress", address);
    params.put("start_timestamp", "1529856000000");
    params.put("end_timestamp", "1552549912537");
    response = TronscanApiList.getAssetTransferList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data object
    responseArrayContent = responseContent.getJSONArray("Data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("blockId").toString()) >= 1000000);
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("tokenName").toString()) >= 1000000);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("transactionHash")).matches());
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(
              patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("transferFromAddress")).matches());
      Assert.assertTrue(
              patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("transferToAddress")).matches());
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
