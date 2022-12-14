package tron.tronlink.v2;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class tokenInfo extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject dataContent;
    private HttpResponse response;
    private HashMap<String,String> param = new HashMap<>();
    private HashMap<String,String> header = new HashMap<>();
    JSONObject jsonObject = new JSONObject();

    @Test(enabled = true)
    public void tokenInfo() throws Exception {
        ArrayList<String> tokenList = new ArrayList<String>();
        //nile env
        /*tokenList.add("1000323");  //official
        tokenList.add("TGoDaioTnU8229Qj2GcA2ujw9ZspKvbZpn");
        tokenList.add("TU6R6yPunDPayS8Awf63Xs3QUggfx7Ppd2"); //defiType=2
        tokenList.add("TT6Qk1qrBM4MgyskYZx5pjeJjvv3fdL2ih"); //defiType=1
        tokenList.add("TXLAQ63Xg1NAzckPwKHvzw7CSEmLMEqcdj"); //national
        tokenList.add("TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3");
        tokenList.add("TLBaRhANQoJFTqre9Nf1mjuwNWjCJeYqUL");
        tokenList.add("TMfZ7vSBQ2fTVpKBtHkfsD2XqFYy8TqgnQ");
        tokenList.add("TGjgvdTWWrybVLaVeFqSyVqJQWjxqRYbaK");
        tokenList.add("TXYZopYRdj2D9XRtbG411XZZ3kM5VkAeBf");
        tokenList.add("TLBaRhANQoJFTqre9Nf1mjuwNWjCJeYqUL");  //recommanded
        tokenList.add("TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3");
        tokenList.add("1000002");
        tokenList.add("1000016");
        tokenList.add("TWrZRHY9aKQZcyjpovdH6qeCEyYZrRQDZt");
        tokenList.add("TGjgvdTWWrybVLaVeFqSyVqJQWjxqRYbaK"); //top

        tokenList.add("TUqVyGDsU1zf19FTh8eYNHHyiCP6wTBs46"); //匿名币
        tokenList.add("TVaWGs55FAhms53ChyXtU1TNVcfEZcskos");
        tokenList.add("TYBPViM3kdhAhYMWKvoihyc5VyN9nRUwY2");

        tokenList.add("TUR6Q9KeajwGVeHxah2qT5jQ7C6wQXvF6V");//自定义未录入
        tokenList.add("TCrxgwVL8V7fBWRfsHygQncM7252CT7Np2");  *///自定义已经录入，未同步

        //prod env
        tokenList.add("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"); //USDT, official+top:1,national:DM
        tokenList.add("TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");  //jTOken
        tokenList.add("TQn9Y2khEsLJW1ChVWFMSMeRDow5KcbLSE");  //LPToken
        tokenList.add("TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq"); //NFT： official, national:DM
        tokenList.add("TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn"); //USDD： top:2
        tokenList.add("THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF"); // ETH： 普通
        tokenList.add("1002000"); //BTTOLD


        for (int i = 0; i<tokenList.size(); i++) {
            String curToken = tokenList.get(i);
            param.clear();
            param.put("tokenAddress",curToken);
            param.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
            header.clear();
            response = TronlinkApiList.tokenInfo(param,header);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        }
    }
}
