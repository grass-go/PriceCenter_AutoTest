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
  private HashMap<String, String> testAccount;

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
              Double.valueOf(exchangeArray.getJSONObject(i).get("total_supply_with_decimals").toString()) >= 1000000000);
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
   * constructor.
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
    Params.put("start_timestamp", "1529856000000");
    Params.put("end_timestamp", "1567588908615");
    Params.put("direction", "all");
    Params.put("address", "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9");
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
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("amount").toString()) >= 1);
      sonContent = exchangeArray.getJSONObject(i).getJSONObject("cost");
      //address
      Assert.assertTrue(sonContent.containsKey("net_fee"));
      Assert.assertTrue(sonContent.containsKey("energy_usage"));
      Assert.assertTrue(sonContent.containsKey("energy_fee"));
      //实际消耗的能量
      Long energy_usage_total = Long.valueOf(sonContent.get("energy_usage_total").toString());
      //允许使用的最大能量
      Long origin_energy_usage = Long.valueOf(sonContent.get("origin_energy_usage").toString());
      Assert.assertTrue(origin_energy_usage >= energy_usage_total);
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
   * 地址下无token
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "地址下无token")
  public void getTokentrc20_transfer_02() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
    Params.put("start_timestamp", "1529856000000");
    Params.put("end_timestamp", "1567588908615");
    Params.put("direction", "all");
    Params.put("address", "TSmZ71H9S6BQLdyGcr8QfG9qr92N6WUXKS");
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
