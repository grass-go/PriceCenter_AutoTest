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

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "首页展开图表")
    public void getSystemHomepage() {
        //Get response
        Map<String, String> Params = new HashMap<>();
        response = TronscanApiList.getSystemHomepage(tronScanNode,Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //账户总数
        Assert.assertTrue(responseContent.getJSONObject("accountList").getLong("rangeTotal")>53872441);

        //TVL数据
        Assert.assertTrue(!responseContent.getJSONObject("tvl").isEmpty());

        //trx_volume_24h
        Assert.assertTrue(!responseContent.getString("trx_volume_24h").isEmpty());

    }


    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Trx价格")
    public void getTrxPrice() {
        //Get response
        response = TronscanApiList.getPrice(tronScanNode);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        Assert.assertTrue(!(responseContent.getString("price_in_usd")).isEmpty());
        Assert.assertEquals(responseContent.getString("from") , "coinmarketcap");
        Assert.assertEquals(responseContent.getString("token")  , "trx");

    }


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
