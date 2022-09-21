package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

@Slf4j
public class sdk_verify {

    //该接口简单验证请求的内容与数据库存储内容是否一致。
    //数据库存储内容如下：
    // app_package_name, app_sign, app_id, secret
    //1	company_nametest	company_profiletest	websitetest	nametest	phonetest	emailtest	app_nametest	app_icontest	app_urltest	app_package_nametest	sdsevhytv	kiptvu650vjvnr8	0fei-3mfnv83kmfmv7jeekff-fmdg	0	2019-04-09 07:45:46	0
    //2	Coin Play	币用宝	http://byb.world/	潘栋	15210352198	fandong@tron.network	Coin Play	https://coin.top/production/js/20190428103958.png	http://byb.world/	com.biyongbao	a5:cd:82:a5:ed:70:84:06:72:26:87:a3:d6:04:2d:c2:59:74:13:ba	99ddf689-f25c-48da-a901-321e775898b2	93d635158f00dd40aaf848c522cd2e63	0	2019-04-29 03:22:17	0
    //3	sdkexamplet	测试	http://tronlink.org	sdk.example	15200000000	test@qq.com	com.tronlink.sdk.example	https://coin.top/production/js/20190428103958.png	http://tronlink.org	com.tronlink.sdk.example	0E:9A:45:FA:8D:A3:72:22:1E:8F:83:9B:D5:8B:14:E1:DB:01:B4:B4	c8925c2c-5375-4e7e-a0a7-4daa29a3e926	8e1b1ec06054c72d3fbe6b604c399e51	0	2019-05-05 04:10:35	1
    private JSONObject responseContent;
    private HttpResponse response;

    @Test(enabled = true,description = "test sdk_verify can access")
    public void Test000TestSdk_verify() throws Exception {
        String jsonbody = "{\n" +
                "    \"appID\":\"kiptvu650vjvnr8\",\n" +
                "    \"packName\":\"app_package_nametest\",\n" +
                "    \"secret\":\"0fei-3mfnv83kmfmv7jeekff-fmdg\",\n" +
                "    \"sign\":\"sdsevhytv\"\n" +
                "}";

        response = TronlinkApiList.sdk_verify(jsonbody);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertNotEquals(null, responseContent);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
        Assert.assertEquals("OK",responseContent.getString("message"));
        JSONObject dataContent = responseContent.getJSONObject("data");
        Assert.assertNotEquals(null, dataContent);
        Assert.assertEquals("app_nametest",dataContent.getString("appName"));
        Assert.assertEquals("app_urltest",dataContent.getString("appUrl"));
        Assert.assertTrue(dataContent.getBooleanValue("isVerify"));
        Assert.assertEquals(0,dataContent.getIntValue("isSupportWhiteList"));

    }



}
