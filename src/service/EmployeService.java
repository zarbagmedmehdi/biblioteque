package service;

import bean.Employe;
import helper.EmpItemHelper;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import util.*;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeService {

   static Connection connection= null;
 static   CallableStatement cs = null;
    public  static int createEmploye(String login, String adresse  , String nom, String statut, ProgressIndicator p) throws SQLException, MessagingException {
        connection= DbHelper.GetConnection();
            Statement st=connection.createStatement();
            String ps= MotDePasse.generate(4);
            cs = connection.prepareCall("{ ?=call InsertEmploye(?,?,?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, login);
            cs.setString(3, ps);
            cs.setString(4, nom);
            cs.setString(5,adresse);
            cs.setString(6,statut);
            cs.execute();
        System.out.println(cs.getInt(1));
        switch(cs.getInt(1)) {
            case 1: p.setVisible(true);
               Send s=new Send(login,ps,p);
            break;
            case -1: AlertUtil.duploginError("Login");break;
            case -2: AlertUtil.ErreurBD();break;default:
        }
        return cs.getInt(1);

    }

    public  static int deleteEmploye(String login ) throws SQLException, MessagingException {
        connection= DbHelper.GetConnection();
        Statement st=connection.createStatement();
        cs = connection.prepareCall("{ ?=call deleteEmploye(?)}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, login);
        cs.execute();
        switch(cs.getInt(1)) {
            case 1:
                AlertUtil.showDelete();
                break;
            case -1: AlertUtil.showDeleteError("////////");break;
            case -2: AlertUtil.ErreurBD();break;default:
        }
        return cs.getInt(1);

    }

    public  static ArrayList<Employe> getAllEmploye()
    {
        ArrayList<Employe> es= new ArrayList<>();
        try
        {
            connection= DbHelper.GetConnection();
            String commandText = "{call GETALLEMP(?)}";
            CallableStatement stmt = connection.prepareCall(commandText);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet s = ((OracleCallableStatement)stmt).getCursor(1);
            System.out.println("Commissioned employees:");


            while(s.next())
            {
                System.out.println(s.getString(2));
                Employe e= new Employe(s.getString("nom"), s.getString("adresse"),
                        s.getString("statut"), "FST1",s.getString("login"),s.getString("password")) ;
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

    public static  List<EmpItemHelper> parseList(ArrayList<Employe> es) throws SQLException {
        List<EmpItemHelper> fs=new ArrayList<>();

        for (int i=0; i<es.size();i++)
        {
            fs.add(new EmpItemHelper(es.get(i)));
        }
        System.out.println("ha list dyal EmpHelper"+fs);
        return fs;

    }

    public static int updateEmploye(EmpItemHelper selected) throws SQLException {
        connection= DbHelper.GetConnection();
        Statement st=connection.createStatement();
        cs = connection.prepareCall("{ ?=call updateEmploye(?,?,?,?,?)}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2,selected.getLogin());
        cs.setString(3, selected.getPasword());
        cs.setString(4,selected.getAdresse());
        cs.setString(5,selected.getNom());
        cs.setString(6,selected.getStatut());
        cs.execute();
        System.out.println(cs.getInt(1));
        Sessions.createAtrribute(1,"tab");
        switch(cs.getInt(1)) {
            case 1:
                Sessions.createAtrribute(1,"tab");
                Platform.runLater(()-> AlertUtil.showConfirmation("Confirmation","Employé est  modifier "));
                break;
            case -2: AlertUtil.ErreurBD();break;default:
        }
        return cs.getInt(1);
    }

    public  static int getEmployeId(String login,String password ) throws SQLException, MessagingException {
        connection= DbHelper.GetConnection();
        cs = connection.prepareCall("{ ?=call getEmployeId(?,?)}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, login);
        cs.setString(3, password);

        cs.execute();
        System.out.println(cs.getInt(1));
        switch(cs.getInt(1)) {
            case 0:
                AlertUtil.showAlert("Erreur","Employé non trouvée", Alert.AlertType.ERROR);
                Sessions.delete("v1");Sessions.delete("v2");
                break;
            case 1: ;break;
        }
        connection=null;
        return cs.getInt(1);

    }
}

