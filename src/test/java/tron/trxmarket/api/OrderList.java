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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class OrderList {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private JSONObject subtargetContent;
    private HttpResponse response;
    private String trxmarketNode = Configuration.getByPath("testng.conf").getStringList("trxmarket.ip.list").get(0);

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "Get BTT Order List")
    public void test01orderList(){
        int pairId = 39;
        System.out.println();
        response = TrxMarketApiList.getOrderList(trxmarketNode, String.valueOf(pairId));
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 3);

    }

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "Get BTT Latest Order")
    public void test02latestOrders(){
        System.out.println();
        int limit = 20;
        int pairId = 39;
        Map<String, String> params = new HashMap<>();
        params.put("pairID", String.valueOf(pairId));
        params.put("limit", String.valueOf(limit));
        params.put("start", "0");
        response = TrxMarketApiList.getLatestOrder(trxmarketNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 3);


    }

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "GET User Order")
    public void test03userOrders(){
        System.out.println();
        int limit = 7;
        Map<String, String> params = new HashMap<>();
        params.put("start", "0");
        params.put("limit", String.valueOf(limit));
        params.put("uAddr", "THEBmNZKc9RUmU7KGQGP6vW2uxjMPBiw6X");
        params.put("status", "-1,0,1");
        params.put("channelId", "0");
        response = TrxMarketApiList.getUserOrder(trxmarketNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TrxMarketApiList.parseResponseContent(response);
        TrxMarketApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.size() == 3);

    }

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "GET Top Price")
    public void test04topPrice(){
        List list = new ArrayList();
        list.add(39);     // BTT/TRX pairId
        list.add(419);    // JST/TRX pairId
        list.add(514);    // SUN/TRX pairId
        list.add(69);     // WIN/TRX pairId
        for(Object obj : list){
            response = TrxMarketApiList.getTopPrice(trxmarketNode, String.valueOf(obj));
            log.info("code is " + response.getStatusLine().getStatusCode());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TrxMarketApiList.parseResponseContent(response);
            TrxMarketApiList.printJsonContent(responseContent);
            Assert.assertTrue(responseContent.size() == 3);
        }

    }
    @AfterClass
    public void shutdown() throws InterruptedException{
        TrxMarketApiList.disGetConnect();
    }
}
