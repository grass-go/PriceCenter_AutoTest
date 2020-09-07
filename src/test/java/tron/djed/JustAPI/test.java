package tron.djed.JustAPI;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class test {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/AutoTestScan";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "zK199595@";

    @Test(enabled = false)
    public void test(){
        Connection conn = null;
        Statement stmt = null;
        String result = "";
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String time = "2020-04-13 10:00:00";
            String status = "2";
            String sucessClass = "sdfs";
            String failClass = "sdfsffsdf";
            String sql = "INSERT INTO `AutoTestScan`.`tronscanUI`(`time`, `status`, `sucessClass`, `failClass`) VALUES ('"+time+"','"+status+"','"+sucessClass+"','"+failClass+"')";
            stmt.executeUpdate(sql);
//            result = rs.toString();
            System.out.println(result);
//            rs.close();
            stmt.close();
            conn.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
