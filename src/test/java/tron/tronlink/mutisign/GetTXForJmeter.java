package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import io.grpc.ManagedChannel;
import org.apache.http.HttpResponse;
import org.tron.common.utils.Base58;
import org.tron.common.utils.Commons;
import tron.common.TronlinkApiList;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.spongycastle.util.encoders.Hex;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.tron.api.GrpcAPI;
import org.tron.api.WalletGrpc;
import org.tron.common.utils.Base58;
import org.tron.common.utils.Commons;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.*;
import tron.common.TronlinkApiList;

import java.util.concurrent.TimeUnit;

public class GetTXForJmeter {
    private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;
    private ManagedChannel channelFull = null;
    private String fullnode = "47.252.19.181:50051";  //线上

    HttpResponse res;
    private JSONObject responseContent;

    String quince58 = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
    byte[] quince = Commons.decode58Check(quince58);
    String wqq1key = "8d5c18030466b6ab0e5367154d15c4f6cb46d2fb56a0b552e017d183abd8c255";
    byte[] wqq1 = TronlinkApiList.getFinalAddress(wqq1key);
    String wqq1Addr="TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo";

    public String sendCoinTx() {
        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(wqq1, 500_000, quince, blockingStubFull);
        System.out.println("-----111111  "+ JsonFormat.printToString(transaction));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        System.out.println("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",wqq1Addr);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        String txStr= object.toJSONString();
        return txStr;
    }

}
