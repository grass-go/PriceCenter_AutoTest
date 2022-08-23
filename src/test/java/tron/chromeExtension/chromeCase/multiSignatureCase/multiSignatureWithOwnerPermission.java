package tron.chromeExtension.chromeCase.multiSignatureCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.pages.TronScanPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

public class multiSignatureWithOwnerPermission extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
    Helper.switchAccount(testAccountMultiIndex, multiAddress);
  }

  @Test(
      groups = {"P0"},
      description = "Transfer trx with owner permission and confirm to signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001transferTrxWithOwnerPermissionTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation("owner", minorHandle, loginAddress, "TRX", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, true);
    Assert.assertTrue(tips.contains("成功"));
  }

  @Test(
      groups = {"P0"},
      description = "Transfer trx with owner permission and cancel signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002transferTrxWithOwnerPermissionFailedTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation("owner", minorHandle, loginAddress, "TRX", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, false);
    Assert.assertTrue(tips.contains("declined"));
  }

  @Test(
      groups = {"P0"},
      description = "Transfer TRC10 with owner permission and confirm to signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003transferTRC10WithOwnerPermissionTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation("owner", minorHandle, loginAddress, "1004910", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, true);
    Assert.assertTrue(tips.contains("成功"));
  }

  @Test(
      groups = {"P0"},
      description =
          "Transfer TRC10 with owner permission and confirm to signature and cancel signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004transferTRC10WithOwnerPermissionFailedTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation("owner", minorHandle, loginAddress, "1004910", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, false);
    Assert.assertTrue(tips.contains("declined"));
  }

  @Test(
      groups = {"P0"},
      description = "Transfer TRC20 with owner permission and confirm to signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test005transferTRC20WithOwnerPermissionTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation(
            "owner", minorHandle, loginAddress, "TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, true);
    Assert.assertTrue(tips.contains("成功"));
  }

  @Test(
      groups = {"P0"},
      description = "Transfer TRC20 with owner permission and cancel to signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test006transferTRC20WithOwnerPermissionFailedTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation(
            "owner", minorHandle, loginAddress, "TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, false);
    Assert.assertTrue(tips.contains("declined"));
  }

  @Test(
      groups = {"P0"},
      description = "Transfer TRC721 with owner permission and confirm to signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test007transferTRC721WithOwnerPermissionTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation(
            "owner", minorHandle, loginAddress, "TVzRKmCZ471QGsKqFJbXc2qeNtJNmcumbR", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, true);
    Assert.assertTrue(tips.contains("成功"));
  }

  @Test(
      groups = {"P0"},
      description = "Transfer TRC721 with owner permission and cancel to signature.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test008transferTRC721WithOwnerPermissionFailedTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.clickJump_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime(10);
    String tips =
        Helper.multiSignatureInitiation(
            "owner", minorHandle, loginAddress, "TVzRKmCZ471QGsKqFJbXc2qeNtJNmcumbR", "1", true);
    Assert.assertTrue(tips.contains("成功"));
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    Helper.switchAccount(testAccountTwoIndex, accountAddress002);
    waitingTime();
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    tips = Helper.multiSignature(minorHandle, false);
    Assert.assertTrue(tips.contains("declined"));
  }

  @Test(
      groups = {"P0"},
      description = "Switch account.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test009switchAccountToLoginAddressTest() throws Exception {
    Assert.assertTrue(Helper.switchAccount(testAccountOneIndex, loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {

    logoutAccount();
  }
}
