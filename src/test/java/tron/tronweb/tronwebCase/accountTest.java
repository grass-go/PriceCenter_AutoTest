package tron.tronweb.tronwebCase;

import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
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

  @Test(enabled = true,description = "Test is address")
  public void test02IsAddress() throws IOException{
    functionName = "isAddress ";
    String address = "414fa1f834a47f621957ec2ae7d445da9b3be0bee4";
    String isAddress = executeJavaScript(accountDir + functionName + address);
    System.out.println(isAddress);
    Assert.assertTrue(Boolean.valueOf(isAddress));

    address = "THEGR4Aor5pCDVktbbbwgHAE6PQWRfejBf";
    isAddress = executeJavaScript(accountDir + functionName + address);
    System.out.println(isAddress);
    Assert.assertTrue(Boolean.valueOf(isAddress));


    address = "wrongAddress";
    isAddress = executeJavaScript(accountDir + functionName + address);
    System.out.println(isAddress);
    Assert.assertFalse(Boolean.valueOf(isAddress));

  }

  @Test(enabled = true,description = "Test set privateKey")
  public void test03SetPrivateKey() throws IOException{
    functionName = "setPrivateKey ";
    String privateKey = "a8107ea1c97c90cd4d84e79cd79d327def6362cc6fd498fc3d3766a6a71924f6";
    String result = executeJavaScript(accountDir + functionName + privateKey);
    System.out.println(result);
    Assert.assertTrue(result.contains(privateKey));
  }

  @Test(enabled = true,description = "Test set address")
  public void test04SetAddress() throws IOException{
    functionName = "setAddress ";
    String address = usdjContractAddress;
    String result = executeJavaScript(accountDir + functionName + address);
    System.out.println(result);
    Assert.assertTrue(result.toLowerCase().contains(usdjContractAddress.toLowerCase())
        && result.toLowerCase().contains(usdjContractBase64Address.toLowerCase()));


    address = usdjContractBase64Address;
    result = executeJavaScript(accountDir + functionName + address);
    System.out.println(result);
    Assert.assertTrue(result.toLowerCase().contains(usdjContractAddress.toLowerCase())
        && result.toLowerCase().contains(usdjContractBase64Address.toLowerCase()));

  }

  @Test(enabled = true, description = "Test get account")
  public void test05GetAccount() throws IOException {
    functionName = "getAccount ";
    String address = queryAddress;
    String result = executeJavaScript(accountDir + functionName + address);
    System.out.println(result);
    JSONObject jsonObject = JSONObject.parseObject(result);
    Assert.assertTrue(jsonObject.containsKey("balance"));
    Assert.assertTrue(jsonObject.getInteger("balance") > 0);
    Assert.assertTrue(jsonObject.containsKey("address"));
    Assert.assertEquals(queryAddress41.toLowerCase(), jsonObject.getString("address"));
    Assert.assertTrue(jsonObject.containsKey("account_resource"));
    Assert.assertTrue(jsonObject.containsKey("create_time"));
    Assert.assertEquals(1588824369000L, jsonObject.getLongValue("create_time"));
    Assert.assertTrue(jsonObject.containsKey("owner_permission"));
  }

}
