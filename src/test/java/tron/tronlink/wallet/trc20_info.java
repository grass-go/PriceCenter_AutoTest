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
  private HashMap<String,String> header = new HashMap<>();

  @Test(enabled = true,groups = {"NoSignature"})
  public void Test000getTrc20InfoLowVersionWithNoSig() throws Exception {
    param.put("address", "TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    response = TronlinkApiList.trc20InfoNoSig(param, null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
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

  @Test(enabled = true)
  public void Test000getTrc20InfoHighVersionWithNoSig() throws Exception {
    header.put("System", "Android");
    header.put("Version", "4.11.0");
    param.put("address", "TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    response = TronlinkApiList.trc20InfoNoSig(param, header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true)
  public void Test000getTrc20Info() throws Exception {
    header.put("System","Android");
    header.put("Version","4.11.0");
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    response = TronlinkApiList.trc20Info(param,header);
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
