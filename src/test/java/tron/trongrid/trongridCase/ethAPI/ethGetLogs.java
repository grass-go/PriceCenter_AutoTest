package tron.trongrid.trongridCase.ethAPI;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

import java.util.Arrays;
import java.util.List;



public class ethGetLogs extends V1Base {
    JSONObject getEthLogsBody;

    @Test(enabled = true, description = "Eth api of eth_getLogs only contain address.")
    public void test01GetLogsOnlyContainAddress() {

        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_getLogs");
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
        getEthLogsBody = getEthApi(ethJsonObj);
        //System.out.println(getEthLogsBody);
        printJsonContent(getEthLogsBody);
        List<String> addresses = JsonPath.read(getEthLogsBody, "$.result[*].address");
        Assert.assertEquals(addresses.size(), 20);
        for (String address : addresses) {
            Assert.assertEquals("0xa614f803b6fd780986a42c78ec9c7f77e6ded13c", address);
        }


    }


    @Test(enabled = true, description = "Eth api of eth_getLogs only contain topic.")
    public void test02GetLogsOnlyContainTopic() {
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_getLogs");
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
        getEthLogsBody = getEthApi(ethJsonObj);
        System.out.println(getEthLogsBody);
        printJsonContent(getEthLogsBody);
        List<String> topics = JsonPath.read(getEthLogsBody, "$.result[*].topics[0]");
        System.out.println(Arrays.toString(topics.toArray()));
        Assert.assertEquals(topics.size(), 20);
        for (String topic : topics) {
            Assert.assertEquals("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef", topic);
        }

    }

    @Test(enabled = true, description = "Eth api of eth_getLogs both contains topic and address.")
    public void test03GetLogsContainsTopicAndAddress() {
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc", "2.0");
        ethJsonObj.addProperty("method", "eth_getLogs");
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
        System.out.println(ethJsonObj);
        getEthLogsBody = getEthApi(ethJsonObj);
        printJsonContent(getEthLogsBody);
        List<String> addresses = JsonPath.read(getEthLogsBody, "$.result[*].address");
        Assert.assertEquals(addresses.size(), 20);
        for (String address : addresses) {
            Assert.assertEquals("0xa614f803b6fd780986a42c78ec9c7f77e6ded13c", address);
        }
        List<String> topics = JsonPath.read(getEthLogsBody, "$.result[*].topics[0]");
        //System.out.println(Arrays.toString(topic.toArray()));
        Assert.assertEquals(topics.size(), 20);
        Assert.assertEquals(getEthLogsBody.get("id"), 2);
        for (String topic : topics) {
            Assert.assertEquals("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef", topic);
        }


    }

    @Test(enabled = true, description = "Eth api of eth_getLogs check current block.")
    public void test04GetLogsCheckCurrentBlock() throws InterruptedException {
        Boolean flag = false;
        for (int i = 0; i < 10; i++) {
            flag = true;
            JsonObject ethJsonObj = new JsonObject();
            ethJsonObj.addProperty("jsonrpc", "2.0");
            ethJsonObj.addProperty("method", "eth_getLogs");
            JsonArray params = new JsonArray();
            JsonObject paramBody = new JsonObject();
            JsonArray topicArray = new JsonArray();
            topicArray.add("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef");
            paramBody.add("topics", topicArray);
            params.add(paramBody);
            ethJsonObj.add("params", params);
            ethJsonObj.addProperty("id", 2);
            System.out.println(ethJsonObj);
            getEthLogsBody = getEthApi(ethJsonObj);
            printJsonContent(getEthLogsBody);
            List<String> topics = JsonPath.read(getEthLogsBody, "$.result[*].topics[0]");
            for (String topic : topics) {
                if (!("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef".equals(topic))) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                break;
            }else{
                Thread.sleep(3000);
            }
        }
        Assert.assertTrue(flag);
    }
}
