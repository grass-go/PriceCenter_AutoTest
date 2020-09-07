package tron.djed.JustAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.DjedApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

public class FeedInfo {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("djed.ip.list")
            .get(0);

    @Test(enabled = true)
    public void feedInfoTimeLine(){

        response = DjedApiList.feedInfoTimeLine(node);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        JSONArray jsonArray = responseObject.getJSONArray("feedEventDTOList");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        Assert.assertTrue(jsonObject.containsKey("address"));
        Assert.assertTrue(jsonObject.containsKey("price"));
        Assert.assertTrue(jsonObject.containsKey("lu"));
        Assert.assertTrue(jsonObject.containsKey("exp"));


    }

}
