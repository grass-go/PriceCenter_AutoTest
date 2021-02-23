package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class home_page {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONObject targetContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list").get(0);
    private String tronScanNodeDapp = "dappchainapi.tronscan.org/";

    /**
     * constructor.
     * 功能: trx市值及流通量
     * 		请求方法: get
     * 		入参:
     * 			source: 数据源，可填两个参数，coingecko，coinmarketcap
     * 		返回:
     * 			trx_price_graph: 24小时trx价格变化
     *          total_turnover: 总流通量
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "trx市值及流通量")
    public void getSystemHomepage() {
        //Get response
        Map<String, String> Params = new HashMap<>();
        Params.put("source", "coinmarketcap");
        response = TronscanApiList.getSystemHomepage(tronScanNode,Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //Five object
        Assert.assertTrue(responseContent.size() == 4);
        Assert.assertTrue(!responseContent.get("node").toString().isEmpty());
        Assert.assertTrue(Double.valueOf(responseContent.get("trx_volume_24h").toString()) > 0);

        Assert.assertTrue(!responseContent.get("tps").toString().isEmpty());
        //yesterdayStat
        targetContent = responseContent.getJSONObject("yesterdayStat");
        Assert.assertTrue(!targetContent.get("data").toString().isEmpty());
        Assert.assertTrue(Boolean.valueOf(targetContent.getString("success")));
    }


    /**
     * constructor.
     * 功能: 每日交易数，账户增长
     * 		请求方法: get
     * 		入参:可无参，默认返回14天的数据
     * 			limit: 0-20
     * 		返回:
     * 			energy_usage: 能量消耗
     *          net_usage: 带宽消耗
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "账户增长(14天) 主网")
    public void getOverView_usage() {
        //Get response
        Map<String, String> Params = new HashMap<>();
        Params.put("limit", "20");
        response = TronscanApiList.getOverView_usage(tronScanNode,Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //Five object
        Assert.assertTrue(responseContent.size() == 2);
        //two object "success" and "data"
        Assert.assertTrue(Boolean.valueOf(responseContent.getString("success")));
        JSONArray exchangeArray = responseContent.getJSONArray("data");
        targetContent = exchangeArray.getJSONObject(0);
        //data
        Assert.assertTrue(targetContent.containsKey("date"));
        //avgBlockTime
        Assert.assertTrue(Long.valueOf(targetContent.get("avgBlockTime").toString()) > 0);
        //totalBlockCount 区块数
        Assert.assertTrue(Long.valueOf(targetContent.get("totalBlockCount").toString()) > 1000);
        //totalTransaction 交易数
        Assert.assertTrue(Long.valueOf(targetContent.get("totalTransaction").toString()) > 0);
        //blockchainSize
        Assert.assertTrue(Long.valueOf(targetContent.get("blockchainSize").toString()) > 1000);
        //avgBlockSize
        Assert.assertTrue(Long.valueOf(targetContent.get("avgBlockSize").toString()) > 10);
        //
        Assert.assertFalse(targetContent.get("newTransactionSeen").toString().isEmpty());
        Assert.assertFalse(targetContent.get("newAddressSeen").toString().isEmpty());
        Assert.assertFalse(targetContent.get("totalAddress").toString().isEmpty());
        Assert.assertFalse(targetContent.get("newBlockSeen").toString().isEmpty());
        //energy_usage
        Assert.assertTrue(Long.valueOf(targetContent.get("energy_usage").toString()) >= 0);
        //net_usage
        Assert.assertTrue(Long.valueOf(targetContent.get("net_usage").toString()) >= 0);

    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "账户增长(14天) 侧链")
    public void getOverView_usage_dapp() {
        //Get response
        Map<String, String> Params = new HashMap<>();
        Params.put("limit", "20");
        response = TronscanApiList.getOverView_usage(tronScanNodeDapp,Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //Five object
        Assert.assertTrue(responseContent.size() == 2);
        //two object "success" and "data"
        Assert.assertTrue(Boolean.valueOf(responseContent.getString("success")));
        JSONArray exchangeArray = responseContent.getJSONArray("data");
        targetContent = exchangeArray.getJSONObject(0);
        //data
        Assert.assertTrue(targetContent.containsKey("date"));
        //avgBlockTime
        Assert.assertTrue(Long.valueOf(targetContent.get("avgBlockTime").toString()) > 0);
        //totalBlockCount 区块数
        Assert.assertTrue(Long.valueOf(targetContent.get("totalBlockCount").toString()) > 1000);
        //totalTransaction 交易数
        Assert.assertTrue(Long.valueOf(targetContent.get("totalTransaction").toString()) > 0);
        //blockchainSize
        Assert.assertTrue(Long.valueOf(targetContent.get("blockchainSize").toString()) > 1000);
        //avgBlockSize
        Assert.assertTrue(Long.valueOf(targetContent.get("avgBlockSize").toString()) > 10);
        //
        Assert.assertFalse(targetContent.get("newTransactionSeen").toString().isEmpty());
        Assert.assertFalse(targetContent.get("newAddressSeen").toString().isEmpty());
        Assert.assertFalse(targetContent.get("totalAddress").toString().isEmpty());
        Assert.assertFalse(targetContent.get("newBlockSeen").toString().isEmpty());
        //energy_usage
        Assert.assertTrue(Long.valueOf(targetContent.get("energy_usage").toString()) >= 0);
        //net_usage
        Assert.assertTrue(Long.valueOf(targetContent.get("net_usage").toString()) >= 0);

    }

    /**
     * constructor.
     * 功能: trx交易量
     * 		请求方法: get
     * 		入参:
     * 			start_day : 开始日期，例如 2020-06-07
     *          end_day   : 结束日期，例如 2020-06-20
     * 		返回:
     * 			返回数据中，total_freeze_weight为trx冻结量
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "trx每日冻结量(14天)")
    public void getFreezeresource() {
        //Get response

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        System.out.println("结束时间：" + sdf.format(date));
        String end_day = sdf.format(date);
        calendar.add(Calendar.DAY_OF_MONTH, -13);
        date = calendar.getTime();
        String start_day = sdf.format(date);
        System.out.println("开始时间：" + sdf.format(date));


        Map<String, String> Params = new HashMap<>();
        Params.put("end_day", end_day);
        Params.put("start_day", start_day);
        response = TronscanApiList.getFreezeresource(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //Five object
        Assert.assertTrue(responseContent.size() == 2);
        Assert.assertTrue(responseContent.containsKey("total"));

        //data json
        JSONArray tokenBalancesArray = responseContent.getJSONArray("data");
        targetContent = tokenBalancesArray.getJSONObject(0);
        for (int i = 0; i < targetContent.size(); i++) {
            Assert.assertTrue(Double.valueOf(targetContent.get("freezing_rate").toString()) > 0);
            Assert.assertTrue(Double.valueOf(targetContent.get("net_rate").toString()) > 0);

            Assert.assertTrue(Long.valueOf(targetContent.get("total_energy_weight").toString()) > 1000000);
            Assert.assertTrue(Double.valueOf(targetContent.get("total_turn_over").toString()) > 1000000);
            Assert.assertTrue(Double.valueOf(targetContent.get("energy_rate").toString()) > 0);

            Assert.assertTrue(Long.valueOf(targetContent.get("total_net_weight").toString()) > 1000000);
            //total_freeze_weight  trx冻结量
            Assert.assertTrue(Long.valueOf(targetContent.get("total_freeze_weight").toString()) > 1000000000L);
            Assert.assertTrue(targetContent.containsKey("day"));
        }

    }
    }