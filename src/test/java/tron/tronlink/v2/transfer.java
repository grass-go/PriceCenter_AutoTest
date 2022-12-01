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
    private HashMap<String,String> params = new HashMap<>();

    @Test(description = "监控1155转账")
    public void test01_transfer_1155() {
        params.put("relatedAddress", "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        //params.put("relatedAddress","41E7D71E72EA48DE9144DC2450E076415AF0EA745F");
        params.put("contract_address", "TFKBbJxXzeHMknNW3SZkwxj1KZHvca3yNG");
        params.put("direction", "all");
        params.put("start", "0");
        params.put("limit", "10");
        params.put("confirm", "0");
        params.put("address","");

        HttpResponse response = TronlinkApiList.transfer1155(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String apStr = TronlinkApiList.parseResponse2String(response);
        log.info(apStr);
        Trans1155Rsp trans1155Rsp = JSONObject.parseObject(apStr, Trans1155Rsp.class);


    }
}