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
public class RiskToken extends TronlinkBase {


    @Test(description = "查询所有的风险tokens")
    public void getRiskTokens(){
        final String url = "/api/wallet/v2/risktokens";
        Map<String,String> params = GenerateParams(queryAddress58, url);
        Map<String,String> headers = GenerateHeaders();
        JSONObject body = new JSONObject();
        body.put(Keys.Address, queryAddress58);

        HttpResponse response = TronlinkApiList.v2PostRiskToken(params, body, headers, url);
        Assert.assertNotEquals(response, null);
        JSONObject assetListRespContent = TronlinkApiList.parseJsonObResponseContent(response);

        log.debug(assetListRespContent.toJSONString());
//        Object usdPrice = JSONPath.eval(assetListRespContent, String.join("","$..data.token[contractAddress='" + token + "'].usdPrice[0]"));
//        BigDecimal up = new BigDecimal((String)usdPrice);
//        log.info("first usd price = " + up);
    }

    private Map<String,String> GenerateParams(String Address, String url){
        Map<String,String> params = new HashMap<>();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        // 计算sig
        HashMap<String,String> sigs = new HashMap<>();
        sigs.put("address", Address);
        sigs.put("url", url);
        sigs.put("method", "POST");
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
