package tron.priceCenter;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.collections.functors.ExceptionPredicate;
import tron.common.TronlinkApiList;
import tron.priceCenter.base.priceBase;
import tron.common.PriceCenterApiList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class getallprice {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private JSONObject object;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();

    @Test(enabled = true, description = "Test getallprice api return normally")
    public void Test001getallprice() {
        response = PriceCenterApiList.getallprice();
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        object = responseContent.getJSONObject("data");
        JSONArray tokensArray = object.getJSONArray("rows");
        int rowCount = tokensArray.size();
        log.info("rowCount:"+ rowCount);
        Assert.assertTrue(rowCount > 500);
        object = (JSONObject) tokensArray.get(0);
        Assert.assertTrue(object.containsKey("fShortName"));
        Assert.assertTrue(object.containsKey("fTokenAddr"));
        Assert.assertTrue(object.containsKey("price"));
        Assert.assertTrue(object.containsKey("sShortName"));
        Assert.assertTrue(object.containsKey("sTokenAddr"));
    }

}
