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
public class Top10List {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONObject targetContent;
    private JSONArray contentArray;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list").get(0);
    /**
     * constructor.
     * 最佳数据-概览
     * type=0表示查询合约
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "最佳数据-概览")
    public void getTop10() {
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("type", "0");
        params.put("time", "1");
        response = TronscanApiList.getTop10(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        // list
        Assert.assertTrue(!responseContent.getString("start_time").isEmpty());
        Assert.assertTrue(!responseContent.getString("end_time").isEmpty());
        //TRX总余额
        Assert.assertTrue(Double.valueOf(responseContent.getString("contract_whole_balance")) > 0);;

        //调用账户数
        //调用账户数-小时
        Long houraddressCount = Long.valueOf(responseContent.get("last_hour_contract_active_address").toString());
        //调用账户数-天
        Long dayaddressCount = Long.valueOf(responseContent.get("last_day_contract_active_address").toString());
        //调用账户数-周
        Long weekaddressCount = Long.valueOf(responseContent.get("last_week_contract_active_address").toString());
        Assert.assertTrue(weekaddressCount >= dayaddressCount && dayaddressCount >= houraddressCount && houraddressCount > 0);
        //调用次数
        //调用次数-小时
        Long hourCount_triggers = Long.valueOf(responseContent.get("last_hour_contract_triggers").toString());
        //调用次数-天
        Long dayCount_triggers = Long.valueOf(responseContent.get("last_day_contract_triggers").toString());
        //调用次数-周
        Long weekCount_triggers = Long.valueOf(responseContent.get("last_week_contract_triggers").toString());
        Assert.assertTrue(weekCount_triggers >= dayCount_triggers && dayCount_triggers >= hourCount_triggers && hourCount_triggers > 0);
        //TRX转账数量
        //TRX转账数量-小时
        Double hourCount_amount = Double.valueOf(responseContent.get("last_hour_trx_transfer_amount").toString());
        //TRX转账数量-天
        Double dayCount_amount = Double.valueOf(responseContent.get("last_day_trx_transfer_amount").toString());
        //TRX转账数量-周
        Double weekCount_amount = Double.valueOf(responseContent.get("last_week_trx_transfer_amount").toString());
        Assert.assertTrue(weekCount_amount >= dayCount_amount && dayCount_amount >= hourCount_amount && hourCount_amount > 0);
        //调用次数
        //调用次数-小时
        Long hourCount_transaction = Long.valueOf(responseContent.get("last_hour_trx_transaction_number").toString());
        //调用次数-天
        Long dayCount_transaction = Long.valueOf(responseContent.get("last_day_trx_transaction_number").toString());
        //调用次数-周
        Long weekCount_transaction = Long.valueOf(responseContent.get("last_week_trx_transaction_number").toString());
        Assert.assertTrue(weekCount_transaction >= dayCount_transaction && dayCount_transaction >= hourCount_transaction && hourCount_transaction > 0);
        //冻结数据量
        Assert.assertTrue(Double.valueOf(responseContent.get("whole_freeze").toString()) > 0);
        //投票总数
        Assert.assertTrue(Long.valueOf(responseContent.get("whole_vote").toString()) > 0);
        //TRON通证总数
        Long token_all = Long.valueOf(responseContent.get("token_all").toString());
        //10通证总数
        Long trc10_all = Long.valueOf(responseContent.get("trc10_all").toString());
        //20通证总数
        Long trc20_all = Long.valueOf(responseContent.get("trc20_all").toString());
        Assert.assertTrue(token_all == trc10_all + trc20_all && token_all >0);

        //TRONSCAN录入总数
        Long token_record = Long.valueOf(responseContent.get("token_record").toString());
        //10通证总数
        Long trc10_record = Long.valueOf(responseContent.get("trc10_record").toString());
        //20通证总数
        Long trc20_record = Long.valueOf(responseContent.get("trc20_record").toString());
        Assert.assertTrue(token_record == trc10_record + trc20_record && token_record >0);

        //消耗总能量_小时
        Long hour_energy = Long.valueOf(responseContent.get("last_hour_energy_usage").toString());
        Assert.assertTrue(Long.valueOf(responseContent.get("last_day_energy_usage").toString()) > 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("last_week_energy_usage").toString()) > 0);

        //消耗冻结TRX的能量
        Long energy_use = Long.valueOf(responseContent.get("last_hour_whole_energy_use").toString());
        Assert.assertTrue(Long.valueOf(responseContent.get("last_day_whole_energy_use").toString()) > 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("last_week_whole_energy_use").toString()) > 0);
        //消耗燃烧TRX的能量
        Long energy_burn = Long.valueOf(responseContent.get("last_hour_whole_energy_burn").toString());
        Assert.assertTrue(Long.valueOf(responseContent.get("last_day_whole_energy_burn").toString()) > 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("last_week_whole_energy_burn").toString()) > 0);
        //消耗合约部署者提供的能量
        Long contract_use = Long.valueOf(responseContent.get("last_hour_whole_contract_use").toString());
        Assert.assertTrue(Long.valueOf(responseContent.get("last_day_whole_contract_use").toString()) > 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("last_week_whole_contract_use").toString()) > 0);
        //按小时计算
        Assert.assertTrue(hour_energy == energy_use + energy_burn + contract_use && hour_energy >0);

        //消耗总带宽_小时
        Long hour_bandwidth = Long.valueOf(responseContent.get("last_hour_net_usage").toString());
        Assert.assertTrue(Long.valueOf(responseContent.get("last_day_net_usage").toString()) > 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("last_week_net_usage").toString()) > 0);

        //消耗冻结TRX的带宽
        Long bandwidth_use = Long.valueOf(responseContent.get("last_hour_whole_net_use").toString());
        Assert.assertTrue(Long.valueOf(responseContent.get("last_day_whole_net_use").toString()) > 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("last_week_whole_net_use").toString()) > 0);
        //消耗燃烧TRX的带宽
        Long bandwidth_burn = Long.valueOf(responseContent.get("last_hour_whole_net_burn").toString());
        Assert.assertTrue(Long.valueOf(responseContent.get("last_day_whole_net_burn").toString()) > 0);
        Assert.assertTrue(Long.valueOf(responseContent.get("last_week_whole_net_burn").toString()) > 0);

        //按小时计算
        Assert.assertTrue(hour_bandwidth == bandwidth_use + bandwidth_burn && hour_bandwidth >0);
    }
    /**
     * constructor.
     * 最佳数据-合约
     * type=11,12,13表示查询合约
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "最佳数据-合约")
    public void getTop10_0() {
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("type", "11,12,13");
        params.put("time", "1");
        response = TronscanApiList.getTop10(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONArray responseContentArray = TronscanApiList.parseArrayResponseContent(response);
        TronscanApiList.printJsonArrayContent(responseContentArray);
        JSONArray exchangeArray = responseContentArray;
//    TronscanApiList.printJsonContent(responseContent);
        // list
        Assert.assertTrue(exchangeArray.size() > 0);
        for (int i = 0; i < exchangeArray.size(); i++) {
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("start_time").isEmpty());
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("end_time").isEmpty());
            //type
            String type = exchangeArray.getJSONObject(i).getString("type");
            Assert.assertTrue(!type.isEmpty());
            //data list
            contentArray = exchangeArray.getJSONObject(i).getJSONArray("data");
            Assert.assertTrue(contentArray.size() > 0);
            //最佳合约--TRX总余额列表
            if (type.equals("11")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //余额
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("holders").toString()) > 0);
                    //contract地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("contract");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //合约名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                }
            }

            //最佳合约--调用账户数
            if (type.equals("12")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //调用账户数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("active_address").toString()) > 0);
                    //contract地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("contract");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //合约名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                }
            }

            //最佳合约--调用次数
            if (type.equals("13") ) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //调用账户数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("triggers").toString()) > 0);
                    //contract地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("contract");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //合约名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                }
            }
       }
    }


    /**
     * constructor.
     * 最佳数据-账户
     * type=1，2，3，4，5，6表示查询账户列表数据
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
    public void getTop10_1() {
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("type", "1,2,3,4,5");
        params.put("time", "1");
        response = TronscanApiList.getTop10(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONArray responseContentArray = TronscanApiList.parseArrayResponseContent(response);
        TronscanApiList.printJsonArrayContent(responseContentArray);
        JSONArray exchangeArray = responseContentArray;
        // list
        Assert.assertTrue(exchangeArray.size() > 0);
        for (int i = 0; i < exchangeArray.size(); i++) {
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("start_time").isEmpty());
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("end_time").isEmpty());
            //type
            String type = exchangeArray.getJSONObject(i).getString("type");
            Assert.assertTrue(!type.isEmpty());
            //data list
            contentArray = exchangeArray.getJSONObject(i).getJSONArray("data");
            Assert.assertTrue(contentArray.size() > 0);
            //最佳账户--发送TRX数量、接收TRX数量
            if (type.equals("1") || type.equals("3")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //数量
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("amount").toString()) > 0);
                    //账户地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("address");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("type").toString().isEmpty());
                }
            }

            //最佳账户--发送TRX总笔数、接收TRX总笔数
            if (type.equals("2") || type.equals("4")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //笔数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("transaction_number").toString()) > 0);
                    //账户地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("address");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("type").toString().isEmpty());
                }
            }

            //最佳账户--冻结TRX数量
            if (type.equals("5")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //冻结数量
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("freeze").toString()) > 0);
                    //账户地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("address");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                }
            }
            //最佳账户--已投票数
            if (type.equals("6")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //票数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("votes").toString()) > 0);
                    //账户地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("address");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                }
            }
        }
    }

    /**
     * constructor.
     * 最佳数据-通证
     * type=7,8,9,10表示查询账户列表数据
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
    public void getTop10_2() {
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("type", "7,8,9,10");
        params.put("time", "1");
        response = TronscanApiList.getTop10(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONArray responseContentArray = TronscanApiList.parseArrayResponseContent(response);
        TronscanApiList.printJsonArrayContent(responseContentArray);
        JSONArray exchangeArray = responseContentArray;
        // list
        Assert.assertTrue(exchangeArray.size() > 0);
        for (int i = 0; i < exchangeArray.size(); i++) {
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("start_time").isEmpty());
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("end_time").isEmpty());
            //type
            String type = exchangeArray.getJSONObject(i).getString("type");
            Assert.assertTrue(!type.isEmpty());
            //data list
            contentArray = exchangeArray.getJSONObject(i).getJSONArray("data");
            Assert.assertTrue(contentArray.size() > 0);
            //最佳通证--持有账户
            if (type.equals("7")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //持有账户数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("holders").toString()) > 0);
                    //logo
                    String logo = contentArray.getJSONObject(i).get("logo").toString();
                    Assert.assertTrue(!logo.isEmpty());
                    if (logo.substring(0, 8) == "https://") {
                        HttpResponse httpResponse = TronscanApiList.getUrlkey(logo);
                        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
                    } else {
                        Assert.assertTrue(contentArray.getJSONObject(i).containsKey("logo"));
                    }
                    //环比不为0
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //通证
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("token_id").toString().isEmpty());
                    //通证名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                }
            }

            //最佳通证--交易账户数
            if (type.equals("8")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //交易账户数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("address_number").toString()) > 0);
                    //logo
                    String logo = exchangeArray.getJSONObject(i).get("logo").toString();
                    Assert.assertTrue(!logo.isEmpty());
                    if (logo.substring(0, 8) == "https://") {
                        HttpResponse httpResponse = TronscanApiList.getUrlkey(logo);
                        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
                    } else {
                        Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("logo"));
                    }
                    //环比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //通证
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("token_id").toString().isEmpty());
                    //通证名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                    //简称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("abbr").toString().isEmpty());
                }
            }

            //最佳通证--交易总笔数
            if (type.equals("9")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //交易总笔数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("transaction_number").toString()) > 0);
                    //logo
                    String logo = exchangeArray.getJSONObject(i).get("logo").toString();
                    Assert.assertTrue(!logo.isEmpty());
                    if (logo.substring(0, 8) == "https://") {
                        HttpResponse httpResponse = TronscanApiList.getUrlkey(logo);
                        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
                    } else {
                        Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("logo"));
                    }
                    //环比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //通证
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("token_id").toString().isEmpty());
                    //通证名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                    //简称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("abbr").toString().isEmpty());
                }
            }
            //最佳通证--交易总额
            if (type.equals("10")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //交易总额
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("amount").toString()) > 0);
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("priceInTrx").toString()) > 0);
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("tokenPrice").toString()) > 0);

                    //logo
                    String logo = exchangeArray.getJSONObject(i).get("logo").toString();
                    Assert.assertTrue(!logo.isEmpty());
                    if (logo.substring(0, 8) == "https://") {
                        HttpResponse httpResponse = TronscanApiList.getUrlkey(logo);
                        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
                    } else {
                        Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("logo"));
                    }
                    //环比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //通证
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("token_id").toString().isEmpty());
                    //通证名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                    //简称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("abbr").toString().isEmpty());
                }
                }
            }

    }

    /**
     * constructor.
     * 最佳数据-资源
     * type=14,15表示查询账户列表数据
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
    public void getTop10_3() {
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("type", "14,15");
        params.put("time", "1");
        response = TronscanApiList.getTop10(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONArray responseContentArray = TronscanApiList.parseArrayResponseContent(response);
        TronscanApiList.printJsonArrayContent(responseContentArray);
        JSONArray exchangeArray = responseContentArray;
        // list
        Assert.assertTrue(exchangeArray.size() > 0);
        for (int i = 0; i < exchangeArray.size(); i++) {
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("start_time").isEmpty());
            Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("end_time").isEmpty());
            //type
            String type = exchangeArray.getJSONObject(i).getString("type");
            Assert.assertTrue(!type.isEmpty());
            //data list
            contentArray = exchangeArray.getJSONObject(i).getJSONArray("data");
            Assert.assertTrue(contentArray.size() > 0);
            //最佳通证--持有账户
            if (type.equals("14")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //消耗燃烧TRX的能量
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("energy_burn").toString()) > 0);
                    //消耗合约部署者提供的能量
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("contract_use").toString()) >= 0);
                    //消耗总能量
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("whole_energy_use").toString()) > 0);
                    //账户地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("address");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //占比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //消耗冻结TRX的能量
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("energy_use").toString()) >= 0);
                    //addressTag
                    Assert.assertTrue(contentArray.getJSONObject(i).containsKey("addressTag"));

                }
            }

            //最佳通证--交易账户数
            if (type.equals("15")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //消耗燃烧TRX的带宽
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("net_burn").toString()) > 0);
                    //消耗总带宽
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("whole_net_use").toString()) > 0);
//                    消耗冻结TRX的带宽
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("net_use").toString()) >= 0);
                    //账户地址
                    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
                    String contract = contentArray.getJSONObject(i).getString("address");
                    Assert.assertTrue(patternAddress.matcher(contract).matches() && !contract.isEmpty());
                    //环比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //addressTag
                    Assert.assertTrue(contentArray.getJSONObject(i).containsKey("addressTag"));
                }
            }

            //最佳通证--交易总笔数
            if (type.equals("9")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //交易总笔数
                    Assert.assertTrue(
                            Long.valueOf(contentArray.getJSONObject(i).get("transaction_number").toString()) > 0);
                    //logo
                    String logo = exchangeArray.getJSONObject(i).get("logo").toString();
                    Assert.assertTrue(!logo.isEmpty());
                    if (logo.substring(0, 8) == "https://") {
                        HttpResponse httpResponse = TronscanApiList.getUrlkey(logo);
                        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
                    } else {
                        Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("logo"));
                    }
                    //环比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //通证
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("token_id").toString().isEmpty());
                    //通证名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                    //简称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("abbr").toString().isEmpty());
                }
            }
            //最佳通证--交易总额
            if (type.equals("10")) {
                for (int j = 0; j < contentArray.size(); j++) {
                    //交易总额
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("amount").toString()) > 0);
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("priceInTrx").toString()) > 0);
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("tokenPrice").toString()) > 0);

                    //logo
                    String logo = exchangeArray.getJSONObject(i).get("logo").toString();
                    Assert.assertTrue(!logo.isEmpty());
                    if (logo.substring(0, 8) == "https://") {
                        HttpResponse httpResponse = TronscanApiList.getUrlkey(logo);
                        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
                    } else {
                        Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("logo"));
                    }
                    //环比
                    Assert.assertTrue(
                            Double.valueOf(contentArray.getJSONObject(i).get("percentage").toString()) > 0);
                    //通证
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("token_id").toString().isEmpty());
                    //通证名称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("name").toString().isEmpty());
                    //简称
                    Assert.assertTrue(!contentArray.getJSONObject(i).get("abbr").toString().isEmpty());
                }
            }
        }

    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }
}
