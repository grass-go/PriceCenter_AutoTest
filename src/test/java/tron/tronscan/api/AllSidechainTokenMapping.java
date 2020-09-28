package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class AllSidechainTokenMapping {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    /**
     * constructor.主侧链币对应关系
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "主侧链币对应关系")
    public void getAllSidechainTokenMapping() {
        response = TronscanApiList.getAllSidechainTokenMapping(tronScanNode);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() == 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        targetContent = responseContent.getJSONObject("data");
        responseArrayContent = targetContent.getJSONArray("sidechainTokens");
        Assert.assertTrue(responseArrayContent.size() > 0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("chainid").toString().isEmpty());
            Assert.assertEquals(responseArrayContent.getJSONObject(i).get("chainid").toString(),"413AF23F37DA0D48234FDD43D89931E98E1144481B");
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("mainchain_address")).matches());
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("sidechain_address")).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("type"));
        }
    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
