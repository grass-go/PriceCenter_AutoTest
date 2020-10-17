package tron.tronscan.api;

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
public class Trc20Tokens {
    private JSONObject responseContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    /**
     * constructor.新增trc20信息.post请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "新增trc20信息")
    public void postTrc20Tokens() {
        String issuer_addr = "TDaVYpgwV1cBuM2p4byRpgZrBmnoLUqE1n";
        response = TronscanApiList.postTrc20Tokens(tronScanNode,issuer_addr);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) > 0);
        //data
        Assert.assertTrue(responseContent.containsKey("data"));

    }

    /**
     * constructor.更新trc20信息.post请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "更新trc20信息")
    public void postUpdata20Tokens() {
        String issuer_addr = "TDaVYpgwV1cBuM2p4byRpgZrBmnoLUqE1n";
        response = TronscanApiList.postUpdata20Tokens(tronScanNode,issuer_addr);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) > 0);
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
