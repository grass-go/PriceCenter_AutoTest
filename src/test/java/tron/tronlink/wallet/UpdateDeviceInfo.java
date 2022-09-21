package tron.tronlink.wallet;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.Map;

@Slf4j
public class UpdateDeviceInfo extends TronlinkBase {
    private static HttpResponse response;
    private JSONObject responseContent;

    @Test(enabled = true)
    public void test001UpdateDeviceInfo() {
        String requestUrl = TronlinkBase.tronlinkUrl + "/api/wallet/push/UpdateDeviceInfo";
        response = TronlinkApiList.createGetConnect(requestUrl, null,null, null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
        Assert.assertEquals("OK",responseContent.getString("message"));
        Assert.assertEquals("",responseContent.getString("data"));
    }

}
