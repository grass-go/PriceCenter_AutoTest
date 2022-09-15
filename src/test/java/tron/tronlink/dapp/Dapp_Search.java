package tron.tronlink.dapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

public class Dapp_Search extends TronlinkBase {

  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);
  @Test(enabled = true)
  public void dapp_search(){
    Map<String, String> params = new HashMap<>();
    params.put("name","JUST");
    response = TronlinkApiList.search(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    for (Object json:responseArrayContent
    ) {
      System.out.println(json);
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("classify_id"));
      Assert.assertTrue(jsonObject.containsKey("name"));
      Assert.assertTrue(jsonObject.containsKey("image_url"));
      Assert.assertTrue(jsonObject.containsKey("home_url"));
      Assert.assertTrue(jsonObject.containsKey("slogan"));
      Assert.assertTrue(jsonObject.containsKey("is_roll"));
    }
    System.out.println(responseArrayContent.size());
    Assert.assertTrue(responseArrayContent.size() >= 0);
  }

  @Test(enabled = true)
  public void dapp_searchV3() throws Exception {
    response = TronlinkApiList.dapp_V3search(quince_B58,"iOSTest","http");
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    responseArrayContent = responseContent.getJSONArray("data");
    for (Object json:responseArrayContent) {
      System.out.println(json);
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("classifyId"));
      Assert.assertTrue(jsonObject.containsKey("name"));
      Assert.assertTrue(jsonObject.containsKey("imageUrl"));
      Assert.assertTrue(jsonObject.containsKey("homeUrl"));
    }
    System.out.println(responseArrayContent.size());
    Assert.assertTrue(responseArrayContent.size() >= 0);
  }


}
