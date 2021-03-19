package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class blockchain_transactions_page {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject sonContent;
    private JSONObject proposalContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list").get(0);
    private String accountAddress = Configuration.getByPath("testng.conf")
            .getString("defaultParameter.accountAddress");
    String url = "https://tronscan.org/#/blockchain/transactions";


    @Test(enabled = true, description = "区块链-交易")
    public void blockchain_transactions_page() {
        //Get response
        int end_time = (int) (System.currentTimeMillis() / 1000) ;
        String end_timestamp = String.valueOf(end_time) + "000";
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("start_timestamp","1529856000000");
        Params.put("end_timestamp",end_timestamp);
        response = TronscanApiList.getTransactionList(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 1000000000);
        Long wholeChainTxCount = responseContent.getLong("wholeChainTxCount");
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total >0);
        responseArrayContent = responseContent.getJSONArray("data");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
            String hash_key = responseArrayContent.getJSONObject(i).getString("hash");
            Assert.assertEquals(hash_key.length(), 64);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("block") >= 10000000);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("timestamp").isEmpty());

            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("contractType").isEmpty());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String ownerAddress = responseArrayContent.getJSONObject(i).getString("ownerAddress");
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("toAddress"));
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("contractRet").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("result").isEmpty());
//            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("tokenType").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("amount").isEmpty());
//            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("tokenAbbr").isEmpty());
            //contractData
            JSONObject responseObject = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(responseObject.getString("owner_address"),ownerAddress);
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
            try {
                Thread.sleep(1000);
            }catch (Exception e){
            }

            //交易详情数据校验
            response = TronscanApiList.getTransactionInfo(tronScanNode,hash_key);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronscanApiList.parseResponseContent(response);
            TronscanApiList.printJsonContent(responseContent);
            Assert.assertFalse(responseContent.getString("contract_map").isEmpty());


            //交易数据
            response = TronscanApiList.getTransactionStatistics(tronScanNode);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronscanApiList.parseResponseContent(response);
            TronscanApiList.printJsonContent(responseContent);

            //three object, "retCode" and "Data"
////            Assert.assertTrue(responseContent.containsKey("trc20Proportion"));
////            Assert.assertTrue(Double.valueOf(responseContent.get("trxTransferAmount").toString()) > 0);
//            Assert.assertTrue(responseContent.containsKey("trc10TransferCount"));
//            Assert.assertTrue(responseContent.getLong("lastDayTxCount") > 1000000);
////            Assert.assertTrue(responseContent.getLong("lastDayTxAmount") > 100000000);
            Assert.assertTrue(responseContent.containsKey("trxTransferCount"));
            Assert.assertTrue(Double.valueOf(responseContent.get("txAmount").toString()) > 1108940999624238L);
//            Assert.assertTrue((responseContent.getLong("txCount") - wholeChainTxCount) < 1000);


        }
    }

}
