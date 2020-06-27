package service;

import bean.Auteur;
import bean.Ouvrage;
import javafx.scene.control.TextField;
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

public class OuvrageService {

    public int insert(Ouvrage ouvrage) {
        int result = 1;
        Connection connection = DbHelper.GetConnection();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call insert_ouvrage(?,?,?,?,?,?,?)}");
            cs.setString(1, ouvrage.getTitre());
            cs.setString(2, ouvrage.getEditeur());
            cs.setInt(3, ouvrage.getAnnee());
            cs.setString(4, ouvrage.getDomaine());
            cs.setInt(5, ouvrage.getStock());
            if((Integer)Sessions.getAttribut("v1")==1){
                cs.setString(6,"FST");
            }
            else  cs.setString(6,"FSSM");
            cs.setLong(7, ouvrage.getAuteur().getId_aut());
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

    public List<Ouvrage> findByTitre(String vTitre) {
        List<Ouvrage> ouvrages = new ArrayList<>();
        ResultSet rs;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();;
        String query = "begin ? := findOeuvreByTitre(?); end;";
        try {
            cs = connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setString(2, vTitre);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                ouvrages.add(new Ouvrage(
                        rs.getLong("ID_OUV"),
                        rs.getString("TITRE"),
                        rs.getString("EDITEUR"),
                        rs.getInt("ANNEE"),
                        rs.getString("DOMAINE"),
                        rs.getInt("STOCK"),
                        new Auteur(rs.getString("NOM_AUTEUR")))
                );
            }
            return ouvrages;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ouvrages;
    }

    public int delete(Ouvrage ouvrage) {
        //0: exception , 1: suppresion ok, -1:ouvrage en relation avec des prêts
        int res = 0;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();
        String query = "begin ? := deleteOuvrage(?); end;";
        try {
            cs = connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.INTEGER);
            cs.setLong(2, ouvrage.getId_ouv());
            cs.executeUpdate();
            res = cs.getInt(1);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return res;
    }

    public int updateStock(Ouvrage ouvrage, int vStock) {
        //0: exception , 1: modif ok,
        int res = 0;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();;
        String query = "begin ? := updateStock(?,?); end;";
        try {
            cs = connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.INTEGER);
            cs.setLong(2, ouvrage.getId_ouv());
            cs.setInt(3, vStock);
            cs.executeUpdate();
            res = cs.getInt(1);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return res;

    }

    public List<Ouvrage> findByCriteria(String vTitre, String domaine, Auteur auteur, String site) {
        List<Ouvrage> ouvrages = new ArrayList<>();
        ResultSet rs;
        String query = "SELECT * FROM ouvrage WHERE 1=1";
        if (vTitre != null && !vTitre.isEmpty()) {
            query += " AND titre LIKE CONCAT('%',CONCAT('" + vTitre + "','%'))";
        }
        if (domaine != null && !domaine.isEmpty()) {
            query += " AND domaine LIKE CONCAT('%',CONCAT('" + domaine + "','%'))";
        }
        if (auteur != null && auteur.getId_aut() != 0) {
            query += " AND id_auteur =" + auteur.getId_aut();
        }
        if (site != null && !site.isEmpty()) {
            query += " AND site ='" + site + "'";
        }
        query += " ORDER BY titre ASC";
        System.out.println(query);
        try {
            rs = DbHelper.executeQuery(query);
            while (rs.next()) {
                ouvrages.add(new Ouvrage(
                        rs.getString("titre"),
                        rs.getString("editeur"),
                        rs.getInt("annee"),
                        rs.getString("domaine"),
                        rs.getInt("stock"),
                        rs.getString("site"),
                        new Auteur(rs.getString("auteur"))
                ));
            }
            return ouvrages;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            try {
                DbHelper.GetDatabaseConnection( (Integer) Sessions.getAttribut("v1"),(Integer)Sessions.getAttribut("v2")).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ouvrages;
    }
}
