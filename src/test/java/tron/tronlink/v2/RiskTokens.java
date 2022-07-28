package tron.tronlink.v2;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Keys;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RiskTokens extends TronlinkBase {


    @Test(description = "查询所有的风险tokens")
    public void getRiskTokens(){
        JSONObject tokens = GetAllRiskTokens();
        //todo 断言
    }

    // 对外提供的接口
    public JSONObject GetAllRiskTokens(){
        final String url = "/api/wallet/v2/risktokens";
        Map<String,String> params = GenerateParams(queryAddress58, url, "POST");
        Map<String,String> headers = GenerateHeaders();
        JSONObject body = new JSONObject();
        body.put(Keys.Address, queryAddress58);
        HttpResponse response = TronlinkApiList.v2PostRiskTokens(params, body, headers, url);
        Assert.assertNotEquals(response, null);
        JSONObject riskTokensRsp = TronlinkApiList.parseJsonObResponseContent(response);
        log.debug("all risk tokens =" + riskTokensRsp.toJSONString());
        return riskTokensRsp;
    }

    public Map<String,String> GenerateParams(String Address, String url, String method){
        Map<String,String> params = new HashMap<>();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
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
        log.info("headers : " + headers);
        return headers;
    }

}
