package tron.chromeExtension.chromeCase.mainPageCase;

import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;

public class queryToken extends Base {
  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Query trx balance",
      alwaysRun = true,
      enabled = true)
  public void test001QueryTrxTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);

    String trxBalanceStr = getText(mainPage.trxBalance);
    waitingTime(5);
    double trxBalance = getBalanceFromSelectionBtn(trxBalanceStr);
    System.out.println("trxBalance:" + trxBalance);
    Assert.assertTrue(trxBalance > 0);
  }

  @Test(
      groups = {"P0"},
      description = "Query trc20 token balance",
      alwaysRun = true,
      enabled = true)
  public void test002QueryToken20Test() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    String trc20BalanceStr = getText(mainPage.trc20Balance);
    waitingTime(5);
    double trc20Balance = getBalanceFromSelectionBtn(trc20BalanceStr);
    System.out.println("trc20Balance:" + trc20Balance);
    Assert.assertTrue(trc20Balance > 0);
  }

  @Test(
      groups = {"P0"},
      description = "Query trc10 token balance",
      alwaysRun = true,
      enabled = true)
  public void test003QueryToken10Test() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    String trc10BalanceStr = getText(mainPage.trc10Balance);
    waitingTime(5);
    double trc10Balance = getBalanceFromSelectionBtn(trc10BalanceStr);
    System.out.println("trc10Balance:" + trc10Balance);
    Assert.assertTrue(trc10Balance > 0);
  }

  @Test(
      groups = {"P0"},
      description = "Query trc721 token balance",
      alwaysRun = true,
      enabled = true)
  public void test004QueryToken721Test() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.collectibles_btn);
    waitingTime(5);
    String trc721BalanceStr = getText(mainPage.trc721Balance);
    waitingTime(5);
    double trc721Balance = getBalanceFromSelectionBtn(trc721BalanceStr);
    System.out.println("trc721Balance:" + trc721Balance);
    Assert.assertTrue(trc721Balance > 0);
    click(mainPage.assets_btn);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.assets_btn);
    logoutAccount();
  }
}
