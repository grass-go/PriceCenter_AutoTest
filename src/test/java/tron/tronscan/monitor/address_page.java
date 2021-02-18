package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class address_page {

    private JSONObject responseContent;
    private JSONObject responseContent2;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private JSONObject proposalContent;
    private String accountAddress = Configuration.getByPath("testng.conf")
            .getString("defaultParameter.accountAddress");
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "address token")
    public void address_token() {
        System.out.println();
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("address", "TKwmkoBFyhekCgkAZ7FmxnN6oaL3GyD8xR");
        response = TronscanApiList.getAccountList(tronScanNode, params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("trc20token_balances");
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(!responseObject.getString("tokenId").isEmpty());
            String trc20Address = responseObject.getString("tokenId");
            Map<String, String> params2 = new HashMap<>();
            params2.put("contract",trc20Address);
            response = TronscanApiList.getTokentrc20(tronScanNode,params2);
            responseContent2 = TronscanApiList.parseResponseContent(response);
//            Assert.assertTrue(responseContent2.getInteger("total") == 1);
            try {
                Thread.sleep(500);
            }catch (Exception ex){
            }
        }
    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "address transaction")
    public void address_transaction() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 1000000000);
        double total = responseContent.getDouble("total");
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);
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
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("amount").isEmpty());
            //contractData
            JSONObject responseObject = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(responseObject.getString("owner_address"), ownerAddress);
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
        }
        String start = String.valueOf(((int)Math.floor(total/20)*20));
        System.out.println(start);
        Map<String, String> Params2 = new HashMap<>();
        Params2.put("sort", "-timestamp");
        Params2.put("limit", "20");
        Params2.put("count", "true");
        Params2.put("start", start);
        Params2.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        response = TronscanApiList.getTransactionList(tronScanNode, Params2);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
