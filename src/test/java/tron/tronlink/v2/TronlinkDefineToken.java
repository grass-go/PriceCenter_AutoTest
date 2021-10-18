package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
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

    @Test(enabled = true)
    public void Case001SearchBeforeAdd(){
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");
        params.put("address",addressNewAsset41);
        params.put("keyWord",deployedContract);
        params.put("page","1");
        params.put("count","10");
        params.put("version","v2");
        log.info("Case001SearchBeforeAdd: begin to assert...");
        response = TronlinkApiList.v2SearchAsset(params);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        Object tokenContent=dataContent.getJSONArray("token");
        Assert.assertEquals("[]",tokenContent.toString());

    }
    @Test(enabled = true)
    public void Case002QueryBeforeAdd(){
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","3asGeVuGYWLVDh4ZqC15HDBezdE%3D");
        params.put("address",addressNewAsset41);

        jsonObject.clear();
        jsonObject.put("address",addressNewAsset41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case002QueryBeforeAdd");
        response = TronlinkApiList.v2TokenQuery(params,jsonObject);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        dataContent = responseContent.getJSONObject("data");
        Integer status=dataContent.getInteger("status");
        Integer expect=0;
        Assert.assertEquals(expect,status);
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

    @Test(enabled = true)
    public void Case003Add(){
        //sync
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","41T0WANF78fg4SioWeg%2Bl%2FVjzDs%3D");
        params.put("address",addressNewAsset41);

        jsonObject.clear();
        jsonObject.put("address",addressNewAsset41);
        jsonObject.put("tokenAddress",deployedContract);

        log.info("Case003Add-sync");
        response = TronlinkApiList.v2TokenAdd(params,jsonObject);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Object data = responseContent.getString("data");
        Assert.assertEquals("false",data.toString());

        //add
        /*params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","qcNpxfoLG0DYJ6U3D%2Bm%2BkRIxX5I%3D");
        params.put("address",addressNewAsset41);

        jsonObject.clear();
        jsonObject.put("address",addressNewAsset41);
        jsonObject.put("tokenAddress",deployedContract);
        jsonObject.put("decimal",9);
        jsonObject.put("name","TronlinkServerToken");
        jsonObject.put("symbol","TSToken");
        jsonObject.put("type",2);

        log.info("Case003Add-add");
        response = TronlinkApiList.v2TokenAdd(params,jsonObject);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        data = responseContent.getJSONObject("data");
        Assert.assertEquals("false",data.toString());*/
    }

    @Test(enabled = true)
    public void Case004QueryAfterAdd(){

    }

    @Test(enabled = true)
    public void Case005QueryAfterAdd(){

    }




}
