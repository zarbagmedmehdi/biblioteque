package controller;

import bean.Employe;
import bean.Etudiant;
import bean.Ouvrage;
import bean.Pret;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.EtudiantService;
import service.PretService;
import util.AlertUtil;
import util.DateUtil;
import util.Sessions;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PreterOuvrageController implements Initializable {
    Ouvrage ouvrage;
    @FXML
    private AnchorPane ap;
    @FXML
    private TextField cne;
    @FXML
    private Label titre;
    @FXML
    private Label dateEmprunt;
    @FXML
    private DatePicker dateRetourLimite;
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

    public PretService pretService = new PretService();
    public EtudiantService etudiantService = new EtudiantService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateEmprunt.setText(DateUtil.getFormatedNow());
        initTable();

    }

    public void initOuvrageFields(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
        titre.setText(ouvrage.getTitre());
    }

    @FXML
    public void initTable() {
        col_cne.setCellValueFactory(new PropertyValueFactory<>("CNE"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_universite.setCellValueFactory(new PropertyValueFactory<>("universite"));
        col_emprunt.setCellValueFactory(new PropertyValueFactory<>("nb_emprunts"));
        col_sanction.setCellValueFactory(new PropertyValueFactory<>("sanctionne"));
    }

    public void filtrer(ActionEvent actionEvent) {
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

    public void preter(ActionEvent actionEvent) {
        Etudiant etudiant = etudiant_table.getSelectionModel().getSelectedItem();
        Employe employe = Sessions.getConnectedEmployee();
        Date dRetLimite = DateUtil.getDateFromDatePicker(dateRetourLimite);
        if (etudiant == null || etudiant.getId_etu() == 0) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "Veuillez selectionner un etudiant de la liste.");
        } else if (dRetLimite == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "Veuillez fournir une date imite de retour de l'ouvrrage.");
        } else if (!(dRetLimite.compareTo(new Date()) > 0)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "La date de retour limite du retour de l'ouvrage doit être en futur");
        } else if (etudiant.getNb_emprunts() >= 3) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "L'etudiant a depassé le nombre de prêt autorisé (3).Ppour plus d'information: voir la liste des prêts des etudiants.");
        } else if (etudiant.isSanctionne()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "L'etudiant a depassé la date limite pour rendre un ouvrage. Pour plus d'information: voir la liste des prêts des etudiants.");
        } else if (ouvrage.getStock()==0) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "Aucun exemplaire de l'ouvrage n'est disponible dans notre bibliothèque.");
        } else {
            Pret pret = new Pret(new Date(), null, dRetLimite, employe, etudiant, ouvrage);
            int result = pretService.preter(pret);
            switch (result) {
                case -1:
                    AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "Sanctionnement: L'etudiant a depassé la date limite pour rendre un ouvrage. Pour plus d'information: voir la liste des prêts des etudiants.");
                    break;
                case -2:
                    AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "L'etudiant a depassé le nombre de prêt autorisé (3).Ppour plus d'information: voir la liste des prêts des etudiants.");
                    break;
                case -3:
                    AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "Aucun exemplaire de l'ouvrage n'est disponible dans notre bibliothèque.");
                    break;
                case -4:
                    AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Prêt", "Impossible d'emprunter plus d'un exemplaire d'ouvrage simultanément .");
                    break;
                case 1: AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Succès", "Prêt", "Prêt avec succès");
                    ((Stage)ap.getScene().getWindow()).close();
                    break;

            }
        }
    }
}
