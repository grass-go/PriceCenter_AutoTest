package tron.tronweb.tronwebCase;

import java.io.IOException;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;


public class addressConversiton extends Base{
  String base58Address = "THph9K2M2nLvkianrMGswRhz5hjSA9fuH7";
  String base64Address = "415624C12E308B03A1A6B21D9B86E3942FAC1AB92B";
  String privateKey = "FC8BF0238748587B9617EB6D15D47A66C0E07C1A1959033CF249C6532DC29FE6";
  String functionName;

  @Test(enabled = true,description = "Test convert address from base58 to base64")
  public void test01Base58ToBase64Test() throws IOException{
    functionName = "base58ToBase64 ";
    String convertAddress = executeJavaScript(addressConvertDir + functionName +  base58Address);
    Assert.assertEquals(convertAddress,base64Address.toLowerCase());
  }

  @Test(enabled = true,description = "Test convert address from base64 to base58")
  public void test02Base64ToBase58Test() throws IOException{
    functionName = "base64ToBase58 ";
    String convertAddress = executeJavaScript(addressConvertDir + functionName + base64Address);
    Assert.assertEquals(convertAddress.toLowerCase(),base58Address.toLowerCase());
  }

  @Test(enabled = true,description = "Test convert address from private key")
  public void test03PrivateKeyToBase58Test() throws IOException{
    functionName = "privateKeyToBase58 ";
    String convertAddress = executeJavaScript(addressConvertDir + functionName + privateKey);
    Assert.assertEquals(convertAddress.toLowerCase(),base58Address.toLowerCase());
  }
}
