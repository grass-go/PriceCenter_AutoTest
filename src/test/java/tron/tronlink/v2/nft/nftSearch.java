package tron.tronlink.v2.nft;

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

import java.util.HashMap;
import java.util.Map;
public class nftSearch extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();

    @Test(enabled = true)
    //wqq1 search by id
    public void test001NftSearchById() {
        params.put("address","TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        params.put("tokenAddress","TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG");
        String searchWord="1";
        params.put("word",searchWord);
        response = TronlinkApiList.v2NftSearch(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));

        Object tokenAddress = JSONPath.eval(responseContent, "$.data.tokenAddress");
        System.out.println("tokenAddress is: "+tokenAddress.toString());
        Assert.assertEquals("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG", tokenAddress.toString());

        Object assetIds = JSONPath.eval(responseContent, "$.data.collectionInfoList.assetId");
        System.out.println("assetIds is: "+assetIds.toString());
        JSONArray assetIdsArray=(JSONArray)assetIds;
        for (Object json:assetIdsArray) {
            System.out.println("cur object: "+json.toString());;
            Assert.assertTrue(json.toString().contains(searchWord));
        }
    }
    @Test(enabled = true)
    // search by name
    public void test002NftSearchbyName() {
        params.put("address","TGzgVdQszcAHbEd9VELwifASLRdQY9kTcx");
        params.put("tokenAddress","TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG");
        String searchWord="hree";
        params.put("word",searchWord);
        response = TronlinkApiList.v2NftSearch(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));

        Object tokenAddress = JSONPath.eval(responseContent, "$.data.tokenAddress");
        System.out.println("tokenAddress is: "+tokenAddress.toString());
        Assert.assertEquals("TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG", tokenAddress.toString());

        Object assetNames = JSONPath.eval(responseContent, "$.data.collectionInfoList.name");
        System.out.println("asset Names are: "+assetNames.toString());
        JSONArray assetIdsArray=(JSONArray)assetNames;
        for (Object json:assetIdsArray) {
            System.out.println("cur object: "+json.toString());;
            Assert.assertTrue(json.toString().contains(searchWord));
        }

    }

}
