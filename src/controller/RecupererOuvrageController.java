package controller;

import bean.Etudiant;
import bean.Pret;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.EtudiantService;
import service.PretService;
import util.AlertUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecupererOuvrageController implements Initializable {
    @FXML
    private TextField cne;
    @FXML
    private TableView<Etudiant> etudiant_table = new TableView<>();
    @FXML
    private TableColumn<Etudiant, String> col_cne = new TableColumn<>();
    @FXML
    private TableColumn<Etudiant, String> col_nom = new TableColumn<>();
    @FXML
    private TableColumn<Etudiant, String> col_universite = new TableColumn<>();
    @FXML
    private TableColumn<Etudiant, String> col_emprunt = new TableColumn<>();
    @FXML
    private TableColumn<Etudiant, String> col_sanction = new TableColumn<>();

    @FXML
    private TableView<Pret> pret_table = new TableView<>();
    @FXML
    private TableColumn<Pret, String> col_titre = new TableColumn<>();
    @FXML
    private TableColumn<Pret, String> col_date_emprunt = new TableColumn<>();
    @FXML
    private TableColumn<Pret, String> col_date_retour = new TableColumn<>();
    @FXML
    private TableColumn<Pret, String> col_date_limite = new TableColumn<>();



    public PretService pretService = new PretService();
    public EtudiantService etudiantService = new EtudiantService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableEtudiant();
        initTablePret();

        etudiant_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Etudiant>() {
            @Override
            public void changed(ObservableValue<? extends Etudiant> observable, Etudiant oldValue, Etudiant newValue) {
                if (newValue!=null){
                        initTablePret();
                        List<Pret> prets=pretService.findByEtudiant(newValue);
                        pret_table.setItems(FXCollections.observableArrayList(prets));
                }
            }
        });

    }

    public void rechercherEtudiant(ActionEvent actionEvent) {
        String vCNE = cne.getText();
        if (vCNE == null || vCNE.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Recherche etudiant", "Veuillez saisir le CNE de l'etudiant.");
        } else {
            List<Etudiant> etudiants;
            etudiants = etudiantService.findByCNE(vCNE);
            if (etudiants == null || etudiants.isEmpty()) {
                AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Recherche etudiant", "Aucun etudiant ne correspont au CNE saisi.");
            } else {
                etudiant_table.setItems(FXCollections.observableArrayList(etudiants));
            }
        }
    }

    public void recuperer(ActionEvent actionEvent) {
        Pret pret=pret_table.getSelectionModel().getSelectedItem();
        if (pret==null){
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Récuperation d'ouvrage", "Aucun prêt  n'a été selectionné.");
        }else if(pret.getDate_retour()!=null){
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Récuperation d'ouvrage", "L'ouvrage relatif à ce prêt a été déjà rendu");
        }else {
            int result = pretService.recuperer(pret);
            switch (result) {
                case -1:
                    AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Récuperation d'ouvrage", "L'ouvrage relatif à ce prêt a été déjà rendu");
                    break;
                case 1: AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Succès", "Récuperation d'ouvrage", "L'ouvrage a bien été recuperé.");
                initTablePret();
                initTableEtudiant();
                    break;
                default:break;
            }
        }
    }

    public void initTableEtudiant() {
        col_cne.setCellValueFactory(new PropertyValueFactory<>("CNE"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_universite.setCellValueFactory(new PropertyValueFactory<>("universite"));
        col_emprunt.setCellValueFactory(new PropertyValueFactory<>("nb_emprunts"));
        col_sanction.setCellValueFactory(new PropertyValueFactory<>("sanctionne"));
    }

    public void initTablePret() {
        col_titre.setCellValueFactory(new PropertyValueFactory<>("ouvrage"));
        col_date_emprunt.setCellValueFactory(new PropertyValueFactory<>("date_emprunt"));
        col_date_retour.setCellValueFactory(new PropertyValueFactory<>("date_retour"));
        col_date_limite.setCellValueFactory(new PropertyValueFactory<>("date_retour_limite"));
    }
}
