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


public class TransferTrc20 extends TrondataBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;


  @Test(enabled = true,description = "getTransferTrc20 contain all from and to")
  public void Test001getTransferTrc20All() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("trc20Id","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("direction","0");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferTrc20(params);
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
      Assert.assertEquals("TPqv6RDJj82JmuZ7bQxqzNuosGkB9tTs9r",jsonObject.getString("issue_address"));
      Assert.assertTrue(jsonObject.containsKey("decimals"));
      Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
      Assert.assertEquals("WINK",jsonObject.getString("token_name"));
      Assert.assertTrue(jsonObject.containsKey("direction"));
    }
  }

  @Test(enabled = true,description = "getTransferTrc20 only from")
  public void Test002getTransferTrc20From() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("direction","1");
    params.put("trc20Id","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("start_timestamp","1594711731000");
    params.put("end_timestamp","1596614499000");
    params.put("start","2");
    params.put("limit","1");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferTrc20(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(1, responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1, responseArrayContent.size());
    JSONObject jsonObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals("1000",jsonObject.getString("amount"));
    Assert.assertEquals(1596613665000L,jsonObject.getLongValue("block_timestamp"));
    Assert.assertEquals(22114313L,jsonObject.getLongValue("block"));
    Assert.assertEquals("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr",jsonObject.getString("from"));
    Assert.assertEquals("TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6",jsonObject.getString("to"));
    Assert.assertEquals("109587104f51c2084b0807e8d18dc77b5b3231cbdd379223c6d1957540076cb1",jsonObject.getString("hash"));
    Assert.assertEquals(1,jsonObject.getIntValue("confirmed"));
    Assert.assertEquals("TriggerSmartContract",jsonObject.getString("contract_type"));
    Assert.assertEquals("TPqv6RDJj82JmuZ7bQxqzNuosGkB9tTs9r",jsonObject.getString("issue_address"));
    Assert.assertEquals("WINK",jsonObject.getString("token_name"));
    Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
    Assert.assertEquals(1,jsonObject.getIntValue("direction"));
  }

  @Test(enabled = true,description = "getTransferTrc20 only to within the specified time range")
  public void Test003getTransferTrc20To() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("direction","2");
    params.put("trc20Id","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("start_timestamp","1594711731000");
    params.put("end_timestamp","1596614499000");
    params.put("start","1");
    params.put("limit","1");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferTrc20(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(1, responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1, responseArrayContent.size());
    JSONObject jsonObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals("100000",jsonObject.getString("amount"));
    Assert.assertEquals(1594711731000L,jsonObject.getLongValue("block_timestamp"));
    Assert.assertEquals(21481217L,jsonObject.getLongValue("block"));
    Assert.assertEquals("TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6",jsonObject.getString("from"));
    Assert.assertEquals("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr",jsonObject.getString("to"));
    Assert.assertEquals("55a34da5b766c7ae59e13142f06c7d83f87bff97ab0b42fd28ebc68fd5ea0bfe",jsonObject.getString("hash"));
    Assert.assertEquals(1,jsonObject.getIntValue("confirmed"));
    Assert.assertEquals("TriggerSmartContract",jsonObject.getString("contract_type"));
    Assert.assertEquals("TPqv6RDJj82JmuZ7bQxqzNuosGkB9tTs9r",jsonObject.getString("issue_address"));
    Assert.assertEquals("WINK",jsonObject.getString("token_name"));
    Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
    Assert.assertEquals(2,jsonObject.getIntValue("direction"));
  }
}
