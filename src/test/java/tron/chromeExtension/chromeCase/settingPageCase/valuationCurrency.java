package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;

public class valuationCurrency extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "Change currency to RMB",
      alwaysRun = true,
      enabled = true)
  public void test001changeCurrencyToRMBTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(3));
    waitingTime();
    click(settingPage.RMB_btn);
    waitingTime();
    click(settingPage.back);
    waitingTime();
    click(mainPage.back_btn);
    String moneySymbol = getText(mainPage.accountTotalBalance).substring(0, 1);
    log("moneySymbol:" + moneySymbol);
    Assert.assertEquals("¥", moneySymbol);
  }

  @Test(
      groups = {"P0"},
      description = "Change currency to dollar",
      alwaysRun = true,
      enabled = true)
  public void test002changeCurrencyToDollarTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(3));
    waitingTime();
    click(settingPage.dollar_btn);
    waitingTime();
    click(settingPage.back);
    waitingTime();
    click(mainPage.back_btn);
    String moneySymbol = getText(mainPage.accountTotalBalance).substring(0, 1);
    log("moneySymbol:" + moneySymbol);
    Assert.assertEquals("$", moneySymbol);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
