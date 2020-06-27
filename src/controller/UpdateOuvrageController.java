package controller;

import bean.Ouvrage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.OuvrageService;
import util.AlertUtil;
import util.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateOuvrageController implements Initializable {
    @FXML
    private AnchorPane ap;

    private Ouvrage ouvrage;
    @FXML
    private Label titre;
    @FXML
    private Label oldStock;
    @FXML
    private Spinner <Integer> newStock;

    private OuvrageService ouvrageService=new OuvrageService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initOuvrageFields(Ouvrage ouvrage) {
        this.ouvrage=ouvrage;
        titre.setText(ouvrage.getTitre());
        oldStock.setText(String.valueOf(ouvrage.getStock()));
        Object o=newStock;
         o=newStock.getValueFactory();
         o=ouvrage.getStock();
         newStock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,ouvrage.getStock()));
        //newStock.getValueFactory().setValue(ouvrage.getStock());

    }

    public void modifier(ActionEvent actionEvent) {
        if (newStock.getValue()==null || !Util.validateInteger(String.valueOf(newStock.getValue()))){
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Erreur", "Modification de stock", "Veuillez dpnner un nombre d'exemplaire valide.");
        }else {
            int vStock=newStock.getValue();
            String msg="Voulez-vous modifier le stock de l'ouvrage << "+ouvrage.getTitre()+" >> de "+ ouvrage.getStock()+ " à "+vStock+" ?";
            Alert alert=AlertUtil.getConfirmationDialog("Modification du stock",msg);
            alert.showAndWait();
            if (alert.getResult()== ButtonType.YES){
                int result=ouvrageService.updateStock(ouvrage,vStock);
                switch (result) {
                    case 1:
                        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Succès", "modification  d'ouvrage", "Le stocj de l'ouvrage a été modifié");
                        ((Stage)ap.getScene().getWindow()).close();
                        break;
                    default:
                }
            }
        }
    }
}
