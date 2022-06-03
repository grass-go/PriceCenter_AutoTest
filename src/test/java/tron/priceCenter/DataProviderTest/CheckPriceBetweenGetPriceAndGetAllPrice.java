package tron.priceCenter.DataProviderTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
public class CheckPriceBetweenGetPriceAndGetAllPrice {
    public JSONObject allpriceResponseContent;

    @BeforeClass(enabled = true,description = "get exchange Rate")
    public void prepareGetAllPriceContent() throws URISyntaxException {
        String allpriceResponse_str = PriceCenterApiList.getallprice();
        allpriceResponseContent = JSON.parseObject(allpriceResponse_str);
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

        Object[][] data = new Object[totalLine-3][3];
        int dataIndex = 0;
        for(int i = 1; i<totalLine; i++){
            String stringValue[] = contentLines.get(i).split(",");
            if(stringValue[0].equals("TRX") || stringValue[0].equals("USDT")){
                continue;
            }else{
                data[dataIndex][0] = stringValue[0];
                data[dataIndex][1] = stringValue[1];
                data[dataIndex][2] = stringValue[3];
                dataIndex +=1;
            }
        }
        log.info("data:"+data.toString());
        return data;
    }

    @Test(dataProvider = "ddt")
    public void test001CheckPriceBetweenGetPriceAndGetAllPrice(String symbol,String address,String tolerance) throws URISyntaxException {
        log.info("test001CheckPriceBetweenGetPriceAndGetAllPrice:symbol:"+symbol+", address:"+address + ", tolerance:"+tolerance);
        //get getallprice response once for all the tokens.

        String curSymbol = symbol;
        String curTokenAddress = address;

        //get trx/usd price from getallprice API
        String myjsonpath;
        if (curSymbol.equals("BTTOLD")) {
            myjsonpath ="$..data.rows[fShortName='"+curSymbol+"']";
        } else{
            myjsonpath="$..data.rows[fTokenAddr='"+curTokenAddress+"']";
        }

        java.util.List<String> getallprice_Price = PriceCenterApiList.getTRXandUSDbyfTokenAddrFromAllAPI(allpriceResponseContent,myjsonpath);
        String getallprice_trxPrice=getallprice_Price.get(0);
        String getallprice_usdPrice=getallprice_Price.get(1);

        //get trx price from getprice API and compare with getallprice API
        JSONObject getprice_obj = PriceCenterApiList.getprice(curTokenAddress,"T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb");
        Object getprice_result;
        if(curSymbol.equals("BTTOLD")) {
            getprice_result = JSONPath.eval(getprice_obj, "$..quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]");
        }else{
            getprice_result = JSONPath.eval(getprice_obj, "$..data." + curTokenAddress + ".quote.T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb.price[0]");
        }
        String getprice_trxPrice = getprice_result.toString();
        log.info("test001CheckPriceBetweenGetPriceAndGetAllPrice:TRX:getallpriceResult:"+getallprice_trxPrice+", getpriceResult"+getprice_trxPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(getallprice_trxPrice, getprice_trxPrice,tolerance));


        //get usd price from getprice API and compare with getallprice API
        getprice_obj = PriceCenterApiList.getprice(curTokenAddress,"USD");
        getprice_result = JSONPath.eval(getprice_obj,"$..data."+curTokenAddress+".quote.USD.price[0]");
        String getprice_usdPrice = getprice_result.toString();
        log.info("test001CheckPriceBetweenGetPriceAndGetAllPrice:USD:getallpriceResult:"+getallprice_usdPrice+", getpriceResult"+getprice_usdPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(getallprice_usdPrice, getprice_usdPrice,tolerance));
    }
}
