package tron.tronweb.tronwebCase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;

import java.io.IOException;


public class blockTest extends Base {
    String functionName;
    Long blockNum;
    String blockId;

    @Test(enabled = true, description = "Test get current block")
    public void test01GetCurrentBlock() throws IOException {
        functionName = "getCurrentBlock ";
        String result = executeJavaScript(blockDir + functionName);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertTrue(jsonObject.containsKey("blockID"));
        blockId = jsonObject.getString("blockID");
        Assert.assertTrue(jsonObject.containsKey("block_header"));
        JSONObject header = jsonObject.getJSONObject("block_header");
        Assert.assertTrue(header.containsKey("raw_data"));
        JSONObject rawData = header.getJSONObject("raw_data");
        Assert.assertTrue(rawData.containsKey("number"));
        blockNum = rawData.getLongValue("number");
        Assert.assertTrue(blockNum > 21740142);
        Assert.assertTrue(rawData.containsKey("txTrieRoot"));
        Assert.assertTrue(rawData.containsKey("witness_address"));
        Assert.assertTrue(rawData.containsKey("parentHash"));
        Assert.assertTrue(rawData.containsKey("timestamp"));
    }

    @Test(enabled = true, description = "Test GetBlock")
    public void test02GetBlockDefault() throws IOException {
        functionName = "getBlock ";
        String result = executeJavaScript(blockDir + functionName);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertTrue(jsonObject.containsKey("blockID"));
        Assert.assertTrue(jsonObject.containsKey("block_header"));
        JSONObject header = jsonObject.getJSONObject("block_header");
        Assert.assertTrue(header.containsKey("raw_data"));
        JSONObject rawData = header.getJSONObject("raw_data");
        Assert.assertTrue(rawData.containsKey("txTrieRoot"));
        Assert.assertTrue(rawData.containsKey("witness_address"));
        Assert.assertTrue(rawData.containsKey("parentHash"));
    }


    @Test(enabled = true, description = "Test get getBlockByNum")
    public void test03GetBlockByNum() throws IOException {
        functionName = "getBlockByNumber ";
        String result = executeJavaScript(blockDir + functionName + blockNum);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertTrue(jsonObject.containsKey("blockID"));
        Assert.assertTrue(jsonObject.containsKey("block_header"));
        JSONObject header = jsonObject.getJSONObject("block_header");
        Assert.assertTrue(header.containsKey("raw_data"));
        JSONObject rawData = header.getJSONObject("raw_data");
        Assert.assertTrue(rawData.containsKey("number"));
        Long num = rawData.getLongValue("number");
        Assert.assertTrue(Long.valueOf(num).equals(blockNum));
        Assert.assertTrue(rawData.containsKey("txTrieRoot"));
        Assert.assertTrue(rawData.containsKey("witness_address"));
        Assert.assertTrue(rawData.containsKey("parentHash"));
        Assert.assertTrue(rawData.containsKey("timestamp"));
    }

    @Test(enabled = true, description = "Test getBlockByHash")
    public void test04GetBlockByHash() throws IOException {
        functionName = "getBlockByHash ";
        String result = executeJavaScript(blockDir + functionName + blockId);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertTrue(jsonObject.containsKey("blockID"));
        Assert.assertTrue(jsonObject.getString("blockID").equals(blockId));
        Assert.assertTrue(jsonObject.containsKey("block_header"));
        JSONObject header = jsonObject.getJSONObject("block_header");
        Assert.assertTrue(header.containsKey("raw_data"));
        JSONObject rawData = header.getJSONObject("raw_data");
        Assert.assertTrue(rawData.containsKey("number"));
        Assert.assertTrue(rawData.containsKey("txTrieRoot"));
        Assert.assertTrue(rawData.containsKey("witness_address"));
        Assert.assertTrue(rawData.containsKey("parentHash"));
        Assert.assertTrue(rawData.containsKey("timestamp"));
    }

    @Test(enabled = true, description = "Test getBlockRange")
    public void test05GetBlockRange() throws IOException {
        functionName = "getBlockRange ";
        String result = executeJavaScript(blockDir + functionName + (blockNum - 3) + " " + blockNum);
        System.out.println(result);
        JSONArray array = JSONObject.parseArray(result);
        Assert.assertEquals(4, array.size());
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Assert.assertTrue(jsonObject.containsKey("blockID"));
            Assert.assertTrue(jsonObject.containsKey("block_header"));
            JSONObject header = jsonObject.getJSONObject("block_header");
            Assert.assertTrue(header.containsKey("raw_data"));
            JSONObject rawData = header.getJSONObject("raw_data");
            Assert.assertTrue(rawData.containsKey("number"));
            Long nu = rawData.getLongValue("number");
            Assert.assertTrue((nu >= blockNum - 3) && (nu <= blockNum));
            Assert.assertTrue(rawData.containsKey("txTrieRoot"));
            Assert.assertTrue(rawData.containsKey("witness_address"));
            Assert.assertTrue(rawData.containsKey("parentHash"));
            Assert.assertTrue(rawData.containsKey("timestamp"));
        }
    }

    @Test(enabled = true, description = "Test getBlockTransactionCount")
    public void test06GetBlockTransactionCount() throws IOException {
        functionName = "getBlockTransactionCount ";
        String result = executeJavaScript(blockDir + functionName + "21740142");
        System.out.println(result);
        Assert.assertEquals("28", result);
    }

}
