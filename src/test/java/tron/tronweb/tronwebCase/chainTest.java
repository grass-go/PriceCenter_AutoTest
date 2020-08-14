package tron.tronweb.tronwebCase;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;

import java.io.IOException;


public class chainTest extends Base {
    String functionName;

    @Test(enabled = true, description = "Test getChainParameters")
    public void test01GetChainParameters() throws IOException {
        functionName = "getChainParameters ";
        String result = executeJavaScript(chainDir + functionName);
        System.out.println(result);
        JSONArray jsonArray = JSONObject.parseArray(result);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String key = jsonObject.getString("key");
            if ("getMaintenanceTimeInterval".equals(key)){
                Assert.assertEquals(jsonObject.getLongValue("value"),21600000);
            }else if ("getAccountUpgradeCost".equals(key)){
                Assert.assertEquals(jsonObject.getLongValue("value"),9999000000L);
            }else if ("getCreateAccountFee".equals(key)){
                Assert.assertEquals(jsonObject.getLongValue("value"),100000);
            }else if ("getTransactionFee".equals(key)){
                Assert.assertEquals(jsonObject.getLongValue("value"),10);
            }else if ("getWitnessPayPerBlock".equals(key)){
                Assert.assertEquals(jsonObject.getLongValue("value"),16000000);
            }
        }
    }

    @Test(enabled = true, description = "Test getNodeInfo")
    public void test02GetNodeInfo() throws IOException {
        functionName = "getNodeInfo ";
        String result = executeJavaScript(chainDir + functionName);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertTrue(jsonObject.containsKey("activeConnectCount"));
        Assert.assertTrue(jsonObject.containsKey("beginSyncNum"));
        Assert.assertTrue(jsonObject.containsKey("block"));
        Assert.assertTrue(jsonObject.containsKey("cheatWitnessInfoMap"));
        Assert.assertTrue(jsonObject.containsKey("configNodeInfo"));
        Assert.assertTrue(jsonObject.containsKey("currentConnectCount"));
        Assert.assertTrue(jsonObject.containsKey("passiveConnectCount"));
        Assert.assertTrue(jsonObject.containsKey("peerList"));
        Assert.assertTrue(jsonObject.containsKey("machineInfo"));
        Assert.assertTrue(jsonObject.containsKey("solidityBlock"));
        Assert.assertTrue(jsonObject.containsKey("totalFlow"));
    }

    @Test(enabled = true, description = "Test ListExchanges")
    public void test03ListExchanges() throws IOException {
        functionName = "listExchanges ";
        String result = executeJavaScript(chainDir + functionName);
        System.out.println(result);
        JSONArray jsonArray = JSONObject.parseArray(result);
        JSONObject jsonObject;
        for (int i=0;i<jsonArray.size();i++){
            jsonObject = jsonArray.getJSONObject(i);
            Assert.assertTrue(jsonObject.containsKey("exchange_id"));
            Assert.assertTrue(jsonObject.containsKey("creator_address"));
            Assert.assertTrue(jsonObject.containsKey("create_time"));
            Assert.assertTrue(jsonObject.containsKey("first_token_id"));
            Assert.assertTrue(jsonObject.containsKey("second_token_id"));
        }
    }

    @Test(enabled = true, description = "Test getNodeInfo")
    public void test04ListExchangesPaginated() throws IOException {
        functionName = "listExchangesPaginated ";
        String limit = "2 ";
        String offset = "0 ";
        String result = executeJavaScript(chainDir + functionName + limit + offset);
        System.out.println(result);
        JSONArray jsonArray = JSONObject.parseArray(result);
        Assert.assertEquals(2,jsonArray.size());
        JSONObject jsonObject;
        for (int i=0;i<jsonArray.size();i++){
            jsonObject = jsonArray.getJSONObject(i);
            Assert.assertTrue(jsonObject.containsKey("exchange_id"));
            Assert.assertTrue(jsonObject.containsKey("creator_address"));
            Assert.assertTrue(jsonObject.containsKey("create_time"));
            Assert.assertTrue(jsonObject.containsKey("first_token_id"));
            Assert.assertTrue(jsonObject.containsKey("second_token_id"));
        }
    }

    @Test(enabled = true, description = "Test list nodes")
    public void test05listNodes() throws IOException {
        functionName = "listNodes ";
        String result = executeJavaScript(chainDir + functionName);
        System.out.println(result);
        Assert.assertTrue(result.contains(":18888"));
    }

    @Test(enabled = true, description = "Test list proposals")
    public void test06listProposals() throws IOException {
        functionName = "listProposals ";
        String result = executeJavaScript(chainDir + functionName);
        System.out.println(result);
        JSONArray jsonArray = JSONObject.parseArray(result);
        Assert.assertTrue(jsonArray.size()>0);
        for(Object ob: jsonArray){
            Assert.assertTrue(((JSONObject)ob).containsKey("proposal_id"));
            Assert.assertTrue(((JSONObject)ob).containsKey("proposer_address"));
            Assert.assertTrue(((JSONObject)ob).containsKey("parameters"));
            Assert.assertTrue(((JSONObject)ob).containsKey("expiration_time"));
            Assert.assertTrue(((JSONObject)ob).containsKey("create_time"));
//            Assert.assertTrue(((JSONObject)ob).containsKey("state"));
            if(((JSONObject)ob).getIntValue("proposal_id") == 39){
                Assert.assertEquals("41fcbc93454e116c2213f794d931c03b0943df2633",((JSONObject)ob).getString("proposer_address"));
                Assert.assertEquals(1594552686000L,((JSONObject)ob).getLongValue("create_time"));
                Assert.assertEquals("DISAPPROVED",((JSONObject)ob).getString("state"));
            }
        }
    }

    @Test(enabled = true, description = "Test listSuperRepresentatives")
    public void test07ListSuperRepresentatives() throws IOException {
        functionName = "listSuperRepresentatives ";
        String result = executeJavaScript(chainDir + functionName);
        System.out.println(result);
        JSONArray jsonArray = JSONObject.parseArray(result);
        Assert.assertTrue(jsonArray.size()>27);
        for(Object ob: jsonArray){
            Assert.assertTrue(((JSONObject)ob).containsKey("address"));
            Assert.assertTrue(((JSONObject)ob).containsKey("url"));
            if(((JSONObject)ob).getBooleanValue("isJobs")){
                Assert.assertTrue(((JSONObject)ob).containsKey("voteCount"));
                Assert.assertTrue(((JSONObject)ob).containsKey("totalProduced"));
                Assert.assertTrue(((JSONObject)ob).containsKey("totalMissed"));
                Assert.assertTrue(((JSONObject)ob).containsKey("latestBlockNum"));
                Assert.assertTrue(((JSONObject)ob).containsKey("latestSlotNum"));
            }
        }
    }

    @Test(enabled = true, description = "Test list tokens")
    public void test08ListTokens() throws IOException {
        functionName = "listTokens ";
        String result = executeJavaScript(chainDir + functionName);
//        System.out.println(result);
        JSONArray jsonArray = JSONObject.parseArray(result);
        Assert.assertTrue(jsonArray.size()>20);
        for(Object ob: jsonArray){
            Assert.assertTrue(((JSONObject)ob).containsKey("owner_address"));
            Assert.assertTrue(((JSONObject)ob).containsKey("name"));
            Assert.assertTrue(((JSONObject)ob).containsKey("total_supply"));
            Assert.assertTrue(((JSONObject)ob).containsKey("trx_num"));
            Assert.assertTrue(((JSONObject)ob).containsKey("num"));
            Assert.assertTrue(((JSONObject)ob).containsKey("start_time"));
            Assert.assertTrue(((JSONObject)ob).containsKey("end_time"));
            Assert.assertTrue(((JSONObject)ob).containsKey("description"));
            Assert.assertTrue(((JSONObject)ob).containsKey("url"));
            Assert.assertTrue(((JSONObject)ob).containsKey("id"));
            if (((JSONObject)ob).getIntValue("id") == 1002000){
                Assert.assertEquals(1548000000000L,((JSONObject)ob).getLongValue("start_time"));
                Assert.assertEquals(1,((JSONObject)ob).getIntValue("trx_num"));
                Assert.assertEquals(990000000000000000L,((JSONObject)ob).getLongValue("total_supply"));
                Assert.assertEquals(6,((JSONObject)ob).getIntValue("precision"));
                Assert.assertEquals(1,((JSONObject)ob).getIntValue("num"));
                Assert.assertEquals("BitTorrent",((JSONObject)ob).getString("name"));
                Assert.assertEquals(1548000001000L,((JSONObject)ob).getLongValue("end_time"));
                Assert.assertEquals("OfficialTokenofBitTorrentProtocol",((JSONObject)ob).getString("description"));
                Assert.assertEquals("4137fa1a56eb8c503624701d776d95f6dae1d9f0d6",((JSONObject)ob).getString("owner_address"));
                Assert.assertEquals("BTT",((JSONObject)ob).getString("abbr"));
                Assert.assertEquals("www.bittorrent.com",((JSONObject)ob).getString("url"));
            }
        }
    }

    @Test(enabled = true, description = "Test getExchangeByID")
    public void test09GetExchangeByID() throws IOException {
        functionName = "getExchangeByID ";
        String result = executeJavaScript(chainDir + functionName+ "1");
        System.out.println(result);
        JSONObject object = JSONObject.parseObject(result);
        Assert.assertEquals(1,object.getIntValue("exchange_id"));
        Assert.assertEquals("41f596e85bfd042744f76880979a133da0728679d9",object.getString("creator_address"));
        Assert.assertEquals(1539673398000L,object.getLongValue("create_time"));
        Assert.assertEquals("31303030353634",object.getString("first_token_id"));
        Assert.assertEquals(174,object.getLongValue("first_token_balance"));
        Assert.assertEquals("5f",object.getString("second_token_id"));
        Assert.assertEquals(85199,object.getLongValue("second_token_balance"));
    }

    @Test(enabled = true, description = "Test timeUntilNextVoteCycle")
    public void test09timeUntilNextVoteCycle() throws IOException {
        functionName = "timeUntilNextVoteCycle ";
        String result = executeJavaScript(chainDir + functionName+ "1");
        System.out.println(result);
        Assert.assertTrue(Long.valueOf(result)>=0);
    }

}
