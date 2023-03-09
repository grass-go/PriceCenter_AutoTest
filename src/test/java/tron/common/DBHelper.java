package tron.common;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
    public static final String url="jdbc:mysql://123.56.3.74/tronlink_trx?useSSL=false";
    public static final String name="com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "1234";
    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql){
        try{
            Class.forName(name);
            conn = DriverManager.getConnection(url,user,password);
            pst = conn.prepareStatement(sql);

        } catch (Exception e){
            e.printStackTrace();

        }

    }

    public void close(){
        try{
            this.conn.close();
            this.pst.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
