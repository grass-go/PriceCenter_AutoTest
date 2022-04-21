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
      description = "Import account from mnemonicWords.",
      alwaysRun = false,
      enabled = false)
  public void test001importAccountFromMnemonicWordTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    ImportPage importPage = new ImportPage(DRIVER);
    waitingTime();
    click(importPage.importWallet_btn);
    Helper.slidingScrollBar();
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
