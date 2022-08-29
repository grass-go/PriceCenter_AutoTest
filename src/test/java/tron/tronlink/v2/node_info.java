package tron.tronlink.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class node_info extends TronlinkBase {
    private JSONObject responseContent;
    private String responseString;
    private JSONObject object;
    private JSONArray dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();

    @Test(enabled = true)
    public void node_info01() throws Exception {
        JSONArray array = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", 1);
        array.add(jsonObject1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3", 2);
        array.add(jsonObject2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo", 11);
        array.add(jsonObject3);
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ", 3);
        array.add(jsonObject4);
        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("TPmQw8dDLdrH8bTN3u9H1A2XCanbqyE533", 8);
        array.add(jsonObject5);
        JSONObject jsonObject6 = new JSONObject();
        jsonObject6.put("TXFmkVZkpkv8ghNCKwpeVdVvRVqTedSCAK", 9);
        array.add(jsonObject6);

        log.info(array.toString());

        response = TronlinkApiList.nodeinfo("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t","AndroidTest", array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        dataContent = responseContent.getJSONArray("data");
        Assert.assertEquals(2, dataContent.size());
        for (Object json:dataContent) {
            JSONObject curObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(curObject.containsKey("chainName"));
            Assert.assertTrue(curObject.containsKey("chainId"));
            Assert.assertTrue(curObject.containsKey("sideChainContract"));
            Assert.assertTrue(curObject.containsKey("mainChainContract"));
            Assert.assertTrue(curObject.containsKey("fullNode"));
            Assert.assertTrue(curObject.containsKey("solidityNode"));
            Assert.assertTrue(curObject.containsKey("eventServer"));
            Assert.assertTrue(curObject.containsKey("isMainChain"));
            Assert.assertTrue(curObject.containsKey("serviceTime"));
        }

    }

}
