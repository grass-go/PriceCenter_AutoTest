package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class versionLog extends TronlinkBase {
  private HttpResponse response;
  private HashMap<String,String> parameter = new HashMap();
  private HashMap<String,String> header = new HashMap();

  @Test(enabled = true, description = "Api GET /api/v1/wallet/version_log test")
  public void test01VersionLogForAndroid() throws Exception {
    parameter.put("lang", "2");
    parameter.put("system","Android");
    response = api.getVersionLog(parameter);

    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);

    Assert.assertEquals(data.getString("system"),"Android");
    Assert.assertTrue(!data.getString("create_time").isEmpty());
    Assert.assertTrue(!data.getString("title").isEmpty());
    Assert.assertTrue(!data.getString("version").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");

    parameter.put("lang", "1");
    parameter.put("system","Android");
    response = api.getVersionLog(parameter);

    jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    data = jsonObject.getJSONArray("data").getJSONObject(0);

    Assert.assertEquals(data.getString("system"),"Android");
    Assert.assertTrue(!data.getString("create_time").isEmpty());
    Assert.assertTrue(!data.getString("title").isEmpty());
    Assert.assertTrue(!data.getString("version").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");
  }
  @Test(enabled = true, description = "Api GET /api/v1/wallet/version_log test")
  public void test01VersionLogForHarmony() throws Exception {
    parameter.clear();
    parameter.put("lang", "2");
    parameter.put("system","Android");
    parameter.put("channel","harmony");
    header.put("package","wallet.tronlink.harmony");
    response = api.getVersionLog(parameter,header);

    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    String msg = jsonObject.getString("msg");
    Assert.assertEquals(msg, "success");

    // wait for prod has harmony versions.
    /*JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);

    Assert.assertEquals(data.getString("system"),"Android");
    Assert.assertTrue(!data.getString("create_time").isEmpty());
    Assert.assertTrue(!data.getString("title").isEmpty());
    Assert.assertTrue(!data.getString("version").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");*/

    msg = jsonObject.getString("msg");
    Assert.assertEquals(msg, "success");
    parameter.put("lang", "1");
    parameter.put("system","Android");
    response = api.getVersionLog(parameter,header);

    jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);


    // wait for prod has harmony versions.
    /*data = jsonObject.getJSONArray("data").getJSONObject(0);

    Assert.assertEquals(data.getString("system"),"Android");
    Assert.assertTrue(!data.getString("create_time").isEmpty());
    Assert.assertTrue(!data.getString("title").isEmpty());
    Assert.assertTrue(!data.getString("version").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");*/
  }

  @Test(enabled = true, description = "Api GET /api/v1/wallet/version_log test")
  public void test02VersionLogForIos() throws Exception {
    parameter.clear();
    parameter.put("lang", "2");
    parameter.put("system","iOS");
    parameter.put("download_platform", "appstore");
    response = api.getVersionLog(parameter);

    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);

    Assert.assertEquals(data.getString("system"),"iOS");
    Assert.assertTrue(!data.getString("create_time").isEmpty());
    Assert.assertTrue(!data.getString("title").isEmpty());
    Assert.assertTrue(!data.getString("version").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");

    parameter.put("lang", "1");
    parameter.put("system","iOS");
    parameter.put("download_platform", "appstore");
    response = api.getVersionLog(parameter);

    jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);
    data = jsonObject.getJSONArray("data").getJSONObject(0);

    Assert.assertEquals(data.getString("system"),"iOS");
    Assert.assertTrue(!data.getString("create_time").isEmpty());
    Assert.assertTrue(!data.getString("title").isEmpty());
    Assert.assertTrue(!data.getString("version").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");
  }

}