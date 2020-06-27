package controller;


import helper.EtudInfoHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;
import service.EtudiantService;
import service.EtudiantService2;
import util.AlertUtil;
import util.Sessions;

import java.net.URL;
import java.util.ResourceBundle;

public class EtudInfoItemController implements Initializable {
    //////PasswordItem

    @FXML
    TextField nomField;
    @FXML
    Button modifier ;

    @FXML
    TextField adresseField;
    @FXML
    Label cneLabel;
    @FXML
    Label modification;
    @FXML
    ComboBox cursusComb;
EtudInfoHelper selected=null;
    Boolean activate=true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       selected=(EtudInfoHelper)  Sessions.getAttribut("selectedEtudiantInfo");
        nomField.setText(selected.getNom());
        adresseField.setText(selected.getAdresse());
        cneLabel.setText("CNE  : "+selected.getCne());
        setSiteComboListenner(selected.getCursus());

    }
    public void modifierItem(ActionEvent actionEvent) throws Exception {

        if(AlertUtil.showConfirmation("Supprresion","Vous voulez vraiment modifier? ")) {
            selected.setAdresse(adresseField.getText());
            selected.setNom(nomField.getText());
            selected.setCursus(cursusComb.getValue().toString());
            EtudiantService2.updateEtudiantInfo(selected);
            Main.forward(actionEvent, "../view/administrateur/ajouterEtudiant.fxml", this.getClass());
        }
        }


    public void setSiteComboListenner(String val){
        cursusComb.getItems().addAll  ("Ingénierie en Réseauxs et Systèmes d’Information","Génie Civil",
                "Ingénierie en Finance et Actuariat",
                "Industrie et Sécurité des Aliments",
                "Génie des Matériaux et Procédés ",
                "Energies renouvelables & mobilité électrique");


        cursusComb.getSelectionModel().select(val);
        cursusComb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {

            }
        });
    }



}

