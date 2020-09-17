package tron.trxmarket.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TrxMarketApiList;
import tron.common.utils.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KChart {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private JSONObject subtargetContent;
    private HttpResponse response;
    private String trxmarketNode = Configuration.getByPath("testng.conf").getStringList("trxmarket.ip.list").get(0);

    @Test(enabled = true, description = "Get BTT K Chart Message")
    public void test01getKChart(){
        System.out.println();
        int pairId = 39;
        Map<String, String> params = new HashMap<>();
        params.put("exchange_id", String.valueOf(pairId));
        params.put("granularity", "5min");
        params.put("time_start", "1600041600");
        params.put("time_end", "1600146054");

        response = TrxMarketApiList.getKChart(trxmarketNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 5);

    }
    @AfterClass
    public void shutdown() throws InterruptedException{
        TrxMarketApiList.disGetConnect();
    }
}
