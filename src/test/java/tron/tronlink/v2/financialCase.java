package tron.tronlink.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.api.Http;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math.stat.descriptive.rank.Max;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.aspectj.org.eclipse.jdt.core.IJavaElement;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigInteger;

@Slf4j
public class financialCase extends TronlinkBase {
    private HttpResponse response;
    private HttpResponse tokenFinancialListResp;
    private JSONObject responseContent;
    private JSONObject tokenFinancialListRespContent;
    private JSONObject mytokenListContent;
    private JSONObject myprojectListContent;

    HashMap<String, String> params = new HashMap<>();
    HashMap<String, String> headers = new HashMap<>();
    JSONObject bodyObject = new JSONObject();
    List<String> walletAddressList = new ArrayList<>();
    List<String> trc20tokenList = new ArrayList<>();
    //public String MaxJTrxHolder="TPyjyZfsYaXStgz2NmAraF1uZcMtkgNan5";
    public String MaxJTrxHolder = "TLipJxwgDbn7FaQCnECxiYdxFTBhshLiW3"; //qianqian
    //public String MaxJTrxHolder = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";  //quince
    //public String MaxJTrxHolder = "TPyVMgQ9gCpnP6oyHpKY2kEYPDCXm4KY7y";  //ledger

    //public String MaxJTrxHolder = "TXFmkVZkpkv8ghNCKwpeVdVvRVqTedSCAK";
    public static HashMap<String, String> justlendTokens = new HashMap<>();
    public static HashMap<String, String> Tokens = new HashMap<>();
    private List<String> expTokenNames = java.util.Arrays.asList("TRX","USDD","USDT","SUN","BTT","NFT",
            "JST","WIN","USDJ","USDC","TUSD","BTC","ETH","WBTT");
    public static HashMap<String,String> jTokenTokenMap = new HashMap<>();

