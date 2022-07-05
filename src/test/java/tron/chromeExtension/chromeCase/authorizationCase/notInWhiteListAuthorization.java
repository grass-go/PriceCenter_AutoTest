package tron.chromeExtension.chromeCase.authorizationCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AuthorizationPage;
import tron.chromeExtension.pages.JustLendPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.TronScanPage;
import tron.common.utils.MyIRetryAnalyzer;

public class notInWhiteListAuthorization extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = "Authorization not in white lists.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001AuthorizationNotInWhiteListTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    AuthorizationPage authorizationPage = new AuthorizationPage(DRIVER);
    click(mainPage.pledge_btn);
    waitingTime();
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    DRIVER.get("https://tronscan.org/#/contract/TGuqYUfia1HhBBhNkrV4mToMKtXrxxoh9S/code");
    TronScanPage tronScanPage = new TronScanPage(DRIVER);
    click(tronScanPage.prepareContract_btn);
    waitingTime();
    click(tronScanPage.approve_btn);
    waitingTime(5);
    sendKeys(tronScanPage.spenderAddress_input, "TGuqYUfia1HhBBhNkrV4mToMKtXrxxoh9S");
    waitingTime();
    sendKeys(tronScanPage.value_input, "1000000");
    waitingTime();
    click(tronScanPage.send_btn);
    waitingTime();
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    String tips = getAttribute(authorizationPage.white_btn, "data-tip");
    log("tips:" + tips);
    Assert.assertEquals("此合约地址归属于未知项目，请谨慎授权！", tips);
    click(authorizationPage.signatureAuthorization_btn);
    waitingTime();
    String authorizationCopyWriting_text = getText(authorizationPage.authorizationCopyWriting_text);
    log("authorizationCopyWriting_text:" + authorizationCopyWriting_text);
    Assert.assertEquals(
        "此合约为未知合约，授权额度为1。我已知晓确认授权意味着允许该合约交易我的资产，可能存在资产损失风险。", authorizationCopyWriting_text);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
