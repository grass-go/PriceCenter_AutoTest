package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class getLatestAPK extends TronlinkBase {

  private HttpResponse response;
  @Test(enabled = true, description = "Api /api/v1/wallet/getLatestAPK  test")
  public void test001LatestAPK() throws Exception {
    response = api.getLatestAPK();

    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);

    String data = jsonObject.getString("data");

    org.testng.Assert.assertTrue(!data.isEmpty());
  }
}