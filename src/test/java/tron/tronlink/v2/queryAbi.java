package tron.tronlink.v2;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class queryAbi {
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
    public void queryAbiManual() {
        List<String> contractList = new ArrayList<>();
        /*contractList.add("TLACBUD3u2hs4feishLXGco9LtVdrRHAwb"); //proxy
        contractList.add("TYW6uzyn91Ao31FA1yGJ4fZzn8idGP38Jo"); //imple

        contractList.add("TBEzkiB2JUevVNLUnnD8NtCYnnaE9XeviM"); //代理
        contractList.add("TBzacFGD8u6KJxwe5gKpGHHBxA4Fw8jif7"); //实现*/
        contractList.add("TBzacFGD8u6KJxwe5gKpGHHBxA4Fw8jif6");

        //prod
        //contractList.add("TMz2SWatiAtZVVcH2ebpsbVtYwUPT9EdjH"); //代理
        //contractList.add("TTtQQxmhZTnpKhdVVJpANpX12vRjc16TxZ"); //实现
        contractList.add("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");


        for(String contract:contractList) {

            params.clear();
            //params.put("contract","TUpYjDEo8CtWfPE7u6MaR9jeiwkAnrWmpw");
            //params.put("contract","TLKMRsRaiULnGCfUaUWJ6Fshq1huXCeqSh");
            params.put("contract", contract); //实现
            //params.put("contract","TBEzkiB2JUevVNLUnnD8NtCYnnaE9XeviM"); //代理
            //params.put("contract","TTMy66EHYbsbWn4LxnH2fxyySX6yH6KeuK");
            response = TronlinkApiList.queryAbi(params, null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        }
    }
}
