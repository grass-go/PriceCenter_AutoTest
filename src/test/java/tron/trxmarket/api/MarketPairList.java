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

@Slf4j
public class MarketPairList {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private JSONObject subtargetContent;
    private HttpResponse response;
    private String trxmarketNode = Configuration.getByPath("testng.conf").getStringList("trxmarket.ip.list").get(0);

    @Test(enabled = true, description = "Get MarketPair List")
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
            String target = (String) subtargetContent.get("gain");
            String removeStr = "-";
            String newTarget = target.replace(removeStr, "");
            Assert.assertTrue(Float.parseFloat(newTarget) < 2);
        }

    }

    @AfterClass
    public void shutdown() throws InterruptedException{
        TrxMarketApiList.disGetConnect();
    }
}
