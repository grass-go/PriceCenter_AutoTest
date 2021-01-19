package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.tron.common.utils.Base58;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import tron.common.TronlinkApiList;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CreateMultiTransaction {
    private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;
    private String fullnode = "34.220.77.106:50051";  //线上
    private ManagedChannel channelFull = null;
    HttpResponse res;
    private JSONObject responseContent;
    String key1="2eb094fa2b7a83a1a799b6fbee88d6037a39b668d523106d32ff06255b3b9098"; //线上
    byte[] address1=TronlinkApiList.getFinalAddress(key1);
    String address158= Base58.encode(address1);
    String key2 = "7306c6044ad7c03709980aa188b8555288b7e0608f5edbf76ff2381c5a7a15a8";//线上
    byte[] address2=TronlinkApiList.getFinalAddress(key2);
    String address258=Base58.encode(address2);

    /**
     * constructor.
     */
    @BeforeClass(enabled = true)
    public void beforeClass() {

        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
    }

    @Test(enabled = true,description = "nulti sign send coin")
    public void sendCoin() {

        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(address2, 3000_000, address1, blockingStubFull);

        log.info("-----111111  "+ JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 2, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(res.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        if (channelFull != null) {
            channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
