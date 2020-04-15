package tron.tronscan.external;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class MappingFees {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.获取映射手续费
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "获取映射手续费")
    public void getMappingFees() {
        response = TronscanApiList.getMappingFees(tronScanNode);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        targetContent = responseContent.getJSONObject("data");
        Assert.assertTrue(Long.valueOf(targetContent.get("retryFee").toString()) >= 0);
        Assert.assertTrue(Long.valueOf(targetContent.get("mappingFee").toString()) >= 0);
        Assert.assertTrue(Long.valueOf(targetContent.get("trxDepositMinValue").toString()) >= 0);
        Assert.assertTrue(Long.valueOf(targetContent.get("depositFee").toString()) >= 0);
        Assert.assertTrue(Long.valueOf(targetContent.get("withdrawFee").toString()) >= 0);
        Assert.assertTrue(Long.valueOf(targetContent.get("trxWithdrawMinValue").toString()) >= 0);

    }
    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
