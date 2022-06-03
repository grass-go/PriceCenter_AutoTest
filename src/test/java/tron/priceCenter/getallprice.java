package tron.priceCenter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import org.testng.annotations.BeforeClass;
import tron.common.TronlinkApiList;
import tron.common.PriceCenterApiList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class getallprice {
    private HttpResponse response;
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject object;
    Map<String, String> params = new HashMap<>();

    private HttpResponse allpriceResponse;
    private JSONObject allpriceResponseContent;
    private JSONArray allpriceTokensArray = new JSONArray();
    String usdTrxPrice = "";


    public java.util.List<String> getTRXandUSDbyfTokenAddr(JSONObject allpriceResponseContent, String jsonpath){
        java.util.List<String> prices= new java.util.ArrayList<String>();
        Object itemsArray = JSONPath.eval(allpriceResponseContent, jsonpath);
        JSONArray jsonArray = (JSONArray)itemsArray;
        String trxPrice = "";
        String usdPrice = "";
        for (int i=0; i<jsonArray.size(); i++ ){
            JSONObject curItem = jsonArray.getJSONObject(i);
            log.info("curItem:"+curItem.toString());
            String price = (String) curItem.get("price");
            log.info("price:"+price);
            if(curItem.get("sShortName").equals("TRX")){
                trxPrice = price;
            }else if ( curItem.get("sShortName").equals("USD")) {
                usdPrice = price;
            }
        }
        prices.add(trxPrice);
        prices.add(usdPrice);
        return prices;
    }

    @BeforeClass(enabled = true,description = "getallprice and get usdt->trx price")
    public void getAllPriceContentAndUSDTRXPrice() throws InterruptedException, URISyntaxException {
        String allpriceResponse_str = PriceCenterApiList.getallprice();
        allpriceResponseContent = JSON.parseObject(allpriceResponse_str);
        log.info("getAllPriceContentAndUSDTRXPrice,allpriceResponseContent:"+allpriceResponseContent.toString());

        //http://47.253.46.247:8051/v1/cryptocurrency/getprice?symbol=USDT&convert=TRX
        responseContent = PriceCenterApiList.getprice("USD","TRX");
        log.info("getAllPriceContentAndUSDTRXPrice:responseContent:"+responseContent.toString());
        Object usdttrx = JSONPath.eval(responseContent, "$..USD.quote.TRX.price[0]");
        usdTrxPrice = usdttrx.toString() ;
        log.info("usdTrxPrice:"+usdTrxPrice);

    }

    @Test(enabled = true, description = "Test getallprice api return normally")
    public void Test001getallprice() {
        int rowCount = allpriceTokensArray.size();
        Assert.assertTrue(rowCount > 500);
        object = (JSONObject) allpriceTokensArray.get(0);
        Assert.assertTrue(object.containsKey("fShortName"));
        Assert.assertTrue(object.containsKey("fTokenAddr"));
        Assert.assertTrue(object.containsKey("price"));
        Assert.assertTrue(object.containsKey("sShortName"));
        Assert.assertTrue(object.containsKey("sTokenAddr"));
        Object statusCode = JSONPath.eval(allpriceResponseContent, "$..status.error_code[0]");
        Assert.assertEquals(0,statusCode);
        Object error_msg = JSONPath.eval(allpriceResponseContent, "$..status.error_message[0]");
        Assert.assertNull(error_msg);
    }

    @Test(enabled = true, description = "Test USDD in getallprice")
    public void Test002USDD() {
        //log.info("Test002USDD: allpriceResponseContent:"+allpriceResponseContent);

        //USDD 10 ->TRX
        java.util.List<String> prices = getTRXandUSDbyfTokenAddr(allpriceResponseContent,"$..data.rows[fTokenAddr='1004777']");
        String t10trx = prices.get(0);
        String t10usd = prices.get(1);

        prices = getTRXandUSDbyfTokenAddr(allpriceResponseContent,"$..data.rows[fTokenAddr='TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn']");
        String t20trx = prices.get(0);
        String t20usd = prices.get(1);

        log.info("usdtrx:"+ usdTrxPrice);
        log.info("usdd t10trx:"+ t10trx);
        log.info("usdd t10usd:"+ t10usd);
        log.info("usdd t20trx:"+ t20trx);
        log.info("usdd t20usd:"+ t20usd);

        String oneusd = String.valueOf("1.000000000000000000");
        log.info(String.valueOf(oneusd.length()));

        Assert.assertTrue(t10usd.equals("1.000000000000000000"));
        Assert.assertTrue(t20usd.equals("1.000000000000000000"));

        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(usdTrxPrice, t10trx,"0.01"));
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(usdTrxPrice, t20trx,"0.01"));
    }

    //WBTT, BTTOLD, jWBTT, BTT; Relationship: WBTT=BTTOLD=100jWBTT=1000BTT
    @Test(enabled = true, description = "Test BTT in getallprice")
    public void Test003BTTRelated() {

        //Check BTTOLD(fix BTTOLD value to test BTT)
        String expBTTOLDtrxPrice = "0.012644975581545992";
        java.util.List<String> prices = getTRXandUSDbyfTokenAddr(allpriceResponseContent,"$..data.rows[fTokenAddr='1002000']");
        String bttoldtrx = prices.get(0);
        String bttoldusd = prices.get(1);

        log.info("bttoldtrx:" + bttoldtrx);
        log.info("bttoldusd:"+ bttoldusd);

        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expBTTOLDtrxPrice, bttoldtrx,"0.1"));

        BigDecimal usdTrxPrice_bd = new BigDecimal(usdTrxPrice);
        BigDecimal bttoldtrx_bd = new BigDecimal(bttoldtrx);
        BigDecimal expbttoldusd = bttoldtrx_bd.divide(usdTrxPrice_bd,18,1);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expbttoldusd.toString(), bttoldusd,"0.01"));


        //Check WBTT
        prices = getTRXandUSDbyfTokenAddr(allpriceResponseContent,"$..data.rows[fTokenAddr='TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt']");
        String wbtttrx = prices.get(0);
        String wbttusd = prices.get(1);
        log.info("wbtttrx:" + wbtttrx);
        log.info("wbttusd:"+ wbttusd);
        Assert.assertEquals(wbtttrx, bttoldtrx);
        Assert.assertEquals(wbttusd, bttoldusd);

        //check jWBTT
        prices = getTRXandUSDbyfTokenAddr(allpriceResponseContent,"$..data.rows[fTokenAddr='TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt']");
        String jwbtttrx = prices.get(0);
        String jwbttusd = prices.get(1);
        log.info("jwbtttrx:" + jwbtttrx);
        log.info("jwbttusd:"+ jwbttusd);

        BigDecimal onehundred = new BigDecimal("100");
        BigDecimal wbtttrx_bd = new BigDecimal(wbtttrx);
        BigDecimal expjWBTTtrx = wbtttrx_bd.divide(onehundred,18,1);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expjWBTTtrx.toString(), jwbtttrx,"0.01"));
        BigDecimal wbttusd_bd = new BigDecimal(wbttusd);
        BigDecimal expjWBTTusd = wbttusd_bd.divide(onehundred,18,1);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expjWBTTusd.toString(), jwbttusd,"0.01"));

        //check BTT
        prices = getTRXandUSDbyfTokenAddr(allpriceResponseContent,"$..data.rows[fTokenAddr='TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4']");
        String btttrx = prices.get(0);
        String bttusd = prices.get(1);
        log.info("btttrx:" + btttrx);
        log.info("bttusd:"+ bttusd);

        BigDecimal onethousand = new BigDecimal("1000");
        BigDecimal expbtttrx = bttoldtrx_bd.divide(onethousand,18,1);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expbtttrx.toString(), btttrx,"0.01"));
        BigDecimal bttoldusd_bd = new BigDecimal(bttoldusd);
        BigDecimal expbttusd = bttoldusd_bd.divide(onethousand,18,1);
        Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(expbttusd.toString(), bttusd,"0.01"));
    }

}