    static {
        justlendTokens.put("TRX","TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");
        justlendTokens.put("USDD","TX7kybeP6UwTBRHLNPYmswFESHfyjm9bAS");
        justlendTokens.put("USDT","TXJgMdjVX5dKiQaUi9QobwNxtSQaFqccvd");
        justlendTokens.put("SUN","TPXDpkg9e3eZzxqxAUyke9S4z4pGJBJw9e");
        justlendTokens.put("BTT","TUaUHU9Dy8x5yNi1pKnFYqHWojot61Jfto");
        justlendTokens.put("NFT","TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE");
        justlendTokens.put("JST","TWQhCXaWz4eHK4Kd1ErSDHjMFPoPc9czts");
        justlendTokens.put("WIN","TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv");
        justlendTokens.put("USDJ","TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA");
        justlendTokens.put("USDC","TNSBA6KvSvMoTqQcEgpVK7VhHT3z7wifxy");
        justlendTokens.put("TUSD","TSXv71Fy5XdL3Rh2QfBoUu3NAaM4sMif8R");
        justlendTokens.put("BTC","TLeEu311Cbw63BcmMHDgDLu7fnk9fqGcqT");
        justlendTokens.put("ETH","TR7BUFRQeq1w5jAZf1FKx85SHuX6PfMqsV");
        justlendTokens.put("WBTT","TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt");
        //justlendTokens.put("BUSD","TLHASseQymmpGQdfAyNjkMXFTJh8nzR2x2");
        Tokens.put("TRX","_");
        Tokens.put("USDD","TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");
        Tokens.put("USDT","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        Tokens.put("SUN","TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S");
        Tokens.put("BTT","TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");
        Tokens.put("NFT","TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq");
        Tokens.put("JST","TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9");
        Tokens.put("WIN","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
        Tokens.put("USDJ","TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT");
        Tokens.put("USDC","TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");
        Tokens.put("TUSD","TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");
        Tokens.put("BTC","TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");
        Tokens.put("ETH","THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");
        Tokens.put("WBTT","TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt");
        //Tokens.put("BUSD","TMz2SWatiAtZVVcH2ebpsbVtYwUPT9EdjH");

        jTokenTokenMap.put("TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP","_");  //trx
        jTokenTokenMap.put("TX7kybeP6UwTBRHLNPYmswFESHfyjm9bAS","TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");  //usdd
        jTokenTokenMap.put("TXJgMdjVX5dKiQaUi9QobwNxtSQaFqccvd","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");  //usdt
        jTokenTokenMap.put("TPXDpkg9e3eZzxqxAUyke9S4z4pGJBJw9e","TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S");  //sun
        jTokenTokenMap.put("TUaUHU9Dy8x5yNi1pKnFYqHWojot61Jfto","TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");  //btt
        jTokenTokenMap.put("TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE","TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq");  //nft
        jTokenTokenMap.put("TWQhCXaWz4eHK4Kd1ErSDHjMFPoPc9czts","TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9"); //jst
        jTokenTokenMap.put("TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7"); //win
        jTokenTokenMap.put("TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA","TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT"); //usdj
        jTokenTokenMap.put("TNSBA6KvSvMoTqQcEgpVK7VhHT3z7wifxy","TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");  //usdc
        jTokenTokenMap.put("TSXv71Fy5XdL3Rh2QfBoUu3NAaM4sMif8R","TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");  //tusd
        jTokenTokenMap.put("TLeEu311Cbw63BcmMHDgDLu7fnk9fqGcqT","TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");  //btc
        jTokenTokenMap.put("TR7BUFRQeq1w5jAZf1FKx85SHuX6PfMqsV","THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");  //eth
        jTokenTokenMap.put("TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt","TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt");   //wbtt
        //jTokenTokenMap.put("TLHASseQymmpGQdfAyNjkMXFTJh8nzR2x2","TMz2SWatiAtZVVcH2ebpsbVtYwUPT9EdjH");  //busd*/
    }

    //v4.2.1 new user(not even have transfer trx),with parameter version=v1, will return trx only.


    @BeforeClass(enabled = true,description = "request tokenFinancialList and save response ", groups={"P2"})
    public void requestTokenFinancialList() throws InterruptedException, URISyntaxException, IOException {
        walletAddressList.clear();
        walletAddressList.add(MaxJTrxHolder);  //max jTRX holder
        bodyObject.put("walletAddress",walletAddressList);

        response = TronlinkApiList.myFinancialTokenList(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        mytokenListContent = TronlinkApiList.parseResponse2JsonObject(response);

        response = TronlinkApiList.myFinancialProjectList(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        myprojectListContent = TronlinkApiList.parseResponse2JsonObject(response);

        bodyObject.put("sort",1);
        params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        response = TronlinkApiList.tokenFinancialList(bodyObject, params,null);
        //response = TronlinkApiList.tokenFinancialListNoSig(bodyObject, null,null);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        tokenFinancialListRespContent = TronlinkApiList.parseResponse2JsonObject(response);



    }

    @Test(enabled = true, description = "check math relationship", groups={"P2"})
    public void totalAssets01() {
        walletAddressList.clear();
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        walletAddressList.add("TAAsAVbdrDVFWd6fAb8y7ZbHj5uuroECeN");
        walletAddressList.add("TAaoSTy34xLPS41ZbMPPjZyZtWBfZk2J8M");
        log.info(walletAddressList.toString());
        bodyObject.put("walletAddress",walletAddressList);
        bodyObject.put("showUsers",true);
        response = TronlinkApiList.totalAssets(bodyObject, null,null);
        //response = TronlinkApiList.totalAssetsNoSig(bodyObject,null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        //check address
        Object addressTotal = JSONPath.eval(responseContent, "$.data.total.accountAddress[0]");
        Assert.assertEquals("",addressTotal);
        Object address_obj = JSONPath.eval(responseContent, "$..accountAddress");
        ArrayList addressList = (ArrayList) address_obj;
        Assert.assertTrue(addressList.contains("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"));
        Assert.assertTrue(addressList.contains("TAAsAVbdrDVFWd6fAb8y7ZbHj5uuroECeN"));
        Assert.assertTrue(addressList.contains("TAaoSTy34xLPS41ZbMPPjZyZtWBfZk2J8M"));

        //check totalAssetsUsd
        Object totalAssertsUsd_obj_all = JSONPath.eval(responseContent, "$..totalAssetsUsd");
        ArrayList totalAssertsUsdList = (ArrayList) totalAssertsUsd_obj_all;
        BigDecimal totalAssertsUsd = new BigDecimal(totalAssertsUsdList.get(0).toString());
        BigDecimal userAssertsUsd = new BigDecimal(totalAssertsUsdList.get(1).toString()).add(new BigDecimal(totalAssertsUsdList.get(2).toString())).add(new BigDecimal(totalAssertsUsdList.get(3).toString()));
        int flag = totalAssertsUsd.compareTo(userAssertsUsd);
        Assert.assertEquals(0,flag);

        //check financialIncomeUsd
        Object financialIncomeUsd_obj_all = JSONPath.eval(responseContent, "$..financialIncomeUsd");
        ArrayList financialIncomeUsdList = (ArrayList) financialIncomeUsd_obj_all;
        BigDecimal financialIncomeUsd = new BigDecimal(financialIncomeUsdList.get(0).toString());
        BigDecimal userfinancialIncomeUsd = new BigDecimal(financialIncomeUsdList.get(1).toString()).add(new BigDecimal(financialIncomeUsdList.get(2).toString())).add(new BigDecimal(financialIncomeUsdList.get(3).toString()));
        flag = financialIncomeUsd.compareTo(userfinancialIncomeUsd);
        Assert.assertEquals(0,flag);

        //check financialAssetUsd
        Object financialAssetsUsd_obj_all = JSONPath.eval(responseContent, "$..financialAssetsUsd");
        ArrayList financialAssetsUsdList = (ArrayList) financialAssetsUsd_obj_all;
        log.info("financialAssetsUsdList:"+financialAssetsUsdList.toString());
        BigDecimal financialAssetsUsd = new BigDecimal(financialAssetsUsdList.get(0).toString());
        BigDecimal userfinancialAssetsUsd = new BigDecimal(financialAssetsUsdList.get(1).toString()).add(new BigDecimal(financialAssetsUsdList.get(2).toString())).add(new BigDecimal(financialAssetsUsdList.get(3).toString()));
        //log.info("financialAssetsUsd:"+financialAssetsUsd.toString()+";userfinancialAssetsUsd:"+userfinancialAssetsUsd.toString());
        flag = financialAssetsUsd.compareTo(userfinancialAssetsUsd);
        Assert.assertEquals(0,flag);

        //check totalAssetsCny
        Object totalAssertsCny_obj_all = JSONPath.eval(responseContent, "$..totalAssetsCny");
        ArrayList totalAssertsCnyList = (ArrayList) totalAssertsCny_obj_all;
        BigDecimal totalAssertsCny = new BigDecimal(totalAssertsCnyList.get(0).toString());
        BigDecimal userAssertsCny = new BigDecimal(totalAssertsCnyList.get(1).toString()).add(new BigDecimal(totalAssertsCnyList.get(2).toString())).add(new BigDecimal(totalAssertsCnyList.get(3).toString()));
        flag = totalAssertsCny.compareTo(userAssertsCny);
        Assert.assertEquals(0,flag);

        //check financialPercent
        log.info("financialAssetsUsd:"+financialAssetsUsd.toString() +";totalAssertsUsd:"+totalAssertsUsd.toString());
        BigDecimal percentInTotal = financialAssetsUsd.divide(totalAssertsUsd,18, java.math.RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        Object actualPctInTotal = JSONPath.eval(responseContent, "$.data.total.financialPercent[0]");
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(percentInTotal, new BigDecimal(actualPctInTotal.toString()), "0.00001"));
    }

    @Test(enabled = true, description = "check token order", groups={"P2"})
    public void tokenFinancialList01() throws URISyntaxException, InterruptedException, IOException {
        Object tokens_obj = JSONPath.eval(tokenFinancialListRespContent,"$..tokenName");
        log.info(tokens_obj.toString());
        ArrayList tokenArray = (ArrayList) tokens_obj;
        Assert.assertEquals(expTokenNames.size(),tokenArray.size());
        for(int i=0; i<expTokenNames.size(); i++){
            Assert.assertEquals(expTokenNames.get(i),tokenArray.get(i));
        }
    }
    @Test(enabled = true, description = "check justlend token apy",groups={"P2"})
    public void checkJustlendAPY() throws URISyntaxException, InterruptedException, IOException {
        //get APY from justlend
        HttpResponse minResp = TronlinkApiList.getJustlendMiningAPY();
        JSONObject minContent = TronlinkApiList.parseResponse2JsonObject(minResp);
        HttpResponse depositeResp = TronlinkApiList.getJustlendDepositeAPY();
        JSONObject depositeContent = TronlinkApiList.parseResponse2JsonObject(depositeResp);

        Object myTokens_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[0].tokenList[*].tokenName");
        JSONArray myTokensArray = (JSONArray) myTokens_obj;

        for(Map.Entry<String, String> entry : justlendTokens.entrySet()) {
            String curToken = entry.getKey();
            log.info("curToken in justlend tokens: " + curToken);
            Object minapy = JSONPath.eval(minContent, "$.data." + entry.getValue() + ".USDD[0]");
            Object depositeapy = JSONPath.eval(depositeContent, "$..data.jtokenList[jtokenAddress='" + entry.getValue() + "'].depositedAPY[0]");
            BigDecimal minapy_bd = new BigDecimal(minapy.toString()).multiply(new BigDecimal("100"));
            BigDecimal depositeapy_bd = new BigDecimal(depositeapy.toString()).multiply(new BigDecimal("100"));
            BigDecimal justlendApy = minapy_bd.add(depositeapy_bd);
            log.info("APY compare:curToken:"+curToken + "; justlend apy:" + justlendApy.toString() + "; deposite apy:"+depositeapy.toString()+ "; mint apy:"+minapy.toString());

            Object tronlinkApy_obj = JSONPath.eval(tokenFinancialListRespContent, "$.data[tokenName='" + curToken + "'].projectList[0].apy");
            log.info("curToken:" + curToken + "; justlend apy:" + justlendApy+" tronlink server apy:" + tronlinkApy_obj.toString());
            BigDecimal tronlinkApy = new BigDecimal(tronlinkApy_obj.toString());
            Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendApy,tronlinkApy,"0.02"));


            if (myTokensArray.contains(curToken)){
                //check in justlend detail API
                params.clear();
                params.put("walletAddress", MaxJTrxHolder);
                params.put("tokenId",Tokens.get(curToken));
                response = TronlinkApiList.justLendDetail(params,null);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object apyInDetail_obj = JSONPath.eval(responseContent,"$.data.apy[0]");
                Object baseApyInDetail_obj = JSONPath.eval(responseContent,"$.data.baseApy[0]");
                Object mintApyInDetail_obj = JSONPath.eval(responseContent,"$.data.mintApy[0]");
                log.info("curToken:" + curToken + "; apyInDetail:"+apyInDetail_obj.toString()+"; baseApyInDetail:"+baseApyInDetail_obj.toString()+"; mintApyInDetail:"+mintApyInDetail_obj.toString());
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendApy, new BigDecimal(apyInDetail_obj.toString()),"0.00001"));
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(depositeapy_bd, new BigDecimal(baseApyInDetail_obj.toString()),"0.00001"));
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(minapy_bd, new BigDecimal(mintApyInDetail_obj.toString()),"0.00001"));

                //check in justlend operate API
                response = TronlinkApiList.justLendOperate(params,null);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object apyInOperate_obj = JSONPath.eval(responseContent,"$.data.apy[0]");
                Object baseApyInOperate_obj = JSONPath.eval(responseContent,"$.data.baseApy[0]");
                Object mintApyInOperate_obj = JSONPath.eval(responseContent,"$.data.mintApy[0]");
                log.info("curToken:"+curToken + "; apyInOperate:" + apyInOperate_obj.toString() + "; baseApyInOperate:"+baseApyInOperate_obj.toString()+"; mintApyInOperate:"+ mintApyInOperate_obj.toString());
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendApy, new BigDecimal(apyInOperate_obj.toString()),"0.00001"));
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(depositeapy_bd, new BigDecimal(baseApyInOperate_obj.toString()),"0.000001"));
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(minapy_bd, new BigDecimal(mintApyInOperate_obj.toString()),"0.000005"));

            }
        }
    }
    @Test(enabled = true, description = "check bttc apy",groups={"P2"})
    public void tokenFinancialList03() throws URISyntaxException, InterruptedException, IOException {
        //get APY from justlend
        HttpResponse bttcResp = TronlinkApiList.getBttcAPY();
        JSONObject bttcContent = TronlinkApiList.parseResponse2JsonObject(bttcResp);
        Object bttcapy_obj = JSONPath.eval(bttcContent, "$.data.avgApy[0]");
        BigDecimal bttcApy = new BigDecimal(bttcapy_obj.toString()).multiply(new BigDecimal("100"));
        Object tronlinkApy_obj = JSONPath.eval(tokenFinancialListRespContent, "$.data[tokenName='BTT'].projectList[1].apy");
        log.info("curToken: BTT; bttc APY:" + bttcApy.toString() +"; tronlink server apy:" + tronlinkApy_obj.toString());
        BigDecimal tronlinkApy = new BigDecimal(tronlinkApy_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(bttcApy,tronlinkApy,"0.01"));
    }
    @Test(enabled = true, description = "check trx vote apy",groups={"P2"})
    public void tokenFinancialList04() throws URISyntaxException, InterruptedException, IOException {
        //get APY from justlend
        HttpResponse scanResp = TronlinkApiList.getScanWitnessAPY();
        JSONObject scanContent = TronlinkApiList.parseResponse2JsonObject(scanResp);
        Object voteApy_obj = JSONPath.eval(scanContent, "$..annualizedRate");
        ArrayList scanApyList = (ArrayList) voteApy_obj;
        BigDecimal scanApy = BigDecimal.ZERO;
        for (Object voteApy : scanApyList) {
            final BigDecimal decimal = new BigDecimal(String.valueOf(voteApy));
            if (decimal.compareTo(scanApy) > 0) {
                scanApy = decimal;
            }
        }
        Object tronlinkApy_obj = JSONPath.eval(tokenFinancialListRespContent, "$.data[tokenName='TRX'].projectList[1].apy");
        log.info("curToken: BTT; bttc APY:" + scanApy.toString() +"; tronlink server apy:" + tronlinkApy_obj.toString());
        BigDecimal tronlinkApy = new BigDecimal(tronlinkApy_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(scanApy,tronlinkApy,"0.01"));
    }

    @Test(enabled = true, description = "check Available Balance In Assets, tokenlist, justlend detail",groups={"P2"})
    public void checkAvailableBalanceInFinancialAPIs() {
        walletAddressList.clear();
        walletAddressList.add(MaxJTrxHolder);
        bodyObject.clear();
        bodyObject.put("walletAddress",walletAddressList);
        for(Map.Entry<String, String> entry : Tokens.entrySet()) {
            String curToken = entry.getKey();
            log.info("checkBalanceInFinancialAPI: curToken:"+ curToken);
            //request v2/asset
            bodyObject.put("tokenId",entry.getValue());
            response = TronlinkApiList.v2assets(bodyObject, null,null);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            Object assetBalance_obj = JSONPath.eval(responseContent, "$.data[0].balance[0]");

            //balance in tokenlist
            Object tokenlistBalance_obj =  JSONPath.eval(tokenFinancialListRespContent, "$.data[tokenName='" + curToken + "'].balance[0]");

            //balance in justlendDetail
            params.clear();
            params.put("walletAddress",MaxJTrxHolder);
            params.put("tokenId",entry.getValue());
            response = TronlinkApiList.justLendDetail(params,null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            Object detailBalance_obj = JSONPath.eval(responseContent, "$.data.balance[0]");

            if(assetBalance_obj!=null && detailBalance_obj!=null) {
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(assetBalance_obj.toString(), detailBalance_obj.toString(), "0.01"));
            }
            if(assetBalance_obj!=null && tokenlistBalance_obj!=null) {
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(assetBalance_obj.toString(), tokenlistBalance_obj.toString(), "0.01"));
            }
        }
    }

    @Test(enabled = true, description = "check FinancialAssets InJustlend, v2Asset, myFinancialTokenList, myFinancialProjectList, justLendDetail,justLendOperate: financialAssetsValue",groups={"P2"})
    public void checkJustlendFinancialAssets() {
        walletAddressList.clear();
        walletAddressList.add(MaxJTrxHolder);
        bodyObject.clear();
        bodyObject.put("walletAddress", walletAddressList);

        //get assets in Justlend API
        HttpResponse depositRsp = TronlinkApiList.getJustlandUserDeposite(MaxJTrxHolder);
        JSONObject depositContent = TronlinkApiList.parseResponse2JsonObject(depositRsp);

        HttpResponse JstMiningResp = TronlinkApiList.getJustlendMiningReward(MaxJTrxHolder);
        JSONObject JstMiningContent = TronlinkApiList.parseResponse2JsonObject(JstMiningResp);

        for (Map.Entry<String, String> entry : justlendTokens.entrySet()) {
            String curToken = entry.getKey();
            log.info("checkFinancialAssetsInJustlend: curToken:" + curToken);
            Object jTokenBalance_obj = JSONPath.eval(depositContent, "$.data.assetList[jtokenAddress='" + entry.getValue() + "'].account_depositJtoken[0]");
            if(jTokenBalance_obj==null){
                continue;
            }
            HttpResponse jtokenDetailRsp = TronlinkApiList.getJTokenDetailInJustlend(entry.getValue());
            JSONObject jtokenDetailContent = TronlinkApiList.parseResponse2JsonObject(jtokenDetailRsp);
            Object oneToExchangeRate_obj = JSONPath.eval(jtokenDetailContent, "$.data.oneToExchangeRate");
            Object priceUSD_obj = JSONPath.eval(jtokenDetailContent, "$.data.priceUSD");
            Object collateralDecimal_obj = JSONPath.eval(jtokenDetailContent, "$.data.collateralDecimal");
            BigDecimal tokenDecimal = new BigDecimal("10").pow(Integer.valueOf(collateralDecimal_obj.toString()));
            BigDecimal justlendAssetUSD = new BigDecimal(jTokenBalance_obj.toString()).divide(new BigDecimal("100000000"), 18, java.math.RoundingMode.HALF_UP).divide(new BigDecimal(oneToExchangeRate_obj.toString()), 0, java.math.RoundingMode.HALF_UP).multiply(new BigDecimal(priceUSD_obj.toString()));
            BigDecimal justlendAsset = new BigDecimal(jTokenBalance_obj.toString()).divide(new BigDecimal("100000000"), 18, java.math.RoundingMode.HALF_UP).divide(new BigDecimal(oneToExchangeRate_obj.toString()), Integer.valueOf(collateralDecimal_obj.toString()), java.math.RoundingMode.HALF_UP).multiply(tokenDecimal);
            log.info("balance compare:curToken:" + curToken + "; justlendAsset:" + justlendAsset.toString());

            //if(jTokenBalance_obj !=null) {
            BigDecimal justlendIncome = new BigDecimal(0);
            if (curToken.equals("ETH") || curToken.equals("WBTT") || curToken.equals("BUSD")) {
                justlendIncome = new BigDecimal(0);
            } else {
                Object justlendIncome_obj = JSONPath.eval(JstMiningContent, "$.data." + justlendTokens.get(curToken) + ".USDD.currReward[0]");
                justlendIncome = new BigDecimal(justlendIncome_obj.toString()).multiply(new BigDecimal("1000000000000000000"));
            }
            if ((justlendIncome.compareTo(BigDecimal.ZERO) == 0 && justlendAsset.compareTo(BigDecimal.ZERO) == 0)) {
                continue;
            } else {

                {

                    //query in v2/asset api
                    bodyObject.put("tokenId", Tokens.get(curToken));
                    bodyObject.put("projectId", "534ed914-babc-4910-b6cf-0ebf4b59348b");
                    response = TronlinkApiList.v2assets(bodyObject, null, null);
                    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                    Object assetBalance_obj = JSONPath.eval(responseContent, "$.data[0].balance[0]");
                    log.info("balance compare:curToken:" + curToken + "; justlendAsset:" + justlendAsset.toString() + "; assetBalance:" + assetBalance_obj.toString());

                    //balance In myFinancialProjectlist
                    Object myFinancialTokenBalance_obj = JSONPath.eval(mytokenListContent, "$.data.tokenList[tokenName='" + curToken + "'].projectList[0].financialAssetsValue[0]");
                    log.info("balance compare:curToken:" + curToken + "; justlendAsset:" + justlendAsset.toString() + "; myFinancilToken financialAssetsValue:" + myFinancialTokenBalance_obj.toString());

                    //balance In myProjectList
                    Object myprojectBalance_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[0].tokenList[tokenName='" + curToken + "'].financialAssetsValue[0]");
                    log.info("balance compare:curToken:" + curToken + "; justlendAsset:" + justlendAsset.toString() + "; myFinancilToken financialAssetsValue:" + myprojectBalance_obj.toString());

                    params.clear();
                    params.put("walletAddress", MaxJTrxHolder);
                    params.put("tokenId", Tokens.get(curToken));
                    response = TronlinkApiList.justLendDetail(params, null);
                    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                    Object justDetailAsset_obj = JSONPath.eval(responseContent, "$.data.financialAssetsValue[0]");
                    log.info("balance compare:curToken:" + curToken + "; justlendAsset:" + justlendAsset.toString() + "; justlendDetail financialAssetsValue:" + justDetailAsset_obj.toString());

                    response = TronlinkApiList.justLendOperate(params, null);
                    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                    Object justOperateAsset_obj = JSONPath.eval(responseContent, "$.data.financialAssetsValue[0]");
                    log.info("balance compare:curToken:" + curToken + "; justlendAsset:" + justlendAsset.toString() + "; justlendOperate financialAssetsValue:" + justOperateAsset_obj.toString());

                    if (justlendAsset.equals(new BigDecimal("0"))) {
                        new BigDecimal(assetBalance_obj.toString()).equals(new BigDecimal("0"));
                        new BigDecimal(myFinancialTokenBalance_obj.toString()).equals(new BigDecimal("0"));
                        new BigDecimal(myprojectBalance_obj.toString()).equals(new BigDecimal("0"));
                        new BigDecimal(justDetailAsset_obj.toString()).equals(new BigDecimal("0"));
                        new BigDecimal(justOperateAsset_obj.toString()).equals(new BigDecimal("0"));

                    } else {
                        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendAsset, new BigDecimal(assetBalance_obj.toString()), "0.01"));
                        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendAsset, new BigDecimal(myFinancialTokenBalance_obj.toString()), "0.01"));
                        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendAsset, new BigDecimal(myprojectBalance_obj.toString()), "0.01"));
                        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendAsset, new BigDecimal(justDetailAsset_obj.toString()), "0.01"));
                        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendAsset, new BigDecimal(justOperateAsset_obj.toString()), "0.01"));

                    }
                }

            }

        }
    }

    @Test(enabled = true, description = "check trx financialAsset in froozen",groups={"P2"})
    public void checkTRXStakingBalance() {
        //get trx staking in tronlink-server AssetList api
        params.clear();
        params.put("address", MaxJTrxHolder);
        params.put("version", "v2");
        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object availableTrx_obj = JSONPath.eval(responseContent,"$.data.token[0].balance[0]");
        Object totalTrx_obj = JSONPath.eval(responseContent,"$.data.token[0].totalBalance[0]");
        BigDecimal stakingTrx = new BigDecimal(totalTrx_obj.toString()).subtract(new BigDecimal(availableTrx_obj.toString())).multiply(new BigDecimal("1000000"));

        //query in v2/asset api
        bodyObject.put("tokenId", Tokens.get("TRX"));
        bodyObject.put("projectId", "2f38665c-7c74-4e63-bbdf-c69d6a623892");
        response = TronlinkApiList.v2assets(bodyObject, null, null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object assetBalance_obj = JSONPath.eval(responseContent, "$.data[0].balance[0]");
        log.info("Staking TRX Compare:" + "assetList API:" + stakingTrx.toString() + "; assetBalance:" + assetBalance_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(stakingTrx,new BigDecimal(assetBalance_obj.toString()),"0.01"));

        walletAddressList.clear();
        walletAddressList.add(MaxJTrxHolder);
        bodyObject.clear();
        bodyObject.put("walletAddress",walletAddressList);

        //balance In myFinancialProjectlist
        Object myFinancialTokenBalance_obj = JSONPath.eval(mytokenListContent, "$.data.tokenList[tokenName='TRX'].projectList[1].financialAssetsValue[0]");
        log.info("Staking TRX Compare:" + "assetList API:" + stakingTrx.toString() + "; myFinancilTokenlist financialAssetsValue:"+myFinancialTokenBalance_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(stakingTrx,new BigDecimal(myFinancialTokenBalance_obj.toString()),"0.01"));


        Object myprojectBalance_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='2f38665c-7c74-4e63-bbdf-c69d6a623892'].tokenList[0].financialAssetsValue[0]");
        log.info("Staking TRX Compare:" + "assetList API:" + stakingTrx.toString() + "; myPorjectlist financialAssetsValue:"+myprojectBalance_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(stakingTrx,new BigDecimal(myprojectBalance_obj.toString()),"0.01"));

    }

    @Test(enabled = true, description = "check btt financialAsset in bttc project",groups={"P2"})
    public void checkBTTStakingBalance() {
        response = TronlinkApiList.getBttInBttcProject(MaxJTrxHolder);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object bttcStake_obj = JSONPath.eval(responseContent,"$.data.accountInfoView.ownerStackCount");
        BigDecimal bttcStake = new BigDecimal(bttcStake_obj.toString());

        //query in v2/asset api
        bodyObject.put("tokenId", Tokens.get("BTT"));
        bodyObject.put("projectId", "6b37b8ff-59d1-4086-8645-cef782d217c3");
        response = TronlinkApiList.v2assets(bodyObject, null, null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object assetBalance_obj = JSONPath.eval(responseContent, "$.data[0].balance[0]");
        log.info("Staking BTT Compare: Bttc Asset:" + bttcStake.toString() + "; assetBalance:" + assetBalance_obj.toString());

        //balance In myFinancialProjectlist
        log.info("mytokenListContent:"+mytokenListContent.toString());
        Object myFinancialTokenBalance_obj = JSONPath.eval(mytokenListContent, "$.data.tokenList[tokenName='BTT'].projectList[projectId='6b37b8ff-59d1-4086-8645-cef782d217c3'].financialAssetsValue[0]");
        log.info("Staking BTT Compare: Bttc Asset:" + bttcStake.toString() + "; myFinancilToken financialAssetsValue:"+myFinancialTokenBalance_obj.toString());

        //balance In myProjectList
        Object myprojectBalance_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='6b37b8ff-59d1-4086-8645-cef782d217c3'].tokenList[0].financialAssetsValue[0]");
        log.info("Staking BTT Compare: Bttc Asset:" + bttcStake.toString() + "; myFinancilToken financialAssetsValue:"+myprojectBalance_obj.toString());

        Assert.assertTrue(bttcStake.equals(new BigDecimal(assetBalance_obj.toString())));
        Assert.assertTrue(bttcStake.equals(new BigDecimal(myFinancialTokenBalance_obj.toString())));
        Assert.assertTrue(bttcStake.equals(new BigDecimal(myprojectBalance_obj.toString())));
    }

    @Test(enabled = false, description = "当用户没有往期收益时适用，即仅检查tronlink-server接口为当期收益时适用。保留用例用来手工测试",groups={"P2"})
    public void checkUnsettleJustlendIncome() {

        //justlend deposit api 包括所有市场。
        //justlend mining api 包括开启了usdd抵押的市场

        HttpResponse JstMiningResp = TronlinkApiList.getJustlendMiningReward(MaxJTrxHolder);
        JSONObject JstMiningContent = TronlinkApiList.parseResponse2JsonObject(JstMiningResp);
        JSONObject incomeTokens_data = (JSONObject) JstMiningContent.get("data");

        HttpResponse depositRsp = TronlinkApiList.getJustlandUserDeposite(MaxJTrxHolder);
        JSONObject depositContent = TronlinkApiList.parseResponse2JsonObject(depositRsp);

        log.info("wqq debug0:"+mytokenListContent.toString());
        log.info("wqq debug1:"+myprojectListContent.toString());

        for(Map.Entry<String, String> entry : justlendTokens.entrySet()) {
            String curToken = entry.getKey();
            log.info("checkJustlendIncome: curToken:"+curToken);
            BigDecimal justlendIncome = new BigDecimal(0);
            //下面三个token，因为没有开启挖矿，usdd收益直接为0。
            if (curToken.equals("ETH") || curToken.equals("WBTT") || curToken.equals("BUSD")) {
                justlendIncome = new BigDecimal(0);
            }else{

                Object justlendIncome_obj = JSONPath.eval(JstMiningContent,"$.data."+justlendTokens.get(curToken)+".USDD.currReward[0]");
                log.info(justlendIncome_obj.toString());
                justlendIncome = new BigDecimal(justlendIncome_obj.toString()).multiply(new BigDecimal("1000000000000000000"));
            }

            Object justlendasset_obj = JSONPath.eval(depositContent,"$.data.assetList[jtokenAddress='"+entry.getValue()+"'].account_depositJtoken[0]");
            if (justlendasset_obj == null){
                continue;
            }
            BigDecimal justlendasset = new BigDecimal(justlendasset_obj.toString()).multiply(new BigDecimal("1000000000000000000"));
            log.info("checkJustlendIncome: justlendasset:"+justlendasset.toString()+"; justlendIncome:"+justlendIncome);

            //已存和收益为0。
            //if ((justlendIncome.compareTo(BigDecimal.ZERO)==0 && justlendasset.compareTo(BigDecimal.ZERO)==0) || !incomeTokens_data.containsKey(justlendTokens.get(curToken))){
            if ((justlendIncome.compareTo(BigDecimal.ZERO)==0 && justlendasset.compareTo(BigDecimal.ZERO)==0)){
                continue;
            }else{

                Object myFinancialTokenIncome_obj = JSONPath.eval(mytokenListContent, "$.data.tokenList[tokenName='" + curToken + "'].projectList[0].financialIncomeValue[0]");
                log.info("Justlend Income Compare: justlend:" + justlendIncome + "; myFinancilToken financialAssetsValue:" + myFinancialTokenIncome_obj.toString());

                //income In myProjectList
                Object myprojectIncome_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='534ed914-babc-4910-b6cf-0ebf4b59348b'].tokenList[tokenName='" + curToken + "'].financialIncomeValue[0]");
                log.info("Justlend Income Compare: justlend:" + justlendIncome + "; myFinancilToken financialAssetsValue:" + myprojectIncome_obj.toString());

                params.clear();
                params.put("walletAddress", MaxJTrxHolder);
                params.put("tokenId", Tokens.get(curToken));
                response = TronlinkApiList.justLendDetail(params, null);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object justDetailAsset_obj = JSONPath.eval(responseContent, "$.data.financialIncomeValue[0]");
                log.info("Justlend Income Compare: justlend:" + curToken + "; justlendAsset:" + justlendIncome.toString() + "; justlendDetail financialAssetsValue:" + justDetailAsset_obj.toString());

                if (justlendIncome.equals(new BigDecimal("0"))) {
                    new BigDecimal(myFinancialTokenIncome_obj.toString()).equals(new BigDecimal("0"));
                    new BigDecimal(myprojectIncome_obj.toString()).equals(new BigDecimal("0"));
                    new BigDecimal(justDetailAsset_obj.toString()).equals(new BigDecimal("0"));

                } else {
                    Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendIncome, new BigDecimal(myFinancialTokenIncome_obj.toString()), "0.05"));
                    Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendIncome, new BigDecimal(myprojectIncome_obj.toString()), "0.05"));
                    Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(justlendIncome, new BigDecimal(justDetailAsset_obj.toString()), "0.05"));
                }
            }
        }
    }

    //关闭用例是因为每个justlend token的往期收益对每个币是不可查的。trolnink-server对每个币的收益是自己累加的总收益。此用例适用于仅有当前期和上一期的情况。
    @Test(enabled = false,groups={"P2"})
    public void checkAllJustlendIncome(){
        //String testuser = "TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr";
        List<String> users = new ArrayList<>();
        users.add("TPyjyZfsYaXStgz2NmAraF1uZcMtkgNan5");  //btc, usdc,usdj, usdt, usdd, trx, btt,tusd
        users.add("TTcnrDx8vtqvDZpr2ExCoS9LUqotB3cZAD"); //sun
        users.add("TLipJxwgDbn7FaQCnECxiYdxFTBhshLiW3");


        for (String curUser:users) {
            HttpResponse depositeResp = TronlinkApiList.getJustlandUserDeposite(curUser);
            JSONObject depositeContent = TronlinkApiList.parseResponse2JsonObject(depositeResp);
            Object depositToken_obj = JSONPath.eval(depositeContent, "$..jtokenAddress");
            java.util.ArrayList depositTokens = (java.util.ArrayList) depositToken_obj;

            walletAddressList.clear();
            walletAddressList.add(curUser);   //wqq2
            bodyObject.put("walletAddress", walletAddressList);

            response = TronlinkApiList.myFinancialTokenList(bodyObject, null, null);
            JSONObject myFTokenContent = TronlinkApiList.parseResponse2JsonObject(response);

            response = TronlinkApiList.myFinancialProjectList(bodyObject, null, null);
            JSONObject myprojectListContent = TronlinkApiList.parseResponse2JsonObject(response);

            HttpResponse minRewardResp = TronlinkApiList.getJustlendMiningReward(curUser);
            JSONObject minRewardContent = TronlinkApiList.parseResponse2JsonObject(minRewardResp);

            ///sunProject/getAllUnClaimedAirDrop此接口在tronlink-server仅用户判断用户是否领取往期收益。接口中的均为未领取。
            for (Object jToken : depositTokens) {

                String curToken = jTokenTokenMap.get(jToken.toString());
                log.info("cur User: "+ curUser +"; checkAllJustlendIncome:" + jToken.toString());

                Object account_depositJtoken_obj = JSONPath.eval(depositeContent, "$.data.assetList[jtokenAddress='"+jToken.toString()+"'].account_depositJtoken[0]");

                //jsunold: TQ7JUeFHWAxNru1Yp8YjPP3c7guZSe4e2E 收益为0的和jsunold不参与比较，单独测试
                if (account_depositJtoken_obj.toString().equals("0") || jToken.toString().equals("TGBr8uh9jBVHJhhkwSJvQN2ZAKzVkxDmno")){
                    continue;
                }
                BigDecimal justlendIncome;
                BigDecimal curReward;
                BigDecimal lastReward;
                //未开启挖矿收益的几个币
                //nile: if (jToken.toString().equals("TYVr8QECrDkf6EAiKehok5FF3ckWV5Ds7k") || jToken.toString().equals("TAj5XxJtkrEDvTT7mTsS3uqMcvSCp82cnR") || jToken.toString().equals("TTNcbZWxaeUSq81HJ4uY1SpyVsKykUX97W") || jToken.toString().equals("TQ7JUeFHWAxNru1Yp8YjPP3c7guZSe4e2E")) {
                if (jToken.toString().equals("TR7BUFRQeq1w5jAZf1FKx85SHuX6PfMqsV") || jToken.toString().equals("TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt") || jToken.toString().equals("TLHASseQymmpGQdfAyNjkMXFTJh8nzR2x2") || jToken.toString().equals("TGBr8uh9jBVHJhhkwSJvQN2ZAKzVkxDmno")) {

                    justlendIncome = new BigDecimal(0);
                    curReward = new BigDecimal(0);
                    lastReward = new BigDecimal(0);
                }else {

                    Object curReward_obj = JSONPath.eval(minRewardContent, "$.data." + jToken.toString() + ".USDD.currReward[0]");
                    Object lastReward_obj = JSONPath.eval(minRewardContent, "$.data." + jToken.toString() + ".USDD.lastReward[0]");
                    curReward = new BigDecimal(curReward_obj.toString());
                    lastReward = new BigDecimal(lastReward_obj.toString());
                    justlendIncome = new BigDecimal(curReward_obj.toString()).add(new BigDecimal(lastReward_obj.toString()));
                }
                Object myFinancialTokenIncome_obj = JSONPath.eval(myFTokenContent, "$.data.tokenList[tokenId='" + curToken + "'].projectList[0].financialIncomeValue[0]");
                //log.info("Justlend Income Compare: justlend:" + justlendIncome + "cur:" + curReward.toString() + "last:"+ lastReward.toString() + "; myFinancilIncome:" + myFinancialTokenIncome_obj.toString());
                BigDecimal myFinancialTokenIncome = new BigDecimal(myFinancialTokenIncome_obj.toString()).divide(new BigDecimal("1000000000000000000"), 18, java.math.RoundingMode.HALF_UP);

                //income In myProjectList
                Object myprojectIncome_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='534ed914-babc-4910-b6cf-0ebf4b59348b'].tokenList[tokenId='" + curToken + "'].financialIncomeValue[0]");
                //log.info("Justlend Income Compare: justlend:" + justlendIncome +  "cur:" + curReward.toString() + "last:"+ lastReward.toString() + "; myProjectIncome:" + myprojectIncome_obj.toString());
                BigDecimal myprojectIncome = new BigDecimal(myprojectIncome_obj.toString()).divide(new BigDecimal("1000000000000000000"), 18, java.math.RoundingMode.HALF_UP);

                //justlend detail is curReward
                params.clear();
                params.put("walletAddress",curUser);
                params.put("tokenId",curToken);
                response = TronlinkApiList.justLendDetail(params,null);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object detailIncome_obj = JSONPath.eval(responseContent, "$.data.financialIncomeValue[0]");
                //log.info("Justlend Income Compare: justlend:" + justlendIncome +  "cur:" + curReward.toString() + "last:"+ lastReward.toString() + "; Justlenddetail:" + detailIncome.toString());
                BigDecimal detailIncome = new BigDecimal(detailIncome_obj.toString()).divide(new BigDecimal("1000000000000000000"), 18, java.math.RoundingMode.HALF_UP);

                BigDecimal curGap = curReward.subtract(detailIncome);
                BigDecimal totalGap = justlendIncome.subtract(myFinancialTokenIncome);

                log.info("Justlend Income Compare: cur User: "+ curUser +"; getAllUnClaimedAirDrop:" + jToken.toString());
                log.info("Justlend Income Compare: justlend:" + justlendIncome + "; cur:" + curReward.toString() + " last: "+ lastReward.toString() + "; myFinancilIncome:" + myFinancialTokenIncome.toString());
                log.info("Justlend Income Compare: justlend:" + justlendIncome +  "; cur:" + curReward.toString() + " last: "+ lastReward.toString() + "; myProjectIncome:" + myprojectIncome.toString());
                log.info("Justlend Income Compare: justlend:" + justlendIncome +  "; cur:" + curReward.toString() + " last: "+ lastReward.toString() + "; Justlenddetail:" + detailIncome.toString());
                log.info("Justlend Income Compare: check gap Asset vs cur:" + curGap.toString());
                log.info("Justlend Income Compare: check gap justlend total vs myfToken :" + totalGap.toString());
                //我的理财按token排序和我的理财按项目排序，每个币的收益是相同的。
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(myFinancialTokenIncome.toString(), myprojectIncome.toString(), "0.000001"));

                //我的理财按token排序，给出的某个币的待结算收益（变化值）=某个币收益（变化值）-待领取收益（固定值）。
                //计算得出的待结算与justlend接口查到的curReward的差距，应该等于某个币的所有收益与justlend所有收益的差距。也就是一个变化值之间的差别等于另一个变化值差别。
                BigDecimal calculate_tokenCurReward = myFinancialTokenIncome.subtract(lastReward);
                BigDecimal gap_curReward = curReward.subtract(calculate_tokenCurReward);
                log.info("Justlend Income Compare: check gap myTokenList curReward calculated by self VS justlend curReward:"+gap_curReward.toString());
                Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(totalGap,gap_curReward,"0.0001"));
            }
        }
    }

    @Test(enabled = true,description = "检查justlend整个项目的收益，不区分token", groups={"P2"})
    public void checkJustlendProjectIncome(){
        List<String> users = new ArrayList<>();
        users.add("TPyjyZfsYaXStgz2NmAraF1uZcMtkgNan5");  //btc, usdc,usdj, usdt, usdd, trx, btt,tusd
        users.add("TLipJxwgDbn7FaQCnECxiYdxFTBhshLiW3");

        for (String curUser:users) {
            //从justlend接口计算所有收益：airdrop接口是往期收益，minReward接口是当前收益。
            response = TronlinkApiList.getJustlendAllUnClaimedAirDrop(curUser);
            JSONObject responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            Object amount_obj = JSONPath.eval(responseContent,"..amount");
            java.util.ArrayList amount_list = (java.util.ArrayList)amount_obj;
            BigInteger justlend_preIncome = BigInteger.ZERO;
            for(int i=0; i<amount_list.size();i++){
                String value16 = (String)amount_list.get(i);
                BigInteger amount = new BigInteger(value16.substring(2), 16);
                justlend_preIncome = justlend_preIncome.add(amount);
            }
            log.info("justlend_preIncome:"+justlend_preIncome.toString());
            //计算当期收益
            HttpResponse minRewardResp = TronlinkApiList.getJustlendMiningReward(curUser);
            JSONObject minRewardContent = TronlinkApiList.parseResponse2JsonObject(minRewardResp);
            Object curReward_obj = JSONPath.eval(minRewardContent,"$..currReward");
            java.util.ArrayList curRewardList = (java.util.ArrayList)curReward_obj;
            BigDecimal justlend_curReward_bd = BigDecimal.ZERO;
            for(int i=0; i<curRewardList.size();i++){
                justlend_curReward_bd = justlend_curReward_bd.add(new BigDecimal((String)curRewardList.get(i)));
            }
            log.info("justlend_curReward_bd:"+justlend_curReward_bd.toString());
            BigInteger justlend_curReward = justlend_curReward_bd.multiply(new BigDecimal("1000000000000000000")).toBigInteger();
            BigInteger justlend_reward = justlend_preIncome.add(justlend_curReward);
            log.info("justlend_reward:"+justlend_reward.toString());

            //计算tokenlist中justlend项目的所有收益
            walletAddressList.clear();
            walletAddressList.add(curUser);   //wqq2
            bodyObject.put("walletAddress", walletAddressList);

            response = TronlinkApiList.myFinancialTokenList(bodyObject, null, null);
            JSONObject myFTokenContent = TronlinkApiList.parseResponse2JsonObject(response);
            JSONObject dataContent = myFTokenContent.getJSONObject("data");
            JSONArray tokenlistContent = dataContent.getJSONArray("tokenList");
            BigInteger tronlink_TListIncome = BigInteger.ZERO;
            for (Object curObject:tokenlistContent){
                JSONObject curJsonObject = (JSONObject) curObject;
                Object incomeValue = JSONPath.eval(curJsonObject,"$.projectList[projectId='534ed914-babc-4910-b6cf-0ebf4b59348b'].financialIncomeValue[0]");
                log.info("incomeValue:"+incomeValue.toString());
                tronlink_TListIncome = tronlink_TListIncome.add(new BigInteger(incomeValue.toString()));
            }
            log.info("tronlink_TListIncome:"+tronlink_TListIncome.toString());

            //计算projectlsit中justlend项目的所有收益
            response = TronlinkApiList.myFinancialProjectList(bodyObject, null, null);
            JSONObject myprojectListContent = TronlinkApiList.parseResponse2JsonObject(response);
            dataContent = myprojectListContent.getJSONObject("data");
            JSONArray myProjects = dataContent.getJSONArray("projectList");
            JSONObject justlendProject = myProjects.getJSONObject(0);
            Object justlendIncomeValues = JSONPath.eval(justlendProject,"$..financialIncomeValue");
            log.info("justlendIncomeValues:"+justlendIncomeValues.toString());
            java.util.ArrayList justlendIncomeValueL = (java.util.ArrayList)justlendIncomeValues;
            BigInteger tronlink_ProjectIncome = BigInteger.ZERO;
            for(int i=0; i<justlendIncomeValueL.size();i++){
                tronlink_ProjectIncome = tronlink_ProjectIncome.add(new BigInteger((String) justlendIncomeValueL.get(i)));
            }


            log.info("justlend_reward:"+justlend_reward.toString());
            log.info("tronlink_TListIncome:"+tronlink_TListIncome.toString());
            log.info("tronlink_ProjectIncome:"+tronlink_ProjectIncome.toString());
            Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(justlend_reward.toString(),tronlink_ProjectIncome.toString(),"0.01"));
            Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(tronlink_TListIncome.toString(),tronlink_ProjectIncome.toString(),"0"));

        }
    }



    @Test(enabled = true, description = "check Trx financialIncome in trx staking project",groups={"P2"})
    public void checkTrxStakingIncome() {
        response = TronlinkApiList.getFullNodeTrxReward(MaxJTrxHolder);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object fullnodeIncome_obj = JSONPath.eval(responseContent, "$.reward[0]");

        //income In myFinancialProjectlist
        Object myFinancialTokenIncome_obj = JSONPath.eval(mytokenListContent, "$.data.tokenList[tokenName='TRX'].projectList[1].financialIncomeValue[0]");
        log.info("Staking TRX Compare: fullNode:" + fullnodeIncome_obj.toString() + "; myFinancilToken financialAssetsValue:"+myFinancialTokenIncome_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(new BigDecimal(fullnodeIncome_obj.toString()),new BigDecimal(myFinancialTokenIncome_obj.toString()),"0.01"));

        //income In myProjectList
        Object myprojectIncome_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='2f38665c-7c74-4e63-bbdf-c69d6a623892'].tokenList[0].financialIncomeValue[0]");
        log.info("Staking TRX Compare: fullNode:" + fullnodeIncome_obj.toString() + "; myFinancilToken financialAssetsValue:"+myprojectIncome_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(new BigDecimal(fullnodeIncome_obj.toString()),new BigDecimal(myprojectIncome_obj.toString()),"0.01"));

    }

    @Test(enabled = true, description = "check btt financialIncome in bttc project",groups={"P2"})
    public void checkBTTStakingIncome() {
        response = TronlinkApiList.getBttInBttcProject(MaxJTrxHolder);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object bttcIncome_obj = JSONPath.eval(responseContent,"$.data.accountInfoView.awaitRewardCount");
        BigDecimal bttcIncome = new BigDecimal(bttcIncome_obj.toString());

        //income In myFinancialProjectlist
        Object myFinancialTokenIncome_obj = JSONPath.eval(mytokenListContent, "$.data.tokenList[tokenName='BTT'].projectList[projectId='6b37b8ff-59d1-4086-8645-cef782d217c3'].financialIncomeValue[0]");
        log.info("Staking BTT Compare: Bttc Asset:" + bttcIncome.toString() + "; myFinancilToken financialAssetsValue:"+myFinancialTokenIncome_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(bttcIncome,new BigDecimal(myFinancialTokenIncome_obj.toString()),"0.01"));

        //income In myProjectList
        Object myprojectIncome_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='6b37b8ff-59d1-4086-8645-cef782d217c3'].tokenList[0].financialIncomeValue[0]");
        log.info("Staking BTT Compare: Bttc Asset:" + bttcIncome.toString() + "; myFinancilToken financialAssetsValue:"+myprojectIncome_obj.toString());
        Assert.assertTrue(TronlinkApiList.CompareGapInGivenToleranceInDecimalFormat(bttcIncome,new BigDecimal(myprojectIncome_obj.toString()),"0.01"));
    }

    @Test(enabled = true, description = "check available balance in tokenFinancialList,asset,justLendDetail, justLendOperate",groups={"P2"})
    public void checkAvilableBalance() {
        walletAddressList.clear();
        walletAddressList.add(MaxJTrxHolder);
        bodyObject.clear();

        params.clear();
        params.put("address",MaxJTrxHolder);
        params.put("version", "v2");
        response = TronlinkApiList.V2AllAssetList(params);
        Object allAssetContent = TronlinkApiList.parseResponse2JsonObject(response);

        for(Map.Entry<String, String> entry : Tokens.entrySet()) {
            String curToken = entry.getKey();
            log.info("checkAvilableBalance: curToken:"+curToken);

            //get avilable in allAsset api.
            Object balanceInallAsset_obj = new Object();
            BigDecimal decimalPow = BigDecimal.ZERO;
            int precisionInallAsset = 0;

            if (curToken.equals("TRX")) {
                balanceInallAsset_obj = JSONPath.eval(allAssetContent, "$.data.token[0].balance[0]");
                precisionInallAsset = 6;
            }else{
                balanceInallAsset_obj = JSONPath.eval(allAssetContent, "$.data.token[shortName='"+curToken+"'].balance[0]");
                if (balanceInallAsset_obj == null) {
                    continue;
                }
                Object decimal_obj = JSONPath.eval(allAssetContent, "$.data.token[shortName='"+curToken+"'].precision[0]");
                precisionInallAsset = Integer.valueOf(decimal_obj.toString());
            }
            decimalPow = new BigDecimal("10").pow(precisionInallAsset);
            BigDecimal balanceInallAsset = new BigDecimal(balanceInallAsset_obj.toString()).multiply(decimalPow);
            log.info("checkAvilableBalance:"+curToken+"; precision in allAsset:"+precisionInallAsset+"; balance in allAsset:"+balanceInallAsset.toString());

            //in financial token list
            Object balanceInFT_obj = JSONPath.eval(tokenFinancialListRespContent, "$.data[tokenName='"+curToken+"'].balance[0]");
            Object precisionInFT_obj = JSONPath.eval(tokenFinancialListRespContent, "$.data[tokenName='"+curToken+"'].precision[0]");
            log.info("checkAvilableBalance:"+curToken+"; precision in financail list:"+precisionInFT_obj.toString()+"; balance in financail list:"+balanceInFT_obj.toString());

            Assert.assertEquals(0,balanceInallAsset.compareTo(new BigDecimal(balanceInFT_obj.toString())));
            Assert.assertEquals(Integer.valueOf(precisionInallAsset),Integer.valueOf(precisionInFT_obj.toString()));

            Object myFTokens_obj = JSONPath.eval(mytokenListContent,"$..tokenName");
            java.util.ArrayList myFTokens = (java.util.ArrayList) myFTokens_obj;
            if(myFTokens.contains(curToken)){
                bodyObject.put("walletAddress",walletAddressList);
                bodyObject.put("tokenId", Tokens.get(curToken));
                response = TronlinkApiList.v2assets(bodyObject, null, null);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object balanceInAsset_obj = JSONPath.eval(responseContent, "$.data[0].balance[0]");
                Object precisionInAsset_obj = JSONPath.eval(responseContent, "$.data[0].precision[0]");
                log.info("checkAvilableBalance:"+curToken+"; precision in asset:"+precisionInAsset_obj.toString()+"; balance in asset:"+balanceInAsset_obj.toString());
                Assert.assertEquals(0,balanceInallAsset.compareTo(new BigDecimal(balanceInAsset_obj.toString())));
                Assert.assertEquals(Integer.valueOf(precisionInallAsset),Integer.valueOf(precisionInAsset_obj.toString()));

                params.clear();
                params.put("walletAddress", MaxJTrxHolder);
                params.put("tokenId",Tokens.get(curToken));
                response = TronlinkApiList.justLendDetail(params,null);
                Assert.assertEquals(200, response.getStatusLine().getStatusCode());
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object balanceInJstDetail_obj = JSONPath.eval(responseContent,"$.data.balance[0]");
                Object precisionInJstDetail_obj = JSONPath.eval(responseContent,"$.data.precision[0]");
                log.info("checkAvilableBalance:"+curToken+"; precision in jst detail:"+precisionInJstDetail_obj.toString()+"; balance in jst detail:"+balanceInJstDetail_obj.toString());
                Assert.assertEquals(0,balanceInallAsset.compareTo(new BigDecimal(balanceInJstDetail_obj.toString())));
                Assert.assertEquals(Integer.valueOf(precisionInallAsset),Integer.valueOf(precisionInJstDetail_obj.toString()));

                response = TronlinkApiList.justLendOperate(params,null);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object balanceInJstOperate_obj = JSONPath.eval(responseContent,"$.data.balance[0]");
                Object precisionInJstOperate_obj = JSONPath.eval(responseContent,"$.data.precision[0]");
                log.info("checkAvilableBalance:"+curToken+"; precision in jst detail:"+precisionInJstOperate_obj.toString()+"; balance in jst detail:"+balanceInJstOperate_obj.toString());
                Assert.assertEquals(0,balanceInallAsset.compareTo(new BigDecimal(balanceInJstOperate_obj.toString())));
                Assert.assertEquals(Integer.valueOf(precisionInallAsset),Integer.valueOf(precisionInJstOperate_obj.toString()));

            }

        }


    }





}
