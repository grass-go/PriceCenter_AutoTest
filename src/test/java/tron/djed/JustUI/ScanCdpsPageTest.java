package tron.djed.JustUI;

import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.djed.JustUI.pages.ScanCdpsPage;

public class ScanCdpsPageTest extends Base {

  private String node = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private String URL = "https://" + node + "/?lang=en-US#/scan/cdps";
  Navigation navigation;
  ScanCdpsPage scanCdpsPage;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    scanCdpsPage = new ScanCdpsPage(DRIVER).enterScanCdpsPage();
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "cdpList")
  public void testCdpList() {
    List<WebElement> cdpList_text = scanCdpsPage.cdpsList_text;
    Assert.assertTrue(cdpList_text.size() > 0);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "serachCdp")
  public void testSerachCdp() throws Exception {
    scanCdpsPage.serachCdp_input.sendKeys("3");
    scanCdpsPage.searchCdp_btn.click();
    Thread.sleep(1000);
    Assert
        .assertEquals(DRIVER.getCurrentUrl(), "https://just.tronscan.org/?lang=en-US#/scan/cdps/3");
    Thread.sleep(500);
    Assert.assertEquals("CDP ID: 3", scanCdpsPage.cdpId_text.getText());
    List<WebElement> cdpActionHistoryList_text = scanCdpsPage.cdpActionHistoryList_text;
    Assert.assertTrue(cdpActionHistoryList_text.size() > 0);
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
