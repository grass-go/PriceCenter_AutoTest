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
public class TransactionList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject sonContent;
  private JSONObject proposalContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor. limit不为零
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-number");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("address", "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9");
    response = TronscanApiList.getTransactionList(tronScanNode, Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 1000000000);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
      String hash_key = responseArrayContent.getJSONObject(i).getString("hash");
      Assert.assertEquals(hash_key.length(), 64);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("block") >= 10000000);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("timestamp").isEmpty());

      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("contractType").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String ownerAddress = responseArrayContent.getJSONObject(i).getString("ownerAddress");
      Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("toAddress"));
      //contractData
      JSONObject responseObject = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
      Assert.assertEquals(responseObject.getString("owner_address"),ownerAddress);
      Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
    }
  }
  /**
   * constructor. limit为零
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test02getBlockDetail() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-number");
    Params.put("limit", "0");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("address", "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9");
    response = TronscanApiList.getTransactionList(tronScanNode, Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 5);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    Assert.assertTrue(responseContent.containsKey("data"));
    Assert.assertTrue(responseContent.containsKey("wholeChainTxCount"));
    Assert.assertTrue(responseContent.containsKey("contractMap"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List a single the exchange pair's transaction records ")
  public void getTransactionTest() {
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    response = TronscanApiList.getTransactionTest(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    Assert.assertTrue(responseContent.size() >= 3);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //exchangeID
      Assert.assertTrue(Integer.valueOf(exchangeArray.getJSONObject(i).get("exchangeID").toString()) >= 1);
      //blockID
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("blockID").toString()) >= 10000000);
      //tokenID
      Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("tokenID").isEmpty());
      //createTime
      Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("createTime").isEmpty());
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("trx_hash")).matches());
      //quant
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("quant"));
      //address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("creatorAddress")).matches());
      //confirmed
      Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
    }
  }

  /**
   * constructor. limit不为零
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions in the blockchain(only display the latest 10,000 data records in the query time range)")
  public void getTransactionTestRang() {
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("start_timestamp", "1548000000000");
    Params.put("end_timestamp", "1548056638507");
    response = TronscanApiList.getTransactionList(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    Assert.assertTrue(responseContent.size() >= 0);
    Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) > 1000000000);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //contractRet
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("contractRet"));
      //data
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("data"));
      //contractRet
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("contractType").toString().isEmpty());
      //fee
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("fee"));
      //toAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String ownerAddress = exchangeArray.getJSONObject(i).getString("ownerAddress");
      Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
      //confirmed
      Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("Events"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("SmartCalls"));
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) >= 1000000);
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("id"));
      //timestamp
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
      //contractData json
      proposalContent = exchangeArray.getJSONObject(i).getJSONObject("contractData");
      Assert.assertEquals(proposalContent.getString("owner_address"),ownerAddress);

    }

  }

  /**
   * constructor.查询区块上交易列表 limit不为零
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询区块上交易列表")
  public void getTransactionTestBlock() {
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("total", "0");
    Params.put("block", "12448572");
    response = TronscanApiList.getTransactionList(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    Assert.assertTrue(responseContent.size() == 5);
    Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);

    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //contractRet
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("contractRet"));
      //cost json
      proposalContent = exchangeArray.getJSONObject(i).getJSONObject("cost");
      //net_fee
      Assert.assertTrue(Long.valueOf(proposalContent.get("net_fee").toString()) >= 0);
      //energy_usage
      Assert.assertTrue(Long.valueOf(proposalContent.get("energy_usage").toString()) >= 0);
      //energy_fee
      Assert.assertTrue(Long.valueOf(proposalContent.get("energy_fee").toString()) >= 0);
      //energy_usage_total
      Assert.assertTrue(Long.valueOf(proposalContent.get("energy_usage_total").toString()) >= 0);
      //origin_energy_usage
      Assert.assertTrue(Long.valueOf(proposalContent.get("origin_energy_usage").toString()) >= 0);
      //net_usage
      Assert.assertTrue(Long.valueOf(proposalContent.get("net_usage").toString()) >= 0);
      //data
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("data"));
      //contractRet
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("contractType").toString().isEmpty());
      //fee
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("fee"));
      //toAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String ownerAddress = exchangeArray.getJSONObject(i).getString("ownerAddress");
      Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
      //confirmed
      Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("Events"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("SmartCalls"));
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) >= 1000000);
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("id"));
      //timestamp
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
      //contractData json
      proposalContent = exchangeArray.getJSONObject(i).getJSONObject("contractData");
      Assert.assertEquals(proposalContent.getString("owner_address"),ownerAddress);

    }
  }


  /**
   * constructor.simple-transaction接口
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions under specified conditions;")
  public void getSimple_Transaction() {
    response = TronscanApiList.getSimple_Transaction(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total_votes
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {

      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("hash")).matches());
      //timestamp
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      //ownerAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String ownerAddress = responseArrayContent.getJSONObject(i).getString("ownerAddress");
      Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
      Assert.assertTrue(
          patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("toAddress")).matches());
      //contractType
      Assert.assertTrue(
          !responseArrayContent.getJSONObject(i).get("contractType").toString().isEmpty());
      //contractData json
      proposalContent = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
      Assert.assertEquals(proposalContent.getString("owner_address"),ownerAddress);
    }

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void testStatistics(){
    response = TronscanApiList.statistics();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getLong("lastDayTxCount") > 0);
    Assert.assertTrue(responseContent.getLong("lastDayTxAmount") > 0);
    Assert.assertTrue(responseContent.getLong("txCount") > 0);
    Assert.assertTrue(responseContent.getLong("txAmount") > 0);
    Assert.assertTrue(responseContent.getLong("triggerProportion") > 0);
    Assert.assertTrue(responseContent.getLong("triggerAmount") > 0);
    Assert.assertTrue(responseContent.getLong("triggerProportion") > 0);
    Assert.assertTrue(responseContent.getLong("trc20Count") > 0);
    Assert.assertTrue(responseContent.getLong("trc20Amount") > 0);
    Assert.assertTrue(responseContent.getLong("trc20Proportion") > 0);
    Assert.assertTrue(responseContent.getLong("trxTransferCount") > 0);
    Assert.assertTrue(responseContent.getLong("trxTransferProportion") > 0);
    Assert.assertTrue(responseContent.getLong("trc10TransferCount") > 0);
    Assert.assertTrue(responseContent.getLong("trc10TransferCount") > 0);
    Assert.assertTrue(responseContent.getLong("trc10TransferProportion") > 0);
    Assert.assertTrue(responseContent.getLong("votesCount") > 0);
    Assert.assertTrue(responseContent.getLong("votesAmount") > 0);
    Assert.assertTrue(responseContent.getDouble("votesProportion") > 0);
    Assert.assertTrue(responseContent.getLong("freezeBalanceCount") > 0);
    Assert.assertTrue(responseContent.getLong("freezeBalanceAmount") > 0);
    Assert.assertTrue(responseContent.getDouble("freezeBalanceProportion") > 0);
    Assert.assertTrue(responseContent.getLong("othersCount") > 0);
    Assert.assertTrue(responseContent.getLong("othersAmount") > 0);
    Assert.assertTrue(responseContent.getDouble("othersProportion") > 0);


  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
