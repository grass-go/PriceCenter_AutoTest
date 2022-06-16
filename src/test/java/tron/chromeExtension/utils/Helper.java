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

  // multiSignature
  public static String multiSignatureInitiation(
      String permission,
      String minorHandle,
      String toAddress,
      int index,
      String amount,
      boolean flag)
      throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    TronScanPage tronScanPage = new TronScanPage(DRIVER);
    waitingTime();
    click(tronScanPage.accountAddress_btn);
    Helper.click(tronScanPage.multiSignature_btn);
    waitingTime();
    int i = 10;
    while (i > 0) {
      try {
        click(tronScanPage.permission_input);
        if (permission.equals("owner")) {
          click(tronScanPage.ownerPermission_option);
        } else {
          click(tronScanPage.activePermission_option);
        }
        break;
      } catch (Exception e) {
        i--;
      }
    }
    waitingTime(5);
    sendKeys(tronScanPage.receive_input, toAddress);
    waitingTime(5);

    waitingTime();
    int j = 10;
    if (index == 5) {
      while (j > 0) {
        try {
          click(tronScanPage.token_box);
          click(tronScanPage.token_list.get(index));
          waitingTime();
          click(tronScanPage.collection_box);
          click(tronScanPage.trc721Token_btn);
          break;
        } catch (Exception e) {
          j--;
          waitingTime(1);
        }
      }
    } else {
      while (j > 0) {
        try {
          click(tronScanPage.token_box);
          click(tronScanPage.token_list.get(index));
          waitingTime();
          clickAndClearAndInput(tronScanPage.tokenAccount_input, amount);
          waitingTime();
          break;
        } catch (Exception e) {
          j--;
          waitingTime(1);
        }
      }
    }

    waitingTime();
    click(tronScanPage.transferConfirm_btn);
    waitingTime();
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    if (flag) {
      click(mainPage.signature_btn);
    } else {
      click(mainPage.cancelSignature_btn);
    }
    waitingTime(5);
    switchWindows(minorHandle);
    String tips = null;
    if (flag) {
      tips = getText(tronScanPage.signSuccess_tips);
    } else {
      tips = getText(tronScanPage.cancelSign_tips);
    }
    log("tips:" + tips);
    return tips;
  }

  // multiSignature
  public static String multiSignature(String minorHandle, boolean flag) throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    TronScanPage tronScanPage = new TronScanPage(DRIVER);
    waitingTime();
    click(tronScanPage.confirm_btn);
    waitingTime();
    click(tronScanPage.multiSignTransactionTab_btn);
    click(tronScanPage.toBeSignedTab_btn);
    waitingTime();
    click(tronScanPage.sign_btn);
    waitingTime();
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    if (flag) {
      click(mainPage.signature_btn);
    } else {
      click(mainPage.cancelSignature_btn);
    }
    waitingTime(5);
    switchWindows(minorHandle);
    String tips = null;
    if (flag) {
      tips = getText(tronScanPage.multipleSignaturesSucceeded_tips);
    } else {
      tips = getText(tronScanPage.multipleSignaturesFailed_tips);
    }

    log("tips:" + tips);
    return tips;
  }

  /* public static boolean switchChain(String chainName, int index) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    try {
      if (chainName.contains("nile")) {
        mainPage.selectedChain_btn.click();
        DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        waitingTime(2);
        mainPage.chainList.get(index).click();
        waitingTime(5);
      }
    } catch (Exception e) {
      log("Change chain Failed!");
      return false;
    }
    return true;
  }*/

  public static boolean switchChain(String chainName, int index) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    try {

      mainPage.selectedChain_btn.click();
      DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      waitingTime(2);
      mainPage.chainList.get(index).click();
      waitingTime(5);

    } catch (Exception e) {
      log("Change chain Failed!");
      return false;
    }
    return true;
  }

  // Switch account.
  public static boolean switchAccount(String index, String switchToAddress) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    AccountListPage accountlistPage = new AccountListPage(DRIVER);
    int times = 10;
    while (times > 0) {
      try {
        log("switchToAddress:" + switchToAddress);
        Helper.closeWindow(accountlistPage.close_btn);
        click(mainPage.switchAccount_btn);
        waitingTime(5);
        click(accountlistPage.account_list.get(Integer.parseInt(index)));
        waitingTime(5);
        break;
      } catch (Exception e) {
        times--;
        log("Switch account failed!");
      }
    }

    return onTheHomepageOrNot(switchToAddress);
  }

  // Transfer.
  public static String transfer(
      String receiveAddress, String searchContent, String amount, Boolean isTrc721)
      throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    SendPage sendPage = new SendPage(DRIVER);
    waitingTime();
    click(mainPage.transfer_btn);
    waitingTime();
    sendKeys(sendPage.receiverAddress_input, receiveAddress);
    waitingTime();
    click(sendPage.next_btn);
    waitingTime();
    click(sendPage.trx_neirong);
    sendKeys(sendPage.search_input, searchContent);
    waitingTime();
    click(sendPage.search_result);
    waitingTime();
    if (!isTrc721) {
      sendKeys(sendPage.amount_input, amount);
      waitingTime();
    }
    click(sendPage.transfer_btn);
    waitingTime();
    click(sendPage.signature_btn);
    waitingTime(5);
    String transactionStatus = getText(sendPage.transactionStatus);
    click(sendPage.complete_btn);

    return transactionStatus;
  }

  // vote
  public static String vote(boolean flag, String minorHandle) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    TronScanPage tronScanPage = new TronScanPage(DRIVER);
    waitingTime();
    click(tronScanPage.beforeEnteringVotes_btn);
    waitingTime(5);
    Helper.clickAndClearAndInput(tronScanPage.vote_input, "1");
    waitingTime();
    click(tronScanPage.votesEntered_btn);
    waitingTime();
    click(tronScanPage.confirmVote_btn);
    waitingTime();
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    waitingTime(5);
    if (flag) {
      click(mainPage.signature_btn);
    } else {
      click(mainPage.cancelSignature_btn);
    }
    waitingTime();
    switchWindows(minorHandle);
    waitingTime(5);
    String tips = getText(tronScanPage.voteSuccessOrFailed_tips);
    log("tips:" + tips);
    return tips;
  }

  // Is pledge for myself or not.
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

  // Is pledge for myself or not.
  public static String exchangeToken(String searchedToken, String minorHandle) throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    SunSwapPage tronScanPage = new SunSwapPage(DRIVER);
    /* click(tronScanPage.connectAccount_btn);
    waitingTime(5);*/
    sendKeys(tronScanPage.amount_input, "1");
    waitingTime(5);
    click(tronScanPage.dropDown_btn);
    waitingTime(5);
    sendKeys(tronScanPage.search_input, searchedToken);
    waitingTime(5);
    click(tronScanPage.searchedTokenList.get(0));
    waitingTime(5);
    click(tronScanPage.exchange_btn);
    waitingTime(5);
    click(tronScanPage.confirmExchange_btn);
    String majorHandle = DRIVER.getWindowHandle();
    switchWindows(majorHandle);
    waitingTime(5);
    click(mainPage.cancelSignature_btn);
    waitingTime(5);
    switchWindows(minorHandle);
    waitingTime(5);
    String errorMsg = getText(tronScanPage.errorMsg);
    log("errorMsg:" + errorMsg);
    return errorMsg;
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

  public static void slidingScrollBar() throws Exception {
    ImportPage importPage = new ImportPage(DRIVER);
    Actions actions = new Actions(DRIVER);
    while (true) {
      actions.sendKeys(importPage.scrollBar, Keys.DOWN).perform();
      try {
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
