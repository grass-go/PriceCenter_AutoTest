package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class getCoinCapTrxPrice extends TronlinkBase {

  private HttpResponse response;
  @Test(enabled = true, description = "GetCoinCapTrxPrice test")
  public void test001CoinCapTrxPrice() throws Exception {
    response = api.getCoinCapTrxPrice();
    JSONObject trxData = api.parseResponse2JsonObject(response).getJSONObject("data");
    api.printJsonObjectContent(trxData);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_usd")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_cny")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_btc")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eth")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_gbp")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eur")) > 0);
  }
}

