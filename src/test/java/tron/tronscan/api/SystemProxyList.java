package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
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
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor.
   */
  @Test(enabled = true, description = "提供CNY货币转换价格")
  public void getProxyList() {
    //Get response
    String params = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=TRX&convert=ETH";
    //Map<String, String> params = new HashMap<>();
    //JsonObject body = new JsonObject();
    //params.add("url",urladd);
    response = TronscanApiList.postProxyList(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONArray responseContent = TronscanApiList.parseArrayResponseContent(response);
    JSONArray exchangeArray = responseContent;


    //first_token_id
//    targetContent = exchangeArray.getJSONObject(0);
//    Double total_supply = Double.valueOf(targetContent.get("total_supply").toString());
//    Assert.assertTrue(total_supply > 1000000);
//
//    Double price_cny = Double.valueOf(targetContent.get("price_cny").toString());
//    Assert.assertTrue(price_cny > 0);

  }
}
