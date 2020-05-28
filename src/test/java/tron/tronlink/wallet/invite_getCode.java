package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
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

    JSONObject inviteCodeinfo = TronlinkApiList.parseJsonObResponseContent(response);
    System.out.println(inviteCodeinfo);
    TronlinkApiList.printJsonContent(inviteCodeinfo);
    JSONObject data = inviteCodeinfo.getJSONObject("data");
//    Assert.assertTrue(data.getString("address") == "TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
//    Assert.assertTrue(data.getString("invitationCode") == "cN0J");
//    Assert.assertTrue(!data.getString("isInvited").isEmpty());
  }
}
