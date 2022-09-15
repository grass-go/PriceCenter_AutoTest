package tron.tronlink.dapp;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.dapp.model.plug.Data;
import tron.tronlink.dapp.model.plug.PlugRsp;

@Slf4j
public class Dapp_plug extends TronlinkBase {

    private HttpResponse response;

    @Test(description = "获取dapp的详细信息")
    public void Test_plug_01(){

        response = TronlinkApiList.dappPlug();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String apStr = TronlinkApiList.parseResponse2String(response);
        log.info(apStr);
        PlugRsp auths = JSONObject.parseObject(apStr, PlugRsp.class);
        org.testng.Assert.assertEquals(auths.getRet(), 200);
        Assert.assertEquals(auths.getMsg().equals(""), true);
        Assert.assertNotEquals(auths.getData().size(), 0);

        for (Data d:
                auths.getData()) {
            Assert.assertEquals(d.getIcon().trim().startsWith("https://"), true);
            Assert.assertEquals(d.getHref().trim().startsWith("https://"), true);
            Assert.assertEquals(d.getImg().trim().startsWith("https://"), true);
            Assert.assertEquals(d.getIs_authorize().equals("1"), true);

        }
    }


}
