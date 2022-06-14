package tron.chromeExtension.chromeCase.mainPageCase;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LockPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.TronScanPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

public class pledge extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "Pledge myself to get bandwidth. ",
      alwaysRun = true,
      enabled = true)
  public void test001PledgeMyselfToGetBandwidthTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.pledge_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    String tips = Helper.pledgeTrxForMyself("width", minorHandle);
    Assert.assertTrue(tips.contains("成功质押"));
  }

  @Test(
      groups = {"P0"},
      description = " Pledge to myself for energy. ",
      alwaysRun = true,
      enabled = true)
  public void test002PledgeMyselfToGetEnergyTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.pledge_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    String tips = Helper.pledgeTrxForMyself("energy", minorHandle);
    Assert.assertTrue(tips.contains("成功质押"));
  }

  @Test(
      groups = {"P0"},
      description = "Pledge others to get bandwidth. ",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003PledgeOthersToGetBandwidthTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.pledge_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    String tips = Helper.pledgeTrxForOthers("width", minorHandle, testAddress);
    Assert.assertTrue(tips.contains("成功质押"));
  }

  @Test(
      groups = {"P0"},
      description = " Pledge to others for energy. ",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004PledgeOthersToGetEnergyTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.pledge_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(5);
    String tips = Helper.pledgeTrxForOthers("energy", minorHandle, testAddress);
    Assert.assertTrue(tips.contains("成功质押"));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
