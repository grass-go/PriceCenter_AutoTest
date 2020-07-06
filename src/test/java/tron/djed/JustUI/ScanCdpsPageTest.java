package tron.djed.JustUI;

import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
  String justLink;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
    String WindowsTronLink = DRIVER.getWindowHandle();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    scanCdpsPage = new ScanCdpsPage(DRIVER).enterScanCdpsPage();
    justLink = DRIVER.getWindowHandle();

    Thread.sleep(1000);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != WindowsTronLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(2000);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "cdpList")
  public void test001CdpList() {
    List<WebElement> cdpList_text = scanCdpsPage.cdpsList_text;
    Assert.assertTrue(cdpList_text.size() > 0);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "serachCdp")
  public void test002SerachCdp() throws Exception {
    scanCdpsPage.serachCdp_input.sendKeys("3");
    scanCdpsPage.searchCdp_btn.click();
    Thread.sleep(1000);
    Assert
        .assertEquals(DRIVER.getCurrentUrl(), URL + "/3");
    Thread.sleep(500);
    Assert.assertEquals("CDP ID: 3", scanCdpsPage.cdpId_text.getText());
    List<WebElement> cdpActionHistoryList_text = scanCdpsPage.cdpActionHistoryList_text;
    Assert.assertTrue(cdpActionHistoryList_text.size() > 0);
  }

  @Test(enabled = false, retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003LoginWithTronlink() throws Exception {
    // login
    scanCdpsPage.login_btn.click();
    System.out.println("1111111111");
    Thread.sleep(1000);
    Actions action = new Actions(DRIVER);
    action.moveToElement(scanCdpsPage.loginWithTronlink);
    System.out.println("2222222222");
    scanCdpsPage.loginWithTronlink_btn.click();
    Thread.sleep(8000);
    scanCdpsPage.tronlink_link.click();
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != justLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(1000);
    System.out.println("DRIVER.getCurrentUrl()：" + DRIVER.getCurrentUrl());
    Assert.assertTrue(DRIVER.getCurrentUrl().contains(
        "https://chrome.google.com/webstore/detail/tronlink%EF%BC%88%E6%B3%A2%E5%AE%9D%E9%92%B1%E5%8C%85%EF%BC%89/ibnejdfjmmkpcnlpebklmnkoeoihofec"));
    navigation.back();
    DRIVER.switchTo().window(justLink);
    scanCdpsPage.login_tronlink_btn.click();

    Assert.assertTrue(scanCdpsPage.login_address.getText().contains("..."));
  }

  @Test(enabled = false, retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004Exit() throws Exception {
    // exit
    scanCdpsPage.login_address.click();
    Thread.sleep(1000);
    scanCdpsPage.exit_btn.click();
    Thread.sleep(20000);

    Assert.assertTrue(scanCdpsPage.login.getText().contains("Login"));
  }

  @Test(enabled = false, retryAnalyzer = MyIRetryAnalyzer.class)
  public void test005LoginWithLedger() throws Exception {
    // login
    scanCdpsPage.login_btn.click();
    Thread.sleep(1000);
    Actions action = new Actions(DRIVER);
    action.moveToElement(scanCdpsPage.loginWithLedger);
    Thread.sleep(1000);
    scanCdpsPage.loginWithLedger.click();
    Thread.sleep(5000);
    System.out.println(
        "scanCdpsPage.loginWithLedger_text.getText():" + scanCdpsPage.loginWithLedger_text
            .getText());
    Assert.assertTrue(scanCdpsPage.loginWithLedger_text.getText().contains(
        "Install Tron App: Search “Tron(TRX) v0.2.0 or higher” in Manager of Ledger Live, and then install it on your Ledger device"));
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
