package controller;

import helper.EmpItemHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;
import service.EmpPretService;
import service.EmployeService;
import util.AlertUtil;
import util.FxUtil;
import util.Sessions;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AjouterEmployeController  implements Initializable  {
    @FXML
    TextField loginField;
    @FXML
    TextField nomField;
    @FXML
    TextField  statutField;
    @FXML
    TextField adresseField;
    @FXML
    ProgressIndicator progress;
    @FXML
     TabPane tabPane;
    @FXML
    Tab ajouter;
    @FXML
    Tab lister ;
    @FXML
Button modifier;
    @FXML
    Button voir;
    @FXML
    Button supprimer;
    ArrayList<TextField> txs=new ArrayList<>();
@FXML
TableView empTable;
    @FXML
    TableView pretTable;
    private ObservableList<EmpItemHelper> EmpHelper =new ObservableListBase<EmpItemHelper>() {
        @Override
        public EmpItemHelper get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    };
    private ObservableList<helper.EmpPretHelper> EmpPretHelper =new ObservableListBase<helper.EmpPretHelper>() {
        @Override
        public helper.EmpPretHelper get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    };


    @FXML private TableColumn<helper.EmpPretHelper, String> cne=new TableColumn<>("cne");
    @FXML private TableColumn<helper.EmpPretHelper, String> titre=new TableColumn<>("titre");
    @FXML private TableColumn<helper.EmpPretHelper, String> date_emprunt=new TableColumn<>("date_emprunt");
    @FXML private TableColumn<helper.EmpPretHelper,String > date_retour=new TableColumn<>("date_retour");
    @FXML private TableColumn<helper.EmpPretHelper,String > date_retour_limit=new TableColumn<>("date_retour_limit");



    @FXML private TableColumn<EmpItemHelper, String> nom=new TableColumn<>("nom");
    @FXML private TableColumn<EmpItemHelper, String> adresse=new TableColumn<>("adresse");
    @FXML private TableColumn<EmpItemHelper, String> statut=new TableColumn<>("affectation");
    @FXML private TableColumn<EmpItemHelper,String > login=new TableColumn<>("login");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     txs.add(loginField);     txs.add(nomField);
     txs.add(statutField);     txs.add(adresseField);
     progress.setProgress(-1);

     progress.setVisible(false);
     nom.setMinWidth(100);     statut.setMinWidth(100);
     adresse.setMinWidth(200);     login.setMinWidth(200);
     modifier.setVisible(false);   supprimer.setVisible(false);voir.setVisible(false);
        empTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                modifier.setVisible(true);   supprimer.setVisible(true);voir.setVisible(true);
            }else                { modifier.setVisible(false); voir.setVisible(false);  supprimer.setVisible(false);}

        });

        try {
            populateEmpTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabPane.getTabs().get(2).setDisable(true);
        tabPane.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
           if( newValue.intValue()==1) {
               try { populateEmpTable();modifier.setVisible(false);   supprimer.setVisible(false); } catch (SQLException e) { e.printStackTrace(); }
           }
            else if( newValue.intValue()==2) {
                try { populateEmpTable();modifier.setVisible(false);   supprimer.setVisible(false); } catch (SQLException e) { e.printStackTrace(); }
            }
        });
    }

    public void enregister(ActionEvent actionEvent) throws SQLException, MessagingException {
        System.out.println("enregister");
        boolean is=false;
        for (int i = 0; i < txs.size(); i++) {
           if((!FxUtil.isFilled(txs.get(i)) || !FxUtil.isLength(txs.get(i),4)) && is==false){
               AlertUtil.showAlert("Erreur","Formulaire non bien rempli", Alert.AlertType.WARNING); {
               is=true;}
            } }
           if(is==false){
              int val= EmployeService.createEmploye( loginField.getText(), adresseField.getText()  ,
                       nomField.getText(), statutField.getText(),progress);
              if(val==1){
                  for (int j = 0; j < txs.size(); j++) {
                      txs.get(j).setText("");
                      }
               }
           }



    }
    public void populateEmpTable() throws SQLException {
        EmpHelper = FXCollections.observableArrayList(EmployeService.parseList(EmployeService.getAllEmploye()));
        nom.setCellValueFactory(new PropertyValueFactory<EmpItemHelper, String>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<EmpItemHelper, String>("adresse"));
        statut.setCellValueFactory(new PropertyValueFactory<EmpItemHelper, String>("statut"));
        login.setCellValueFactory(new PropertyValueFactory<EmpItemHelper, String >("login"));

        empTable.getColumns().clear();
        empTable.getColumns().addAll(nom, login, statut, adresse);
        System.out.println("i got in it ;");
        empTable.getItems().setAll(EmpHelper);
        }


    public void modifier(ActionEvent actionEvent) throws IOException {
        Sessions.createAtrribute((EmpItemHelper) empTable.getSelectionModel().getSelectedItem(),"selectedEmpItem");
        System.out.println(((EmpItemHelper) empTable.getSelectionModel().getSelectedItem()).getStatut());
        Main.forward(actionEvent, "../view/administrateur/EmployeInfoItem.fxml", this.getClass());
    }

    public void supprimer(ActionEvent actionEvent) throws SQLException, MessagingException {
        EmpItemHelper selectedItem = (EmpItemHelper) empTable.getSelectionModel().getSelectedItem();
        if( selectedItem!=null) {
            if(AlertUtil.showConfirmation("Supprresion","Vous voulez vraiment supprimer? ")){
                if(EmployeService.deleteEmploye(selectedItem.getLogin())==1)
                    empTable.getItems().remove(selectedItem);

            }
        }
    }
    public void voir(ActionEvent actionEvent) throws SQLException, MessagingException {
        EmpItemHelper selectedItem = (EmpItemHelper) empTable.getSelectionModel().getSelectedItem();
        if( selectedItem!=null) {
            populatePretTable(selectedItem.getLogin());
            tabPane.getSelectionModel().select(2);            }
        }
    public void populatePretTable(String login) throws SQLException {
        EmpPretHelper = FXCollections.observableArrayList(EmpPretService.getEmpPrets(login));
        cne.setCellValueFactory(new PropertyValueFactory<helper.EmpPretHelper, String>("cne"));
        titre.setCellValueFactory(new PropertyValueFactory<helper.EmpPretHelper, String>("titre"));
        date_emprunt.setCellValueFactory(new PropertyValueFactory<helper.EmpPretHelper, String>("date_emprunt"));
        date_retour.setCellValueFactory(new PropertyValueFactory<helper.EmpPretHelper, String >("date_retour"));
        date_retour_limit.setCellValueFactory(new PropertyValueFactory<helper.EmpPretHelper, String >("date_retour_limit"));
        pretTable.getColumns().clear();
        pretTable.getColumns().addAll(cne, titre, date_emprunt, date_retour,date_retour_limit);
        System.out.println("i got in it ;");
        pretTable.getItems().setAll(EmpPretHelper);
    }
}
