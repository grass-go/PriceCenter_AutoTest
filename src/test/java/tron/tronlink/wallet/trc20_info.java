package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;

import java.util.HashMap;

public class trc20_info {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true)
  public void Test000getTrc20Info() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    response = TronlinkApiList.trc20Info(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
     responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("tokenAddress"));
      Assert.assertTrue(jsonObject.containsKey("balance"));
      Assert.assertTrue(jsonObject.getDoubleValue("balance")>0);
      Assert.assertTrue(jsonObject.containsKey("balanceStr"));
      Assert.assertTrue(jsonObject.containsKey("decimals"));
      Assert.assertTrue(jsonObject.containsKey("logoUrl"));
      Assert.assertTrue(jsonObject.containsKey("name"));
      Assert.assertTrue(jsonObject.containsKey("shortName"));
      Assert.assertTrue(jsonObject.containsKey("isMapping"));
    }
  }
}
