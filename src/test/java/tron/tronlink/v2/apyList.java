package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tools.DAO.FProjectTokenInfoDao;
import tron.tronlink.base.TronlinkBase;

import java.math.BigDecimal;
import java.util.*;

import tron.common.utils.TronlinkServerHttpClient;

import static tron.common.utils.TronlinkServerHttpClient.createGetConnect;

@Slf4j
public class apyList extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject object;
    private JSONObject dataContent;
    public static HttpResponse response;
    private JSONArray array = new JSONArray();
    public static String HttpNode = TronlinkBase.tronlinkUrl;
    public static Map<String, String> header = new HashMap<>();
    Map<String, String> params = new HashMap<>();
    Map<String, String> paramsOfJustLend = new HashMap<>();

    @SneakyThrows
    @Test(enabled = true)
    public void apyList() {

        ArrayList<String> listOfAddress = new ArrayList<String>();
        ArrayList<String> listOfjAddress = new ArrayList<String>();

        //予发布
        //String requestUrlOfJustLend = "https://labc.ablesdxd.link" + "/justlend/markets";
        //test环境
        String requestUrlOfJustLend = "http://47.252.29.162:10091" + "/justlend/markets";
        response = createGetConnect(requestUrlOfJustLend, null, null, null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        log.info("responseContentOfJustLend:" + responseContent);

        JSONArray jSONArray = responseContent.getJSONObject("data").getJSONArray("jtokenList");
        for (int i = 0; i < jSONArray.size(); i++) {
            String jtokenAddress = jSONArray.getJSONObject(i).getString("jtokenAddress");
            listOfjAddress.add(jtokenAddress);
            String collateralAddress = jSONArray.getJSONObject(i).getString("collateralAddress");
            listOfAddress.add(collateralAddress);
        }


        boolean flag = true;
        for (int i = 0; i < listOfAddress.size(); i++) {
            //测试环境
            // String requestUrl = "http://123.56.3.74" + "/api/financial/apyList";
            //nile美国环境
            // String requestUrl = "https://niletest.tronlink.org/" + "/api/financial/apyList";
            //nile新加坡环境
            String requestUrl = "http://54.151.250.227:80" + "/api/financial/apyList";
            //pre美国环境
            // String requestUrl = "https://testpre.tronlink.org" + "/api/financial/apyList";


            //  params.put("signature", "x8N9g9wShp3%3DM4un6rQscf1jg28o%3D");
            params.put("signature", "x8N9g9wShp3=M4un6rQscf1jg28o=");

            params.put("projectId", "534ed914-babc-4910-b6cf-0ebf4b59348b");
            params.put("tokenId", listOfAddress.get(i));
            log.info("requestUrl:" + requestUrl);
            response = createGetConnect(requestUrl, params, null, null);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            log.info("responseContent:" + responseContent);
            JSONArray resultFromTronlink = responseContent.getJSONArray("data");
            //justlend测试环境接口
            String requestUrlOfJustLendJtoken = "http://47.252.29.162:10091" + "/justlend/markets/jtokenDetails";
            //justlend 线上环境接口
            //String requestUrlOfJustLendJtoken = "https://labc.ablesdxd.link" + "/justlend/markets/jtokenDetails";
            paramsOfJustLend.put("jtokenAddr", listOfjAddress.get(i));
            log.info("requestUrlOfJustLendJtoken:" + requestUrlOfJustLendJtoken);
            response = createGetConnect(requestUrlOfJustLendJtoken, paramsOfJustLend, null, null);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            log.info("responseContentOfJustLend:" + responseContent);
            JSONArray resultFromJustLend = responseContent.getJSONObject("data").getJSONArray("depositDetail");
            if (!resultFromJustLend.containsAll(resultFromTronlink)) {
                log.info("i:" + i);
                log.info("tokenAddress:" + listOfAddress.get(i));
                log.info("jTokenAddress:" + listOfjAddress.get(i));
                log.info("resultFromTronLink:" + resultFromTronlink);
                log.info("resultFromJustLend:" + resultFromJustLend);
                flag = false;
            }


        }
        Assert.assertTrue(flag);


    }


    @Test(enabled = true)
    public void fProjectTokenInfoDepositedUsdOfJustLend() throws Exception {

        FProjectTokenInfoDao fp = new FProjectTokenInfoDao();
        List<FProjectTokenInfoDao> list = fp.query();
        HashMap<String, String> resultFromDBMap = new HashMap<String, String>();
        for (FProjectTokenInfoDao fProjectTokenInfoDao : list) {
            resultFromDBMap.put(fProjectTokenInfoDao.getContractAddress(), fProjectTokenInfoDao.getDepositedUsd());

        }
        log.info("resultFromDBMap:" + resultFromDBMap);

        ArrayList<String> listOfjAddress = new ArrayList<String>(
            Arrays.asList("TKM7w4qFmkXQLEF2MgrQroBYpd5TY7i1pq", "TRM3faiTDB9D4Vq4mwezUeo5rQLzCDqGSE", "TT6Qk1qrBM4MgyskYZx5pjeJjvv3fdL2ih", "TYf16sZLR9uXpm63bXsRCNQMQFvqqvXQ2t",
                "TPovsintcLMh9udvXgt45jvb1RYQ86imnL", "TMBRbGrkx2d3m8nAZWezFzSyJG6KrEGjj1", "TXNg6MoDTDEZKwPzTAdnzdQwfTF4LdU1QW", "TZ51C31Zh3qBSRBnTmbcuRX1rqyhzoCe8Q", "TLBoPBNAfrBPxq3rTQzSKzTXrRjjAqaiJ6",
                "TMsoCkr2yhukcGnvjhVk8Gj541BCQPEHwm", "TXFDQpnXxNSEsxo8R3brAaTMWk4Nv6uGji", "TBGCExAC3iRk5EXAVXEer3bwhTi9EN9rht", "TYVr8QECrDkf6EAiKehok5FF3ckWV5Ds7k", "TAj5XxJtkrEDvTT7mTsS3uqMcvSCp82cnR",
                "TQ7JUeFHWAxNru1Yp8YjPP3c7guZSe4e2E", "TTNcbZWxaeUSq81HJ4uY1SpyVsKykUX97W", "TKM7w4qFmkXQLEF2MgrQroBYpd5TY7i1pq", "TPovsintcLMh9udvXgt45jvb1RYQ86imnL"));
        HashMap<String, String> resultFromJustLendMap = new HashMap<String, String>();
        for (int i = 0; i < listOfjAddress.size(); i++) {

            String requestUrlOfJustLend = "http://47.252.29.162:10091" + "/justlend/markets/jtokenDetails";
            paramsOfJustLend.put("jtokenAddr", listOfjAddress.get(i));
            log.info("requestUrlOfJustLend:" + requestUrlOfJustLend);
            response = createGetConnect(requestUrlOfJustLend, paramsOfJustLend, null, null);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            log.info("responseContentOfJustLend:" + responseContent);
            JSONArray resultFromJustLend = responseContent.getJSONObject("data").getJSONArray("depositDetail");
            String depositedUSD = responseContent.getJSONObject("data").getString("depositedUSD");
            resultFromJustLendMap.put(listOfjAddress.get(i), depositedUSD);

        }
    /*    for (int i = 0; i < listOfjAddress.size(); i++) {
            String requestUrlOfJustLend = "http://47.252.29.162:10091" + "/justlend/markets";
            paramsOfJustLend.put("jtokenAddr", listOfjAddress.get(i));
            log.info("requestUrlOfJustLend:" + requestUrlOfJustLend);
            response = createGetConnect(requestUrlOfJustLend, paramsOfJustLend, null, null);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            log.info("responseContentOfJustLend:" + responseContent);
            JSONArray resultFromJustLend = responseContent.getJSONObject("data").getJSONArray("depositDetail");
            String depositedUSD = responseContent.getJSONObject("data").getString("depositedUSD");
            resultFromJustLendMap.put(listOfjAddress.get(i), depositedUSD);

        }*/
        log.info("resultFromJustLendMap:" + resultFromJustLendMap);
        boolean flag = true;

/*
        Set<String> keys = resultFromDBMap.keySet();

        for (String temp : keys) {
            String key = resultFromDBMap.get(temp);
            log.info("key: " + temp);
            log.info("333333:" + resultFromJustLendMap.get(key));
            log.info("111222:" + Double.parseDouble(resultFromJustLendMap.get(key)));
            double abs = Math.abs(Double.parseDouble(resultFromJustLendMap.get(key)) - Double.parseDouble(resultFromDBMap.get(key)));
            flag = abs < 1L ? true : false;

        }
        Assert.assertTrue(flag);*/
    }

    @Test(enabled = true)
    public void fProjectTokenInfoDepositedUsdOfTRX() throws Exception {
        //测试环境
        //String requestUrlOfJustLend = "https://nileapi.tronscan.org" + "/api/vote/witness";

        //线上环境
        String requestUrlOfJustLend = "https://api.tronscan.org" + "/api/vote/witness";

        response = createGetConnect(requestUrlOfJustLend, null, null, null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        log.info("responseContentOfJustLend:" + responseContent);

        BigDecimal totalPledgedVotes = new BigDecimal(responseContent.getString("totalVotes"));
        log.info("totalPledgedVotes:" + totalPledgedVotes);
        //todo: niletrx的usd价格可以动态查mock的接口
       /* String getPrice = "https://nileapi.tronscan.org" + "/api/vote/witness";
        response = createGetConnect(getPrice, null, null, null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        log.info("getPrice:" + responseContent);
        float trxPriceOfUsd =
            responseContent.getJSONObject("data").getJSONObject("TRX").getJSONObject("USD").getFloat("price");
        log.info("trxPriceOfUsd:" + trxPriceOfUsd);*/
        //test 环境mock接口
        //BigDecimal depositedUsdOfTRX = new BigDecimal(Double.toString(totalPledgedVotes * 0.05));
        //pre接口

        String priceCenterUrl = "https://c.tronlink.org/v1/cryptocurrency/getprice?symbol=trx&convert=usd";
        response = createGetConnect(priceCenterUrl, null, null, null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        log.info("responseContentOfPriceCenter:" + responseContent);
        BigDecimal price = new BigDecimal(responseContent.getJSONObject("data").getJSONObject("TRX").getJSONObject("quote").getJSONObject("USD").getString("price"));

        BigDecimal depositedUsdOfTRX = totalPledgedVotes.multiply(price);
        log.info("depositedUsdOfTRX:" + depositedUsdOfTRX);
        //todo:可以新增assert将计算结果和接口对比
        //直接从这个接口拿值https://testpre.tronlink.org/api/financial/tokenFinancialList?signature=YnFSwtH2L5dx5ZbJX9YnFQCReAUguyXkyn

    }

    @Test(enabled = true)
    public void fProjectTokenInfoDepositedUsdOfBttc() throws Exception {
        //测试环境
        //String requestUrlOfJustLend = "https://newtestapi.bt.io" + "/bttc/chain/info";
        //线上
        String requestUrlOfJustLend = "https://api.newbt.io" + "/bttc/chain/info";
        response = createGetConnect(requestUrlOfJustLend, null, null, null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        log.info("responseContentOfBttc:" + responseContent);
        BigDecimal rewardCount = responseContent.getJSONObject("data").getBigDecimal("stackCount");
        log.info("rewardCount:" + rewardCount);
        BigDecimal price = responseContent.getJSONObject("data").getBigDecimal("price");
        log.info("price:" + price.toPlainString());
        log.info("1123:" + new BigDecimal(10).pow(18));
        BigDecimal depositedUsdOfBttc = (BigDecimal) rewardCount.multiply(price).divide(new BigDecimal(10).pow(18));
        log.info("depositedUsdOfBttc:" + depositedUsdOfBttc);
    }
}
