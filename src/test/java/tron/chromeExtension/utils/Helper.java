package tron.chromeExtension.utils;

import javafx.geometry.Dimension2DBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.internal.Utils.log;
import static tron.chromeExtension.base.Base.DRIVER;

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

    return "";
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
