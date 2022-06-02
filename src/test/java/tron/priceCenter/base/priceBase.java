package tron.priceCenter.base;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.TronlinkApiList;

public class priceBase {
    public static volatile String priceUrl;
    public static  volatile String tronscanApiUrl;
    public static  volatile String testPriceUrl;

    @Parameters({"priceUrl","tronscanApiUrl","testPriceUrl"})
    @BeforeTest
    public void  getMonitorUrl(String priceUrl, String tronscanApiUrl, String testPriceUrl) {
        this.priceUrl = priceUrl;
        this.tronscanApiUrl = tronscanApiUrl;
        this.testPriceUrl = testPriceUrl;
    }
}
