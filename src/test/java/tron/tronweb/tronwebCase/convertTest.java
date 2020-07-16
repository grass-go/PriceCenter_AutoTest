package tron.tronweb.tronwebCase;

import java.io.IOException;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;


public class convertTest extends Base{
  String functionName;

  @Test(enabled = true,description = "Test from Ascii")
  public void test01FromAscii() throws IOException{
    functionName = "fromAscii";
    String convertString = " test";
    String convertInfo = executeJavaScript(convertDir + functionName +convertString);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.contains("74657374"));

  }

  @Test(enabled = true,description = "Test from decimal")
  public void test02FromDecimal() throws IOException{
    functionName = "fromDecimal";
    String convertString = " 21";
    String convertInfo = executeJavaScript(convertDir + functionName +convertString);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.contains("15"));
  }

  @Test(enabled = true,description = "Test from SUN")
  public void test03FromSun() throws IOException{
    functionName = "fromSun";
    String convertString = " 1000001";
    String convertInfo = executeJavaScript(convertDir + functionName +convertString);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.equals("1.000001"));
  }

  @Test(enabled = true,description = "Test from Utf8")
  public void test04FromUtf8() throws IOException{
    functionName = "fromUtf8";
    String convertString = " test";
    String convertInfo = executeJavaScript(convertDir + functionName +convertString);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.contains("74657374"));
  }

  @Test(enabled = true,description = "Test is connected")
  public void test05IsConnected() throws IOException{
    functionName = "isConnected";
    String convertInfo = executeJavaScript(convertDir + functionName);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.contains("fullNode:true"));
    Assert.assertTrue(convertInfo.contains("solidityNode:true"));
    Assert.assertTrue(convertInfo.contains("eventServer:true"));
  }

  @Test(enabled = true,description = "Test set default block")
  public void test06SetDefaultBlock() throws IOException{
    functionName = "setDefaultBlock";
    String blockInput = "latest";
    String convertInfo = executeJavaScript(convertDir + functionName +  " " +blockInput);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.equals(blockInput));

    blockInput = "earliest";
    convertInfo = executeJavaScript(convertDir + functionName +  " " +blockInput);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.equals(blockInput));

    blockInput = "100";
    convertInfo = executeJavaScript(convertDir + functionName +  " " +blockInput);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.equals("undefined"));


  }



}
