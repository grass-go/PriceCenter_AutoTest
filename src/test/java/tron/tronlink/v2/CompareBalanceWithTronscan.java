package tron.tronlink.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CompareBalanceWithTronscan extends TronlinkBase {
    private JSONObject TSContent;
    private JSONObject ScanContent;
    private String responseString;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();

    @Test(enabled = true)
    public void CompareBalanceWithTronscan001(){
        //Access to tronlink-server allasset api
        params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        TSContent = TronlinkApiList.parseResponse2JsonObject(response);

        //Access to tronscan api
        String scanURL = TronlinkBase.tronscanApiUrl + "/api/accountv2?address=TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
        response = TronlinkApiList.createGetConnect(scanURL,null,null,null);
        ScanContent = TronlinkApiList.parseResponse2JsonObject(response);

        //check TRX balance
        Object scanTrxBalanceObject = JSONPath.eval(ScanContent,"$.withPriceTokens[0].balance");
        Object tokenTrxDecimalObject = JSONPath.eval(ScanContent,"$.withPriceTokens[0].tokenDecimal");
        Object tsTrxBalanceObject = JSONPath.eval(TSContent,"$.data.token[0].balance");
        log.info("scanTrxBalanceObject:"+scanTrxBalanceObject.toString() + " tokenTrxDecimalObject:"+tokenTrxDecimalObject.toString() + " tsTrxBalanceObject:"+tsTrxBalanceObject);

        int unit = (int)Math.pow(10, Integer.valueOf(tokenTrxDecimalObject.toString()));
        log.info("unit:" + unit);
        BigDecimal scanTrxBalance_bd = new BigDecimal(scanTrxBalanceObject.toString()).divide(BigDecimal.valueOf(unit));
        BigDecimal tsTrxBalance_bd = new BigDecimal(tsTrxBalanceObject.toString());
        log.info("scanTrxBalance_bd: "+scanTrxBalance_bd + "tsTrxBalance_bd:"+ tsTrxBalance_bd );
        Assert.assertEquals(scanTrxBalance_bd,tsTrxBalance_bd);

        JSONArray tokensInfo = ScanContent.getJSONArray("withPriceTokens");
        for (Object tokenInfo : tokensInfo){
            log.info("cur token info: "+tokenInfo.toString() );
            JSONObject token = (JSONObject) JSON.toJSON(tokenInfo);
            String tokenId = token.getString("tokenId");
            if (tokenId.equals("_")){
                continue;
            }

            String scanBalance_str = token.getString("balance");
            int tokenDecimal = token.getIntValue("tokenDecimal");
            double curunit = (double)Math.pow(10, tokenDecimal);
            BigDecimal scanBalance_bd = new BigDecimal(scanBalance_str).divide(BigDecimal.valueOf(curunit),tokenDecimal,BigDecimal.ROUND_HALF_UP);
            log.info("scanBalance_str:"+scanBalance_str + " tokenDecimal:"+tokenDecimal + " curunit:"+curunit + " scanBalance_bd:"+scanBalance_bd);


            String tokenType = token.getString("tokenType");
            Object tsBalanceObject = null;
            if (tokenType.equals("trc10")){
                tsBalanceObject = JSONPath.eval(TSContent,"$.data.token[id='"+tokenId+"'].balance[0]");
            }
            else if (tokenType.equals("trc20")) {
                tsBalanceObject = JSONPath.eval(TSContent,"$.data.token[contractAddress='"+tokenId+"'].balance[0]");
            }
            BigDecimal tsBalance_bd = new BigDecimal(tsBalanceObject.toString()).setScale(tokenDecimal,BigDecimal.ROUND_HALF_UP);
            log.info("scanBalance_bd: "+scanBalance_bd + " tsBalance_bd:"+ tsBalance_bd );
            Assert.assertEquals(scanBalance_bd,tsBalance_bd);
        }
    }
        //check other tokens in withPriceTokens filed in scan api



}



