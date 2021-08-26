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

public class ethNewFilter extends V1Base {
    JSONObject getEthNewFilterBody;
    @Test(enabled = true, description = "Eth api of eth_newFilter only contain address.")
    public void test01GetNewFilterContainAddress() {

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


    }


    @Test(enabled = true, description = "Eth api of eth_newFilter only contain topic.")
    public void test02GetNewFilterOnlyContainTopic() {
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_newFilter");
        JsonArray params = new JsonArray();
        JsonObject paramBody = new JsonObject();
        paramBody.addProperty("fromBlock", "0x1f8b6a7");
        paramBody.addProperty("toBlock", "0x1f8b6a7");
        JsonArray topicArray = new JsonArray();
        topicArray.add("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef");
        paramBody.add("topics", topicArray);
        params.add(paramBody);
        ethJsonObj.add("params", params);
        ethJsonObj.addProperty("id", 2);
        System.out.println(ethJsonObj);
        getEthNewFilterBody = getEthApi(ethJsonObj);
        printJsonContent(getEthNewFilterBody);
        Assert.assertNotNull(getEthNewFilterBody.get("result"));

    }

    @Test(enabled = true, description = "Eth api of eth_newFilter both contains topic and address.")
    public void test03GetNewFilterContainsTopicAndAddress() {
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
        JsonArray topicArray = new JsonArray();
        topicArray.add("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef");
        paramBody.add("topics", topicArray);
        params.add(paramBody);
        ethJsonObj.add("params", params);
        ethJsonObj.addProperty("id", 2);
        getEthNewFilterBody = getEthApi(ethJsonObj);
        printJsonContent(getEthNewFilterBody);
        Assert.assertNotNull(getEthNewFilterBody.get("result"));


    }

    @Test(enabled = true, description = "Eth api of eth_newFilter only contain topic and blockHash.")
    public void test04GetNewFilterOnlyContainTopic() {
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_newFilter");
        JsonArray params = new JsonArray();
        JsonObject paramBody = new JsonObject();
        paramBody.addProperty("blockHash", "0x0000000001f8b6a791bc25560940f2fa1d10bd11b949d1aebe01e41cde500f17");
        JsonArray topicArray = new JsonArray();
        topicArray.add("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef");
        paramBody.add("topics", topicArray);
        params.add(paramBody);
        ethJsonObj.add("params", params);
        ethJsonObj.addProperty("id", 2);
        System.out.println(ethJsonObj);
        getEthNewFilterBody = getEthApi(ethJsonObj);
        printJsonContent(getEthNewFilterBody);
        Assert.assertNotNull(getEthNewFilterBody.get("result"));

    }

    @Test(enabled = true, description = "Eth api of eth_newFilter check new block.")
    public void test05GetNewFilterCheckNewBLOCK() {
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_newFilter");
        JsonArray params = new JsonArray();
        JsonObject paramBody = new JsonObject();
        JsonArray topicArray = new JsonArray();
        topicArray.add("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef");
        paramBody.add("topics", topicArray);
        params.add(paramBody);
        ethJsonObj.add("params", params);
        ethJsonObj.addProperty("id", 2);
        System.out.println(ethJsonObj);
        getEthNewFilterBody = getEthApi(ethJsonObj);
        printJsonContent(getEthNewFilterBody);
        Assert.assertNotNull(getEthNewFilterBody.get("result"));
    }



}
