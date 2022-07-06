package tron.common.jmeter;

//public class CsvOperation {
//}
//package com.yiyang.myfirstspringdemo.utils;

//import com.yiyang.myfirstspringdemo.model.Passenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static tron.common.jmeter.JmeterOperation.JMETER_ENCODING;
import static tron.common.jmeter.JmeterOperation.NUMBER_THREADS;

//import static com.yiyang.myfirstspringdemo.service.JemterTest.JMETER_ENCODING;
//import static com.yiyang.myfirstspringdemo.service.JemterTest.NUMBER_THREADS;


@Slf4j
@Component
public class CVSUtils {

    static String format = "\"%s\"";

    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param outPutPath 文件路径
     * @param fileName   文件名称
     * @return
     */
    private static File createCSVFile(List<List<String>> exportData, String outPutPath, String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    log.info("创建cvs文件成功");
                } else {
                    log.error("创建cvs文件失败");
                }
            }
            // 定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile)));
            for (List<String> exportDatum : exportData) {
                writeRow(exportDatum, csvFileOutputStream);
                csvFileOutputStream.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (csvFileOutputStream != null) {
                try {
                    csvFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 如果临时文件，不需要在使用完成之后，记得删除。我这里就先不删除了。
            // csvFile.delete();
            // csvFile.deleteOnExit();
        }
        return csvFile;
    }

    /**
     * 写一行数据
     * @param row       数据列表
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
        for (String data : row) {
            csvWriter.write(new String(data.getBytes(JMETER_ENCODING), JMETER_ENCODING));
            // csvWriter.write(data);
        }
    }

    /***
     * 将json弄成csv文件可以识别的，保证这个请求体（json格式）在同一列
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    private static String cvsFormat(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(format, "{"));
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        int i = 0;
        for(Field field : declaredFields) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (value == null) {
                continue;
            }
            String name = field.getName();
            sb.append(String.format(format, name));
            sb.append(String.format(format, ":"));
            sb.append(String.format(format, value));
            if (i < declaredFields.length - 1) {
                sb.append(String.format(format, ","));
            }
            i++;
        }
        sb.append(String.format(format, "}"));
        return sb.toString();
    }


    public static File getCsvPath () {
        String outFile = "/Users/liufei/Downloads/jmter";
        String filename = "my_replay_data";
        List<List<String>> listList = new ArrayList<List<String>>();
        List<String> list = null;
        list = new ArrayList<>();//一个List为一行
        // 标题
        list.add("body");
        listList.add(list);

        // 内容
        int count = NUMBER_THREADS;
        for (int i = 0; i < count; i++){
            List<String> colList = new ArrayList<>();
//            ArrayListPassenger passenger = new Passenger();
//            String uuId = UUID.randomUUID().toString().substring(0, 8);
//            passenger.setName("刘翊扬_" + uuId);
//            passenger.setPassword("123456_" + uuId);
//            String json = null;
//            try {
////                json = cvsFormat(passenger);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            colList.add(json);
            listList.add(colList);
        }
        return createCSVFile(listList, outFile, filename);
    }

    /***
     * 获取文件的行数
     * @param file
     * @return
     * @throws IOException
     */
    public static int getTotalLines(File file) throws IOException {
        long startTime = System.currentTimeMillis();
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        reader.skip(Long.MAX_VALUE);
        int lines = reader.getLineNumber();
        reader.close();
        long endTime = System.currentTimeMillis();

        System.out.println("统计文件行数运行时间： " + (endTime - startTime) + "ms");
        return lines;
    }
}

