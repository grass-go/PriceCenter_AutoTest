package tron.chromeExtension.chromeCase.mainPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

public class exchange extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      retryAnalyzer = MyIRetryAnalyzer.class,
      description = " Exchange token cancellation. ",
      alwaysRun = true,
      enabled = true)
  public void test001exchangeTokenCancellationTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.exchange_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    String errorMsg = Helper.exchangeToken("usdt", minorHandle);
    log("errorMsg:" + errorMsg);
    Assert.assertTrue(errorMsg.contains("兑换被拒绝，请“重试”"));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
