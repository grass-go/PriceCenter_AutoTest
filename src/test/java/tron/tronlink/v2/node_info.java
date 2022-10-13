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

import java.io.*;
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
        jsonObject1.put("TU5AuFTkKaAypdz5SdwyGhKDf97ZfoPWn2", 1);
        array.add(jsonObject1);
        //大trx小usd
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("TWjvFoH2HgkNCsf897tG5BSzx7ZpfkqHPs", 10);
        array.add(jsonObject2);
        //大usd，小trx
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("TUTxy4PRoVoFMeCgq2sGfiLYZhLTSG6tM1", 2);
        array.add(jsonObject3);
        //小trx小usd
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("TCacbFbmK9WjRzeEpqkGRoPRewagQM7MG8", 11);
        array.add(jsonObject4);
        //大 trx
        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("TFDP1vFeSYPT6FUznL7zUjhg5X7p2AA8vw", 3);
        array.add(jsonObject5);
        //大trx 大 usd
        JSONObject jsonObject6 = new JSONObject();
        jsonObject6.put("TDchKqQ8T2BhGfL7m2DfWfxp5eqa1we5hu", 8);
        array.add(jsonObject6);
        //小 trx，大usd
        JSONObject jsonObject7 = new JSONObject();
        jsonObject7.put("TPYFECURCk4jvD41KfDpFNx5sNiCYtEnRZ", 9);
        array.add(jsonObject7);*/

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("TZJXkxuvdEFLMpZin8GJDab8Lb2WR77dmX", 9);
        array.add(jsonObject1);

        log.info(array.toString());

        List<String> testSystems = new ArrayList<>();
        /*testSystems.add("chrome-extension-test");
        testSystems.add("AndroidTest");
        testSystems.add("firefox-test");
        testSystems.add("Android");
        testSystems.add("chrome-extension");
        testSystems.add("iOS");
        testSystems.add("Chrome");

        testSystems.add("Chrome");

        testSystems.add("Chrome");
        testSystems.add("Firefox");
        */

        testSystems.add("Android");



        for (String system : testSystems) {
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

    public static List<String> ReadFile(String fileNamePath) throws IOException {
        File tokenFile = new File(fileNamePath);

        List<String> lines = new ArrayList<>();
        if (tokenFile.isFile() && tokenFile.exists()) {
            InputStreamReader Reader = new InputStreamReader(new FileInputStream(tokenFile), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(Reader);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                //log.info("data:"+lineTxt);
                lines.add(lineTxt);
            }
            Reader.close();
        } else {
            log.info("prepare0xTokensAndPrice: Can't find 0xToken file: src/test/resources/TestData/Price/tokens0x.txt");
        }
        return lines;
    }


    @Test(enabled = true)
    public void write_wallet_address_for_perf() throws Exception {
        List<String> testSystems = new ArrayList<>();
        /*testSystems.add("chrome-extension-test");
        testSystems.add("chrome-extension");
        testSystems.add("Chrome");
        testSystems.add("AndroidTest");*/
        testSystems.add("Android");
        /*testSystems.add("iOSTest");
        testSystems.add("iOS");
        testSystems.add("Firefox");
        testSystems.add("firefox-test");*/

        String accountFile = "/Users/wqq/Text/test_bridge/tronOPT/preparePerf/accounts.txt.9000";
        List<String> contentLines = ReadFile(accountFile);
        int totalLine = contentLines.size();
        String oncePostBody = "[";
        int i = 300;
        while (i < totalLine) {

            oncePostBody += "{\"" + contentLines.get(i) + "\": 1}, ";
            i++;
            if (i % 100 == 0) {
                log.info("i:" + i);
                oncePostBody = oncePostBody.substring(0, oncePostBody.length() - 2) + "]";
                log.info("oncePostBody:" + oncePostBody);
                JSONArray array = JSONArray.parseArray(oncePostBody);
                for (String system : testSystems) {
                    response = TronlinkApiList.node_info("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", system, array);
                    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
                    Assert.assertTrue(responseContent.containsKey("code"));
                    oncePostBody = "[";
                }
            }
        }
    }
}
