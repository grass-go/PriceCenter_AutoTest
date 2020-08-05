package tron.trondata.base;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.utils.Configuration;


public class TrondataBase {

    public static  String trondataUrl = Configuration.getByPath("testng.conf").getString("tronlink.trondataUrl");
    public String queryAddress = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress");


    @Parameters({"trondataUrl"})
    @BeforeTest()
    public void getMonitorUrl(String data) {
        trondataUrl = data;
    }

}
