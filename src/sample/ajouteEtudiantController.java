package sample;

import helper.EtudInfoHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.EmployeService;
import service.EtudiantService;
import util.AlertUtil;
import util.FxUtil;
import util.Sessions;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Math.E;

public class ajouteEtudiantController implements Initializable {
    @FXML
    TextField cneField;
    @FXML
    TextField cneField1;
    @FXML
    TextField nomField;
    @FXML
    ComboBox  cursusCombo1;
    @FXML
    ComboBox  cursusCombo;
    @FXML
    TextField adresseField;
    @FXML
    TabPane tabPane;
    @FXML
    Tab ajouter;
    @FXML
    Tab lister ;
    @FXML
    Button modifier;
    @FXML
    Button supprimer;
    ArrayList<TextField> txs=new ArrayList<>();
    @FXML
    TableView etudTable;
    private ObservableList<EtudInfoHelper> EmpHelper =new ObservableListBase<EtudInfoHelper>() {
        @Override
        public EtudInfoHelper get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    };


    @FXML private TableColumn<EtudInfoHelper, String> nom=new TableColumn<>("nom");
    @FXML private TableColumn<EtudInfoHelper, String> adresse=new TableColumn<>("adresse");
    @FXML private TableColumn<EtudInfoHelper, String> cne=new TableColumn<>("cne");
    @FXML private TableColumn<EtudInfoHelper,String > cursus=new TableColumn<>("cursus");
    @FXML private TableColumn<EtudInfoHelper,String > nb_emprunts=new TableColumn<>("nb_emprunts");
    @FXML private TableColumn<EtudInfoHelper,String > sanctionne=new TableColumn<>("sanctionne");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txs.add(cneField);     txs.add(nomField);
        txs.add(adresseField);
        setSiteComboListenner();
        nom.setMinWidth(100);     cne.setMinWidth(100);
        adresse.setMinWidth(150);     cursus.setMinWidth(300);
        nb_emprunts.setMinWidth(100);     sanctionne.setMinWidth(100);
if(Sessions.getAttribut("tab")!=null) {tabPane.getSelectionModel().select(1);Sessions.delete("tab");}

        etudTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                modifier.setVisible(true);   supprimer.setVisible(true);
            }else                { modifier.setVisible(false);   supprimer.setVisible(false);}

        });

        try {
            populateetudTable(0,"","");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tabPane.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
            if( newValue.intValue()==1) {
                try { populateetudTable(0,"","");modifier.setVisible(false);   supprimer.setVisible(false); } catch (SQLException e) { e.printStackTrace(); }
            }
        });

        
    }






    public void populateetudTable(int val,String cn,String curs) throws SQLException {
        EmpHelper = FXCollections.observableArrayList(EtudiantService.parseList(EtudiantService.getEtudiant(val, cn, curs)));
        nom.setCellValueFactory(new PropertyValueFactory<EtudInfoHelper, String>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<EtudInfoHelper, String>("adresse"));
        cne.setCellValueFactory(new PropertyValueFactory<EtudInfoHelper, String>("cne"));
        cursus.setCellValueFactory(new PropertyValueFactory<EtudInfoHelper, String >("cursus"));
        nb_emprunts.setCellValueFactory(new PropertyValueFactory<EtudInfoHelper, String >("nb_emprunts"));
        sanctionne.setCellValueFactory(new PropertyValueFactory<EtudInfoHelper, String >("sanctionne"));
        etudTable.getColumns().clear();
        etudTable.getColumns().addAll(nom, cne, cursus, adresse,nb_emprunts,sanctionne);
        System.out.println("i got in it ;");
        etudTable.getItems().setAll(EmpHelper);
    }


    public void modifier(ActionEvent actionEvent) throws IOException {
        Sessions.createAtrribute((EtudInfoHelper) etudTable.getSelectionModel().getSelectedItem(),"selectedEtudiantInfo");
        Main.forward(actionEvent, "../view/etudInfoItem.fxml", this.getClass());
    }

    public void supprimer(ActionEvent actionEvent) throws SQLException, MessagingException {
        EtudInfoHelper selectedItem = (EtudInfoHelper) etudTable.getSelectionModel().getSelectedItem();
        if( selectedItem!=null) {
            if(AlertUtil.showConfirmation("Supprresion","Vous voulez vraiment supprimer? ")){
               if(EtudiantService.deleteEtudiant(selectedItem.getCne())==1)
                   etudTable.getItems().remove(selectedItem);
            }

        }
    }


    public void setSiteComboListenner(){
        cursusCombo.getItems().addAll  ("Ingénierie en Réseauxs et Systèmes d’Information","Génie Civil",
                "Ingénierie en Finance et Actuariat",
                "Industrie et Sécurité des Aliments",
                "Génie des Matériaux et Procédés ",
                "Energies renouvelables & mobilité électrique");
        cursusCombo1.getItems().add("");
        cursusCombo1.getItems().addAll(cursusCombo.getItems());

        cursusCombo.getSelectionModel().select(0);
        cursusCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {

            }
        });
    }

    public void creer(ActionEvent actionEvent) throws SQLException, MessagingException {
        System.out.println("enregister");
        boolean is=false;
        for (int i = 0; i < txs.size(); i++) {
            if((!FxUtil.isFilled(txs.get(i)) || !FxUtil.isLength(txs.get(i),4)) && is==false){
                AlertUtil.showAlert("Erreur","Formulaire non bien rempli", Alert.AlertType.WARNING); {
                    is=true;}
            } }
        if(is==false){
           int val=EtudiantService.createEtudiant( nomField.getText(),
                    adresseField.getText()  , cursusCombo.getValue().toString(), cneField.getText());
            if(val==1){
                for (int j = 0; j < txs.size(); j++) {
                    txs.get(j).setText("");
                }
            }
            }
    }
    public void rechercher(ActionEvent actionEvent) throws SQLException, MessagingException {

        populateetudTable(1,
               cneField1.getText(),
                cursusCombo1.getValue().toString());

    }
    public void tout(ActionEvent actionEvent) throws SQLException, MessagingException {
        populateetudTable(0,"","");
    }
}
