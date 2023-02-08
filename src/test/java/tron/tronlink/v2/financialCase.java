package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class financialCase {
    private JSONObject responseContent;
    private String responseString;
    private JSONObject dataContent;
    private JSONObject object;
    private HttpResponse response;
    HashMap<String, String> params = new HashMap<>();
    HashMap<String, String> headers = new HashMap<>();
    JSONObject bodyObject = new JSONObject();
    List<String> walletAddressList = new ArrayList<>();
    List<String> trc20tokenList = new ArrayList<>();


    //v4.2.1 new user(not even have transfer trx),with parameter version=v1, will return trx only.

    @Test(enabled = true)
    public void totalAssets01() {
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        bodyObject.put("walletAddress",walletAddressList);
        bodyObject.put("showUsers",true);

        response = TronlinkApiList.totalAssets(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

    @Test(enabled = true)
    public void tokenFinancialList01() {
        walletAddressList.clear();
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        bodyObject.put("walletAddress",walletAddressList);
        bodyObject.put("sort",1);

        response = TronlinkApiList.tokenFinancialList(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

    @Test(enabled = true)
    public void v2asset() {
        List<String> tokenIds = new ArrayList<>();
        tokenIds.add("_");
        tokenIds.add("T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb");
        tokenIds.add("TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");
        tokenIds.add("THfS8gUDH5Cx1FnwvdQ2QfBdCHyeNDaKzs");
        for(String tokenId:tokenIds){
            walletAddressList.clear();
            walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
            walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
            bodyObject.put("walletAddress",walletAddressList);
            bodyObject.put("tokenId",tokenId);

            response = TronlinkApiList.v2assets(bodyObject, null,null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        }


    }

    @Test(enabled = true)
    public void myFinancialTokenList() {
        walletAddressList.clear();
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        bodyObject.put("walletAddress",walletAddressList);

        response = TronlinkApiList.myFinancialTokenList(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

    @Test(enabled = true)
    public void myFinancialProjectList() {
        walletAddressList.clear();
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        bodyObject.put("walletAddress",walletAddressList);

        response = TronlinkApiList.myFinancialProjectList(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

    // bug: projectId参数多余

    @Test(enabled = true)
    public void justLendDetail() {
        List<String> tokenIds = new ArrayList<>();
        tokenIds.add("_");
        tokenIds.add("T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb");
        tokenIds.add("TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");
        tokenIds.add("THfS8gUDH5Cx1FnwvdQ2QfBdCHyeNDaKzs");
        List<String> projectIds = new ArrayList<>();
        projectIds.add("2f38665c-7c74-4e63-bbdf-c69d6a623892");
        projectIds.add("534ed914-babc-4910-b6cf-0ebf4b59348b");
        projectIds.add("6b37b8ff-59d1-4086-8645-cef782d217c3");
        for(String tokenId:tokenIds){
            for(String projectId:projectIds){
                params.clear();
                params.put("walletAddress","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
                params.put("tokenId",tokenId);
                params.put("projectId",projectId);
                response = TronlinkApiList.justLendDetail(params,null);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            }
        }

    }

    @Test(enabled = true)
    public void justLendOperate() {
        List<String> tokenIds = new ArrayList<>();
        tokenIds.add("_");
        tokenIds.add("T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb");
        tokenIds.add("TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");
        tokenIds.add("THfS8gUDH5Cx1FnwvdQ2QfBdCHyeNDaKzs");
        List<String> projectIds = new ArrayList<>();
        projectIds.add("2f38665c-7c74-4e63-bbdf-c69d6a623892");
        projectIds.add("534ed914-babc-4910-b6cf-0ebf4b59348b");
        projectIds.add("6b37b8ff-59d1-4086-8645-cef782d217c3");
        for(String tokenId:tokenIds) {
            for (String projectId : projectIds) {
                params.clear();
                params.put("walletAddress", "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
                params.put("tokenId", tokenId);
                params.put("projectId", projectId);

                response = TronlinkApiList.justLendOperate(params, null);
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            }
        }
    }




}
