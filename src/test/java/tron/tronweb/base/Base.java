package tron.tronweb.base;

import tron.common.utils.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Base {
  public String jsDir = "src/test/java/tron/tronweb/jsDir/";
  public String nodecmd = "node ";
  public String addressConvertDir = "src/test/java/tron/tronweb/jsDir/addressConvert.js ";
  public String contractDir = "src/test/java/tron/tronweb/jsDir/contract.js ";
  public String accountDir = "src/test/java/tron/tronweb/jsDir/account.js ";
  public String convertDir = "src/test/java/tron/tronweb/jsDir/convertTool.js ";
  public String usdjContractAddress = "TCkkpmnY38nsXAtideWzHTybvbMozzXUot";
  public String usdjContractBase64Address = "411E8D88B8516ED59B8DBEF73B286D562C51D486AA";
  public String queryAddress = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddress");
  public String queryAddress41 = Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressBase64With41Start");
  public String querySrAddress = Configuration.getByPath("testng.conf").getString("tronWeb.querySrAddress");
  public String querySrAddress41 = Configuration.getByPath("testng.conf").getString("tronWeb.querySrAddressWith41Start");

  public  String executeJavaScript(String cmd) throws IOException {
    Process process = Runtime.getRuntime().exec("node " + cmd);
    InputStreamReader isr=new InputStreamReader(process.getInputStream());
    Scanner sc=new Scanner(isr);
    StringBuffer sb = new StringBuffer();
    while(sc.hasNext()){
      sb.append(sc.next());
    }
    return sb.toString();
  }



}
