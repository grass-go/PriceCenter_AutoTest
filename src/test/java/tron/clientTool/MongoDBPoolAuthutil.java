package tron.clientTool;


import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.http.HttpResponse;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;
public class MongoDBPoolAuthutil {

  private static MongoClient client = null;

  static {
    if(client==null){
      //创建一个用户认证信息
      MongoCredential credential = MongoCredential.createCredential("javatron","online","123456".toCharArray());
      //封装MongoDB的地址和端口
      ServerAddress address = new ServerAddress("101.200.46.37", 27017);
      //方法过时是由于现在推荐池连的方式
      client = new MongoClient(address, Arrays.asList(credential));
    }
  }

  //获取MongoDB数据库
  public static MongoDatabase getDatabase(String databaseName){
    return client.getDatabase(databaseName);
  }

  //获取Mongo集合
  public static MongoCollection getCollection(String databaseName,String collectionName){
    return getDatabase(databaseName).getCollection(collectionName);
  }

  FindIterable<Document> documentsBlock = client.getDatabase("online").getCollection("block").find();
  FindIterable<Document> documentsTransaction = client.getDatabase("online").getCollection("transaction").find();
  FindIterable<Document> documentsContractEvent = client.getDatabase("online").getCollection("contractevent").find();
  FindIterable<Document> documentsContractlog = client.getDatabase("online").getCollection("contractlog").find();

  public static JSONObject responseContent;
  public static HttpResponse response;

  HashSet<String> passedTxid = new HashSet<>();

  @Test
  public void testMongo() {

    MongoCursor<Document> cursorContractlog = documentsContractlog.iterator();
    int times = 0;
    while (cursorContractlog.hasNext()){
      Document document = cursorContractlog.next();
      String txid = document.getString("transactionId");
      if(passedTxid.contains(txid)) {
        continue;
      }
      passedTxid.add(txid);
      long count = 0;

      count = getCollection("online","contractlog").countDocuments(new BasicDBObject("transactionId",txid));
      System.out.println("count:" + count);
      System.out.println("txid:" + txid);
      System.out.println("Times:" + times++);
      response = fullOrSolidityBase.getTransactionInfoById("https://api.trongrid.io/wallet/",txid);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      Assert.assertEquals(fullOrSolidityBase.parseResponseContent(response).getJSONArray("log").size(), count);

      getCollection("online","contractlog").deleteMany(new BasicDBObject("transactionId",txid));
    }





  }


}