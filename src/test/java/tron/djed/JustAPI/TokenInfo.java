package tron.djed.JustAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.DjedApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

import java.util.regex.Pattern;

public class TokenInfo {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("djed.ip.list")
            .get(0);

    @Test(enabled = true)
    public void tokenInfoTrx(){

        response = DjedApiList.tokeninfo(node,"TRX");
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        Assert.assertTrue(responseObject.containsKey("t"));
        Assert.assertTrue(responseObject.containsKey("supply"));
        Assert.assertTrue(responseObject.containsKey("price"));


    }
    @Test(enabled = true)
    public void tokenInfoDai(){
        response = DjedApiList.tokeninfo(node,"DAI");
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        Assert.assertTrue(responseObject.containsKey("t"));
        Assert.assertTrue(responseObject.containsKey("supply"));
        Assert.assertTrue(responseObject.containsKey("price"));

    }
    @Test(enabled = true)
    public void tokenInfoMkr(){
        response = DjedApiList.tokeninfo(node,"MKR");
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(responseArrayContent.size()-1);
        Assert.assertTrue(responseObject.containsKey("t"));
        Assert.assertTrue(responseObject.containsKey("supply"));
        Assert.assertTrue(responseObject.containsKey("price"));

    }
}
