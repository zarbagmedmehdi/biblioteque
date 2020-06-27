package service;

import helper.EmpPretHelper;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import util.DbHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpPretService {
    static Connection connection= null;
    static CallableStatement cs = null;
    public  static List<EmpPretHelper> getEmpPrets(String login)
    {
        List<EmpPretHelper> es= new ArrayList<>();
        try
        {
            connection= DbHelper.GetConnection();
            String commandText = "{call getPretsEmploye(?,?)}";
            CallableStatement stmt = connection.prepareCall(commandText);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, login);
            stmt.execute();

            ResultSet s = ((OracleCallableStatement)stmt).getCursor(1);
            System.out.println("pret employe:");
            while(s.next())
            {
                es.add(new EmpPretHelper(s));
            }
            System.out.println(es);

        }
        catch(Exception err)
        {
            System.out.println("An error has occurred.");
            System.out.println("See full details below.");
            err.printStackTrace();
        }            return es;

    }
}
