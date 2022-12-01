package tron.tronlink.v2.trc1155;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class getCollectionList  extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject dataContent;
    private HttpResponse response;
    Map<String, String> params = new HashMap<>();

    public String expFollowAndHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowAndHold");

    public String expFollowAndNoHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowButNoHold");

    @Test
    public void getCollectionListTest001() {
        params.put("address",quince_B58);
        //params.put("tokenAddress","TQhDhNKGadHrEXKrTacSGUwvvJMHxsgKS9");
        params.put("tokenAddress","TE2VpPmPQp9UZpDPcBS4G8Pw3R1BZ4Ea6j");
        params.put("pageIndex","0");
        params.put("pageSize","10");

        response = TronlinkApiList.v2GetCollectionList_1155(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);


    }
}
