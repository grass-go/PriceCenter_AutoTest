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
   * constructor.TRC&TRC10转账、TRC20转账
   * 转账总数等于两个接口total相加之和
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail() {
    //Get response
    //TRC&TRC10转账
    Map<String, String> params = new HashMap<>();
    String address = "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn";
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", "20");
    params.put("start", "0");
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
    Assert.assertTrue(responseArrayContent.size() > 0);
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

    //Get response
//    TRC20转账
    Map<String, String> params_TRC20 = new HashMap<>();
    params_TRC20.put("limit", "20");
    params_TRC20.put("start", "0");
    params_TRC20.put("relatedAddress", address);
    params_TRC20.put("sort","-timestamp");
    params_TRC20.put("count","true");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params_TRC20);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total_trc20 = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_trc20 = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_trc20 >= total_trc20);
    //token_transfers object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    Assert.assertTrue(responseArrayContent.size() > 0);
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
      String contract_address = responseArrayContent.getJSONObject(i).getString("contract_address");
      Assert.assertTrue(
              patternAddress.matcher(contract_address).matches() && !contract_address.isEmpty());
      Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("tokenId"),contract_address);

    }
    //转账总数
    Map<String, String> params_account = new HashMap<>();
    params_account.put("address", address);
    response = TronscanApiList.getAccountStats(tronScanNode, params_account);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() == 3);
    //wholeChainTxCount
    Assert.assertTrue(Long.valueOf(responseContent.get("transactions").toString()) >= 0);
    Long transactions = Long.valueOf(responseContent.get("transactions").toString());
    Long transactions_out = Long.valueOf(responseContent.get("transactions_out").toString());
    Long transactions_in = Long.valueOf(responseContent.get("transactions_in").toString());
    //判断转入+转出=转账总数；TRC20转账+TRX&TRC10=转账总数
    Assert.assertTrue((transactions_in + transactions_out) == transactions && transactions==(total_trc20+total));
  }

  /**
   * constructor.TRC&TRC10转账
   * 转入加上转出等于总数total--TRC10
   * 并且点最后一页，列表不为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void account_TRC10Tranfers() {
    //Get response
    //TRC&TRC10转账
    Map<String, String> params = new HashMap<>();
    String address = "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn";
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", "20");
    params.put("start", "0");
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
    Assert.assertTrue(responseArrayContent.size() > 0);
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
//TRC&TRC10转账-转入
    Map<String, String> params_in = new HashMap<>();
    params_in.put("sort", "-timestamp");
    params_in.put("count", "true");
    params_in.put("limit", "20");
    params_in.put("start", "0");
    params_in.put("address", address);
    params_in.put("direction", "in");
    response = TronscanApiList.getTransferList(tronScanNode, params_in);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total_in = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_in = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_in >= total_in);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertTrue(responseArrayContent.size() > 0);

//    TRC&TRC10转账-转出
    Map<String, String> params_out = new HashMap<>();
    params_out.put("sort", "-timestamp");
    params_out.put("count", "true");
    params_out.put("limit", "20");
    params_out.put("start", "0");
    params_out.put("address", address);
    params_out.put("direction", "out");
    response = TronscanApiList.getTransferList(tronScanNode, params_out);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total_out = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_out = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_out >= total_out);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertTrue(responseArrayContent.size() > 0);
    //总和
    Assert.assertTrue((total_out + total_in) == total );
//    判断最后一页不为0
    String start = String.valueOf(((int)Math.floor(total/20)*20));
    Map<String, String> params_lage = new HashMap<>();
    params_lage.put("sort", "-timestamp");
    params_lage.put("count", "true");
    params_lage.put("limit", "20");
    params_lage.put("start", start);
    params_lage.put("address", address);
    response = TronscanApiList.getTransferList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total_lage = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_lage = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_lage >= total_lage);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertTrue(responseArrayContent.size() > 0);

  }

  /**
   * constructor.TRC&TRC10转账
   * 转入加上转出等于总数total--TRC20
   * 并且点最后一页，列表不为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void account_TRC20Tranfers() {
    //Get response
    //TRC20转账
    Map<String, String> params_TRC20 = new HashMap<>();
    String address = "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn";
    params_TRC20.put("limit", "20");
    params_TRC20.put("start", "0");
    params_TRC20.put("relatedAddress", address);
    params_TRC20.put("sort","-timestamp");
    params_TRC20.put("count","true");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params_TRC20);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total_trc20 = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_trc20 = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_trc20 >= total_trc20);
    //token_transfers object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    Assert.assertTrue(responseArrayContent.size() > 0);
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
      String contract_address = responseArrayContent.getJSONObject(i).getString("contract_address");
      Assert.assertTrue(
              patternAddress.matcher(contract_address).matches() && !contract_address.isEmpty());
      Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("tokenId"),contract_address);

    }
//TRC&TRC10转账-转入
    Map<String, String> params_in = new HashMap<>();
    params_in.put("sort", "-timestamp");
    params_in.put("count", "true");
    params_in.put("limit", "20");
    params_in.put("start", "0");
    params_in.put("relatedAddress", address);
    params_in.put("direction", "in");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params_in);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total_in = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_in = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_in >= total_in);
    //data object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    Assert.assertTrue(responseArrayContent.size() > 0);

//    TRC&TRC10转账-转出
    Map<String, String> params_out = new HashMap<>();
    params_out.put("sort", "-timestamp");
    params_out.put("count", "true");
    params_out.put("limit", "20");
    params_out.put("start", "0");
    params_out.put("relatedAddress", address);
    params_out.put("direction", "out");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params_out);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total_out = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_out = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_out >= total_out);
    //data object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    Assert.assertTrue(responseArrayContent.size() > 0);
    //总和
    Assert.assertTrue((total_out + total_in) == total_trc20 );
//    判断最后一页不为0
    String start = String.valueOf(((int)Math.floor(total_trc20/20)*20));
    Map<String, String> params_lage = new HashMap<>();
    params_lage.put("sort", "-timestamp");
    params_lage.put("count", "true");
    params_lage.put("limit", "20");
    params_lage.put("start", start);
    params_lage.put("relatedAddress", address);
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params_lage);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Long total_lage = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal_lage = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal_lage >= total_lage);
    //data object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    Assert.assertTrue(responseArrayContent.size() > 0);

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
