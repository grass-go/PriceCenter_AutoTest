package tron.trondata.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.utils.Configuration;


public class TrondataBase {

    public static  String trondataUrl;
    public String queryAddress = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress");


    @Parameters({"trondataUrl"})
    @BeforeSuite()
    public void getMonitorUrl(String trondataUrl) {
        this.trondataUrl = trondataUrl;
    }

}
