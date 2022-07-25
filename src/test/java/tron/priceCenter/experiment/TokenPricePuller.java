package tron.priceCenter.experiment;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.FileOperation;
import tron.tronlink.base.TronlinkBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenPricePuller extends TronlinkBase {

    private String testAddress = "TJNSYUxnViYJH95oK3fk9aZH7ejr5BFvQW";
    private String token = "";

    @Test(enabled = true,description = "get part token prices", invocationCount = 10)
    public void GetTokenPrice() throws InterruptedException {
        BigDecimal first = GetV2PriceByToken();
        BigDecimal sencond = GetSinglePriceByToken();
        BigDecimal three  = GetAllPriceByToken();

        String line = System.currentTimeMillis() + "\t" +
                first + "\t" + sencond + "\t" + three;
        FileOperation.OutToFile("./price_result.txt", line);

        Thread.sleep(5 * 1000);
    }

    public BigDecimal GetV2PriceByToken(){
        Map<String,String> params = GenerateParams();
        Map<String,String> headers = GenerateHeaders();
        HttpResponse response = TronlinkApiList.v2GetAssetList(params, null, headers);
        JSONObject assetListRespContent = TronlinkApiList.parseJsonObResponseContent(response);

        log.debug(assetListRespContent.toJSONString());
        Object usdPrice = JSONPath.eval(assetListRespContent, String.join("","$..data.token[contractAddress='TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE'].usdPrice[0]"));
        BigDecimal up = new BigDecimal((String)usdPrice);
        log.info("first usd price = " + up);
        return up;

    }




    public Map<String,String> GenerateParams(){
        Map<String,String> params = new HashMap<>();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        HashMap<String,String> sigs = new HashMap<>();
        sigs.put("address",testAddress);
        sigs.put("url", "/api/wallet/v2/assetList");
        try {
            String sig = getSign(sigs);
            params.put("signature",sig);
        }catch (Exception e){
            e.printStackTrace();
        }
        params.put("address", testAddress);
        params.put("version", "v2");
        return params;
    }

    public Map<String, String> GenerateHeaders(){
        Map<String,String> headers = TronlinkApiList.getV2Header();
        headers.put("deviceName", "wqq");
        headers.put("osVersion", "10");
        log.info("headers : " + headers.toString());
        return headers;
    }

    public BigDecimal GetSinglePriceByToken(){
        HttpResponse response = TronlinkApiList.createGetConnect("https://c.tronlink.org/v1/cryptocurrency/getprice?symbol=TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE&convert=USD");

        JSONObject assetListRespContent = TronlinkApiList.parseJsonObResponseContent(response);
        log.debug(assetListRespContent.toJSONString());
        Object usdPrice = JSONPath.eval(assetListRespContent, String.join("","$..data[0].TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE.quote.USD.price"));
        BigDecimal up = new BigDecimal((String)usdPrice);
        log.info("second usd price = " + up);
        return up;
    }

    public BigDecimal GetAllPriceByToken(){
        HttpResponse response = TronlinkApiList.createGetConnect("https://c.tronlink.org/v1/cryptocurrency/getallprice");

        JSONObject assetListRespContent = TronlinkApiList.parseJsonObResponseContent(response);
        log.debug(assetListRespContent.toJSONString());
        Object usdPrice = JSONPath.eval(assetListRespContent, String.join("","$..data[0].rows[fTokenAddr='TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE'][sShortName = 'USD'].price[0]"));
        BigDecimal up = new BigDecimal((String)usdPrice);
        log.info("three usd price = " + up);
        return up;

    }
}
