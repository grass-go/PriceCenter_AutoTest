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
public class TransfersToken_trc20 {

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
    String address = "TCN77KWWyUyi2A4Cu7vrh5dnmRyvUuME1E";
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("contract_address", address);
    params.put("start_timestamp", "1529856000000");
    params.put("end_timestamp", "1552549912537");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("transaction_id")).matches());

      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("block_ts"));
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("block").toString()) >= 1000000);

      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("quant"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("contractRet"));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String from_address = responseArrayContent.getJSONObject(i).getString("from_address");
      Assert.assertTrue(
              patternAddress.matcher(from_address).matches() && !from_address.isEmpty());
      String to_address = responseArrayContent.getJSONObject(i).getString("to_address");
      Assert.assertTrue(
              patternAddress.matcher(to_address).matches() && !to_address.isEmpty());
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
