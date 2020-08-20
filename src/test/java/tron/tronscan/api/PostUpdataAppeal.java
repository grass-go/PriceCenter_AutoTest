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
public class PostUpdataAppeal {
    private JSONObject responseContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.更新appeal.post请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "更新appeal")
    public void postUpdataAppeal() {
        String appealIdness = "";
        response = TronscanApiList.postUpdataAppeal(tronScanNode,appealIdness);
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
     * constructor.交易所新增一个.post请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "交易所新增一个")
    public void postTrx_market() {
        String market = "";
        response = TronscanApiList.postTrx_market(tronScanNode,market);
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
     * constructor.展示列表
     *
     * 增加一个账户的token可展示列表
     *     URL：/external/account/addShowList
     *     请求方式：POST
     *     参数：
     *         address: 账户地址
     *         show_list: 添加的可展示token列表
     *         {
     *             "address": "TCLAwirSPHsrfVLqJUJB3iS4ZqKxu2kzZE",
     *             "show_list": [
     *                 "1000021",
     *                 "1000011"
     *             ]
     *         }
     *     返回：
     *         是否添加成功
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "账户的token可展示列表")
    public void postAddShowList() {
        //
        String address = "TZEZWXYQS44388xBoMhQdpL1HrBZFLfDpt";
        JSONArray jsonArray = new JSONArray(1);
        jsonArray.add("1000021");
        String showlist = jsonArray.toJSONString();

        response = TronscanApiList.postAddShowList(tronScanNode,address,showlist);
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
     * constructor.屏蔽列表
     *
     * 增加一个账户的token屏蔽列表
     *     URL：/external/account/addBlockList
     *     请求方式：POST
     *     参数：
     *         address: 账户地址
     *         show_list: 添加的屏蔽token列表
     *         {
     *             "address": "TCLAwirSPHsrfVLqJUJB3iS4ZqKxu2kzZE",
     *             "block_list": [
     *                 "1000021"
     *             ]
     *         }
     *     返回：
     *         是否添加成功
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "账户的token屏蔽列表")
    public void postDeleBlockList() {
        //
        String address = "TZEZWXYQS44388xBoMhQdpL1HrBZFLfDpt";
        JSONArray jsonArray = new JSONArray(1);
        jsonArray.add("1000021");
        String blocklist = jsonArray.toJSONString();

        response = TronscanApiList.postDeleBlockList(tronScanNode,address,blocklist);
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
