package tron.trongrid.trongridCase.ethAPI;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class ethNewBlockFilter extends V1Base {
    JSONObject getEthNewBlockFilter;
    @Test(enabled = true, description = "Eth api of eth_newBlockFilter")
    public void test01GetEthNewBlockFilter() {
        JsonObject ethJsonObj = new JsonObject();
        ethJsonObj.addProperty("jsonrpc","2.0");
        ethJsonObj.addProperty("method","eth_newBlockFilter");
        ethJsonObj.add("params",new JsonArray());
        ethJsonObj.addProperty("id",2);
        System.out.println(ethJsonObj);
        getEthNewBlockFilter = getEthApi(ethJsonObj);
        printJsonContent(getEthNewBlockFilter);
        System.out.println(ethJsonObj);
        Assert.assertNotNull(getEthNewBlockFilter.get("result"));







    }
}
