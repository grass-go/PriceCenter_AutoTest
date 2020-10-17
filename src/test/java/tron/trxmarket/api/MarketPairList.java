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
import tron.common.utils.MyIRetryAnalyzer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MarketPairList {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private JSONObject subtargetContent;
    private HttpResponse response;
    private String trxmarketNode = Configuration.getByPath("testng.conf").getStringList("trxmarket.ip.list").get(0);

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "Get MarketPair List")
    public void test01getPairList(){
        System.out.println();
        response = TrxMarketApiList.getList(trxmarketNode);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 3);
        targetContent = responseContent.getJSONObject("data");
        responseArrayContent = targetContent.getJSONArray("rows");
        for (int i = 0;i < responseArrayContent.size() ; i++) {
            subtargetContent = responseArrayContent.getJSONObject(i);
            float target = subtargetContent.getFloat("gain");
            Assert.assertTrue(target > -10 && target < 10);
        }

    }

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "Search List By Ids For BTT")
    public void test02searchListByIds(){
        System.out.println();
        int ids = 39;
        Map<String, String> params = new HashMap<>();
        params.put("ids", String.valueOf(ids));
        response = TrxMarketApiList.searchListByIds(trxmarketNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 3);

    }

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "Search List By key For BTT")
    public void test03searchListByKey(){
        System.out.println();
        int sortType = 1;
        Map<String, String> params = new HashMap<>();
        params.put("key", "btt");
        params.put("sortType", String.valueOf(sortType));
        response = TrxMarketApiList.searchListByKey(trxmarketNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 3);

    }

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "Search HistoricalTrades")
    public void test04getHistoricalTrades(){
        System.out.println();
        Map<String, String> params = new HashMap<>();
        params.put("pair", "BTT-TRX");
        params.put("limit", "20");
        params.put("fromId", "101338");
        response = TrxMarketApiList.getHistoricalTrades(trxmarketNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 3);

    }


    @AfterClass
    public void shutdown() throws InterruptedException{
        TrxMarketApiList.disGetConnect();
    }
}
