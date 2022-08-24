package tron.tronlink.v2.trc1155;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import tron.common.TronlinkApiList;
import tron.tronlink.v2.GetSign;
import tron.tronlink.v2.model.trc1155.Data;
import tron.tronlink.v2.model.trc1155.GetAllCollectionRsp;

import java.util.Map;

import static tron.common.Constants.getAllCollection1155Url;
import static tron.common.TronlinkApiList.v2GetAllCollectionByType;
import static tron.common.utils.ErrorMsg.*;
import static tron.common.utils.ErrorMsg.trc1155NotFound;

public class AssertGetAllCollection {

    GetSign sig = new GetSign();
    private void AssertGetAllCollection(GetAllCollectionRsp rsp, String followToken, boolean expect){
        org.testng.Assert.assertEquals(rsp.getCode(), 0, SuccessCodeErr);
        org.testng.Assert.assertEquals(rsp.getMessage(), OK,OKErr );
        boolean find = false;
        for(Data data : rsp.getData()){
            if(followToken.equals(data.getContractAddress())){
                find = true;
                break;
            }
        }
        org.testng.Assert.assertEquals(find, expect, trc1155NotFound);
    }


    public void AssertNotFoundInGAC(String user, String token,boolean expect){
        // 删除之后无法在全部资产查询
        Map<String,String> params = sig.GenerateParams(user, getAllCollection1155Url, "GET" );
        HttpResponse httpResponse = v2GetAllCollectionByType(getAllCollection1155Url, params);
        String getAllCollectionStr = TronlinkApiList.parseResponse2String(httpResponse);
        GetAllCollectionRsp gacRsp = JSONObject.parseObject(getAllCollectionStr, GetAllCollectionRsp.class);
        AssertGetAllCollection(gacRsp, token, expect);
    }
}
