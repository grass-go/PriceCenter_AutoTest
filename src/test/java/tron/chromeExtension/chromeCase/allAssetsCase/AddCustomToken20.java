package tron.chromeExtension.chromeCase.allAssetsCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AllAssetsPage;
import tron.chromeExtension.pages.ExportAccountPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;

public class AddCustomToken20 extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 20 without balanceOf function.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001AddCustomToken20WithoutBalanceOfFunctionTest() throws Exception {
    Assert.assertTrue(Helper.addCustomTokenFailed("非通证合约，无法添加自定义通证","TWqfoPF8MXQR1sPaLsZNZjdF7R7H6bmmC5"));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 20 without Transfer function.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002AddCustomToken20WithoutTransferFunctionTest() throws Exception {

    Assert.assertTrue(Helper.addCustomTokenFailed("非通证合约，无法添加自定义通证","TGmiMvg11brj78c5X5fkkvySUsUv37QTk2"));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 20 with balanceOf and  Transfer functions and delete failed.",
      alwaysRun = true,
      enabled = false,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003AddCustomToken20WithBalanceOfAndTransferFunctionsAndDeleteFailedTest()
      throws Exception {
      //todo:4.0版本自定义通证已经不支持删除
    String shortName = "shortName";
    String fullName = "fullName";
    String tokenAddress = "TX9MUEoScrGQJ8X5jvp9eSVxxbfZfRGJUb";
    Assert.assertTrue(Helper.addCustomTokenSuccess(tokenAddress, shortName, fullName, "TRC20"));
  }

  @Test(
      groups = {"P0"},
      description = "Delete custom token 20 with balanceOf and  Transfer functions  failed.",
      alwaysRun = true,
      enabled = false,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004DeleteCustomToken20WithBalanceOfAndTransferFunctionsFailedTest()
      throws Exception {
      //todo:4.0版本自定义通证已经不支持删除
    waitingTime(5);
    String shortName = "shortName";
    String tokenAddress = "TX9MUEoScrGQJ8X5jvp9eSVxxbfZfRGJUb";
    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, false));
  }

  @Test(
      groups = {"P0"},
      description = "Delete custom token 20 with balanceOf and  Transfer functions  success.",
      alwaysRun = true,
      enabled = false,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test005DeleteCustomToken20WithBalanceOfAndTransferAndDeleteSuccessFunctionsTest()
      throws Exception {
      //todo:4.0版本自定义通证已经不支持删除
    String shortName = "shortName";
    String tokenAddress = "TX9MUEoScrGQJ8X5jvp9eSVxxbfZfRGJUb";

    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, true));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 20 with all functions.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test006AddCustomToken20WithAllFunctionsTest() throws Exception {

    String tokenAddress = "TJx9N7dMg31CkEwcS3Sb3NHS3fCgpX1YoZ";
    Assert.assertTrue(Helper.addCustomTokenFailed("此通证已录入，请通过搜索添加该通证",tokenAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