//        responseContent = TronscanApiList.parseResponseContent(response);
//        total = responseContent.getDouble("total");
//        rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
//        TronscanApiList.printJsonContent(responseContent);
//        Assert.assertTrue(rangeTotal >= total && total > 0);
//        responseArrayContent = responseContent.getJSONArray("data");
//        Assert.assertTrue(!responseArrayContent.isEmpty());
    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
    public void test01getBlockDetail() {
        //Get response
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-number");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", accountAddress);
        response = TronscanApiList.getTransactionList(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 1000000000);
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
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("amount").isEmpty());
            //contractData
            JSONObject responseObject = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(responseObject.getString("owner_address"),ownerAddress);
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
        }
    }

    //该功能搜索框前端已去掉
    /**
     * constructor. limit不为零
     * 根据地址查询出账户下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
     * 账户页下的交易界面
     * 当前hash是当前账户地址内部交易页面中的hash数据，查询出来的数据有且只有一条
     */
    @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
    public void test01getBlockDetail02() {
        //Get response
        String keyword = "334fadcf1465237ea09e10f3f38873a4c5b445fdaf725d44266c8760090120fe";
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-number");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", accountAddress);
        //账户交易页下的hash值，通过搜索hash值有且只能查询出一条数据
        Params.put("keyword",keyword);
        response = TronscanApiList.getTransactionList(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 1000000000);
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal == total && total ==1);
        responseArrayContent = responseContent.getJSONArray("data");
        targetContent = responseArrayContent.getJSONObject(0);
        Assert.assertTrue(targetContent.containsKey("hash"));
        String hash_key = targetContent.getString("hash");
        Assert.assertEquals(hash_key.length(), 64);
        Assert.assertEquals(hash_key, keyword);
        Assert.assertTrue(targetContent.getLong("block") >= 10000000);
        Assert.assertTrue(!targetContent.getString("timestamp").isEmpty());

        Assert.assertTrue(!targetContent.getString("contractType").isEmpty());
        Assert.assertTrue(targetContent.containsKey("confirmed"));
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        String ownerAddress = targetContent.getString("ownerAddress");
        Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
        Assert.assertTrue(targetContent.containsKey("toAddress"));
        Assert.assertTrue(!targetContent.getString("contractRet").isEmpty());
        Assert.assertTrue(!targetContent.getString("result").isEmpty());
        Assert.assertTrue(!targetContent.getString("tokenType").isEmpty());
        Assert.assertTrue(!targetContent.getString("tokenId").isEmpty());
        Assert.assertTrue(!targetContent.getString("amount").isEmpty());
        Assert.assertTrue(!targetContent.getString("tokenAbbr").isEmpty());
        //contractData
        JSONObject responseObject = targetContent.getJSONObject("contractData");
        Assert.assertEquals(responseObject.getString("owner_address"),ownerAddress);
        Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());

    }

    //该功能搜索框前端已去掉
    /**
     * constructor. limit不为零
     * 根据地址查询出账户下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
     * 账户页下的交易界面
     * 按地址搜索
     */
    @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
    public void test01getBlockDetail03() {
        //Get response
//    String keyword = "";
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-number");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", accountAddress);
        //搜索框中输入交易页中相关的地址，搜索出来的数据有且只有一条或多条
        Params.put("keyword",accountAddress);
        response = TronscanApiList.getTransactionList(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 1000000000);
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
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("amount").isEmpty());
            //contractData
            JSONObject responseObject = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(responseObject.getString("owner_address"),ownerAddress);
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
        }
    }

    //该功能搜索框前端已去掉
    /**
     * constructor. limit为零
     * 搜索中输入错误的数据，查询出来的数据为空
     */
    @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions related to a specified account")
    public void test02getBlockDetail05() {
        //Get response
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-number");
        Params.put("limit", "0");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", accountAddress);
        //输入错误的数据
        Params.put("keyword","9999");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal == total && total==0);
        Assert.assertTrue(responseContent.containsKey("data"));
        Assert.assertTrue(responseContent.containsKey("wholeChainTxCount"));
        Assert.assertTrue(responseContent.containsKey("contractMap"));
    }

    /**
     * constructor.
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List a single the exchange pair's transaction records ")
    public void getTransactionTest() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        response = TronscanApiList.getTransactionTest(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        Assert.assertTrue(responseContent.size() >= 3);
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total);
        //data
        JSONArray exchangeArray = responseContent.getJSONArray("data");
        for (int i = 0; i < exchangeArray.size(); i++) {
            //exchangeID
            Assert.assertTrue(Integer.valueOf(exchangeArray.getJSONObject(i).get("exchangeID").toString()) >= 1);
            //blockID
            Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("blockID").toString()) >= 10000000);
            //tokenID
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("tokenID").isEmpty());
            //createTime
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("createTime").isEmpty());
            //hash
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("trx_hash")).matches());
            //quant
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("quant"));
            //address
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("creatorAddress")).matches());
            //confirmed
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("confirmed"));
        }
    }

    /**
     * constructor. limit不为零
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions in the blockchain(only display the latest 10,000 data records in the query time range)")
    public void getTransactionTestRang() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("start_timestamp", "1548000000000");
        Params.put("end_timestamp", "1548056638507");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        Assert.assertTrue(responseContent.size() >= 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) > 1000000000);
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total);
        JSONArray exchangeArray = responseContent.getJSONArray("data");
        for (int i = 0; i < exchangeArray.size(); i++) {
            //contractRet
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("contractRet"));
            //data
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("data"));
            //contractRet
            Assert.assertTrue(!exchangeArray.getJSONObject(i).get("contractType").toString().isEmpty());
            //fee
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("fee"));
            //toAddress
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String ownerAddress = exchangeArray.getJSONObject(i).getString("ownerAddress");
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
            //confirmed
            Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("Events"));
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("SmartCalls"));
            Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) >= 1000000);
            //hash
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("id"));
            //timestamp
            Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
            //contractData json
            proposalContent = exchangeArray.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(proposalContent.getString("owner_address"),ownerAddress);

        }

    }

    /**
     * constructor.查询区块上交易列表 limit不为零
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询区块上交易列表")
    public void getTransactionTestBlock() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("total", "0");
        Params.put("block", "12448572");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        for (int i = 0; i < exchangeArray.size(); i++) {
            //contractRet
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("contractRet"));
            //cost json
            proposalContent = exchangeArray.getJSONObject(i).getJSONObject("cost");
            //net_fee
            Assert.assertTrue(Long.valueOf(proposalContent.get("net_fee").toString()) >= 0);
            //energy_usage
            Assert.assertTrue(Long.valueOf(proposalContent.get("energy_usage").toString()) >= 0);
            //energy_fee
            Assert.assertTrue(Long.valueOf(proposalContent.get("energy_fee").toString()) >= 0);
            //energy_usage_total
            Assert.assertTrue(Long.valueOf(proposalContent.get("energy_usage_total").toString()) >= 0);
            //origin_energy_usage
            Assert.assertTrue(Long.valueOf(proposalContent.get("origin_energy_usage").toString()) >= 0);
            //net_usage
            Assert.assertTrue(Long.valueOf(proposalContent.get("net_usage").toString()) >= 0);
            //data
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("data"));
            //contractRet
            Assert.assertTrue(!exchangeArray.getJSONObject(i).get("contractType").toString().isEmpty());
            //fee
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("fee"));
            //toAddress
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String ownerAddress = exchangeArray.getJSONObject(i).getString("ownerAddress");
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
            //confirmed
            Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("Events"));
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("SmartCalls"));
            Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) >= 1000000);
            //hash
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            Assert.assertTrue(patternHash.matcher(exchangeArray.getJSONObject(i).getString("hash")).matches());
            Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("id"));
            //timestamp
            Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
            //contractData json
            proposalContent = exchangeArray.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(proposalContent.getString("owner_address"),ownerAddress);

        }
    }


    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于8，为变更账户权限
     * 此账户下存在变更账户权限类型，数据不为空
     */

    @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_02() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "8");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }

    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于1，为TRX转账
     * 此账户下存在TRX转账，数据不为空
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_03() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "1");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }
    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于6，为TRC10转账
     * 此账户下存在TRC10转账，数据不为空
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_04() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "6");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }
    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于2，为冻结资产
     * 此账户下存在冻结资产，数据不为空
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_05() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "2");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }

    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于3，为解冻资产
     * 此账户下存在解冻资产，数据不为空
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_06() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "3");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }

    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于5，为触发智能合约
     * 此账户下存在触发智能合约，数据不为空
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_07() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "5");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }
    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于4，为投票
     * 此账户下存在投票，数据不为空
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_08() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "4");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }

    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 状态为已确认的状态，此账户下数据不为0
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_10() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("confirm", "0");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }
    /**
     * constructor.查询区块上交易列表
     * 20币的交易数据统计
     * 账户地址下交易列表的20币带有交易，交易列表不为空
     * 交易类型等于7，为提取收益
     * 此账户下存在提取收益，数据不为空
     */

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询个人账户页下20token上交易列表")
    public void getTransaction_token20_09() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        Params.put("type", "7");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //three object, "total" and "Data","rangeTotal"
        
        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //total不为空
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        JSONArray exchangeArray = responseContent.getJSONArray("data");
        Assert.assertTrue(exchangeArray.size() > 0);

    }
    /**
     * constructor.simple-transaction接口
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the transactions under specified conditions;")
    public void getSimple_Transaction() {
        response = TronscanApiList.getSimple_Transaction(tronScanNode);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //total_votes
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total);

        //data list
        responseArrayContent = responseContent.getJSONArray("data");

        Assert.assertTrue(responseArrayContent.size() > 0);
        for (int i = 0; i < responseArrayContent.size(); i++) {

            //hash
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            Assert.assertTrue(patternHash.matcher(responseArrayContent.getJSONObject(i).getString("hash")).matches());
            //timestamp
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
            //ownerAddress
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String ownerAddress = responseArrayContent.getJSONObject(i).getString("ownerAddress");
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
//            Assert.assertTrue(
//                    patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("toAddress")).matches());
            //contractType
            Assert.assertTrue(
                    !responseArrayContent.getJSONObject(i).get("contractType").toString().isEmpty());
            //contractData json
            proposalContent = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(proposalContent.getString("owner_address"),ownerAddress);
        }

    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
    public void testStatistics(){
        response = TronscanApiList.statistics();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.getLong("lastDayTxCount") > 0);
        Assert.assertTrue(responseContent.getLong("lastDayTxAmount") > 0);
        Assert.assertTrue(responseContent.getLong("txCount") > 0);
        Assert.assertTrue(responseContent.getLong("txAmount") > 0);
        Assert.assertTrue(responseContent.getLong("triggerProportion") > 0);
        Assert.assertTrue(responseContent.getLong("triggerAmount") > 0);
        Assert.assertTrue(responseContent.getLong("triggerProportion") > 0);
        Assert.assertTrue(responseContent.getLong("trc20Count") > 0);
        Assert.assertTrue(responseContent.getLong("trc20Amount") > 0);
        Assert.assertTrue(responseContent.getLong("trc20Proportion") > 0);
        Assert.assertTrue(responseContent.getLong("trxTransferCount") > 0);
        Assert.assertTrue(responseContent.getLong("trxTransferProportion") > 0);
        Assert.assertTrue(responseContent.getLong("trc10TransferCount") > 0);
        Assert.assertTrue(responseContent.getLong("trc10TransferCount") > 0);
        Assert.assertTrue(responseContent.getLong("trc10TransferProportion") > 0);
        Assert.assertTrue(responseContent.getLong("votesCount") > 0);
        Assert.assertTrue(responseContent.getLong("votesAmount") > 0);
        Assert.assertTrue(responseContent.getDouble("votesProportion") > 0);
        Assert.assertTrue(responseContent.getLong("freezeBalanceCount") > 0);
        Assert.assertTrue(responseContent.getLong("freezeBalanceAmount") > 0);
        Assert.assertTrue(responseContent.getDouble("freezeBalanceProportion") > 0);
        Assert.assertTrue(responseContent.getLong("othersCount") > 0);
        Assert.assertTrue(responseContent.getLong("othersAmount") > 0);
        Assert.assertTrue(responseContent.getDouble("othersProportion") > 0);


    }
}
