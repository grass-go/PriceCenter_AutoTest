package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class ContractTransactionList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private String contractAddress = Configuration.getByPath("testng.conf")
      .getString("defaultParameter.contractAddress");

  /**
   * constructor.
   * 根据地址查询出合约下的交易，当前地址显示total大于10000条时，目前只展示10000条
   * 未做筛选，total首页展示20条数据
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    params.put("sort=&count=true&", "-timestamp");
    params.put("count", "true");
    params.put("limit", Integer.toString(limit));
    params.put("start", "0");
    params.put("address", contractAddress);

    response = TronscanApiList.getContractTransactionList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total <= 10000);
    //contractMap
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //data
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      int dataSize = responseArrayContent.size();
      Assert.assertEquals(dataSize, limit); //address
      //block
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("block").toString().isEmpty());
      //call_data
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("call_data").toString().isEmpty());
      //ownAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("ownAddress")).matches());
      //ownAddressType
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("ownAddressType").toString().isEmpty());
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("txHash")).matches());

      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("contractRet"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("cost"));
      //ownAddressType
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("toAddressType").toString().isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("toAddress")).matches());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("token"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("txFee"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("parentHash"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("value"));
      //timestamp
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());

    }
  }
  /**
   * constructor.
   * 根据地址查询出合约下的交易，当前地址显示total大于10000条时，目前只展示10000条
   * 当前账户地址下交易界面根据hash值查询出有且只有一条数据
   * hash做筛选
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList02() {
    //Get response
    int limit = 1;
//    contractAddress地址下的hash值
    String hash = "aad4633946b6638fca795bc88ee1b1c0abb7373846edac94ad46b2244a064fcf";
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    params.put("sort=&count=true&", "-timestamp");
    params.put("count", "true");
    params.put("limit", Integer.toString(limit));
    params.put("start", "0");
    params.put("address", contractAddress);
    params.put("keyword",hash);
    response = TronscanApiList.getContractTransactionList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    //根据搜索查询的hash值并且只能查询出一条数据
    Assert.assertTrue(total==1 && rangeTotal == total);
    //contractMap
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //data
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
//      int dataSize = responseArrayContent.size();
//      Assert.assertEquals(dataSize, limit); //address
      targetContent = responseArrayContent.getJSONObject(0);
      //block
      Assert.assertTrue(!targetContent.get("block").toString().isEmpty());
      //call_data
      Assert.assertTrue(!targetContent.get("call_data").toString().isEmpty());
      //ownAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(targetContent.getString("ownAddress")).matches());
      //ownAddressType
      Assert.assertTrue(!targetContent.get("ownAddressType").toString().isEmpty());
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      String txHash = targetContent.getString("txHash");
      //判断查询出来的hash值数据是否是搜索查询的hash值数据
      Assert.assertEquals(targetContent.getString("txHash"), hash);
      //contractRet
      Assert.assertTrue(targetContent.containsKey("contractRet"));
      Assert.assertTrue(targetContent.containsKey("cost"));
      //ownAddressType
      Assert.assertTrue(!targetContent.get("toAddressType").toString().isEmpty());
      Assert.assertTrue(targetContent.containsKey("confirmed"));

      String toAddress = targetContent.getString("toAddress");
      Assert.assertTrue(patternAddress.matcher(toAddress).matches());
      Assert.assertEquals(toAddress, contractAddress);
      Assert.assertTrue(targetContent.containsKey("token"));
      Assert.assertTrue(targetContent.containsKey("txFee"));
      Assert.assertTrue(targetContent.containsKey("parentHash"));
      Assert.assertTrue(targetContent.containsKey("value"));
      //timestamp
      Assert.assertTrue(!targetContent.get("timestamp").toString().isEmpty());
  }

  /**
   * constructor.
   * 根据地址查询出合约下的交易，当前地址显示total大于10000条时，目前只展示10000条
   * 当前账户地址下交易界面根据时间值查询出数据
   * 时间做筛选,共找到 4,233 个交易
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList03() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    params.put("sort=&count=true&", "-timestamp");
    params.put("count", "true");
    params.put("limit", Integer.toString(limit));
    params.put("start", "0");
    params.put("address", contractAddress);
    //在当前时间范围内，查出的数据应为空
    params.put("start_timestamp","1567241116701");
    params.put("end_timestamp","1567327530465");
    response = TronscanApiList.getContractTransactionList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    //根据搜索查询的时间值,查询出数据
    Assert.assertTrue(rangeTotal >= total && total >0);
    //contractMap
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //data
    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      int dataSize = responseArrayContent.size();
      Assert.assertEquals(dataSize, limit); //address
      //block
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("block").toString().isEmpty());
      //call_data
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("call_data").toString().isEmpty());
      //ownAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("ownAddress")).matches());
      //ownAddressType
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("ownAddressType").toString().isEmpty());
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("txHash")).matches());

      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("contractRet"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("cost"));
      //ownAddressType
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("toAddressType").toString().isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("toAddress")).matches());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("token"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("txFee"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("parentHash"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("value"));
      //timestamp
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      }
    }

  /**
   * constructor.
   * 根据地址查询出合约下的交易，当前地址显示total大于10000条时，目前只展示10000条
   * 当前账户地址下交易界面，按hash值搜索，该hash值不在该合约交易中
   * 查出的结果应为空
   * hash异常case
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList04() {
    //Get response
    int limit = 1;
//    contractAddress地址下没有这个hash值
    String hash = "f500cbe8eb9ca8c9c41a869e77d7790383357c4aeba76099d50e092e64402965";
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    params.put("sort=&count=true&", "-timestamp");
    params.put("count", "true");
    params.put("limit", Integer.toString(limit));
    params.put("start", "0");
    params.put("address", contractAddress);
    params.put("keyword",hash);
    response = TronscanApiList.getContractTransactionList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    //根据搜索查询的hash值查出空数据
    Assert.assertTrue(rangeTotal == total && total==0);
    //contractMap
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    //data
    Assert.assertTrue(responseContent.containsKey("data"));
  }

  /**
   * constructor.
   * 根据地址查询出合约下的交易，当前地址显示total大于10000条时，目前只展示10000条
   * 当前账户地址下交易界面，按时间值搜索，该时间值在该合约交易中没有数据产生
   * 查出的结果应为空
   * 异常case
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList05() {
    //Get response
    int limit = 1;
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    params.put("sort=&count=true&", "-timestamp");
    params.put("count", "true");
    params.put("limit", Integer.toString(limit));
    params.put("start", "0");
    params.put("address", contractAddress);
    //在当前时间范围内，查出的数据应为空
    params.put("start_timestamp","1593593116701");
    params.put("end_timestamp","1600850730465");
    response = TronscanApiList.getContractTransactionList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    //根据搜索查询的hash值查出空数据
    Assert.assertTrue(rangeTotal == total && total==0);
    //contractMap
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    //data
    Assert.assertTrue(responseContent.containsKey("data"));
  }
  /**
   * constructor.
   * 根据地址查询出合约下的交易，当前地址显示total大于10000条时，目前只展示10000条
   * 搜索框输入错误数据
   * 查出的结果应为空
   * 异常case
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList06() {
    //Get response
    int limit = 1;
    String hash = "9999";
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    params.put("sort=&count=true&", "-timestamp");
    params.put("count", "true");
    params.put("limit", Integer.toString(limit));
    params.put("start", "0");
    params.put("address", contractAddress);
    //搜索框中输入错误的数据，查询出来的结果为空
    params.put("keyword",hash);
    response = TronscanApiList.getContractTransactionList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    //根据搜索查询的hash值查出空数据
    Assert.assertTrue(rangeTotal == total && total==0);
    //contractMap
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    //data
    Assert.assertTrue(responseContent.containsKey("data"));
  }

  /**
   * constructor.
   * 根据地址查询出合约下的交易，当前地址显示total大于10000条时，目前只展示10000条
   * 当前账户地址下搜索相关涉及到的地址
   * 搜索框地址做筛选，查出的数据有且只有一条或者多条数据
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList07() {
    //Get response
    int limit = 1;
//    contractAddress地址下的交易地址值
    String hash = "TCaijKuQXWTsqkvpB2hig7QaTYSftS1RaR";
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    params.put("sort=&count=true&", "-timestamp");
    params.put("count", "true");
    params.put("limit", Integer.toString(limit));
    params.put("start", "0");
    params.put("address", contractAddress);
    //搜索框中的地址，查询出一条或这多条相关数据
    params.put("keyword",hash);
    response = TronscanApiList.getContractTransactionList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    //根据搜索查询的hash值并且只能查询出一条数据
    Assert.assertTrue(total==1 && rangeTotal == total);
    //contractMap
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //data
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    targetContent = responseArrayContent.getJSONObject(0);
    //block
    Assert.assertTrue(!targetContent.get("block").toString().isEmpty());
    //call_data
    Assert.assertTrue(!targetContent.get("call_data").toString().isEmpty());
    //ownAddress
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("ownAddress")).matches());
    //ownAddressType
    Assert.assertTrue(!targetContent.get("ownAddressType").toString().isEmpty());
    //hash
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    String txHash = targetContent.getString("txHash");
    //
    Assert.assertTrue(patternHash.matcher(txHash).matches());
    //contractRet
    Assert.assertTrue(targetContent.containsKey("contractRet"));
    Assert.assertTrue(targetContent.containsKey("cost"));
    //ownAddressType
    Assert.assertTrue(!targetContent.get("toAddressType").toString().isEmpty());
    Assert.assertTrue(targetContent.containsKey("confirmed"));

    String toAddress = targetContent.getString("toAddress");
    Assert.assertTrue(patternAddress.matcher(toAddress).matches());
    Assert.assertEquals(toAddress, hash);
    Assert.assertTrue(targetContent.containsKey("token"));
    Assert.assertTrue(targetContent.containsKey("txFee"));
    Assert.assertTrue(targetContent.containsKey("parentHash"));
    Assert.assertTrue(targetContent.containsKey("value"));
    //timestamp
    Assert.assertTrue(!targetContent.get("timestamp").toString().isEmpty());
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
