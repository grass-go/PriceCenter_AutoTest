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
import tron.djed.JustUI.pages.ScanSystemPage;

public class ScanSystemPageTest extends Base {

  private String node = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private String URL = "https://" + node + "/?lang=en-US#/scan/system";
  Navigation navigation;
  ScanSystemPage scanSystemPage;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    scanSystemPage = new ScanSystemPage(DRIVER).enterScanSystemPage();
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "overview")
  public void testOverview001() {
    Assert.assertNotNull(scanSystemPage.Overview_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "priceFeeds")
  public void testPriceFeeds002() {
    Assert.assertNotNull(scanSystemPage.PriceFeeds_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "cdpActions")
  public void testCdpActions003() {
    Assert.assertNotNull("CDP Actions (1 week)", scanSystemPage.cdpActions_text);
    List<WebElement> cdpActionsList_text = scanSystemPage.cdpActionsList_text;
    Assert.assertEquals(7, cdpActionsList_text.size());
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
