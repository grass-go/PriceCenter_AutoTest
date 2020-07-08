package tron.djed.JustUI;

import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.djed.JustUI.pages.ScanSummaryPage;

public class ScanSummaryPageTest extends Base {

  private String node = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private String URL = "https://" + node + "/?lang=en-US#/scan";
  Navigation navigation;
  ScanSummaryPage scanSummaryPage;
  WebDriverWait wait;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    scanSummaryPage = new ScanSummaryPage(DRIVER).enterScanSummaryPage();
    wait = new WebDriverWait(DRIVER, 30);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "trx")
  public void testTrx001() {
    String trxCollateralization_text = scanSummaryPage.trxCollateralization_text.getText();
    String trxCollateralization_text1 = trxCollateralization_text.split("≈")[0].replaceAll(" ", "")
        .replaceAll(",", "");
    String trxCollateralization_text2 = trxCollateralization_text.split("≈")[1]
        .replaceAll("\\$", "").replaceAll(" ", "").replaceAll(",", "");
    String usdjSupply_text = scanSummaryPage.usdjSupply_text.getText().replaceAll(",", "");
    String jstPrice_text = scanSummaryPage.jstPrice_text.getText().replaceAll("\\$", "")
        .replaceAll(",", "");
    String cdpInteractions_text = scanSummaryPage.cdpInteractions_text.getText()
        .replaceAll(",", "");
    Assert.assertTrue(Double.parseDouble(trxCollateralization_text1) > 0);
    Assert.assertTrue(Double.parseDouble(trxCollateralization_text2) > 0);
    Assert.assertTrue(Double.parseDouble(usdjSupply_text) > 0);
    Assert.assertTrue(Double.parseDouble(jstPrice_text) > 0);
    Assert.assertTrue(Double.parseDouble(cdpInteractions_text) > 0);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "recentCdpList")
  public void testRecentCdpList002() {
    List<WebElement> cdpList_text = scanSummaryPage.recentCdpList_text;
    Assert.assertTrue(cdpList_text.size() > 0);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "viewAll")
  public void testViewAll003() throws Exception {
    wait.until(ExpectedConditions.elementToBeClickable(scanSummaryPage.viewAll_btn));
    Thread.sleep(3000);
    scanSummaryPage.viewAll_btn.click();
    Thread.sleep(300);
    Assert
        .assertEquals(DRIVER.getCurrentUrl(), URL + "/cdps");
    navigation.back();
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
