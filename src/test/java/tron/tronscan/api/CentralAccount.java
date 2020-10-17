package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.converters.IParameterSplitter;
import com.sun.jna.platform.win32.WinDef;

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
public class CentralAccount {
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
     * 功能: 激活链接，用户注册后的激活功能
     * 		请求方法: GET
     * 		入参:
     * 			email: 注册邮箱
     * 			token: 注册时生成的token
     * 		返回:
     * 			注册校验的结果，成功后跳转登陆页面，失败跳转失败页面。
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "激活链接，用户注册后的激活功能")
    public void getRegisterVerify() {
        //Get response
        String email = "Holly.jiang@tron.network";
        String token = "jianghong5215";
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("token",token);
        response = TronscanApiList.getActivation(tronScanNode,params);
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
     * 功能: 获取用户登陆状态
     * 		请求方法: GET
     * 		入参:
     * 			无
     * 		返回:
     * 			用户详情
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "获取用户登陆状态")
    public void getUserStat() {
        //Get response
        response = TronscanApiList.getUserStat(tronScanNode);
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
     * constructor.中心账户.获取用户全部标签
     * 打标签相关接口
     * 	注：这里的标签是中心化的打标签系统，因此需要保持登陆状态。这里保持登陆状态需要在header里面添加token和user-id两个字段
     * 		token: 登陆时返回的信息，后端存储，用于保持用户登陆状态的
     * 		user-id: 登陆时返回的用户相关信息中，包含用户的id这一项，需要添加到header里面
     * 	功能: 获取账户打的所有标签
     * 		请求方法: GET
     *   入参:
     * 			user_id: 用户id, 必填字段
     * 			start: 分页开始, 必填字段
     * 			limit: 每页数量, 必填字段
     * 			target_address: 被标记地址(非必填)
     * 	返回(与之前标签内容返回相同):
     * 			contract_map: 合约标记
     * 			user_tags: 用户所打标签
     * 			total: 标签总数
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "获取账户打的所有标签")
    public void getUserTag() {
        String user_id = "36326";
        String target_address = "TNaRAoLUyYEV2uF7GUrzSjRQTU8v5ZJ5VR";
        Map<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("start","0");
        params.put("limit","20");
        params.put("target_address",target_address);
        //Get response
        response = TronscanApiList.getUserTag(tronScanNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(!responseContent.get("retCode").toString().isEmpty());
        //data
//        Assert.assertTrue(responseContent.containsKey("data"));
        targetContent = responseContent.getJSONObject("data");

//        Assert.assertTrue(!targetContent.getString("contract_map").isEmpty());
//        Assert.assertTrue(Long.valueOf(targetContent.get("total").toString()) > 0);
//
//        responseArrayContent = targetContent.getJSONArray("user_tags");
//        JSONObject tags_ob = responseArrayContent.getJSONObject(0);
//        Assert.assertEquals(tags_ob.getString("userId"),user_id);
//        Assert.assertEquals(tags_ob.getString("targetAddress"),target_address);
//        Assert.assertTrue(!tags_ob.getString("tag").isEmpty());
//        Assert.assertTrue(tags_ob.containsKey("description"));
//        Assert.assertTrue(!tags_ob.getString("dateCreated").isEmpty());
//        Assert.assertTrue(!tags_ob.getString("dateUpdated").isEmpty());

    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
