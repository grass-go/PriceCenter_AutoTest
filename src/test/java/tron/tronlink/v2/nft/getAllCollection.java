package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;
import tron.common.utils.CompareJson;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStreamReader;

public class getAllCollection extends TronlinkBase {
  private JSONObject responseContent;
  private String responseString;
  private JSONArray dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();
  Map<String, Nft> NftPointedInV1 = new HashMap<>();
  {
    NftPointedInV1.put("34", new Nft("JustNFT", "JNFT", "TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG"));
  }
  //To do

  //Verify backward compatibility: No version parameter input, the top coin is in home page

  @Test
  public void getAllCollectionTest001(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","1A8%2FhWTN%2F8g7qKv7vqUdIYKVjJU%3D");
    params.put("address",addressNewAsset41);

    response = TronlinkApiList.v2GetAllCollection(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONArray("data");
    Assert.assertTrue(dataContent.size() >= 1);
    for (int n = 0; n < dataContent.size(); n++ ){
      JSONObject nftData = dataContent.getJSONObject(n);

      Assert.assertTrue(nftData.containsKey("transferCount"));
      Assert.assertTrue(nftData.containsKey("nrOfTokenHolders"));
      Nft Expect = NftPointedInV1.get(nftData.getString("id"));


      Assert.assertNotNull(Expect);
      Assert.assertEquals(Expect.name, nftData.getString("name"));
      Assert.assertEquals(Expect.shortName, nftData.getString("shortName"));
      Assert.assertEquals(Expect.ContractAddress, nftData.getString("contractAddress"));

      Assert.assertEquals(200,
          TronlinkApiList.createGetConnect(nftData.getString("logoUrl")).getStatusLine().getStatusCode());

    }
    System.out.println("responseContent : " + responseContent.toString());
  }

  //Version=v2: the 4 type of focos coins are always in home page.
  // Prepare data：加关注以下3种币，官方币（不持有），推荐币（不持有）， 非推荐币（持有）
  // Verify: 1. 置顶币永远在此接口输出； 2，其他3种币也在此接口输出。
  // 用例设计说明：1. 此账号只能关注这些币，不能在此账号操作关注和取消关注，否则容易造成case不稳定。
  //            2. 相当于对此账户下首页展示的收藏品做全对比。
  //            3. 同时也校验了关注币的排序。
  @SneakyThrows
  @Test(enabled=false)
  public void getAllCollectionTest002(){
    // read expected json
    char cbuf[] = new char[2000];
    InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/getAllCollection_exp.json")),"UTF-8");
    int len =input.read(cbuf);
    String expResponse =new String(cbuf,0,len);
    //JSONObject getAllCollection_exp = JSONObject.parseObject(text);

    // read actual json
    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","15sBsg%2B0R9FOdxGVrZr9K6XVpXI%3D");
    params.put("address",address721_B58);
    params.put("version","v2");
    response = TronlinkApiList.v2GetAllCollection(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseString = TronlinkApiList.parseResponse2String(response);
    String cmp_result = new CompareJson("contractAddress").compareJson(responseString, expResponse);
    System.out.println("=========actual response==========\n"+responseString+"\n");
    System.out.println("=========expect response==========\n"+expResponse+"\n");
    System.out.println("=========cmp_result===============\n"+cmp_result);
    Assert.assertEquals("null",cmp_result);

  }

  class Nft {
    String name;
    String shortName;
    String ContractAddress;

    public Nft(String name, String shortName, String ContractAddress){
      this.name = name;
      this.shortName = shortName;
      this.ContractAddress = ContractAddress;
    }
  }




}
