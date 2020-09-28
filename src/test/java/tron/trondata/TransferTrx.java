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


public class TransferTrx extends TrondataBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;


  @Test(enabled = true,description = "getTransferTrx contain all from and to")
  public void Test001getTransferTrxAll() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("direction","0");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferTrx(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(20, responseContent.getIntValue("page_size"));
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
      Assert.assertTrue(jsonObject.containsKey("symbol"));
      Assert.assertTrue(jsonObject.containsKey("issue_address"));
      Assert.assertTrue(jsonObject.containsKey("decimals"));
      Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
      Assert.assertTrue(jsonObject.containsKey("token_name"));
      Assert.assertTrue(jsonObject.containsKey("direction"));
    }
  }

  @Test(enabled = true,description = "getTransferTrx only from")
  public void Test002getTransferTrxFrom() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("direction","1");
    params.put("start_timestamp","1593573629009");
    params.put("end_timestamp","1594780242000");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferTrx(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(1, responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1, responseArrayContent.size());
    JSONObject jsonObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals("3000000",jsonObject.getString("amount"));
    Assert.assertEquals(1593573915000L,jsonObject.getLongValue("block_timestamp"));
    Assert.assertEquals("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr",jsonObject.getString("from"));
    Assert.assertEquals("TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6",jsonObject.getString("to"));
    Assert.assertEquals("26684a60097b5d681b5bdd4cc9eb49c828ce2776480596bbe69c1fa5baa01312",jsonObject.getString("hash"));
    Assert.assertEquals(1,jsonObject.getIntValue("confirmed"));
    Assert.assertEquals("TransferContract",jsonObject.getString("contract_type"));
    Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
    Assert.assertEquals(1,jsonObject.getIntValue("direction"));
  }

  @Test(enabled = true,description = "getTransferTrx only to within the specified time range")
  public void Test003getTransferTrxTo() {
    Map<String, String> params = new HashMap<>();
    params.put("address",queryAddress);
    params.put("direction","2");
    params.put("start_timestamp","1594708419000");
    params.put("end_timestamp","1596078660000");
    params.put("start","1");
    params.put("limit","1");
    params.put("db_version","1");
    response = TronlinkApiList.getTransferTrx(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("page_size"));
    Assert.assertEquals(1, responseContent.getIntValue("page_size"));
    Assert.assertTrue(responseContent.containsKey("data"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1, responseArrayContent.size());
    JSONObject jsonObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals("100000",jsonObject.getString("amount"));
    Assert.assertEquals(1594708437000L,jsonObject.getLongValue("block_timestamp"));
    Assert.assertEquals("TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6",jsonObject.getString("from"));
    Assert.assertEquals("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr",jsonObject.getString("to"));
    Assert.assertEquals("81753d26f1494261e7bb34fee0eed035919a5809cc7e0e086e996a9c9e92d6e7",jsonObject.getString("hash"));
    Assert.assertEquals(1,jsonObject.getIntValue("confirmed"));
    Assert.assertEquals("TransferContract",jsonObject.getString("contract_type"));
    Assert.assertEquals(6, jsonObject.getIntValue("decimals"));
    Assert.assertEquals(2,jsonObject.getIntValue("direction"));
  }
}
