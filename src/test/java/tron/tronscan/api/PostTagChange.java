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
public class PostTagChange {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    /**
     * constructor.更新标签.POST请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "更新标签")
    public void postTagUpdate() {
        int limit = 20;
        String user_address = "TDaVYpgwV1cBuM2p4byRpgZrBmnoLUqE1n";
        String target_address = "TXTZtfVD7ghfjSfVrnPwDFowoYXaV6cp9Y";
        String tag = "测试插入";
        String description = "成功";

        response = TronscanApiList.postTagUpdate(tronScanNode,user_address,target_address,tag,description);
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
     * constructor.增加标签.POST请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "增加标签")
    public void postTagInsert() {
        int limit = 20;
        String user_address = "TDaVYpgwV1cBuM2p4byRpgZrBmnoLUqE1n";
        String target_address = "TXTZtfVD7ghfjSfVrnPwDFowoYXaV6cp9Y";
        String tag = "测试插入";
        String description = "成功";

        response = TronscanApiList.postTagInsert(tronScanNode,user_address,target_address,tag,description);
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
     * constructor.删除标签.POST请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "删除标签")
    public void postTagDelete() {
        int limit = 20;
        String user_address = "TDaVYpgwV1cBuM2p4byRpgZrBmnoLUqE1n";
        String target_address = "TXTZtfVD7ghfjSfVrnPwDFowoYXaV6cp9Y";

        response = TronscanApiList.postTagDetele(tronScanNode,user_address,target_address);
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
