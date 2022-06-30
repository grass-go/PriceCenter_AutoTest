package tron.chromeExtension.base;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.Configuration;
import tron.chromeExtension.pages.*;

public class Base {

  public static ChromeDriver DRIVER;
  static String UNIQUEID =
      Configuration.getByPath("testng.conf").getString("chromeExtension.uniqueId");
  static String PAGEPATH =
      Configuration.getByPath("testng.conf").getString("chromeExtension.pagePath");
  static String userDataDir =
      Configuration.getByPath("testng.conf").getString("chromeExtension.userDataDir");
  static String extensionDir =
      Configuration.getByPath("testng.conf").getString("chromeExtension.extensionDir");
  public static String loginAddress =
      Configuration.getByPath("testng.conf").getString("chromeExtension.loginAddress");
  public static Object loginKeyStore =
      Configuration.getByPath("testng.conf").getObject("chromeExtension.loginKeyStore");
  public static String testAddress =
      Configuration.getByPath("testng.conf").getString("chromeExtension.testAddress");
  public static String exportAccountTips =
      Configuration.getByPath("testng.conf").getString("chromeExtension.exportAccountTips");
  public static String lockPageTips1 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.lockPageTips1");
  public static String lockPageTips2 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.lockPageTips2");
  public static String duplicateAddressTips =
      Configuration.getByPath("testng.conf").getString("chromeExtension.duplicateAddressTips");
  public static String testAddressBase58 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.testAddressBase58");
  public static String password =
      Configuration.getByPath("testng.conf").getString("chromeExtension.password");
  public static String passwordWrong =
      Configuration.getByPath("testng.conf").getString("chromeExtension.passwordWrong");
  public static String mnemonicWords =
      Configuration.getByPath("testng.conf").getString("chromeExtension.mnemonicWords");
  public static String loginPrivateKey =
      Configuration.getByPath("testng.conf").getString("chromeExtension.loginPrivateKey");
  public static String testAccountOneIndex =
      Configuration.getByPath("testng.conf").getString("chromeExtension.testAccountOneIndex");
  public static String testAccountTwoIndex =
      Configuration.getByPath("testng.conf").getString("chromeExtension.testAccountTwoIndex");
  public static String testAccountMultiIndex =
      Configuration.getByPath("testng.conf").getString("chromeExtension.testAccountMultiIndex");

  public String accountAddress001 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountAddress001");
  public String accountKey001 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountKey001");
  public String accountMnemonicWords001 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountMnemonicWords001");
  public String accountAddress002 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountAddress002");
  public String accountKey002 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountKey002");
  public String multiAddress =
      Configuration.getByPath("testng.conf").getString("chromeExtension.multiAddress");

  public String URL = "chrome-extension://" + UNIQUEID + PAGEPATH;
  public static String privacyReportUrlCn =
      Configuration.getByPath("testng.conf").getString("link.privacyReportUrlCn");

  public static String privacyReportUrlEn =
      Configuration.getByPath("testng.conf").getString("link.privacyReportUrlEn");
  public static String privacyReportUrlJp =
      Configuration.getByPath("testng.conf").getString("link.privacyReportUrlJp");
  public static String auditReport =
      Configuration.getByPath("testng.conf").getString("link.auditReport");
  public static String websiteCn =
      Configuration.getByPath("testng.conf").getString("link.websiteCn");
  public static String website = Configuration.getByPath("testng.conf").getString("link.website");
  public static String supportCenterCn =
      Configuration.getByPath("testng.conf").getString("link.supportCenterCn");
  public static String supportCenter =
      Configuration.getByPath("testng.conf").getString("link.supportCenter");
  public static String contactUsCn =
      Configuration.getByPath("testng.conf").getString("link.contactUsCn");
  public static String contactUs =
      Configuration.getByPath("testng.conf").getString("link.contactUs");
  public static String chainNile =
      Configuration.getByPath("test.conf").getString("chromeExtension.chainNile");
  public static String chainMain =
      Configuration.getByPath("test.conf").getString("chromeExtension.chainMain");
  public static String nowVersion =
      Configuration.getByPath("testng.conf").getString("chromeExtension.nowVersion");
  ChromeOptions OPTION = new ChromeOptions();
  public static SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss ");
  public static String addressBookName = "自动化测试账户1";

