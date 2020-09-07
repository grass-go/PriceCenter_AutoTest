package tron.djed.JustAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.DjedApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

public class PollList {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("djed.ip.list")
            .get(0);

    @Test(enabled = true)
    public void pollList () {

        response = DjedApiList.pollList(node);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size() - 1);
        Assert.assertTrue(responseObject.containsKey("content"));
        Assert.assertTrue(responseObject.containsKey("endTime"));
        Assert.assertTrue(responseObject.containsKey("id"));
        Assert.assertTrue(responseObject.containsKey("pollKey"));
        Assert.assertTrue(responseObject.containsKey("startTime"));
        Assert.assertTrue(responseObject.containsKey("title"));
        Assert.assertTrue(responseObject.containsKey("summary"));
        Assert.assertTrue(responseObject.containsKey("votersCount"));
        Assert.assertTrue(responseObject.containsKey("votesSum"));
        Assert.assertTrue(responseObject.containsKey("winningOpt"));

    }
}
