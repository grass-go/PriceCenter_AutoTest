package tron.tronlink.v2;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Keys;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.v2.model.GetRiskTokensRsp;
import tron.tronlink.v2.model.RiskRsp;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RiskTokens extends TronlinkBase {


    @Test(description = "查询所有的风险tokens")
    public void getAllRiskTokens(){
        HttpResponse rsp = GetAllRiskTokensRsp();
        String riskTokensStr = TronlinkApiList.parseResponse2String(rsp);
        GetRiskTokensRsp r = JSONObject.parseObject(riskTokensStr, GetRiskTokensRsp.class);
        Assert.assertEquals(r.getCode(),0);
        Assert.assertEquals(r.getMessage(), "OK");
        Assert.assertNotEquals(r.getData().size(), 0);
    }

    // 对外提供的接口
    public JSONObject GetAllRiskTokens(){
        HttpResponse response = GetAllRiskTokensRsp();
        Assert.assertNotEquals(response, null);
        JSONObject riskTokensRsp = TronlinkApiList.parseResponse2JsonObject(response);

        return riskTokensRsp;
    }

    public HttpResponse GetAllRiskTokensRsp(){
        Map<String,String> params = new HashMap<>();
        params.put("address",commonUser);
        JSONObject body = new JSONObject();
        body.put(Keys.Address, commonUser);
        HttpResponse response = TronlinkApiList.v2RiskTokens(params,body);
        Assert.assertNotEquals(response, null);
        return response;
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
