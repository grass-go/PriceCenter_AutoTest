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


}
