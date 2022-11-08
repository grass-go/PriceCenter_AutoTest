package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class accountList extends TronlinkBase {
    private JSONObject responseContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();
    Map<String, String> headers = new HashMap<>();

    @Test(enabled = true, description = "")
    public void testaccountListV2() throws Exception {

        headers.put("System","Android");
        headers.put("Version","4.10.0");

        params.put("address",quince_B58);

        array.clear();
        String postStr = "{\"TMSsn5sSP9u66dgzf2y63KaTmaLBPgJfj4\":1}";
        String postStr2 = "{\"TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t\":1}";
        String postStr3 = "{\"TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo\":1}";
        array.add(JSONObject.parse(postStr));
        array.add(JSONObject.parse(postStr2));
        array.add(JSONObject.parse(postStr3));
        response = TronlinkApiList.v2accountList(params, array,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }
}
