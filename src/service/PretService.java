package service;

import bean.Etudiant;
import bean.Ouvrage;
import bean.Pret;
import oracle.jdbc.OracleTypes;
import util.Connexion;
import util.DateUtil;
import util.DbHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PretService {
    public int preter(Pret pret) {
        int res=0;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();;
        String query="begin ? := preterOuvrage(?,?,?,?,?); end;";
        try {
            cs= connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.INTEGER);
            cs.setDate(2, DateUtil.convertFormUtilToSql(pret.getDate_emprunt()));
            cs.setDate(3,DateUtil.convertFormUtilToSql(pret.getDate_retour_limite()));
            cs.setLong(4,pret.getEtudiant().getId_etu());
            cs.setLong(5,pret.getOuvrage().getId_ouv());
            cs.setLong(6,pret.getEmploye().getId_pers());
            cs.executeUpdate();
            res=cs.getInt(1);
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        finally {
            try {
                DbHelper.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public List<Pret> findByEtudiant(Etudiant newValue) {
        List<Pret> prets=new ArrayList<>();
        ResultSet rs;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();;
        String query="begin ? := findPretByEtudiant(?); end;";
        try {
            cs= connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setLong(2,newValue.getId_etu());
            cs.execute();
            rs= (ResultSet) cs.getObject(1);
            while(rs.next()){
                prets.add(new Pret(
                        rs.getLong("id_pret"),
                        rs.getDate("date_empreunt"),
                        rs.getDate("date_retour"),
                        rs.getDate("date_retour_limite"),
                        new Ouvrage(rs.getString("ouvrage"))
                        ));
            }
            return prets;
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        finally {
            try {
                Connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return prets;
    }

    public int recuperer(Pret pret) {
        int res=0;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();;
        String query="begin ? := rendreOuvrage(?); end;";
        try {
            cs= connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.INTEGER);
            cs.setLong(2,pret.getId_pret());
            cs.executeUpdate();
            res=cs.getInt(1);
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        finally {
            try {
                Connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
