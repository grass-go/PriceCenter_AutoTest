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
public class FundsInfo {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject proposalContent;
  private JSONArray exchangeArray;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void test01FundsInfo() {
    //Get response
    response = TronscanApiList.getFundsInfo(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(Long.valueOf(responseContent.get("totalDonateBalance").toString()) > 1000000);
    Assert.assertTrue(Long.valueOf(responseContent.get("totalFundBalance").toString()) > 1000000);
    Assert.assertTrue(Long.valueOf(responseContent.get("genesisBlockIssue").toString()) > 1000000);
    Assert.assertTrue(Long.valueOf(responseContent.get("totalBlockPay").toString()) > 1000000);
    Assert.assertTrue(Long.valueOf(responseContent.get("totalNodePay").toString()) > 1000000);
    Assert.assertTrue(Long.valueOf(responseContent.get("burnPerDay").toString()) > 1000000);

    Assert.assertTrue(Double.valueOf(responseContent.get("burnByCharge").toString()) > 1000000);
    Assert.assertTrue(Double.valueOf(responseContent.get("totalTurnOver").toString()) > 1000000);
    Assert.assertTrue(Double.valueOf(responseContent.get("fundTrx").toString()) > 1000000);
    Assert.assertTrue(Double.valueOf(responseContent.get("turnOver").toString()) > 1000000);
    Assert.assertTrue(Long.valueOf(responseContent.get("fundSumBalance").toString()) > 1000000);
    Assert.assertTrue(Long.valueOf(responseContent.get("donateBalance").toString()) > 1000000);
  }

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List all the foundation addresses ")
  public void getFundTest() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("page_index", "1");
    Params.put("per_page", "20");
    response = TronscanApiList.getFundTest(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(Integer.valueOf(responseContent.get("page_index").toString()) >= 1);
    Assert.assertTrue(responseContent.containsKey("message"));

    //data json
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(Long.valueOf(targetContent.get("total").toString()) > 1000000);
    //data Contain data json
    JSONArray dataArray = targetContent.getJSONArray("data");
    targetContent = dataArray.getJSONObject(0);
    //address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
    //balance
    Assert.assertTrue(Long.valueOf(targetContent.get("balance").toString()) > 1000000000);
    Assert.assertTrue(Long.valueOf(targetContent.get("key").toString()) >= 1);

  }

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "获取Bittorrent的流通量和市值")
  public void getBitt_fund() {
    response = TronscanApiList.getBitt_fund(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert
        .assertTrue(Double.valueOf(responseContent.get("totalTurnOver").toString()) >= 1000000000);
    Assert.assertTrue(Double.valueOf(responseContent.get("marketValue").toString()) >= 10000000);
  }


  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "获取bittorrent代笔解锁时间表图")
  public void getBitt_graphic() {
    response = TronscanApiList.getBitt_graphic(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseArrayContent = TronscanApiList.parseArrayResponseContent(response);
    //list
    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("data").isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("name").isEmpty());
    }
  }


  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "获取wink的流通量和市值")
  public void getWinkFund() {
    response = TronscanApiList.getWinkFund(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert
        .assertTrue(Double.valueOf(responseContent.get("totalTurnOver").toString()) >= 1000000000);
    Assert.assertTrue(Double.valueOf(responseContent.get("marketValue").toString()) >= 10000000);
  }

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "获取wink代笔解锁时间表图")
  public void getWink_graphic() {
    response = TronscanApiList.getWink_graphic(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseArrayContent = TronscanApiList.parseArrayResponseContent(response);
    //list
    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("data").isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("name").isEmpty());
    }
  }

  @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class, description = "基金会地址接口")
  public void totalFundBalance() {
    //Get response
    response = TronscanApiList.getFundsInfo(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("genesisBlockIssue"));
    Assert.assertTrue(responseContent.containsKey("totalBlockPay"));
    Assert.assertTrue(responseContent.containsKey("totalNodePay"));
    Assert.assertTrue(responseContent.containsKey("burnPerDay"));
    Assert.assertTrue(responseContent.containsKey("burnByCharge"));
    Assert.assertTrue(responseContent.containsKey("totalTurnOver"));
    Assert.assertTrue(responseContent.containsKey("fundSumBalance"));
    Assert.assertTrue(responseContent.containsKey("donateBalance"));
    Assert.assertTrue(responseContent.containsKey("fundTrx"));
    Assert.assertTrue(responseContent.containsKey("turnOver"));
    Assert.assertTrue(responseContent.getString("totalFundBalance") == "33177933703065630");
  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
