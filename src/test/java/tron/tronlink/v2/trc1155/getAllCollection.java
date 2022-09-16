package tron.tronlink.v2.trc1155;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.CompareJson;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class getAllCollection extends TronlinkBase {
    private JSONObject responseContent;
    private String responseString;
    private JSONArray dataContent;
    private HttpResponse response;
    Map<String, String> params = new HashMap<>();

    //public String expTopToken = Configuration.getByPath("testng.conf").getString("tronlink.trc1155TopToken");
    public String expFollowAndHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowAndHold");
    public String expFollowButNoHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowButNoHold");


    //Verify backward compatibility: No version parameter input, the top coin is in home page

    @Test(enabled = true, description = "only check contain all fields keys")
    public void getAllCollectionTest001(){
        params.put("address",address721_Hex);

        response = TronlinkApiList.v2GetAllCollection_1155(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        Assert.assertEquals(0,(int)responseContent.get("code"));
        Assert.assertEquals("OK",responseContent.get("message"));
        dataContent = responseContent.getJSONArray("data");
        Assert.assertTrue(dataContent.size() >= 1);
        for (int n = 0; n < dataContent.size(); n++ ) {
            JSONObject nftData = dataContent.getJSONObject(n);
            Assert.assertTrue(nftData.containsKey("type"));
            Assert.assertTrue(nftData.containsKey("top"));
            Assert.assertTrue(nftData.containsKey("isOfficial"));
            Assert.assertTrue(nftData.containsKey("name"));
            Assert.assertTrue(nftData.containsKey("shortName"));
            Assert.assertTrue(nftData.containsKey("contractAddress"));
            Assert.assertTrue(nftData.containsKey("count"));
            Assert.assertTrue(nftData.containsKey("logoUrl"));
            Assert.assertTrue(nftData.containsKey("maincontractAddress"));
            Assert.assertTrue(nftData.containsKey("homePage"));
            Assert.assertTrue(nftData.containsKey("tokenDesc"));
            Assert.assertTrue(nftData.containsKey("issueTime"));
            Assert.assertTrue(nftData.containsKey("issueAddress"));
            Assert.assertTrue(nftData.containsKey("nrOfTokenHolders"));
            Assert.assertTrue(nftData.containsKey("transferCount"));
            Assert.assertTrue(nftData.containsKey("totalSupply"));
            Assert.assertTrue(nftData.containsKey("totalSupplyStr"));
            Assert.assertTrue(nftData.containsKey("marketId"));
            Assert.assertTrue(nftData.containsKey("tokenStatus"));
            Assert.assertTrue(nftData.containsKey("transferStatus"));
        }
    }

    //Version=v2: the 4 type of focos coins are always in home page.
    // Prepare data：加关注以下3种币，官方币（不持有），推荐币（不持有）， 非推荐币（持有）
    // Verify: 1. 置顶币永远在此接口输出； 2，其他3种币也在此接口输出。
    // 用例设计说明：1. 此账号只能关注这些币，不能在此账号操作关注和取消关注，否则容易造成case不稳定。
    //            2. 相当于对此账户下首页展示的收藏品做全对比。
    //            3. 同时也校验了关注币的排序。
    @SneakyThrows
    @Test(enabled=true, description = "Test token in getAllCollection")
    public void getAllCollectionTest002(){
        // read actual json
        params.clear();
        params.put("address",address721_B58);
        params.put("version","v2");
        response = TronlinkApiList.v2GetAllCollection_1155(params);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        /*Object topToken = JSONPath.eval(responseContent, "$..data[0].contractAddress");
        log.info("expect top token: "+ expTopToken +", actual top Token"+topToken.toString());
        Assert.assertEquals(expTopToken,topToken.toString());
*/
        Object FollowAndHold = JSONPath.eval(responseContent, "$..data[0].contractAddress");
        log.info("expect FollowAndHold token: "+ expFollowAndHold +", actual FollowButNoHold Token"+FollowAndHold.toString());
        Assert.assertEquals(expFollowAndHold,FollowAndHold.toString());

        Object FollowButNoHold = JSONPath.eval(responseContent, "$..data[1].contractAddress");
        log.info("expect FollowButNoHold token: "+ expFollowButNoHold +", actual FollowButNoHold Token: "+FollowButNoHold.toString());
        Assert.assertEquals(expFollowButNoHold,FollowButNoHold.toString());

    }


}