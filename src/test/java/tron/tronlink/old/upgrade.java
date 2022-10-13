package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class upgrade extends TronlinkBase {
  private HttpResponse response;
  private JSONObject responseContent;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true, description = "Api upgrade test")
  public void upgrade() throws Exception {
    param.put("packageName","com.tronlinkpro.wallet");
    param.put("DownloadPlatform","");
    param.put("DeviceID","sdfsasdfsa");
    param.put("Lang","2");
    param.put("Version","4.10.0");
    param.put("System","Android");

    response = TronlinkApiList.upgrade(param);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() >= 5);
    Assert.assertTrue(responseContent.containsKey("latest_version"));

    JSONObject data = responseContent.getJSONObject("data");

//        Assert.assertTrue(!data.getString("internal_update_url").isEmpty());
//        Assert.assertTrue(!data.getString("google_apk_url").isEmpty());
    Assert.assertTrue(!data.getString("version").isEmpty());
    Assert.assertTrue(!data.getString("strategy").isEmpty());
//        Assert.assertTrue(!data.getString("url").isEmpty());

  }
}
