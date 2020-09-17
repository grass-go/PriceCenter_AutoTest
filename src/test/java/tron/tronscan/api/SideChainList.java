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
public class SideChainList {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.侧链列表
     * 结果是固定
     * 地址有变动
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "侧链列表")
    public void getSideChainList() {
        response = TronscanApiList.getSideChainList(tronScanNode);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() == 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) == 0);
        //data
        targetContent = responseContent.getJSONObject("data");
        responseArrayContent = targetContent.getJSONArray("chains");
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        Assert.assertEquals(responseObject.getString("chainid"),"413AF23F37DA0D48234FDD43D89931E98E1144481B");
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("mainchain_gateway")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("sidechain_gateway")).matches());
        Assert.assertEquals(responseObject.getString("name"),"公链部-dapp测试链");
        Assert.assertTrue(responseObject.getString("rpc").substring(0,7).equals("http://"));

    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
