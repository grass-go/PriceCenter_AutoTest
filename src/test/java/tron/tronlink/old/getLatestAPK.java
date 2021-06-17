package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class getLatestAPK extends TronlinkBase {

  private HttpResponse response;
  @Test(enabled = true, description = "Api /api/v1/wallet/getLatestAPK  test")
  public void test001LatestAPK() throws Exception {
    response = api.getLatestAPK();

    JSONObject jsonObject = api.parseResponseContent(response);
    JSONObject jsonData = jsonObject.getJSONObject("data");
    api.printJsonContent(jsonObject);
    Assert.assertTrue(TronlinkApiList.urlCanVisited(jsonData.getString("testflight"),6000 ));
    //Assert.assertTrue(TronlinkApiList.urlCanVisited(jsonData.getString("url"),6000 ));
    Assert.assertTrue(TronlinkApiList.urlCanVisited(jsonData.getString("china_url"),6000 ));
  }
}