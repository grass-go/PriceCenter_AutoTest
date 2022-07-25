package tron.common.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileOperation {

    public static void OutToFile(String filePath, String content){
        //写入的文件的内容
        try{
            File file = new File(filePath);
            FileOutputStream fos = null;
            if(!file.exists()){
                file.createNewFile();//如果文件不存在，就创建该文件
                fos = new FileOutputStream(file);//首次写入获取
            }else{
                //如果文件已存在，那么就在文件末尾追加写入
                fos = new FileOutputStream(file,true);//这里构造方法多了一个参数true,表示在文件末尾追加写入
            }
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//指定以UTF-8格式写入文件
            osw.write(content);
            osw.write("\r\n");
            //写入完成关闭流
            osw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
