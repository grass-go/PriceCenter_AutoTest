package tron.chromeExtension.utils;

import javafx.geometry.Dimension2DBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import tron.chromeExtension.pages.MainPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.internal.Utils.log;
import static tron.chromeExtension.base.Base.DRIVER;
import static tron.chromeExtension.base.Base.chain;

public class Helper {

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
  public void screenShotByTakesScreenshot1() {

  }

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
