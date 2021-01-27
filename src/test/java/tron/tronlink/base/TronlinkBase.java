package tron.tronlink.base;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.utils.Configuration;

public class TronlinkBase {
    public static  String tronlinkUrl = Configuration.getByPath("testng.conf").getString("tronlink.tronlinkUrl");
    public static  String tronscanApiUrl = Configuration.getByPath("testng.conf").getString("tronlink.tronscanApiUrl");
    public String queryAddress = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress41");
    public String queryAddress58 = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress");
    public  String queryAddressTxt41 = Configuration.getByPath("testng.conf").getString("tronlink.queryAddressTxt41");
    public  String queryAddressTH48 = Configuration.getByPath("testng.conf").getString("tronlink.queryAddressTH48");
    public  String addressNewAsset = Configuration.getByPath("testng.conf").getString("tronlink.addressNewAsset");
    public  String addressNewAsset41 = Configuration.getByPath("testng.conf").getString("tronlink.addressNewAsset41");


    @Parameters({"tronlinkUrl","tronscanApiUrl"})
    @BeforeTest()
    public void getMonitorUrl(String link, String scan) {
        tronlinkUrl = link;
        tronscanApiUrl = scan;
    }
}
