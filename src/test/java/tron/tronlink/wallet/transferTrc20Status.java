package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;

@Slf4j
public class transferTrc20Status extends TronlinkBase {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private HashMap<String,String> param = new HashMap<>();

    @Test(enabled = true,description = "test trc20_status api")
    public void Test000getTrc20_Status() throws Exception {
        param.put("address",quince_B58); //sophia's address
        param.put("db_version","1");
        param.put("direction","0");
        param.put("limit","20");
        param.put("start","0");
        param.put("reverse","true");
        param.put("trc20Id","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        int index;


        for(index=0; index<5; index++){
            log.info("Test000getTrc20_Status cur index is " + index);
            response = TronlinkApiList.apiTransferTrc20Status(param);
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
        responseArrayContent = responseContent.getJSONArray("data");

        //data object
        for (Object json:responseArrayContent) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(jsonObject.containsKey("hash"));
            Assert.assertTrue(jsonObject.containsKey("block_timestamp"));
            Assert.assertTrue(jsonObject.containsKey("from"));
            Assert.assertTrue(jsonObject.containsKey("to"));
            Assert.assertTrue(jsonObject.containsKey("block"));
            Assert.assertTrue(jsonObject.containsKey("amount"));
            Assert.assertTrue(jsonObject.containsKey("contract_address"));
            Assert.assertTrue(jsonObject.containsKey("amount"));
            Assert.assertTrue(jsonObject.containsKey("direction"));
            Assert.assertTrue(jsonObject.containsKey("confirmed"));
            Assert.assertTrue(jsonObject.containsKey("contract_ret"));
            Assert.assertTrue(jsonObject.containsKey("event_type"));
            Assert.assertTrue(jsonObject.containsKey("approval_amount"));
            Assert.assertTrue(jsonObject.containsKey("contract_type"));
            Assert.assertTrue(jsonObject.containsKey("contractType"));
            Assert.assertTrue(jsonObject.containsKey("final_result"));
            Assert.assertTrue(jsonObject.containsKey("revert"));
            Assert.assertTrue(jsonObject.containsKey("issue_address"));
            Assert.assertTrue(jsonObject.containsKey("id"));
            Assert.assertTrue(jsonObject.containsKey("token_name"));
            Assert.assertTrue(jsonObject.containsKey("decimals"));

        }
    }

}
