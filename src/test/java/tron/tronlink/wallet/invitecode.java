package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

public class invitecode {
  private HttpResponse response;
  private JSONObject responseContent;
  private JSONObject addressJson = new JSONObject();

  @Test(enabled = true, description = "Api POST /api/wallet/invite/code/wallet/invite/code test")
  public void test001InviteCode() throws Exception {
//    addressJson.put("address", api.testAddressBase64);
    addressJson.put("invitedCode", "11Zm");
    addressJson.put("address", "TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    addressJson.put("system", "Android");
    addressJson.put("deviceId", "ffffefefe");
    response = TronlinkApiList.insertInviteCode(addressJson);

    JSONObject inviteCodeinfo = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(inviteCodeinfo);
    Assert.assertEquals(inviteCodeinfo.getString("message"),"User has been invited");
  }

}
