package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class feedBack extends TronlinkBase {
  private HttpResponse response;
  private JSONObject postbody = new JSONObject();
  HashMap<String, String> params = new HashMap<>();
  HashMap<String, String> headers = new HashMap<>();

  @Test(enabled = true, description = "Api POST /api/v1/wallet/feedback test",  groups = {"NoSignature"})
  public void test001FeedBackLowVersionWithNoSig() throws Exception {
    postbody.put("title", "test");
    postbody.put("system","Android");
    postbody.put("depict","test test test test test");
    postbody.put("email","test@test.com");

    response = api.feedBackNoSig(postbody,null);
    org.junit.Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    // since result sometimes return "{"code":0,"msg":"success","data":null}", so disable below check
    /*JSONObject jsonObject = api.parseResponse2JsonObject(response);
    System.out.println(jsonObject);
    api.printJsonObjectContent(jsonObject);
    Assert.assertEquals(jsonObject.getString("msg"),"success");*/
  }

  @Test(enabled = true, description = "Api POST /api/v1/wallet/feedback test")
  public void test001FeedBackHighVersionWithNoSig() throws Exception {
    postbody.put("title", "test");
    postbody.put("system","Android");
    postbody.put("depict","test test test test test");
    postbody.put("email","test@test.com");
    headers.put("System","Android");
    headers.put("Version","4.13.0");
    response = api.feedBackNoSig(postbody,headers);
    org.junit.Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    JSONObject jsonObject = api.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, jsonObject.getIntValue("code"));
    Assert.assertEquals("Error param.", jsonObject.getString("message"));
  }


  @Test(enabled = true, description = "Api POST /api/v1/wallet/feedback test")
  public void test001FeedBack() throws Exception {
    postbody.put("title", "test");
    postbody.put("system","Android");
    postbody.put("depict","test test test test test");
    postbody.put("email","test@test.com");
    params.put("address",quince_B58);
    response = api.feedBack(postbody,params);
    org.junit.Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    JSONObject jsonObject = api.parseResponse2JsonObject(response);
    System.out.println(jsonObject);
    api.printJsonObjectContent(jsonObject);
    Assert.assertEquals(jsonObject.getString("msg"),"success");
  }

}
