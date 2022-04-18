package tron.chromeExtension.chromeCase.accountListCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AccountListPage;
import tron.chromeExtension.pages.MainPage;
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
      enabled = true)
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
      // todo: 无法输入内容，xpath路径给的不对
      sendKeys(accountlistPage.searchAccount_input, searchStr);
      String address = getText(mainPage.address_content);
      log("address:" + address);
      Assert.assertEquals(loginAddress, address);
      click(accountlistPage.close_btn);
    } catch (Exception e) {
      AccountListPage accountlistPage = new AccountListPage(DRIVER);
      waitingTime(5);
      click(accountlistPage.close_btn);
    }
  }

  @Test(
      groups = {"P0"},
      description = " Switch to another test account ",
      alwaysRun = true,
      enabled = true)
  public void test003switchAccountTest() throws Exception {
    try {
      MainPage mainPage = new MainPage(DRIVER);
      AccountListPage accountlistPage = new AccountListPage(DRIVER);
      waitingTime(5);
      click(mainPage.switchAccount_btn);
      waitingTime(5);
      click(accountlistPage.account_list.get(Integer.parseInt(testAccountTwoIndex)));
      waitingTime(5);
      Assert.assertTrue(onTheHomepageOrNot(testAddress));
      click(mainPage.switchAccount_btn);
      waitingTime(5);
      click(accountlistPage.account_list.get(Integer.parseInt(testAccountOneIndex)));
      waitingTime(5);
      Assert.assertTrue(onTheHomepageOrNot(loginAddress));
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
