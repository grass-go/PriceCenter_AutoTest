package tron.priceCenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;
import tron.common.TronlinkApiList;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class getprice {
    private HttpResponse response;
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    Map<String,String> params = new HashMap<>();
    private JSONObject object;
    private String trxusdPrice;
    private String symbolExchageTokens="";
    private String addressExchageTokens="";

    @BeforeClass(enabled = true,description = "getallprice")
    //@Test(enabled = true, description = "")
    public void prepareTokenExpPriceFromOtherPlat() {
        PriceCenterApiList.prepareTokensAndPrice();

        for (Map.Entry<String, String> entry : PriceCenterApiList.ExgTokenAddressMap.entrySet()) {
            symbolExchageTokens = symbolExchageTokens+entry.getKey()+",";
            addressExchageTokens = addressExchageTokens+entry.getValue()+",";
        }
        symbolExchageTokens = symbolExchageTokens.substring(0,symbolExchageTokens.length()-1);
        addressExchageTokens = addressExchageTokens.substring(0,addressExchageTokens.length()-1);
    }

    @Test(enabled = true, description = "Check price of exchange Tokens from Price-Centre by symbol is the same as tronscan")
    public void Test001CompareExgTokensWithTronscan() {
        //PriceCenterApiList.prepareTokensAndPrice();
        long curTime = System.currentTimeMillis();
        log.info("startTime:"+curTime);
        Map<String,String> priceCentreMap= new HashMap<>();
        String symbol=null;
        String symbolParamValue = "";
        for (Map.Entry<String, String> entry : PriceCenterApiList.ExgTokenAddressMap.entrySet()) {
            symbol = entry.getKey();
            symbolParamValue = symbolParamValue+symbol+",";
        }
        symbolParamValue = symbolParamValue.substring(0,symbolParamValue.length()-1);

        //get ExchangeToken Price From price-centre.
        params.clear();
        params.put("symbol", symbolParamValue);
        params.put("convert", "TRX");
        response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        for (Map.Entry<String, String> entry : PriceCenterApiList.exp_exgTokenValueMap.entrySet()) {
            symbol = entry.getKey();
            String expPrice = entry.getValue();
            Object curPrice = JSONPath.eval(responseContent, "$..data."+symbol+".quote.TRX.price[0]");
            String priceCentreValue =curPrice.toString();
            Object curPriceTime = JSONPath.eval(responseContent, "$..data."+symbol+".quote.TRX.last_updated[0]");
            log.info("symbol:"+symbol+", curPriceTime:"+ curPriceTime);
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expPrice, priceCentreValue));
        }

        Object statusCode = JSONPath.eval(responseContent, "$..status.error_code[0]");
        Assert.assertEquals(0,statusCode);
        Object error_msg = JSONPath.eval(responseContent, "$..status.error_message[0]");
        Assert.assertNull(error_msg);
    }

    //symbol=symbol&convert=TRX，USDT，USD，CNY，GBP，EUR
    //symbol=地址&convert=TRX地址，USDT地址，USD
    @Test(enabled = true, description = "Check values correct between address/address, address/usd, symbol/symbol, symbol/exchangeRate")
    public void Test002CheckSameValueGotByDiffFormat() throws URISyntaxException {
        HttpResponse symbolResp;
        JSONObject symbolRespContent;
        HttpResponse addressResp;
        JSONObject addressRespContent;
        //print prepared data.
        log.info("Test002CheckSameValueGotByDiffFormat:checked tokens symbold : "+symbolExchageTokens);
        log.info("Test002CheckSameValueGotByDiffFormat:checked tokens address: " + addressExchageTokens);
        //get USDT cny Price
        PriceCenterApiList.SetTrxPriceMap();
        for (Map.Entry<String, String> entry : PriceCenterApiList.trxPriceMap.entrySet()) {
            log.info("TRX-"+entry.getKey()+": "+entry.getValue());
        }

        params.clear();
        params.put("symbol", symbolExchageTokens);
        params.put("convert", "TRX,USDT,USD,CNY,GBP,EUR");
        symbolResp = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, symbolResp.getStatusLine().getStatusCode());
        symbolRespContent = TronlinkApiList.parseJsonObResponseContent(symbolResp);

        params.clear();
        params.put("symbol", addressExchageTokens);
        params.put("convert", "T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb,TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t,USD");
        addressResp = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, addressResp.getStatusLine().getStatusCode());
        addressRespContent = TronlinkApiList.parseJsonObResponseContent(addressResp);

        //1 地址-》地址       SwapToken-〉TRX； 交易所-》USDT。
        //2 地址-》USD：
        //3 symbol-》symbol: 交易所常用对交易所常用。
        //4 symbol-》四种汇率（USD，CNY，EUR，GBP）

        //Check: 1 equals to 3 for each Token.   trx、usdt.(同一个计价单位trx或者usdt，地址访问=symbol访问)
        //Check: 2 equals to 4 for each Token;   usd（统一计价单位USD，地址访问=symbold访问）
        //Check: 不通计价单位的值都换算成trx价格，应该与trx价格之差在容忍度内。 例：cny价格/（cny/trx）=trx价格。
        //暂不支持地址对CNY，EUR，GBP。
        for (Map.Entry<String, String> entry : PriceCenterApiList.ExgTokenAddressMap.entrySet()) {
            String curSymbol = entry.getKey();
            String curAddress = entry.getValue();
            log.info("Test002CheckSameValueGotByDiffFormat: curSymbol:"+curSymbol+ ",curAddress: "+curAddress);
            Object trxSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.TRX.price[0]"));
            Object trxAddressPrice = JSONPath.eval(addressRespContent,String.join("","$..data.",curAddress,".quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]"));
            log.info("debug trxAddressPrice jsonPath:"+String.join("","$..data.",curAddress,".quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]"));
            Object usdtSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.USDT.price[0]"));
            Object usdtAddressPrice = JSONPath.eval(addressRespContent,String.join("","$..data.",curAddress,".quote.TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t.price[0]")); ;
            Object usdSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.USD.price[0]"));
            Object usdAddressPrice = JSONPath.eval(addressRespContent, String.join("", "$..data.",curAddress,".quote.USD.price[0]"));
            Object cnyPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.CNY.price[0]"));
            Object eurPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.EUR.price[0]"));
            Object gbpPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.GBP.price[0]"));
            log.info("Test002CheckSameValueGotByDiffFormat:trxSymbolPrice:"+trxSymbolPrice+", trxAddressPrice:"+trxAddressPrice+", usdtSymbolPrice:"+usdtSymbolPrice+", usdtAddressPrice:"+usdtAddressPrice);
            log.info("Test002CheckSameValueGotByDiffFormat:cnyPrice:"+cnyPrice+", eurPrice:"+eurPrice+", gbpPrice:"+gbpPrice+", usdSymbolPrice:"+usdSymbolPrice +", usdAddressPrice:"+usdAddressPrice);

            Assert.assertEquals(trxSymbolPrice,trxAddressPrice);
            Assert.assertEquals(usdtSymbolPrice,usdtAddressPrice);
            Assert.assertEquals(usdSymbolPrice,usdAddressPrice);

            String calcTrxPrice_usdtSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(usdtSymbolPrice.toString(),PriceCenterApiList.trxPriceMap.get("USDT"));
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxSymbolPrice.toString(), calcTrxPrice_usdtSymbol));

            String calcTrxPrice_usdSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(usdSymbolPrice.toString(),PriceCenterApiList.trxPriceMap.get("USD"));
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxSymbolPrice.toString(), calcTrxPrice_usdSymbol));

            String calcTrxPrice_cnySymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(cnyPrice.toString(),PriceCenterApiList.trxPriceMap.get("CNY"));
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxSymbolPrice.toString(), calcTrxPrice_cnySymbol));

            String calcTrxPrice_eurSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(eurPrice.toString(),PriceCenterApiList.trxPriceMap.get("EUR"));
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxSymbolPrice.toString(), calcTrxPrice_eurSymbol));

            String calcTrxPrice_gbpSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(gbpPrice.toString(),PriceCenterApiList.trxPriceMap.get("GBP"));
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxSymbolPrice.toString(), calcTrxPrice_gbpSymbol));
        }

    }
}
