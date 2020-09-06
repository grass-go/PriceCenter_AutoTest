package tron.trxmarket.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TrxMarketApiList;
import tron.common.utils.Configuration;

public class MarketPairList {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String trxmarketNode = Configuration.getByPath("testng.conf").getStringList("trxmarket.ip.list").get(0);

    @Test(enabled = true, description = "Get MarketPair List")
    public void test01getPairList(){
        System.out.println();
        response = TrxMarketApiList.getList(trxmarketNode);
        //log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    }

    @AfterClass
    public void shutdown() throws InterruptedException{
        TrxMarketApiList.disGetConnect();
    }
}
