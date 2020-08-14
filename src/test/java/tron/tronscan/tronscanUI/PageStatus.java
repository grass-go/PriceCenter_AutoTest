package tron.tronscan.tronscanUI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PageStatus {

  public static void main(String[] args) throws Exception {
    CloseableHttpClient closeableHttpClient=HttpClients.createDefault(); //1、创建实例
    HttpGet httpGet=new HttpGet("https://tronscan.io"); //2、创建请求

    httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");

    CloseableHttpResponse closeableHttpResponse=closeableHttpClient.execute(httpGet); //3、执行
    HttpEntity httpEntity=closeableHttpResponse.getEntity(); //4、获取实体

    System.out.println(closeableHttpResponse.getStatusLine()); //获取状态信息
    System.out.println(closeableHttpResponse.getStatusLine().getStatusCode()); //获取状态码

    closeableHttpResponse.close();
    closeableHttpClient.close();
  }

}
