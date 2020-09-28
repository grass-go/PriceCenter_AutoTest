package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;


@Slf4j
public class ExchangeListAll {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String,String> testAccount;

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get exchange list all")
  public void getExchangeListAll() {
    //Get response
    response = TronscanApiList.getExchangesListAll(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONArray responseContent = TronscanApiList.parseArrayResponseContent(response);
    TronscanApiList.printJsonArrayContent(responseContent);
    JSONArray exchangeArray = responseContent;

    //first_token_id
    for (int i = 0; i < exchangeArray.size(); i++) {
      Long firstTokenId = Long.valueOf(exchangeArray.getJSONObject(i).get("first_token_id").toString());
      Assert.assertTrue(firstTokenId > 1000000);

      //up_down_percent > -1 && up_down_percent < 1
      Double upDownPercent = Double.valueOf(exchangeArray.getJSONObject(i).get("up_down_percent").toString());
      Assert.assertTrue(upDownPercent >= -1 && upDownPercent <= 10);

      //second_token_balance
      Long secondTokenBalance = Long.valueOf(exchangeArray.getJSONObject(i).get("second_token_balance").toString());
      Assert.assertTrue(secondTokenBalance >= 0);

      //exchange_name
      String exchangeName = exchangeArray.getJSONObject(i).getString("exchange_name");
      Assert.assertTrue(exchangeName.contains(firstTokenId.toString()));

      //volume
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("volume").toString().isEmpty());

      //exchange_id
      Assert.assertTrue(Integer.valueOf(exchangeArray.getJSONObject(i).get("exchange_id").toString()) > 0);

      //high
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("high").toString().isEmpty());

      //first_token_balance
      Long firstTokenBalance = Long.valueOf(exchangeArray.getJSONObject(i).get("first_token_balance").toString());
      Assert.assertTrue(firstTokenBalance >= 0);

      //high
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("low").toString().isEmpty());

      //Price
      Double price = Double.valueOf(exchangeArray.getJSONObject(i).get("price").toString());
      Assert.assertTrue(price >= 0);

      //first_owner_address
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("first_owner_address"));

      //creator_address
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("creator_address"));

      //svolume
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("svolume").toString().isEmpty());

      //first_token_abbr
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("first_token_abbr").toString().isEmpty());

      //exchange_abbr_name
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("exchange_abbr_name").toString().isEmpty());

      //second_token_id
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("second_token_id").toString().isEmpty());

      //status
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("status").toString().isEmpty());
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
