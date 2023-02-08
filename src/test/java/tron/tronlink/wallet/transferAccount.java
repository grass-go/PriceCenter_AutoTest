package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class transferAccount {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private HttpResponse response;
    private HashMap<String,String> param = new HashMap<>();
    private HashMap<String,String> header = new HashMap<>();

    @Test(enabled = true,description = "test trc20_status api", groups={"NoSignature"})
    public void test01Manual() throws Exception {
        List<String> addresses = new ArrayList<>();
        /*addresses.add("TCvAhjZ4Zw4hvdRWoR8rgQ8cNofPJqEUWi"); //risk address
        addresses.add("TMoxY1RioZy6xaqNzDXebpPM1w7eqwFAWp"); //risk contract
        addresses.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"); //common address
        addresses.add("TGjgvdTWWrybVLaVeFqSyVqJQWjxqRYbaK"); //common contract
        addresses.add("123");*/

        /*addresses.add("TTTwHe789fFsEU3VCg4LQ7BcQnAWjdHRL3"); //risk contract
        addresses.add("TTWKV8SY2gFS8qSYFPXNUEsdZRDmbpZzup"); //risk contract
        addresses.add("TCvAhjZ4Zw4hvdRWoR8rgQ8cNofPJqEUWi"); *///un-active address
        addresses.add("TCnac63tZbf1TsV28Ze2ndf2fJfeC3m6rX"); //risk account
        addresses.add("TTWJVt2mDBRMx5EXDk6Cc7tjcFXYidG81H"); //risk account
        addresses.add("TCvAhjZ4Zw4hvdRWoR8rgQ8cNofPJqEUWi");
        addresses.add("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        addresses.add("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");

        header.put("System","Chrome");
        header.put("Version","3.27.2");



        for(String address : addresses) {
            param.put("accountAddress", address);     //contract
            response = TronlinkApiList.transferAccount(param, header);
            responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        }

    }

}
