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
public class ContractTriggerList {

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
   * 合约调用展示接口
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = " List all the triggers of the contracts in the blockchain")
  public void test01getContractTrigger() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("start_timestamp", "1548000000000");
    params.put("end_timestamp", "1548060167540");
    response = TronscanApiList.getContractTrigger(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);

    //object data
    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.size() == 20);
      Assert.assertTrue(Integer.valueOf(responseArrayContent.getJSONObject(i).getString("block")) > 0);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("callData"));
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("callValue")) >= 0);

      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("contractType"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("token"));
      Assert.assertTrue(Boolean.valueOf(responseArrayContent.getJSONObject(i).getString("confirmed")));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      //地址不能唯空
      String contractAddress = responseArrayContent.getJSONObject(i).getString("contractAddress");
      Assert.assertTrue(patternAddress.matcher(contractAddress).matches() && !contractAddress.isEmpty());
      String ownerAddress = responseArrayContent.getJSONObject(i).getString("ownerAddress");
      Assert.assertTrue(patternAddress.matcher(ownerAddress).matches() && !ownerAddress.isEmpty());

      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      String hash = responseArrayContent.getJSONObject(i).getString("hash");
      Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("timestamp").isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("result").isEmpty());
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
