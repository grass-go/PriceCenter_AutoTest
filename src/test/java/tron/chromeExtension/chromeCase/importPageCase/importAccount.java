package tron.chromeExtension.chromeCase.importPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.ImportPage;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.utils.Helper;

public class importAccount extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    importAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Import account from privateKey.",
      alwaysRun = true,
      enabled = false)
  public void test001importAccountFromPrivateKeyTest() throws Exception {

    ImportPage importPage = new ImportPage(DRIVER);
    waitingTime();
    click(importPage.importWallet_btn);
    Helper.slidingScrollBar();
    click(importPage.agree_btn);
    waitingTime();
    sendKeys(importPage.import_input, accountKey001);
    waitingTime();
    click(importPage.next_btn);
    waitingTime();
    Helper.clickAndClearAndInput(importPage.walletName_input, "自动化导入1");
    waitingTime();
    sendKeys(importPage.setPassword_input, password);
    waitingTime();
    sendKeys(importPage.setPasswordAgain_input, password);
    waitingTime();
    click(importPage.import_btn);
    waitingTime();
    click(importPage.known_btn);
    onTheHomepageOrNot(accountAddress001);
  }

  @Test(
      groups = {"P0"},
      description = "Import account from keyStore.",
      alwaysRun = true,
      enabled = false)
  public void test002importAccountFromKeyStoreTest() throws Exception {}

  @Test(
      groups = {"P0"},
      description = "Import account from mnemonicWords.",
      alwaysRun = true,
      enabled = false)
  public void test003importAccountFromMnemonicWordTest() throws Exception {}

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
