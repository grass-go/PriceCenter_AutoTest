package tron.tronweb.tronwebCase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronweb.base.Base;

import java.io.IOException;


public class tokenTest extends Base {
  String functionName;


  @Test(enabled = true, description = "Test getTokenByID")
  public void test01GetTokenByID() throws IOException {
    functionName = "getTokenByID ";
    String id = "1002000";
    String result = executeJavaScript(tokenDir + functionName + id);
    System.out.println(result);
    JSONObject jsonObject = JSONObject.parseObject(result);

    Assert.assertEquals("4137fa1a56eb8c503624701d776d95f6dae1d9f0d6", jsonObject.getString("owner_address"));
    Assert.assertEquals("BitTorrent", jsonObject.getString("name"));
    Assert.assertEquals("BTT", jsonObject.getString("abbr"));
    Assert.assertEquals(1, jsonObject.getIntValue("trx_num"));
    Assert.assertEquals(6, jsonObject.getIntValue("precision"));
    Assert.assertEquals(1548000000000L, jsonObject.getLongValue("start_time"));
    Assert.assertEquals(1548000001000L, jsonObject.getLongValue("end_time"));
    Assert.assertEquals("OfficialTokenofBitTorrentProtocol", jsonObject.getString("description"));
    Assert.assertEquals("www.bittorrent.com", jsonObject.getString("url"));
    Assert.assertEquals("1002000", jsonObject.getString("id"));
  }

  @Test(enabled = true, description = "Test getTokenFromID")
  public void test02GetTokenFromID() throws IOException {
    functionName = "getTokenFromID ";
    String id = "1002000";
    String result = executeJavaScript(tokenDir + functionName + id);
    System.out.println(result);
    JSONObject jsonObject = JSONObject.parseObject(result);

    Assert.assertEquals("4137fa1a56eb8c503624701d776d95f6dae1d9f0d6", jsonObject.getString("owner_address"));
    Assert.assertEquals("BitTorrent", jsonObject.getString("name"));
    Assert.assertEquals("BTT", jsonObject.getString("abbr"));
    Assert.assertEquals(1, jsonObject.getIntValue("trx_num"));
    Assert.assertEquals(6, jsonObject.getIntValue("precision"));
    Assert.assertEquals(1548000000000L, jsonObject.getLongValue("start_time"));
    Assert.assertEquals(1548000001000L, jsonObject.getLongValue("end_time"));
    Assert.assertEquals("OfficialTokenofBitTorrentProtocol", jsonObject.getString("description"));
    Assert.assertEquals("www.bittorrent.com", jsonObject.getString("url"));
    Assert.assertEquals("1002000", jsonObject.getString("id"));
  }

  @Test(enabled = true, description = "Test getTokenListByName")
  public void test03GetTokenListByName() throws IOException {
    functionName = "getTokenListByName ";
    String name = "BTT";
    String result = executeJavaScript(tokenDir + functionName + name);
    System.out.println(result);
    JSONArray array = JSONObject.parseArray(result);
    Assert.assertTrue(array.size()>0);
    JSONObject ob;
    for(int i=0;i<array.size();i++){
      ob = array.getJSONObject(i);
      Assert.assertEquals(name, ob.getString("name"));
    }
  }

  @Test(enabled = true, description = "Test getTokensIssuedByAddress")
  public void test04GetTokensIssuedByAddress() throws IOException {
    functionName = "getTokensIssuedByAddress ";
    String address = "4137fa1a56eb8c503624701d776d95f6dae1d9f0d6"; //id: 1002000 token
    String result = executeJavaScript(tokenDir + functionName + address);
    System.out.println(result);
    JSONObject jsonObject = JSONObject.parseObject(result).getJSONObject("BitTorrent");
    Assert.assertEquals(address, jsonObject.getString("owner_address"));
    Assert.assertEquals(1002000, jsonObject.getIntValue("id"));
  }

  @Test(enabled = true, description = "Test getContract")
  public void test05GetContract() throws IOException {
    functionName = "getContract ";
    String address = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"; //Tether USD (USDT)
    String result = executeJavaScript(tokenDir + functionName + address);
    System.out.println(result);
    JSONObject jsonObject = JSONObject.parseObject(result);
    Assert.assertEquals("41a614f803b6fd780986a42c78ec9c7f77e6ded13c", jsonObject.getString("contract_address"));
    Assert.assertEquals(10000000, jsonObject.getIntValue("origin_energy_limit"));
    Assert.assertEquals("TetherToken", jsonObject.getString("name"));
    Assert.assertEquals(30, jsonObject.getIntValue("consume_user_resource_percent"));
    Assert.assertEquals("41517591d35d313bf6a5e33098284502b045e2bc08", jsonObject.getString("origin_address"));
  }
}


