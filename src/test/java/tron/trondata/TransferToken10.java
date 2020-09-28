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


public class TransferToken10 extends TrondataBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;


  @Test(enabled = true,description = "getTransferTrx contain all from and to")
  public void Test001getTransferToken10All() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("trc10Id","1002881");
    params.put("direction","0");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferToken10(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertTrue(responseContent.getIntValue("page_size") > 0);
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("amount"));
      Assert.assertTrue(jsonObject.getLongValue("amount") > 0);
      Assert.assertTrue(jsonObject.containsKey("block_timestamp"));
      Assert.assertTrue(jsonObject.containsKey("from"));
      Assert.assertTrue(jsonObject.containsKey("to"));
      Assert.assertTrue(jsonObject.containsKey("hash"));
      Assert.assertTrue(jsonObject.containsKey("confirmed"));
      Assert.assertTrue(jsonObject.containsKey("contract_type"));
      Assert.assertEquals("ANC",jsonObject.getString("symbol"));
      Assert.assertEquals("TWpKN6y3NVXm5mMjGmPEtLpS6boGM4q8T4",jsonObject.getString("issue_address"));
      Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
      Assert.assertEquals("ANC",jsonObject.getString("token_name"));
      Assert.assertTrue(jsonObject.containsKey("direction"));
    }
  }

  @Test(enabled = true,description = "getTransferToken only from")
  public void Test002getTransferToken10From() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("trc10Id","1002881");
    params.put("direction","1");
    params.put("start_timestamp","1596610454000");
    params.put("end_timestamp","1596610464000");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferToken10(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(1,responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1,responseArrayContent.size());
    JSONObject jsonObject = responseArrayContent.getJSONObject(0);

    Assert.assertEquals("100000",jsonObject.getString("amount"));
    Assert.assertEquals(1596610464000L,jsonObject.getLongValue("block_timestamp"));
    Assert.assertEquals("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr",jsonObject.getString("from"));
    Assert.assertEquals("TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6",jsonObject.getString("to"));
    Assert.assertEquals("4f850eaa6b5232e13e0e4286b12ca0ef328ecff36f0d07dbb42f1bbafe6a1bc9",jsonObject.getString("hash"));
    Assert.assertEquals(1,jsonObject.getIntValue("confirmed"));
    Assert.assertEquals("TransferAssetContract",jsonObject.getString("contract_type"));
    Assert.assertEquals("ANC",jsonObject.getString("symbol"));
    Assert.assertEquals("TWpKN6y3NVXm5mMjGmPEtLpS6boGM4q8T4",jsonObject.getString("issue_address"));
    Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
    Assert.assertEquals("ANC",jsonObject.getString("token_name"));
    Assert.assertEquals(1,jsonObject.getIntValue("direction"));

  }

  @Test(enabled = true,description = "getTransferToken only to within the specified time range")
  public void Test003getTransferToken10To() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("trc10Id","1002881");
    params.put("direction","2");
    params.put("start_timestamp","1585128555000");
    params.put("end_timestamp","1594709226000");
    params.put("start","1");
    params.put("limit","1");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferToken10(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(1, responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1, responseArrayContent.size());
    JSONObject jsonObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals("10000000",jsonObject.getString("amount"));
    Assert.assertEquals(1585128555000L,jsonObject.getLongValue("block_timestamp"));
    Assert.assertEquals(18288726L,jsonObject.getLongValue("block"));
    Assert.assertEquals("TAPSHwnYjF5TvzDKgfUqaTovjmqDNoXHkz",jsonObject.getString("from"));
    Assert.assertEquals("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr",jsonObject.getString("to"));
    Assert.assertEquals("2c7d8ba33bf0a5ca81e898db187a1785bbae1952a5f3f9a1a1dfa302504d2a6a",jsonObject.getString("hash"));
    Assert.assertEquals(1,jsonObject.getIntValue("confirmed"));
    Assert.assertEquals("TransferAssetContract",jsonObject.getString("contract_type"));
    Assert.assertEquals("ANC",jsonObject.getString("symbol"));
    Assert.assertEquals("TWpKN6y3NVXm5mMjGmPEtLpS6boGM4q8T4",jsonObject.getString("issue_address"));
    Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
    Assert.assertEquals("ANC",jsonObject.getString("token_name"));
    Assert.assertEquals(2,jsonObject.getIntValue("direction"));
  }
}
