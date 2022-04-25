package tron.chromeExtension.utils;

import javafx.geometry.Dimension2DBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.*;
import org.openqa.selenium.interactions.Actions;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import org.openqa.selenium.JavascriptExecutor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.internal.Utils.log;
import static tron.chromeExtension.base.Base.DRIVER;
import static tron.chromeExtension.base.Base.chain;

public class Helper extends Base {

  public static SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss ");

  public WebElement findWebElement(String element) throws Exception {
    int tries = 0;
    Boolean Element_is_exist = false;
    WebElement el = null;
    while (!Element_is_exist && tries < 3) {
      //            System.out.println("find  ("+  element  +") WElementTimes:" + tries);
      tries++;
      try {
        el = DRIVER.findElementByName(element);
        Element_is_exist = true;
      } catch (NoSuchElementException e) {
        DRIVER.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      }
    }
    if (el != null) {
      return el;
    } else {
      el = DRIVER.findElementById(element);
      return el;
    }
  }

  // is pledge for myself or not.
  public static String pledgeTrxForMyself(String type, String minorHandle) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    TronScanPage tronScanPage = new TronScanPage(DRIVER);
    click(tronScanPage.getSources_btn);
    waitingTime(5);
    if (type.equals("energy")) {
      click(tronScanPage.dropDown_box);
      click(tronScanPage.getVotesAndEnergy_option);
      waitingTime();
    }
    sendKeys(tronScanPage.amount_input, "1");
    waitingTime(5);
    click(tronScanPage.confirmation_box);
    waitingTime(5);
    click(tronScanPage.pledge_btn);
    waitingTime(5);
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    waitingTime(5);
    click(mainPage.signature_btn);
    waitingTime(5);
    switchWindows(minorHandle);
    waitingTime(5);
    String tips = getText(tronScanPage.pledgeSuccess_tips);
    log("tips:" + tips);
    return tips;
  }

  // is pledge for myself or not.
  public static String pledgeTrxForOthers(String type, String minorHandle, String address)
      throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    TronScanPage tronScanPage = new TronScanPage(DRIVER);
    click(tronScanPage.getSources_btn);
    waitingTime(5);
    if (type.equals("energy")) {
      click(tronScanPage.dropDown_box);
      click(tronScanPage.getVotesAndEnergy_option);
      waitingTime();
    }
    sendKeys(tronScanPage.amount_input, "1");
    waitingTime(5);
    Helper.clickAndClearAndInput(tronScanPage.address_input, address);
    waitingTime();
    click(tronScanPage.confirmation_box);
    waitingTime(5);
    click(tronScanPage.pledge_btn);
    waitingTime(5);
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    waitingTime(5);
    click(mainPage.signature_btn);
    waitingTime(5);
    switchWindows(minorHandle);
    waitingTime(5);
    String tips = getText(tronScanPage.pledgeSuccess_tips);
    log("tips:" + tips);
    return tips;
  }

  public void importUsePrivateKey(String privatekey, String name, String pass) {}

  public void findAcceptAndClick() {}

  public static long get721TokenAmountByName(List<WebElement> list, String name) {
    try {
      for (int i = 0; i < list.size(); i++) {
        WebElement temp = list.get(i).findElement(By.className("nameCollection"));
        String fullName = temp.findElement(new By.ByClassName("fullName")).getText();
        if (fullName.equals(name)) {
          return Long.parseLong(list.get(i).findElement(By.className("worthCollection")).getText());
        }
      }
    } catch (Exception e) {
      log("找不到token:" + name);
    }
    return 0;
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

  public static void slidingScrollBar() throws Exception {
    ImportPage importPage = new ImportPage(DRIVER);
    Actions actions = new Actions(DRIVER);
    while (true) {
      actions.sendKeys(importPage.scrollBar, Keys.DOWN).perform(); /*A：滚动条所在元素位置
           * Keys.DOWN：点击键盘下键
           * perform()：确定键盘操作事件，不能省略*/
      // 使用try…catch…来判断元素是否可见，可见就进行元素操作并退出循环
      try {
        //  boolean flag = isElementChecked(importPage.agree_btn, "class", "disabled");
        if (!isElementChecked(importPage.agree_btn, "class", "disabled")) {
          break;
        }
      } catch (Exception e) {
      }
    }
  }

  public static String getAboutUsLink(SettingPage settingPage, Integer i) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(7));
    waitingTime();
    click(settingPage.aboutUsList.get(i));
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    String url = DRIVER.getCurrentUrl();
    log("privacyPolicy:" + url);
    DRIVER.close();
    return url;
  }

  public static void clickAndClearAndInput(WebElement webElement, String input) throws Exception {
    click(webElement);
    waitingTime();
    clear(webElement);
    waitingTime();
    sendKeys(webElement, input);
    waitingTime();
  }

  public static String getNodeName(List<WebElement> list) {
    int size = list.size();
    WebElement r1 = list.get(size - 1).findElement(By.className("r1"));
    String nodeName = r1.getText().substring(0, 6);
    log("nodeName:" + nodeName);
    return nodeName;
  }

  public static String getFullNodeStr(List<WebElement> list) {
    int size = list.size();
    WebElement r2 = list.get(size - 1).findElement(By.className("r2"));
    List<WebElement> cellList = r2.findElements(By.className("cell"));
    List<WebElement> cell1SpanList = cellList.get(0).findElements(By.tagName("span"));
    String fullNodeStr = cell1SpanList.get(1).getText();
    log("fullNodeStr:" + fullNodeStr);
    return fullNodeStr;
  }

  public static String getEventServerStr(List<WebElement> list) {
    int size = list.size();
    WebElement r2 = list.get(size - 1).findElement(By.className("r2"));
    List<WebElement> cellList = r2.findElements(By.className("cell"));
    List<WebElement> cell2SpanList = cellList.get(1).findElements(By.tagName("span"));
    String eventServerStr = cell2SpanList.get(1).getText();
    log("eventServerStr:" + eventServerStr);
    return eventServerStr;
  }

  public static String getTokenAmountByName(List<WebElement> list, String name) {
    try {
      for (int i = 0; i < list.size(); i++) {
        WebElement temp = list.get(i).findElement(By.className("name"));
        String tokenName = temp.findElement(new By.ByTagName("span")).getText();
        if (tokenName.equals(name)) {
          WebElement worth = list.get(i).findElement(By.className("worth"));
          List<WebElement> spanList = worth.findElements(new By.ByTagName("span"));
          return spanList.get(0).getText();
        }
      }
    } catch (Exception e) {
      log("找不到token:" + name);
    }

    return "找不到token:" + name;
  }

  public static boolean containElement(WebElement wl, String name) {
    try {
      wl.findElement(By.name(name));
      return true;
    } catch (org.openqa.selenium.NoSuchElementException ex) {
      try {
        wl.findElement(By.id(name));
        return true;
      } catch (org.openqa.selenium.NoSuchElementException eex) {
        try {
          WebElement btn = wl.findElement(By.className("XCUIElementTypeButton"));
          if (btn.getText() == name) {
            return true;
          } else {
            return false;
          }
        } catch (org.openqa.selenium.NoSuchElementException evex) {
          return false;
        }
      }
    }
  }

  public static boolean isElementExist(String name) {
    try {
      DRIVER.findElementByName(name);
      System.out.println("IsFindByName: " + name);
      return true;
    } catch (org.openqa.selenium.NoSuchElementException ex) {
      try {
        DRIVER.findElementById(name);
        System.out.println("IsFindById: " + name);

        return true;
      } catch (org.openqa.selenium.NoSuchElementException eex) {
        try {
          if (DRIVER.findElementByClassName("XCUIElementTypeButton").getText().contains(name)) {
            System.out.println("IsFindByBtn: " + name);
            return true;
          } else {
            return false;
          }
        } catch (org.openqa.selenium.NoSuchElementException e) {
          System.out.println("NotFound: " + name);
          return false;
        }
      }
    }
  }

  // Screenshot method.
  public void screenShotByTakesScreenshot() {
    // 执行屏幕截图操作
    File srcFile = ((TakesScreenshot) DRIVER).getScreenshotAs(OutputType.FILE);
    // 通过FileUtils中的copyFile()方法保存getScreenshotAs()返回的文件;"屏幕截图"即时保存截图的文件夹
    try {
      FileUtils.copyFile(srcFile, new File("D:\\screenshot\\通过TakesScreenshot截图.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Compare whether the two pictures are the same
  public void screenShotByTakesScreenshot1() {}

  public static boolean contentTexts(List<WebElement> list, String name) {
    if (list.size() < 1) return false;
    for (int i = 0; i < list.size(); i++) {
      System.out.println(
          "totalSize:" + list.size() + " item:" + i + "name:" + list.get(i).getText());
      if (list.get(i).getText().contains(name)) {
        return true;
      }
    }
    return false;
  }
}
