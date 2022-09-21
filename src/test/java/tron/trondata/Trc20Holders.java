package tron.trondata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.trondata.base.TrondataBase;

import java.util.HashMap;
import java.util.Map;


public class Trc20Holders extends TrondataBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;


  @Test(enabled = true,description = "getTransferTrc20 holders")
  public void Test001getTrc20HodersAll() {
    Map<String, String> params = new HashMap<>();
    params.put("contract_address","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("start","0");
    params.put("limit","10");
    response = TronlinkApiList.getTrc20Holders(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(10,responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(10,responseArrayContent.size());
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("balance_str"));
      Assert.assertTrue(jsonObject.getDoubleValue("balance") >= 0);
      Assert.assertTrue(jsonObject.containsKey("total_supply"));
      Assert.assertEquals("WINkLink",jsonObject.getString("name"));
      Assert.assertTrue(jsonObject.containsKey("owner_address"));
      Assert.assertEquals("WIN",jsonObject.getString("short_name"));
      Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",jsonObject.getString("contract_address"));
    }
  }

  @Test(enabled = true,description = "get account Trc20 balance")
  public void Test002getAccountTrc20Balance() {
    Map<String, String> params = new HashMap<>();
    params.put("contract_address","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("holder_address",queryAddress);
    response = TronlinkApiList.getTrc20Holders(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(1,responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1,responseArrayContent.size());

    JSONObject jsonObject = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(jsonObject.containsKey("balance_str"));
    Assert.assertTrue(jsonObject.getDoubleValue("balance") >= 0);
    Assert.assertTrue(jsonObject.containsKey("total_supply"));
    Assert.assertEquals("WINkLink",jsonObject.getString("name"));
    Assert.assertTrue(jsonObject.containsKey("owner_address"));
    Assert.assertEquals("WIN",jsonObject.getString("short_name"));
    Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",jsonObject.getString("contract_address"));

  }

}
