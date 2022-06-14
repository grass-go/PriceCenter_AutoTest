package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;

public class changeLanguage extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "Change language to English",
      alwaysRun = true,
      enabled = true)
  public void test001changeLanguageToEnglishTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(4));
    waitingTime();
    click(settingPage.English_btn);
    waitingTime();
    String languageType = getText(settingPage.language_btn);
    log("languageType:" + languageType);
    Assert.assertEquals("Language", languageType);
  }

  @Test(
      groups = {"P0"},
      description = "Change language to Japanese",
      alwaysRun = true,
      enabled = true)
  public void test002changeLanguageToJapaneseTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(4));
    waitingTime();
    click(settingPage.Japanese_btn);
    waitingTime(5);
    String languageType = getText(settingPage.language_btn);
    log("languageType:" + languageType);
    Assert.assertEquals("言語", languageType);
  }

  @Test(
      groups = {"P0"},
      description = "Change language to Chinese",
      alwaysRun = true,
      enabled = true)
  public void test003changeLanguageToChineseTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(4));
    waitingTime(5);
    click(settingPage.Chinese_btn);
    waitingTime(5);
    String languageType = getText(settingPage.language_btn);
    log("languageType:" + languageType);
    Assert.assertEquals("语言", languageType);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
