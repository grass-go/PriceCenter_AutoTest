package tron.tronlink.v2;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class accountList extends TronlinkBase {
    private JSONObject responseContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();
    Map<String, String> headers = new HashMap<>();

    //v4.11.0-done
    @Test(enabled = true, description = "")
    public void testaccountListV2() throws Exception {
        headers.put("System","Android");
        headers.put("Version","4.10.0");

        params.put("address",quince_B58);

        array.clear();
        String postStr = "{\"TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t\":1}";
        String postStr2 = "{\"TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo\":1}";
        array.add(JSONObject.parse(postStr));
        array.add(JSONObject.parse(postStr2));

        response = TronlinkApiList.v2accountList(params, array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object accountT_obj1 = JSONPath.eval(responseContent, String.join("","$..data.balanceList[address='TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t'].accountType[0]"));
        log.info("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t account Type is : "+ accountT_obj1.toString());
        Object accountT_obj2 = JSONPath.eval(responseContent, String.join("","$..data.balanceList[address='TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo'].accountType[0]"));
        log.info("TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo account Type is : "+ accountT_obj2.toString());
        Assert.assertEquals(1, (int)accountT_obj1);
        Assert.assertEquals(0, (int)accountT_obj2);
    }
}
