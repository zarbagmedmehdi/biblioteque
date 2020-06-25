package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;

public class DbHelper {

static String url1="192.168.56.102";
static String url2="192.168.56.101";
    static String fst1="fst1";
    static String fst2="fst2";


        public static Connection GetDatabaseConnection(int val,int val2) {
            Connection conn=null;

            try {conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.56.10"+val2+":1521:fst"+val, "system", "cc");

                if (conn != null) {

                    System.out.println("Connected to the database!");
                } else {
                    System.out.println("Failed to make connection!");
                }

            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;


        }


}
