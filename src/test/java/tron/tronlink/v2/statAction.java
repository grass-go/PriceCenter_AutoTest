package tron.tronlink.v2;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class statAction {
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
    @Test(enabled = true)
    public void statAction() {
        //params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        bodyObject.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32");
        bodyObject.put("actionType",1);
        bodyObject.put("actionTime",java.lang.String.valueOf(System.currentTimeMillis()));
        bodyObject.put("tokenId","TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt");
        bodyObject.put("amount","10000");

        response = TronlinkApiList.statAction(bodyObject, params,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }



}
