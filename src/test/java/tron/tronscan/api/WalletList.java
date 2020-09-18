package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class WalletList {

    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.地址不同，response不同
     *
     * 获取用户可展示的token列表，包括token名称，id，余额等信息
     * 	URL： /api/account/wallet
     * 	请求方式：GET
     * 	参数：
     * 		address: 用户地址
     * 	返回值：用户可展示的token列表
     * 		token_id: token的id，10币为id，20币为合约地址。"_"为trx
     * 		balance: 当前币的余额，为真实值，无需做任何处理
     * 		token_abbr: token简称
     * 		token_name: token名称
     * 		token_type: token类型，int类型。10为10币，20为20币，trx为0。
     *      token_value
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "token_value值")
    public void testWallet_10token() {
        //Get response
        String address = "TEqZpKG8cLquDHNVGcHXJhEQMoWE653nBH";
        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        response = TronscanApiList.getWallet(tronScanNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //data object
        Assert.assertTrue(responseContent.size() == 2);
        //count
        Assert.assertTrue(Long.valueOf(responseContent.getString("count")) > 0);
        //object data
        responseArrayContent = responseContent.getJSONArray("data");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("token_price").toString().isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("token_id").toString().isEmpty());
            Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("balance")) > 0);
            String token_abbr = responseArrayContent.getJSONObject(i).getString("token_abbr");
            String token_name = responseArrayContent.getJSONObject(i).getString("token_name");
            Assert.assertEquals(token_abbr,token_name);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("token_type"));
        }
    }

    /**
     * constructor.地址不同，response不同
     *
     * 获取用户可展示的token列表，包括token名称，id，余额等信息
     * 	URL： /api/account/wallet
     * 	请求方式：GET
     * 	参数：
     * 		address: 用户地址
     * 	返回值：用户可展示的token列表
     * 		token_id: token的id，10币为id，20币为合约地址。"_"为trx
     * 		balance: 当前币的余额，为真实值，无需做任何处理
     * 		token_abbr: token简称
     * 		token_name: token名称
     * 		token_type: token类型，int类型。10为10币，20为20币，trx为0。
     *      frozen
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "frozen值")
    public void testWallet_Frozen() {
        //Get response
        String address = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        response = TronscanApiList.getWallet(tronScanNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //data object
        Assert.assertTrue(responseContent.size() == 2);
        //count
        Assert.assertTrue(Long.valueOf(responseContent.getString("count")) > 0);
        //object data
        responseArrayContent = responseContent.getJSONArray("data");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("token_price").toString().isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("token_id").toString().isEmpty());
            //当前余额为0
            Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("balance")) >= 0);
            String token_abbr = responseArrayContent.getJSONObject(i).getString("token_abbr");
            String token_name = responseArrayContent.getJSONObject(i).getString("token_name");
            Assert.assertEquals(token_abbr,token_name);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("token_type"));
        }
    }

    /**
     * token搜索
     * 	URL：/api/token/search
     * 	请求方式：GET
     * 	参数：
     * 		term：搜索关键词
     * 	返回：搜索结果列表
     * 		desc: 同搜索接口，为类型
     * 		value: 同搜索接口，为搜索结果
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "搜索关键词")
    public void testTokenSearch() {
        //搜索关键字
        String term = "1000152";
        Map<String, String> params = new HashMap<>();
        params.put("term", term);
        response = TronscanApiList.getTokenSearch(tronScanNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //data object
        Assert.assertTrue(responseContent.size() >= 0);
        //count
        Assert.assertTrue(Long.valueOf(responseContent.getString("count")) >= 0);
        //object data
        Assert.assertTrue(responseContent.containsKey("data"));

    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
