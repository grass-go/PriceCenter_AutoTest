package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;

public class marketPairList extends TronlinkBase {
  private HttpResponse response;
  int trxNumAndroid370;
  int trxNumIos370;
  int trxNumAndroid360;
  int trxNumIos360;

  @Test(enabled = true, description = "Api GET /api/exchange/marketPair/list test android 3.7.0 ")
  public void test001MarketPairList() throws Exception {
    HashMap<String,String> header = new HashMap<>();
    header.put("Lang","1");
    header.put("Version","3.7.0");
    header.put("DeviceID","1111111111");
    header.put("chain","MainChain");
    header.put("packageName","com.tronlinkpro.wallet");
    header.put("System","Android");
    response = api.marketPairList(header);
    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    JSONObject data = jsonObject.getJSONObject("data");
    JSONArray rows = data.getJSONArray("rows");
    trxNumAndroid370 = rows.size();
    Assert.assertTrue(trxNumAndroid370 > 0);
    for (Object exchange : rows){
      JSONObject exchangeJSON = (JSONObject) JSON.toJSON(exchange);
      Assert.assertTrue(!exchangeJSON.getString("fTokenName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("id").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("volume").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("gain").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("price").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("fShortName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("sShortName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("fTokenAddr").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("unit").isEmpty());
    }
  }

  @Test(enabled = true, description = "Api GET /api/exchange/marketPair/list ios 3.7.0")
  public void test002MarketPairList() throws Exception {
    HashMap<String,String> header = new HashMap<>();
    header.put("Lang","1");
    header.put("Version","3.7.0");
    header.put("DeviceID","1111111111");
    header.put("chain","MainChain");
    header.put("packageName","com.tronlinkpro.wallet");
    header.put("System","iOS");
    response = api.marketPairList(header);
    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    JSONObject data = jsonObject.getJSONObject("data");
    JSONArray rows = data.getJSONArray("rows");
    trxNumIos370 = rows.size();
    Assert.assertEquals(trxNumAndroid370,trxNumIos370);
    for (Object exchange : rows){
      JSONObject exchangeJSON = (JSONObject) JSON.toJSON(exchange);
      Assert.assertTrue(!exchangeJSON.getString("fTokenName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("id").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("volume").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("gain").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("price").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("fShortName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("sShortName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("fTokenAddr").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("unit").isEmpty());
    }
  }

  @Test(enabled = true, description = "Api GET /api/exchange/marketPair/list ios 3.6.0")
  public void test003MarketPairList() throws Exception {
    HashMap<String,String> header = new HashMap<>();
    header.put("Lang","1");
    header.put("Version","3.6.0");
    header.put("DeviceID","1111111111");
    header.put("chain","MainChain");
    header.put("packageName","com.tronlinkpro.wallet");
    header.put("System","iOS");
    response = api.marketPairList(header);
    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    JSONObject data = jsonObject.getJSONObject("data");
    JSONArray rows = data.getJSONArray("rows");
    trxNumIos360 = rows.size();
    Assert.assertTrue(trxNumIos370 > trxNumIos360);
    for (Object exchange : rows){
      JSONObject exchangeJSON = (JSONObject) JSON.toJSON(exchange);
      Assert.assertTrue(!exchangeJSON.getString("fTokenName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("id").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("volume").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("gain").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("price").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("fShortName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("sShortName").isEmpty());
      Assert.assertEquals("TRX",exchangeJSON.getString("sShortName"));
      Assert.assertTrue(!exchangeJSON.getString("fTokenAddr").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("unit").isEmpty());
    }
  }

  @Test(enabled = true, description = "Api GET /api/exchange/marketPair/list test android 3.6.0 ")
  public void test004MarketPairList() throws Exception {
    HashMap<String,String> header = new HashMap<>();
    header.put("Lang","1");
    header.put("Version","3.6.0");
    header.put("DeviceID","1111111111");
    header.put("chain","MainChain");
    header.put("packageName","com.tronlinkpro.wallet");
    header.put("System","Android");
    response = api.marketPairList(header);
    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    JSONObject data = jsonObject.getJSONObject("data");
    JSONArray rows = data.getJSONArray("rows");
    trxNumAndroid360 = rows.size();
    Assert.assertEquals(trxNumIos360,trxNumAndroid360);
    for (Object exchange : rows){
      JSONObject exchangeJSON = (JSONObject) JSON.toJSON(exchange);
      Assert.assertTrue(!exchangeJSON.getString("fTokenName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("id").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("volume").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("gain").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("price").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("fShortName").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("sShortName").isEmpty());
      Assert.assertEquals("TRX",exchangeJSON.getString("sShortName"));
      Assert.assertTrue(!exchangeJSON.getString("fTokenAddr").isEmpty());
      Assert.assertTrue(!exchangeJSON.getString("unit").isEmpty());
    }
  }

}
