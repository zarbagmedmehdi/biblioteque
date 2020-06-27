package controller;

import bean.Auteur;
import bean.Ouvrage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.AuteurService;
import service.OuvrageService;
import util.AlertUtil;
import util.Util;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateOuvrageController implements Initializable {
    @FXML
    private TextField titre;
    @FXML
    private TextField editeur;
    @FXML
    private TextField annee;
    @FXML
    private TextField domaine;
    @FXML
    private TextField stock;
    @FXML
    private ComboBox<Auteur> auteurCB = new ComboBox<Auteur>();
    @FXML
    private TextField nomAuteur;

    private OuvrageService ouvrageService = new OuvrageService();
    private AuteurService auteurService = new AuteurService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAuteurCB();
    }

    public void ajouterOuvrage(ActionEvent actionEvent) {
        int validation = validateOeuvreInputs();
        if (validation == 1) {
            Ouvrage ouvrage = new Ouvrage(titre.getText(), editeur.getText(), Integer.parseInt(annee.getText()), domaine.getText(), Integer.parseInt(stock.getText()));
            ouvrage.setAuteur(auteurCB.getValue());
            int result = ouvrageService.insert(ouvrage);
            if (result == 1) {
                AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Information", "Ajout d'ouvrage:", "Ajout avec succès.");
                initOeuvreInputs();
            } else {
                AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Erreur", "Ajout d'ouvrage:", "Erreur au niveau du serveur, veuillez reesayer plustard.");
            }
        }
    }

    public void ajouterAuteur(ActionEvent actionEvent) {
        if (validateAuteurInputs() == 1) {
            Auteur auteur = new Auteur(nomAuteur.getText());
            int result = auteurService.insert(auteur);
            if (result == 1) {
                AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Information", "Ajout d'auteur:", "Ajout avec succès.");
                initAuteurInputs();
                initAuteurCB();
            } else {
                AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Erreur", "Ajout d'auteur:", "Erreur au niveau du serveur, veuillez reesayer plustard.");

            }
        }
    }

    private int validateOeuvreInputs() {
        if (titre.getText() == null || titre.getText().isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout d'ouvrage:", "Veuillez saisir un titre valide.");
            return -1;
        } else if (editeur.getText() == null || editeur.getText().isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout d'ouvrage:", "Veuillez saisir un nom d'editeur valide.");
            return -2;
        } else if (annee.getText() == null || annee.getText().isEmpty() || !Util.isNumeric(annee.getText())) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout d'ouvrage:", "Veuillez saisir une année valide.");
            return -3;
        } else if (domaine.getText() == null || domaine.getText().isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout d'ouvrage:", "Veuillez saisir un domaine valide.");
            return -4;
        } else if (stock.getText() == null || stock.getText().isEmpty() || !Util.isNumeric(stock.getText())) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout d'ouvrage:", "Veuillez saisir un stock valide.");
            return -5;
        } else if (auteurCB == null || auteurCB.getValue() == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout d'ouvrage:", "Veuillez choisir un auteur.");
            return -6;
        } else {
            return 1;
        }
    }

    private int validateAuteurInputs() {
        if (nomAuteur.getText() == null || nomAuteur.getText().isEmpty() || !Util.validateName(nomAuteur.getText())) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout d'auteur:", "Veuillez saisir un nom valide.");
            return -1;
        } else {
            return 1;
        }
    }

    private void initOeuvreInputs() {
        titre.setText("");
        editeur.setText("");
        annee.setText("");
        domaine.setText("");
        stock.setText("");
        auteurCB.setValue(null);
    }

    private void initAuteurInputs() {
        nomAuteur.setText("");
    }

    private void initAuteurCB(){
        auteurCB.setItems(FXCollections.observableArrayList(auteurService.findAll()));
    }
}
