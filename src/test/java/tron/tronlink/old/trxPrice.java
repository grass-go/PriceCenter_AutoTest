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
    Assert.assertTrue(!trxData.getString("price_usd").isEmpty());
    Assert.assertTrue(!trxData.getString("price_cny").isEmpty());
    Assert.assertTrue(!trxData.getString("price_btc").isEmpty());
    Assert.assertTrue(!trxData.getString("price_eth").isEmpty());
    Assert.assertTrue(!trxData.getString("price_gbp").isEmpty());
    Assert.assertTrue(!trxData.getString("price_eur").isEmpty());

  }

}

