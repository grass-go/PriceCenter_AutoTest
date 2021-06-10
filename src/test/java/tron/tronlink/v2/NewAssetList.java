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
public class NewAssetList extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONObject object;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  @Test(enabled = true)
  public void newAssetList01(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","0YrPyUSLIbE%2FxRE76n63OZXp2D4%3D");
    params.put("address",addressNewAsset41);

    response = TronlinkApiList.v2NewAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    int count =dataContent.getIntValue("count");
    Assert.assertEquals(1,count);
    array = dataContent.getJSONArray("token");
    Assert.assertEquals(1,array.size());
    object = array.getJSONObject(0);
    int type=object.getIntValue("type");
    Assert.assertEquals(1,type);
    Assert.assertEquals("1002962",object.getString("id"));
    Assert.assertTrue(object.getLongValue("balance")>0);
    Assert.assertFalse(object.getBooleanValue("isInAssets"));

  }


}
