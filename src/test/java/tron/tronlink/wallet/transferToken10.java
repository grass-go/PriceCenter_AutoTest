package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class transferToken10 {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true,description = "get token10 transaction",groups={"NoSignature"})
  public void getToken10TransferLowVersionWithNoSig() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","0");
    param.put("reverse","true");
    param.put("trc10Id","1002881");
    response = TronlinkApiList.apiTransferToken10NoSig(param,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertNotEquals(null, responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertNotEquals(responseArrayContent, null);

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("block_timestamp"));
      Assert.assertTrue(jsonObject.containsKey("amount"));
      Assert.assertTrue(jsonObject.getInteger("amount")>0);
      Assert.assertTrue(jsonObject.containsKey("block"));
      Assert.assertTrue(jsonObject.containsKey("from"));
      Assert.assertTrue(jsonObject.containsKey("to"));
      Assert.assertTrue(jsonObject.containsKey("hash"));
      Assert.assertTrue(jsonObject.containsKey("confirmed"));
      Assert.assertTrue(jsonObject.containsKey("contract_type"));
      Assert.assertTrue(jsonObject.containsKey("symbol"));
      Assert.assertTrue(jsonObject.containsKey("issue_address"));
      Assert.assertTrue(jsonObject.containsKey("decimals"));
      Assert.assertTrue(jsonObject.containsKey("token_name"));
      Assert.assertTrue(jsonObject.containsKey("direction"));
    }
  }
  @Test(enabled = true,description = "get token10 transaction")
  public void getToken10TransferHighVersionWithNoSig() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","0");
    param.put("reverse","true");
    param.put("trc10Id","1002881");
    HashMap<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = TronlinkApiList.apiTransferToken10NoSig(param,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));

  }
  @Test(enabled = true,description = "get token10 transaction")
  public void Test000getToken10Transfer() throws Exception {
    //param.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"); //sophia's address
    param.put("address","4199AB9DF0BAB6385C5ECADE8B0F7A7E914502F6FC");
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","2");
    param.put("reverse","true");
    param.put("trc10Id","1002013");
    response = TronlinkApiList.apiTransferToken10(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertNotEquals(null, responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertNotEquals(responseArrayContent, null);

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("block_timestamp"));
      Assert.assertTrue(jsonObject.containsKey("amount"));
      //Assert.assertTrue(jsonObject.getInteger("amount")>0);
      Assert.assertTrue(jsonObject.containsKey("block"));
      Assert.assertTrue(jsonObject.containsKey("from"));
      Assert.assertTrue(jsonObject.containsKey("to"));
      Assert.assertTrue(jsonObject.containsKey("hash"));
      Assert.assertTrue(jsonObject.containsKey("confirmed"));
      Assert.assertTrue(jsonObject.containsKey("contract_type"));
      Assert.assertTrue(jsonObject.containsKey("symbol"));
      Assert.assertTrue(jsonObject.containsKey("issue_address"));
      Assert.assertTrue(jsonObject.containsKey("decimals"));
      Assert.assertTrue(jsonObject.containsKey("token_name"));
      Assert.assertTrue(jsonObject.containsKey("direction"));
    }
  }
}
