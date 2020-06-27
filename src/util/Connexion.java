package util;

import java.sql.*;

public class                                       Connexion {
    private static String urlstring = "jdbc:oracle:thin:@";
    private static String driverName = "oracle.jdbc.driver.OracleDriver";
    private static Connection con;
    public static String ipAdress="192.168.116.10";
    public static String port="1521";
    public static String SID="FST1";
    private static String username = "FST";
    private static String password = "FST";
    public static String site = "FST";


    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(getconnectionString(), username, password);
                System.out.println("connetion ok");
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
            ex.printStackTrace();
        }
        if(con!=null){
            return con;
        }else{
            return null;
        }

    }

    public static String getconnectionString(){
        return urlstring+ipAdress+":"+port+":"+SID;
    }


    public static Statement createStatement() throws SQLException{
        return getConnection().createStatement();
    }

    public static ResultSet executeQuery(String sql)throws SQLException{
        return createStatement().executeQuery(sql);
    }

    public static void close()throws SQLException{
        getConnection().close();
    }

}
