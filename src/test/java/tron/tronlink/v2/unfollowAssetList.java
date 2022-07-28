package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class unfollowAssetList extends TronlinkBase{
    private JSONObject responseContent;
    private String responseString;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();
    private addAsset addAsset = new addAsset();


    @Test(enabled = true, description = "有余额有价值的币，取消关注,可以查到")
    public void unfollowAssetList01(){
        // 先关注一个10币
        boolean follow =  addAsset.addAssetByToken10(false, unfollowAsset41, "1002000");
        if (!follow){
            org.testng.Assert.assertEquals(true, follow);
            return;
        }

        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","wZG2VUWX2bl4PN9ubRZGD8TR2C8%3D");
        params.put("address",unfollowAsset41);
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Boolean dataContent = responseContent.getBooleanValue("data");
        org.testng.Assert.assertEquals(dataContent.booleanValue(), true);
    }



    @Test(enabled = true, description = "有余额有价值的币，关注,无法查到")
    public void unfollowAssetList02(){
        // 先关注一个10币
       boolean follow =  addAsset.addAssetByToken10(true, unfollowAsset41, "1002000");
       if (!follow){
           org.testng.Assert.assertEquals(true, follow);
           return;
       }

        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","wZG2VUWX2bl4PN9ubRZGD8TR2C8%3D");
        params.put("address",unfollowAsset41);
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Boolean dataContent = responseContent.getBooleanValue("data");
        org.testng.Assert.assertEquals(dataContent.booleanValue(), false);
    }

    @Test(enabled = true, description = "验证余额balance=0的普通币，取消、加关注，也不在unfollow接口")
    public void unfollowAssetList03(){
        // 先关注一个10币
        boolean follow =  addAsset.addAssetByToken10(true, unfollowAsset41, "1002000");
        if (!follow){
            org.testng.Assert.assertEquals(true, follow);
            return;
        }

        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","wZG2VUWX2bl4PN9ubRZGD8TR2C8%3D");
        params.put("address",unfollowAsset41);
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Boolean dataContent = responseContent.getBooleanValue("data");
        org.testng.Assert.assertEquals(dataContent.booleanValue(), false);
    }

    @Test(enabled = true, description = "系统推荐币（线上：USDT）没有余额balance=0， 取消关注、加关注，都在unfollow接口。")
    public void unfollowAssetList04(){
        // 先关注一个推荐币
        boolean follow =  addAsset.addAssetByToken10(true, unfollowAsset41, "1002000");
        if (!follow){
            org.testng.Assert.assertEquals(true, follow);
            return;
        }

        // 判断是否在该接口
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","wZG2VUWX2bl4PN9ubRZGD8TR2C8%3D");
        params.put("address",unfollowAsset41);
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Boolean dataContent = responseContent.getBooleanValue("data");
        org.testng.Assert.assertEquals(dataContent.booleanValue(), true);
    }

    @Test(enabled = true, description = "排序验证：trxCount > 余额 > 简称忽略大小写排序")
    public void unfollowAssetList05(){

    }

}
