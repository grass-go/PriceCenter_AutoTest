package tron.tronlink.base;

import java.net.URLEncoder;
import java.util.HashMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.trondata.base.TrondataBase;
import tron.tronlink.base.GetSign;

public class TronlinkBase {
    public static  volatile String tronlinkUrl;
    public static  volatile String tronscanApiUrl;
    public String queryAddress = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress41");
    public String queryAddress58 = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress");
    public  String queryAddressTxt41 = Configuration.getByPath("testng.conf").getString("tronlink.queryAddressTxt41");
    public  String queryAddressTH48 = Configuration.getByPath("testng.conf").getString("tronlink.queryAddressTH48");
    public  String addressNewAsset = Configuration.getByPath("testng.conf").getString("tronlink.addressNewAsset");
    public  String addressNewAsset41 = Configuration.getByPath("testng.conf").getString("tronlink.addressNewAsset41");
    public String address721_B58 = Configuration.getByPath("testng.conf").getString("tronlink.address721_B58");
    public String address721_Hex = Configuration.getByPath("testng.conf").getString("tronlink.address721_Hex");

    public String new1_B58 = Configuration.getByPath("testng.conf").getString("tronlink.new1_B58");
    public String new1_Hex = Configuration.getByPath("testng.conf").getString("tronlink.new1_Hex");
    public String quince_B58 = Configuration.getByPath("testng.conf").getString("tronlink.quince_B58");


    public static GetSign getSign = new GetSign();
    public String nonce = "12345";
    public String secretId = "SFSUIOJBFMLKSJIF";

    @Parameters({"tronlinkUrl","tronscanApiUrl"})
    @BeforeTest
    public void  getMonitorUrl(String tronlinkUrl, String tronscanApiUrl) {
        this.tronlinkUrl = tronlinkUrl;
        this.tronscanApiUrl = tronscanApiUrl;
        TronlinkApiList.HttpNode =tronlinkUrl;
        TronlinkApiList.HttpTronDataNode = tronscanApiUrl;
    }

    public String getSign(HashMap<String,String> paramMap) throws Exception{
        GetSign getSign = new GetSign();
        return URLEncoder.encode(getSign.getSignature(
            paramMap.containsKey("channel") ? paramMap.get("channel") : "official",
            paramMap.containsKey("chain") ? paramMap.get("chain") : "MainChain",
            paramMap.containsKey("lang") ? paramMap.get("lang") : "1",
            paramMap.get("address"),
            paramMap.containsKey("nonce") ? paramMap.get("nonce") : "12345",
            paramMap.containsKey("secretId") ? paramMap.get("secretId") : "SFSUIOJBFMLKSJIF",
            paramMap.containsKey("system") ? paramMap.get("system") : "AndroidTest",
            paramMap.containsKey("deviceId") ? paramMap.get("deviceId") : "1:1:1:1",
            paramMap.containsKey("ts") ? paramMap.get("ts") : "1609302220000",
            paramMap.containsKey("version") ? paramMap.get("version") : "v1.0.0",
            paramMap.get("url"),
            paramMap.containsKey("method") ? paramMap.get("method") : "GET"));
    }


}
