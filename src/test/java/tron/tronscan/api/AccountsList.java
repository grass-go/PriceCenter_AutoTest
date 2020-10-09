package tron.tronscan.api;

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
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class AccountsList {


  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor.账户页列表接口
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List account")
  public void test01getAccount() {
    System.out.println();
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-balance");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    response = TronscanApiList.getAccount(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total
    //页面仅展示的数量，如果大于10000，则最多显示10000
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total > 0 && total <= 10000);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertEquals(limit, responseArrayContent.size());
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      //账户
      String address = responseObject.getString("address");
      Assert.assertTrue(patternAddress.matcher(address).matches() && !address.isEmpty());
      //TRX总余额
      Assert.assertTrue(Double.valueOf(responseObject.get("balance").toString()) > 0);
      //冻结TRX数量
      Assert.assertTrue(Long.valueOf(responseObject.get("power").toString()) >= 0);
      //交易数量
      Assert.assertTrue(Long.valueOf(responseObject.get("totalTransactionCount").toString()) > 0);
      //账户是否为合约
      Assert.assertTrue(responseContent.containsKey("contractMap"));
    }
  }

  /**
   * constructor.账户详情页接口
   * 普通账户
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get a single account's detail ")
  public void getAccountList() {
    //Get response
    String address = "TWd4WrZ9wn84f5x1hZhL4DHvk738ns5jwb";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getAccountList(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() >= 18);
    //allowExchange
    Assert.assertTrue(responseContent.containsKey("allowExchange"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    String addressOb = responseContent.getString("address");
    Assert.assertTrue(patternAddress.matcher(addressOb).matches());
    Assert.assertEquals(addressOb,address);
    Assert.assertTrue(responseContent.containsKey("frozen_supply"));
    Assert.assertTrue(responseContent.containsKey("accountType"));
    Assert.assertTrue(responseContent.containsKey("exchanges"));
    //名称
    Assert.assertTrue(responseContent.containsKey("name"));
    Assert.assertTrue(!responseContent.get("bandwidth").toString().isEmpty());
    //投票数
    Assert.assertTrue(Long.valueOf(responseContent.get("voteTotal").toString()) >= 0);
   //交易数
    Assert.assertTrue(Long.valueOf(responseContent.get("totalTransactionCount").toString()) >= 0);
    Assert.assertTrue(responseContent.containsKey("activePermissions"));
    //创建时间
    Assert.assertTrue(responseContent.containsKey("date_created"));
    //可用余额
    Assert.assertTrue(Double.valueOf(responseContent.get("balance").toString()) > 0);
    Assert.assertTrue(!responseContent.get("date_created").toString().isEmpty());
    //地址下的20token
    JSONArray exchangeArray = responseContent.getJSONArray("trc20token_balances");
    targetContent = exchangeArray.getJSONObject(0);
    //symbol
    Assert.assertTrue(!targetContent.get("symbol").toString().isEmpty());
    //balance
    Assert.assertTrue(Double.valueOf(targetContent.get("balance").toString()) > 0);
    //decimals
    Integer decimals = Integer.valueOf(targetContent.get("decimals").toString());
    Assert.assertTrue(decimals >= 0 && decimals <= 18);
    //name
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    //contract_address
    Assert
        .assertTrue(patternAddress.matcher(targetContent.getString("contract_address")).matches());

    //bandwidth json
    targetContent = responseContent.getJSONObject("bandwidth");
    //energyRemaining
    Assert.assertTrue(Long.valueOf(targetContent.get("energyRemaining").toString()) >= 0);
    //totalEnergyLimit
    Assert.assertTrue(Long.valueOf(targetContent.get("totalEnergyLimit").toString()) > 0);
    Assert.assertTrue(Long.valueOf(targetContent.get("totalEnergyWeight").toString()) > 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("netUsed").toString()) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("storageLimit").toString()) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("storagePercentage").toString()) >= 0);
    //assets
    Assert.assertTrue(targetContent.containsKey("assets"));

    //frozen json
    targetContent = responseContent.getJSONObject("frozen");
    //冻结总数量获得的投票权
    Assert.assertTrue(Long.valueOf(targetContent.get("total").toString()) >= 0);
    Assert.assertTrue(targetContent.containsKey("balances"));

    //accountResource json
    targetContent = responseContent.getJSONObject("accountResource");
    Assert.assertTrue(targetContent.containsKey("frozen_balance_for_energy"));

    //tokenBalances json
    JSONArray tokenBalancesArray = responseContent.getJSONArray("tokenBalances");
    targetContent = tokenBalancesArray.getJSONObject(0);
    Assert.assertTrue(Long.valueOf(targetContent.get("balance").toString()) > 0);
    Assert.assertTrue(targetContent.containsKey("name"));


    //balances json
    JSONArray balancesArray = responseContent.getJSONArray("balances");
    targetContent = balancesArray.getJSONObject(0);
    Assert.assertTrue(Long.valueOf(targetContent.get("balance").toString()) > 0);
    Assert.assertTrue(targetContent.containsKey("name"));

    //delegated json
    targetContent = responseContent.getJSONObject("delegated");
    Assert.assertTrue(targetContent.containsKey("sentDelegatedBandwidth"));
    Assert.assertTrue(targetContent.containsKey("sentDelegatedResource"));
    Assert.assertTrue(targetContent.containsKey("receivedDelegatedBandwidth"));
    Assert.assertTrue(targetContent.containsKey("receivedDelegatedResource"));

    //representative json
    targetContent = responseContent.getJSONObject("representative");
    Assert.assertTrue(Long.valueOf(targetContent.get("lastWithDrawTime").toString()) >= 0);
    Assert.assertTrue(Integer.valueOf(targetContent.get("allowance").toString()) >= 0);
    //enabled
    Assert.assertTrue(targetContent.containsKey("enabled"));
    Assert.assertTrue(targetContent.containsKey("url"));

  }

  /**
   * constructor.查询账户交易统计信息
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询账户交易统计信息")
  public void getAccountStats() {
    //Get response
    String address = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getAccountStats(tronScanNode, params);
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
    Long total = transactions_in + transactions_out;
    Assert.assertTrue((transactions_in + transactions_out) == transactions);
  }

  /**
   * constructor.查询投票接口
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get specific account's vote list")
  public void getAccountVote() {
    //Get response
    String address = "TGzz8gjYiYRqpfmDwnLxfgPuLVNmpCswVp";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getAccountVote(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() == 1);
    //votes
    Assert.assertTrue(responseContent.containsKey("votes"));

  }

  /**
   * constructor.查看SR信息
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get a super representative's github link")
  public void getAccountSr() {
    //Get response
    String address = "TDDQuZKCF5dNqZV8pTjL3pNiPS2FnGngw2";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getAccountSr(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() == 1);
    targetContent = responseContent.getJSONObject("data");
    //address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    //账户
    String addressOb = targetContent.getString("address");
    Assert.assertTrue(patternAddress.matcher(addressOb).matches() && !addressOb.isEmpty());
    //githubLink
    Assert.assertTrue(targetContent.containsKey("githubLink"));

  }

//以下为Hbase后补加接口

  /**
   * constructor.查看
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get a super representative's github link")
  public void getAccountResource() {
    //Get response
    String address = "TDDQuZKCF5dNqZV8pTjL3pNiPS2FnGngw2";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getResource(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(Long.valueOf(responseContent.getString("total")) >= 0);
    //data object
    Assert.assertTrue(responseContent.size() == 2);
    Assert.assertTrue(responseContent.containsKey("data"));
  }

  /**
   * constructor.查看
   * getActive_statistic
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getActive_statistic() {
    //Get response
    response = TronscanApiList.getActive_statistic(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(Long.valueOf(responseContent.getString("total")) >= 0);
    //data object
    Assert.assertTrue(responseContent.size() == 2);
    JSONArray dataArray = responseContent.getJSONArray("data");
    for (int i = 0; i < dataArray.size(); i++) {
      Assert.assertTrue(!dataArray.getJSONObject(i).getString("day_string").isEmpty());
      Assert.assertTrue(!dataArray.getJSONObject(i).getString("day_time").isEmpty());
      Assert.assertTrue(Long.valueOf(dataArray.getJSONObject(i).getString("active_count")) > 0);
      Assert.assertTrue(Double.valueOf(dataArray.getJSONObject(i).getString("amount")) > 0);
      Assert.assertTrue(Long.valueOf(dataArray.getJSONObject(i).getString("transactions")) > 0);
      Assert.assertTrue(!dataArray.getJSONObject(i).getString("type").isEmpty());
      Assert.assertTrue(Double.valueOf(dataArray.getJSONObject(i).getString("proportion")) > 0);
      Assert.assertTrue(Double.valueOf(dataArray.getJSONObject(i).getString("mom")) > -100);

    }
  }
  /**
   * constructor.查看
   * getIncrease
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getIncrease() {
    //Get response
    response = TronscanApiList.getIncrease(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(!responseContent.getString("success").isEmpty());
    //data object
    Assert.assertTrue(responseContent.size() == 2);
    JSONArray dataArray = responseContent.getJSONArray("data");
    for (int i = 0; i < dataArray.size(); i++) {
      Assert.assertTrue(!dataArray.getJSONObject(i).getString("day_string").isEmpty());
      Assert.assertTrue(!dataArray.getJSONObject(i).getString("date").isEmpty());
      Assert.assertTrue(Long.valueOf(dataArray.getJSONObject(i).getString("new_account_seen")) > 0);
      Assert.assertTrue(Long.valueOf(dataArray.getJSONObject(i).getString("total_account")) >= 0);

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
