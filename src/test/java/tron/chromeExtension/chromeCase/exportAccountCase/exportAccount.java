package tron.chromeExtension.chromeCase.exportAccountCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.ExportAccountPage;
import tron.chromeExtension.pages.MainPage;
import tron.common.utils.MyIRetryAnalyzer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;

public class exportAccount extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Export  mnemonic words",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001exportMnemonicWordsTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    ExportAccountPage exportAccountPage = new ExportAccountPage(DRIVER);
    String result =
        exportAccountPage.exportAccount(
            mainPage, exportAccountPage.exportMnemonicWords_btn, mnemonicWords);
    log("result:" + result);
    // Click  QR code button.
    click(exportAccountPage.QRCode_btn);
    // todo:QRCode_content 路径不对
    // String QRCode_content = getText(exportAccountPage.QRCode_content);
    // log("QRCode_content:" + QRCode_content);
    click(exportAccountPage.close_btn);
    waitingTime(5);
    click(mainPage.backedUp_btn);
    // todo:后续：点击验证助记词，4.0补充该逻辑
  }

  @Test(
      groups = {"P0"},
      description = "Export  private key",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002exportPrivateKeyTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    ExportAccountPage exportAccountPage = new ExportAccountPage(DRIVER);
    String result =
        exportAccountPage.exportAccount(
            mainPage, exportAccountPage.exportPrivateKeys_btn, loginPrivateKey);
    log("result" + result);

    // Click  QR code button.
    click(exportAccountPage.QRCode_btn);
    // todo:QRCode_content 路径不对
    // String QRCode_content = getText(exportAccountPage.QRCode_content);
    // log("QRCode_content:" + QRCode_content);
    click(exportAccountPage.close_btn);
    waitingTime(5);
    click(mainPage.backedUp_btn);
    // 验证回到首页
    waitingTime(5);
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @Test(
      groups = {"P0"},
      description = "Export  keystore",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003exportKeystoreTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    ExportAccountPage exportAccountPage = new ExportAccountPage(DRIVER);
    exportAccountPage.exportAccount(mainPage, exportAccountPage.exportKeystore_btn);
    waitingTime(5);
    // Click copy button.
    click(exportAccountPage.copy_btn);
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    String keyStore = fetchClipboardContents(clip);
    log("keyStore :" + keyStore);
    log("loginKeyStore:" + loginKeyStore);
    Assert.assertNotNull(keyStore);
    Assert.assertTrue(keyStore.contains(testAddressBase58));
    // Click  QR code button.
    click(exportAccountPage.QRCode_btn);
    // todo:QRCode_content 路径不对
    // String QRCode_content = getText(exportAccountPage.QRCode_content);
    // log("QRCode_content:" + QRCode_content);
    click(exportAccountPage.close_btn);
    waitingTime(5);
    click(mainPage.backedUp_btn);
    waitingTime(5);
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
