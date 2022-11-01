package tron.chromeExtension.chromeCase.mainPageCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class listDapp extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
    Helper.switchAccount(testAccountOneIndex, loginAddress);
  }

  @Test(
      groups = {"P2"},
      description = "List dapp",
      alwaysRun = true,
      enabled = true)
  public void test001listDappHotTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.dApp_btn);
    // 获取dapp列表中第一个元素
    String str = getText(mainPage.dAppHot_content);
    Assert.assertNotNull(str);
  }

  @Test(
      groups = {"P2"},
      description = "Used list",
      alwaysRun = true,
      enabled = true)
  public void test002listDappUsedTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.dApp_btn);
    waitingTime(5);
    click(mainPage.used_btn);
    waitingTime(5);
    click(mainPage.back_btn);
    waitingTime(5);
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
