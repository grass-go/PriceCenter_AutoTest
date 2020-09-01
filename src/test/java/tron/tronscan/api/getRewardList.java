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
public class getRewardList {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONObject targetContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("httpapi.ip.list").get(0);

    /**
     * constructor.
     * 该地址为有投票，有奖励分成，奖励大于0
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "收取收益")
    public void getReward() {
        //Get response
        Map<String, String> Params = new HashMap<>();
        Params.put("address", "TSmZ71H9S6BQLdyGcr8QfG9qr92N6WUXKS");
        response = TronscanApiList.getReward(tronScanNode,Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //object
        Assert.assertTrue(responseContent.size() >= 1);
        Assert.assertTrue(Long.valueOf(responseContent.get("reward").toString()) > 0);
    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
