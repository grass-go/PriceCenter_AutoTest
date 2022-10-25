package tron.priceCenter.DataProviderTest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;
import tron.common.utils.Configuration;
import tron.priceCenter.base.priceBase;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
public class CheckCMCTokensPriceWithCMC extends priceBase {
    public JSONObject allpriceResponseContent;

    @BeforeClass(enabled = true,description = "get allprice response")
    public void prepareGetAllPriceContent() throws URISyntaxException {
        String allpriceResponse_str = PriceCenterApiList.getallprice();
        allpriceResponseContent = JSON.parseObject(allpriceResponse_str);
    }

    @DataProvider(name = "ddt")
    public Object[][] data() throws IOException {

        String datafile = Configuration.getByPath("testng.conf").getString("commonTokens");
        File directory = new File(".");
        String tokenFile = directory.getCanonicalFile() + datafile;
        List<String> contentLines = PriceCenterApiList.ReadFile(tokenFile);

        int columnNum = 0;
        int totalLine = contentLines.size();
        columnNum = contentLines.get(0).split(",").length;

        Object[][] data = new Object[totalLine - 3][3];
        int dataIndex = 0;
        for (int i = 1; i < totalLine; i++) {
            String stringValue[] = contentLines.get(i).split(",");
            if (stringValue[0].equals("TRX") || stringValue[0].equals("USDT")) {
                continue;
            } else {
                data[dataIndex][0] = stringValue[0];
                data[dataIndex][1] = stringValue[1];
                data[dataIndex][2] = stringValue[3];
                dataIndex += 1;
            }
        }
        log.info("data:" + data.toString());
        return data;
    }

    @Test(enabled = true, dataProvider = "ddt")
    public void test001CheckPriceWithCMC(String symbol, String address, String tolerance) throws URISyntaxException, InterruptedException {
        log.info("test001CheckPriceBetweenGetPriceAndGetAllPrice:symbol:"+symbol+", address:"+address + ", tolerance:"+tolerance);
        Thread.sleep(1000);
        //get getallprice response once for all the tokens.

        String curSymbol = symbol;
        String curTokenAddress = address;

        //get trx/usd price from getallprice API
        String myjsonpath="$..data.rows[fTokenAddr='"+curTokenAddress+"']";


        List<String> getallprice_Price = PriceCenterApiList.getTRXandUSDbyfTokenAddrFromAllAPI(allpriceResponseContent,myjsonpath);
        String getallprice_trxPrice=getallprice_Price.get(0);
        String getallprice_usdPrice=getallprice_Price.get(1);

        String cmcUSDPrice = PriceCenterApiList.getPricefromCMC(curSymbol, "USD");
        String cmcTRXPrice = PriceCenterApiList.getPricefromCMC(curSymbol, "TRX");

        log.info("test001CheckPriceWithCMC:TRX:getallpriceResult:"+getallprice_trxPrice+", cmcPrice:"+cmcTRXPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(getallprice_trxPrice, cmcTRXPrice,tolerance));


        log.info("test001CheckPriceWithCMC:USD:getallpriceResult:"+getallprice_usdPrice+", cmcPrice:"+cmcUSDPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(getallprice_usdPrice, cmcUSDPrice,tolerance));


    }
}
