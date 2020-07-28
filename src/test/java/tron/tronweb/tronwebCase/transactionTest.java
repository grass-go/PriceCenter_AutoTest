package tron.tronweb.tronwebCase;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;

import java.io.IOException;


public class transactionTest extends Base {
    String functionName;


    @Test(enabled = true, description = "Test getTransaction by id ")
    public void test01GetTransaction() throws IOException {
        functionName = "getTransaction ";
        String result = executeJavaScript(transactionDir + functionName + queryQractionId);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertEquals(queryQractionId, jsonObject.getString("txID"));
        Assert.assertEquals("SUCCESS",
                jsonObject.getJSONArray("ret").getJSONObject(0).getString("contractRet"));
        JSONObject rawData = jsonObject.getJSONObject("raw_data");
        Assert.assertEquals(1595833744078L, rawData.getLongValue("timestamp"));
        Assert.assertEquals(1595833797000L, rawData.getLongValue("expiration"));
        Assert.assertEquals("d7ba39131fc4be0d", rawData.getString("ref_block_hash"));
        Assert.assertEquals("7a6d", rawData.getString("ref_block_bytes"));
        Assert.assertEquals("VoteWitnessContract",
                rawData.getJSONArray("contract").getJSONObject(0).getString("type"));
    }

}


