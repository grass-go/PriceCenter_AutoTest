package tron.chromeExtension.chromeCase.mainPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AllAssetsPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;

public class allAssets extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = " Enter all assets page successfully.",
      alwaysRun = true,
      enabled = true)
  public void test001enterAllAssetsPageTest() throws Exception {
        MainPage mainPage = new MainPage(DRIVER);
        AllAssetsPage allAssetsPage = new AllAssetsPage(DRIVER);
        waitingTime(3);
        click(mainPage.allAssets_btn);
        Assert.assertEquals("全部资产", getText(allAssetsPage.allAssets_title));
    }

  @Test(
      groups = {"P0"},
      description = " The details of assets not concerned are displayed normally.",
      alwaysRun = true,
      enabled = true)
  public void test002detailsOfAssetsNotConcernedTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    AllAssetsPage allAssetsPage = new AllAssetsPage(DRIVER);
    waitingTime(5);
    click(mainPage.allAssets_btn);
    Assert.assertEquals("全部资产", getText(allAssetsPage.allAssets_title));
    click(allAssetsPage.tokenList.get(2));
    Assert.assertEquals("Tether USD", getText(allAssetsPage.specificAsset_title));
    waitingTime(5);
    click(allAssetsPage.details_btn);
    Assert.assertEquals("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t",getText(allAssetsPage.contract_address));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
