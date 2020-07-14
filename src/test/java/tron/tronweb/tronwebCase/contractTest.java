package tron.tronweb.tronwebCase;

import java.io.IOException;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;


public class contractTest extends Base{
  String functionName;

  @Test(enabled = true,description = "Test get contract from abi")
  public void test01GetContractFromAbiTest() throws IOException{
    functionName = "getContractFromAbi";
    String contractInfo = executeJavaScript(contractDir + functionName);
    //System.out.println(contractInfo);
    Assert.assertTrue(contractInfo.contains("eventServer"));
    Assert.assertTrue(contractInfo.contains("solidityNode"));
    Assert.assertTrue(contractInfo.contains("fullNode"));
    Assert.assertTrue(contractInfo.contains("transactionBuilder"));
  }

  @Test(enabled = true,description = "Test get contract from address")
  public void test02GetContractFromAddressTest() throws IOException{
    functionName = "getContractFromAddress ";
    String contractInfo = executeJavaScript(contractDir + functionName + usdjContractAddress);
    System.out.println(contractInfo);
    Assert.assertTrue(contractInfo.contains(usdjContractAddress));

    contractInfo = executeJavaScript(contractDir + functionName + usdjContractBase64Address);
    System.out.println(contractInfo);
    Assert.assertTrue(contractInfo.contains(usdjContractBase64Address));


  }


}
