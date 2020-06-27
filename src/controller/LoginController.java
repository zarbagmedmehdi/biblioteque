package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.Main;
import service.EmployeService;
import util.AlertUtil;
import util.Sessions;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    ComboBox siteComb;
    @FXML
    ComboBox typeComb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        siteComb.getItems().addAll  (" ","FST","SEMLALIA");
        typeComb.getItems().addAll  (" ","Administrateur","fonctionnaire");
siteComb.getSelectionModel().select(0);typeComb.getSelectionModel().select(0);

    }

    public void annonyme(ActionEvent actionEvent) throws IOException {
        Sessions.createAtrribute(1,"v1");
        Sessions.createAtrribute(2,"v2");
        Main.forward(actionEvent, "../view/etudiant/listOuvrage.fxml", this.getClass());
    }
    public void connexion(ActionEvent actionEvent) throws IOException, SQLException, MessagingException {
        sessionAtt(actionEvent);
    }
    public void typeCombAtt(ActionEvent actionEvent ) throws IOException, SQLException, MessagingException {

        if(typeComb.getSelectionModel().getSelectedItem().equals("fonctionnaire")){
            System.out.println("fonctionnaire");

            int v= EmployeService.getEmployeId(loginField.getText(),passwordField.getText());
            switch(v) {
                case 0:
                    break;
                default:
                    Sessions.createAtrribute(v,"id");
                    Main.forward(actionEvent, "../view/dashboard.fxml", this.getClass());
                    break;
            }

        }
        else if(typeComb.getSelectionModel().getSelectedItem().equals("Administrateur")){
            System.out.println("Administrateur");
            if(loginField.getText().equals("system") && passwordField.getText().equals("cc") )
            Main.forward(actionEvent, "../view/administrateur/dashboard.fxml", this.getClass());
            else     AlertUtil.showAlert("Erreur","Admin non trouvé", Alert.AlertType.ERROR);
        }
        else     AlertUtil.showAlert("Erreur","Choisissez un type", Alert.AlertType.WARNING);


    }
    public void sessionAtt(ActionEvent actionEvent) throws SQLException, IOException, MessagingException {
if(siteComb.getSelectionModel().getSelectedItem().equals("FST")){
    System.out.println("FST choix");

    Sessions.createAtrribute(2,"v1");
    Sessions.createAtrribute(1,"v2");
    typeCombAtt( actionEvent );
}
else if(siteComb.getSelectionModel().getSelectedItem().equals("SEMLALIA")){
    System.out.println("SEMLALIA choix");

    Sessions.createAtrribute(1,"v1");
            Sessions.createAtrribute(2,"v2");

    typeCombAtt( actionEvent );
        }
else if(siteComb.getSelectionModel().getSelectedItem().equals(" ")){
   AlertUtil.showAlert("Erreur","Remplissage de site indispensable", Alert.AlertType.WARNING);
}


    }


}
