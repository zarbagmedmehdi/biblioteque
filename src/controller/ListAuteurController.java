package controller;

import bean.Auteur;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import service.AuteurService;
import util.AlertUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ListAuteurController implements Initializable {
    @FXML
    private ListView<Auteur> auteurLV;

    private AuteurService auteurService = new AuteurService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auteurLV.setItems(FXCollections.observableArrayList(auteurService.findAll()));
    }

    public void supprimer(ActionEvent actionEvent) {
        Auteur auteur = auteurLV.getSelectionModel().getSelectedItem();
        if (auteur == null) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Suppression d'auteur", "Veuillez selectionner un auteur a supprimer.");
        } else {
            int result = auteurService.delete(auteur);
            switch (result) {
                case 1:
                    AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Succès", "Suppression d'auteur", "L'auteur a été suprimer");
                    auteurLV.getItems().remove(auteur);
                    break;
                case -1:
                    AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Suppression d'auteur", "Impossible de supprimer l'auteur, il est lié a quelques ouvrages de la bibliothèque");
                    break;
                default:
                    AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Suppression d'auteur", "Erreur au niveqau du serveur. Veuillez réssayer plus tard.");
            }
        }
    }
}
