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

import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Test(description = "历史请求测试，测试的时候删除db数据或者更换token")
    public void test002GetNodeInfo() {
        JSONArray array = new JSONArray();
        JSONObject ob1 = new JSONObject();
        ob1.put("TBXmEE4txLmdH3yM2yyKHbJM7Qc7EujNrK", 2);
        JSONObject ob2 = new JSONObject();
        ob2.put("TH4Vi2SXuiYCpnWykZgmphEKfajVNbFYA7", 2);
        array.add(ob1);
        array.add(ob2);
        log.info(array.toJSONString());
        response = TronlinkApiList.getNodeInfo(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
        log.info(getNodeInfoStr);
        NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
        AssertNodeInfo(nodeInfoRsp);
    }


    @Test(description = "正确性测试：app版本 >= 4.11.0 的时候， 当前接口进行权限校验。")
    public void test001_GetNodeInfo() {
        for (int i = 0; i < 20; i++) {
            JSONArray array = getRequestBody();
            Map<String, String> headers = getTest001Headers();
            Map<String, String> params = getTest001Params();
            response = TronlinkApiList.getNodeInfoV2(params, array, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
            log.info(getNodeInfoStr);
            NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
            AssertNodeInfo(nodeInfoRsp);
        }
    }

    @Test(description = "异常测试：app版本 < 4.11.0 的时候， 如果不包含System，不返回正确结果。")
    public void test007_GetNodeInfo() {
        for (int i = 0; i < 20; i++) {
            JSONArray array = getRequestBody();
            Map<String, String> headers = getTest007Headers();
            Map<String, String> params = getTestParams();
            response = TronlinkApiList.getNodeInfoV2(params, array, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
        }
    }

    @Test(description = "正确性测试：chrome版本 >= 4.0.0 的时候， 当前接口进行权限校验。")
    public void test002_GetNodeInfo() {
        for (int i = 0; i < 20; i++) {
            JSONArray array = getRequestBody();
            Map<String, String> headers = getTest002Headers();
            Map<String, String> params = getTest002Params();
            response = TronlinkApiList.getNodeInfoV2(params, array, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
            log.info(getNodeInfoStr);
            NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
            AssertNodeInfo(nodeInfoRsp);
        }
    }







    @Test(description = "测试header里面的env，在没有env或者env！=prod的情况下，后端不记录请求里的账户信息(需要观察数据库)")
    public void test003_GetNodeInfo() {
        for (int i = 0; i < 20; i++) {
            JSONArray array = getRequestBody();
            Map<String, String> headers = getTest003Headers();
            Map<String, String> params = getTest003Params();
            response = TronlinkApiList.getNodeInfoV2(params, array, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
            log.info(getNodeInfoStr);
            NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
            AssertNodeInfo(nodeInfoRsp);
        }
    }

    @Test(description = "正确性测试，请求参数里的ts为当前时间的毫秒数，跟服务器时间误差不能超过10分钟。")
    public void test004_GetNodeInfo() {
        for (int i = 0; i < 20; i++) {
            JSONArray array = getRequestBody();
            Map<String, String> headers = getTest004Headers();
            Map<String, String> params = getTestParams();
            response = TronlinkApiList.getNodeInfoV2(params, array, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
            log.info(getNodeInfoStr);
            NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
            AssertNodeInfo(nodeInfoRsp);
        }
    }

    @Test(description = "边界测试，请求参数里的ts为当前时间的毫秒数，跟服务器时间超过10分钟返回错误。")
    public void test005_GetNodeInfo() {
        for (int i = 0; i < 20; i++) {
            JSONArray array = getRequestBody();
            Map<String, String> headers = getTest005Headers();
            Map<String, String> params = getTestParams();
            response = TronlinkApiList.getNodeInfoV2(params, array, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
            log.info(getNodeInfoStr);
            NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
            AssertNotNodeInfo(nodeInfoRsp);
        }
    }

    @Test(description = "正确性测试，system为必传参数")
    public void test006_GetNodeInfo() {
        for (int i = 0; i < 20; i++) {
            JSONArray array = getRequestBody();
            Map<String, String> headers = getTest006Headers();
            Map<String, String> params = getTestParams();
            response = TronlinkApiList.getNodeInfoV2(params, array, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            String getNodeInfoStr = TronlinkApiList.parseResponse2String(response);
            log.info(getNodeInfoStr);
            NodeInfoRsp nodeInfoRsp = JSONObject.parseObject(getNodeInfoStr, NodeInfoRsp.class);
            AssertNotNodeInfo(nodeInfoRsp);
        }
    }



    private Map<String, String> getTest001Params() {
        return g.GenerateParams(queryAddress58, "/api/wallet/node_info", "POST");
    }

    private Map<String, String> getTest002Params() {
        return g.GenerateParams(queryAddress58, "/api/wallet/node_info", "POST");
    }

    private Map<String, String> getTest003Params() {
        return g.GenerateParams(queryAddress58, "/api/wallet/node_info", "POST");
    }

    private Map<String, String> getTestParams() {
        return g.GenerateParams(queryAddress58, "/api/wallet/node_info", "POST");
    }

    GetSign g = new GetSign();
    public static String system;
    public static String ts;
    public static String version;
    public static String method = "POST";

    private Map<String, String> getTest001Headers() {
        Map<String, String> headers;
        headers = TronlinkApiList.getV2Header();
        ts = String.valueOf(System.currentTimeMillis());
        headers.put("ts", ts);
        version = getLimitVersion();
        headers.put("Version", version);
        headers.put("env", "prod");
        system = getAppSystem();
        headers.put("System", system);

        return headers;
    }

    private Map<String, String> getTest007Headers() {
        Map<String, String> headers;
        headers = TronlinkApiList.getV2Header();
        ts = String.valueOf(System.currentTimeMillis());
        headers.put("ts", ts);
        version = getLowLimitVersion();
        headers.put("Version", version);
        headers.put("env", "prod");
        system = getAppSystem();
//        headers.put("System", system);
        headers.remove("System");



        return headers;
    }

    private Map<String, String> getTest003Headers() {
        Map<String, String> headers;
        headers = TronlinkApiList.getV2Header();
        ts = String.valueOf(System.currentTimeMillis());
        headers.put("ts", ts);
        version = getLimitVersion();
        headers.put("Version", version);
        int n = r.nextInt(1000);
        if (n % 2 == 0) {
            headers.put("env", "test");
        }
        system = getAppSystem();
        headers.put("System", system);

        return headers;
    }

    private Map<String, String> getTest004Headers() {
        Map<String, String> headers;
        headers = TronlinkApiList.getV2Header();
        int t = r.nextInt(1000);
        long now;
        if(t %2 ==0) {
            now = System.currentTimeMillis() - 9 * 60 * 1000;
        }else{
            now  = System.currentTimeMillis() + 9 * 60 * 1000;
        }
        ts = String.valueOf(now);
        headers.put("ts", ts);
        version = getLimitVersion();
        headers.put("Version", version);
        int n = r.nextInt(1000);
        if (n % 2 == 0) {
            headers.put("env", "test");
        }else{
            headers.put("env","prod");
        }
        system = getAppSystem();
        headers.put("System", system);

        return headers;
    }

    private Map<String, String> getTest005Headers() {
        Map<String, String> headers;
        headers = TronlinkApiList.getV2Header();
        int t = r.nextInt(1000);
        long now;
        if(t %2 ==0) {
            now = System.currentTimeMillis() - 11 * 60 * 1000;
        }else{
            now  = System.currentTimeMillis() + 11 * 60 * 1000;
        }
        ts = String.valueOf(now);
        headers.put("ts", ts);
        version = getLimitVersion();
        headers.put("Version", version);
        int n = r.nextInt(1000);
        if (n % 2 == 0) {
            headers.put("env", "test");
        }else{
            headers.put("env","prod");
        }
        system = getAppSystem();
        headers.put("System", system);

        return headers;
    }

    private Map<String, String> getTest002Headers() {
        Map<String, String> headers;
        headers = TronlinkApiList.getV2Header();
        ts = String.valueOf(System.currentTimeMillis());
        headers.put("ts", ts);
        version = getChromeLimitVersion();
        headers.put("Version", version);
        headers.put("env", "prod");
        system = getChromeSystem();
        headers.put("System", system);

        return headers;
    }


    private Map<String, String> getTest006Headers() {
        Map<String, String> headers;
        headers = TronlinkApiList.getV2Header();
        int t = r.nextInt(1000);
        long now;
        if(t %2 ==0) {
            now = System.currentTimeMillis() - 9 * 60 * 1000;
        }else{
            now  = System.currentTimeMillis() + 9 * 60 * 1000;
        }
        ts = String.valueOf(now);
        headers.put("ts", ts);
        version = getLimitVersion();
        headers.put("Version", version);
        int n = r.nextInt(1000);
        if (n % 2 == 0) {
            headers.put("env", "test");

        }else{
            headers.put("env","prod");
        }
        system = getAppSystem();

//        if (n % 2 == 0) {
//            headers.put("System", "test"); //不能带错误system
//        }else{
//        }
        return headers;
    }

    private String getLimitVersion() {
        String[] vs = new String[]{"4.11.0", "4.13.4", "5.10.0", "100.1.1", "99.99.1"};
        Random r = new Random();
        return vs[r.nextInt(vs.length)];
    }

    private String getLowLimitVersion() {
        String[] vs = new String[]{"4.10.0", "4.10.99", "3.10.0", "2.1.1", "1.99.1"};
        Random r = new Random();
        return vs[r.nextInt(vs.length)];
    }

    private String getChromeLimitVersion() {
        String[] vs = new String[]{"4.0.0", "4.13.4", "5.10.0", "100.1.1", "99.99.1"};
        Random r = new Random();
        return vs[r.nextInt(vs.length)];
    }

    private String getAppSystem() {
        String[] vs = new String[]{"Android", "iOS", "android", "ios", "androidtest", "IOS", "iostest", "iosTest"};
        Random r = new Random();
        return vs[r.nextInt(vs.length)];

    }

    private String getChromeSystem() {
        String[] vs = new String[]{"chrome", "Chrome", "chrome-extension", "chrome-extension-test", "chrome-extension-tesT", "chrome-Extension"};
        Random r = new Random();
        return vs[r.nextInt(vs.length)];

    }

    private void AssertNodeInfo(NodeInfoRsp nodeInfoRsp) {
        Assert.assertTrue(nodeInfoRsp.code == 0);
        Assert.assertTrue(nodeInfoRsp.message.equals("OK"));
        Assert.assertTrue(nodeInfoRsp.getData().size() > 0);
        for (Data d :
                nodeInfoRsp.data) {
            Assert.assertTrue(d.getFullNode().size() > 0);
            Assert.assertTrue(d.getSolidityNode().size() > 0);
            Assert.assertTrue(d.getEventServer().equals("") || d.getEventServer().trim().startsWith("http"));
            if (d.getIsMainChain() == 1) {
                Assert.assertTrue(d.sideChainContract.equals(""));
                Assert.assertTrue(d.chainName.equals("MainChain"));
                Assert.assertTrue(d.mainChainContract.equals(""));
                Assert.assertTrue(d.chainId.equals(""));
            }
        }
    }

    private void AssertNotNodeInfo(NodeInfoRsp nodeInfoRsp) {
        Assert.assertTrue(nodeInfoRsp.code == 20004);
        Assert.assertTrue(nodeInfoRsp.message.equals("Error param."));
        Assert.assertTrue(nodeInfoRsp.getData() == null);

    }
    Random r = new Random();

    private JSONArray getRequestBody() {
        JSONArray array = new JSONArray();
        JSONObject ob1 = new JSONObject();
        int[] accountTypes = new int[]{1,2,3,8,9,11};
        ob1.put(queryAddress58, accountTypes[r.nextInt(accountTypes.length)]);
        JSONObject ob2 = new JSONObject();
        ob2.put(queryAddressTH48, accountTypes[r.nextInt(accountTypes.length)]);
        array.add(ob1);
        array.add(ob2);
        log.info(array.toJSONString());
        return array;
    }


}
