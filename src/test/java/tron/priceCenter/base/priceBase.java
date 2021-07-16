package tron.priceCenter.base;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.TronlinkApiList;

public class priceBase {
    public static volatile String priceUrl;
    public static  volatile String tronscanApiUrl;

    @Parameters({"priceUrl","tronscanApiUrl"})
    @BeforeTest
    public void  getMonitorUrl(String priceUrl, String tronscanApiUrl) {
        this.priceUrl = priceUrl;
        this.tronscanApiUrl = tronscanApiUrl;
    }
}
