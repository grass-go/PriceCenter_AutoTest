package tron.tronlink.base;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.utils.Configuration;

public class TronlinkBase {
    public static  String tronlinkUrl = Configuration.getByPath("testng.conf").getString("tronlink.tronlinkUrl");
    public static  String tronscanApiUrl = Configuration.getByPath("testng.conf").getString("tronlink.tronscanApiUrl");
    public String queryAddress = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress41");


    @Parameters({"tronlinkUrl","tronscanApiUrl"})
    @BeforeTest()
    public void getMonitorUrl(String link, String scan) {
        tronlinkUrl = link;
        tronscanApiUrl = scan;
    }
}
