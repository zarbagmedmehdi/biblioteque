package util;

import java.sql.*;

public class DbHelper {

static String url1="192.168.56.102";
static String url2="192.168.56.101";
    static String fst1="fst1";
    static String fst2="fst2";


        public static Connection GetDatabaseConnection(int val1,int val2) {
            Connection conn=null;

            try {conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.56.10"+val1+":1521:fst"+val2, "system", "cc");
                System.out.println("fst"+val2);
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
    public static Connection GetConnection() {
        Connection conn=null;
        conn=GetDatabaseConnection(
                (Integer)Sessions.getAttribut("v1"),(Integer)Sessions.getAttribut("v2"));
        System.out.println((Integer)Sessions.getAttribut("v1"));
        return conn;


    }
    public static Statement createStatement() throws SQLException{
        return GetDatabaseConnection(
                (Integer)Sessions.getAttribut("v1"),(Integer)Sessions.getAttribut("v2")
        ).createStatement();
    }

    public static ResultSet executeQuery(String sql)throws SQLException{
        return createStatement().executeQuery(sql);
    }

    public static void close()throws SQLException{
        GetDatabaseConnection(
                (Integer)Sessions.getAttribut("v1"),(Integer)Sessions.getAttribut("v2")).close();
    }


}
