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
     * 功能: 忘记密码，发送验证码接口
     * 		请求方法: POST
     * 		入参:
     * 			email: 邮箱
     * 			g-recaptcha-response: 谷歌人机认证结果
     * 		返回:
     * 			成功或失败原因，详见错误码表
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "忘记密码，发送验证码接口")
    public void postFPSendMail() {
        //Get response
        String email = "Holly.jiang@tron.network";
        String g_recaptcha = "true";
        response = TronscanApiList.postFPSendMail(tronScanNode,email,g_recaptcha);
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
     * 功能: 收到邮箱验证码后，输入验证码，后端对验证码进行校验
     * 		请求方法: POST
     * 		入参:
     * 			email: 邮箱
     * 			code: 验证码
     * 		返回:
     * 			token: 修改密码时所需的token
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "修改密码验证码校验")
    public void postFPVerify() {
        //Get response
        String email = "Holly.jiang@tron.network";
        String code = "417426";
        response = TronscanApiList.postFPVerify(tronScanNode,email,code);
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
     * 功能: 修改密码
     * 		请求方法: POST
     * 		入参:
     * 			email: 邮箱
     * 			token: 验证码校验后返回的token
     * 			password: 密码
     * 			verify_password: 再次输入的确认密码。
     * 		返回:
     * 			成功或失败原因，详见错误码表
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "修改密码")
    public void postFPassword() {
        //Get response
        String email = "Holly.jiang@tron.network";
        String token = "417426";
        String password = "12345";
        String verify_password = "12345";
        response = TronscanApiList.postFPassword(tronScanNode,email,token,password,verify_password);
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
     * constructor.中心账户下标签
     * 打标签相关接口
     * 	注：这里的标签是中心化的打标签系统，因此需要保持登陆状态。这里保持登陆状态需要在header里面添加token和user-id两个字段
     * 		token: 登陆时返回的信息，后端存储，用于保持用户登陆状态的
     * 		user-id: 登陆时返回的用户相关信息中，包含用户的id这一项，需要添加到header里面
     * 		入参:
     * 			user_id: 用户id
     * 			target_address: 被标记地址
     * 			tag: 打的标签，长度不超过20
     * 			description: 标签备注
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "中心账户插入标签")
    public void postCenTagInsert() {
        //Get response
        String user_id = "d0216aab31f8638269b106f139a24ff531592531574";
        String target_address = "TSmZ71H9S6BQLdyGcr8QfG9qr92N6WUXKS";
        String tag = "中心账户测试Tag";
        String description = "测试插入Tag";
        response = TronscanApiList.postCenTagInsert(tronScanNode,user_id,target_address,tag,description);
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
     * constructor.修改标签
     * 入参:
     * 			user_id: 用户id
     * 			target_address: 被标记地址
     * 			tag: 打的标签，长度不超过20
     * 			description: 标签备注
     * 		返回:
     * 			所有参数都会进行校验，校验不通过会返回错误信息和错误码。
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "中心账户修改标签")
    public void postCenTagUpdate() {
        //Get response
        String user_id = "d0216aab31f8638269b106f139a24ff531592531574";
        String target_address = "TSmZ71H9S6BQLdyGcr8QfG9qr92N6WUXKS";
        String tag = "中心账户测试Tag22222";
        String description = "测试插入Tag22222";
        response = TronscanApiList.postCenTagUpdate(tronScanNode,user_id,target_address,tag,description);
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
     * 功能: 删除标签
     * 		请求方法: POST
     * 		入参:
     * 			user_id: 用户id
     * 			target_address: 被标记地址
     * 		返回:
     * 			所有参数都会进行校验，校验不通过会返回错误信息和错误码。
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "中心账户删除标签")
    public void postCenTagDelete() {
        //Get response
        String user_id = "d0216aab31f8638269b106f139a24ff531592531574";
        String target_address = "TSmZ71H9S6BQLdyGcr8QfG9qr92N6WUXKS";
        response = TronscanApiList.postCenTagDelete(tronScanNode,user_id,target_address);
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
