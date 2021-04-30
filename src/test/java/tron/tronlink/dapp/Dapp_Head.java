package tron.tronlink.dapp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

public class Dapp_Head extends TronlinkBase {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("tronlink.ip.list")
            .get(0);
//todo 待补充
    @Test(enabled = true)
    public void head() {

        response = TronlinkApiList.head();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        //data object
        targetContent = responseContent.getJSONObject("data");

        Assert.assertTrue(targetContent.containsKey("roll_data"));
        Assert.assertTrue(targetContent.containsKey("roll_dapp"));
        Assert.assertTrue(targetContent.containsKey("hot_recommend"));
        Assert.assertTrue(targetContent.containsKey("small_banner"));

        JSONArray roll_data = targetContent.getJSONArray("roll_data");
        JSONArray roll_dapp = targetContent.getJSONArray("roll_dapp");
        JSONArray hot_recommend = targetContent.getJSONArray("hot_recommend");
        JSONObject big_banner = targetContent.getJSONObject("big_banner");
        JSONObject small_banner = targetContent.getJSONObject("small_banner");
        JSONArray dapp = targetContent.getJSONArray("dapp");

//        Assert.assertEquals(roll_data.size(), roll_dapp.size());
        for (int n = 0; n < roll_data.size(); n++){
//            Assert.assertEquals(roll_data.getJSONObject(n).getString("name"), roll_dapp.getJSONObject(n).getString("name"));
            Assert.assertEquals(200, TronlinkApiList.createGetConnect(roll_data.getJSONObject(n).getString("image_url")).getStatusLine().getStatusCode());
            Assert.assertEquals(200, TronlinkApiList.createGetConnect(roll_data.getJSONObject(n).getString("home_url")).getStatusLine().getStatusCode());
        }

        for (int n = 0; n < hot_recommend.size(); n++){
            JSONObject jo = hot_recommend.getJSONObject(n);
            Assert.assertEquals(200, TronlinkApiList.createGetConnect(jo.getString("image_url")).getStatusLine().getStatusCode());
            Assert.assertEquals(200, TronlinkApiList.createGetConnect(jo.getString("home_url")).getStatusLine().getStatusCode());
        }



    }
}

