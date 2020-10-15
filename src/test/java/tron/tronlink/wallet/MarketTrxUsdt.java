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
    param.put("sort_type", "1");
    param.put("desc", "2");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("volume24h")>=jsonObject1.getDoubleValue("volume24h"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test001getMarketTrxUsdt() throws Exception {
    param.put("type","TRX"); //sophia's address
    param.put("sort_type", "1");
    param.put("desc", "1");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("volume24h")<=jsonObject1.getDoubleValue("volume24h"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test002getMarketTrxUsdt() throws Exception {
    param.put("type","TRX"); //sophia's address
    param.put("sort_type", "2");
    param.put("desc", "1");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("price")<=jsonObject1.getDoubleValue("price"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test003getMarketTrxUsdt() throws Exception {
    param.put("type","TRX"); //sophia's address
    param.put("sort_type", "2");
    param.put("desc", "2");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("price")>=jsonObject1.getDoubleValue("price"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test004getMarketTrxUsdt() throws Exception {
    param.put("type","TRX"); //sophia's address
    param.put("sort_type", "3");
    param.put("desc", "2");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("gain")>=jsonObject1.getDoubleValue("gain"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test005getMarketTrxUsdt() throws Exception {
    param.put("type","TRX"); //sophia's address
    param.put("sort_type", "3");
    param.put("desc", "1");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("gain")<=jsonObject1.getDoubleValue("gain"));
    }
  }


  @Test(enabled = true, description = "type usdt")
  public void Test006getMarketTrxUsdt() throws Exception {
    param.clear();
    param.put("type","USDT"); //sophia's address
    param.put("sort_type", "1");
    param.put("desc", "1");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("volume24h")<=jsonObject1.getDoubleValue("volume24h"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test007getMarketTrxUsdt() throws Exception {
    param.put("type","USDT"); //sophia's address
    param.put("sort_type", "1");
    param.put("desc", "2");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("volume24h")>=jsonObject1.getDoubleValue("volume24h"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test008getMarketTrxUsdt() throws Exception {
    param.put("type","USDT"); //sophia's address
    param.put("sort_type", "2");
    param.put("desc", "2");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("price")>=jsonObject1.getDoubleValue("price"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test009getMarketTrxUsdt() throws Exception {
    param.put("type","USDT"); //sophia's address
    param.put("sort_type", "2");
    param.put("desc", "1");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("price")<=jsonObject1.getDoubleValue("price"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test010getMarketTrxUsdt() throws Exception {
    param.put("type","USDT"); //sophia's address
    param.put("sort_type", "3");
    param.put("desc", "1");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("gain")<=jsonObject1.getDoubleValue("gain"));
    }
  }

  @Test(enabled = true,description = "type trx")
  public void Test011getMarketTrxUsdt() throws Exception {
    param.put("type","USDT"); //sophia's address
    param.put("sort_type", "3");
    param.put("desc", "2");
    response = TronlinkApiList.walletMarketTrxUsdt(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    responseArrayContent = data.getJSONArray("rows");

    //data object
    JSONObject jsonObject;
    JSONObject jsonObject1;
    for (int i=0;i<responseArrayContent.size();i++) {
      jsonObject = responseArrayContent.getJSONObject(i);

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
      if((i+1)==responseArrayContent.size()) break;
      jsonObject1 = responseArrayContent.getJSONObject(i+1);
      Assert.assertTrue(jsonObject.getDoubleValue("gain")>=jsonObject1.getDoubleValue("gain"));
    }
  }

  @Test(enabled = true,description = "type usdj")
  public void Test007getMarketTrxUsdj() throws Exception {
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
