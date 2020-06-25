package sample;


import helper.EmpItemHelper;
import helper.EtudInfoHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import service.EmployeService;
import service.EtudiantService;
import util.AlertUtil;
import util.SendChange;
import util.Sessions;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeInfoItemController implements Initializable {
    //////PasswordItem
    @FXML
    ProgressIndicator progress;
    @FXML
    TextField nomField;
    @FXML
    Button modifier ;
    @FXML
    TextField statusField;
    @FXML
    TextField adresseField;
    @FXML
    Label loginLabel;


EmpItemHelper selected=null;
    Boolean activate=true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
progress.setVisible(false);
       selected=(EmpItemHelper)  Sessions.getAttribut("selectedEmpItem");
        nomField.setText(selected.getNom());
        adresseField.setText(selected.getAdresse());
        statusField.setText(selected.getStatut());
        loginLabel.setText("Login  : "+selected.getLogin());


    }
    public void modifierItem(ActionEvent actionEvent) throws Exception {

        if(AlertUtil.showConfirmation("Modification","Vous voulez vraiment modifier? ")) {
            selected.setAdresse(adresseField.getText());
            selected.setNom(nomField.getText());
            selected.setStatut(statusField.getText());
            EmployeService.updateEmploye(selected);
            Main.forward(actionEvent, "../view/ajouterEmploye.fxml", this.getClass());
        }
        }
    public void nouveauPassword(ActionEvent actionEvent) throws Exception {
        SendChange s=new SendChange(selected,progress);
    }







}

