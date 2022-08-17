package tron.priceCenter.DataProviderTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.api.Http;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.priceCenter.base.priceBase;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Check0xTokenPriceWithBA extends priceBase {

    private JSONObject ResponseContent;

    @DataProvider(name = "0xToken")
    public Object[][] data() throws IOException, URISyntaxException {
        String datafile = Configuration.getByPath("testng.conf").getString("tokens0x");
        File directory = new File(".");
        String tokenFile= directory.getCanonicalFile() + datafile;
        List<String> contentLines = PriceCenterApiList.ReadFile(tokenFile);

        int columnNum = 0;
        int totalLine = contentLines.size();
        columnNum = contentLines.get(0).split(",").length;
        Object[][] data = new Object[totalLine][4];

        //request to BiAn to get BA pirce.
        String baURL = "https://api.binance.com/api/v3/ticker/price";
        URIBuilder builder = new URIBuilder(baURL);
        URI requestUri = builder.build();
        HttpResponse baRsp = PriceCenterApiList.createGetConnect(requestUri);
        JSONArray baRspArray = TronlinkApiList.parseJsonArrayResponseContent(baRsp);
        JSONObject baContents = new JSONObject();
        baContents.put("baTokens",baRspArray);
        System.out.println("baContents:"+baContents.toString());

        String allTokenStr="";
        for(int i = 0; i<totalLine; i++){
            String stringValue[] = contentLines.get(i).split(",");
            String curTokenAddress = stringValue[0];
            String curTokenSymbol = stringValue[1];
            data[i][0] = curTokenAddress;
            data[i][1] = curTokenSymbol;
            allTokenStr = allTokenStr + curTokenAddress + ",";
            Object baPrice_obj = JSONPath.eval(baContents, "$.baTokens[symbol='"+curTokenSymbol+"USDT'].price[0]");
            System.out.println("baPrice_obj:"+baPrice_obj.toString());
            data[i][2] = baPrice_obj.toString();
        }

        //get Price from price-centre.
        allTokenStr = allTokenStr.substring(0,allTokenStr.length()-1);
        Map<String,String> params = new HashMap<>();
        params.put("symbol", allTokenStr);
        params.put("convert", "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        HttpResponse response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        JSONObject centreContent = TronlinkApiList.parseJsonObResponseContent(response);
        for(int i = 0; i<totalLine; i++){
            String curTokenAddress = (String) data[i][0];
            Object centrePrice_obj = JSONPath.eval(centreContent, "$..data." + curTokenAddress + ".quote.TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t.price");
            List<String> centrePrice_list = (List<String>) centrePrice_obj;
            System.out.println("centrePrice_obj:" + centrePrice_list.get(0) );
            data[i][3] = centrePrice_list.get(0);
        }

        System.out.println("data:"+data.toString());
        return data;

    }

    @Test(dataProvider = "0xToken")
    public void test0xTokenPrice(String TokenAddr, String ShortName , String baPrice,String centerPrice) throws URISyntaxException, IOException, InterruptedException {
        log.info("ShortName:"+ShortName+", TokenAddr:"+TokenAddr+", baPrice:"+baPrice+", centerPrice:"+centerPrice);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(baPrice, centerPrice, "0.1"));

    }




}
