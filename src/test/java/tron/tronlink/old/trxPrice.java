package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class trxPrice extends TronlinkBase {
  private HttpResponse response;
  private JSONObject responseContent;

  @Test(enabled = true, description = "Api /api/v1/wallet/trxPrice test")
  public void test001TrxPrice() throws Exception {
    response = api.trxPrice();
    JSONObject trxData = api.parseResponseContent(response).getJSONObject("data");
    api.printJsonContent(trxData);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_usd")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_cny")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_btc")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eth")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_gbp")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eur")) > 0);
  }

}

