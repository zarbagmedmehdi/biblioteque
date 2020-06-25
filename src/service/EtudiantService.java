package service;

import bean.Employe;
import bean.Etudiant;
import helper.EmpItemHelper;
import helper.EtudInfoHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import util.*;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantService {

    static Connection connection= null;
    static CallableStatement cs = null;
    public  static int createEtudiant(String nom, String adresse  , String cursus, String cne) throws SQLException, MessagingException {
        connection= DbHelper.GetDatabaseConnection(1,2);
        Statement st=connection.createStatement();
        String ps= MotDePasse.generate(4);
        cs = connection.prepareCall("{ ?=call InsertEtudiant(?,?,?,?)}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, nom);
        cs.setString(3, adresse);
        cs.setString(4,cursus);
        cs.setString(5,cne);
        cs.execute();
        System.out.println(cs.getInt(1));
        switch(cs.getInt(1)) {
            case 1:
                AlertUtil.showAlert("Confirmation","Etudiant Crée", Alert.AlertType.CONFIRMATION);
                break;
            case -1: AlertUtil.duploginError("CNE");break;
            case -2: AlertUtil.ErreurBD();break;default:
        }
        return cs.getInt(1);

    }

    public  static int deleteEtudiant(String cne ) throws SQLException, MessagingException {
        connection= DbHelper.GetDatabaseConnection(1,2);
        Statement st=connection.createStatement();
        cs = connection.prepareCall("{ ?=call DELETEETUDIANT(?)}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, cne);
        cs.execute();
        switch(cs.getInt(1)) {
            case 1:
                AlertUtil.showDelete();
                break;
            case -1: AlertUtil.showDeleteError("CNE n'existe pas");break;
            case -2: AlertUtil.showDeleteError(" cet étudiant a un historique de prets ");break;
            case -3: AlertUtil.ErreurBD();break;default:
        }
        return cs.getInt(1);

    }

    public  static ArrayList<Etudiant> getEtudiant(int type,String cne,String cursus) throws SQLException {
        ArrayList<Etudiant> es= new ArrayList<>();
        String proc="";
        try
        {if(type==0) proc="getAllEtudiant(?)"; else proc="getEtudiantByCriteria(?,?,?,?)";

            connection= DbHelper.GetDatabaseConnection(1,2);
            String commandText = "{call "+proc+"}";
            CallableStatement stmt = connection.prepareCall(commandText);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            if(type==1){
                stmt.registerOutParameter(2, OracleTypes.NUMBER);

                if(cne!=null) {
                    stmt.setString(3, cne);
                }
                else {          stmt.setString(3, null);}

                if(cursus!=null){
                     stmt.setString(4, cursus);}
                else{    stmt.setString(4, null);}
            }
            stmt.execute();
            if(type==1) System.out.println( "RESULT"+ ((OracleCallableStatement)stmt).getNUMBER(2).stringValue());
            ResultSet s = ((OracleCallableStatement)stmt).getCursor(1);

            while(s.next())
            {
                Etudiant e=  new Etudiant( s.getString("cne"),  s.getString("nom"),  s.getString("adresse")
                    ,s.getString("cursus"),  s.getInt("nb_emprunts"),  s.getBoolean("nb_emprunts")) ;

                es.add(e);
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

    public static List<EtudInfoHelper> parseList(ArrayList<Etudiant> es) throws SQLException {
        List<EtudInfoHelper> fs=new ArrayList<>();

        for (int i=0; i<es.size();i++)
        {
            fs.add(new EtudInfoHelper(es.get(i)));
            System.out.println("parse"+fs.get(i).getCne());
        }
        System.out.println("ha list dyal EtudInfoHelper"+fs);
        return fs;

    }


    public static int updateEtudiantInfo(EtudInfoHelper selected) throws SQLException {
        connection= DbHelper.GetDatabaseConnection(1,2);
        Statement st=connection.createStatement();
        cs = connection.prepareCall("{ ?=call updateEtudiant(?,?,?,?)}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2,selected.getNom());
        cs.setString(3, selected.getAdresse());
        cs.setString(4,selected.getCursus());
        cs.setString(5,selected.getCne());
        cs.execute();
        System.out.println(cs.getInt(1));
        Sessions.createAtrribute(1,"tab");
        switch(cs.getInt(1)) {
            case 1:
                Sessions.createAtrribute(1,"tab");
              //  AlertUtil.showAlert("Confirmation","Etudiant Modifié", Alert.AlertType.CONFIRMATION);
                break;
            case -2: AlertUtil.ErreurBD();break;default:
        }
        return cs.getInt(1);
    }
}
