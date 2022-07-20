package tron.tronlink.v2;

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
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","l8zYMIE%2Feav7sTCetMLAcgZqV00%3D");
        params.put("address",price_B58);
        params.put("version","v2");

        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        assetListRespContent = TronlinkApiList.parseJsonObResponseContent(response);
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
        response = TronlinkApiList.createGetConnectWithHeader(CMCurl,params,null,header);
        JSONObject responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object expPrice = JSONPath.eval(responseContent, String.join("", "$..data.TRX.quote."+exchageType+".price[0]"));
        String trxPrice = expPrice.toString();
        return trxPrice;
    }
    public static void SetTrxPriceMap() {
        trxPriceMap.put("USD",getTrxPricefromCMC("USD"));
        trxPriceMap.put("USDT",getTrxPricefromCMC("USDT"));
        trxPriceMap.put("CNY",getTrxPricefromCMC("CNY"));
        trxPriceMap.put("EUR",getTrxPricefromCMC("EUR"));
        trxPriceMap.put("GBP",getTrxPricefromCMC("GBP"));
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

    @Test(enabled = false, dataProvider = "checkPriceTokens")
    public void test001CompareTokenPriceWithTronscan(String symbol, String address, String tolerance) throws URISyntaxException, IOException {
        log.info("test001CompareTokenPriceWithTronscan:"+symbol,", address", address, ", tolerance:");
        Object trxCount_obj;
        Object usdCount_obj;
        Object cnyCount_obj;

        if (symbol == "BTTOLD" || symbol == "USDD10"){
            trxCount_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[id='", address, "'].trxCount[0]"));
            usdCount_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[id='", address, "'].usdCount[0]"));
            cnyCount_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[id='", address, "'].cnyCount[0]"));
        }
        else{
            trxCount_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[contractAddress='", address, "'].trxCount[0]"));
            usdCount_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[contractAddress='", address, "'].usdCount[0]"));
            cnyCount_obj = JSONPath.eval(assetListRespContent,String.join("","$..data.token[contractAddress='", address, "'].cnyCount[0]"));
        }


        //get token unit price in assetlist response
        //get token unit price in tronscan
        //compare price between assetlist api and tronscan api.
    }

    @Test(enabled = false)
    public void test002trxPrice() {


    }


}
