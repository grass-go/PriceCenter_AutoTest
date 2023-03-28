package tron.tronlink.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.api.Http;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class financialCaseManual {
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
    public static HashMap<String, String> justlendTokens = new HashMap<>();
    public static HashMap<String, String> Tokens = new HashMap<>();
    public static HashMap<String,String> jTokenTokenMapNile = new HashMap<>();
    private List<String> expTokenNames = java.util.Arrays.asList("TRX","USDD","USDT","SUN","BTT","NFT",
            "JST","WIN","USDJ","USDC","TUSD","BTC","ETH","WBTT","BUSD");

    static {
        //nile
        jTokenTokenMapNile.put("TKM7w4qFmkXQLEF2MgrQroBYpd5TY7i1pq","_");  //trx
        jTokenTokenMapNile.put("TRM3faiTDB9D4Vq4mwezUeo5rQLzCDqGSE","THfS8gUDH5Cx1FnwvdQ2QfBdCHyeNDaKzs");  //usdd
        jTokenTokenMapNile.put("TT6Qk1qrBM4MgyskYZx5pjeJjvv3fdL2ih","TPYwAC9Y4uUcT2QH3WPPjqxzJSJWymMoMS");  //usdt
        jTokenTokenMapNile.put("TYf16sZLR9uXpm63bXsRCNQMQFvqqvXQ2t","TESJCkrX1rrNgJNb69b4vUJzSNBn1B8iZC");  //sun
        jTokenTokenMapNile.put("TPovsintcLMh9udvXgt45jvb1RYQ86imnL","TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");  //btt
        jTokenTokenMapNile.put("TMBRbGrkx2d3m8nAZWezFzSyJG6KrEGjj1","TWZ7nrMxQiGQ499D1BXpB42S7EtRa926nN");  //nft
        jTokenTokenMapNile.put("TXNg6MoDTDEZKwPzTAdnzdQwfTF4LdU1QW","TJqk3ChKSjmpoNm3gaqSEatNsueD37NGDK"); //jst
        jTokenTokenMapNile.put("TZ51C31Zh3qBSRBnTmbcuRX1rqyhzoCe8Q","TLdhbJkAxt3UxUyY7DpnkDt6uiDTyHeRNd"); //win
        jTokenTokenMapNile.put("TLBoPBNAfrBPxq3rTQzSKzTXrRjjAqaiJ6","TMTqj3nkT9jFfGniT8Fw8qSmfiZ42Yhqjb"); //usdj
        jTokenTokenMapNile.put("TMsoCkr2yhukcGnvjhVk8Gj541BCQPEHwm","TM1Xq1HHd5RTcR4VAiQ8oV6CQvfVdn3F1f");  //usdc
        jTokenTokenMapNile.put("TXFDQpnXxNSEsxo8R3brAaTMWk4Nv6uGji","THpYaJaY3wcGbkhEjQH6mW8uhNncP1CJYz");  //tusd
        jTokenTokenMapNile.put("TBGCExAC3iRk5EXAVXEer3bwhTi9EN9rht","TSkW3KiyHNbS9ozn99PHZz6rz1V2DMBFVa");  //btc
        jTokenTokenMapNile.put("TYVr8QECrDkf6EAiKehok5FF3ckWV5Ds7k","TTynJcuXkXUMBBU6ReC437eG4qafq9qU98");  //eth
        jTokenTokenMapNile.put("TAj5XxJtkrEDvTT7mTsS3uqMcvSCp82cnR","TSrZn7QRYdZdn8MiK3QY7JurQe8EHbxNdS");   //wbtt
        jTokenTokenMapNile.put("TTNcbZWxaeUSq81HJ4uY1SpyVsKykUX97W","TBEzkiB2JUevVNLUnnD8NtCYnnaE9XeviM");  //busd

        /*//prod
        jTokenTokenMapNile.put("TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP","_");  //trx
        jTokenTokenMapNile.put("TX7kybeP6UwTBRHLNPYmswFESHfyjm9bAS","TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");  //usdd
        jTokenTokenMapNile.put("TXJgMdjVX5dKiQaUi9QobwNxtSQaFqccvd","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");  //usdt
        jTokenTokenMapNile.put("TPXDpkg9e3eZzxqxAUyke9S4z4pGJBJw9e","TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S");  //sun
        jTokenTokenMapNile.put("TUaUHU9Dy8x5yNi1pKnFYqHWojot61Jfto","TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");  //btt
        jTokenTokenMapNile.put("TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE","TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq");  //nft
        jTokenTokenMapNile.put("TWQhCXaWz4eHK4Kd1ErSDHjMFPoPc9czts","TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9"); //jst
        jTokenTokenMapNile.put("TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7"); //win
        jTokenTokenMapNile.put("TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA","TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT"); //usdj
        jTokenTokenMapNile.put("TNSBA6KvSvMoTqQcEgpVK7VhHT3z7wifxy","TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");  //usdc
        jTokenTokenMapNile.put("TSXv71Fy5XdL3Rh2QfBoUu3NAaM4sMif8R","TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");  //tusd
        jTokenTokenMapNile.put("TLeEu311Cbw63BcmMHDgDLu7fnk9fqGcqT","TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");  //btc
        jTokenTokenMapNile.put("TR7BUFRQeq1w5jAZf1FKx85SHuX6PfMqsV","THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");  //eth
        jTokenTokenMapNile.put("TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt","TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt");   //wbtt
        jTokenTokenMapNile.put("TLHASseQymmpGQdfAyNjkMXFTJh8nzR2x2","TMz2SWatiAtZVVcH2ebpsbVtYwUPT9EdjH");  //busd*/


        justlendTokens.put("TRX","TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");
        justlendTokens.put("USDD","TX7kybeP6UwTBRHLNPYmswFESHfyjm9bAS");
        justlendTokens.put("USDT","TXJgMdjVX5dKiQaUi9QobwNxtSQaFqccvd");
        justlendTokens.put("SUN","TPXDpkg9e3eZzxqxAUyke9S4z4pGJBJw9e");
        justlendTokens.put("BTT","TUaUHU9Dy8x5yNi1pKnFYqHWojot61Jfto");
        justlendTokens.put("NFT","TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE");
        justlendTokens.put("JST","TWQhCXaWz4eHK4Kd1ErSDHjMFPoPc9czts");
        justlendTokens.put("WIN","TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv");
        justlendTokens.put("USDJ","TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA");
        justlendTokens.put("USDC","TNSBA6KvSvMoTqQcEgpVK7VhHT3z7wifxy");
        justlendTokens.put("TUSD","TSXv71Fy5XdL3Rh2QfBoUu3NAaM4sMif8R");
        justlendTokens.put("BTC","TLeEu311Cbw63BcmMHDgDLu7fnk9fqGcqT");
        justlendTokens.put("ETH","TR7BUFRQeq1w5jAZf1FKx85SHuX6PfMqsV");
        justlendTokens.put("WBTT","TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt");
        justlendTokens.put("BUSD","TLHASseQymmpGQdfAyNjkMXFTJh8nzR2x2");
        Tokens.put("TRX","_");
        Tokens.put("USDD","TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");
        Tokens.put("USDT","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        Tokens.put("SUN","TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S");
        Tokens.put("BTT","TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");
        Tokens.put("NFT","TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq");
        Tokens.put("JST","TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9");
        Tokens.put("WIN","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
        Tokens.put("USDJ","TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT");
        Tokens.put("USDC","TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");
        Tokens.put("TUSD","TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");
        Tokens.put("BTC","TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");
        Tokens.put("ETH","THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");
        Tokens.put("WBTT","TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt");
        Tokens.put("BUSD","TMz2SWatiAtZVVcH2ebpsbVtYwUPT9EdjH");
    }

    //v4.2.1 new user(not even have transfer trx),with parameter version=v1, will return trx only.

    @Test(enabled = true)
    public void totalAssets01() {
        walletAddressList.add("TRWNvb15NmfNKNLhQpxefFz7cNjrYjEw7x");
        //walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        //walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        //walletAddressList.add("TSbfmgnSs3KgW9yPJB33QKZ6DKJLPVpR7L");//未激活
        //walletAddressList.add("TZFQt962hcvBJ6jRdctkUNu7fJeVTvkn9K");
        //walletAddressList.add("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ");
        //walletAddressList.add("TXFmkVZkpkv8ghNCKwpeVdVvRVqTedSCAK");
        //walletAddressList.add("TW6omSrQ1ZK37SwSvTQD5Cnp2QbEX2zDVZ"); //liqi
        bodyObject.put("walletAddress",walletAddressList);
        bodyObject.put("showUsers",1);
        //totalAssets
        response = TronlinkApiList.totalAssets(bodyObject, null,null);
        //response = TronlinkApiList.totalAssetsNoSig(bodyObject,null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);


    }

    @Test(enabled = true)
    public void tokenFinancialList01() {
        walletAddressList.clear();
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        //walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        //walletAddressList.add("TSbfmgnSs3KgW9yPJB33QKZ6DKJLPVpR7L");
        //walletAddressList.add("TPyjyZfsYaXStgz2NmAraF1uZcMtkgNan5");
        //walletAddressList.add("TZFQt962hcvBJ6jRdctkUNu7fJeVTvkn9K");
        //walletAddressList.add("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ");
        //walletAddressList.add("TQ9gXwoLcLi6W5gV5RjRykVuLVYTDY54nK");
        bodyObject.put("walletAddress",walletAddressList);
        bodyObject.put("sort",4);
        //params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        response = TronlinkApiList.tokenFinancialList(bodyObject, null,null);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

    @Test(enabled = true)
    public void v2assets() {
        List<String> tokenIds = new ArrayList<>();


        tokenIds.add("_");   //trx
        //tokenIds.add("TJqk3ChKSjmpoNm3gaqSEatNsueD37NGDK");  //JST
        //tokenIds.add("T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb");   //trx
        /*tokenIds.add("THfS8gUDH5Cx1FnwvdQ2QfBdCHyeNDaKzs");   //USDD
        tokenIds.add("TPYwAC9Y4uUcT2QH3WPPjqxzJSJWymMoMS");  //USDT
        tokenIds.add("TESJCkrX1rrNgJNb69b4vUJzSNBn1B8iZC");  //SUN
        tokenIds.add("TWZ7nrMxQiGQ499D1BXpB42S7EtRa926nN");  //NFT
        tokenIds.add("TJqk3ChKSjmpoNm3gaqSEatNsueD37NGDK");  //JST
        tokenIds.add("TLdhbJkAxt3UxUyY7DpnkDt6uiDTyHeRNd");  //WIN
        tokenIds.add("TMTqj3nkT9jFfGniT8Fw8qSmfiZ42Yhqjb");  //USDJ
        tokenIds.add("TM1Xq1HHd5RTcR4VAiQ8oV6CQvfVdn3F1f");  //USDC
        tokenIds.add("THpYaJaY3wcGbkhEjQH6mW8uhNncP1CJYz");  //TUSD
        tokenIds.add("TSkW3KiyHNbS9ozn99PHZz6rz1V2DMBFVa");  //BTC
        tokenIds.add("TTynJcuXkXUMBBU6ReC437eG4qafq9qU98");  //ETH
        tokenIds.add("TSrZn7QRYdZdn8MiK3QY7JurQe8EHbxNdS");  //WBTT
        tokenIds.add("TBEzkiB2JUevVNLUnnD8NtCYnnaE9XeviM");  //BUSD*/
        //tokenIds.add("TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");  //BTT
        //tokenIds.add("TVSvjZdyDSNocHm7dP3jvCmMNsCnMTPa5W");


        for(String tokenId:tokenIds){
            walletAddressList.clear();
            //walletAddressList.add("TQ9gXwoLcLi6W5gV5RjRykVuLVYTDY54nK");
            //walletAddressList.add("TP5pnxeY9ahxAAhRLM2QUSkkGN64avYeP9");  //new
            //walletAddressList.add("TE4CeJSjLmBsXQva3F1HXvAbdAP71Q2Ucw");
            //walletAddressList.add("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ");   //wqq2
            //walletAddressList.add("TPmQw8dDLdrH8bTN3u9H1A2XCanbqyE533");   //wqq3
            walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
            //walletAddressList.add("TFcEHjsvYz3Bo5QaX85vrqjJCPemNdN11X");
            //walletAddressList.add("TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
            //walletAddressList.add("TPyjyZfsYaXStgz2NmAraF1uZcMtkgNan5");
            //walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
            bodyObject.put("walletAddress",walletAddressList);
            bodyObject.put("tokenId",tokenId);
            bodyObject.put("projectId","2f38665c-7c74-4e63-bbdf-c69d6a623892");
            //bodyObject.put("projectId","6b37b8ff-59d1-4086-8645-cef782d217c3");
            //bodyObject.put("projectId","534ed914-babc-4910-b6cf-0ebf4b59348b");
            //params.put("address","TZFQt962hcvBJ6jRdctkUNu7fJeVTvkn9K");
            //response = TronlinkApiList.v2assetsNoSig(bodyObject, params,null);
            response = TronlinkApiList.v2assets(bodyObject, params,null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        }


    }

    @Test(enabled = true)
    public void myFinancialTokenList() {
        walletAddressList.clear();
        //walletAddressList.add("TLipJxwgDbn7FaQCnECxiYdxFTBhshLiW3");
        //walletAddressList.add("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ");  //wqq2
        //walletAddressList.add("TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        //walletAddressList.add("TPmQw8dDLdrH8bTN3u9H1A2XCanbqyE533");  //wqq3
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");

        //walletAddressList.add("TZCHj1ZE7JCXMnSoePzqcQxgzNE1YNvHBw");
        //walletAddressList.add("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr");

        //walletAddressList.add("TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");  //liuyue
        //walletAddressList.add("TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        //walletAddressList.add("TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");//yawen
        //walletAddressList.add("TSbfmgnSs3KgW9yPJB33QKZ6DKJLPVpR7L");//未激活
        //walletAddressList.add("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ");
        //walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        //walletAddressList.add("TE4CeJSjLmBsXQva3F1HXvAbdAP71Q2Ucw");
        //walletAddressList.add("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ");  //wqq2
        //walletAddressList.add("TP5pnxeY9ahxAAhRLM2QUSkkGN64avYeP9");   //0质押
        bodyObject.put("walletAddress",walletAddressList);
        //response = TronlinkApiList.myFinancialTokenListNoSig(bodyObject, null,null);
        response = TronlinkApiList.myFinancialTokenList(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

    @Test(enabled = true)
    public void myFinancialProjectList() {
        walletAddressList.clear();
        walletAddressList.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        //walletAddressList.add("TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ");
        //walletAddressList.add("TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
        //walletAddressList.add("TE4CeJSjLmBsXQva3F1HXvAbdAP71Q2Ucw");
        //walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        //walletAddressList.add("TATA96pE3YR1oe3o6VGY768WDLjkXMYN64");  //quince_self
        //walletAddressList.add("TLipJxwgDbn7FaQCnECxiYdxFTBhshLiW3");  //qianqian
        //walletAddressList.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        bodyObject.put("walletAddress",walletAddressList);
        //response = TronlinkApiList.myFinancialProjectListNoSig(bodyObject, null,null);
        response = TronlinkApiList.myFinancialProjectList(bodyObject, null,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

    // bug: projectId参数多余

    @Test(enabled = true)
    public void justLendDetail() {
        List<String> tokenIds = new ArrayList<>();
        //jTokenTokenMapNile.put("TPovsintcLMh9udvXgt45jvb1RYQ86imnL","TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");  //btt

        //tokenIds.add("TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");
        tokenIds.add("THfS8gUDH5Cx1FnwvdQ2QfBdCHyeNDaKzs");  //no bttc 投资

        String testUser = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";
        //String testUser = "TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3";

        for(String tokenId:tokenIds){
            params.clear();
            params.put("walletAddress",testUser);
            params.put("tokenId",tokenId);
            params.put("projectId","6b37b8ff-59d1-4086-8645-cef782d217c3");
            response = TronlinkApiList.justLendDetail(params,null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);


            params.clear();
            params.put("walletAddress",testUser);
            params.put("tokenId",tokenId);
            params.put("projectId","534ed914-babc-4910-b6cf-0ebf4b59348b");
            response = TronlinkApiList.justLendDetail(params,null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);

            params.clear();
            params.put("walletAddress",testUser);
            params.put("tokenId",tokenId);
            params.put("projectId","2f38665c-7c74-4e63-bbdf-c69d6a623892");
            response = TronlinkApiList.justLendDetail(params,null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);


        }

    }

    @Test(enabled = true)
    public void justLendOperate() {
        String testUser = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";
        //String testUser = "TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3";
        List<String> tokenIds = new ArrayList<>();
        tokenIds.add("TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp");
        //tokenIds.add("TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW");

        /*List<String> projectIds = new ArrayList<>();
        projectIds.add("2f38665c-7c74-4e63-bbdf-c69d6a623892");
        projectIds.add("534ed914-babc-4910-b6cf-0ebf4b59348b");
        projectIds.add("6b37b8ff-59d1-4086-8645-cef782d217c3");*/

        for(String tokenId:tokenIds) {
            params.clear();
            params.put("walletAddress",testUser);
            params.put("tokenId", tokenId);
            params.put("projectId", "6b37b8ff-59d1-4086-8645-cef782d217c3");
            response = TronlinkApiList.justLendOperate(params, null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);


            params.clear();
            params.put("walletAddress",testUser);
            params.put("tokenId", tokenId);
            params.put("projectId", "534ed914-babc-4910-b6cf-0ebf4b59348b");
            response = TronlinkApiList.justLendOperate(params, null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);

            params.clear();
            params.put("walletAddress",testUser);
            params.put("tokenId", tokenId);
            params.put("projectId", "2f38665c-7c74-4e63-bbdf-c69d6a623892");
            response = TronlinkApiList.justLendOperate(params, null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        }
    }
    @Test(enabled = true)
    public void justLendDetailOperate() throws InterruptedException {
        String testUser = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";
        //String testUser = "TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3";

        String tokenId = "TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp";


        /*params.clear();
        params.put("walletAddress",testUser);
        params.put("tokenId",tokenId);
        params.put("projectId","6b37b8ff-59d1-4086-8645-cef782d217c3");
        response = TronlinkApiList.justLendDetail(params,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);*/
        for(int i=0; i<50; i++){
            params.clear();
            params.put("walletAddress",testUser);
            params.put("tokenId", tokenId);
            params.put("projectId", "6b37b8ff-59d1-4086-8645-cef782d217c3");
            response = TronlinkApiList.justLendOperate(params, null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);


            response = TronlinkApiList.createGetConnect("https://newtestapi.bt.io/bttc/chain/info",null, null,null);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
            Thread.sleep(20000);

        }

    }

    @Test(enabled = true)
    public void apyList() throws InterruptedException {
        String testUser = "TSNcHHwKgfiMqJQQTJGBf91EJHtB67mdTW";
        //String testUser = "TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3";
        String tokenId = "TBagxx57zx73VJJ61o12VfxzQ2EG3KHYJp";

        for(Map.Entry<String, String> entry : jTokenTokenMapNile.entrySet()){
            String jTokenAddress = entry.getKey();
            String TokenAddress = entry.getValue();
            params.clear();
            params.put("tokenId", TokenAddress);
            //2f38665c-7c74-4e63-bbdf-c69d6a623892
            //6b37b8ff-59d1-4086-8645-cef782d217c3
            //534ed914-babc-4910-b6cf-0ebf4b59348b
            params.put("projectId", "534ed914-babc-4910-b6cf-0ebf4b59348b");
            headers.put("System","Android");
            headers.put("Version","4.10.0");
            response = TronlinkApiList.apyList(params, headers);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);


        }

    }



    @Test(enabled = true)
    public void reward() {
        params.clear();
        params.put("walletAddress","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        //params.put("walletAddress","TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        response = TronlinkApiList.reward(params,null);
        //response = TronlinkApiList.rewardNoSig(params,null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    }

    @Test(enabled = true)
    public void getAllUnClaimedAirDrop(){
        String testuser = "TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr";
        List<String> users = new ArrayList<>();
        //users.add("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr");  //jtrx compare
        //users.add("TM4dm6EjYbpFpgJJujihUfPQSzuvA62RDj"); //trx, usdd, sun, btt, jst, tusd, usdt
        //users.add("TFDP1vFeSYPT6FUznL7zUjhg5X7p2AA8vw");  //nft, busd
        //users.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");

        users.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");

        //users.add("TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");    //liqi

        for (String curUser:users) {
            //如果没存款有收益，有待领取收益的情况。
            HttpResponse depositeResp = TronlinkApiList.getJustlandUserDeposite(curUser);
            JSONObject depositeContent = TronlinkApiList.parseResponse2JsonObject(depositeResp);
            Object depositToken_obj = JSONPath.eval(depositeContent, "$..jtokenAddress");
            java.util.ArrayList depositTokens = (java.util.ArrayList) depositToken_obj;


            walletAddressList.clear();
            walletAddressList.add(curUser);   //wqq2
            bodyObject.put("walletAddress", walletAddressList);

            response = TronlinkApiList.myFinancialTokenList(bodyObject, null, null);
            JSONObject myFTokenContent = TronlinkApiList.parseResponse2JsonObject(response);
            //log.info("curUser:"+curUser+"myFTokenContent:\n");
            //log.info(myFTokenContent.toString());

            response = TronlinkApiList.myFinancialProjectList(bodyObject, null, null);
            JSONObject myprojectListContent = TronlinkApiList.parseResponse2JsonObject(response);
            //log.info("curUser:"+curUser+"myprojectListContent:\n");
            //log.info(myprojectListContent.toString());

            HttpResponse minRewardResp = TronlinkApiList.getJustlendMiningReward(curUser);
            JSONObject minRewardContent = TronlinkApiList.parseResponse2JsonObject(minRewardResp);
            depositTokens.add("TXNg6MoDTDEZKwPzTAdnzdQwfTF4LdU1QW");
            for (Object jToken : depositTokens) {

                String curToken = jTokenTokenMapNile.get(jToken.toString());
                log.info("cur User: "+ curUser +"; getAllUnClaimedAirDrop:" + jToken.toString());

                Object account_depositJtoken_obj = JSONPath.eval(depositeContent, "$.data.assetList[jtokenAddress='"+jToken.toString()+"'].account_depositJtoken[0]");

                //jsunold: TQ7JUeFHWAxNru1Yp8YjPP3c7guZSe4e2E 收益为0的和jsunold不参与比较，单独测试
                if (account_depositJtoken_obj.toString().equals("0") || jToken.toString().equals("TQ7JUeFHWAxNru1Yp8YjPP3c7guZSe4e2E")){
                    continue;
                }
                BigDecimal justlendIncome;
                BigDecimal curReward;
                BigDecimal lastReward;
                if (jToken.toString().equals("TYVr8QECrDkf6EAiKehok5FF3ckWV5Ds7k") || jToken.toString().equals("TAj5XxJtkrEDvTT7mTsS3uqMcvSCp82cnR") || jToken.toString().equals("TTNcbZWxaeUSq81HJ4uY1SpyVsKykUX97W") || jToken.toString().equals("TQ7JUeFHWAxNru1Yp8YjPP3c7guZSe4e2E")) {

                    justlendIncome = new BigDecimal(0);
                    curReward = new BigDecimal(0);
                    lastReward = new BigDecimal(0);
                }else {

                    Object curReward_obj = JSONPath.eval(minRewardContent, "$.data." + jToken.toString() + ".USDD.currReward[0]");
                    Object lastReward_obj = JSONPath.eval(minRewardContent, "$.data." + jToken.toString() + ".USDD.lastReward[0]");
                    curReward = new BigDecimal(curReward_obj.toString());
                    lastReward = new BigDecimal(lastReward_obj.toString());
                    justlendIncome = new BigDecimal(curReward_obj.toString()).add(new BigDecimal(lastReward_obj.toString()));
                }
                Object myFinancialTokenIncome_obj = JSONPath.eval(myFTokenContent, "$.data.tokenList[tokenId='" + curToken + "'].projectList[0].financialIncomeValue[0]");
                //log.info("Justlend Income Compare: justlend:" + justlendIncome + "cur:" + curReward.toString() + "last:"+ lastReward.toString() + "; myFinancilIncome:" + myFinancialTokenIncome_obj.toString());
                BigDecimal myFinancialTokenIncome = new BigDecimal(myFinancialTokenIncome_obj.toString()).divide(new BigDecimal("1000000000000000000"), 18, java.math.RoundingMode.HALF_UP);

                //income In myProjectList
                Object myprojectIncome_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='534ed914-babc-4910-b6cf-0ebf4b59348b'].tokenList[tokenId='" + curToken + "'].financialIncomeValue[0]");
                //log.info("Justlend Income Compare: justlend:" + justlendIncome +  "cur:" + curReward.toString() + "last:"+ lastReward.toString() + "; myProjectIncome:" + myprojectIncome_obj.toString());
                BigDecimal myprojectIncome = new BigDecimal(myprojectIncome_obj.toString()).divide(new BigDecimal("1000000000000000000"), 18, java.math.RoundingMode.HALF_UP);

                //justlend detail is curReward
                params.clear();
                params.put("walletAddress",curUser);
                params.put("tokenId",curToken);
                response = TronlinkApiList.justLendDetail(params,null);
                responseContent = TronlinkApiList.parseResponse2JsonObject(response);
                Object detailIncome_obj = JSONPath.eval(responseContent, "$.data.financialIncomeValue[0]");
                //log.info("Justlend Income Compare: justlend:" + justlendIncome +  "cur:" + curReward.toString() + "last:"+ lastReward.toString() + "; Justlenddetail:" + detailIncome.toString());
                BigDecimal detailIncome = new BigDecimal(detailIncome_obj.toString()).divide(new BigDecimal("1000000000000000000"), 18, java.math.RoundingMode.HALF_UP);

                BigDecimal curGap = curReward.subtract(detailIncome);
                BigDecimal totalGap = justlendIncome.subtract(myFinancialTokenIncome);

                log.info("Justlend Income Compare: cur User: "+ curUser +"; getAllUnClaimedAirDrop:" + jToken.toString());
                log.info("Justlend Income Compare: justlend:" + justlendIncome + "; cur:" + curReward.toString() + " last: "+ lastReward.toString() + "; myFinancilIncome:" + myFinancialTokenIncome.toString());
                log.info("Justlend Income Compare: justlend:" + justlendIncome +  "; cur:" + curReward.toString() + " last: "+ lastReward.toString() + "; myProjectIncome:" + myprojectIncome.toString());
                log.info("Justlend Income Compare: justlend:" + justlendIncome +  "; cur:" + curReward.toString() + " last: "+ lastReward.toString() + "; Justlenddetail:" + detailIncome.toString());
                log.info("Justlend Income Compare: check gap Asset vs cur:" + curGap.toString());
                log.info("Justlend Income Compare: check gap justlend total vs myfToken :" + totalGap.toString());

                BigDecimal calculate_tokenCurReward = myFinancialTokenIncome.subtract(lastReward);
                BigDecimal gap_token_cur_calculate_curReward = curReward.subtract(calculate_tokenCurReward);
                log.info("Justlend Income Compare: check gap myTokenList calculate_curReward VS justlend curReward:"+gap_token_cur_calculate_curReward.toString());

                BigDecimal calculate_projectCurReward = myFinancialTokenIncome.subtract(lastReward);
                BigDecimal gap_project_cur_calculate_curReward = curReward.subtract(calculate_projectCurReward);
                log.info("Justlend Income Compare: check gap myProjectList calculate_curReward VS justlend curReward:"+gap_project_cur_calculate_curReward.toString());

            }
        }
    }


    @Test(enabled = true)
    public void myProjectList_moreUsers(){
        List<String> users = new ArrayList<>();
        users.add("TXTNcgJHD9GPfpiTbSG2VGtfdfii9VcpEr");  //jtrx compare
        users.add("TM4dm6EjYbpFpgJJujihUfPQSzuvA62RDj"); //trx, usdd, sun, btt, jst, tusd, usdt
        users.add("TFDP1vFeSYPT6FUznL7zUjhg5X7p2AA8vw");  //nft, busd
        users.add("TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        users.add("TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");

        for (String curUser:users) {
            log.info("myProjectList_moreUsers:"+curUser);
            walletAddressList.clear();
            walletAddressList.add(curUser);
            bodyObject.clear();
            bodyObject.put("walletAddress",walletAddressList);
            response = TronlinkApiList.myFinancialProjectList(bodyObject, null, null);
            JSONObject myprojectListContent = TronlinkApiList.parseResponse2JsonObject(response);
            Object totalIncomeUsd_obj = JSONPath.eval(myprojectListContent, "$.data.total.financialIncomeUsd[0]");
            Object totalIncomecny_obj = JSONPath.eval(myprojectListContent, "$.data.total.financialIncomeCny[0]");
            Object totalAssetsUsd_obj = JSONPath.eval(myprojectListContent, "$.data.total.financialAssetsUsd[0]");
            Object totalAssetsCny_obj = JSONPath.eval(myprojectListContent, "$.data.total.financialAssetsCny[0]");

            Object justlend_AssetsUsd_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='534ed914-babc-4910-b6cf-0ebf4b59348b'].financialAssetsUsd[0]");
            Object justlend_AssetsCny_obj;
            if(justlend_AssetsUsd_obj!=null) {
                justlend_AssetsCny_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='534ed914-babc-4910-b6cf-0ebf4b59348b'].financialAssetsCny[0]");
            }

            Object trx_AssetsUsd_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='2f38665c-7c74-4e63-bbdf-c69d6a623892'].financialAssetsUsd[0]");
            Object trx_AssetsCny_obj;
            if(justlend_AssetsUsd_obj!=null) {
                trx_AssetsCny_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='2f38665c-7c74-4e63-bbdf-c69d6a623892'].financialAssetsCny[0]");
            }

            Object bttc_AssetsUsd_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='6b37b8ff-59d1-4086-8645-cef782d217c3'].financialAssetsUsd[0]");
            Object bttc_AssetsCny_obj;
            if(justlend_AssetsUsd_obj!=null) {
                bttc_AssetsCny_obj = JSONPath.eval(myprojectListContent, "$.data.projectList[projectId='6b37b8ff-59d1-4086-8645-cef782d217c3'].financialAssetsCny[0]");
            }

            //Object cal_totalAssetsUsd_obj = null;
            BigDecimal cal_totalAssetsUsd;
            if (justlend_AssetsUsd_obj==null) {
                if (trx_AssetsUsd_obj != null && bttc_AssetsUsd_obj != null) {
                    cal_totalAssetsUsd = new BigDecimal(trx_AssetsUsd_obj.toString()).add(new BigDecimal(bttc_AssetsUsd_obj.toString()));
                    log.info("totalAssetsUsd compare:" + "actual totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; calcullate totalAssetsUsd:" + cal_totalAssetsUsd.toString());
                } else if (trx_AssetsUsd_obj == null && bttc_AssetsUsd_obj != null) {
                    cal_totalAssetsUsd = new BigDecimal(bttc_AssetsUsd_obj.toString());
                    log.info("totalAssetsUsd compare:" + "actual totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; calcullate totalAssetsUsd:" + cal_totalAssetsUsd.toString());
                } else if (trx_AssetsUsd_obj != null && bttc_AssetsUsd_obj == null) {
                    cal_totalAssetsUsd = new BigDecimal(trx_AssetsUsd_obj.toString());
                    log.info("totalAssetsUsd compare:" + "actual totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; calcullate totalAssetsUsd:" + cal_totalAssetsUsd.toString());
                } else if (trx_AssetsUsd_obj == null && bttc_AssetsUsd_obj == null) {
                    cal_totalAssetsUsd = new BigDecimal("0");
                    log.info("totalAssetsUsd compare:" + "actual totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; calcullate totalAssetsUsd:" + cal_totalAssetsUsd.toString());
                }
            }else if (justlend_AssetsUsd_obj!=null){
                if (trx_AssetsUsd_obj != null && bttc_AssetsUsd_obj != null) {
                    cal_totalAssetsUsd = new BigDecimal(justlend_AssetsUsd_obj.toString()).add(new BigDecimal(trx_AssetsUsd_obj.toString())).add(new BigDecimal(bttc_AssetsUsd_obj.toString()));
                    log.info("totalAssetsUsd compare:"+"actual totalAssetsUsd:"+totalAssetsUsd_obj.toString()+"; calcullate totalAssetsUsd:"+cal_totalAssetsUsd.toString());
                } else if (trx_AssetsUsd_obj == null && bttc_AssetsUsd_obj != null) {
                    cal_totalAssetsUsd = new BigDecimal(justlend_AssetsUsd_obj.toString()).add(new BigDecimal(bttc_AssetsUsd_obj.toString()));
                    log.info("totalAssetsUsd compare:" + "actual totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; calcullate totalAssetsUsd:" + cal_totalAssetsUsd.toString());
                } else if (trx_AssetsUsd_obj != null && bttc_AssetsUsd_obj == null) {
                    cal_totalAssetsUsd = new BigDecimal(justlend_AssetsUsd_obj.toString()).add(new BigDecimal(trx_AssetsUsd_obj.toString()));
                    log.info("totalAssetsUsd compare:" + "actual totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; calcullate totalAssetsUsd:" + cal_totalAssetsUsd.toString());
                } else if (trx_AssetsUsd_obj == null && bttc_AssetsUsd_obj == null) {
                    cal_totalAssetsUsd = new BigDecimal(justlend_AssetsUsd_obj.toString());
                    log.info("totalAssetsUsd compare:" + "actual totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; calcullate totalAssetsUsd:" + cal_totalAssetsUsd.toString());
                }
            }

            Object tokensIncomeUsd_obj = JSONPath.eval(myprojectListContent,"$..financialIncomeUsd" );
            log.info("totalIncomeUsd compare with calculate in self api:"+tokensIncomeUsd_obj.toString());
            java.util.ArrayList tokensIncomeUsd = (java.util.ArrayList)tokensIncomeUsd_obj;
            BigDecimal cal_tokensIncomeUsd = new BigDecimal("0");
            int flag = 0;
            for (int i = 0; i<tokensIncomeUsd.size(); i++)
            {
                if (flag==0 && tokensIncomeUsd.get(i).equals(totalIncomeUsd_obj.toString())){
                    flag = 1;
                    continue;
            }
                cal_tokensIncomeUsd = cal_tokensIncomeUsd.add(new BigDecimal(tokensIncomeUsd.get(i).toString()));
            }
            log.info("totalIncomeUsd compare with calculate in self api:" + "actual totalIncomeUsd:" + totalIncomeUsd_obj.toString() + "; calcullate totalIncomeUsd:" + cal_tokensIncomeUsd.toString());

            //compare with totalAssets
            walletAddressList.clear();
            bodyObject.clear();
            walletAddressList.add(curUser);
            bodyObject.put("walletAddress",walletAddressList);
            bodyObject.put("showUsers",1);
            response = TronlinkApiList.totalAssets(bodyObject, null,null);
            JSONObject totalAssetContent = TronlinkApiList.parseResponse2JsonObject(response);
            Object fassetUsdInTotalAPI_obj = JSONPath.eval(totalAssetContent,"$.data.total.financialAssetsUsd[0]");
            Object incomeUsdInTotalAPI_obj = JSONPath.eval(totalAssetContent,"$.data.total.financialIncomeUsd[0]");

            log.info("totalIncomeUsd compare with total assets api:" + "myPjt totalAssetsUsd:" + totalAssetsUsd_obj.toString() + "; totalAssets fIncomeUsd:" + fassetUsdInTotalAPI_obj.toString());
            log.info("totalIncomeUsd compare with total assets api:" + "myPjt totalIncomeUsd:" + totalIncomeUsd_obj.toString() + "; totalAssets fIncomeUsd:" + incomeUsdInTotalAPI_obj.toString());

        }
    }

    @Test(enabled = true)
    public void compare_reward_projectList(){
        //String testUser = "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ";
        String testUser = "TLipJxwgDbn7FaQCnECxiYdxFTBhshLiW3";
        //String testUser = "TRWNvb15NmfNKNLhQpxefFz7cNjrYjEw7x";
        //String testUser = "TATA96pE3YR1oe3o6VGY768WDLjkXMYN64";
        //String testUser = "TXFmkVZkpkv8ghNCKwpeVdVvRVqTedSCAK";
        params.clear();
        params.put("walletAddress",testUser);
        //params.put("walletAddress","TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
        response = TronlinkApiList.reward(params,null);
        JSONObject rewardConntent = TronlinkApiList.parseResponse2JsonObject(response);


        bodyObject.clear();
        walletAddressList.add(testUser);
        bodyObject.put("walletAddress",walletAddressList);
        response = TronlinkApiList.myFinancialProjectList(bodyObject, null, null);
        JSONObject myProjectContent = TronlinkApiList.parseResponse2JsonObject(response);

        response = TronlinkApiList.myFinancialTokenList(bodyObject, null, null);
        JSONObject myFTokenContent = TronlinkApiList.parseResponse2JsonObject(response);
        //log.info("curUser:"+curUser+"myFTokenContent:\n");
        //log.info(myFTokenContent.toString());

    }

}


