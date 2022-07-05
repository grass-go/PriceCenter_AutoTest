package tron.chromeExtension.chromeCase.authorizationCase;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.ExportAccountPage;
import tron.chromeExtension.pages.JustLendPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;

public class whiteListAuthorization extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = "Authorization white lists.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001AuthorizationWhiteListTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    click(mainPage.pledge_btn);
    waitingTime();
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    DRIVER.get("https://justlend.cc/#/home");
    JustLendPage justLendPage = new JustLendPage(DRIVER);
    click(justLendPage.USDDSupply_btn);
    waitingTime();
    click(justLendPage.approveUSDD_btn);
    waitingTime(5);
    minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    String tips = getAttribute(justLendPage.white_btn, "data-tip");
    log("tips:" + tips);
    Assert.assertEquals("此合约地址归属于 JustLend 项目", tips);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
