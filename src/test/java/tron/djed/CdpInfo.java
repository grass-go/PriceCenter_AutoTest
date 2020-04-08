package tron.djed;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.DjedApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

public class CdpInfo {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("djed.ip.list")
            .get(0);

    @Test(enabled = true)
    public void cdpInfoAll(){

        response = DjedApiList.cdpInfoAll(node);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        Assert.assertTrue(responseObject.containsKey("ptrxHold"));
        Assert.assertTrue(responseObject.containsKey("cdpId"));
        Assert.assertTrue(responseObject.containsKey("lasteventTime"));
        Assert.assertTrue(responseObject.containsKey("liqPrice"));
        Assert.assertTrue(responseObject.containsKey("cRatio"));
        Assert.assertTrue(responseObject.containsKey("id"));
        Assert.assertTrue(responseObject.containsKey("debt"));
        Assert.assertTrue(responseObject.containsKey("address"));


    }

    @Test(enabled = true)
    public void cdpInfoLatest(){

        response = DjedApiList.cdpHistoryLatest(node);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        Assert.assertTrue(responseObject.containsKey("curPip"));
        Assert.assertTrue(responseObject.containsKey("afterPtrxhold"));
        Assert.assertTrue(responseObject.containsKey("opParameter"));
        Assert.assertTrue(responseObject.containsKey("afterDebt"));
        Assert.assertTrue(responseObject.containsKey("opType"));
        Assert.assertTrue(responseObject.containsKey("cdpId"));
        Assert.assertTrue(responseObject.containsKey("curPer"));
        Assert.assertTrue(responseObject.containsKey("eventTime"));
        Assert.assertTrue(responseObject.containsKey("liqPrice"));
        //todo 添加链上数据对照
        Assert.assertTrue(responseObject.containsKey("txId"));
        Assert.assertTrue(responseObject.containsKey("cRatio"));
        Assert.assertTrue(responseObject.containsKey("id"));
    }
}
