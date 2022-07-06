package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
//import com.sun.istack.internal.NotNull;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.junit.Assert;
import org.testng.annotations.*;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Commons;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.AssetIssueContractOuterClass;
import org.tron.protos.contract.BalanceContract;
import tron.common.TronlinkApiList;
import tron.common.jmeter.CsvOperation;
import tron.common.jmeter.JmeterOperation;
import tron.common.utils.Sha256Sm3Hash;
import tron.tronlink.base.TronlinkBase;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
//import stest.tron.wallet.common.client.utils;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CreateMultiTransaction {
    private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;
    private String fullnode = "47.75.245.225:50051";  //线上
    private ManagedChannel channelFull = null;
//    HttpResponse res;
    private JSONObject responseContent;
//    String key1="c21f0c6fdc467f4ae7dc4c1a0b802234a930bff198ce34fde6850b0afd383cf5"; //线上
//    byte[] address1=TronlinkApiList.getFinalAddress(key1);

//    String address158= "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
//    byte[] address1 = Commons.decode58Check(address158);
    String address158;
    byte[] address1;
    String priKey1;
    String priKey2;
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2=TronlinkApiList.getFinalAddress(key2);
    String address258=Base58.encode(address2);
    String toAddress;
    byte[] toAddressByte;

    /**
     * constructor.
     */
    @BeforeClass(enabled = true)
    @Parameters({"fullnode", "firstAddress", "secondAddress", "firstPriKey", "secondPriKey","toAddress"})
    public void beforeClass(String fullnode, String address158, String address258, String firstPriKey,String secondPriKey, String toAddress) {
        TronlinkBase.tronlinkUrl = "http://101.201.66.150";
        initEnv(fullnode, address158, address258, firstPriKey,secondPriKey, toAddress);
        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
        startTime = System.currentTimeMillis();
    }

    // 初始化环境
    private void initEnv(String fullnode, String address158, String address258, String firstPriKey,String secondPriKey, String toAddress){
        this.fullnode = fullnode;
        this.address158 = address158;
        this.address258 = address258;
        address1 = Commons.decode58Check(address158);
        address2 = Commons.decode58Check(address258);
        priKey1 = firstPriKey;
        priKey2 = secondPriKey;
        this.toAddress = toAddress;
        toAddressByte = Commons.decode58Check(toAddress);
    }

    private volatile static int count = 0;


    // 串行 执行100次
    @Test(enabled = true,invocationCount = 100, threadPoolSize = 1 ,description = "multi sign performance test，A and B control account of C")
    public void serserialExcuteMultiSign100()  {
            try {
                excuteOnceMultiSign();
            }catch (Exception e){

                e.printStackTrace();
            }
    }


    // 串行 执行1000次
    @Test(enabled = true,invocationCount = 1000, threadPoolSize = 1 ,description = "multi sign performance test，A and B control account of C")
    public void serserialExcuteMultiSign1000()  {
        try {
            excuteOnceMultiSign();
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("failed count == " + failed);
    }
    private volatile static AtomicInteger failed ;

    // 并发 5线程100交易数量
    @Test(enabled = true,invocationCount = 10, threadPoolSize = 2 ,description = "multi sign performance test，A and B control account of C")
    public void concurrentExcuteMultiSign100()  {
        try {
            excuteOnceMultiSign();
        }catch (Exception e){
            e.printStackTrace();
        }
//        printResult();
    }


    // 并发 10个线程1000个交易
    @Test(enabled = true,invocationCount = 1000, threadPoolSize = 10 ,description = "multi sign performance test，A and B control account of C")
    public void concurrentExcuteMultiSign1000()  {
        try {
            excuteOnceMultiSign();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 并发 30个线程30000笔交易
    @Test(enabled = true,invocationCount = 30000, threadPoolSize = 30 ,description = "multi sign performance test，A and B control account of C")
    public void concurrentExcuteMultiSign3000()  {
        try {
            excuteOnceMultiSign();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printResult(){
        log.info("failed count == " + failed+ " repeated count = " + repeatedHash  + " hashset size = " + s.size());
        log.info("cost time = " + (endTime - startTime));
//        for (String key:
//                s) {
//            log.info("hash value = " + key);
//
//        }
    }

    // 根据tx计算交易hash值
    public String getHash(Protocol.Transaction transaction){
        String txID = ByteArray.toHexString(Sha256Sm3Hash.hash(transaction.getRawData().toByteArray()));
        return txID;
    }




    static Set<String> s = new CopyOnWriteArraySet<>();
    // 重复hash值数量
    private volatile static int repeatedHash = 0;
    static long  startTime = 0;
    static long endTime = 0;

    private void excuteOnceMultiSign(){
        count++;
        log.info("count = " + count);
        log.info("thread pool id = " + Thread.currentThread().getId() + Thread.currentThread().getName());
        log.info("address1 : " + address158 + " address2 = " + address258);
        int money = new Random().nextInt(10000);
        // 发起一笔交易
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(toAddressByte, money + 1, address1, blockingStubFull);
        if (transaction == null){
            failed.getAndAdd(1);
            return;
        }
        log.info("send coin finished!  tx hash = "   + JsonFormat.printToString(transaction));


        // 第一个用户签名
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, priKey1, 3, blockingStubFull);
        log.info("key1 sign finished!  " + JsonFormat.printToString(transaction1));
        String txID = getHash(transaction1);
        synchronized (CreateMultiTransaction.class) {
            if (s.contains(txID)) {
                repeatedHash++;
                return;
            } else {
                s.add(txID);
            }
        }
        // post & 断言
        HttpResponse res;
        res = postTransction(address158, transaction1);
        assertResponse(res);



        // 第二个用户签名
        Protocol.Transaction transaction2 = TronlinkApiList.addTransactionSignWithPermissionIdAndExpiredTime(
                transaction1, priKey2, 3, blockingStubFull);
        log.info("key2 sign finished!  " + JsonFormat.printToString(transaction2));

        // 对hash 去重
//        txID = getHash(transaction2);
//        synchronized (CreateMultiTransaction.class) {
//            if (s.contains(txID)) {
//                repeatedHash++;
//                return;
//            } else {
//                s.add(txID);
//            }
//        }

        // post & 断言
        res = postTransction(address258, transaction2);
        assertResponse(res);

        log.info("test finished!");
    }

    BlockingQueue<Protocol.Transaction> firstQueue = new LinkedBlockingQueue<>(10);

    // 准备数据
    private void prepareTransaction(){
        for (int i = 0; i < 1; i++) {
            int money = new Random().nextInt(10000);
            // 发起一笔交易
            Protocol.Transaction transaction = TronlinkApiList
                    .sendcoin(toAddressByte, money + 1, address1, blockingStubFull);
            if (transaction == null){
                failed.getAndAdd(1);
                return;
            }
            // 第一个用户签名
            Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                    transaction, priKey1, 3, blockingStubFull);
            log.info("key1 sign finished!  " + JsonFormat.printToString(transaction1));
            String txID = getHash(transaction1);
            synchronized (CreateMultiTransaction.class) {
                if (s.contains(txID)) {
                    repeatedHash++;
                    return;
                } else {
                    s.add(txID);
                }
            }
            firstQueue.add(transaction);
        }
    }

    private void excuteOnceMultiSignV2(){
        count++;
//        log.info("count = " + count);
//        log.info("thread pool id = " + Thread.currentThread().getId() + Thread.currentThread().getName());
//        log.info("address1 : " + address158 + " address2 = " + address258);
        prepareTransaction();
//        log.info("send coin finished!  tx hash = "   + JsonFormat.printToString(transaction));
//        generateJmx();
        File csv = CsvOperation.generateCSV("/Users/dannygguo/Desktop/tron/Tronscan_AutoTest/logs/", firstQueue);
        ArrayList<TestElementProperty> heads = generateHeaderInfo();
        JmeterOperation.RunJemterWithCSV("http://101.201.66.150", "80", "/api/wallet/multi/transaction", "${body}",csv,heads);

        // post & 断言
//        HttpResponse res;
//        res = postTransction(address158, transaction1);
//        assertResponse(res);
//
//
//
//        // 第二个用户签名
//        Protocol.Transaction transaction2 = TronlinkApiList.addTransactionSignWithPermissionIdAndExpiredTime(
//                transaction1, priKey2, 3, blockingStubFull);
//        log.info("key2 sign finished!  " + JsonFormat.printToString(transaction2));

        // 对hash 去重
//        txID = getHash(transaction2);
//        synchronized (CreateMultiTransaction.class) {
//            if (s.contains(txID)) {
//                repeatedHash++;
//                return;
//            } else {
//                s.add(txID);
//            }
//        }

        // post & 断言
//        res = postTransction(address258, transaction2);
//        assertResponse(res);

        log.info("test finished!");
    }

    // 生成header信息
    private ArrayList<TestElementProperty> generateHeaderInfo(){
        ArrayList<TestElementProperty> headerMangerList = new ArrayList<>();
        Header header = new Header("Content-Type", "application/json");
        TestElementProperty HeaderElement = new TestElementProperty("", header);
        headerMangerList.add(HeaderElement);
        return headerMangerList;
    }




    /**
     * invocationCount设定的是这个方法的执行次数.
     * threadPoolSize 这个属性表示的是开启线程数的多少.
     */
    @Test(enabled = true,invocationCount = 30, threadPoolSize = 1 ,description = "multi sign performance test，A and B control account of C")
    public void createMultiSign() throws InterruptedException {
        count++;
        log.info("count = " + count);
        log.info("thread pool id = " + Thread.currentThread().getId() + Thread.currentThread().getName());
//        Thread.sleep(2000);
        log.info("address1 : " + address158 + " address2 = " + address258);
        int money = new Random().nextInt(5);
        // 发起一笔交易
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(toAddressByte, money * 100_000, address1, blockingStubFull);
        if (transaction == null){
            log.error("send coin failed! skip this case!");
            return;
        }
        log.info("send coin finished!  tx hash = "   + JsonFormat.printToString(transaction));


        // 第一个用户签名
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, priKey1, 3, blockingStubFull);
        log.info("key1 sign finished!  " + JsonFormat.printToString(transaction1));
        // post & 断言
        HttpResponse res;
        res = postTransction(address158, transaction1);
        assertResponse(res);

        // 第二个用户签名
        Protocol.Transaction transaction2 = TronlinkApiList.addTransactionSignWithPermissionIdAndExpiredTime(
                transaction1, priKey2, 3, blockingStubFull);
        log.info("key2 sign finished!  " + JsonFormat.printToString(transaction2));
        // post & 断言
        res = postTransction(address258, transaction2);
        assertResponse(res);

        log.info("test finished!");
    }

    private void assertResponse( HttpResponse res){
        Assert.assertNotEquals(res, null);
        // 结果校验
        log.info( res.toString());
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }


    private HttpResponse postTransction(String address, Protocol.Transaction transaction) {
        // 开始广播
        JSONObject object = new JSONObject();
        object.put("address", address);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction)));
        TronlinkApiList.HttpNode = "http://101.201.66.150";
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);
        return res;
    }

    @Test(enabled = false,description = "multi sign send coin")
    public void sendCoin() {
        for (int i = 1;i < 200;i ++) {
            Protocol.Transaction transaction = TronlinkApiList
                    .sendcoin(toAddressByte, 500_000, address1, blockingStubFull);
            log.info("-----111111  " + JsonFormat.printToString(transaction));

            Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                    transaction, priKey1, 3, blockingStubFull);
            log.info("-----2222  " + JsonFormat.printToString(transaction1));

            JSONObject object = new JSONObject();
            object.put("address", address158);
            object.put("netType", "main_net");
            object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));
            TronlinkApiList.HttpNode = "http://101.201.66.150";
            HttpResponse res;
            res = TronlinkApiList.multiTransaction(object);
            Assert.assertEquals(200, res.getStatusLine().getStatusCode());
            responseContent = TronlinkApiList.parseJsonObResponseContent(res);
            Assert.assertEquals(0, responseContent.getIntValue("code"));
        }
    }

    @Test(enabled = false,description = "multi sign freeze balandce")
    public void freezeBalandce() throws Exception{
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        ByteString byteAddreess = ByteString.copyFrom(address1);
        builder.setOwnerAddress(byteAddreess).setFrozenBalance(1000000)
                .setFrozenDuration(3).setResourceValue(0);
        BalanceContract.FreezeBalanceContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.freezeBalance(contract);
        log.info("0000 "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 2, blockingStubFull);
        log.info("-----111  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = false,description = "multi sign transfer asset")
    public void transferTrc10() throws Exception{
        AssetIssueContractOuterClass.TransferAssetContract.Builder builder = AssetIssueContractOuterClass.TransferAssetContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(address2);
        ByteString bsName = ByteString.copyFrom("1002000".getBytes());
        ByteString bsOwner = ByteString.copyFrom(address1);
        builder.setToAddress(bsTo);
        builder.setAssetName(bsName);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(1);

        AssetIssueContractOuterClass.TransferAssetContract contract = builder.build();
        log.info("-----0000  "+JsonFormat.printToString(contract));
        Protocol.Transaction transaction = blockingStubFull.transferAsset(contract);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 2, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);

        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = false,description = "nulti sign transfer trc20")
    public void transferTrc20() throws Exception{

        String contractAddress = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "000000000000000000000000ebae50590810b05d4b403f13766f213518edef650000000000000000000000000000000000000000000000000000000000000001";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd,"transfer(address,uint256)",args,true,0,maxFeeLimit,"0",0L,
                address1,blockingStubFull);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));


    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        endTime = System.currentTimeMillis();
        printResult();
        if (channelFull != null) {
            channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
