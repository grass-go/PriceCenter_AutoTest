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
import tron.djed.JustUI.pages.ScanTokensPage;

public class ScanTokensPageTest extends Base {

  private String node = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private String URL = "https://" + node + "/?lang=en-US#/scan/tokens";
  Navigation navigation;
  ScanTokensPage scanTokensPage;
  String justLink;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    scanTokensPage = new ScanTokensPage(DRIVER).enterScanTokensPage();
    justLink = DRIVER.getWindowHandle();
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "jstPrice")
  public void testJstPrice001() {
    Assert.assertNotNull(scanTokensPage.jstPrice_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "priceFeeds")
  public void testPriceFeeds002() {
    Assert.assertNotNull(scanTokensPage.usdjSupply_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "ptrxTrxRatio")
  public void testPtrxTrxRatio003() {
    Assert.assertNotNull(scanTokensPage.ptrxTrxRatio_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "jstContractAddress")
  public void testJstContractAddress004() throws Exception {
    scanTokensPage.jstContractAddress_link.click();
    Thread.sleep(500);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != justLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(1000);
    System.out.println("DRIVER.getCurrentUrl()：" + DRIVER.getCurrentUrl());
    Assert
        .assertTrue(DRIVER.getCurrentUrl()
            .contains("https://tronscan.org/#/contract/TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9"));
    navigation.back();
    logoutAccount();
    DRIVER.switchTo().window(justLink);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "usdjContractAddress")
  public void testUsdjContractAddress005() throws Exception {
    System.out.println(
        "scanTokensPage.totalSupply_text.getText():" + scanTokensPage.totalSupply_text.getText());
    Assert.assertTrue(Double.parseDouble(scanTokensPage.totalSupply_text.getText()) > 0);
    scanTokensPage.usdjContractAddress_link.click();
    Thread.sleep(1000);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != justLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(1000);
    Assert
        .assertTrue(DRIVER.getCurrentUrl()
            .contains("https://tronscan.org/#/contract/TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT"));
    navigation.back();
    logoutAccount();
    DRIVER.switchTo().window(justLink);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "ptrxContractAddress")
  public void testPtrxContractAddress006() throws Exception {
    scanTokensPage.ptrxContractAddress_link.click();
    Thread.sleep(1000);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for (String i : allWindow) {
      if (i != justLink) {
        DRIVER.switchTo().window(i);
      }
    }
    Thread.sleep(1000);
    Assert
        .assertTrue(DRIVER.getCurrentUrl()
            .contains("https://tronscan.org/#/address/TWjE8mAr3a7ZwgtyBxzmkuumnNZdEFJ4DD"));
    navigation.back();
    logoutAccount();
    DRIVER.switchTo().window(justLink);
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
