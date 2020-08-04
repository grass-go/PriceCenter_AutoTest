package tron.tronlink.dapp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        Assert.assertTrue(targetContent.containsKey("hot_recommend"));
        Assert.assertTrue(targetContent.containsKey("small_banner"));

        }
    }

