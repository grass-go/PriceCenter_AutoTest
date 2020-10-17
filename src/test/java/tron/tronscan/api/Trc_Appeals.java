package tron.tronscan.api;

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
public class Trc_Appeals {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    /**
     * constructor.trc请求
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "trc请求")
    public void getTrcAppeals() {
        String address = "TSmZ71H9S6BQLdyGcr8QfG9qr92N6WUXKS";
        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        response = TronscanApiList.getTrc_Appeals(tronScanNode,params);
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
//        responseArrayContent = targetContent.getJSONArray("appeals");
        Assert.assertTrue(targetContent.containsKey("appeals"));
//        JSONObject responseObject = responseArrayContent.getJSONObject(0);
//        Assert.assertTrue(responseObject.containsKey("id"));
//        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
//        Assert.assertTrue(patternAddress.matcher(responseObject.getString("address")).matches());
//        Assert.assertEquals(responseObject.getString("address"),address);
//        Assert.assertTrue(!responseObject.get("reasons").toString().isEmpty());
//        Assert.assertTrue(responseObject.containsKey("content"));
//        Assert.assertTrue(responseObject.containsKey("status"));
//        Assert.assertTrue(!responseObject.get("update_time").toString().isEmpty());
//        Assert.assertTrue(!responseObject.get("create_time").toString().isEmpty());

    }

    /**
     * constructor.{id}无参数
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "{id}无参数.1")
    public void getTrcAppeals_1() {
        response = TronscanApiList.getTrc_Appeals_1(tronScanNode);
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
        JSONObject responseObject = targetContent.getJSONObject("appeal");
        Assert.assertTrue(responseObject.containsKey("id"));
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("address")).matches());
        Assert.assertTrue(!responseObject.get("reasons").toString().isEmpty());
        Assert.assertTrue(responseObject.containsKey("content"));
        Assert.assertTrue(responseObject.containsKey("status"));
        Assert.assertTrue(!responseObject.get("update_time").toString().isEmpty());
        Assert.assertTrue(!responseObject.get("create_time").toString().isEmpty());

    }

    /**
     * constructor.接收币申请
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "接收币申请")
    public void getRecent() {
        String address = "TSmZ71H9S6BQLdyGcr8QfG9qr92N6WUXKS";
        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        response = TronscanApiList.getTrc_recent(tronScanNode,params);
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
//        JSONObject responseObject = targetContent.getJSONObject("appeal");
        Assert.assertTrue(targetContent.containsKey("appeal"));
//        Assert.assertTrue(responseObject.containsKey("id"));
//        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
//        Assert.assertTrue(patternAddress.matcher(responseObject.getString("address")).matches());
//        Assert.assertEquals(responseObject.getString("address"),address);
//        Assert.assertTrue(!responseObject.get("reasons").toString().isEmpty());
//        Assert.assertTrue(responseObject.containsKey("content"));
//        Assert.assertTrue(responseObject.containsKey("status"));
//        Assert.assertTrue(!responseObject.get("update_time").toString().isEmpty());
//        Assert.assertTrue(!responseObject.get("create_time").toString().isEmpty());

    }
    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
