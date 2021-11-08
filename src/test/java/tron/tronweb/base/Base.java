package tron.tronweb.base;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import tron.common.utils.Configuration;



public class Base {
  public String jsDir = "src/test/java/tron/tronweb/jsDir/";
  public String nodecmd = "node ";
  public String addressConvertDir = "src/test/java/tron/tronweb/jsDir/addressConvert.js ";
  public String contractDir = "src/test/java/tron/tronweb/jsDir/contract.js ";
  public String accountDir = "src/test/java/tron/tronweb/jsDir/account.js ";
  public String ethSdkDir = "src/test/java/tron/tronweb/jsDir/ethsdk.js ";
  public String convertDir = "src/test/java/tron/tronweb/jsDir/convertTool.js ";
  public String blockDir = "src/test/java/tron/tronweb/jsDir/block.js ";
  public String chainDir = "src/test/java/tron/tronweb/jsDir/chain.js ";
  public String tokenDir = "src/test/java/tron/tronweb/jsDir/token.js ";
  public String transactionDir = "src/test/java/tron/tronweb/jsDir/transaction.js ";
  public String usdjContractAddress = "TCkkpmnY38nsXAtideWzHTybvbMozzXUot";
  public String usdjContractBase64Address = "411E8D88B8516ED59B8DBEF73B286D562C51D486AA";
  public String usdtEthAddress = "0xA614F803B6FD780986A42C78EC9C7F77E6DED13C";
  public String queryAddress =
      Configuration.getByPath("testng.conf").getString("tronGrid.queryAddress");
  public String queryAddress41 =
      Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressBase64With41Start");
  public String querySrAddress =
      Configuration.getByPath("testng.conf").getString("tronWeb.querySrAddress");
  public String querySrAddress41 =
      Configuration.getByPath("testng.conf").getString("tronWeb.querySrAddressWith41Start");
  public String queryEthAddress =
      Configuration.getByPath("testng.conf").getString("tronWeb.ethQueryAddress");
  public String queryTractionId =
      Configuration.getByPath("testng.conf").getString("tronWeb.queryTransactionId");
  public String ethQueryTriggerContractId =
      Configuration.getByPath("testng.conf").getString("tronWeb.ethQueryTriggerContractId");
  public String test = Configuration.getByPath("testng.conf").getString("tronWeb.test");
  public String test1 = Configuration.getByPath("testng.conf").getString("tronWeb.test1");
  public String transactionId =
      Configuration.getByPath("testng.conf").getString("tronWeb.transactionId");
  public String queryAddressSophia =
      Configuration.getByPath("testng.conf").getString("tronGrid.queryAddressSophia");
  public String jsonRpcMainnetNode =
      Configuration.getByPath("testng.conf").getString("ethJsonRpc.mainnetNode");
  public String jsonRpcNilenetNode =
      Configuration.getByPath("testng.conf").getString("ethJsonRpc.nilenetNode");


  /** constructor. */
  public String executeJavaScript(String cmd) throws IOException {
    Process process = Runtime.getRuntime().exec("node " + cmd);
    InputStreamReader isr = new InputStreamReader(process.getInputStream());
    Scanner sc = new Scanner(isr);
    StringBuffer sb = new StringBuffer();
    while (sc.hasNext()) {
      sb.append(sc.next());
    }
    return sb.toString();
  }
}
