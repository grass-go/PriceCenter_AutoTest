package tron.chromeExtension.chromeCase.allAssetsCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

public class AddCustomToken721 extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 721 without Transfer function.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001AddCustomToken721WithoutTransferFunctionTest() throws Exception {
    Assert.assertTrue(Helper.addCustomTokenFailed("非通证合约，无法添加自定义通证","TYwrv5ExAs52MPYqnaucf24fW57FQhLp8u"));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 721 without ownerOf function.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002AddCustomToken721WithoutOwnerOfFunctionTest() throws Exception {

    Assert.assertTrue(Helper.addCustomTokenFailed("非通证合约，无法添加自定义通证","TLioFVVnTb9x6d4Vaet4CXZWYBLut7rk1f"));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 721 with balanceOf and  Transfer functions.",
      alwaysRun = false,
      enabled = false,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003AddCustomToken721WithBalanceOfAndTransferFunctionsAndDeleteFailedTest()
      throws Exception {
      //todo:4.0版本自定义通证已经不支持删除
    String shortName = "shortName";
    String fullName = "fullName";
    String tokenAddress = "TY4deKdy1f4YXMHhmiqzu9tA2rBYTTTBpB";
   // Assert.assertTrue(Helper.addCustomTokenSuccess(tokenAddress, shortName, fullName, "TRC721"));
    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, false));
  }

  @Test(
      groups = {"P0"},
      description = "Delete custom token 721 with balanceOf and  Transfer functions success.",
      alwaysRun = false,
      enabled = false,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004AddCustomToken721WithBalanceOfAndTransferFunctionsTest() throws Exception {
      //todo:4.0版本自定义通证已经不支持删除
    String shortName = "shortName";
    String tokenAddress = "TY4deKdy1f4YXMHhmiqzu9tA2rBYTTTBpB";
    Assert.assertTrue(Helper.deleteCustomToken(shortName, tokenAddress, true));
  }

  @Test(
      groups = {"P0"},
      description = "Add custom token 721 with all functions.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test005AddCustomToken721WithAllFunctionsTest() throws Exception {

    String tokenAddress = "TJx9N7dMg31CkEwcS3Sb3NHS3fCgpX1YoZ";
    Assert.assertTrue(Helper.addCustomTokenFailed("此通证已录入，请通过搜索添加该通证",tokenAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
