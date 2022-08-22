package tron.tronlink.v2;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class white extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject dataContent;
    private HttpResponse response;
    private HashMap<String,String> param = new HashMap<>();
    JSONObject jsonObject = new JSONObject();


    @Test(enabled = true)
    public void test01CheckWhiteStatus() throws Exception {

        param.put("contract_address","TXXD6jwUYdPRcMA3CP6gCWPQPY7LwrD5mZ");
        param.put("nonce","12345");
        param.put("secretId","SFSUIOJBFMLKSJIF");
        param.put("signature","DgRWiagEjwyFqgYOV7Smcqe%2FtsU%3D");
        param.put("address",quince_B58);

        response = TronlinkApiList.v2CheckWhite(param);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        TronlinkApiList.printJsonContent(responseContent);
        Assert.assertTrue(responseContent.getInteger("code") == 0);
        Assert.assertEquals(responseContent.getString("message"),"OK");
        JSONObject dataContent = responseContent.getJSONObject("data");
        Assert.assertEquals("TXXD6jwUYdPRcMA3CP6gCWPQPY7LwrD5mZ", dataContent.getString("contractAddress"));
        Assert.assertEquals("SUN", dataContent.getString("projectName"));
        Assert.assertEquals("sun.io", dataContent.getString("projectUrl"));
        Assert.assertTrue(dataContent.getBooleanValue("white"));

    }


}
