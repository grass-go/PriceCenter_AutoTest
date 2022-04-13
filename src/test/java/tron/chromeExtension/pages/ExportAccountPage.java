package tron.chromeExtension.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

  // 密码输入框
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement password_input;
  // 密码错误提示
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[2]/div[2]")
  public WebElement tips;

  // 取消键
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[3]/button[1]/span")
  public WebElement cancel_btn;
  //  确认键
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[3]/button[2]/span")
  public WebElement confirmExport_btn;

  // 复制按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div[1]/span")
  public WebElement copy_btn;
  // 二维码按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div[2]/span")
  public WebElement QRCode_btn;

  // 助记词二维码内容
  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div/div[2]/svg")
  public WebElement QRCode_content;

  // 助记词关闭二维码按钮
  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div/div[2]/div/button")
  public WebElement close_btn;

  // 导出账户按钮
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[2]/span[2]")
  public WebElement exportAccount_btn;
  // 导出助记词按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/span")
  public WebElement exportMnemonicWords_btn;

  // 导出私钥按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/span")
  public WebElement exportPrivateKeys_btn;

  // 导出Keystore按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/span")
  public WebElement exportKeystore_btn;

  // 开始备份按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/button/span")
  public WebElement startBackup_btn;

  // 查看助记词/私钥按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div/div/div/button/span")
  public WebElement view_btn;

  // 我已安全备份按钮
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
      // 点击导出助记词/私钥/keystone
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
      System.out.println("result:" + result);
      System.out.println("actual:" + actual);
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
      // 点击导出助记词/私钥/keystone
      webElement.click();
      TimeUnit.SECONDS.sleep(5);
      password_input.sendKeys(passwordWrong);
      confirmExport_btn.click();
      TimeUnit.SECONDS.sleep(5);
      System.out.println("exportAccountTips:" + exportAccountTips);
      System.out.println("tips:" + tips.getText());
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
