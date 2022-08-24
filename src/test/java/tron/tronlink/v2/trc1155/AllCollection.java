package tron.tronlink.v2.trc1155;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import tron.common.TronlinkApiList;
import tron.tronlink.v2.GetSign;
import tron.tronlink.v2.model.Token;
import tron.tronlink.v2.model.trc1155.AllCollectionRsp;

import java.util.Map;

import static tron.common.Constants.AllCollection1155Url;
import static tron.common.TronlinkApiList.v2GetAllCollectionByType;
import static tron.common.utils.ErrorMsg.*;
import static tron.common.utils.ErrorMsg.trc1155NotFound;

@Slf4j
public class AllCollection {
    GetSign sig = new GetSign();

    public void AssertNotFoundInAC(String user, String token,boolean expect){
        // 删除之后无法在全部资产查询
        Map<String,String> params = sig.GenerateParams(user, AllCollection1155Url, "GET" );
        HttpResponse httpResponse = v2GetAllCollectionByType(AllCollection1155Url, params);
        String AllCollectionStr = TronlinkApiList.parseResponse2String(httpResponse);
        log.info(AllCollectionStr);
        AllCollectionRsp gacRsp = JSONObject.parseObject(AllCollectionStr, AllCollectionRsp.class);
        AssertAllCollection(gacRsp, token, expect);
    }

    private void AssertAllCollection(AllCollectionRsp rsp, String followToken, boolean expect){
        org.testng.Assert.assertEquals(rsp.getCode(), 0, SuccessCodeErr);
        org.testng.Assert.assertEquals(rsp.getMessage(), OK,OKErr );
        boolean find = false;
        for(Token data : rsp.getData().getToken()){
            if(followToken.equals(data.getContractAddress())){
                find = true;
                break;
            }
        }
        org.testng.Assert.assertEquals(find, expect, trc1155NotFound);
    }
}
