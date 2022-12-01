package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;

public class trxPrice extends TronlinkBase {
  private HttpResponse response;
  private JSONObject responseContent;

  @Test(enabled = true, description = "Api /api/v1/wallet/trxPrice test", groups = {"NoSignature"})
  public void test001TrxPriceLowVersionWithNoSig() throws Exception {
    response = api.trxPriceNoSig(null);
    JSONObject trxData = api.parseResponse2JsonObject(response).getJSONObject("data");
    api.printJsonObjectContent(trxData);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_usd")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_cny")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_btc")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eth")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_gbp")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eur")) > 0);
  }

  @Test(enabled = true, description = "Api /api/v1/wallet/trxPrice test")
  public void test001TrxPriceHighVersionWithNoSig() throws Exception {
    HashMap<String, String> header = new HashMap<>();
    header.put("System","Android");
    header.put("Version","4.12.0");
    response = api.trxPriceNoSig(header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true, description = "Api /api/v1/wallet/trxPrice test")
  public void test001TrxPrice() {
    HashMap<String, String> params = new HashMap<>();
    //params.put("address",quince_B58);
    response = api.trxPrice(params);
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

