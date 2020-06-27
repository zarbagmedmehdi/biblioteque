package controller;

import bean.Auteur;
import bean.Ouvrage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.OuvrageService;
import util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchOuvrageController implements Initializable {
    @FXML
    private TableView<Ouvrage> table_ouvrage = new TableView<>();
    @FXML
    private TableColumn<Ouvrage, String> col_titre = new TableColumn<>();
    @FXML
    private TableColumn<Ouvrage, String> col_editeur = new TableColumn<>();
    @FXML
    private TableColumn<Ouvrage, Integer> col_annee = new TableColumn<>();
    @FXML
    private TableColumn<Ouvrage, String> col_domaine = new TableColumn<>();
    @FXML
    private TableColumn<Ouvrage, Integer> col_stock = new TableColumn<>();
    @FXML
    private TableColumn<Ouvrage, String> col_Auteur = new TableColumn<>();
    @FXML
    private TextField titre;

    private OuvrageService ouvrageService = new OuvrageService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }

    @FXML
    public void initTable() {
        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_editeur.setCellValueFactory(new PropertyValueFactory<>("editeur"));
        col_annee.setCellValueFactory(new PropertyValueFactory<>("annee"));
        col_domaine.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        col_Auteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
    }


    public void rechercherOuvrage(ActionEvent actionEvent) {
        List<Ouvrage> ouvrages;
        String vTitre = titre.getText();
        ouvrages = ouvrageService.findByTitre(vTitre);
        if (ouvrages == null || ouvrages.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Recherche ouvrage", "aucun ouvrage ne correspond au titre fourni");
        }
        table_ouvrage.setItems(FXCollections.observableArrayList(ouvrages));
    }

    public void preterOuvrage(ActionEvent actionEvent) {
        try {
            Ouvrage ouvrage = table_ouvrage.getSelectionModel().getSelectedItem();
            table_ouvrage.setItems(null);
            open(actionEvent, "../view/preterOuvrage.fxml", this.getClass(), ouvrage, "PreterOuvrageController");
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "prêt d'ouvrage", "Veuillez selectionner un ouvrage à prêter");
        }
    }


    public static void open(ActionEvent actionEvent, String pageName, Class myClass, Ouvrage ouvrage, String controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(myClass.getResource(pageName));
        Parent parent = (Parent) loader.load();
        if (controller.equals("PreterOuvrageController")) {
            PreterOuvrageController preterOuvrageController = loader.getController();
            preterOuvrageController.initOuvrageFields(ouvrage);
        } else if (controller.equals("UpdateOuvrageController")) {
            UpdateOuvrageController updateOuvrageController = loader.getController();
            updateOuvrageController.initOuvrageFields(ouvrage);
        } else {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur", "Action non autorisée.");
            return;
        }
        Scene scene = new Scene(parent);
        Stage app_stage = new Stage();
        app_stage.setScene(scene);
        app_stage.show();
    }

    public void supprimer(ActionEvent actionEvent) {
        Ouvrage ouvrage = table_ouvrage.getSelectionModel().getSelectedItem();
        if (ouvrage == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Suppression d'ouvrage", "Veuillez selectionner l'ouvrage à supprimer.");
        } else {
            String msg = "Voulez-vous supprimer l'ouvrage << " + ouvrage.getTitre() + " >> ?";
            Alert alert = AlertUtil.getConfirmationDialog("Suppression d'ouvrage", msg);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                int result = ouvrageService.delete(ouvrage);
                switch (result) {
                    case -1:
                        AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Suppression d'ouvrage", "l'ouvrage que vous avez essayer de le supprimer a été déjà prêté");
                        break;
                    case 1:
                        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Succès", "Suppression d'ouvrage", "l'ouvrage a été supprimer.");
                        table_ouvrage.getItems().remove(ouvrage);
                        break;
                    default:
                }
            }
        }
    }

    public void modifierStock(ActionEvent actionEvent) {
        try {
            Ouvrage ouvrage = table_ouvrage.getSelectionModel().getSelectedItem();
            if (ouvrage == null) {
                AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Suppression d'ouvrage", "Veuillez selectionner l'ouvrage à supprimer.");
            }else {
                open(actionEvent, "../view/modifierOuvrage.fxml", this.getClass(), ouvrage, "UpdateOuvrageController");

            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Modification d'ouvrage", "Veuillez selectionner un ouvrage pour modifier son stock.");
        }
    }
}
