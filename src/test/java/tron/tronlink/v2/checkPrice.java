package tron.tronlink.v2;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class checkPrice extends TronlinkBase {
    private static HttpResponse response;
    private JSONObject responseContent;
    private JSONObject dataContent;
    private JSONObject object;
    private JSONObject assetListRespContent;
    Map<String, String> params = new HashMap<>();
    public static Map<String,String> trxPriceMap= new HashMap<>();

    @BeforeClass(enabled = true,description = "get user's assetlist before test")
    public void assetList_request() {
        params.put("address",price_B58);
        params.put("version","v2");

        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        assetListRespContent = TronlinkApiList.parseResponse2JsonObject(response);
        SetTrxPriceMap();
    }

    public static String getTrxPricefromCMC(String exchageType){
        Map<String,String> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        header.put("Content-Type","MediaType.MULTIPART_FORM_DATA");
        header.put("X-CMC_PRO_API_KEY","8cd87197-8386-4bcb-835c-ff7b78d6ba48");
        header.put("Accept","application/json");
        Map<String,String> params = new HashMap<>();
        params.put("symbol","TRX");
        params.put("convert",exchageType);
        String CMCurl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
        response = TronlinkApiList.createGetConnect(CMCurl,params,null,header);
        if(response == null || response.getStatusLine().getStatusCode() != 200){
            return null;
        }
        JSONObject responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object expPrice = JSONPath.eval(responseContent, String.join("", "$..data.TRX.quote."+exchageType+".price[0]"));
        String trxPrice = expPrice.toString();
        return trxPrice;
    }
    public static void SetTrxPriceMap() {
        String trxPrice = getTrxPricefromCMC("CNY");
        if (trxPrice == null){
            JSONObject trxPriceResp = TronlinkApiList.getprice("TRX","CNY");
            Object trxPrice_obj =JSONPath.eval(trxPriceResp,"$..data.TRX.quote.CNY.price[0]");
            trxPrice = trxPrice_obj.toString();
        }
        trxPriceMap.put("CNY",trxPrice);
    }

    @DataProvider(name = "checkPriceTokens")
    public Object[][] data() throws IOException {

        String datafile = Configuration.getByPath("testng.conf").getString("checkPriceTokens");
        File directory = new File(".");
        String tokenFile= directory.getCanonicalFile() + datafile;
        List<String> contentLines = TronlinkApiList.ReadFile(tokenFile);

        int columnNum = 0;
        int totalLine = contentLines.size();
        columnNum = contentLines.get(0).split(",").length;

        Object[][] data = new Object[totalLine-2][columnNum];
        for(int i = 2; i<totalLine; i++){
            String stringValue[] = contentLines.get(i).split(",");
            for(int j = 0; j<columnNum; j++) {
                data[i-2][j] = stringValue[j];
            }
        }
        log.info("data:"+data.toString());
        return data;
    }

    @Test(enabled = true, dataProvider = "checkPriceTokens", description = "token without WBTC and WETH")
    public void test001CompareTokenPriceWithTronscan(String symbol, String address, String tolerance) {
        log.info("test001CompareTokenPriceWithTronscan:"+symbol,", address", address, ", tolerance:");
        Object trxPrice_obj;
        Object usdPrice_obj;
        Object cnyPrice_obj;

        //get token unit price in assetlist response
        if ("BTTOLD".equalsIgnoreCase(symbol) || "USDD10".equalsIgnoreCase(symbol)){
            trxPrice_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[id='", address, "'].price[0]"));
            usdPrice_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[id='", address, "'].usdPrice[0]"));
            cnyPrice_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[id='", address, "'].cnyPrice[0]"));
        }
        else{
            trxPrice_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[contractAddress='", address, "'].price[0]"));
            usdPrice_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[contractAddress='", address, "'].usdPrice[0]"));
            cnyPrice_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[contractAddress='", address, "'].cnyPrice[0]"));
        }

        //get token unit price in tronscan
        JSONObject tronscanPriceObj = TronlinkApiList.getTronscanTrc20Price(address);
        Assert.assertNotEquals(tronscanPriceObj, null);
        System.out.println(tronscanPriceObj.toJSONString());
        String tronscanTrxPrice = tronscanPriceObj.get("priceInTrx").toString();
        String tronscanUsdPrice = tronscanPriceObj.get("priceInUsd").toString();
        log.info("trxPriceMap:"+trxPriceMap.toString());
        String cmcCnyPrice = new BigDecimal(trxPriceMap.get("CNY")).multiply(new BigDecimal(trxPrice_obj.toString())).toString();

        //compare price between assetlist api and tronscan api.
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(trxPrice_obj.toString(), tronscanTrxPrice, tolerance));
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(usdPrice_obj.toString(), tronscanUsdPrice, tolerance));
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(cnyPrice_obj.toString(), cmcCnyPrice, tolerance));
    }

    @Test(enabled = false)
    public void test002trxPrice() {


    }


}
