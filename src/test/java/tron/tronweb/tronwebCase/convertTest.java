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

  @Test(enabled = true,description = "Test sha3 convert")
  public void test07Sha3Convert() throws IOException{
    functionName = "sha3";
    String input = " testSha3Convert";
    String result = executeJavaScript(convertDir + functionName + input);
    System.out.println(result);
    Assert.assertTrue(result.contains("b721c6a5b9434b9a383851a91e8176a6633479ea216fe6c590609644be441d9d"));
  }

  @Test(enabled = true,description = "Test to ascii")
  public void test08ToAscii() throws IOException{
    functionName = "toAscii";
    String input = " 0x74726f6e";
    String result = executeJavaScript(convertDir + functionName + input);
    System.out.println(result);
    Assert.assertTrue(result.equals("tron"));
  }

  @Test(enabled = true,description = "Test to big number")
  public void test09ToBigNumber() throws IOException{
    functionName = "toBigNumber";
    String input = " 200000000000000000000001";
    String result = executeJavaScript(convertDir + functionName + input);
    System.out.println(result);
    Assert.assertTrue(result.contains("2.0000000000000002e+23"));
  }

  @Test(enabled = true,description = "Test to decimal")
  public void test10ToDecimal() throws IOException{
    functionName = "toDecimal";
    String convertString = " 0x15";
    String convertInfo = executeJavaScript(convertDir + functionName +convertString);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.contains("21"));
  }

  @Test(enabled = true,description = "Test to sun")
  public void test11ToSun() throws IOException{
    functionName = "toSun";
    String convertString = " 10";
    String convertInfo = executeJavaScript(convertDir + functionName +convertString);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.contains("10000000"));
  }

  @Test(enabled = true,description = "Test from Utf8")
  public void test12ToUtf8() throws IOException{
    functionName = "toUtf8";
    String convertString = " 0x74657374";
    String convertInfo = executeJavaScript(convertDir + functionName +convertString);
    System.out.println(convertInfo);
    Assert.assertTrue(convertInfo.contains("test"));
  }



}
