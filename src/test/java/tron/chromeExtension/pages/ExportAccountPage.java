package tron.chromeExtension.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.utils.Helper;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.concurrent.TimeUnit;

import static tron.chromeExtension.base.Base.*;

/** mine page */
public class ExportAccountPage extends AbstractPage {

  public ExportAccountPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  // Password input box.
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement password_input;
  // Password error prompt.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[2]/div[2]")
  public WebElement tips;

  // Cancel key.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[3]/button[1]/span")
  public WebElement cancel_btn;
  //  Confirm key.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[3]/button[2]/span")
  public WebElement confirmExport_btn;

  // Copy button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div[1]/span")
  public WebElement copy_btn;
  // QR code button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div[2]/span")
  public WebElement QRCode_btn;

  // Mnemonic QR code content.
  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div/div[2]/svg")
  public WebElement QRCode_content;

  // Mnemonic close QR code button.
  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div/div[2]/div/button")
  public WebElement close_btn;

  // Export account button.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[2]/span[2]")
  public WebElement exportAccount_btn;
  // Export mnemonic button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/span")
  public WebElement exportMnemonicWords_btn;

  // Export private key button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/span")
  public WebElement exportPrivateKeys_btn;

  // Export keystore button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/span")
  public WebElement exportKeystore_btn;

  // Start Backup button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/button/span")
  public WebElement startBackup_btn;

  // View mnemonic / private key button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div/div/button/span")
  public WebElement view_btn;

  // I have the secure backup button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/button/span")
  public WebElement backedUp_btn;

  public String exportAccount(MainPage mainPage, WebElement webElement, String actual)
      throws Exception {
    String result = null;
    try {

      TimeUnit.SECONDS.sleep(5);
      mainPage.more_btn.click();
      TimeUnit.SECONDS.sleep(5);
      mainPage.exportAccount_btn.click();
      TimeUnit.SECONDS.sleep(5);
      // Click export mnemonic / private key / keystone.
      webElement.click();
      TimeUnit.SECONDS.sleep(5);
      password_input.sendKeys(passwordWrong);
      confirmExport_btn.click();
      Assert.assertEquals(exportAccountTips, tips.getText());
      cancel_btn.click();
      TimeUnit.SECONDS.sleep(5);
      webElement.click();
      TimeUnit.SECONDS.sleep(5);
      password_input.sendKeys(password);
      confirmExport_btn.click();
      startBackup_btn.click();
      view_btn.click();
      copy_btn.click();
      Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
      result = fetchClipboardContents(clip);
      Base.log("result:" + result);
      Base.log("actual:" + actual);
      Assert.assertNotNull(result);
      Assert.assertEquals(actual, result);
    } catch (Exception e) {

    }
    return result;
  }

  public void exportAccount(MainPage mainPage, WebElement webElement) throws Exception {
    String result = null;
    try {

      TimeUnit.SECONDS.sleep(5);
      mainPage.more_btn.click();
      TimeUnit.SECONDS.sleep(5);
      mainPage.exportAccount_btn.click();
      TimeUnit.SECONDS.sleep(5);
      // Click export mnemonic / private key / keystone.
      webElement.click();
      TimeUnit.SECONDS.sleep(5);
      password_input.sendKeys(passwordWrong);
      confirmExport_btn.click();
      TimeUnit.SECONDS.sleep(5);
      Base.log("exportAccountTips:" + exportAccountTips);
      Base.log("tips:" + tips.getText());
      Assert.assertEquals(exportAccountTips, tips.getText());
      cancel_btn.click();
      TimeUnit.SECONDS.sleep(5);
      webElement.click();
      TimeUnit.SECONDS.sleep(5);
      password_input.sendKeys(password);
      confirmExport_btn.click();
      TimeUnit.SECONDS.sleep(5);
      startBackup_btn.click();
      TimeUnit.SECONDS.sleep(5);
      view_btn.click();
      TimeUnit.SECONDS.sleep(5);

    } catch (Exception e) {

    }
  }
}
