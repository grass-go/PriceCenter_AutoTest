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
        param.put("address",quince_B58); //sophia's address
        param.put("trc20Id","TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG");
        param.put("limit","20");
        param.put("start","0");
        param.put("direction","all");
        param.put("confirm","0");

        int index;

        for(index=0; index<5; index++){
            log.info("Test000TransferTrc721 cur index is " + index);
            response = TronlinkApiList.TransferV2Trc721(param);
            if(response.getStatusLine().getStatusCode() == 200)
            {
                index = 6;
            }
            else {
                Thread.sleep(1000);
                continue;
            }
        }

        Assert.assertEquals(7,index);

        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
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
