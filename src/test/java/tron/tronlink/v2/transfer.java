package tron.tronlink.v2;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.springframework.util.StringUtils;
import org.testng.annotations.Test;
import org.tron.common.utils.StringUtil;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.dapp.model.AuthProjectRsp;
import tron.tronlink.dapp.model.Data;
import tron.tronlink.v2.model.trc1155.transfer.Token_transfers;
import tron.tronlink.v2.model.trc1155.transfer.Trans1155Rsp;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class transfer extends TronlinkBase {
    GetSign s = new GetSign();

    private String user = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
    private Map<String, String> initParams() {

        Map<String, String> params = s.GenerateParams(user, "/api/transfer/v2/trc1155", "GET");
        params.put("relatedAddress", user);
        params.put("contract_address", "TE2VpPmPQp9UZpDPcBS4G8Pw3R1BZ4Ea6j");
        params.put("direction", "all");
        params.put("start", "0");
        params.put("limit", "10");
        params.put("confirm", "0");
        return params;
    }

    @Test(description = "监控1155转账")
    public void test01_transfer_1155() {
        Map<String, String> params = initParams();
        HttpResponse response = TronlinkApiList.transfer1155(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String apStr = TronlinkApiList.parseResponse2String(response);
        log.info(apStr);
        Trans1155Rsp trans1155Rsp = JSONObject.parseObject(apStr, Trans1155Rsp.class);
        for (Token_transfers t:
             trans1155Rsp.getToken_transfers()) {
            Assert.assertTrue(t.getBlock_ts() > 0);
            Assert.assertTrue(t.getFrom_address().equals(user) || t.getTo_address().equals(user));
            Assert.assertTrue(t.getContract_type().equals("trc1155"));
            Assert.assertTrue(t.getContract_address().equals(t.getTokenInfo().getTokenId()));
            Assert.assertTrue(Long.parseLong(t.getToken_id()) >= 0);
        }

    }
}