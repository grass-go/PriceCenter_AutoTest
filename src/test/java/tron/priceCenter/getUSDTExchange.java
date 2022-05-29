package tron.priceCenter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;
import tron.common.TronlinkApiList;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;

@Slf4j
public class getUSDTExchange {
    private HttpResponse response;
    private JSONObject responseContent;

    @Test(enabled = true, description = "check /v1/cryptocurrency/getUSDTExchange content is correct")
    public void Test001CompareAPIWithCoinBase() {
        long curTime = System.currentTimeMillis();
        log.info("startTime:"+curTime);
        //get USDT exchange Rate from coinbase as expected value.
        Map<String,String> expUsdtPriceMap= new HashMap<>();
        expUsdtPriceMap.put("USD", PriceCenterApiList.getUSDTPriceFromCoinbase("USD"));
        expUsdtPriceMap.put("CNY",PriceCenterApiList.getUSDTPriceFromCoinbase("CNY"));
        expUsdtPriceMap.put("EUR",PriceCenterApiList.getUSDTPriceFromCoinbase("EUR"));
        expUsdtPriceMap.put("GBP",PriceCenterApiList.getUSDTPriceFromCoinbase("GBP"));

        //get USDT exchange Rate from price-centre as actual value.
        String priceURL = PriceCenterApiList.HttpNode + "/v1/cryptocurrency/getUSDTExchange";
        response = PriceCenterApiList.createGetConnectWithHeader(priceURL,null,null,null);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object usdPrice = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.USD.price[0]"));
        Object  usdPrice_ts = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.USD.last_updated[0]"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expUsdtPriceMap.get("USD"),usdPrice.toString()));
        Assert.assertTrue(PriceCenterApiList.CompareLastUpdateInTolerance(curTime, Long.parseLong(usdPrice_ts.toString())));

        Object cnyPrice = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.CNY.price[0]"));
        Object  cnyPrice_ts = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.CNY.last_updated[0]"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expUsdtPriceMap.get("CNY"),cnyPrice.toString()));
        Assert.assertTrue(PriceCenterApiList.CompareLastUpdateInTolerance(curTime, Long.parseLong(cnyPrice_ts.toString())));


        Object eurPrice = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.EUR.price[0]"));
        Object  eurPrice_ts = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.EUR.last_updated[0]"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expUsdtPriceMap.get("EUR"),eurPrice.toString()));
        Assert.assertTrue(PriceCenterApiList.CompareLastUpdateInTolerance(curTime, Long.parseLong(eurPrice_ts.toString())));

        Object gbpPrice = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.GBP.price[0]"));
        Object  gbpPrice_ts = JSONPath.eval(responseContent, String.join("", "$..data.USDT.quote.GBP.last_updated[0]"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expUsdtPriceMap.get("GBP"),gbpPrice.toString()));
        Assert.assertTrue(PriceCenterApiList.CompareLastUpdateInTolerance(curTime, Long.parseLong(gbpPrice_ts.toString())));

        Object statusCode = JSONPath.eval(responseContent, "$..status.error_code[0]");
        Assert.assertEquals(0,statusCode);
        Object error_msg = JSONPath.eval(responseContent, "$..status.error_message[0]");
        Assert.assertNull(error_msg);

    }

}
