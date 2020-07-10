package tron.djed.JustUI;

import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
  WebDriverWait wait;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    scanTokensPage = new ScanTokensPage(DRIVER).enterScanTokensPage();
    justLink = DRIVER.getWindowHandle();
    wait = new WebDriverWait(DRIVER, 30);
  }

  @BeforeMethod
  public void beforeTest() throws Exception {
    DRIVER.switchTo().window(justLink);
    navigation.refresh();
    Thread.sleep(25000);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "jstPrice")
  public void test001JstPrice() {
    Assert.assertNotNull(scanTokensPage.jstPrice_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "priceFeeds")
  public void test002PriceFeeds() {
    Assert.assertNotNull(scanTokensPage.usdjSupply_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "ptrxTrxRatio")
  public void test003PtrxTrxRatio() {
    Assert.assertNotNull(scanTokensPage.ptrxTrxRatio_text);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "jstContractAddress")
  public void test004JstContractAddress() throws Exception {
    wait.until(ExpectedConditions.elementToBeClickable(scanTokensPage.jstContractAddress_link));
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
    DRIVER.switchTo().window(justLink);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "usdjContractAddress")
  public void test005UsdjContractAddress() throws Exception {
    wait.until(ExpectedConditions.visibilityOf(scanTokensPage.totalSupply_text));
    System.out.println(
        "scanTokensPage.totalSupply_text.getText():" + scanTokensPage.totalSupply_text.getText());
    Assert.assertTrue(Double.parseDouble(scanTokensPage.totalSupply_text.getText()) > 0);
    wait.until(ExpectedConditions.elementToBeClickable(scanTokensPage.usdjContractAddress_link));
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
    DRIVER.switchTo().window(justLink);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "ptrxContractAddress")
  public void test006PtrxContractAddress() throws Exception {
    wait.until(ExpectedConditions.elementToBeClickable(scanTokensPage.ptrxContractAddress_link));
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
    DRIVER.switchTo().window(justLink);
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
