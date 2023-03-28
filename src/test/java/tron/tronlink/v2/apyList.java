package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.*;

import tron.common.utils.TronlinkServerHttpClient;

import static tron.common.utils.TronlinkServerHttpClient.createGetConnect;

@Slf4j
public class apyList extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject object;
    private JSONObject dataContent;
    public static HttpResponse response;
    private JSONArray array = new JSONArray();
    public static String HttpNode = TronlinkBase.tronlinkUrl;
    public static Map<String, String> header = new HashMap<>();
    Map<String, String> params = new HashMap<>();
    Map<String, String> paramsOfJustLend = new HashMap<>();

    @SneakyThrows
    @Test(enabled = true)
    public void apyList() {

        ArrayList<String> listOfAddress = new ArrayList<String>(
            Arrays.asList("T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb", "THfS8gUDH5Cx1FnwvdQ2QfBdCHyeNDaKzs", "TPYwAC9Y4uUcT2QH3WPPjqxzJSJWymMoMS", "TESJCkrX1rrNgJNb69b4vUJzSNBn1B8iZC", "TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp", "TWZ7nrMxQiGQ499D1BXpB42S7EtRa926nN", "TJqk3ChKSjmpoNm3gaqSEatNsueD37NGDK", "TLdhbJkAxt3UxUyY7DpnkDt6uiDTyHeRNd", "TMTqj3nkT9jFfGniT8Fw8qSmfiZ42Yhqjb",
                "TM1Xq1HHd5RTcR4VAiQ8oV6CQvfVdn3F1f", "THpYaJaY3wcGbkhEjQH6mW8uhNncP1CJYz", "TSkW3KiyHNbS9ozn99PHZz6rz1V2DMBFVa", "TTynJcuXkXUMBBU6ReC437eG4qafq9qU98", "TSrZn7QRYdZdn8MiK3QY7JurQe8EHbxNdS",
                "TD3Q1BmkxNGCz5VkzyL4S6gqw5YwHQZHNL", "TBEzkiB2JUevVNLUnnD8NtCYnnaE9XeviM", "T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb", "TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp"));


        ArrayList<String> listOfjAddress = new ArrayList<String>(
            Arrays.asList("TKM7w4qFmkXQLEF2MgrQroBYpd5TY7i1pq", "TRM3faiTDB9D4Vq4mwezUeo5rQLzCDqGSE", "TT6Qk1qrBM4MgyskYZx5pjeJjvv3fdL2ih", "TYf16sZLR9uXpm63bXsRCNQMQFvqqvXQ2t",
                "TPovsintcLMh9udvXgt45jvb1RYQ86imnL", "TMBRbGrkx2d3m8nAZWezFzSyJG6KrEGjj1", "TXNg6MoDTDEZKwPzTAdnzdQwfTF4LdU1QW", "TZ51C31Zh3qBSRBnTmbcuRX1rqyhzoCe8Q", "TLBoPBNAfrBPxq3rTQzSKzTXrRjjAqaiJ6",
                "TMsoCkr2yhukcGnvjhVk8Gj541BCQPEHwm", "TXFDQpnXxNSEsxo8R3brAaTMWk4Nv6uGji", "TBGCExAC3iRk5EXAVXEer3bwhTi9EN9rht", "TYVr8QECrDkf6EAiKehok5FF3ckWV5Ds7k", "TAj5XxJtkrEDvTT7mTsS3uqMcvSCp82cnR",
                "TQ7JUeFHWAxNru1Yp8YjPP3c7guZSe4e2E", "TTNcbZWxaeUSq81HJ4uY1SpyVsKykUX97W", "TKM7w4qFmkXQLEF2MgrQroBYpd5TY7i1pq", "TPovsintcLMh9udvXgt45jvb1RYQ86imnL"));
        boolean flag = true;
        for (int i = 0; i < listOfAddress.size(); i++) {
            String requestUrl = "http://123.56.3.74" + "/api/financial/apyList";

            params.put("signature", "x8N9g9wShp3%3DM4un6rQscf1jg28o%3D");
            params.put("projectId", "534ed914-babc-4910-b6cf-0ebf4b59348b");
            params.put("tokenId", listOfAddress.get(i));
            log.info("requestUrl:" + requestUrl);
            response = createGetConnect(requestUrl, params, null, null);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            log.info("responseContent:" + responseContent);
            log.info("HttpNode123132:" + HttpNode);
            JSONArray resultFromTronlink = responseContent.getJSONArray("data");

            String requestUrlOfJustLend = "http://47.252.29.162:10091" + "/justlend/markets/jtokenDetails";
            paramsOfJustLend.put("jtokenAddr", listOfjAddress.get(i));
            log.info("requestUrlOfJustLend:" + requestUrlOfJustLend);
            response = createGetConnect(requestUrlOfJustLend, paramsOfJustLend, null, null);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            log.info("responseContentOfJustLend:" + responseContent);
            JSONArray resultFromJustLend = responseContent.getJSONObject("data").getJSONArray("depositDetail");
            //Assert.assertEquals(resultFromTronlink, resultFromJustlend);
            if (!resultFromTronlink.equals(resultFromJustLend)) {
                log.info("i:" + i);
                log.info("resultFromTronLink:" + resultFromTronlink);
                log.info("resultFromJustLend:" + resultFromJustLend);
                flag = false;
            }
            Assert.assertTrue(flag);

        }


    }


}