  @BeforeSuite
  public void beforeSuit() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
    //  Assert.assertTrue(loginAccount(chainNile));
    Assert.assertTrue(Helper.switchAccount(testAccountOneIndex, loginAddress));
  }

  public void setUpChromeDriver() throws Exception {
    killChromePid();
    try {
      // Set to headless mode (required)
      // OPTION.addArguments("--headless");
      OPTION.addArguments("start-maximized");
      OPTION.addArguments("--user-data-dir=" + userDataDir);
      OPTION.addArguments("load-extension=" + extensionDir);
      OPTION.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
      OPTION.addArguments("--enable-extensions");
      OPTION.addArguments("--verbose");
      log("OPTION:" + OPTION.toString());
      DRIVER = new ChromeDriver(OPTION);
      DRIVER.manage().window().maximize();
    } catch (Exception e) {
      log("Setup Chromedriver exception!");
      e.printStackTrace();
    }
  }

  public boolean loginAccount(String network) throws Exception {
    Integer retryLoginTimes = 1;
    while (retryLoginTimes > 0) {
      try {
        retryLoginTimes--;
        DRIVER.get(URL);
        try {
          if (new MainPage(DRIVER).send_btn.isEnabled()) {
            return true;
          }
        } catch (Exception e) {
        }
        DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(DRIVER);
        MainPage mainPage = new MainPage(DRIVER);
        loginPage.password_input.sendKeys(password);
        DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        loginPage.login_btn.click();
        DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        closeWindow(new AccountListPage(DRIVER).close_btn);
        // Switch chain to nile.
        /*if(network.equals("main")){
            Assert.assertTrue(Helper.switchChain(chain, 0));
        }else{
            Assert.assertTrue(Helper.switchChain(chain, 3));
        }*/

        switch (network) {
          case "main":
            Assert.assertTrue(Helper.switchChain(network, 0));
            break;
          case "nile":
            Assert.assertTrue(Helper.switchChain(network, 3));
            break;
          default:
            Assert.assertTrue(Helper.switchChain(network, 3));
        }

        // Switch account.
        // Assert.assertTrue(Helper.switchAccount(testAccountOneIndex, loginAddress));
        String totalBalanceStr = mainPage.accountTotalBalance.getText().substring(1);
        double totalBalance = getBalanceFromSelectionBtn(totalBalanceStr);
        if (totalBalance > 0) {
          return true;
        }
        // DRIVER.quit();
      } catch (Exception e) {
        e.printStackTrace();
        DRIVER.quit();
        setUpChromeDriver();
      }
    }
    return false;
  }

  public boolean importAccount() throws Exception {
    Integer retryLoginTimes = 1;
    while (retryLoginTimes > 0) {
      retryLoginTimes--;
      DRIVER.get(URL);
    }
    return false;
  }

  public void logoutAccount() throws Exception {
    DRIVER.quit();
  }

  public static void closeWindow(WebElement webElement) throws Exception {
    try {
      if (webElement.isDisplayed()) {
        webElement.click();
      }
    } catch (org.openqa.selenium.NoSuchElementException ex) {
      log("No Such Element!");
    }
  }

  public static String getText(WebElement webElement) throws Exception {
    return webElement.getText();
  }

  public static String getTextWithDefaultValue(WebElement webElement) throws Exception {
    return webElement.getAttribute("value");
  }

  public static void clear(WebElement webElement) throws Exception {
    webElement.sendKeys(Keys.chord(Keys.COMMAND, "a"));
    webElement.sendKeys(Keys.DELETE);
  }

  public static void click(WebElement webElement) throws Exception {
    webElement.click();
  }

  public static Boolean isSelected(WebElement webElement) throws Exception {
    return webElement.isSelected();
  }

  public static Boolean isElementChecked(WebElement webElement, String eleKey, String eleValue)
      throws Exception {
    Boolean isSelectOrNot = null;
    try {
      // String temp = webElement.getAttribute(eleKey);
      isSelectOrNot = webElement.getAttribute(eleKey).contains(eleValue);
    } catch (org.openqa.selenium.NoSuchElementException e) {
      log("No such element!");
    }
    return isSelectOrNot;
  }

  public WebElement findElementByName(String name) throws Exception {
    WebElement webElement = DRIVER.findElementByName(name);
    return webElement;
  }

  public WebElement findElementById(String id) throws Exception {
    WebElement webElement = DRIVER.findElementById(id);
    return webElement;
  }

  public WebElement findElementByXPath(String XPath) throws Exception {
    WebElement webElement = DRIVER.findElementByXPath(XPath);
    return webElement;
  }

  public WebElement findElementByClassName(String ClassName) throws Exception {
    WebElement webElement = DRIVER.findElementByClassName(ClassName);
    return webElement;
  }

  public static void waitingTime(long time) throws InterruptedException {
    TimeUnit.SECONDS.sleep(time);
  }

  public static void waitingTime() throws InterruptedException {
    waitingTime(2);
  }

  public static void log(String log) {
    String time = timeStamp.format(new Date()).toString();
    System.out.println(time + ": " + log);
  }

  public static void sendKeys(WebElement webElement, String str) {
    webElement.sendKeys(str);
  }

  public double getBalanceFromSelectionBtn(String str) {
    String value = str;
    if (str.contains(",")) {
      value = str.replace(",", "");
    }
    return Double.parseDouble(value);
  }

  public static void switchWindows(String targetHandle) throws InterruptedException {
    Set<String> handles = DRIVER.getWindowHandles();
    for (String i : handles) {
      if (!targetHandle.equals(i)) {
        DRIVER.switchTo().window(i);
        waitingTime(5);
      }
    }
  }

  //  Copy clipboard contents and return string
  public static String fetchClipboardContents(Clipboard clip)
      throws IOException, UnsupportedFlavorException {
    // Get the contents of the clipboard
    Transferable clipT = clip.getContents(null);
    if (clipT != null) {
      // Check whether the content is of text type
      if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
        return (String) clipT.getTransferData(DataFlavor.stringFlavor);
    } else {
      log("Clipboard content is empty!");
    }
    return null;
  }

  // Verify back to home page
  public static Boolean onTheHomepageOrNot(String address) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    TimeUnit.SECONDS.sleep(5);
    click(mainPage.copy_btn);
    TimeUnit.SECONDS.sleep(5);
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    String accountAddress = fetchClipboardContents(clip);
    log("accountAddress: " + accountAddress);
    Assert.assertNotNull(accountAddress);
    if (address.equals(accountAddress)) {
      return true;
    }
    return false;
  }

  public static void killChromePid() throws IOException {
    Process process = Runtime.getRuntime().exec("sh kill_chrome.sh");
  }
}
