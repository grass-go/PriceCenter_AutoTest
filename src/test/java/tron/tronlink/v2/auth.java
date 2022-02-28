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

import java.util.HashMap;
import java.util.Map;

public class auth extends TronlinkBase{
    private JSONObject responseContent;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();

    @Test(enabled = true)
    //quince user's multi Auth who only has self permission.
    public void test001Auth() {
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","1i5sfl%2FPH3KURhgMTaDI%2FuO8y70%3D");
        params.put("address",quince_B58);
        response = TronlinkApiList.v2Auth(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));

        Object ownerAddress = JSONPath.eval(responseContent, "$.data[0].ownerAddress");
        System.out.println("ownerAddress is: "+ownerAddress.toString());
        Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", ownerAddress.toString());

        Object firstActiveOperation = JSONPath.eval(responseContent, "$.data[0].activePermissions[0].operations");
        System.out.println("firstActiveOperation is: "+firstActiveOperation.toString());
        Assert.assertEquals("7fff1fc0033e0300000000000000000000000000000000000000000000000000", firstActiveOperation.toString());

        Object firstActiveThreshold = JSONPath.eval(responseContent, "$.data[0].activePermissions[0].threshold");
        System.out.println("firstActiveOperation is: "+firstActiveThreshold.toString());
        Assert.assertEquals("1", firstActiveThreshold.toString());

        Object firstActiveWeight = JSONPath.eval(responseContent, "$.data[0].activePermissions[0].weight");
        System.out.println("firstActiveWeight is: "+firstActiveWeight.toString());
        Assert.assertEquals("1", firstActiveWeight.toString());
    }

    //wqq1 users multiAuth which have other accounts permission.
    @Test(enabled = true)
    public void test002Auth() {
        params.put("nonce", "12345");
        params.put("secretId", "SFSUIOJBFMLKSJIF");
        params.put("signature", "z1pbfWmx2xd0BCu8jXUj14AmvsA%3D");
        params.put("address", "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        response = TronlinkApiList.v2Auth(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));

        Object ownerAddress = JSONPath.eval(responseContent, "$.data[0].ownerAddress");
        System.out.println("ownerAddress is: " + ownerAddress.toString());
        Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t", ownerAddress.toString());

        Object firstActiveOperation = JSONPath.eval(responseContent, "$.data[0].activePermissions[0].operations");
        System.out.println("firstActiveOperation is: " + firstActiveOperation.toString());
        Assert.assertEquals("77ff07c0027e0300000000000000000000000000000000000000000000000000", firstActiveOperation.toString());

        Object firstActiveThreshold = JSONPath.eval(responseContent, "$.data[0].activePermissions[0].threshold");
        System.out.println("firstActiveOperation is: " + firstActiveThreshold.toString());
        Assert.assertEquals("50", firstActiveThreshold.toString());

        Object firstActiveWeight = JSONPath.eval(responseContent, "$.data[0].activePermissions[0].weight");
        System.out.println("firstActiveWeight is: " + firstActiveWeight.toString());
        Assert.assertEquals("20", firstActiveWeight.toString());
    }


}
