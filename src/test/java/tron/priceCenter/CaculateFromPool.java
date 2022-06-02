package tron.priceCenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.tron.common.utils.Commons;
import tron.common.PriceCenterApiList;
import tron.common.TronlinkApiList;
import tron.common.TronscanApiList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class CaculateFromPool {

    private HttpResponse response;
    private JSONObject responseContent;
    private JSONObject object;



    public static Map<String,String> SymbolAddressMap= new HashMap<>();
    public static Map<String,String> SymbolPoolMap= new HashMap<>();
    public static Map<String,String> CentrePriceMap= new HashMap<>();
    public static Map<String,String> CoinContainsToken= new HashMap<>();



    static {
    SymbolAddressMap.put("2USD", "TXcJ6pCEGKeLEYXrVnLhqpCVuKfV6wgsfC");   //USDD(18), USDT(6)
    SymbolAddressMap.put("3USD","TA1TVZdERDRDGi9QXNdLVfPxbymmi8xFyc"); //USDD,TUSD,USDT
    SymbolAddressMap.put("usdc3SUN","TQ4i5sdj1VGYGFcivyqFW9NXqzpaP6X8BA"); //USDD, 3SUN(USDT,TUSD,USDJ)
    SymbolAddressMap.put("USDD","TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");
    SymbolAddressMap.put("USDT","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
    SymbolAddressMap.put("TUSD","TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");
    SymbolAddressMap.put("USDC","TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");
    SymbolAddressMap.put("3SUN","TD3et9gS2pYz46ZC2mkCfYcKQGNwrnBLef");

    SymbolPoolMap.put("2USD","TAUGwRhmCP518Bm4VBqv7hDun9fg8kYjC4");
    SymbolPoolMap.put("3USD","TKVsYedAY23WFchBniU7kcx1ybJnmRSbGt");
    SymbolPoolMap.put("usdc3SUN","TQx6CdLHqjwVmJ45ecRzodKfVumAsdoRXH");

    CoinContainsToken.put("2USD","USDD,USDT");
    CoinContainsToken.put("3USD","USDD,TUSD,USDT");
    CoinContainsToken.put("usdc3SUN","USDC,3SUN");

    }
    //symbol：传要计算的2USDD/3USDD/USDC3SUN
    public String calculatePriceFromPool(String symbol){
        //query 2USD totalSupply
        String total_supply=PriceCenterApiList.getTotalSupplyOfOneCoin(symbol,SymbolAddressMap.get(symbol));
        //query token amount contained in pool
        String[] tokens_list =CoinContainsToken.get(symbol).split(",");
        java.util.List<String> subCoinsAmount = new ArrayList<String>();
        for (int i=0; i<tokens_list.length; i++) {
            String hex= Integer.toHexString(i);
            String contractParam = hex;
            while (contractParam.length() < 64){
                contractParam="0"+contractParam;

            }
            log.info("calculatePriceFromPool:contractParam:"+contractParam);
            String amount = PriceCenterApiList.getAmountOfSubCoin(tokens_list[i],SymbolPoolMap.get(symbol),contractParam);
            subCoinsAmount.add(amount);
        }
        log.info("calculatePriceFromPool:subCoinsAmount:"+subCoinsAmount.toString());

        java.util.List<String> subCoinsPrice = new ArrayList<String>();
        for (int i=0; i<tokens_list.length; i++) {
            subCoinsPrice.add(CentrePriceMap.get(tokens_list[i]));
        }
        log.info("calculatePriceFromPool:subCoinsPrice:" + subCoinsPrice.toString());

        //calculate total liquid
        BigDecimal liquid = new BigDecimal("0");
        for (int i=0; i<tokens_list.length;i++){
            BigDecimal price = new BigDecimal(subCoinsPrice.get(i));
            BigDecimal amount = new BigDecimal(subCoinsAmount.get(i));
            liquid = price.multiply(amount).add(liquid);
        }
        log.info("calculatePriceFromPool:liquid:"+liquid);
        BigDecimal calculatePrice = liquid.divide(new BigDecimal(total_supply),18,1);
        log.info("calculatePriceFromPool:calculatePrice:"+calculatePrice);
        return calculatePrice.toString();
    }


    @Test(enabled = true, description = "检验2USD, 3USD, USDC3SUN的价格中心价格与自己计算的价格差别不大")
    public void testNewPoolTokenPrice() {
        PriceCenterApiList.getprice(SymbolAddressMap, CentrePriceMap,"USD");

        log.info("testNewPoolTokenPrice:Begin to test 2USD...");
        String USD2_Price = calculatePriceFromPool("2USD");
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(CentrePriceMap.get("2USD"),USD2_Price));

        log.info("testNewPoolTokenPrice:Begin to test 3USD...");
        String USD3_Price = calculatePriceFromPool("3USD");
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(CentrePriceMap.get("3USD"),USD3_Price));

        log.info("testNewPoolTokenPrice:Begin to test USDC3SUN...");
        String USDC3SUN_Price = calculatePriceFromPool("usdc3SUN");
        Assert.assertTrue(PriceCenterApiList.CompareGapInTolerance(CentrePriceMap.get("usdc3SUN"),USDC3SUN_Price));

    }





}
