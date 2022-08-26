package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.v2.GetSign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class NodeInfo extends TronlinkBase {
    public static class NodeInfoRsp {
        private int code;
        private String message;
        private List<Data> data;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public List<Data> getData() {
            return data;
        }
    }

    public static class Data {

        private String chainName;
        private String chainId;
        private String sideChainContract;
        private String mainChainContract;
        private List<String> fullNode;
        private List<String> solidityNode;
        private String eventServer;
        private int isMainChain;
        private long serviceTime;

        public void setChainName(String chainName) {
            this.chainName = chainName;
        }

        public String getChainName() {
            return chainName;
        }

        public void setChainId(String chainId) {
            this.chainId = chainId;
        }

        public String getChainId() {
            return chainId;
        }

        public void setSideChainContract(String sideChainContract) {
            this.sideChainContract = sideChainContract;
        }

        public String getSideChainContract() {
            return sideChainContract;
        }

        public void setMainChainContract(String mainChainContract) {
            this.mainChainContract = mainChainContract;
        }

        public String getMainChainContract() {
            return mainChainContract;
        }

        public void setFullNode(List<String> fullNode) {
            this.fullNode = fullNode;
        }

        public List<String> getFullNode() {
            return fullNode;
        }

        public void setSolidityNode(List<String> solidityNode) {
            this.solidityNode = solidityNode;
        }

        public List<String> getSolidityNode() {
            return solidityNode;
        }

        public void setEventServer(String eventServer) {
            this.eventServer = eventServer;
        }

        public String getEventServer() {
            return eventServer;
        }

        public void setIsMainChain(int isMainChain) {
            this.isMainChain = isMainChain;
        }

        public int getIsMainChain() {
            return isMainChain;
        }

        public void setServiceTime(long serviceTime) {
            this.serviceTime = serviceTime;
        }

        public long getServiceTime() {
            return serviceTime;
        }

    }

    private JSONObject responseContent;
    private JSONArray targetData;
    private HttpResponse response;


    @Test(enabled = true, description = "线上环境：nodeinfo结果校验")
    public void test000GetNodeInfo() {
        JSONArray array = new JSONArray();
        JSONObject ob1 = new JSONObject();
        ob1.put(queryAddress58, 2);
        JSONObject ob2 = new JSONObject();
        ob2.put(queryAddressTH48, 2);
        array.add(ob1);
        array.add(ob2);
        log.info(array.toJSONString());
        response = TronlinkApiList.getNodeInfo(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        targetData = responseContent.getJSONArray("data");
        Assert.assertEquals(2, targetData.size());
        for (int i = 0; i < targetData.size(); i++) {
            JSONObject ob = targetData.getJSONObject(i);
            if ("MainChain".equals(ob.getString("chainName"))) {
                Assert.assertEquals("", ob.getString("chainId"));
                Assert.assertEquals("", ob.getString("sideChainContract"));
                Assert.assertEquals("", ob.getString("mainChainContract"));
                Assert.assertEquals("1", ob.getString("isMainChain"));

            } else {
                Assert.assertEquals("DAppChain", ob.getString("chainName"));
                Assert.assertNotEquals("", ob.getString("chainId"));
                Assert.assertNotEquals("", ob.getString("sideChainContract"));
                Assert.assertNotEquals("", ob.getString("mainChainContract"));
                Assert.assertEquals("0", ob.getString("isMainChain"));
            }
            ob.containsKey("serviceTime");
            Assert.assertEquals("", ob.getString("eventServer"));
            Assert.assertTrue(ob.containsKey("fullNode"));
            Assert.assertTrue(ob.getString("fullNode").length() > 0);
            Assert.assertTrue(ob.containsKey("solidityNode"));
            Assert.assertTrue(ob.getString("solidityNode").length() > 0);
        }
    }

    @Test(description = "正确性测试：在chrome版本号大于等于4.0.0，app版本大于等于4.11.0 的时候， 当前接口进行权限校验。")
    public void test001_GetNodeInfo() {
        JSONArray array = getRequestBody();
        Map<String,String> headers = getTest001Headers();
        Map<String,String> params = getTest001Params();
        response = TronlinkApiList.getNodeInfoV2(params,array,headers);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
        log.info(getNodeInfoStr);
        NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
        AssertNodeInfo(nodeInfoRsp);
    }

    private Map<String,String> getTest001Params(){
//        Map<String,String> params = new HashMap<>();
        return g.GenerateParams("", "/api/wallet/node_info","POST");
//        return params;
    }

    GetSign g = new GetSign();

    private Map<String,String> getTest001Headers(){
        Map<String,String> headers;
        headers = TronlinkApiList.getV2Header();
        headers.put("ts", String.valueOf(System.currentTimeMillis()));
        headers.put("Version", "v4.11.0");
        headers.put("env","prod");

        return headers;
    }

    private void AssertNodeInfo(NodeInfoRsp nodeInfoRsp) {
        Assert.assertTrue(nodeInfoRsp.code == 0);
        Assert.assertTrue(nodeInfoRsp.message.equals("OK"));
        Assert.assertTrue(nodeInfoRsp.getData().size() > 0);
        for (Data d:
             nodeInfoRsp.data) {
            Assert.assertTrue(d.getFullNode().size() > 0);
            Assert.assertTrue(d.getSolidityNode().size() > 0);
            Assert.assertTrue(d.getEventServer().equals("") || d.getEventServer().trim().startsWith("http"));
            if(d.getIsMainChain() == 1){
                Assert.assertTrue(d.sideChainContract.equals(""));
                Assert.assertTrue(d.chainName.equals("MainChain"));
                Assert.assertTrue(d.mainChainContract.equals(""));
                Assert.assertTrue(d.chainId.equals(""));
            }
        }
    }

    private JSONArray getRequestBody() {
        JSONArray array = new JSONArray();
        JSONObject ob1 = new JSONObject();
        ob1.put(queryAddress58, 2);
        JSONObject ob2 = new JSONObject();
        ob2.put(queryAddressTH48, 2);
        array.add(ob1);
        array.add(ob2);
        log.info(array.toJSONString());
        return array;
    }


}
