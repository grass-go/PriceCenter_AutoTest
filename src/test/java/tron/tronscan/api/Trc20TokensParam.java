package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class Trc20TokensParam {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private JSONArray sidechainsArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.根据创建者获取trc20信息
     */
    @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class,description = "根据创建者获取trc20信息")
    public void getTrc20Tokens() {
        String issuer_addr = "TW5y9tuvgummvvuhfmmBQES7fVUhEdqPHK";
        Map<String, String> params = new HashMap<>();
        params.put("issuer_addr", issuer_addr);
        response = TronscanApiList.getTrc20Tokens(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        targetContent = responseContent.getJSONObject("data");
        responseArrayContent = targetContent.getJSONArray("tokens");
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        Assert.assertTrue(responseObject.containsKey("icon_url"));
        Assert.assertTrue(responseObject.containsKey("symbol"));
        Assert.assertTrue(responseObject.containsKey("total_supply"));
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("contract_address")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("issuer_addr")).matches());
        Assert.assertTrue(responseObject.containsKey("home_page"));
        Assert.assertTrue(responseObject.containsKey("token_desc"));
        Assert.assertTrue(responseObject.containsKey("update_time"));
        Assert.assertTrue(responseObject.containsKey("git_hub"));
        Assert.assertTrue(responseObject.containsKey("decimals"));
        Assert.assertTrue(responseObject.containsKey("name"));
        Assert.assertTrue(responseObject.containsKey("issue_time"));
        Assert.assertTrue(responseObject.containsKey("email"));

        Assert.assertTrue(responseObject.containsKey("white_paper"));
        Assert.assertTrue(responseObject.containsKey("social_media"));
        Assert.assertTrue(responseObject.containsKey("status"));
        //sidechains
        sidechainsArrayContent = responseObject.getJSONArray("sidechains");
        JSONObject sidechainsObject = sidechainsArrayContent.getJSONObject(0);
        Pattern sidechainsAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(sidechainsAddress.matcher(sidechainsObject.getString("mainchain_address")).matches());
        Assert.assertTrue(sidechainsAddress.matcher(sidechainsObject.getString("sidechain_address")).matches());
        Assert.assertTrue(sidechainsObject.containsKey("chainid"));
        Assert.assertTrue(sidechainsObject.containsKey("type"));
    }
    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }

}
