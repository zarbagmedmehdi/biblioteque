package controller;

import bean.Auteur;
import bean.Ouvrage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.AuteurService;
import service.OuvrageService;
import util.AlertUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentListOuvrageController implements Initializable {
    @FXML
    private TextField titre;
    @FXML
    private TextField domaine;
    @FXML
    private ComboBox<Auteur> auteur = new ComboBox<Auteur>();
    @FXML
    private ComboBox<String> emplacement = new ComboBox<String>();


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
    private TableColumn<Ouvrage, String> col_emplacement = new TableColumn<>();


    private OuvrageService ouvrageService = new OuvrageService();
    private AuteurService auteurService = new AuteurService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAuteur();
        initEmplacement();
        initTable();
    }

    private void initAuteur(){
        auteur.setItems(FXCollections.observableArrayList(auteurService.findAll()));
    }
    private void initEmplacement(){
        List<String> sites= new ArrayList<>();
        sites.add("FST");
        sites.add("FSSM");
        emplacement.setItems(FXCollections.observableArrayList(sites));
    }
    @FXML
    private void initTable() {
        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_editeur.setCellValueFactory(new PropertyValueFactory<>("editeur"));
        col_annee.setCellValueFactory(new PropertyValueFactory<>("annee"));
        col_domaine.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        col_Auteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        col_emplacement.setCellValueFactory(new PropertyValueFactory<>("site"));
    }

    private void initAfterSearch(){
        titre.setText("");
        domaine.setText("");
        auteur.setValue(null);
        emplacement.setValue(null);
    }

    public void rechercherOuvrage(ActionEvent actionEvent) {
        List<Ouvrage> ouvrages;
        String vTitre = titre.getText();
        String vDomaine =domaine.getText();
        Auteur vAuteur=auteur.getValue();
        String vSite=emplacement.getValue();
        ouvrages = ouvrageService.findByCriteria(vTitre,vDomaine,vAuteur,vSite);
        if (ouvrages == null || ouvrages.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Attention", "Recherche ouvrage", "aucun ouvrage ne correspond au critères fournis");
        }
        table_ouvrage.setItems(FXCollections.observableArrayList(ouvrages));
        initAfterSearch();
    }
}
