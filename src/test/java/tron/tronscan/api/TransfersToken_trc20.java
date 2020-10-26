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
  private String contractAddress = Configuration.getByPath("testng.conf")
          .getString("defaultParameter.contractAddress");

  /**
   * constructor.
   * 合约下的TRC20转账
   * 未筛选查询
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //token_transfers object
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
      String contract_address = responseArrayContent.getJSONObject(i).getString("contract_address");
      Assert.assertTrue(
              patternAddress.matcher(contract_address).matches() && !contract_address.isEmpty());
      Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("tokenId"),contract_address);

    }
  }

  /**
   * constructor.
   * 合约下的TRC20转账
   * 按hash值搜索筛选查询
   * 当前hash是当前账户地址转账页面中的hash数据，查询出来的数据有且只有一条
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail02() {
    //Get response
    //给出转账页中的hash值
    String keyword = "ca45b38ef725acdec25d07f00e10f1d0811fb7d407a0f22dce109a41dbb56f54";
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    //转账页中的hash值，有且只有一条数据
    params.put("keyword",keyword);
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //token_transfers object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    targetContent = responseArrayContent.getJSONObject(0);
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    Assert.assertTrue(patternHash.matcher(targetContent.getString("transaction_id")).matches());

    Assert.assertTrue(targetContent.containsKey("block_ts"));
    Assert.assertTrue(Long.valueOf(targetContent.get("block").toString()) >= 1000000);

    Assert.assertTrue(targetContent.containsKey("quant"));
    Assert.assertTrue(targetContent.containsKey("confirmed"));
    Assert.assertTrue(targetContent.containsKey("contractRet"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    String from_address = targetContent.getString("from_address");
    Assert.assertTrue(
            patternAddress.matcher(from_address).matches() && !from_address.isEmpty());
    String to_address = targetContent.getString("to_address");
    Assert.assertTrue(
            patternAddress.matcher(to_address).matches() && !to_address.isEmpty());
    String contract_address = targetContent.getString("contract_address");
    Assert.assertTrue(
            patternAddress.matcher(contract_address).matches() && !contract_address.isEmpty());
    Assert.assertEquals(targetContent.getString("tokenId"),contract_address);

  }
  /**
   * constructor.
   * 合约下的TRC20转账
   * 按hash值搜索筛选查询
   * 当前hash不是当前账户地址转账页面中的hash数据，查询出来的数据为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail03() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    //转账页中没有的hash值，查询无数据
    params.put("keyword",foundationKey);
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal == total && total==0);
    //token_transfers object
    Assert.assertTrue(responseContent.containsKey("token_transfers"));

  }

  /**
   * constructor.
   * 合约下的TRC20转账
   * 按时间筛选查询
   * 在当前时间范围内查询出多条数据，rangeTotal大于10000条时，total最多展示10000条数据
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail04() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    //当前地址在2019年8月31号-9月1号查询
    params.put("start_timestamp", "1567244852501");
    params.put("end_timestamp", "1567331264793");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total >0);
    //token_transfers object
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
      String contract_address = responseArrayContent.getJSONObject(i).getString("contract_address");
      Assert.assertTrue(
              patternAddress.matcher(contract_address).matches() && !contract_address.isEmpty());
      Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("tokenId"),contract_address);
    }
  }
  /**
   * constructor.
   * 合约下的TRC20转账
   * 按hash值搜索筛选查询
   * 当前时间范围内，查询出来的数据为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail05() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    //时间范围内，查询无数据，当前地址在2019年9月1号查询为空
    params.put("start_timestamp", "1567331264793");
    params.put("end_timestamp", "1567331264793");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal == total && total==0);
    //token_transfers object
    Assert.assertTrue(responseContent.containsKey("token_transfers"));

  }
  /**
   * constructor.
   * 合约下的TRC20转账
   * 按账户页中TRC20转账中涉及相关的地址查询，能够查出一条或多条数据
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail06() {
    //Get response
    //给出转账页中的hash值
    String keyword = "T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb";
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    //转账页中的hash值，有且只有一条数据或多条数据
    params.put("keyword",keyword);
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //token_transfers object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    targetContent = responseArrayContent.getJSONObject(0);
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    Assert.assertTrue(patternHash.matcher(targetContent.getString("transaction_id")).matches());

    Assert.assertTrue(targetContent.containsKey("block_ts"));
    Assert.assertTrue(Long.valueOf(targetContent.get("block").toString()) >= 1000000);

    Assert.assertTrue(targetContent.containsKey("quant"));
    Assert.assertTrue(targetContent.containsKey("confirmed"));
    Assert.assertTrue(targetContent.containsKey("contractRet"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    String from_address = targetContent.getString("from_address");
    Assert.assertTrue(
            patternAddress.matcher(from_address).matches() && !from_address.isEmpty());
    String to_address = targetContent.getString("to_address");
    Assert.assertTrue(
            patternAddress.matcher(to_address).matches() && !to_address.isEmpty());
    String contract_address = targetContent.getString("contract_address");
    Assert.assertTrue(
            patternAddress.matcher(contract_address).matches() && !contract_address.isEmpty());
    Assert.assertEquals(targetContent.getString("tokenId"),contract_address);

  }
  /**
   * constructor.
   * 合约下的TRC20转账
   * 按账户页中TRC20转账中地址查询
   * 搜索框中输入错误数据，查询出的结果为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail07() {
    //Get response
    String keyword = "9999";
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    //输入框中输入错误查询信息，查询出来的数据应为空
    params.put("keyword",keyword);
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal == total && total==0);
    //token_transfers object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    Assert.assertTrue(responseArrayContent.size() == 0);
  }

  /**
   * constructor.
   * 合约下的TRC20转账
   * 按账户页中TRC20转账中地址查询
   * limit=0时，total、rangeTotal、token_transfers中的个数，数据保持一致为0
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void test01getBlockDetail08() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("limit", "0");
    params.put("start", "0");
    params.put("relatedAddress", contractAddress);
    params.put("sort","-timestamp");
    params.put("count","true");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal > 0 && total==0);
    //token_transfers object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    Assert.assertTrue(responseArrayContent.size() == 0);
  }
  /**
   * constructor.
   * 合约下的TRC20转账
   * 有无参数返回状态为0
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
  public void getTotalSupply() {
    //Get response
    response = TronscanApiList.getTotalSupply(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);

  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
