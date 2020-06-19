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
public class CentralLogin {
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
     * 中心化登录接口
     * 邮箱登陆或手机登陆。首先会判断email是否为空，若不为空，则为email登陆。若email为空，则去判断mobile是否为空，
     * 为空则报错，不为空则去判断验证码等信息。
     * 本case说明：email为不空
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "中心化登录")
    public void postCentrallogin() {
        //Get response
        String email = "Holly.jiang@tron.network";
        String password = "jianghong5215";
        String mobile = "";
        String token = "";
        response = TronscanApiList.postCentrallogin(tronScanNode,email,password,mobile,token);
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
     * 中心化登录接口
     * 邮箱登陆或手机登陆。首先会判断email是否为空，若不为空，则为email登陆。若email为空，则去判断mobile是否为空，
     * 为空则报错，不为空则去判断验证码等信息。
     * 本case说明：email为空，mobile不为空
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "中心化登录")
    public void postEmailNull() {
        //Get response
        String email = "";
        String password = "";
        String mobile = "+8617600220299";
        String token = "";
        response = TronscanApiList.postCentrallogin(tronScanNode,email,password,mobile,token);
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
     * 中心化登录接口
     * 邮箱登陆或手机登陆。首先会判断email是否为空，若不为空，则为email登陆。若email为空，则去判断mobile是否为空，
     * 为空则报错，不为空则去判断验证码等信息。
     * 本case说明：email为空，mobile为空
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "中心化登录")
    public void postAllNull() {
        //Get response
        String email = "";
        String password = "";
        String mobile = "";
        String token = "";
        response = TronscanApiList.postCentrallogin(tronScanNode,email,password,mobile,token);
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
     * 手机号登录发送验证码
     * 手机号登陆时，发送验证码功能
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "手机号登录发送验证码")
    public void postMobilLogin() {
        //Get response
        String validate = "true";
        String mobile = "+8617600220299";
        response = TronscanApiList.postMobilLogin(tronScanNode,validate,mobile);
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
     * 登陆状态下修改密码
     * 这里需要在登陆状态下才可对密码进行修改。需在header中添加token和user-id两个字段
     * 		token: 登陆时返回的信息，后端存储，用于保持用户登陆状态的
     * 		user-id: 登陆时返回的用户相关cookies信息中，包含用户的id这一项，需要添加到header里面
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "登陆状态下修改密码")
    public void postResetPassword() {
        //Get response
        String user_id = "d0216aab31f8638269b106f139a24ff531592531574";
        String origin_password = "jianghong5215";
        String new_password = "jianghong5215";
        String verify_password = "jianghong5215";
        response = TronscanApiList.postResetPassword(tronScanNode,user_id,origin_password,new_password,verify_password);
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
     * 退出登录
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "退出登录")
    public void postLogout() {
        //Get response
        String user_id = "d0216aab31f8638269b106f139a24ff531592531574";
        response = TronscanApiList.postLogout(tronScanNode,user_id);
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
