package tron.tronlink.dapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;

import java.util.HashMap;
import java.util.Map;

public class Dapp_History {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("tronlink.ip.list")
            .get(0);

    @Test(enabled = true)
    public void history() {
        Map<String, String> params = new HashMap<>();
        params.put("address","TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
        response = TronlinkApiList.history(node,params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        //data object
    }

}
