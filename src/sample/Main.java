package sample;

import bean.Employe;
import bean.Etudiant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.EmployeService;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import service.EtudiantService;

import javax.mail.MessagingException;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/ajouterEmploye.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, MessagingException {
      //  DbHelper.GetDatabaseConnection(1,2);
       // DbHelper.GetDatabaseConnection(2,1);
       // System.out.println(EmployeService.deleteEmploye("zarbag.mehdi@gmail.com"));
      //  EmployeService.getAllEmploye();
        //System.out.println(EtudiantService.getAllEtudiant());
        launch(args);
    }
    public static void forward(ActionEvent actionEvent, String pageName, Class myClass) throws IOException {

        Parent parent = FXMLLoader.load(myClass.getResource(pageName));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();

    }
}
