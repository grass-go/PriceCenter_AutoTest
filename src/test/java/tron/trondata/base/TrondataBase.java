package tron.trondata.base;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import tron.common.utils.Configuration;

@Slf4j
public class TrondataBase {

    public static volatile String trondataUrl;
    public String queryAddress = Configuration.getByPath("testng.conf").getString("tronlink.queryAddress");


    @Parameters({"trondataUrl"})
    @BeforeSuite()
    public void getMonitorUrl(String trondataUrl) {
        log.info("getMonitorUrl begin ----");
        this.trondataUrl = trondataUrl;
    }

}
