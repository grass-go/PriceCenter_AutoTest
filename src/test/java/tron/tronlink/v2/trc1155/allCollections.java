package tron.tronlink.v2.trc1155;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class allCollections extends TronlinkBase{
    private String responseString;
    private HttpResponse response;
    Map<String, String> params = new HashMap<>();
    private JSONObject responseContent;

    //public String expTopToken = Configuration.getByPath("testng.conf").getString("tronlink.trc1155TopToken");
    public String expNewAssetToken = Configuration.getByPath("testng.conf").getString("tronlink.trc1155newAssetToken");
    public String expFollowAndHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowAndHold");
    //public String expUnFollowButHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155UnFollowButHold");
    public String expFollowButNoHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowButNoHold");

    //返回我的全部收藏品列表，包括置顶token、用户关注、用户持有。
    //验证点：
    // 1. 一定包含置顶的token, 目前只有TNFT    #目前1155还没有置顶币。
    // 2. 一定包含关注未持有的token
    // 3. 一定包含关注持有的token
    // 4. 所有token的排序问题，全文对比可覆盖

    @Test( enabled = true)
    public void Test001allCollections() {

        params.clear();
        params.put("address", address721_Hex);

        response = TronlinkApiList.v2AllCollections_1155(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        //接口包括预设token，并且顺序严格等于预期。置顶币 -> 持有数据从大到小排 -> token简称忽略大小写排序a-z
        /*Object topToken = JSONPath.eval(responseContent, "$..data.token[0].contractAddress");
        log.info("expect top token: "+ expTopToken +", actual top Token "+topToken.toString());
        Assert.assertEquals(expTopToken,topToken.toString());*/

        Object FollowAndHold = JSONPath.eval(responseContent, "$..data.token[0].contractAddress");
        log.info("expect FollowAndHold token: "+ expFollowAndHold +", actual FollowButNoHold Token"+FollowAndHold.toString());
        Assert.assertEquals(expFollowAndHold,FollowAndHold.toString());

        Object newAssetToken = JSONPath.eval(responseContent, "$..data.token[1].contractAddress");
        log.info("expect newAssetToken: "+ expNewAssetToken +", actual newAsset Token "+newAssetToken.toString());
        Assert.assertEquals(expNewAssetToken,newAssetToken.toString());

        /*Object UnFollowButHold = JSONPath.eval(responseContent, "$..data.token[2].contractAddress");
        log.info("expect UnFollowButHold token: "+ expUnFollowButHold +", actual top Token"+UnFollowButHold.toString());
        Assert.assertEquals(expUnFollowButHold,UnFollowButHold.toString());*/

        Object FollowButNoHold = JSONPath.eval(responseContent, "$..data.token[2].contractAddress");
        log.info("expect FollowButNoHold token: "+ expFollowButNoHold +", actual FollowButNoHold Token"+FollowButNoHold.toString());
        Assert.assertEquals(expFollowButNoHold,FollowButNoHold.toString());


    }
}
