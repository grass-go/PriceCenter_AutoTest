package tron.tronweb.tronwebCase;

import java.io.IOException;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;


public class accountTest extends Base{
  String functionName;

  @Test(enabled = true,description = "Test create account")
  public void test01CreateAccount() throws IOException{
    functionName = "createAccount";
    String accountInfo = executeJavaScript(accountDir + functionName);
    //System.out.println(accountInfo);
    Assert.assertTrue(accountInfo.contains("privateKey"));
    Assert.assertTrue(accountInfo.contains("publicKey"));
    Assert.assertTrue(accountInfo.contains("address"));
    Assert.assertTrue(accountInfo.contains("base58"));
    Assert.assertTrue(accountInfo.contains("hex"));
    Assert.assertTrue(accountInfo.contains("41"));
  }


}
