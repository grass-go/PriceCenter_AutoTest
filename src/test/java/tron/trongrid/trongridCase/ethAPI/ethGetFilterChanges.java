package tron.trongrid.trongridCase.ethAPI;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

import java.util.List;

public class ethGetFilterChanges extends V1Base {
    JSONObject getEthNewFilterBody;
    JSONObject getFilterChanges;
    @Test(enabled = true, description = "Eth api of eth_getFilterChanges has less 20 elements.")
    public void test01GetFilterChanges() {
        //生成ID
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_newFilter");
        JsonArray params = new JsonArray();
        JsonObject paramBody = new JsonObject();
        JsonArray addressArray = new JsonArray();
        addressArray.add("23352676A99EF99CCC9D4CB21E90C627FD5181C4");
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

       //eth_getFilterChanges查数

        JsonObject ethJsonObj1 = new JsonObject();
        ethJsonObj1.addProperty("jsonrpc", "2.0");
        ethJsonObj1.addProperty("method", "eth_getFilterChanges");
        JsonArray params1 = new JsonArray();
        params1.add(getEthNewFilterBody.get("result").toString());
        ethJsonObj1.add("params", params1);
        ethJsonObj1.addProperty("id", 2);
        System.out.println(ethJsonObj1);
        //第一页数据
        getFilterChanges = getEthApi(ethJsonObj1);
        printJsonContent(getFilterChanges);
        List<String> topics = JsonPath.read(getFilterChanges, "$.result[*]");
        Assert.assertEquals(topics.size(), 2);



    }
    @Test(enabled = true, description = "Eth api of eth_getFilterChanges has more than  20 elements.")
    public void test02GetFilterChanges() {
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

        //eth_getFilterChanges查数

        JsonObject ethJsonObj1 = new JsonObject();
        ethJsonObj1.addProperty("jsonrpc", "2.0");
        ethJsonObj1.addProperty("method", "eth_getFilterChanges");
        JsonArray params1 = new JsonArray();
        params1.add(getEthNewFilterBody.get("result").toString());
        ethJsonObj1.add("params", params1);
        ethJsonObj1.addProperty("id", 2);
        System.out.println(ethJsonObj1);
        //第一页数据（共46条数据）
        getFilterChanges = getEthApi(ethJsonObj1);
        printJsonContent(getFilterChanges);
        List<String> topics = JsonPath.read(getFilterChanges, "$.result[*]");
        Assert.assertEquals(topics.size(), 20);
        //第二页数据
        getFilterChanges = getEthApi(ethJsonObj1);
        printJsonContent(getFilterChanges);
        List<String> topics1 = JsonPath.read(getFilterChanges, "$.result[*]");
        Assert.assertEquals(topics1.size(), 20);
        //第二页数据（7条数据）
        getFilterChanges = getEthApi(ethJsonObj1);
        printJsonContent(getFilterChanges);
        List<String> topics2 = JsonPath.read(getFilterChanges, "$.result[*]");
        Assert.assertEquals(topics2.size(), 7);

    }
}
