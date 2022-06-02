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

            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expPrice, priceCentreValue));

            Object curPriceTime = JSONPath.eval(responseContent, "$..data."+symbol+".quote.TRX.last_updated[0]");
            log.info("symbol:"+symbol+", curPriceTime:"+ curPriceTime);
        }

        Object statusCode = JSONPath.eval(responseContent, "$..status.error_code[0]");
        Assert.assertEquals(0,statusCode);
        Object error_msg = JSONPath.eval(responseContent, "$..status.error_message[0]");
        Assert.assertNull(error_msg);
    }

    //symbol=symbol&convert=TRX，USDT，USD，CNY，GBP，EUR
    //symbol=地址&convert=TRX地址，USDT地址，USD
    public void CheckSameValueGotByDiffFormat(Map<String,String> tokenAddressMap) throws URISyntaxException {
        HttpResponse symbolResp;
        JSONObject symbolRespContent;
        HttpResponse addressResp;
        JSONObject addressRespContent;
        //print prepared data.
        java.util.List<String> joinedStr = PriceCenterApiList.getJoinedSymbolAndAddress(tokenAddressMap);
        String symbolExchageTokens=joinedStr.get(0);
        String addressExchageTokens=joinedStr.get(1);
        log.info("CheckSameValueGotByDiffFormat:checked tokens symbol : "+symbolExchageTokens);
        log.info("CheckSameValueGotByDiffFormat:checked tokens address: " + addressExchageTokens);
        //get TRX cny/usd/eur/gbp Price

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
        for (Map.Entry<String, String> entry : tokenAddressMap.entrySet()) {
            String curSymbol = entry.getKey();
            if (curSymbol.equals("usdc3SUN")){
                curSymbol = curSymbol.toUpperCase();
            }
            String curAddress = entry.getValue();
            log.info("CheckSameValueGotByDiffFormat: curSymbol:"+curSymbol+ ",curAddress: "+curAddress);
            Object trxSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.TRX.price[0]"));
            Object trxAddressPrice = JSONPath.eval(addressRespContent,String.join("","$..data.",curAddress,".quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]"));
            //log.info("debug trxAddressPrice jsonPath:"+String.join("","$..data.",curAddress,".quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]"));
            //log.info("debug trxAddressPrice jsonPath:" + trxAddressPrice.toString());
            Object usdtSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.USDT.price[0]"));
            Object usdtAddressPrice = JSONPath.eval(addressRespContent,String.join("","$..data.",curAddress,".quote.TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t.price[0]")); ;
            Object usdSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.USD.price[0]"));
            Object usdAddressPrice = JSONPath.eval(addressRespContent, String.join("", "$..data.",curAddress,".quote.USD.price[0]"));
            Object cnyPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.CNY.price[0]"));
            Object eurPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.EUR.price[0]"));
            Object gbpPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.",curSymbol,".quote.GBP.price[0]"));
            log.info("CheckSameValueGotByDiffFormat:trxSymbolPrice:"+trxSymbolPrice+", trxAddressPrice:"+trxAddressPrice+", usdtSymbolPrice:"+usdtSymbolPrice+", usdtAddressPrice:"+usdtAddressPrice);
            log.info("CheckSameValueGotByDiffFormat:cnyPrice:"+cnyPrice+", eurPrice:"+eurPrice+", gbpPrice:"+gbpPrice+", usdSymbolPrice:"+usdSymbolPrice +", usdAddressPrice:"+usdAddressPrice);

            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxSymbolPrice.toString(), trxAddressPrice.toString()));
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(usdtSymbolPrice.toString(), usdtAddressPrice.toString()));
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(usdSymbolPrice.toString(), usdSymbolPrice.toString()));


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

    @Test(enabled = true, description = "Check price of exchange Tokens from Price-Centre by symbol is the same as tronscan")
    public void Test001CheckCentrePrice_ExgTokens_Tronscan() {
        compareTokensPriceWithTronscan(PriceCenterApiList.ExgTokenAddressMap, PriceCenterApiList.exp_exgTokenValueMap);
    }

    @Test(enabled = true, description = "Check price of jToken from Price-Centre by symbol is the same as tronscan")
    public void Test002CheckCentrePrice_jTokens_Tronscan() {
        compareTokensPriceWithTronscan(PriceCenterApiList.JTokenAddressMap,PriceCenterApiList.exp_JTokenValueMap);
    }

    @Test(enabled = true, description = "Check values correct between address/address, address/usd, symbol/symbol, symbol/exchangeRate")
    public void Test003CheckDiffFormat_ExchangeTokens() throws URISyntaxException {
        CheckSameValueGotByDiffFormat(PriceCenterApiList.ExgTokenAddressMap);
    }

    @Test(enabled = true, description = "Check values correct between address/address, address/usd, symbol/symbol, symbol/exchangeRate")
    public void Test004CheckDiffFormat_jTokens() throws URISyntaxException {
        CheckSameValueGotByDiffFormat(PriceCenterApiList.JTokenAddressMap);
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
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expTrxPrice, trxPrice));

        Object wtrxPrice = JSONPath.eval(responseContent, "$..data.WTRX.quote.USD.price[0]");
        log.info("Test005CompareTrxPriceWithCMC: expWTRXPrice: "+wtrxPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxPrice, wtrxPrice.toString()));

        Object jtrxPrice = JSONPath.eval(responseContent, "$..data.JTRX.quote.USD.price[0]");
        log.info("Test005CompareTrxPriceWithCMC: expWTRXPrice: "+jtrxPrice.toString());
        String expJTRXPrice = PriceCenterApiList.getZoomInValue(trxPrice,"100");
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(expJTRXPrice, jtrxPrice.toString()));
    }

    @Test(enabled = true, description = "Check values correct between address/address, address/usd, symbol/symbol, symbol/exchangeRate")
    public void Test006CheckDiffFormat_TrxRelated() throws URISyntaxException {
        Map<String,String> trxRelatedAddressMap = new HashMap<>();
        trxRelatedAddressMap.put("TRX","T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb");
        trxRelatedAddressMap.put("WTRX","TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR");
        trxRelatedAddressMap.put("JTRX","TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");
        CheckSameValueGotByDiffFormat(trxRelatedAddressMap);
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
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(swapPrice.toString(),centrePrice.toString()));
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
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(wbttPrice.toString(), bttoldPrice.toString()));
        String thousandthBttoldPrice = PriceCenterApiList.getZoomInValue(bttoldPrice.toString(),"1000");
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(thousandthBttoldPrice, bttPrice.toString()));
        String percentWbttPrice = PriceCenterApiList.getZoomInValue(wbttPrice.toString(),"100");
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(percentWbttPrice, jwbttPrice.toString()));
    }

    @Test(enabled = true, description = "Check values correct between address/address, address/usd, symbol/symbol, symbol/exchangeRate")
    public void Test009CheckDiffFormat_BttRelated() throws URISyntaxException {
        Map<String,String> bttRelatedAddressMap = new HashMap<>();
        bttRelatedAddressMap.put("WBTT","TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt");
        //bttRelatedAddressMap.put("BTTOLD","1002000"); //------为什么模版不适用呢？
        bttRelatedAddressMap.put("JWBTT","TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt");
        bttRelatedAddressMap.put("BTT","TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");
        CheckSameValueGotByDiffFormat(bttRelatedAddressMap);
    }
    //BTTOLD token的地址为数字形式，fastjson jsonpath无法解析。故单独处理。
    @Test(enabled = true, description = "Check values correct between address/address, address/usd, symbol/symbol, symbol/exchangeRate")
    public void Test010CheckDiffFormat_Bttold() throws URISyntaxException {
        HttpResponse symbolResp;
        JSONObject symbolRespContent;
        HttpResponse addressResp;
        JSONObject addressRespContent;
        //print prepared data.
        params.clear();
        params.put("symbol", "BTTOLD");
        params.put("convert", "TRX,USDT,USD,CNY,GBP,EUR");
        symbolResp = PriceCenterApiList.getprice(params);
        symbolRespContent = TronlinkApiList.parseJsonObResponseContent(symbolResp);

        params.clear();
        params.put("symbol", "1002000");
        params.put("convert", "T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb,TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t,USD");
        addressResp = PriceCenterApiList.getprice(params);
        addressRespContent = TronlinkApiList.parseJsonObResponseContent(addressResp);

        //1 地址-》地址       SwapToken-〉TRX； 交易所-》USDT。
        //2 地址-》USD：
        //3 symbol-》symbol: 交易所常用对交易所常用。
        //4 symbol-》四种汇率（USD，CNY，EUR，GBP）

        //Check: 1 equals to 3 for each Token.   trx、usdt.(同一个计价单位trx或者usdt，地址访问=symbol访问)
        //Check: 2 equals to 4 for each Token;   usd（统一计价单位USD，地址访问=symbold访问）
        //Check: 不通计价单位的值都换算成trx价格，应该与trx价格之差在容忍度内。 例：cny价格/（cny/trx）=trx价格。
        //暂不支持地址对CNY，EUR，GBP。

        Object trxSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.BTTOLD.quote.TRX.price[0]"));
        Object trxAddressPrice = JSONPath.eval(addressRespContent,String.join("","$..quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]"));
        Object usdtSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..data.BTTOLD.quote.USDT.price[0]"));
        Object usdtAddressPrice = JSONPath.eval(addressRespContent,String.join("","$..quote.TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t.price[0]")); ;
        Object usdSymbolPrice = JSONPath.eval(symbolRespContent, String.join("", "$..quote.USD.price[0]"));
        Object usdAddressPrice = JSONPath.eval(addressRespContent, String.join("", "$..quote.USD.price[0]"));
        Object cnyPrice = JSONPath.eval(symbolRespContent, String.join("", "$..quote.CNY.price[0]"));
        Object eurPrice = JSONPath.eval(symbolRespContent, String.join("", "$..quote.EUR.price[0]"));
        Object gbpPrice = JSONPath.eval(symbolRespContent, String.join("", "$..quote.GBP.price[0]"));
        log.info("CheckSameValueGotByDiffFormat:trxSymbolPrice:"+trxSymbolPrice+", trxAddressPrice:"+trxAddressPrice+", usdtSymbolPrice:"+usdtSymbolPrice+", usdtAddressPrice:"+usdtAddressPrice);
        log.info("CheckSameValueGotByDiffFormat:cnyPrice:"+cnyPrice+", eurPrice:"+eurPrice+", gbpPrice:"+gbpPrice+", usdSymbolPrice:"+usdSymbolPrice +", usdAddressPrice:"+usdAddressPrice);

        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(trxSymbolPrice.toString(), trxAddressPrice.toString()));
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(usdtSymbolPrice.toString(), usdtAddressPrice.toString()));
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(usdSymbolPrice.toString(), usdSymbolPrice.toString()));


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
            Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(relatedTokenPrice, wTokenPrice.toString()));
        }
    }

    //wtokens need to do later
    @Test(enabled = true, description = "Check values correct between address/address, address/usd, symbol/symbol, symbol/exchangeRate")
    public void Test012CheckDiffFormat_newPoolTokens() throws URISyntaxException {
        CheckSameValueGotByDiffFormat(PriceCenterApiList.newPoolAddressMap);
    }
    //check getPrice usd Price equals to getAllPrice USD price.

}



