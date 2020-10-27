package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

/**
 * ${params}
 *
 * @Author:jh
 * @Date:2019-08-29 19:34
 */
public class InternalTranList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private String contractAddress = Configuration.getByPath("testng.conf")
          .getString("defaultParameter.contractAddress");

  /**
   * constructor.
   * 查询合约内部合约内交易
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 未做筛选，total首页展示20条数据
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
  public void getInternalTransaction() {
    //
    String address = "TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3";
    Map<String, String> Params = new HashMap<>();
//    Params.put("start_timestamp", "1529856000000");
//    Params.put("end_timestamp", "1567595388290");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("contract", contractAddress);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getInternalTransaction(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //contractMap
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //data Object
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //block
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("block").toString().isEmpty());
      //call_data
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_id").toString().isEmpty());
      //from
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("from")).matches());
      //timestamp
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());

      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("rejected"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("confirmed"));
      //to
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("to")).matches());
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("result"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("revert"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("note"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("valueInfoList"));
      //token_list
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_list").toString().isEmpty());

    }
  }

  /**
   * constructor.
   * 查询合约内部合约内交易
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 当前hash是当前合约地址内部交易页面中的hash数据，查询出来的数据有且只有一条
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
  public void getInternalTransaction02() {
    //
    String keyword = "aad4633946b6638fca795bc88ee1b1c0abb7373846edac94ad46b2244a064fcf";
    Map<String, String> params = new HashMap<>();
//    Params.put("start_timestamp", "1529856000000");
//    Params.put("end_timestamp", "1567595388290");
    params.put("limit", "20");
    params.put("start", "0");
    params.put("contract", contractAddress);
    params.put("keyword",keyword);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getInternalTransaction(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //contractMap
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //data Object
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    targetContent = exchangeArray.getJSONObject(0);
    //block
    Assert.assertTrue(!targetContent.get("block").toString().isEmpty());
    //call_data
    Assert.assertTrue(!targetContent.get("token_id").toString().isEmpty());
    //from
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    String from = targetContent.getString("from");
    Assert.assertTrue(patternAddress.matcher(from).matches());
    Assert.assertEquals(from, contractAddress);
    //timestamp
    Assert.assertTrue(!targetContent.get("timestamp").toString().isEmpty());
    //hash
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    String hash = targetContent.getString("hash");
    Assert.assertTrue(patternHash.matcher(hash).matches());
    //判断查询出来的hash值数据是否是搜索查询的hash值数据
    Assert.assertEquals(hash, keyword);
    Assert.assertTrue(targetContent.containsKey("rejected"));
    Assert.assertTrue(targetContent.containsKey("confirmed"));
    //to
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("to")).matches());
    Assert.assertTrue(targetContent.containsKey("result"));
    Assert.assertTrue(targetContent.containsKey("revert"));
    Assert.assertTrue(targetContent.containsKey("note"));
    Assert.assertTrue(targetContent.containsKey("valueInfoList"));
    //token_list
    Assert.assertTrue(!targetContent.get("token_list").toString().isEmpty());

  }

  /**
   * constructor.
   * 查询合约内部合约内交易
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 当前hash不是当前账户地址内部交易页面中的hash数据，查询出来的数据为空
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
  public void getInternalTransaction03() {
    //
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("contract", contractAddress);
    params.put("keyword",foundationKey);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getInternalTransaction(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal == total && total==0);
    Assert.assertTrue(responseContent.containsKey("data"));
  }

  /**
   * constructor.
   * 查询合约内部合约内交易
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 按时间做筛选，大于10000条的数据total只展示10000条
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
  public void getInternalTransaction04() {
    //
    Map<String, String> Params = new HashMap<>();
    Params.put("start_timestamp", "1529856000000");
    Params.put("end_timestamp", "1567595388290");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("contract", contractAddress);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getInternalTransaction(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //contractMap
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //data Object
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //block
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("block").toString().isEmpty());
      //call_data
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_id").toString().isEmpty());
      //from
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("from")).matches());
      //timestamp
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());

      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("rejected"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("confirmed"));
      //to
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("to")).matches());
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("result"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("revert"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("note"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("valueInfoList"));
      //token_list
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_list").toString().isEmpty());

    }
  }
  /**
   * constructor.
   * 查询合约内部合约内交易
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 在查询时间范围内，查询出来的数据为空
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
    public void getInternalTransaction05() {
      //
      Map<String, String> params = new HashMap<>();
      params.put("limit", "20");
      params.put("start", "0");
      params.put("contract", contractAddress);
      //当前地址在2019年9月1号查询为空
      params.put("start_timestamp", "1567333639905");
      params.put("end_timestamp", "1567333639905");
      //Three object "total" ,"data","rangeTotal"
      response = TronscanApiList.getInternalTransaction(tronScanNode, params);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronscanApiList.parseResponseContent(response);
      TronscanApiList.printJsonContent(responseContent);

      Long total = Long.valueOf(responseContent.get("total").toString());
      Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
      Assert.assertTrue(rangeTotal == total && total==0);
      Assert.assertTrue(responseContent.containsKey("data"));
  }

  /**
   * constructor.
   * 查询合约内部合约内交易
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 按地址搜索栏做筛选
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
  public void getInternalTransaction06() {
    //
    String keyword = "TBfHSbt7rARV5P9FiZfWQE9Z5a9NVypqbz";
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("contract", contractAddress);
    //在搜索栏中输入内部交易相关的地址，可查询出交易数据
    params.put("keyword",keyword);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getInternalTransaction(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data object
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //block
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("block").toString().isEmpty());
      //call_data
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_id").toString().isEmpty());
      //from
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("from")).matches());
      //timestamp
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
      //hash
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());

      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("rejected"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("confirmed"));
      //to
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("to")).matches());
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("result"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("revert"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("note"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("valueInfoList"));
      //token_list
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_list").toString().isEmpty());

    }

  }
  /**
   * constructor.
   * 查询合约内部合约内交易
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 给出错误的hash值，查询出来的数据为空
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
    public void getInternalTransaction07() {
      //
      String keyword = "8888";
      Map<String, String> params = new HashMap<>();
      params.put("limit", "20");
      params.put("start", "0");
      params.put("contract", contractAddress);
      //在搜索栏中输入错误数据
      params.put("keyword",keyword);
      //Three object "total" ,"data","rangeTotal"
      response = TronscanApiList.getInternalTransaction(tronScanNode, params);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronscanApiList.parseResponseContent(response);
      TronscanApiList.printJsonContent(responseContent);

      Long total = Long.valueOf(responseContent.get("total").toString());
      Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
      Assert.assertTrue(rangeTotal == total && total==0);
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
