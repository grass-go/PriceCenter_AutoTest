package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

public class invite_getCode {

  private HttpResponse response;
  private JSONObject responseContent;
  private JSONObject parameter = new JSONObject();

  @Test(enabled = true, description = "Api POST /api/wallet/invite/get_code test")
  public void test001InviteGetCode() throws Exception {
    // 线上环境暂时报错
    parameter.put("address", "TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    response = TronlinkApiList.getInviteCode(parameter);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    JSONObject inviteCodeinfo = TronlinkApiList.parseResponse2JsonObject(response);

    TronlinkApiList.printJsonObjectContent(inviteCodeinfo);
    JSONObject data = inviteCodeinfo.getJSONObject("data");
    Assert.assertTrue(data.containsKey("address"));
    Assert.assertEquals("TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe",data.getString("address"));
    Assert.assertTrue(data.containsKey("invitationCode"));
    Assert.assertNotEquals("",data.getString("invitationCode"));
    Assert.assertNotEquals("0000",data.getString("invitationCode"));
  }
}
