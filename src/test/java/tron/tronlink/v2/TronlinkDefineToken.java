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
    private String deployedContract = "TJZDr4GzYKtoBdCRARnrNkppgDXoiMY2oN";
    private JSONObject responseContent;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    Map<String, String> params = new HashMap<>();
    Map<String, String> header = new HashMap<>();

    @Test(enabled = true)
    public void Case001SyncAndSearch(){

        String deployedContract = "TRBXukyS8hpiAUmeCiF96aM5zsHfrrNMHL";
        //sync,like delete self token
        params.clear();
        params.put("address",quince_B58);

        header.clear();
        header.put("System","Android");
        header.put("Version","4.12.9");

        jsonObject.clear();
        jsonObject.put("address",quince_B58);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case003Add-sync");
        response = TronlinkApiList.v2TokenSync(header, params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);



        //begin to test search
        params.clear();
        params.put("address",quince_B58);
        params.put("keyWord",deployedContract);
        params.put("page","1");
        params.put("count","10");
        params.put("version","v2");
        log.info("Case001SearchBeforeAdd: begin to assert...");
        response = TronlinkApiList.v2SearchAsset(params);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        /*Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        Object tokenContent=dataContent.getJSONArray("token");
        Assert.assertEquals("[]",tokenContent.toString());*/

    }



    //低版本必须带，高版本可带可不带。
    @Test(enabled = true)
    public void Case002QueryBeforeAdd(){
        String deployedContract = "TKM7w4qFmkXQLEF2MgrQroBYpd5TY7i1pq";
        //begin to query
        params.clear();
        //params.put("address",quince_B58);

        header.clear();
        header.put("System","iOS");
        header.put("Version","v4.12.0");
        jsonObject.clear();
        jsonObject.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case002QueryBeforeAdd");
        response = TronlinkApiList.v2TokenQuery(header,params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");


        /*deployedContract = "TRBXukyS8hpiAUmeCiF96aM5zsHfrrNMHL";
        //begin to query
        params.clear();
        //params.put("address",quince_B58);

        header.clear();
        header.put("System","iOS");
        header.put("Version","v4.12.0");
        jsonObject.clear();
        jsonObject.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case002QueryBeforeAdd");
        response = TronlinkApiList.v2TokenQuery(header,params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");*/




    }

    @Test(enabled = true)
    public void Case003Add(){
        /*//add
        params.clear();
        //params.put("address",quince_B58);
        header.clear();
        header.put("System","Chrome");
        header.put("Version","4.0.1");

        jsonObject.clear();
        //jsonObject.put("address","TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        jsonObject.put("address",quince_B58);
        jsonObject.put("tokenAddress","TRBXukyS8hpiAUmeCiF96aM5zsHfrrNMHL");
        jsonObject.put("decimal",6);
        jsonObject.put("name","WQQDefined2");
        jsonObject.put("symbol","WQQDF2");
        jsonObject.put("type",2);

        log.info("Case003Add-add");
        response = TronlinkApiList.v2TokenAdd(header,params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Boolean data = responseContent.getBooleanValue("data");
        //Assert.assertTrue(data);*/


        //add
        params.clear();
        params.put("address",quince_B58);
        header.clear();
        header.put("System","Android");
        header.put("Version","4.10.2");

        jsonObject.clear();
        //jsonObject.put("address","TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        jsonObject.put("address",quince_B58);
        jsonObject.put("tokenAddress","TKM7w4qFmkXQLEF2MgrQroBYpd5TY7i1pq");
        jsonObject.put("decimal",8);
        jsonObject.put("name","jTOKEN-TRX");
        jsonObject.put("symbol","jTRX");
        jsonObject.put("type",2);

        log.info("Case003Add-add");
        response = TronlinkApiList.v2TokenAdd(header,params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        //Assert.assertTrue(data);


        /*//add
        params.clear();
        //params.put("address",quince_B58);
        header.clear();
        header.put("System","iOS");
        header.put("Version","v5.11.2");

        jsonObject.clear();
        //jsonObject.put("address","TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        jsonObject.put("address",quince_B58);
        jsonObject.put("tokenAddress","TQCm6wDsnJxjDJLqV4kFGsr8YCbNRePH5M");
        jsonObject.put("decimal",6);
        jsonObject.put("name","WQQDefined4");
        jsonObject.put("symbol","WQQDF4");
        jsonObject.put("type",2);

        log.info("Case003Add-add");
        response = TronlinkApiList.v2TokenAdd(header,params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        data = responseContent.getBooleanValue("data");
        //Assert.assertTrue(data);
*/

    }

    /*
    @Test(enabled = false)
    public void Case004QueryAfterAdd(){
        //first add, no matter add before or not.
        //add
        params.clear();
        params.put("address",addressNewAsset41);

        jsonObject.clear();
        jsonObject.put("address",addressNewAsset41);
        jsonObject.put("tokenAddress",deployedContract);
        jsonObject.put("decimal",9);
        jsonObject.put("name","TronlinkServerToken");
        jsonObject.put("symbol","TSToken");
        jsonObject.put("type",2);

        log.info("Case003Add-add");
        response = TronlinkApiList.v2TokenAdd(null,params,jsonObject);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        //Query
        params.clear();
        params.put("address",addressNewAsset41);

        jsonObject.clear();
        jsonObject.put("address",addressNewAsset41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case002QueryBeforeAdd");
        response = TronlinkApiList.v2TokenQuery(header,params,jsonObject);
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
        params.put("address",addressNewAsset41);
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
        params.put("address",addressNewAsset41);
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
        params.put("address",addressNewAsset41);
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
        params.put("address",quince_B58);

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
        jsonObject.put("address",addressNewAsset41);
        jsonObject.put("tokenDel",tokenDelList);

        params.clear();
        params.put("address",addressNewAsset41);

        response = TronlinkApiList.v2DelAsset(null,params,jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Boolean data = responseContent.getBooleanValue("data");
        Assert.assertTrue(data);

        //query assetlist page
        params.clear();
        params.put("address",addressNewAsset41);
        params.put("version","v2");

        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object name = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].name[0]"));
        Assert.assertNull(name);

        //query AllAssetList
        params.clear();
        params.put("address",addressNewAsset41);

        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        name = JSONPath.eval(responseContent,String.join("","$..data.token[contractAddress='",deployedContract,"'].name[0]"));
        Assert.assertNull(name);
    }
*/

}
