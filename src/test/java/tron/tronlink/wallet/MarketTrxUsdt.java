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

  @Test(enabled = true,description = "type trx")
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
      Assert.assertEquals(jsonObject.getString("sShortName"),"TRX");
      Assert.assertTrue(jsonObject.containsKey("fTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("sTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("sPrecision"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("unit"));
      Assert.assertTrue(jsonObject.containsKey("volume"));
      Assert.assertTrue(jsonObject.containsKey("volume24h"));
      Assert.assertTrue(jsonObject.containsKey("gain"));
    }
  }

  @Test(enabled = true, description = "type usdt")
  public void Test001getMarketTrxUsdt() throws Exception {
    param.clear();
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
      Assert.assertEquals(jsonObject.getString("sShortName"),"USDT");
      Assert.assertTrue(jsonObject.containsKey("fTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("sTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("sPrecision"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("unit"));
      Assert.assertTrue(jsonObject.containsKey("volume"));
      Assert.assertTrue(jsonObject.containsKey("volume24h"));
      Assert.assertTrue(jsonObject.containsKey("gain"));
    }
  }

  @Test(enabled = true,description = "type usdj")
  public void Test002getMarketTrxUsdj() throws Exception {
    param.clear();
    param.put("type","USDJ"); //sophia's address
    param.put("all","1"); //sophia's address
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    int total = data.getIntValue("total");
    responseArrayContent = data.getJSONArray("rows");
    if (total>0){
      Assert.assertEquals(total,responseArrayContent.size());
    }else {
      return;
    }
    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("fTokenName"));
      Assert.assertTrue(jsonObject.containsKey("sTokenName"));
      Assert.assertTrue(jsonObject.containsKey("fShortName"));
      Assert.assertTrue(jsonObject.containsKey("sShortName"));
      Assert.assertEquals(jsonObject.getString("sShortName"),"USDJ");
      Assert.assertTrue(jsonObject.containsKey("fTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("sTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("sPrecision"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("unit"));
      Assert.assertTrue(jsonObject.containsKey("volume"));
      Assert.assertTrue(jsonObject.containsKey("volume24h"));
      Assert.assertTrue(jsonObject.containsKey("gain"));
    }
  }
}
