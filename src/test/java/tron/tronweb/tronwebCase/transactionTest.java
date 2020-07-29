package tron.tronweb.tronwebCase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;

import java.io.IOException;
import java.util.List;


public class transactionTest extends Base {
    String functionName;


    @Test(enabled = true, description = "Test getTransaction by id ")
    public void test01GetTransaction() throws IOException {
        functionName = "getTransaction ";
        String result = executeJavaScript(transactionDir + functionName + queryTractionId);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertEquals(queryTractionId, jsonObject.getString("txID"));
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

    @Test(enabled = true, description = "Test getTransactionFromBlock only block number ")
    public void test02GetTransactionFromBlock() throws IOException {
        functionName = "getTransactionFromBlock ";
        String blockNum = "21740142";
        String result = executeJavaScript(transactionDir + functionName + blockNum);
        JSONArray jsonArray = JSONObject.parseArray(result);
        System.out.println(jsonArray.size());
        Assert.assertEquals(28, jsonArray.size());
    }

    @Test(enabled = true, description = "Test getTransactionFromBlock with block number and transaction index")
    public void test03GetTransactionFromBlockWithIndex() throws IOException {
        functionName = "getTransactionFromBlock ";
        String blockNum = "21740142";
        String index = "1";
        String result = executeJavaScript(transactionDir + functionName + blockNum + " "+ index);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String txid = "6f0644755edfd0926d9bd2d3fcf657a22f021211701efafdb4c023668f19da49";
        Assert.assertEquals(txid, jsonObject.getString("txID"));
        Assert.assertEquals("SUCCESS",
                jsonObject.getJSONArray("ret").getJSONObject(0).getString("contractRet"));
        JSONObject rawData = jsonObject.getJSONObject("raw_data");
        Assert.assertEquals(1595489411071L, rawData.getLongValue("timestamp"));
        Assert.assertEquals(1595489469000L, rawData.getLongValue("expiration"));
        Assert.assertEquals("20a406a3b3d104cc", rawData.getString("ref_block_hash"));
        Assert.assertEquals("ba53", rawData.getString("ref_block_bytes"));
        Assert.assertEquals("VoteWitnessContract",
                rawData.getJSONArray("contract").getJSONObject(0).getString("type"));
    }

    @Test(enabled = true, description = "Test getTransactionInfo")
    public void test04GetTransactionInfo() throws IOException {
        functionName = "getTransactionInfo ";
        String result = executeJavaScript(transactionDir + functionName + queryTractionId );
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertEquals(queryTractionId, jsonObject.getString("id"));
        Assert.assertEquals(21854857L, jsonObject.getLongValue("blockNumber"));
        Assert.assertEquals(1595833767000L, jsonObject.getLongValue("blockTimeStamp"));
        Assert.assertEquals(299, jsonObject.getJSONObject("receipt").getIntValue("net_usage"));
    }

    @Test(enabled = true, description = "Test getApprovedList")
    public void test05GetApprovedList() throws IOException {
        functionName = "getApprovedList ";
        String result = executeJavaScript(transactionDir + functionName );
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertEquals("TN9RRaXkCFtTXRso2GdTZxSxxwufzxLQPP", jsonObject.getObject("approved_list", List.class).get(0));
        Assert.assertTrue(jsonObject.getJSONObject("transaction").getJSONObject("result").getBooleanValue("result"));
        String txid = "ee188aaf5cf78729d2d14d4db698126da2d75ef78a43837dafd6e6f591d103a2";
        Assert.assertEquals(txid, jsonObject.getJSONObject("transaction").getString("txid"));
        Assert.assertTrue(result.contains("\"amount\":125000000"));
        Assert.assertTrue(result.contains("\"owner_address\":\"TN9RRaXkCFtTXRso2GdTZxSxxwufzxLQPP\""));
        Assert.assertTrue(result.contains("\"to_address\":\"TTSFjEG3Lu9WkHdp4JrWYhbGP6K1REqnGQ\""));
        Assert.assertTrue(result.contains("\"type\":\"TransferContract\""));
        Assert.assertTrue(result.contains("\"ref_block_hash\":\"5c685c92bf035e72\""));
        Assert.assertTrue(result.contains("\"ref_block_bytes\":\"c251\""));
    }

}


