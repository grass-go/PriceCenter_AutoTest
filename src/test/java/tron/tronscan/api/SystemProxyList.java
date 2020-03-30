package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
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
 * @Date:2019-09-11 15:05
 */
@Slf4j
public class SystemProxyList {
  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONObject bodyContent;
  private JSONObject trxContent;
  private JSONObject quoteContent;
  private JSONObject ETHContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "提供CNY货币转换价格")
  public void getProxyList() {
    //Post response
    String params = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=TRX&convert=ETH";
    response = TronscanApiList.postProxyList(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //two object, "status" and "data"
    Assert.assertTrue(responseContent.size() == 2);
    //status
    bodyContent = responseContent.getJSONObject("status");
      Assert.assertTrue(bodyContent.containsKey("timestamp"));
      Assert.assertTrue(bodyContent.containsKey("error_code"));
      Assert.assertTrue(bodyContent.containsKey("error_message"));
      //Assert.assertTrue(bodyContent.containsKey("credit_count"));
      Assert.assertTrue(Long.valueOf(bodyContent.get("credit_count").toString()) >= 0);
      Assert.assertTrue(Long.valueOf(bodyContent.get("elapsed").toString()) >= 0);
      Assert.assertTrue(bodyContent.containsKey("notice"));

    //data
    bodyContent = responseContent.getJSONObject("data");
    //TRX
      trxContent = bodyContent.getJSONObject("TRX");
        Assert.assertTrue(Integer.valueOf(trxContent.get("id").toString()) >= 0);
        //Assert.assertTrue(!trxContent.get("id").toString().isEmpty());
        Assert.assertTrue(!trxContent.get("name").toString().isEmpty());
        Assert.assertTrue(!trxContent.get("symbol").toString().isEmpty());
        Assert.assertTrue(!trxContent.get("slug").toString().isEmpty());
        Assert.assertTrue(!trxContent.get("num_market_pairs").toString().isEmpty());
        Assert.assertTrue(!trxContent.get("date_added").toString().isEmpty());
        Assert.assertTrue(trxContent.containsKey("max_supply"));
        Assert.assertTrue(trxContent.containsKey("total_supply"));
        Assert.assertTrue(trxContent.containsKey("circulating_supply"));
        Assert.assertTrue(Integer.valueOf(trxContent.get("cmc_rank").toString()) >= 0);
        Assert.assertTrue(!trxContent.get("last_updated").toString().isEmpty());
        //quote
        quoteContent = trxContent.getJSONObject("quote");
        //ETH
        ETHContent = quoteContent.getJSONObject("ETH");
          Assert.assertTrue(Double.valueOf(ETHContent.get("price").toString()) >= 0);
          Assert.assertTrue(Double.valueOf(ETHContent.get("volume_24h").toString()) >= 0);
          Assert.assertTrue(!ETHContent.get("percent_change_1h").toString().isEmpty());
          Assert.assertTrue(!ETHContent.get("percent_change_24h").toString().isEmpty());
          Assert.assertTrue(!ETHContent.get("percent_change_7d").toString().isEmpty());
          Assert.assertTrue(Double.valueOf(ETHContent.get("market_cap").toString()) >= 0);
          Assert.assertTrue(!ETHContent.get("last_updated").toString().isEmpty());

  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disConnect();
  }
}
