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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        /*//大trx大usd
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("TScVwVTjqoqPEJ6atnvGCtErWnCyNbzmUL", 1);
        array.add(jsonObject1);*/
        /*//大trx小usd
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("TA7eKuZ8iV6knLU9dCnNdaY1GehbN6QPeR", 2);
        array.add(jsonObject2);*/
        /*//大usd，小trx
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("TJabPGUKsbtn34GiGDMps9gH414A6rvLTK", 8);
        array.add(jsonObject3);*/
        //小trx小usd
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("TX72M2ZbFrumbg9J6E2JwecF2nDN76Y4ZC", 9);
        array.add(jsonObject4);
        //大 trx
        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("TYL7z7VSVRShLoJ6YRQMA4t9pSECt9ZLmz", 10);
        array.add(jsonObject5);
        /*//大trx 大 usd
        JSONObject jsonObject6 = new JSONObject();
        jsonObject6.put("TCQQjfccKdMi4CnPAzmZW5TALH4HbwceVb", 11);
        array.add(jsonObject6);*/
        /*//小 trx，大usd
        JSONObject jsonObject7 = new JSONObject();
        jsonObject7.put("TAKBTyyqMxG96AiSyTWtz1pWBbr1DzZSHj", 9);
        array.add(jsonObject7);*/

        log.info(array.toString());

        List<String> testSystems = new ArrayList<>();
        testSystems.add("chrome-extension-test");
        testSystems.add("chrome-extension");
        testSystems.add("Chrome");
        testSystems.add("AndroidTest");
        testSystems.add("Android");
        testSystems.add("iOSTest");
        testSystems.add("iOS");

        for(String system:testSystems) {

            response = TronlinkApiList.node_info("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", system, array);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseJsonObResponseContent(response);
            Assert.assertTrue(responseContent.containsKey("code"));
            Assert.assertTrue(responseContent.containsKey("message"));
            Assert.assertTrue(responseContent.containsKey("data"));
            dataContent = responseContent.getJSONArray("data");
            Assert.assertEquals(2, dataContent.size());
            for (Object json : dataContent) {
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

}
