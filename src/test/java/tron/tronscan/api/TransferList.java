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
public class TransferList {

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
    Map<String, String> params = new HashMap<>();
    String address = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", "20");
    params.put("start", "0");
    params.put("token", "_");
    params.put("address", address);
    response = TronscanApiList.getTransferList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
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
   * constructor.获取simple-transfer方法
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transfers under specified condition")
  public void getSimple_transfer() {
    //Get response
    Map<String, String> params = new HashMap<>();
    String to = "THzuXNFiDe4jBGiVRpRLxCf4u3WWxgrUZE";
    String from = "TXYeahu7J6Hr7X33XFRaHgyznvun578jPm";
    params.put("sort", "-timestamp");
    params.put("asset_name", "trx");
    params.put("to", to);
    params.put("from", from);
    params.put("end_timestamp", "1548056638507");
    params.put("start_timestamp", "1548000000000");
    response = TronscanApiList.getSimple_transfer(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //amount
      Assert.assertTrue(
          Double.valueOf(responseArrayContent.getJSONObject(i).get("amount").toString()) > 0);
      //tokenName
      Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("tokenName"),"trx");
      //timestamp
      Assert
          .assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      //transferFromAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("transferFromAddress"),from);
      String transferToAddress = responseArrayContent.getJSONObject(i).getString("transferToAddress");
      Assert.assertTrue(
              patternAddress.matcher(transferToAddress).matches() && !transferToAddress.isEmpty());
      //id
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("id"));
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("transactionHash")).matches());
      //confirmed
      Assert.assertTrue(
          Boolean.valueOf(responseArrayContent.getJSONObject(i).getString("confirmed")));
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
