package tron.tronlink.dapp;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.dapp.model.AuthProjectRsp;
import tron.tronlink.dapp.model.Data;

@Slf4j
public class Dapp_authorized_project extends TronlinkBase {

    private HttpResponse response;

    @Test(description = "获取dapp白名单")
    public void Test_get_authorized_project_01(){

        response = TronlinkApiList.dappAuthorizedProject();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String apStr = TronlinkApiList.parseResponse2String(response);
        log.info(apStr);
        AuthProjectRsp auths = JSONObject.parseObject(apStr, AuthProjectRsp.class);
        org.testng.Assert.assertEquals(auths.getCode(), 0);
        Assert.assertEquals(auths.getMessage().equals("OK"), true);
        Assert.assertNotEquals(auths.getData().size(), 0);

        for (Data d:
             auths.getData()) {
            //Assert.assertEquals(d.getIcon().trim().startsWith("https://"), true);
            Assert.assertEquals(d.getUrl().trim().startsWith("https://"), true);
        }
    }

}
