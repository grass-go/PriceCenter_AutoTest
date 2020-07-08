package tron.djed.JustUI;

import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.WebDriver.Navigation;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.djed.JustUI.pages.ScanGovernancePage;

public class ScanGovernancePageTest extends Base {

  private String node = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private String URL = "https://" + node + "/?lang=en-US#/scan/governance";
  Navigation navigation;
  ScanGovernancePage scanGovernancePage;
  String justLink;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    scanGovernancePage = new ScanGovernancePage(DRIVER).enterScanGovernancePage();
    justLink = DRIVER.getWindowHandle();
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "stabilityFee")
  public void test001StabilityFee() {
    Assert.assertNotNull(scanGovernancePage.stabilityFee_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "txHash")
  public void test002TxHash() throws Exception {
    scanGovernancePage.txHash_link.click();
    Thread.sleep(500);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != justLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(1000);
    Assert
        .assertTrue(DRIVER.getCurrentUrl()
            .contains(
                "https://tronscan.org/#/transaction/4c3b61b24b1cbd83c0ff43cff74913c0e0336009c574f87e7a429d716e8b53cf"));
    navigation.back();
    DRIVER.switchTo().window(justLink);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "helpCenter")
  public void test003HelpCenter() throws Exception {
    scanGovernancePage.helpCenter_link.click();
    Thread.sleep(500);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != justLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl().contains("https://justorg.zendesk.com/hc"));
    navigation.back();
    DRIVER.switchTo().window(justLink);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "contactUs")
  public void test004ContactUs() throws Exception {
    scanGovernancePage.contactUs_link.click();
    Thread.sleep(500);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != justLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl().contains("https://t.me/just_defi"));
    navigation.back();
    DRIVER.switchTo().window(justLink);
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
