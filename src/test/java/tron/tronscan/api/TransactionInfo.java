package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class TransactionInfo {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private String transactionHash = Configuration.getByPath("testng.conf")
      .getString("defaultParameter.transactionHash");

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Query transaction hash and get a transaction detail info;")
  public void test01getTransactionInfo() {
    //Get response
    response = TronscanApiList.getTransactionInfo(tronScanNode,transactionHash);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //13 key
    Assert.assertTrue(responseContent.size() > 10);
    Assert.assertTrue(!responseContent.get("contractRet").toString().isEmpty());
    Assert.assertTrue(!responseContent.getJSONObject("cost").isEmpty());
    Assert.assertTrue(responseContent.containsKey("data"));
    Assert.assertTrue(responseContent.getInteger("contractType") > 0);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseContent.getString("toAddress")).matches());
    Assert.assertTrue(responseContent.getBoolean("confirmed"));
    targetContent = responseContent.getJSONObject("trigger_info");
    Assert.assertTrue(!targetContent.getString("method").isEmpty());
    Assert.assertTrue(targetContent.containsKey("parameter"));
    Assert.assertTrue(targetContent.getInteger("call_value") >= 10000000);
    Assert.assertTrue(responseContent.getLong("block") >= 1000000);
    Assert.assertTrue(patternAddress.matcher(responseContent.getString("ownerAddress")).matches());
    Assert.assertEquals(transactionHash,responseContent.getString("hash"));
    Assert.assertTrue(responseContent.containsKey("contractData"));
    Assert.assertTrue(!responseContent.getString("timestamp").isEmpty());
    Assert.assertTrue(!responseContent.getString("internal_transactions").isEmpty());


  }

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Query transaction hash and get a transaction detail info;")
  public void getAccount_proposal() {
    //Get response
    response = TronscanApiList.getAccount_proposal(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //2 key
    Assert.assertTrue(responseContent.size() > 0);
    Assert.assertTrue(Long.valueOf(responseContent.get("total").toString()) >= 0);
    Assert.assertTrue(responseContent.containsKey("data"));

  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
