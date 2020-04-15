package tron.tronscan.external;

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
public class SideChainList {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "侧链列表")
    public void getSideChainList() {
        response = TronscanApiList.getSideChainList(tronScanNode);
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
        responseArrayContent = targetContent.getJSONArray("chains");
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        Assert.assertTrue(responseObject.containsKey("chainid"));
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("mainchain_gateway")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("sidechain_gateway")).matches());
        Assert.assertTrue(responseObject.containsKey("name"));
        Assert.assertTrue(responseObject.containsKey("rpc"));

    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
