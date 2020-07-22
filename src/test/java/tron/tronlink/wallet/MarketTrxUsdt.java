package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class MarketTrxUsdt {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = false,description = "type trx")
  public void Test000getMarketTrxUsdt() throws Exception {
    param.put("type","TRX"); //sophia's address
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("fTokenName"));
      Assert.assertTrue(jsonObject.containsKey("sTokenName"));
      Assert.assertTrue(jsonObject.containsKey("fShortName"));
      Assert.assertTrue(jsonObject.containsKey("sShortName"));
      Assert.assertTrue(jsonObject.containsKey("fTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("sTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("sPrecision"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("unit"));
      Assert.assertEquals(jsonObject.getString("unit"),"TRX");
      Assert.assertTrue(jsonObject.containsKey("volume"));
      Assert.assertTrue(jsonObject.containsKey("volume24h"));
      Assert.assertTrue(jsonObject.containsKey("gain"));
    }
  }

  @Test(enabled = false, description = "type usdt")
  public void Test001getMarketTrxUsdt() throws Exception {
    param.put("type","USDT"); //sophia's address
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("fTokenName"));
      Assert.assertTrue(jsonObject.containsKey("sTokenName"));
      Assert.assertTrue(jsonObject.containsKey("fShortName"));
      Assert.assertTrue(jsonObject.containsKey("sShortName"));
      Assert.assertTrue(jsonObject.containsKey("fTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("sTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("sPrecision"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("unit"));
      Assert.assertEquals(jsonObject.getString("unit"),"USDT");
      Assert.assertTrue(jsonObject.containsKey("volume"));
      Assert.assertTrue(jsonObject.containsKey("volume24h"));
      Assert.assertTrue(jsonObject.containsKey("gain"));
    }
  }

  @Test(enabled = false,description = "type usdj")
  public void Test002getMarketTrxUsdt() throws Exception {
    param.put("type","USDJ"); //sophia's address
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("fTokenName"));
      Assert.assertTrue(jsonObject.containsKey("sTokenName"));
      Assert.assertTrue(jsonObject.containsKey("fShortName"));
      Assert.assertTrue(jsonObject.containsKey("sShortName"));
      Assert.assertTrue(jsonObject.containsKey("fTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("sTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("sPrecision"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("unit"));
      Assert.assertEquals(jsonObject.getString("unit"),"USDJ");
      Assert.assertTrue(jsonObject.containsKey("volume"));
      Assert.assertTrue(jsonObject.containsKey("volume24h"));
      Assert.assertTrue(jsonObject.containsKey("gain"));
    }
  }
}
