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

  @Test(enabled = true,description = "Test event by transaction id")
  public void test03GetEventByTransactionTest() throws IOException{
    functionName = "getEventByTransactionID ";
    String transactionId = "c253fe4118eb0446ff382b344c49d21c5b32e458c1e2aa70cb675c19ba6fe516";
    String eventInfo = executeJavaScript(contractDir + functionName + transactionId);
    System.out.println(eventInfo);
    Assert.assertTrue(eventInfo.contains(transactionId));
    Assert.assertTrue(eventInfo.contains("Transfer"));
  }

  @Test(enabled = true,description = "Test event result")
  public void test04GetEventResultTest() throws IOException{
    functionName = "getEventResult ";
    String contractAddress = "TCkkpmnY38nsXAtideWzHTybvbMozzXUot";
    String eventName = "Transfer";
    String eventInfo = executeJavaScript(contractDir + functionName + contractAddress + " "+ eventName + " " + 2);
    System.out.println(eventInfo);
    Assert.assertTrue(eventInfo.contains(contractAddress));
    Assert.assertTrue(eventInfo.contains(eventName));
    Assert.assertTrue(eventInfo.contains("transaction"));
    Assert.assertTrue(eventInfo.contains("result"));
  }


}