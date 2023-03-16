package tron.tronlink.dapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Dapp_Classify extends TronlinkBase {

  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);
  private Map<String, String> header = new HashMap<>();
  private Map<String, String> params = new HashMap<>();

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

  @Test(enabled = true, description = "classify=0")
  public void dapp_classifyV3(){
    header.put("System","Android");
    header.put("Lang","1");
    params.put("address", quince_B58);
    params.put("classify","0");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONObject responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(11,responseArrayContent.size());

    header.put("Lang","2");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(11,responseArrayContent.size());

  }

  @Test(enabled = true, description = "classify=3")
  public void dapp_classifyV3_game(){
    header.put("System","Android");
    header.put("Lang","1");
    params.put("address", quince_B58);
    params.put("classify","3");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONObject responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1,responseArrayContent.size());

    header.put("Lang","2");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(1,responseArrayContent.size());
  }

  @Test(enabled = true, description = "classify=4")
  public void dapp_classifyV3_transaction(){
    header.put("System","Android");
    header.put("Lang","1");
    params.put("address", quince_B58);
    params.put("classify","4");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONObject responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(4,responseArrayContent.size());

    header.put("Lang","2");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(4,responseArrayContent.size());
  }

  @Test(enabled = true, description = "classify=7")
  public void dapp_classifyV3_other(){
    header.put("System","Android");
    header.put("Lang","1");
    params.put("address", quince_B58);
    params.put("classify","7");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONObject responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(6,responseArrayContent.size());

    header.put("Lang","2");
    response = TronlinkApiList.dappClassfyV3(params,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(6,responseArrayContent.size());


  }

}
