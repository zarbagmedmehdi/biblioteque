package service;

import bean.Etudiant;
import oracle.jdbc.OracleTypes;
import util.Connexion;
import util.DbHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantService {
    public List<Etudiant> findByCNE(String vCNE) {
        List<Etudiant> etudiants=new ArrayList<>();
        ResultSet rs;
        CallableStatement cs;
        Connection connection = DbHelper.GetConnection();
        String query="begin ? := findEtudiantByCNE(?); end;";
        try {
            cs= connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setString(2,vCNE);
            cs.execute();
            rs= (ResultSet) cs.getObject(1);
            while(rs.next()){
                etudiants.add(new Etudiant(
                        rs.getLong("id_etu"),
                        rs.getString("cne"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("universite"),
                        rs.getString("cursus"),
                        rs.getInt("nb_emprunts"),
                        rs.getBoolean("sanctione")));
            }
            return etudiants;
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
        return etudiants;
    }
}
