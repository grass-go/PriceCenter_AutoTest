package tron.chromeExtension.chromeCase.accountListCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AccountListPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

import java.util.concurrent.TimeUnit;

public class queryAccount extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Query the balance of all accounts",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001listAccountsTest() throws Exception {

    try {
      MainPage mainPage = new MainPage(DRIVER);
      AccountListPage accountlistPage = new AccountListPage(DRIVER);
      TimeUnit.SECONDS.sleep(5);
      click(mainPage.switchAccount_btn);
      TimeUnit.SECONDS.sleep(5);
      String totalBalanceOfAllAccountsStr = getText(mainPage.totalBalanceOfAllAccounts);
      double totalBalanceOfAllAccounts = getBalanceFromSelectionBtn(totalBalanceOfAllAccountsStr);
      log("totalBalanceOfAllAccounts:" + totalBalanceOfAllAccounts);
      Assert.assertTrue(totalBalanceOfAllAccounts > 0);
      TimeUnit.SECONDS.sleep(5);
      click(accountlistPage.close_btn);
    } catch (Exception e) {
      AccountListPage accountlistPage = new AccountListPage(DRIVER);
      TimeUnit.SECONDS.sleep(5);
      click(accountlistPage.close_btn);
    }
  }

  @Test(
      groups = {"P0"},
      description = "Search  the login account",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002searchAccountsTest() throws Exception {
    try {
      MainPage mainPage = new MainPage(DRIVER);
      AccountListPage accountlistPage = new AccountListPage(DRIVER);
      waitingTime(5);
      click(mainPage.switchAccount_btn);
      waitingTime(5);
      click(accountlistPage.searchAccount_btn);
      waitingTime(5);
      String searchStr = "自动化测试账户1";
      sendKeys(accountlistPage.searchAccount_input, searchStr);
      waitingTime(5);
      String address = getText(mainPage.address_content);
      log("address:" + address);
      Assert.assertTrue(loginAddress.contains(address.substring(13)));
      click(accountlistPage.close_btn);
    } catch (org.openqa.selenium.NoSuchElementException ex) {
      log("NoSuchElement!");
    }
  }

  @Test(
      groups = {"P0"},
      description = " Switch to another test account ",
      alwaysRun = true,
      enabled = true)
  public void test003switchAccountTest() throws Exception {
    try {
      Assert.assertTrue(Helper.switchAccount(testAccountTwoIndex, testAddress));
      Assert.assertTrue(Helper.switchAccount(testAccountOneIndex, loginAddress));
    } catch (Exception e) {
      AccountListPage accountlistPage = new AccountListPage(DRIVER);
      waitingTime(5);
      click(accountlistPage.close_btn);
    }
  }

  @Test(
      groups = {"P0"},
      description = " Add Wallet ",
      alwaysRun = false,
      enabled = false)
  public void test004addWalletTest() throws Exception {
    // todo:4.0改版后再完善
    MainPage mainPage = new MainPage(DRIVER);
    AccountListPage accountlistPage = new AccountListPage(DRIVER);
    waitingTime(5);
    click(accountlistPage.addWallet_btn);
    waitingTime(5);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
