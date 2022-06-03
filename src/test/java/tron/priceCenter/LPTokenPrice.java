package tron.priceCenter;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.collections.functors.ExceptionPredicate;
import tron.common.TronlinkApiList;
import tron.priceCenter.base.priceBase;
import tron.common.PriceCenterApiList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LPTokenPrice extends priceBase{
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private JSONObject object;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();

    @Test(enabled = true, description = "Check JUSTSwap Token's price，get LP Token from tronscan，then compare price.")
    public void Test001getprice() {
        //request transcan search API to gain LPTokens and price.
        params.put("term","JUSTSWAP");
        params.put("type","token");
        params.put("start","0");
        params.put("limit","20");
        response = PriceCenterApiList.searchtypeFromTranScan(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        JSONArray tokensArray = responseContent.getJSONArray("search_result");
        int searchCount = tokensArray.size();
        Map<String, BigDecimal> LPTokenMap = new HashMap<>();
        log.info("LPTokens from Transcan:");
        for (int i=0;i<searchCount;i++){
            object = tokensArray.getJSONObject(i);
            if (object.getString("name").startsWith("JUSTSWAP-") && object.getString("abbr").startsWith("S-") && object.containsKey("market_info")) {
                JSONObject market_info = object.getJSONObject("market_info");

                LPTokenMap.put(object.getString("contract_address"), market_info.getBigDecimal("priceInTrx"));
                log.info("contract_address:" + object.getString("contract_address") + " ,priceInTrx:" + market_info.getBigDecimal("priceInTrx").toString());
            }
            else{
                continue;
            }
        }
        //check each LPToken price got from price-Center in LPTokenMap
        for(Map.Entry<String, BigDecimal> entry : LPTokenMap.entrySet()){
            String address = entry.getKey();
            BigDecimal expPrice = entry.getValue();
            params.clear();
            params.put("symbol",address);
            params.put("convert","T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb");
            response = PriceCenterApiList.getprice(params);
            Assert.assertEquals(200, response.getStatusLine().getStatusCode());
            responseContent = TronlinkApiList.parseJsonObResponseContent(response);
            Object price = JSONPath.eval(responseContent, "$..T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price");
            try {
                JSONArray priceArray = (JSONArray) price;
                String lpPrice = priceArray.get(0).toString();
                log.info("TranscanPrice:"+expPrice.toString()+", PriceCenter Price:"+lpPrice.toString());
                Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expPrice.toString(),lpPrice,"0.2"));
            } catch (Exception e){
                log.info(e.toString());
                Assert.fail("address:"+address+"has no price in price center!!");
            }

        }
    }

}
