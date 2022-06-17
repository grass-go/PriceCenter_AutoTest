package tron.priceCenter.DataProviderTest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;
import tron.common.TronlinkApiList;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class CheckDiffAccessWay {
    public HttpResponse response;
    public JSONObject responseContent;

    @BeforeClass(enabled = true,description = "get exchange Rate")
    public void prepareTokenExpPriceFromOtherPlat() {
        PriceCenterApiList.SetTrxPriceMap();
    }

    public void CheckSameValueGotByDiffFormat(String curSymbol,String curAddress, String curTolerance ) throws URISyntaxException {
        responseContent = PriceCenterApiList.getprice(curSymbol,"TRX,USDT,USD,CNY,GBP,EUR");
        String upperSymbol = curSymbol.toUpperCase();
        Object trxSymbolPrice = JSONPath.eval(responseContent, String.join("", "$..data.",upperSymbol,".quote.TRX.price[0]"));
        Object usdtSymbolPrice = JSONPath.eval(responseContent, String.join("", "$..data.",upperSymbol,".quote.USDT.price[0]"));
        Object usdSymbolPrice = JSONPath.eval(responseContent, String.join("", "$..data.",upperSymbol,".quote.USD.price[0]"));
        Object cnyPrice = JSONPath.eval(responseContent, String.join("", "$..data.",upperSymbol,".quote.CNY.price[0]"));
        Object eurPrice = JSONPath.eval(responseContent, String.join("", "$..data.",upperSymbol,".quote.EUR.price[0]"));
        Object gbpPrice = JSONPath.eval(responseContent, String.join("", "$..data.",upperSymbol,".quote.GBP.price[0]"));

        responseContent = PriceCenterApiList.getprice(curAddress,"T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb,TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t,USD");
        Object trxAddressPrice;
        Object usdtAddressPrice;
        Object usdAddressPrice;
        if(curSymbol.equals("BTTOLD"))
        {
            trxAddressPrice = JSONPath.eval(responseContent, String.join("", "$..quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]"));
            usdtAddressPrice = JSONPath.eval(responseContent, String.join("", "$..quote.TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t.price[0]"));
            ;
            usdAddressPrice = JSONPath.eval(responseContent, String.join("", "$..quote.USD.price[0]"));

        }else {
            trxAddressPrice = JSONPath.eval(responseContent, String.join("", "$..data.", curAddress, ".quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]"));
            usdtAddressPrice = JSONPath.eval(responseContent, String.join("", "$..data.", curAddress, ".quote.TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t.price[0]"));
            ;
            usdAddressPrice = JSONPath.eval(responseContent, String.join("", "$..data.", curAddress, ".quote.USD.price[0]"));
        }
        log.info("CheckSameValueGotByDiffFormat:trxSymbolPrice:"+trxSymbolPrice+", trxAddressPrice:"+trxAddressPrice+", usdtSymbolPrice:"+usdtSymbolPrice+", usdtAddressPrice:"+usdtAddressPrice);
        log.info("CheckSameValueGotByDiffFormat:cnyPrice:"+cnyPrice+", eurPrice:"+eurPrice+", gbpPrice:"+gbpPrice+", usdSymbolPrice:"+usdSymbolPrice +", usdAddressPrice:"+usdAddressPrice);


        //1 地址-》地址       SwapToken-〉TRX； 交易所-》USDT。
        //2 地址-》USD：
        //3 symbol-》symbol: 交易所常用对交易所常用。
        //4 symbol-》四种汇率（USD，CNY，EUR，GBP）

        //Check: 1 equals to 3 for each Token.   trx、usdt.(同一个计价单位trx或者usdt，地址访问=symbol访问)
        //Check: 2 equals to 4 for each Token;   usd（统一计价单位USD，地址访问=symbold访问）
        //Check: 不通计价单位的值都换算成trx价格，应该与trx价格之差在容忍度内。 例：cny价格/（cny/trx）=trx价格。
        //暂不支持地址对CNY，EUR，GBP。

        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(trxSymbolPrice.toString(), trxAddressPrice.toString(),curTolerance));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(usdtSymbolPrice.toString(), usdtAddressPrice.toString(),curTolerance));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(usdSymbolPrice.toString(), usdSymbolPrice.toString(),curTolerance));


        String calcTrxPrice_usdtSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(usdtSymbolPrice.toString(),PriceCenterApiList.trxPriceMap.get("USDT"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(trxSymbolPrice.toString(), calcTrxPrice_usdtSymbol,curTolerance));

        String calcTrxPrice_usdSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(usdSymbolPrice.toString(),PriceCenterApiList.trxPriceMap.get("USD"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(trxSymbolPrice.toString(), calcTrxPrice_usdSymbol,curTolerance));

        String calcTrxPrice_cnySymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(cnyPrice.toString(),PriceCenterApiList.trxPriceMap.get("CNY"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(trxSymbolPrice.toString(), calcTrxPrice_cnySymbol,curTolerance));

        String calcTrxPrice_eurSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(eurPrice.toString(),PriceCenterApiList.trxPriceMap.get("EUR"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(trxSymbolPrice.toString(), calcTrxPrice_eurSymbol,curTolerance));

        String calcTrxPrice_gbpSymbol = PriceCenterApiList.exchangeOtherPriceToTrxPrice(gbpPrice.toString(),PriceCenterApiList.trxPriceMap.get("GBP"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(trxSymbolPrice.toString(), calcTrxPrice_gbpSymbol,curTolerance));


    }

    @DataProvider(name = "ddt")
    public Object[][] data() throws IOException {

        File directory = new File(".");
        String tokenFile= directory.getCanonicalFile() + "/src/test/resources/TestData/Price/"
                + "CommonTokenTest" + ".csv";
        List<String> contentLines = PriceCenterApiList.ReadFile(tokenFile);

        int columnNum = 0;
        int totalLine = contentLines.size();
        columnNum = contentLines.get(0).split(",").length;

        Object[][] data = new Object[totalLine-1][columnNum];
        for(int i = 1; i<totalLine; i++){
            String stringValue[] = contentLines.get(i).split(",");
            for(int j = 0; j<columnNum; j++) {
                data[i-1][j] = stringValue[j];
            }
        }
        log.info("data:"+data.toString());
        return data;
    }

    @Test(dataProvider = "ddt")
    public void test001DiffFormat(String symbol, String address, String compareWith,String tolerance) throws URISyntaxException, IOException {
        /*File directory = new File(".");
        log.info("data:debug:"+directory.getCanonicalFile());*/
        log.info("test001DiffFormat:"+symbol,", address", address, ", compareWith:",compareWith, ", tolerance:");
        CheckSameValueGotByDiffFormat(symbol,address,tolerance);
    }

}
