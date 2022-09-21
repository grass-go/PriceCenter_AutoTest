package tron.tronlink.dapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.DjedApiList;
import tron.common.TronlinkApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

public class Dapp_Classify extends TronlinkBase {

  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);

  @Test(enabled = true)
  public void dapp_classify(){

    response = TronlinkApiList.classify();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseArrayContent = TronlinkApiList.parseResponse2Array(response);
    //data object
    for (Object json:responseArrayContent
    ) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("classify_id"));
      Assert.assertTrue(jsonObject.containsKey("name"));
      Assert.assertTrue(jsonObject.containsKey("weight"));
      Assert.assertTrue(jsonObject.containsKey("status"));
    }
//    Assert.assertTrue(responseArrayContent.size() == 5);
  }
}
