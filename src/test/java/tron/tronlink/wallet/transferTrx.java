package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class transferTrx {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;

  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true,description = "get only trx transaction")
  public void Test000getTrxTransfer() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","0"); //0：send and accept
    param.put("reverse","true");
    response = TronlinkApiList.apiTransferTrx(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertNotEquals( null,responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertNotEquals( null,responseArrayContent);

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
}
