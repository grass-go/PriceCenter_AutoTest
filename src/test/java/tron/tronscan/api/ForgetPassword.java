package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class ForgetPassword {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONObject targetContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list").get(0);

    /**
     * constructor.
     * 忘记密码发送邮箱验证码
     *
     */
//    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "忘记密码，发送验证码接口")
//    public void postFPSendMail() {
//        //Get response
//        String email = "Holly.jiang@tron.network";
//        String g-recaptcha-response = "true";
//        response = TronscanApiList.postFPSendMail(tronScanNode,email,g-recaptcha-response);
//        log.info("code is " + response.getStatusLine().getStatusCode());
//        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
//        responseContent = TronscanApiList.parseResponseContent(response);
//        TronscanApiList.printJsonContent(responseContent);
//
//        //three object, "retCode" and "Data"
//        Assert.assertTrue(responseContent.size() >= 3);
//        Assert.assertTrue(responseContent.containsKey("retMsg"));
//        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
//        //data
//        Assert.assertTrue(responseContent.containsKey("data"));
//    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disConnect();
    }
}
