package tron.djed;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.DjedApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

public class CollInfo {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("djed.ip.list")
            .get(0);

    @Test(enabled = true)
    public void tokenInfoTrx(){

        response = DjedApiList.collInfo(node);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        Assert.assertTrue(responseObject.containsKey("t"));
        //全局质押率
        Assert.assertTrue(responseObject.containsKey("cRatio"));
        //锁定的PTRX
        Assert.assertTrue(responseObject.containsKey("ptrxLocked"));
        //WTRX/PTRX
        Assert.assertTrue(responseObject.containsKey("per"));
        //锁定的WTRX
        Assert.assertTrue(responseObject.containsKey("wtrxLocked"));


    }
}
