package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

public class upgrade {
  private HttpResponse response;
  private JSONObject responseContent;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = false, description = "Api upgrade test")
  public void upgrade() throws Exception {
    param.put("packageName","com.tronlink.wallet");
    param.put("DownloadPlatform","googlePlayAisle");
    param.put("DeviceID","sdfsasdfsa");
    param.put("Lang","1");
    param.put("Version","3.2.0");
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
