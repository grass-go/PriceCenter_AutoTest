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
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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
  public String accountAddress001 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountAddress001");
  public String accountKey001 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountKey001");
  public String accountAddress002 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountAddress002");
  public String accountKey002 =
      Configuration.getByPath("testng.conf").getString("chromeExtension.accountKey002");
  public String URL = "chrome-extension://" + UNIQUEID + PAGEPATH;
  public static String chain =
      Configuration.getByPath("test.conf").getString("chromeExtension.chain");
  ChromeOptions OPTION = new ChromeOptions();
  public static SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss ");

  public void setUpChromeDriver() throws Exception {
    killChromePid();
    try {
      // Set to headless mode (required)
      // OPTION.addArguments("--headless");
      OPTION.addArguments("--user-data-dir=" + userDataDir);
      OPTION.addArguments("load-extension=" + extensionDir);
      OPTION.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
      OPTION.addArguments("--enable-extensions");
      OPTION.addArguments("--verbose");
      log("OPTION:" + OPTION.toString());
      DRIVER = new ChromeDriver(OPTION);
    } catch (Exception e) {
      log("Setup Chromedriver exception!");
      e.printStackTrace();
    }
  }

  public void switchToTestAccount() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    AccountListPage accountlistPage = new AccountListPage(DRIVER);
    click(mainPage.switchAccount_btn);
    waitingTime(5);
    click(accountlistPage.account_list.get(Integer.parseInt(testAccountOneIndex)));
    waitingTime(5);
  }

  public boolean loginAccount() throws Exception {
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
        switchToTestAccount();
        try {
          if (chain.contains("Nile")) {
            mainPage.selectedChain_btn.click();
            DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            waitingTime(2);
            mainPage.chainList.get(3).click();
            waitingTime(5);
          }
        } catch (Exception e) {
          log("Change chain Failed!");
        }

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

  public void changeChain() throws InterruptedException {
    MainPage mainPage = new MainPage(DRIVER);
    mainPage.selectedChain_btn.click();
    waitingTime();
    if (chain.equals("nile")) {
      try {
        for (int i = 0; i < mainPage.chainList.size(); i++) {
          WebElement temp = mainPage.chainList.get(i).findElement(By.className("content"));
          String chainName = temp.findElement(new By.ByTagName("span")).getText();
          if (chain.equals(chainName)) {
            mainPage.chainList.get(i).click();
            waitingTime();
            break;
          }
        }
      } catch (Exception e) {
        log("Change chain failed");
      }
    }
  }

  public void logoutAccount() throws Exception {
    DRIVER.quit();
  }

  public void closeWindow(WebElement webElement) throws Exception {
    try {
      if (webElement.isDisplayed()) {
        webElement.click();
      }
    } catch (org.openqa.selenium.NoSuchElementException ex) {
      log("No Such Element!");
    }
  }

  public String getText(WebElement webElement) throws Exception {
    return webElement.getText();
  }

  public void click(WebElement webElement) throws Exception {
    webElement.click();
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

  public void waitingTime(long time) throws InterruptedException {
    TimeUnit.SECONDS.sleep(time);
  }

  public void waitingTime() throws InterruptedException {
    waitingTime(2);
  }

  public static void log(String log) {
    String time = timeStamp.format(new Date()).toString();
    System.out.println(time + ": " + log);
  }

  public void sendKeys(WebElement webElement, String str) {
    webElement.sendKeys(str);
  }

  public double getBalanceFromSelectionBtn(String str) {
    String value = str;
    if (str.contains(",")) {
      value = str.replace(",", "");
    }
    return Double.parseDouble(value);
  }

  //  Copy clipboard contents and return string
  public String fetchClipboardContents(Clipboard clip)
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
  public Boolean onTheHomepageOrNot(String address) throws Exception {
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
