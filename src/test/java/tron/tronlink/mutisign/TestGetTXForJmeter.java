package tron.tronlink.mutisign;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.InvalidProtocolBufferException;
import org.tron.core.services.http.JsonFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class TestGetTXForJmeter {
    public static void main(String[] args) throws IOException {


        int totalNum = 10000;
        File file = new File("/Users/wqq/Text/test_v4.0/perf-multiSign/config/tx.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        GetTXForJmeter myclass = new GetTXForJmeter();
        String hexTX = myclass.sendCoinTxHex();
        System.out.println("---wqq debug----: hexTX: " + hexTX);
        JSONObject object = new JSONObject();
        Writer out = new FileWriter(file);
        for (int index = 0; index < totalNum; index++){
            Object mySignedTRX = myclass.addTransactionSignWithPermissionId(hexTX);
            object.clear();
            object.put("address","TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
            object.put("netType","main_net");
            object.put("transaction",mySignedTRX);
            out.write(object.toString()+"\n");
        }

        out.close();

    }

}
