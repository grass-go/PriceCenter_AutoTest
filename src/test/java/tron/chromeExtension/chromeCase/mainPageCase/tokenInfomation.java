package tron.chromeExtension.chromeCase.mainPageCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AllAssetsPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;

public class tokenInfomation extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = "Verify that the TRX icon has a recommendation flag.",
      alwaysRun = true,
      enabled = true)
  public void test001TRXIconHasARecommendationFlag() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(3);
    WebElement trxDiv = mainPage.token_list.get(0);
    waitingTime(3);
    Assert.assertTrue(
        trxDiv
            .findElement(new By.ByClassName("logo"))
            .findElements(By.cssSelector("img[alt]"))
            .get(1)
            .getAttribute("src")
            .contains("/static/media/icon-verify.d25d69e8.svg"));
  }

  @Test(
      groups = {"P0"},
      description = "Verify that the TRX icon has a Dominic French Coins flag.",
      alwaysRun = true,
      enabled = true)
  public void test002TokenFromHomePageIconHasADominicFrenchCoinsFlag() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(3);
    for (int i = 0; i < mainPage.token_list.size(); i++) {
      WebElement trxDiv = mainPage.token_list.get(i);
      waitingTime(3);
      Assert.assertNotNull(
          trxDiv
              .findElement(new By.ByClassName("nameWrap"))
              .findElements(By.className("nationalFlag")));
    }
  }

  @Test(
      groups = {"P0"},
      description = "Verify that the TRX icon has a Dominic French Coins flag.",
      alwaysRun = true,
      enabled = true)
  public void test003TokenFromSearchIconHasADominicFrenchCoinsFlag() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    AllAssetsPage allAssetsPage = new AllAssetsPage(DRIVER);
    waitingTime(3);
    click(mainPage.allAssets_btn);
    waitingTime(3);
    Helper.click(allAssetsPage.tokenSearch_input);
    waitingTime(3);
    sendKeys(allAssetsPage.tokenSearch1_input, "USDD");
    WebElement trxDiv = mainPage.token_list.get(0);
    waitingTime(3);
    Assert.assertNotNull(
        trxDiv
            .findElement(new By.ByClassName("nameWrap"))
            .findElements(By.className("nationalFlag")));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
