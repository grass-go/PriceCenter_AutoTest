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
    Assert.assertTrue(Helper.addCustomTokenFailed("TWqfoPF8MXQR1sPaLsZNZjdF7R7H6bmmC5"));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 20 without Transfer function.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002AddCustomToken20WithoutTransferFunctionTest() throws Exception {

    Assert.assertTrue(Helper.addCustomTokenFailed("TGmiMvg11brj78c5X5fkkvySUsUv37QTk2"));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 20 with balanceOf and  Transfer functions.",
      alwaysRun = false,
      enabled = false,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003AddCustomToken20WithBalanceOfAndTransferFunctionsTest() throws Exception {
    String shortName = "shortName";
    String tokenAddress = "TX9MUEoScrGQJ8X5jvp9eSVxxbfZfRGJUb";
    Assert.assertTrue(Helper.addCustomTokenSuccess(tokenAddress, shortName, "fullName", "TRC20"));

    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, false));
    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, true));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 20 with all functions.",
      alwaysRun = false,
      enabled = false,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004AddCustomToken20WithAllFunctionsTest() throws Exception {

    String shortName = "shortName";
    String tokenAddress = "TJx9N7dMg31CkEwcS3Sb3NHS3fCgpX1YoZ";
    Assert.assertTrue(Helper.addCustomTokenSuccess(tokenAddress, shortName, "fullName", "TRC20"));

    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, false));
    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, true));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
