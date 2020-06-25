package sample;



import helper.EtudInfoHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import service.EtudiantService;
import util.AlertUtil;
import util.FxUtil;
import util.Sessions;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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

        if(AlertUtil.showConfirmation("Supprresion","Vous voulez vraiment supprimer? ")) {
            selected.setAdresse(adresseField.getText());
            selected.setNom(nomField.getText());
            selected.setCursus(cursusComb.getValue().toString());
            EtudiantService.updateEtudiantInfo(selected);
            Main.forward(actionEvent, "../view/ajouterEtudiant.fxml", this.getClass());
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

