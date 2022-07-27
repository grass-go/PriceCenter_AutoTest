package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Keys;
import tron.tronlink.base.TronlinkBase;

import java.util.*;

@Slf4j
public class Rick extends TronlinkBase {

    private RiskTokens riskTokens = new RiskTokens();

    @Test(description = "判断risktokens接口返回的token在该接口能否查到")
    public void getRisk(){
        JSONObject tokenObjs = riskTokens.GetAllRiskTokens();
        JSONArray arr = tokenObjs.getJSONArray(Keys.data);
        List<String> tokenStrs = JSONObject.parseArray(arr.toJSONString(), String.class);
        for (String str:
             tokenStrs) {
            Assert.assertEquals(true, isRiskToken(str));

            break;
        }
    }


    private boolean isRiskToken(String token){
        final String url = "/api/wallet/v2/risk";
        final String method = "POST";

        Map<String,String> params = GenerateParams(queryAddress58, url, method);
        Map<String,String> headers = GenerateHeaders();
        JSONObject body = new JSONObject();
        body.put(Keys.Address, queryAddress58);
        HttpResponse response = TronlinkApiList.v2GetRisk(params, body, headers, url);
        Assert.assertNotEquals(response, null);
        JSONObject riskRsp = TronlinkApiList.parseJsonObResponseContent(response);
        log.debug("all risk tokens =" + riskRsp.toJSONString());

        return false;
    }

    private Map<String,String> GenerateParams(String Address, String url, String method){
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
