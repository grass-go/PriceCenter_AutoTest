package tron.priceCenter;

import com.alibaba.fastjson.JSON;
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
    private static String symbolExchageTokens="";
    private static String addressExchageTokens="";

    private static Map<String,String> getallprice_Map = new HashMap<>();
    private static Map<String,String> getprice_Map = new HashMap<>();

    @BeforeClass(enabled = true,description = "getallprice")
    //@Test(enabled = true, description = "")
    public void prepareTokenExpPriceFromOtherPlat() {
        PriceCenterApiList.prepareExchangeTokensAndPrice();
        PriceCenterApiList.preparejTokensAndPrice();
        PriceCenterApiList.SetTrxPriceMap();

    }

    public void compareTokensPriceWithTronscan(Map<String,String> tokenAddressMap, Map<String,String> tokenExpPrice) {
        long curTime = System.currentTimeMillis();
        log.info("startTime:"+curTime);
        Map<String,String> priceCentreMap= new HashMap<>();
        String symbol=null;
        String symbolParamValue = "";
        for (Map.Entry<String, String> entry : tokenAddressMap.entrySet()) {
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
        for (Map.Entry<String, String> entry : tokenExpPrice.entrySet()) {
            symbol = entry.getKey();
            String expPrice = entry.getValue();
            log.info("compareTokensPriceWithTronscan using TRX price unit: cur symbol:"+symbol);
            Object curPrice = JSONPath.eval(responseContent, "$..data."+symbol+".quote.TRX.price[0]");
            String priceCentreValue =curPrice.toString();
            log.info("compareTokensPriceWithTronscan using TRX price unit: expPrice:"+expPrice+",priceCentreValue:"+priceCentreValue);

            Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expPrice, priceCentreValue,"0.2"));

            Object curPriceTime = JSONPath.eval(responseContent, "$..data."+symbol+".quote.TRX.last_updated[0]");
            log.info("symbol:"+symbol+", curPriceTime:"+ curPriceTime);
        }

        Object statusCode = JSONPath.eval(responseContent, "$..status.error_code[0]");
        Assert.assertEquals(0,statusCode);
        Object error_msg = JSONPath.eval(responseContent, "$..status.error_message[0]");
        Assert.assertNull(error_msg);
    }

    @Test(enabled = true, description = "Check price of exchange Tokens from Price-Centre by symbol is the same as tronscan")
    public void Test001CheckCentrePrice_ExgTokens_Tronscan() {
        compareTokensPriceWithTronscan(PriceCenterApiList.ExgTokenAddressMap, PriceCenterApiList.exp_exgTokenValueMap);
    }

    @Test(enabled = true, description = "Check price of jToken from Price-Centre by symbol is the same as tronscan")
    public void Test002CheckCentrePrice_jTokens_Tronscan() {
        compareTokensPriceWithTronscan(PriceCenterApiList.JTokenAddressMap,PriceCenterApiList.exp_JTokenValueMap);
    }

    @Test(enabled = true, description = "check TRX/usd Price with price got from CMC")
    public void Test005CheckCentrePrice_TrxRelated_CMC() throws URISyntaxException {
        String expTrxPrice = PriceCenterApiList.trxPriceMap.get("USD");
        params.clear();
        params.put("symbol", "TRX,WTRX,jTRX");
        params.put("convert", "USD");
        response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object statusCode = JSONPath.eval(responseContent, "$..status.error_code[0]");
        Assert.assertEquals(0,statusCode);
        Object error_msg = JSONPath.eval(responseContent, "$..status.error_message[0]");
        Assert.assertNull(error_msg);

        Object centrePrice = JSONPath.eval(responseContent, "$..data.TRX.quote.USD.price[0]");
        String trxPrice = centrePrice.toString();
        log.info("Test005CompareTrxPriceWithCMC: expTrxPrice: "+expTrxPrice+", trxPrice:"+trxPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expTrxPrice, trxPrice,"0.1"));

        Object wtrxPrice = JSONPath.eval(responseContent, "$..data.WTRX.quote.USD.price[0]");
        log.info("Test005CompareTrxPriceWithCMC: expWTRXPrice: "+wtrxPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(trxPrice, wtrxPrice.toString(),"0.01"));

        Object jtrxPrice = JSONPath.eval(responseContent, "$..data.JTRX.quote.USD.price[0]");
        log.info("Test005CompareTrxPriceWithCMC: expWTRXPrice: "+jtrxPrice.toString());
        String expJTRXPrice = PriceCenterApiList.getZoomInValue(trxPrice,"100");
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expJTRXPrice, jtrxPrice.toString(),"0.01"));
    }


    @Test(enabled = true, description = "check BTT price with swap api")
    public void Test007CheckCentrePrice_BTT_SunSwap(){
        String swapAPI = "https://pabc.ablesdxd.link/swapv2/scan/getTokenInfo?tokenAddress=TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4";
        response = PriceCenterApiList.createGetConnectWithHeader(swapAPI,null,null,null);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object swapPrice = JSONPath.eval(responseContent, "$..data.derivedTrx[0]");
        params.clear();
        params.put("symbol", "BTT");
        params.put("convert", "TRX");
        response = PriceCenterApiList.getprice(params);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object centrePrice = JSONPath.eval(responseContent, "$..data.BTT.quote.TRX.price[0]");
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(swapPrice.toString(),centrePrice.toString(),"0.1"));
    }

    //WBTT, jWBTT, BTT, BTTOLD
    //真实价格的比较已经在getallprice有，预期是自己设定的固定值。此处仅对getprice内各币关系做比较。
    @Test(enabled = true, description = "check BTT related price")
    public void Test008CheckRelationship_BttRelated() throws URISyntaxException {
        //check WBTT = BTTOLD , BTTOLD=BTT*1000, WBTT=jWBTT*100
        params.clear();
        params.put("symbol", "WBTT,BTTOLD,jWBTT,BTT");
        params.put("convert", "TRX");
        response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object wbttPrice = JSONPath.eval(responseContent, "$..data.WBTT.quote.TRX.price[0]");
        Object bttoldPrice = JSONPath.eval(responseContent, "$..data.BTTOLD.quote.TRX.price[0]");
        Object jwbttPrice = JSONPath.eval(responseContent, "$..data.JWBTT.quote.TRX.price[0]");
        Object bttPrice = JSONPath.eval(responseContent, "$..data.BTT.quote.TRX.price[0]");
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(wbttPrice.toString(), bttoldPrice.toString(),"0.1"));
        String thousandthBttoldPrice = PriceCenterApiList.getZoomInValue(bttoldPrice.toString(),"1000");
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(thousandthBttoldPrice, bttPrice.toString(),"0.1"));
        String percentWbttPrice = PriceCenterApiList.getZoomInValue(wbttPrice.toString(),"100");
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(percentWbttPrice, jwbttPrice.toString(),"0.1"));
    }

    //WTokens 仅收录了四个： WTRX，WBTT，WBTC，WETH，WBTC
    //wTRX已经在trx相关case测到。
    //WBTC没有价格，是因为流动性不足24小时后，改token的价格信息就被清理掉。
    //WBTT价格上面已单独计算，目前仅剩WETH价格。
    //目前价格中心WETH的价格也没有。所以case暂时false。
    @Test(enabled = false, description = "check wToken's price with related token by usd price")
    public void Test011CheckDiffFormat_wTokens() throws URISyntaxException {
        Map<String,String> wTokenMap = new HashMap<>();
        wTokenMap.put("WETH","ETH");
        java.util.List joinedStr = PriceCenterApiList.getJoinedSymbolAndAddress(wTokenMap);
        params.clear();
        params.put("symbol", (String) joinedStr.get(0));
        params.put("convert", "TRX");
        response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        for (Map.Entry<String, String> entry : wTokenMap.entrySet()) {
            String curWtoken = entry.getKey();
            String relatedToken = entry.getValue();
            String relatedTokenPrice = PriceCenterApiList.exp_exgTokenValueMap.get(relatedToken);
            Object wTokenPrice = JSONPath.eval(responseContent, "$..data." + curWtoken + ".quote.TRX.price[0]");
            log.info("Test007CheckWTokenPriceWithToken: curWtoken:" + curWtoken + ", price:" + wTokenPrice + ", relatedToken:" + relatedToken + ", relatedTokenPrice:" + relatedTokenPrice);
            Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(relatedTokenPrice, wTokenPrice.toString(),"0.01"));
        }
    }

}



