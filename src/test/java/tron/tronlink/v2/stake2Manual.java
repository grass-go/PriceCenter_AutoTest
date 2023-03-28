package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.api.Http;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.sqlite.core.DB;
import org.testng.annotations.Test;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Commons;
import tron.common.DBHelper;
import tron.common.HttpMethed;
import tron.common.TronlinkApiList;
import tron.common.utils.Base58;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class stake2Manual {
    private JSONObject responseContent;
    private JSONObject dataContent;
    private JSONObject object;
    HttpResponse res;
    HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> param = new HashMap<>();

    String address158 = "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
    byte[] address1 = Commons.decode58Check(address158);
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2 = TronlinkApiList.getFinalAddress(key2);
    String address258 = Base58.encode(address2);

    String quince58 = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
    byte[] quince = Commons.decode58Check(quince58);
    String quincekey = "b47e686119f2236f38cd0e8a4fe20f8a7fc5cb4284d36131f447c63857e3dac9";
    String yunli58 = "THDEBkMEcWuee6ZpXF1KAmWs8x5YAzvVch";
    byte[] yunli = Commons.decode58Check(yunli58);
    String yunlikey = "cafcc392b9b5518324728a9c43c7d857d6d2766669177ea7515e92f8918ab106";
    String wqq1key = "8d5c18030466b6ab0e5367154d15c4f6cb46d2fb56a0b552e017d183abd8c255";
    byte[] wqq1 = TronlinkApiList.getFinalAddress(wqq1key);
    String wqq158 = Base58.encode58Check(wqq1);
    String wqq2key = "ee16960c312bb08f691fe011ec81530eb613aa1408aca57d7cf736d82f4383de";
    byte[] wqq2 = TronlinkApiList.getFinalAddress(wqq2key);
    String wqq258 = "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ";
    String wqq3key = "119b1042c75e7b797f8d90f3bdffa829171024e2d9f9539e89531b0fbe93833e";
    byte[] wqq3 = TronlinkApiList.getFinalAddress(wqq3key);
    String wqq358 = "TPmQw8dDLdrH8bTN3u9H1A2XCanbqyE533";
    String v421key = "0ae54d7f31c29aa4162ad37e269d59cefafe81310f7461c4d4c33cbdde4da788";
    byte[] v421 = TronlinkApiList.getFinalAddress(v421key);
    String v42158 = "TPbpoVgFDzkE7Sio9mZWKEC4rv3xWRL22C";
    /*String stakeKey = "72c78ff4e317b8f0f58bc423f899ab00f6f4869c76f671a1d3854a5009438e4c";
    byte[] stake = TronlinkApiList.getFinalAddress(stakeKey);
    String stake58 = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";*/
    String stakeKey = "1a48582162822ada65aa6a4f9acb793bbd7cfee13113e9ed9810d7f34cd154cc";
    byte[] stake = TronlinkApiList.getFinalAddress(stakeKey);
    String stake58 = "TDZSyscZ1hdr1wgKwJa34hQyPxK1vCCBQ6";


    //private String httpnode = "47.252.19.181:8090";
    private String httpnode = "api.trongrid.io";
    //private String httpnode = "47.252.3.238:8090";
    //private String httpnode = "nile.trongrid.io";

    @Test(enabled = true, description = "")
    public void calculateResource() throws Exception {
        String test_user = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
        String test_user_41 = "41E7D71E72EA48DE9144DC2450E076415AF0EA745F";

        List<String> ownerAddressList = new ArrayList<>();
        ownerAddressList.add("TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW");
        ownerAddressList.add("TDZSyscZ1hdr1wgKwJa34hQyPxK1vCCBQ6");
        ownerAddressList.add("TWBSXWw2iBPpxPzEQgUruMZZXAb8P7SWzs");
        //ownerAddressList.add("TQRj3keAZdS9mUWqReMFuYRhAZdEZuPoHc");


        //String test_user = "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo";
        //String test_user_41="412CBAEBC9F5FAB6D610549F22406FFB8B9A04AE50";

        HttpResponse resourceRsp = TronlinkApiList.nodeGetResource(httpnode,test_user_41);
        JSONObject resourceObj = TronlinkApiList.parseResponse2JsonObject(resourceRsp);
        Object TotalNetLimit_obj = JSONPath.eval(resourceObj, "$.TotalNetLimit");
        Object TotalNetWeight_obj = JSONPath.eval(resourceObj, "$.TotalNetWeight");
        Object TotalEnergyLimit_obj = JSONPath.eval(resourceObj, "$.TotalEnergyLimit");
        Object TotalEnergyWeight_obj = JSONPath.eval(resourceObj, "$.TotalEnergyWeight");
        BigDecimal bwPerTRX = new BigDecimal(TotalNetLimit_obj.toString()).divide(new BigDecimal(TotalNetWeight_obj.toString()), 18, java.math.RoundingMode.HALF_UP);
        BigDecimal enyPerTRX = new BigDecimal(TotalEnergyLimit_obj.toString()).divide(new BigDecimal(TotalEnergyWeight_obj.toString()), 18, java.math.RoundingMode.HALF_UP);
        Object NetUsed_obj = JSONPath.eval(resourceObj, "$.NetUsed");
        Object EnergyUsed_obj = JSONPath.eval(resourceObj, "$.EnergyUsed");

        String sql = "select frozen_balance,energy_frozen_balance,frozen_balance_for_bandwidth_v2, frozen_balance_for_energy_v2 from balance_info_trx_7 where account_address=\"" + test_user + "\"";
        String bw_v1_balance = null;
        String egy_v1_balance = null;
        String bw_v2_balance = null;
        String egy_v2_balance = null;
        DBHelper db1 = new DBHelper(sql);
        try {
            ResultSet ret = db1.pst.executeQuery();
            while (ret.next()) {
                bw_v1_balance = ret.getString(1);
                egy_v1_balance = ret.getString(2);
                bw_v2_balance = ret.getString(3);
                egy_v2_balance = ret.getString(4);
                log.info("bw_v1_balance:" + bw_v1_balance + ",egy_v1_frozen_balance:" + egy_v1_balance + ",bw_v2_balance:" + bw_v2_balance + ",egy_v2_balance:" + egy_v2_balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        BigDecimal self_bw_v1 = new BigDecimal(bw_v1_balance).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);
        BigDecimal self_bw_v2 = new BigDecimal(bw_v2_balance).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);
        BigDecimal self_egy_v1 = new BigDecimal(egy_v1_balance).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);
        BigDecimal self_egy_v2 = new BigDecimal(egy_v2_balance).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);
        log.info("bw_v1:" + self_bw_v1.toString() + ",bw_v2:" + self_bw_v2.toString() + ",egy_v1:" + self_egy_v1.toString() + ",egy_v2:" + self_egy_v2.toString());

        param.put("resource", "0");
        param.put("stakeVersion", "1");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Object bw_v1_balance_list = JSONPath.eval(responseContent, "$..fromBalance");
        java.util.ArrayList bw_v1_balances = (java.util.ArrayList) bw_v1_balance_list;
        log.info("bw_v1_balances:" + bw_v1_balances.toString());
        Long D_bw_v1_balance = Long.valueOf(0);
        for (int i = 0; i < bw_v1_balances.size(); i++) {
            D_bw_v1_balance = D_bw_v1_balance + Long.valueOf(bw_v1_balances.get(i).toString());
        }
        BigDecimal bw_v1 = new BigDecimal(D_bw_v1_balance).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);


        param.put("resource", "0");
        param.put("stakeVersion", "2");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Object bw_v2_balance_list = JSONPath.eval(responseContent, "$..fromBalance");
        java.util.ArrayList bw_v2_balances = (java.util.ArrayList) bw_v2_balance_list;
        log.info("bw_v2_balances:" + bw_v2_balances.toString());
        Long D_bw_v2_balance = Long.valueOf(0);
        for (int i = 0; i < bw_v2_balances.size(); i++) {
            D_bw_v2_balance = D_bw_v2_balance + Long.valueOf(bw_v2_balances.get(i).toString());
        }
        BigDecimal bw_v2 = new BigDecimal(D_bw_v2_balance).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);

        param.put("resource", "1");
        param.put("stakeVersion", "1");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Object eny_v1_balance_list = JSONPath.eval(responseContent, "$..fromBalance");
        java.util.ArrayList eny_v1_balances = (java.util.ArrayList) eny_v1_balance_list;
        log.info("eny_v1_balances:" + eny_v1_balances.toString());
        Long D_eny_v1_balance = Long.valueOf(0);
        for (int i = 0; i < eny_v1_balances.size(); i++) {
            D_eny_v1_balance = D_eny_v1_balance + Long.valueOf(eny_v1_balances.get(i).toString());
        }
        BigDecimal eny_v1 = new BigDecimal(D_eny_v1_balance).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);


        param.put("resource", "1");
        param.put("stakeVersion", "2");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Object eny_v2_balance_list = JSONPath.eval(responseContent, "$..fromBalance");
        java.util.ArrayList eny_v2_balances = (java.util.ArrayList) eny_v2_balance_list;
        log.info("eny_v2_balances:" + eny_v2_balances.toString());
        Long D_eny_v2_balance = Long.valueOf(0);
        for (int i = 0; i < eny_v2_balances.size(); i++) {
            D_eny_v2_balance = D_eny_v2_balance + Long.valueOf(eny_v2_balances.get(i).toString());
        }
        BigDecimal eny_v2 = new BigDecimal(D_eny_v2_balance).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, java.math.RoundingMode.DOWN);
        log.info("D_bw_v1:" + bw_v1.toString() + ",D_bw_v2:" + bw_v2.toString() + ",D_egy_v1:" + eny_v1.toString() + ",D_egy_v2:" + eny_v2.toString());

        BigDecimal total_bw = self_bw_v1.add(self_bw_v2).add(bw_v1).add(bw_v2).add(new BigDecimal(1500));
        BigDecimal total_egy = self_egy_v1.add(self_egy_v2).add(eny_v1).add(eny_v2);
        log.info("total bw:" + total_bw.toString() + ",total_egy:" + total_egy.toString());

        BigDecimal total_bw_v1 = self_bw_v1.add(bw_v1);
        BigDecimal total_bw_v2 = self_bw_v2.add(bw_v2);
        BigDecimal total_eng_v1 = self_egy_v1.add(eny_v1);
        BigDecimal total_eny_v2 = self_egy_v2.add(eny_v2);
        log.info("total bw v1:" + total_bw_v1.toString() + ",total_bw_v2:" + total_bw_v2.toString());
        log.info("total eny v1:" + total_eng_v1.toString() + ",total_eny_v2:" + total_eny_v2.toString());


        BigDecimal NetUsed = BigDecimal.ZERO;
        BigDecimal EnergyUsed = BigDecimal.ZERO;
        if (NetUsed_obj != null) {
            NetUsed = new BigDecimal(NetUsed_obj.toString());
        }
        if (EnergyUsed_obj != null) {
            EnergyUsed = new BigDecimal(EnergyUsed_obj.toString());
        }

        BigDecimal bw_useRate_v2 = BigDecimal.ZERO;
        BigDecimal eny_useRate_v2 = BigDecimal.ZERO;
        bw_useRate_v2 = NetUsed.divide(total_bw.subtract(new BigDecimal(1500)), 18, java.math.RoundingMode.DOWN);
        eny_useRate_v2 = EnergyUsed.divide(total_egy, 18, java.math.RoundingMode.DOWN);


        log.info("bw_useRate_v2:" + bw_useRate_v2.toString() + ", eny_useRate_v2:" + eny_useRate_v2.toString());

        log.info("========================================================================================");

        /*param.clear();
        param.put("resource","0");
        param.put("lockType","true");
        res = TronlinkApiList.delegateAddress(ownerAddress,param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Object accountAddress = JSONPath.eval(responseContent,"$.data.accountAddress[0]");*/

        for (String ownerAddress : ownerAddressList) {
            /*param.put("resource", "0");
            param.put("lockType", "false");
            res = TronlinkApiList.delegateAddress(ownerAddress, param);
            responseContent = TronlinkApiList.parseResponse2JsonObject(res);*/

            param.put("resource","1");
            param.put("lockType","true");
            res = TronlinkApiList.delegateAddress(ownerAddress,param);
            responseContent = TronlinkApiList.parseResponse2JsonObject(res);


            param.put("resource", "1");
            param.put("lockType", "false");
            res = TronlinkApiList.delegateAddress(ownerAddress, param);
            responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        }


    }


    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void totalAddress() throws Exception {
        //String test_user = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";
        //String test_user = "TGysBYxv1YWF87wqiS4NtysxBPDShsQ6gs";
        String test_user = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
        //String test_user = "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ";
        //String test_user = "TQUsaH7DzTAPQEVsUvQsVyzvwqwT2p7WEm";
        //String test_user = "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo";
        //String test_user = stake58;

        res = TronlinkApiList.totalAddress(test_user);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Object accountAddress = JSONPath.eval(responseContent, "$.data.accountAddress[0]");
        Assert.assertEquals(test_user, accountAddress.toString());
        Object stakeTotalBalance_obj = JSONPath.eval(responseContent, "$.data.stakeTotalBalance[0]");
        Object stakeEnergyBalance_obj = JSONPath.eval(responseContent, "$.data.stakeEnergyBalance[0]");
        Object stakeBandwidthBalance_obj = JSONPath.eval(responseContent, "$.data.stakeBandwidthBalance[0]");
        Object stakeExpireBalance_obj = JSONPath.eval(responseContent, "$.data.stakeExpireBalance[0]");
        Object stakeFreeBalance_obj = JSONPath.eval(responseContent, "$.data.stakeFreeBalance[0]");
        Object freezeTotalBalance_obj = JSONPath.eval(responseContent, "$.data.freezeTotalBalance[0]");


        Long clc_total = Long.valueOf(stakeEnergyBalance_obj.toString()) + Long.valueOf(stakeBandwidthBalance_obj.toString())
                + Long.valueOf(stakeExpireBalance_obj.toString()) + Long.valueOf(stakeFreeBalance_obj.toString())
                + Long.valueOf(freezeTotalBalance_obj.toString());
        Assert.assertEquals(clc_total, Long.valueOf(stakeTotalBalance_obj.toString()));
        if (!(Long.valueOf(stakeExpireBalance_obj.toString()) == Long.valueOf(0))) {
            Object unstakeBalances_obj = JSONPath.eval(responseContent, "$.data.unStakeList[*].balance");
            log.info("unstakeBalances_obj:" + unstakeBalances_obj.toString());
            com.alibaba.fastjson.JSONArray unstakeBalances = (com.alibaba.fastjson.JSONArray) unstakeBalances_obj;
            Long cal_unstake = Long.valueOf(0);
            for (int i = 0; i < unstakeBalances.size(); i++) {
                cal_unstake = cal_unstake + Long.valueOf(unstakeBalances.get(i).toString());
            }
            Assert.assertEquals(cal_unstake, Long.valueOf(stakeExpireBalance_obj.toString()));
        }


        param.put("resource","1");
        param.put("lockType","false");
        res = TronlinkApiList.delegateAddress(test_user,param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);

        param.put("resource","1");
        param.put("lockType","true");
        res = TronlinkApiList.delegateAddress(test_user,param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);

        param.put("resource","0");
        param.put("lockType","false");
        res = TronlinkApiList.delegateAddress(test_user,param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);

        param.put("resource","0");
        param.put("lockType","true");
        res = TronlinkApiList.delegateAddress(test_user,param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);



    }

    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void fromAddress() throws Exception {
        //String test_user = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";
        //String test_user = "TGysBYxv1YWF87wqiS4NtysxBPDShsQ6gs";
        String test_user = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"; //41E7D71E72EA48DE9144DC2450E076415AF0EA745F
        //String test_user = "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo";


        HttpResponse resourceRsp = TronlinkApiList.nodeGetResource(httpnode,"41E7D71E72EA48DE9144DC2450E076415AF0EA745F");
        JSONObject resourceObj = TronlinkApiList.parseResponse2JsonObject(resourceRsp);
        Object TotalNetLimit_obj = JSONPath.eval(resourceObj, "$.TotalNetLimit");
        Object TotalNetWeight_obj = JSONPath.eval(resourceObj, "$.TotalNetWeight");
        Object TotalEnergyLimit_obj = JSONPath.eval(resourceObj, "$.TotalEnergyLimit");
        Object TotalEnergyWeight_obj = JSONPath.eval(resourceObj, "$.TotalEnergyWeight");
        BigDecimal bwPerTRX = new BigDecimal(TotalNetLimit_obj.toString()).divide(new BigDecimal(TotalNetWeight_obj.toString()), 18, java.math.RoundingMode.HALF_UP);
        BigDecimal enyPerTRX = new BigDecimal(TotalEnergyLimit_obj.toString()).divide(new BigDecimal(TotalEnergyWeight_obj.toString()), 18, java.math.RoundingMode.HALF_UP);

        //============================================
        param.put("resource", "1");
        param.put("stakeVersion", "1");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        JSONArray users = responseContent.getJSONArray("data");
        for (int i = 0; i < users.size(); i++) {
            JSONObject userObject = (JSONObject) users.get(i);
            String resourceType = userObject.getString("resource");
            String fromBalance = userObject.getString("fromBalance");
            String fromResourceBalance = userObject.getString("fromResourceBalance");
            BigDecimal clc_resource = BigDecimal.ZERO;
            if (resourceType.equals("1")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            } else if (resourceType.equals("0")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            }
            log.info("fromAddress:fromResourceBalance:" + fromResourceBalance + ", clc_resource:" + clc_resource.toString());
            Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(clc_resource.toString(), fromResourceBalance, "0.00000001"));
        }
        //============================================
        param.put("resource", "0");
        param.put("stakeVersion", "1");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        users = responseContent.getJSONArray("data");
        for (int i = 0; i < users.size(); i++) {
            JSONObject userObject = (JSONObject) users.get(i);
            String resourceType = userObject.getString("resource");
            String fromBalance = userObject.getString("fromBalance");
            String fromResourceBalance = userObject.getString("fromResourceBalance");
            BigDecimal clc_resource = BigDecimal.ZERO;
            if (resourceType.equals("1")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            } else if (resourceType.equals("0")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            }
            log.info("fromAddress:fromResourceBalance:" + fromResourceBalance + ", clc_resource:" + clc_resource.toString());
            Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(clc_resource.toString(), fromResourceBalance, "0.00000001"));
        }
        //============================================
        param.put("resource", "1");
        param.put("stakeVersion", "2");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        /*users = responseContent.getJSONArray("data");
        for (int i = 0; i < users.size(); i++) {
            JSONObject userObject = (JSONObject) users.get(i);
            String resourceType = userObject.getString("resource");
            String fromBalance = userObject.getString("fromBalance");
            String fromResourceBalance = userObject.getString("fromResourceBalance");
            BigDecimal clc_resource = BigDecimal.ZERO;
            if (resourceType.equals("1")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            } else if (resourceType.equals("0")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            }
            log.info("fromAddress:fromResourceBalance:" + fromResourceBalance + ", clc_resource:" + clc_resource.toString());
            Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(clc_resource.toString(), fromResourceBalance, "0.00000001"));
        }*/

        //============================================
        param.put("resource", "0");
        param.put("stakeVersion", "2");
        res = TronlinkApiList.fromAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        /*users = responseContent.getJSONArray("data");
        for (int i = 0; i < users.size(); i++) {
            JSONObject userObject = (JSONObject) users.get(i);
            String resourceType = userObject.getString("resource");
            String fromBalance = userObject.getString("fromBalance");
            String fromResourceBalance = userObject.getString("fromResourceBalance");
            BigDecimal clc_resource = BigDecimal.ZERO;
            if (resourceType.equals("1")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(enyPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            } else if (resourceType.equals("0")) {
                clc_resource = new BigDecimal(fromBalance.toString()).multiply(bwPerTRX).divide(new BigDecimal(1000000), 0, RoundingMode.DOWN);
            }
            log.info("fromAddress:fromResourceBalance:" + fromResourceBalance + ", clc_resource:" + clc_resource.toString());
            Assert.assertTrue(TronlinkApiList.CompareGapInGivenTolerance(clc_resource.toString(), fromResourceBalance, "0.00000001"));
        }*/
    }


    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void delegateAddress() throws Exception {
        //String test_user = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";
        //String test_user = "TGysBYxv1YWF87wqiS4NtysxBPDShsQ6gs";
        String test_user = quince58;
        param.put("resource", "1");
        param.put("lockType", "true");
        res = TronlinkApiList.delegateAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Object pageIndex_obj = JSONPath.eval(responseContent, "$.data.pageIndex[0]");
        while(pageIndex_obj!=null && Integer.valueOf(pageIndex_obj.toString())!=0){
            String pageIndex = pageIndex_obj.toString();
            param.put("pageIndex", pageIndex);
            res = TronlinkApiList.delegateAddress(test_user, param);
            responseContent = TronlinkApiList.parseResponse2JsonObject(res);
            pageIndex_obj = JSONPath.eval(responseContent, "$.data.pageIndex[0]");
            log.info(pageIndex_obj.toString());
        }
        param.clear();
        param.put("resource", "1");
        param.put("lockType", "false");
        res = TronlinkApiList.delegateAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);

        param.clear();
        param.put("resource", "0");
        param.put("lockType", "true");
        res = TronlinkApiList.delegateAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);

        param.clear();
        param.put("resource", "0");
        param.put("lockType", "false");
        res = TronlinkApiList.delegateAddress(test_user, param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);



    }


    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void freezeBalanceV2() throws Exception {
        // visible:true
        HttpResponse bandwidth_visibleT_rsp = HttpMethed.freezeBalanceV2BroadCast(httpnode, stake58, 300100000L, 0, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(bandwidth_visibleT_rsp);
        HttpResponse energy_visibleT_rsp = HttpMethed.freezeBalanceV2BroadCast(httpnode, stake58, 200500000L, 1, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(energy_visibleT_rsp);
    }

    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void UnfreezeBalanceV2() throws Exception {
        HttpResponse bandwidth_visibleT_resp = HttpMethed.unfreezeBalanceV2BroadCast(httpnode, stake58, 2600000L, 0, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(bandwidth_visibleT_resp);
        HttpResponse energy_visibleT_resp = HttpMethed.unfreezeBalanceV2BroadCast(httpnode, stake58, 2700000L, 1, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(energy_visibleT_resp);

    }

    @Test(enabled = false, description = "multi sign freeze balance with serializable no receiver address")
    public void WithdrawExpireUnfreeze() {
        // visible:true
        HttpResponse visibleT_resp = HttpMethed.WithdrawExpireUnfreezeBroadcast(httpnode, quince58, "true", quincekey);
        TronlinkApiList.parseResponse2JsonObject(visibleT_resp);

        HttpResponse visibleF_resp = HttpMethed.WithdrawExpireUnfreezeBroadcast(httpnode, ByteArray.toHexString(quince), "false", quincekey);
        TronlinkApiList.parseResponse2JsonObject(visibleF_resp);
    }

    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void DelegateResource() {
        /*HttpResponse bandwdth_visibleT_resp = HttpMethed.DelegateResourceBroadcast(httpnode, stake58, "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", 10100000L, 0, true, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(bandwdth_visibleT_resp);
        HttpResponse energy_visibleT_resp = HttpMethed.DelegateResourceBroadcast(httpnode, stake58, "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", 10100000L, 1, true, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(energy_visibleT_resp);
        HttpResponse bandwdth_visibleT_lockF_resp = HttpMethed.DelegateResourceBroadcast(httpnode, stake58, "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", 22200000L, 0, false, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(bandwdth_visibleT_lockF_resp);
        */

        HttpResponse energy_visibleT_lockF_resp = HttpMethed.DelegateResourceBroadcast(httpnode, stake58, "TGQVLckg1gDZS5wUwPTrPgRG4U8MKC4jcP", 1000000L, 1, false, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(energy_visibleT_lockF_resp);
    }

    @Test(enabled = true, threadPoolSize = 1,invocationCount = 1)
    public void DelegateResourcePerf() {
        List<String> transactionsList = new ArrayList<>();
        List<String> userList = new ArrayList<>();
        File file = new File("/Users/wqq/Text/test_stake2.0/account_info_10.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                String[] accounts = tempString.split("\t");
                String curAddress = accounts[0];
                userList.add(curAddress);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i<10; i++) {
            int userIndex = (int) (Math.random() * 10);
            long amount = (long) userIndex + 1000000;
            String tx = HttpMethed.MakeDelegateResourceTX(httpnode, stake58, userList.get(userIndex), amount, 1, false, "true", stakeKey);
            transactionsList.add(tx);
        }

        for(String curTx : transactionsList){

            HttpResponse txResp = HttpMethed.broadcastTransaction(httpnode, curTx);
            TronlinkApiList.parseResponse2JsonObject(txResp);
        }

    }

    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void UnDelegateResource() throws Exception {
        HttpResponse bandwdth_visibleT_tx = HttpMethed.UnDelegateResourceBroadcast(httpnode, stake58,quince58, 1500000L,0, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(bandwdth_visibleT_tx);
        HttpResponse energy_visibleT_tx = HttpMethed.UnDelegateResourceBroadcast(httpnode, stake58, quince58, 2500000L, 1, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(energy_visibleT_tx);
        bandwdth_visibleT_tx = HttpMethed.UnDelegateResourceBroadcast(httpnode, stake58,"TEmXqsFgXGUC6pWKXxaWSnQBnaGcMCM6dx", 1500000L,0, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(bandwdth_visibleT_tx);
        energy_visibleT_tx = HttpMethed.UnDelegateResourceBroadcast(httpnode, stake58, "TEmXqsFgXGUC6pWKXxaWSnQBnaGcMCM6dx", 2500000L, 1, "true", stakeKey);
        TronlinkApiList.parseResponse2JsonObject(energy_visibleT_tx);

    }


    @Test(enabled = true, description = "test more user")
    public void DelegateResourceToOneUser() throws Exception {
        File file = new File("/Users/wqq/Text/test_stake2.0/account_info.txt");
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                String[] accounts = tempString.split("\t");
                String curAddress = accounts[0];
                String curkey = accounts[1];

                HttpResponse sendcoinResp = HttpMethed.sendCoinBroadCast(httpnode, quince58, curAddress, 400000000L, "true", quincekey);
                TronlinkApiList.parseResponse2JsonObject(sendcoinResp);
                Thread.sleep(3000);

                HttpResponse bandwidth_visibleT_rsp = HttpMethed.freezeBalanceV2BroadCast(httpnode, curAddress, 200000000L, 0, "true", curkey);
                TronlinkApiList.parseResponse2JsonObject(bandwidth_visibleT_rsp);
                HttpResponse energy_visibleT_rsp = HttpMethed.freezeBalanceV2BroadCast(httpnode, curAddress, 150000000L, 1, "true", curkey);
                TronlinkApiList.parseResponse2JsonObject(energy_visibleT_rsp);

                Thread.sleep(6000);
                HttpResponse bandwdth_visibleT_lockF_resp = HttpMethed.DelegateResourceBroadcast(httpnode, curAddress, quince58, 180000000L, 0, false, "true", curkey);
                TronlinkApiList.parseResponse2JsonObject(bandwdth_visibleT_lockF_resp);

                HttpResponse energy_visibleT_lockF_resp = HttpMethed.DelegateResourceBroadcast(httpnode, curAddress, quince58, 130000000L, 1, false, "true", curkey);
                TronlinkApiList.parseResponse2JsonObject(energy_visibleT_lockF_resp);

            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test(enabled = true, description = "test more user")
    public void OneUserDelegateResourceMoreUser() throws Exception {
        File file = new File("/Users/wqq/Text/test_stake2.0/account_info_31.txt");
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                String[] accounts = tempString.split("\t");
                String curAddress = accounts[0];
                String curkey = accounts[1];

                HttpResponse bandwdth_visibleT_lockF_resp = HttpMethed.DelegateResourceBroadcast(httpnode, quince58,curAddress, 1000000L, 0, false, "true", quincekey);
                TronlinkApiList.parseResponse2JsonObject(bandwdth_visibleT_lockF_resp);

                /*HttpResponse energy_visibleT_lockF_resp = HttpMethed.DelegateResourceBroadcast(httpnode, quince58,curAddress, 1000000L, 1, false, "true", quincekey);
                TronlinkApiList.parseResponse2JsonObject(energy_visibleT_lockF_resp);
*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


