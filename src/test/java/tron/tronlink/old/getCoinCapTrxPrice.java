package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class getCoinCapTrxPrice extends TronlinkBase {

  private HttpResponse response;
  @Test(enabled = true, description = "GetCoinCapTrxPrice test",groups={"NoSignature"})
  public void CoinCapTrxPriceLowVersionWithNoSig() throws Exception {
    response = api.getCoinCapTrxPriceNoSig(null);
    JSONObject trxData = api.parseResponse2JsonObject(response).getJSONObject("data");
    api.printJsonObjectContent(trxData);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_usd")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_cny")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_btc")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eth")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_gbp")) > 0);
    Assert.assertTrue(Double.valueOf(trxData.getString("price_eur")) > 0);
  }

  @Test(enabled = true, description = "GetCoinCapTrxPrice test")
  public void CoinCapTrxPriceHighVersionWithNoSig() throws Exception {
    HashMap<String, String> header = new HashMap<>();
    header.put("Version","4.12.0");
    header.put("System",api.defaultSys);
    response = api.getCoinCapTrxPriceNoSig(header);
    JSONObject trxData = api.parseResponse2JsonObject(response);
    org.junit.Assert.assertEquals(20004, trxData.getIntValue("code"));
    org.junit.Assert.assertEquals("Error param.", trxData.getString("message"));

  }

  @Test(enabled = true, description = "GetCoinCapTrxPrice test")
  public void test001CoinCapTrxPrice() throws Exception {
    HashMap<String, String> params = new HashMap<>();
    //params.put("address", quince_B58);
    response = api.getCoinCapTrxPrice(params);
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

