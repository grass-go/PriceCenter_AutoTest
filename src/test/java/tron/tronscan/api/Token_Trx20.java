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

/**
 * ${params}
 *
 * @Author:tron
 * @Date:2019-08-29 13:41
 */
@Slf4j
public class Token_Trx20 {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONObject sonContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private String accountAddress = Configuration.getByPath("testng.conf")
          .getString("defaultParameter.accountAddress");

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List all the trc20 tokens in the blockchain")
  public void getTokentrc20() {
    //Get response
    Map<String, String> Params = new HashMap<>();
//    Params.put("sort","-balance");
    Params.put("limit", "20");
    Params.put("start", "0");
    response = TronscanApiList.getTokentrc20(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() >= 3);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("trc20_tokens");
    Assert.assertTrue(rangeTotal >= total);
    //Assert.assertTrue(responseContent.containsKey("rangeTotal"));
    for (int i = 0; i < exchangeArray.size(); i++) {
      //icon_url
      String icon_url = exchangeArray.getJSONObject(i).get("icon_url").toString();
      Assert.assertTrue(!icon_url.isEmpty());
      if (icon_url.substring(0, 7) == "http://") {
        HttpResponse httpResponse = TronscanApiList.getUrlkey(icon_url);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      } else {
        Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("icon_url"));
      }
      //total_supply and issue_ts Contain > 0
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("total_supply").toString()) >= 0);
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("issue_ts").toString()) == 0);
      //volume24h and index
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("volume24h").toString()) >= 0);
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("index").toString()) >= 1);
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("symbol").toString().isEmpty());
      //total_supply_str can empty
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("total_supply_str"));
      //contract_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String contract_address = exchangeArray.getJSONObject(i).getString("contract_address");
      Assert.assertTrue(patternAddress.matcher(contract_address).matches() && !contract_address.isEmpty());
      //gain
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("gain"));
      //home_page
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("home_page").toString().isEmpty());
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("volume").toString()) >= 0);
      //issue_address Part is empty
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("issue_address"));
      //token_desc
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_desc").toString().isEmpty());
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("price_trx").toString()) >= 0);
      //git_hub
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("git_hub"));
      //price can null
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("price"));
      //total_supply_with_decimals
      Assert.assertTrue(
              Double.valueOf(exchangeArray.getJSONObject(i).get("total_supply_with_decimals").toString()) >= 10000000);
      //decimals
      Integer decimals = Integer.valueOf(exchangeArray.getJSONObject(i).get("decimals").toString());
      Assert.assertTrue(decimals >= 0 && decimals <= 18);
      //name
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("name").toString().isEmpty());
      //social_media_list
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("social_media_list"));
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("issue_time").toString().isEmpty());
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("white_paper"));
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("social_media"));
    }
  }

  /**
   * constructor.账户地址转账页
   * 未做筛选查询
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "地址下的转账查询")
  public void getTokentrc20_transfer() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
//    Params.put("start_timestamp", "1529856000000");
//    Params.put("end_timestamp", "1567588908615");
    Params.put("direction", "");
    Params.put("address", accountAddress);
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(total >= rangeTotal);
    //contract_map
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //transfers
    JSONArray exchangeArray = responseContent.getJSONArray("transfers");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //contractRet
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("contractRet"));
      //amount > 1
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("amount").toString()) >= 1);
      sonContent = exchangeArray.getJSONObject(i).getJSONObject("cost");
      //address
      Assert.assertTrue(sonContent.containsKey("net_fee"));
      Assert.assertTrue(sonContent.containsKey("energy_usage"));
      Assert.assertTrue(sonContent.containsKey("energy_fee"));
      //实际消耗的能量
      Long energy_usage_total = Long.valueOf(sonContent.get("energy_usage_total").toString());
      //允许使用的最大能量
      Long origin_energy_usage = Long.valueOf(sonContent.get("origin_energy_usage").toString());
      Assert.assertTrue(origin_energy_usage <= energy_usage_total);
      //net_usage
      Assert.assertTrue(Long.valueOf(sonContent.get("net_usage").toString()) >= 0);
      //date_created
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("date_created").toString()) >= 1566962952);
      //owner_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String owner_address = exchangeArray.getJSONObject(i).getString("owner_address");
      Assert.assertTrue(patternAddress.matcher(owner_address).matches() && !owner_address.isEmpty());
      String to_address = exchangeArray.getJSONObject(i).getString("to_address");
      Assert.assertTrue(patternAddress.matcher(to_address).matches() && !to_address.isEmpty());
      //block
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) >= 10000000);
      //type
      String type = exchangeArray.getJSONObject(i).getString("type");
      String tokenType = exchangeArray.getJSONObject(i).getString("tokenType");
      Assert.assertEquals(type,tokenType);
      //confirmed
      Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
//      token_name
      if (tokenType == "trc10") {
        Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_name").toString().isEmpty());
      }
      //parentHash and hash  64 place
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());
    }
  }

  /**
   * constructor.账户地址转账页
   * 搜索框对hash值筛选查询
   * 当前hash是当前账户地址内部交易页面中的hash数据，查询出来的数据有且只有一条
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "地址下的转账查询")
  public void getTokentrc20_transfer02() {
    //Get response
    String keyword = "334fadcf1465237ea09e10f3f38873a4c5b445fdaf725d44266c8760090120fe";
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
    Params.put("direction", "");
    Params.put("address", accountAddress);
    //查询hash值
    Params.put("keyword",keyword);
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(total == rangeTotal && total==1);
    //contract_map
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //transfers
    JSONArray exchangeArray = responseContent.getJSONArray("transfers");
    targetContent = exchangeArray.getJSONObject(0);
      //contractRet
      Assert.assertTrue(targetContent.containsKey("contractRet"));
      //amount > 1
      Assert.assertTrue(Double.valueOf(targetContent.get("amount").toString()) >= 1);
      sonContent = targetContent.getJSONObject("cost");
      //address
      Assert.assertTrue(sonContent.containsKey("net_fee"));
      Assert.assertTrue(sonContent.containsKey("energy_usage"));
      Assert.assertTrue(sonContent.containsKey("energy_fee"));
      //实际消耗的能量
      Long energy_usage_total = Long.valueOf(sonContent.get("energy_usage_total").toString());
      //允许使用的最大能量
      Long origin_energy_usage = Long.valueOf(sonContent.get("origin_energy_usage").toString());
      Assert.assertTrue(origin_energy_usage <= energy_usage_total);
      //net_usage
      Assert.assertTrue(Long.valueOf(sonContent.get("net_usage").toString()) >= 0);
      //date_created
      Assert.assertTrue(Long.valueOf(targetContent.get("date_created").toString()) >= 1566962952);
      //owner_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String owner_address = targetContent.getString("owner_address");
      Assert.assertTrue(patternAddress.matcher(owner_address).matches() && !owner_address.isEmpty());
      String to_address = targetContent.getString("to_address");
      Assert.assertTrue(patternAddress.matcher(to_address).matches() && !to_address.isEmpty());
      //block
      Assert.assertTrue(Long.valueOf(targetContent.get("block").toString()) >= 10000000);
      //type
      String type = targetContent.getString("type");
      String tokenType = targetContent.getString("tokenType");
      Assert.assertEquals(type,tokenType);
      //confirmed
      Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));
//      token_name
      if (tokenType == "trc10") {
        Assert.assertTrue(!targetContent.get("token_name").toString().isEmpty());
      }
      //parentHash and hash  64 place
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(targetContent.getString("hash")).matches());

  }

  /**
   * constructor.账户地址转账页
   * 地址筛选查询
   * 搜索出多条或一条数据
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "地址下的转账查询")
  public void getTokentrc20_transfer03() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
//    Params.put("start_timestamp", "1529856000000");
//    Params.put("end_timestamp", "1567588908615");
    Params.put("direction", "");
    Params.put("address", accountAddress);
    //查询地址值
    Params.put("keyword",accountAddress);
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(total >= rangeTotal && rangeTotal >0);
    //contract_map
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //transfers
    JSONArray exchangeArray = responseContent.getJSONArray("transfers");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //contractRet
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("contractRet"));
      //amount > 1
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("amount").toString()) >= 1);
      sonContent = exchangeArray.getJSONObject(i).getJSONObject("cost");
      //address
      Assert.assertTrue(sonContent.containsKey("net_fee"));
      Assert.assertTrue(sonContent.containsKey("energy_usage"));
      Assert.assertTrue(sonContent.containsKey("energy_fee"));
      //实际消耗的能量
      Long energy_usage_total = Long.valueOf(sonContent.get("energy_usage_total").toString());
      //允许使用的最大能量
      Long origin_energy_usage = Long.valueOf(sonContent.get("origin_energy_usage").toString());
      Assert.assertTrue(origin_energy_usage <= energy_usage_total);
      //net_usage
      Assert.assertTrue(Long.valueOf(sonContent.get("net_usage").toString()) >= 0);
      //date_created
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("date_created").toString()) >= 1566962952);
      //owner_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String owner_address = exchangeArray.getJSONObject(i).getString("owner_address");
      Assert.assertTrue(patternAddress.matcher(owner_address).matches() && !owner_address.isEmpty());
      String to_address = exchangeArray.getJSONObject(i).getString("to_address");
      Assert.assertTrue(patternAddress.matcher(to_address).matches() && !to_address.isEmpty());
      //block
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) >= 10000000);
      //type
      String type = exchangeArray.getJSONObject(i).getString("type");
      String tokenType = exchangeArray.getJSONObject(i).getString("tokenType");
      Assert.assertEquals(type,tokenType);
      //confirmed
      Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
//      token_name
      if (tokenType == "trc10") {
        Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_name").toString().isEmpty());
      }
      //parentHash and hash  64 place
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());
    }
  }

  /**
   * constructor.账户地址转账页
   * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
   * 账户页下的交易界面
   * 按时间查询，在有交易的时间范围内，查出多条
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "地址下的转账查询")
  public void getTokentrc20_transfer04() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");

    Params.put("direction", "");
    Params.put("address", accountAddress);
    //查询2020年9月14号-25号数据，数据大于0
    Params.put("start_timestamp", "1600055220126");
    Params.put("end_timestamp", "1601005626781");
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(total >= rangeTotal && rangeTotal >0);
    //contract_map
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //transfers
    JSONArray exchangeArray = responseContent.getJSONArray("transfers");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //contractRet
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("contractRet"));
      //amount > 1
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("amount").toString()) >= 1);
      sonContent = exchangeArray.getJSONObject(i).getJSONObject("cost");
      //address
      Assert.assertTrue(sonContent.containsKey("net_fee"));
      Assert.assertTrue(sonContent.containsKey("energy_usage"));
      Assert.assertTrue(sonContent.containsKey("energy_fee"));
      //实际消耗的能量
      Long energy_usage_total = Long.valueOf(sonContent.get("energy_usage_total").toString());
      //允许使用的最大能量
      Long origin_energy_usage = Long.valueOf(sonContent.get("origin_energy_usage").toString());
      Assert.assertTrue(origin_energy_usage <= energy_usage_total);
      //net_usage
      Assert.assertTrue(Long.valueOf(sonContent.get("net_usage").toString()) >= 0);
      //date_created
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("date_created").toString()) >= 1566962952);
      //owner_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String owner_address = exchangeArray.getJSONObject(i).getString("owner_address");
      Assert.assertTrue(patternAddress.matcher(owner_address).matches() && !owner_address.isEmpty());
      String to_address = exchangeArray.getJSONObject(i).getString("to_address");
      Assert.assertTrue(patternAddress.matcher(to_address).matches() && !to_address.isEmpty());
      //block
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) >= 10000000);
      //type
      String type = exchangeArray.getJSONObject(i).getString("type");
      String tokenType = exchangeArray.getJSONObject(i).getString("tokenType");
      Assert.assertEquals(type,tokenType);
      //confirmed
      Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
//      token_name
      if (tokenType == "trc10") {
        Assert.assertTrue(!exchangeArray.getJSONObject(i).get("token_name").toString().isEmpty());
      }
      //parentHash and hash  64 place
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());
    }
  }
  /**
   * constructor.
   * 当前时间范围内无交易
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "时间范围内无交易，查询结果为空")
  public void getTokentrc20_transfer_05() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
    Params.put("direction", "");
    Params.put("address", accountAddress);
    //查询2020年9月22号的数据，数据为空
    Params.put("start_timestamp", "1600746420126");
    Params.put("end_timestamp", "1600746426781");
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(total == rangeTotal && rangeTotal==0);
    //contractMap
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    //transfers
    Assert.assertTrue(responseContent.containsKey("transfers"));
  }
  /**
   * constructor.
   * 地址下无此hash值，查询出来的结果为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "地址下此hash值")
  public void getTokentrc20_transfer_06() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
    Params.put("direction", "");
    Params.put("address", accountAddress);
    //查询地址下无此hash值，查询结果为空
    Params.put("keyword",foundationKey);
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(total >= rangeTotal);
    //contractMap
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    //transfers
    Assert.assertTrue(responseContent.containsKey("transfers"));
  }

  /**
   * constructor.
   * 搜索框中输入错误的数据，查询结果为空
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "输入错误数据")
  public void getTokentrc20_transfer_07() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
    Params.put("direction", "");
    Params.put("address", accountAddress);
    //查询地址下无此hash值，查询结果为空
    Params.put("keyword","99999");
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(total >= rangeTotal);
    //contractMap
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    //transfers
    Assert.assertTrue(responseContent.containsKey("transfers"));
  }
  /**
   * constructor.查询trc20通证持有者
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询trc20通证持有者")
  public void getInternal_transaction() {
    //
    String address = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-balance");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("contract_address", address);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getToken20_holders(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());

    Assert.assertTrue(rangeTotal >= total);
    //contract_map
    Assert.assertTrue(!responseContent.get("contractMap").toString().isEmpty());
    //Address list
    JSONArray exchangeArray = responseContent.getJSONArray("trc20_tokens");
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(exchangeArray.size() > 0);
    for (int i = 0; i < exchangeArray.size(); i++) {
      String holder_address = exchangeArray.getJSONObject(i).getString("holder_address");
      Assert.assertTrue(patternAddress.matcher(holder_address).matches() && !holder_address.isEmpty());
      Assert.assertTrue(
          Long.valueOf(exchangeArray.getJSONObject(i).get("balance").toString()) > 1000000000);
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
