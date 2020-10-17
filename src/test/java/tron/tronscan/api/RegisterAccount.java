package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class RegisterAccount {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONObject targetContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list").get(0);

    /**
     * constructor.
     * 功能: 点击创建用户后，对相关信息进行校验，并向注册邮箱发送激活链接
     * 		请求方法: POST
     * 		入参：
     * 			email: 注册邮箱
     * 			password: 注册密码，前端应进行加密后向后端传输加密后的密文。
     * 			verify_password: 再次输入的确认密码。前端应进行加密后向后端传输加密后的密文
     * 			location: 当前域名，用于拼接激活链接
     * 			g-recaptcha-response: 谷歌人机认证结果
     * 		返回:
     * 			成功或失败原因，详见错误码表
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "注册接口")
    public void postRegister() {
        //Get response
        String email = "Holly.jiang@tron.network";
        String password = "jianghong5215";
        String verify_password = "jianghong5215";
        String location = "";
        String g_recaptcha = "true";
        response = TronscanApiList.postRegister(tronScanNode,email,password,verify_password,location,g_recaptcha);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        Assert.assertTrue(responseContent.containsKey("data"));
    }


    /**
     * constructor.
     * 功能: 邮箱注册时，重新发送激活链接
     * 		请求方法: POST
     * 		入参:
     * 			email: 注册邮箱
     * 			location: 当前域名，用于拼接激活链接
     * 			g-recaptcha-response: 谷歌人机认证结果
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "邮箱注册时，重新发送激活链接")
    public void postRegisterResend() {
        //Get response
        String email = "Holly.jiang@tron.network";
        String location = "";
        String g_recaptcha = "true";
        response = TronscanApiList.postRegisterResend(tronScanNode,email,location,g_recaptcha);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        Assert.assertTrue(responseContent.containsKey("data"));
    }
    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disConnect();
    }
}
