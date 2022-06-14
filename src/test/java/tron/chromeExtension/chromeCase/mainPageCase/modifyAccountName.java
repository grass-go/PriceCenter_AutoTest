package tron.chromeExtension.chromeCase.mainPageCase;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;

public class modifyAccountName extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "modify account Name",
      alwaysRun = true,
      enabled = true)
  public void test001modifyAccountNameTest() throws Exception {

    int randomNum = (int) (Math.random() * 100000);
    MainPage mainPage = new MainPage(DRIVER);
    String accountName = getText(mainPage.accountName_content).split("\\+")[0];
    String accountNewName = accountName + "+" + randomNum;
    waitingTime(5);
    click(mainPage.more_btn);
    waitingTime(5);
    click(mainPage.modifyAccountName_btn);
    waitingTime(5);
    sendKeys(mainPage.modifyAccountName_input, accountNewName);
    waitingTime(5);
    click(mainPage.modifyAccountNameConfirm_btn);
    waitingTime(5);
    String accountNowName = getText(mainPage.accountName_content);
    log("accountNowName:" + accountNowName);
    Assert.assertEquals(accountNowName, accountNewName);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
