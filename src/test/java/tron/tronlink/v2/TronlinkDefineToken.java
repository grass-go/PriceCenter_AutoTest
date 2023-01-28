package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TronlinkDefineToken extends TronlinkBase {
    private String deployedContract = "TJMZNqLDPLHDMakYxBKonSvd26VdjWHVhP";
    private JSONObject responseContent;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    Map<String, String> params = new HashMap<>();

    @Test(enabled = false)
    public void Case001SearchBeforeAdd(){
        //sync,like delete self token
        params.clear();
        params.put("address",commonUser41);

        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case003Add-sync");
        response = TronlinkApiList.v2TokenSync(params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        //begin to test search
        params.clear();
        params.put("address",commonUser41);
        params.put("keyWord",deployedContract);
        params.put("page","1");
        params.put("count","10");
        params.put("version","v2");
        log.info("Case001SearchBeforeAdd: begin to assert...");
        response = TronlinkApiList.v2SearchAsset(params);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        Object tokenContent=dataContent.getJSONArray("token");
        Assert.assertEquals("[]",tokenContent.toString());

    }
    @Test(enabled = false)
    public void Case002QueryBeforeAdd(){
        //sync at first
        params.clear();
        params.put("address",commonUser41);

        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case003Add-sync");
        response = TronlinkApiList.v2TokenSync(params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        //begin to query
        params.clear();
        params.put("address",commonUser41);

        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case002QueryBeforeAdd");
        response = TronlinkApiList.v2TokenQuery(params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        //test status
        Integer status=dataContent.getInteger("status");
        Integer expect=0;
        Assert.assertEquals(expect,status);
        //test issueAddress,totalSupply,type in assetInfo
        JSONObject assetInfo = dataContent.getJSONObject("assetInfo");
        String issueAddress = assetInfo.getString("issueAddress");
        Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t",issueAddress);
        String totalSupply = assetInfo.getString("totalSupply");
        log.info("totalSupply:"+totalSupply);
        Assert.assertEquals("100000000000000000", totalSupply);
        String type = assetInfo.getString("type");
        log.info("type:"+type);
        Assert.assertEquals("2", type);
    }

    @Test(enabled = false)
    public void Case003Add(){
        //sync
        params.clear();
        params.put("address",commonUser41);

        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case003Add-sync");
        response = TronlinkApiList.v2TokenSync(params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        //add
        params.clear();
        params.put("address",commonUser41);

        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenAddress",deployedContract);
        jsonObject.put("decimal",9);
        jsonObject.put("name","TronlinkServerToken");
        jsonObject.put("symbol","TSToken");
        jsonObject.put("type",2);

        log.info("Case003Add-add");
        response = TronlinkApiList.v2TokenAdd(params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Boolean data = responseContent.getBooleanValue("data");
        Assert.assertTrue(data);
    }

    @Test(enabled = false)
    public void Case004QueryAfterAdd(){
        //first add, no matter add before or not.
        //add
        params.clear();
        params.put("address",commonUser41);

        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenAddress",deployedContract);
        jsonObject.put("decimal",9);
        jsonObject.put("name","TronlinkServerToken");
        jsonObject.put("symbol","TSToken");
        jsonObject.put("type",2);

        log.info("Case003Add-add");
        response = TronlinkApiList.v2TokenAdd(params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        //Query
        params.clear();
        params.put("address",commonUser41);

        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case002QueryBeforeAdd");
        response = TronlinkApiList.v2TokenQuery(params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        //test status
        Integer status=dataContent.getInteger("status");
        Integer expect=4;
        Assert.assertEquals(expect,status);
        //test issueAddress,totalSupply,type in assetInfo
        JSONObject assetInfo = dataContent.getJSONObject("assetInfo");
        String issueAddress = assetInfo.getString("issueAddress");
        Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t",issueAddress);
        String totalSupply = assetInfo.getString("totalSupply");
        log.info("totalSupply:"+totalSupply);
        Assert.assertEquals("100000000000000000", totalSupply);
        String type = assetInfo.getString("type");
        log.info("type:"+type);
        Assert.assertEquals("2", type);

    }

    @Test(enabled = false)
    public void Case005SearchAfterAdd(){
        //query contract address can get result.
        params.clear();
        params.put("address",commonUser41);
        params.put("keyWord",deployedContract);
        params.put("page","1");
        params.put("count","10");
        params.put("version","v2");
        log.info("Case005SearchAfterAdd: begin to assert...");
        response = TronlinkApiList.v2SearchAsset(params);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        Integer actualCount = dataContent.getInteger("count");
        Integer expectedCount = 1;
        Assert.assertEquals(expectedCount, actualCount);

        JSONArray tokenContent=dataContent.getJSONArray("token");
        JSONObject selfToken = tokenContent.getJSONObject(0);
        Assert.assertEquals("2",selfToken.get("type").toString());
        Assert.assertEquals("TronlinkServerToken",selfToken.getString("name"));
        Assert.assertEquals("TSToken",selfToken.getString("shortName"));
        Assert.assertEquals(deployedContract,selfToken.getString("contractAddress"));
        Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t",selfToken.getString("issueAddress"));
        Assert.assertEquals("1",selfToken.get("tokenStatus").toString());
        Assert.assertEquals("true",selfToken.get("transferStatus").toString());
        Assert.assertEquals("4",selfToken.get("matchField").toString());

        //query by name can't get result.
        params.clear();
        params.put("address",commonUser41);
        params.put("keyWord","TronlinkServerToken");
        params.put("page","1");
        params.put("count","10");
        params.put("version","v2");
        log.info("Case005SearchAfterAdd: begin to assert...");
        response = TronlinkApiList.v2SearchAsset(params);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        Assert.assertEquals("0",dataContent.get("count").toString());
    }
    @Test(enabled = false)
    public void Case006assetListAfterAdd(){
        params.clear();
        params.put("address",commonUser41);
        params.put("version","v2");

        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object name = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].name[0]"));
        Assert.assertEquals("TronlinkServerToken",name.toString());
        Object sname = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].shortName[0]"));
        Assert.assertEquals("TSToken",sname.toString());
        Object totalSupply = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].totalSupply[0]"));
        Assert.assertEquals("100000000000000000",totalSupply.toString());
        Object issueAddress = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].issueAddress[0]"));
        Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t",issueAddress.toString());
    }

    @Test(enabled = false)
    public void Case007allAssetAfterAdd(){
        params.clear();
        params.put("address",commonUser41);

        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        Object name = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].name[0]"));
        Assert.assertEquals("TronlinkServerToken",name.toString());
        Object sname = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].shortName[0]"));
        Assert.assertEquals("TSToken",sname.toString());
        Object totalSupply = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].totalSupply[0]"));
        Assert.assertEquals("100000000000000000",totalSupply.toString());
        Object issueAddress = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].issueAddress[0]"));
        Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t",issueAddress.toString());
    }

    @Test(enabled = false)
    public void Case008delAssetAfterAdd(){
        // del self token
        List<String> tokenDelList = new ArrayList<>();
        tokenDelList.clear();
        tokenDelList.add(deployedContract);
        jsonObject.clear();
        jsonObject.put("address",commonUser41);
        jsonObject.put("tokenDel",tokenDelList);

        params.clear();
        params.put("address",commonUser41);

        response = TronlinkApiList.v2DelAsset(params,jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Boolean data = responseContent.getBooleanValue("data");
        Assert.assertTrue(data);

        //query assetlist page
        params.clear();
        params.put("address",commonUser41);
        params.put("version","v2");

        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object name = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].name[0]"));
        Assert.assertNull(name);

        //query AllAssetList
        params.clear();
        params.put("address",commonUser41);

        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        name = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].name[0]"));
        Assert.assertNull(name);
    }

}
