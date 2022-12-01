package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import javax.xml.ws.ServiceMode;
import java.util.HashMap;

@Slf4j
public class transferV2Trc721 extends TronlinkBase {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;

    private HttpResponse response;
    private HashMap<String,String> param = new HashMap<>();

    @Test(enabled = true,description = "check /api/transfer/v2/trc721 api")
    public void Test000TransferV2Trc721() throws Exception {
        //param.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"); //sophia's address
        param.put("address","41E7D71E72EA48DE9144DC2450E076415AF0EA745F");
        param.put("trc20Id","TCtbigstF5sL6nJLe5y3vDucPB8kveJ8nq");
        param.put("limit","20");
        param.put("start","0");
        param.put("direction","0");
        param.put("confirm","0");
        param.put("db_version","1");
        param.put("reverse","true");

        response = TronlinkApiList.TransferV2Trc721(param);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        responseArrayContent = responseContent.getJSONArray("token_transfers");

        //data object
        for (Object json:responseArrayContent) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(jsonObject.containsKey("transaction_id"));
            Assert.assertTrue(jsonObject.containsKey("block_ts"));
            Assert.assertTrue(jsonObject.containsKey("from_address"));
            Assert.assertTrue(jsonObject.containsKey("to_address"));
            Assert.assertTrue(jsonObject.containsKey("block"));
            Assert.assertTrue(jsonObject.containsKey("contract_address"));
            Assert.assertTrue(jsonObject.containsKey("quant"));
            Assert.assertTrue(jsonObject.containsKey("approval_amount"));
            Assert.assertTrue(jsonObject.containsKey("event_type"));
            Assert.assertTrue(jsonObject.containsKey("contract_type"));
            //Assert.assertTrue(jsonObject.containsKey("token_id"));
            Assert.assertTrue(jsonObject.containsKey("confirmed"));
            Assert.assertTrue(jsonObject.containsKey("contractRet"));
            Assert.assertTrue(jsonObject.containsKey("finalResult"));
            Assert.assertTrue(jsonObject.containsKey("tokenInfo"));
            Assert.assertTrue(jsonObject.containsKey("fromAddressIsContract"));
            Assert.assertTrue(jsonObject.containsKey("toAddressIsContract"));
            Assert.assertTrue(jsonObject.containsKey("revert"));
        }
    }
}
