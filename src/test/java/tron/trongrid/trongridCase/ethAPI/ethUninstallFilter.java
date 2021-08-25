package tron.trongrid.trongridCase.ethAPI;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

import java.util.Arrays;
import java.util.List;

public class ethUninstallFilter extends V1Base {
    JSONObject getEthNewFilterBody;
    JSONObject uninstallFilterBody;
    JSONObject getFilterChanges;
    JSONObject getEthNewBlockFilter;
    @Test(enabled = true, description = "Eth api of eth_uninstallFilter which method is eth_newFilter and params has one element ")
    public void test01EthUninstallFilter() {
       //生成ID
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_newFilter");
        JsonArray params = new JsonArray();
        JsonObject paramBody = new JsonObject();
        JsonArray addressArray = new JsonArray();
        addressArray.add("A614F803B6FD780986A42C78EC9C7F77E6DED13C");
        paramBody.add("address", addressArray);
        paramBody.addProperty("fromBlock", "0x1f8b6a7");
        paramBody.addProperty("toBlock", "0x1f8b6a7");
        params.add(paramBody);
        ethJsonObj.add("params", params);
        ethJsonObj.addProperty("id", 2);
        System.out.println(ethJsonObj);
        getEthNewFilterBody = getEthApi(ethJsonObj);
        printJsonContent(getEthNewFilterBody);
        Assert.assertNotNull(getEthNewFilterBody.get("result"));

        //验证ID失效
        JsonObject ethJsonObj1 = new JsonObject();
        ethJsonObj1.addProperty("jsonrpc", "2.0");
        ethJsonObj1.addProperty("method", "eth_uninstallFilter");
        JsonArray params1 = new JsonArray();
        params1.add(getEthNewFilterBody.get("result").toString());
        ethJsonObj1.add("params", params1);
        ethJsonObj1.addProperty("id", 2);
        System.out.println("-------4545454545454545454--------");
        System.out.println(ethJsonObj1);
        //第一次eth_uninstallFilter返回true
        uninstallFilterBody = getEthApi(ethJsonObj1);
        printJsonContent(uninstallFilterBody);
        Assert.assertEquals(uninstallFilterBody.get("result"),true);
        //第二次eth_uninstallFilter返回false
        uninstallFilterBody = getEthApi(ethJsonObj1);
        printJsonContent(uninstallFilterBody);
        Assert.assertEquals(uninstallFilterBody.get("result"),false);

        //查询getFilterChanges接口,验证ID已失效
        JsonObject ethJsonObj2 = new JsonObject();
        ethJsonObj2.addProperty("jsonrpc", "2.0");
        ethJsonObj2.addProperty("method", "eth_getFilterChanges");
        JsonArray params2 = new JsonArray();
        params2.add(getEthNewFilterBody.get("result").toString());
        ethJsonObj2.add("params", params2);
        ethJsonObj2.addProperty("id", 2);
        System.out.println("-------333333333333--------");
        System.out.println(ethJsonObj2);
        getFilterChanges=getEthApi(ethJsonObj2);
        printJsonContent(getFilterChanges);
        String expect_result = "{\"code\":-32602,\"data\":\"{}\",\"message\":\"filter not found\"}";
        Assert.assertEquals(getFilterChanges.get("error").toString(),expect_result);

    }

    @Test(enabled = true, description = "Eth api of eth_uninstallFilter which method is eth_newBlockFilter and params has one element ")
    public void test02EthUninstallFilter() {
        //生成ID
        JsonObject ethJsonObj1 = new JsonObject();
        ethJsonObj1.addProperty("jsonrpc","2.0");
        ethJsonObj1.addProperty("method","eth_newBlockFilter");
        ethJsonObj1.add("params",new JsonArray());
        ethJsonObj1.addProperty("id",2);
        //System.out.println(ethJsonObj);
        getEthNewBlockFilter = getEthApi(ethJsonObj1);
        printJsonContent(getEthNewBlockFilter);
         //System.out.println(ethJsonObj1);
        //System.out.println(getEthNewBlockFilter.get("result"));
        Assert.assertNotNull(getEthNewBlockFilter.get("result"));

        //验证ID失效
        JsonObject ethJsonObj2 = new JsonObject();
        ethJsonObj2.addProperty("jsonrpc", "2.0");
        ethJsonObj2.addProperty("method", "eth_uninstallFilter");
        JsonArray params1 = new JsonArray();
        params1.add(getEthNewBlockFilter.get("result").toString());
        ethJsonObj2.add("params", params1);
        ethJsonObj2.addProperty("id", 2);
        System.out.println("-------4545454545454545454--------");
        System.out.println(ethJsonObj2);
        //第一次eth_uninstallFilter返回true
        uninstallFilterBody = getEthApi(ethJsonObj2);
        printJsonContent(uninstallFilterBody);
        Assert.assertEquals(uninstallFilterBody.get("result"),true);
        //第二次eth_uninstallFilter返回false
        uninstallFilterBody = getEthApi(ethJsonObj2);
        printJsonContent(uninstallFilterBody);
        Assert.assertEquals(uninstallFilterBody.get("result"),false);

        //查询getFilterChanges接口,验证ID已失效
        JsonObject ethJsonObj3 = new JsonObject();
        ethJsonObj3.addProperty("jsonrpc", "2.0");
        ethJsonObj3.addProperty("method", "eth_getFilterChanges");
        JsonArray params2 = new JsonArray();
        params2.add(getEthNewFilterBody.get("result").toString());
        ethJsonObj3.add("params", params2);
        ethJsonObj3.addProperty("id", 2);
        System.out.println("-------333333333333--------");
        System.out.println(ethJsonObj3);
        getFilterChanges=getEthApi(ethJsonObj3);
        printJsonContent(getFilterChanges);
        String expect_result = "{\"code\":-32602,\"data\":\"{}\",\"message\":\"filter not found\"}";
        Assert.assertEquals(getFilterChanges.get("error").toString(),expect_result);



    }


}
