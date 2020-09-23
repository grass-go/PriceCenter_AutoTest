package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.math.BigDecimal;

public class ScanNode {
  private JSONObject responseContent;

  private HttpResponse response;

  @Test(enabled = true, description = "scan node test")
  public void test000ScanNode() throws Exception{
    JSONObject job = new JSONObject();
    for(int i=0;i<3;i++){
      String url = "https://list.tronlink.org/api/wallet/blockNum";
      response = TronlinkApiList.createGetConnect(url);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      JSONArray dataArray = responseContent.getJSONArray("data");
      double scanNum = dataArray.getJSONObject(0).getDoubleValue("number");


      url = "http://13.127.47.162:8090/wallet/getnowblock";
      response = TronlinkApiList.createGetConnect(url);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      JSONObject dataArray1 = responseContent.getJSONObject("block_header").getJSONObject("raw_data");
      double chainNum = dataArray1.getDoubleValue("number");


      BigDecimal scan = new BigDecimal(scanNum);
      BigDecimal chain = new BigDecimal(chainNum);
      int sub = chain.subtract(scan).intValue();
      String str = "scanNum:"+scanNum+"    \n chainNum: "+chainNum+"\n sub:"+sub;
      Assert.assertTrue(sub <= 12);
      job.put(i+"",str);
      Thread.sleep(300);
    }
    System.out.println(job.toJSONString());
  }
}
