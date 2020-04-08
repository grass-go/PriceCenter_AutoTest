package tron.djed;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.DjedApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

public class CdpHistory {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("djed.ip.list")
            .get(0);

    @Test(enabled = true)
    public void cdpHistoryDay(){

        response = DjedApiList.cdpHistoryDay(node);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        Assert.assertTrue(responseObject.containsKey("bite"));
        Assert.assertTrue(responseObject.containsKey("close"));
        Assert.assertTrue(responseObject.containsKey("draw"));
        Assert.assertTrue(responseObject.containsKey("free"));
        Assert.assertTrue(responseObject.containsKey("give"));
        Assert.assertTrue(responseObject.containsKey("lock"));
        Assert.assertTrue(responseObject.containsKey("open"));
        Assert.assertTrue(responseObject.containsKey("wipe"));
        Assert.assertTrue(responseObject.containsKey("t"));
    }


}
