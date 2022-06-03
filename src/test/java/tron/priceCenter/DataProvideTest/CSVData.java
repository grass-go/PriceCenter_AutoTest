package tron.priceCenter.DataProvideTest;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;

//这个类实现了Iterator迭代器，TestNG调用此类实现的hasNext()、next()方法得到一行数据，在next()方法中可以看到，我把数据是放在Map中的，再把map放在Object[]里，所以测试方法的参数就必须是一个Map。我这里改成了只读取一行，因为一个csv文件的一个caseId只应该有一行。
public class CSVData implements Iterator {
    private BufferedReader br = null;
    //行数
    private int rowNum = 0;
    //获取次数
    private int curRowNo = 0;
    //列数
    private int columnNum = 0;
    //key名
    private String[] columnName;
    //csv中所有行数据
    private List csvList;
    //实际想要的行数据
    private List csvListNeed;

    /*
     * 在TestNG中由@DataProvider(dataProvider = "name")修饰的方法
     * 取csv时，调用此类构造方法(此方法会得到列名并将当前行移到下以后)执行后，转发到
     * TestNG自己的方法中去，然后由它们调用此类实现的hasNext()、next()方法
     * 得到一行数据，然后返回给由@Test(dataProvider = "name")修饰的方法，如此
     * 反复到数据读完为止
     *
     *
     * @param filepath CSV文件名
     * @param casename 用例名
     */
    public CSVData(String fileName, String caseId) {
        try {
            File directory = new File(".");
            String ss = "resources.";
            File csv = new File(directory.getCanonicalFile() + "/src/test/resources/TestData/Price/"
                    + fileName + ".csv");
            System.out.println("CSVData:csv:" + csv);
            br = new BufferedReader(new FileReader(csv));
            csvList = new ArrayList();
            while (br.ready()) {
                csvList.add(br.readLine());
                this.rowNum++;
            }
            String stringValue[] = csvList.get(0).toString().split(",");
            this.columnNum = stringValue.length;
            columnName = new String[stringValue.length];
            for (int i = 0; i < stringValue.length; i++) {
                columnName[i] = stringValue[i].toString();
            }
            this.curRowNo++;
            csvListNeed = new ArrayList();
            for (int i = 1; i < rowNum; i++) {
                String values[] = csvList.get(i).toString().split(",");
                if (caseId.equals(values[0])) {
                    csvListNeed.add(csvList.get(i));
                }
            }

            this.rowNum = 2;//就取一行
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        if (this.rowNum == 0 || this.curRowNo >= this.rowNum) {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object[] next() {
        //将数据放入map
        Map s = new TreeMap();
        String csvCell[] = csvListNeed.get(0).toString().split(",");
        for (int i = 0; i < this.columnNum; i++) {
            String temp = "";
            try {
                temp = csvCell[i].toString();
            } catch (ArrayIndexOutOfBoundsException ex) {
                temp = "";
            }
            s.put(this.columnName[i], temp);
        }
        Object r[] = new Object[1];
        r[0] = s;
        this.curRowNo++;
        return r;
    }

/*    @Override
    public Object[] next() {
        //将数据放入map
        Map s = new TreeMap();
        int csvListNeedLen = csvListNeed.size();
        Object r[] = new Object[csvListNeedLen];

        for (int j=0; j<csvListNeedLen; j++) {
            String csvCell[] = csvListNeed.get(j).toString().split(",");
            for (int i = 0; i < this.columnNum; i++) {
                String temp = "";
                try {
                    temp = csvCell[i].toString();
                } catch (ArrayIndexOutOfBoundsException ex) {
                    temp = "";
                }
                s.put(this.columnName[i], temp);
            }
            r[j] = s;
            this.curRowNo++;
        }
        return r;
    }*/


    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove unsupported");
    }

}
