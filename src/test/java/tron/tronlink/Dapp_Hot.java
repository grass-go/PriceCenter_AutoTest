package tron.tronlink;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;

public class Dapp_Hot {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);

  @Test(enabled = true)
  public void hot_recommend() {

    response = TronlinkApiList.hot_recommend(node);
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
    }
  }

  @Test(enabled = true)
  public void hot_search(){
    response = TronlinkApiList.hot_search(node);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertTrue(responseArrayContent.size()>0);
  }

}
