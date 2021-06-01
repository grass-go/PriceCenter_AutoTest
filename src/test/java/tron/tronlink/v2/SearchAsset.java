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
public class SearchAsset extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  @Test(enabled = true)
  public void searchAssetList01(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");
    params.put("address",addressNewAsset41);
    params.put("keyWord","TLa2f6V");
    params.put("page","1");
    params.put("count","10");

    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    int count =dataContent.getIntValue("count");
    Assert.assertEquals(1,count);
    array = dataContent.getJSONArray("token");
    JSONObject token = array.getJSONObject(0);
    Assert.assertEquals(2,token.getIntValue("type"));
    Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",token.getString("contractAddress"));
  }


}
