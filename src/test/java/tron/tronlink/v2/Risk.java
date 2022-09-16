package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Keys;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.v2.model.RiskRsp;

import java.util.*;

@Slf4j
public class Risk extends TronlinkBase {

    private RiskTokens riskTokens = new RiskTokens();
    private final Random ramdom = new Random();

    @Test(description = "判断risktokens接口返回的token在该接口能否查到")
    public void getRisk() throws InterruptedException {
        JSONObject tokenObjs = riskTokens.GetAllRiskTokens();
        JSONArray arr = tokenObjs.getJSONArray(Keys.data);
        List<String> tokenStrs = JSONObject.parseArray(arr.toJSONString(), String.class);

        // 挑选10-20条数据然后请求
        List<String> pickTokens = generateRandomTokens(tokenStrs);
        for (String str:
             pickTokens) {
            Assert.assertEquals( isRiskToken(str),true);
            Thread.sleep(1 * 1000);
        }

    }

    @Test(description = "判断错误的token在该接口能否查到")
    public void getRisk02(){
        // 构造错误的地址
        List<String> errTokens = generateErrTokens();
        for (String str:
                errTokens) {
            Assert.assertEquals( isRiskToken(str),false);
        }
    }

    // 测试数据：rsp边缘数据+随机抽取数据
    private List<String> generateRandomTokens(List<String> tokens){
        int totalTokenNum = 5;
        Assert.assertTrue(tokens.size() > totalTokenNum);
        List<String> result = tokens.subList(0, 5);
        result.addAll(tokens.subList(tokens.size() - 5,tokens.size()));
        for (int i = 0; i < 5; i++) {
            result.add(tokens.get(ramdom.nextInt(tokens.size())));
        }

        log.info("prepare get rick token correct data: " +result);
        return  result;
    }

    private List<String> generateErrTokens(){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String tmp = UUID.randomUUID().toString().replace("-", "");
            if(tmp.length()> 35) {
                result.add(tmp.substring(0, 35).toUpperCase());
            }
        }
        log.info("prepare get rick token err data: " +result);
        return  result;

    }

    @Test(description = "测试单个case")
    public void testRiskToken(){
        String token = "TJjJ5kmJcZfNay5pJUA1Er8PUHnFFFJ7QX";
        boolean result = isRiskToken(token);
        Assert.assertEquals(result, true);
    }


    private boolean isRiskToken(String token){
        final String url = "/api/wallet/v2/risk";
        final String method = "GET";

        //Map<String,String> params = GenerateParams(queryAddress58,token, url, method);
        //Map<String,String> headers = GenerateHeaders();
        Map<String,String> params =new HashMap<>();
        params.put("address",queryAddress58);
        params.put("tokenAddress", token);
        HttpResponse response = TronlinkApiList.v2GetRisk(params);
        Assert.assertNotEquals(response, null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String riskStr = TronlinkApiList.parseResponse2String(response);
        log.debug("all risk tokens =" + riskStr);
        RiskRsp rsp = JSONObject.parseObject(riskStr, RiskRsp.class);
        if(rsp.getCode() != 0){
            log.error("rsp = " + JSONObject.toJSONString(rsp));
            return false;
        }
        return true;
    }

    private Map<String,String> GenerateParams(String Address,String token, String url, String method){
        Map<String,String> params = new HashMap<>();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("tokenAddress", token);
        // 计算sig
        HashMap<String,String> sigs = new HashMap<>();
        sigs.put("address", Address);
        sigs.put("url", url);
        sigs.put("method", method);
        try {
            String sig = getSign(sigs);
            params.put("signature",sig);
        }catch (Exception e){
            log.error("sig 计算错误！");
            e.printStackTrace();
        }
        params.put(Keys.Address, Address);
        return params;
    }

    private Map<String, String> GenerateHeaders(){
        Map<String,String> headers = TronlinkApiList.getV2Header();
        headers.put("deviceName", "wqq");
        headers.put("osVersion", "10");
        headers.remove("Connection");

        log.info("headers : " + headers);
        return headers;
    }
}
