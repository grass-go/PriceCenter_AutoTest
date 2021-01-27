package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AssetList extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();


  @Test(enabled = true)
  public void assetList01(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","66f37xLdCz%2FV9geQGc%2FhYd98HR0%3D");
    params.put("address",addressNewAsset41);

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokenArray = dataContent.getJSONArray("token");
    int tokenNum = tokenArray.size();
    Assert.assertEquals(1,tokenNum);
    JSONObject token = tokenArray.getJSONObject(0);
    Assert.assertEquals(0,token.getIntValue("type"));
    Assert.assertEquals(1,token.getIntValue("top"));
    Assert.assertEquals("",token.getString("id"));
    Assert.assertEquals("",token.getString("contractAddress"));
    Assert.assertTrue(token.getLongValue("balance")>0);

  }


}
