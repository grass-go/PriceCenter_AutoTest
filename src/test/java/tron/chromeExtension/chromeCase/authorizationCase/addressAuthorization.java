package tron.chromeExtension.chromeCase.authorizationCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AuthorizationPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.TronScanPage;
import tron.common.utils.MyIRetryAnalyzer;

public class addressAuthorization extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = "Address authorization.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001addressAuthorizationTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    AuthorizationPage authorizationPage = new AuthorizationPage(DRIVER);
    click(mainPage.pledge_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(5);
    DRIVER.get("https://tronscan.org/#/contract/TGuqYUfia1HhBBhNkrV4mToMKtXrxxoh9S/code");
    TronScanPage tronScanPage = new TronScanPage(DRIVER);
    click(tronScanPage.prepareContract_btn);
    waitingTime(5);
    click(tronScanPage.approve_btn);
    waitingTime(5);
    sendKeys(tronScanPage.spenderAddress_input, "TG46VehcrBnw5mDPRvcSWFd1sRV85vKDDJ");
    waitingTime(5);
    sendKeys(tronScanPage.value_input, "1000000");
    waitingTime(5);
    click(tronScanPage.send_btn);
    waitingTime(5);
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    waitingTime(5);
    /* String tips = getAttribute(authorizationPage.white_btn, "data-tip");
    log("tips:" + tips);
    Assert.assertEquals("此合约地址归属于未知项目，请谨慎授权！", tips);*/
    click(authorizationPage.signatureAuthorization_btn);
    waitingTime(5);
    String authorizationCopyWriting_text = getText(authorizationPage.authorizationCopyWriting_text);
    log("authorizationCopyWriting_text:" + authorizationCopyWriting_text);
    Assert.assertEquals(
        "对此地址的授权额度为1，我已知晓确认授权意味着允许该地址交易我的资产，可能存在资产损失风险。", authorizationCopyWriting_text);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
