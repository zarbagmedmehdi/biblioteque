package service;

import bean.Auteur;
import oracle.jdbc.OracleTypes;
import sample.Main;
import util.Connexion;
import util.DbHelper;
import util.Sessions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuteurService {

    public int insert(Auteur auteur){
        int result = 1;
        Connection connection = DbHelper.GetConnection();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call insert_auteur(?)}");
            cs.setString(1, auteur.getNom_auteur());
            cs.execute();
            result = 1;
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            if (connection != null) {
                try {
                    if (cs != null) cs.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    return result;
                }
            }
        }
        return result;
    }

    public List<Auteur> findAll() {
        List<Auteur> auteurs=new ArrayList<>();
        ResultSet rs;
        try {
            //////////////
            rs=DbHelper.executeQuery("SELECT id_aut, nom_auteur FROM auteur");
            /////////////
            while(rs.next()){
                auteurs.add(new Auteur(rs.getLong(1),rs.getString(2)));
            }
            return auteurs;
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        finally {
                try {
                    DbHelper.GetDatabaseConnection( (Integer) Sessions.getAttribut("v1"),(Integer)Sessions.getAttribut("v2")).close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return auteurs;

    }

    public int delete(Auteur auteur) {
        int res=0;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();
        String query="begin ? := deleteAuteur(?); end;";
        try {
            cs= connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.INTEGER);
            cs.setLong(2,auteur.getId_aut());
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
