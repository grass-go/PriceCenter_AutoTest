package tron.chromeExtension.chromeCase.mainPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;

public class vote extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = " Successful vote. ",
      alwaysRun = true,
      enabled = true)
  public void test001SuccessfulVoteTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.vote_btn);
    waitingTime();
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    String tips = Helper.vote(true, minorHandle);
    Assert.assertTrue(tips.contains("投票成功"));
  }

  @Test(
      groups = {"P0"},
      description = "Cancel voting. ",
      alwaysRun = true,
      enabled = true)
  public void test002CancelVotingTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.vote_btn);
    waitingTime();
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    String tips = Helper.vote(false, minorHandle);
    Assert.assertTrue(tips.contains("投票失败"));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
