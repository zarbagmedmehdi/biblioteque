package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.Optional;

public class AlertUtil {
    public  static Boolean showInputalert(String title,String question,String reponse) {

    TextInputDialog dialog = new TextInputDialog("réponse");
dialog.setTitle("Text Input Dialog");
dialog.setHeaderText(title);
dialog.setContentText(question);

    Optional<String> result = dialog.showAndWait();
if (result.isPresent()){
      if(result.get().equals(reponse))
      return true;
      else return false;
    }
else
    return false;

//result.ifPresent(name -> System.out.println("Your name: " + name));
    }
    public  static void showAlert(String title,String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public  static void showAlert(String title, String message, Alert.AlertType x) {
        Alert alert = new Alert(x);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void populateResult(ArrayList<String> list, TextArea query, TextArea result){
        if(list.size()>0){
            query.setText("requete utilisé :"+list.get(0));
            result.setText("Résultat : "+list.get(1));
        }
    }
    //erreur de connexion
    public static void loginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("connexion:");
        alert.setContentText("Veuillez saisir des identifiants de longueur plus de 6 caracteres");
        alert.showAndWait();
    }

    public static boolean showConfirmation(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            return false;
        } else if (option.get() == ButtonType.OK) {
            return true;
        } else if (option.get() == ButtonType.CANCEL) {
            return false;
        } else {
            return false;
        }
    }
    public static void duploginError(String nom) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Inscription:");
        alert.setContentText(nom+" existe déja");
        alert.showAndWait();
    }
    public static void ErreurBD() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur interne :");
        alert.setContentText("Base données ");
        alert.showAndWait();
    }
    public static void falseloginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Connexion:");
        alert.setContentText("Connexion echouée!");
        alert.showAndWait();
    }
    public static void nameNotSetAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Modification:");
        alert.setContentText("Role non defini");
        alert.showAndWait();
    }
    public static void nameNoSetAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Création:");
        alert.setContentText("Role non defini");
        alert.showAndWait();
    }



    public static void passwordCreationNotSetAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText("Attention:");
        alert.setContentText("mot de passe non saisi.");
        alert.showAndWait();
    }

    public static void appCreationNotSetAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText("Attention:");
        alert.setContentText("schema ou package non saisi.");
        alert.showAndWait();
    }

    public static void noIdentificationOptionSetAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erruer");
        alert.setHeaderText("Erreur:");
        alert.setContentText("Aucun moyen d'identification n'est sélectionné.");
        alert.showAndWait();
    }
    public static void roleExistAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erruer");
        alert.setHeaderText("Erreur:");
        alert.setContentText("role existant dans la base de données");
        alert.showAndWait();
    }
    public static void noRoleSelectedForGrantAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erruer");
        alert.setHeaderText("Erreur:");
        alert.setContentText("Veuillez selectionner au moins un role.");
        alert.showAndWait();
    }
    public static void noGrantedSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erruer");
        alert.setHeaderText("Erreur:");
        alert.setContentText("Veuillez selectionner au moins un role ou un utilisateur.");
        alert.showAndWait();
    }

    public static void showCreateAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Création:");
        alert.setContentText("avec succes");
        alert.showAndWait();
    }
    public static void showAlterAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Modification:");
        alert.setContentText("avec succes");
        alert.showAndWait();
    }
    public static void showGrantAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Attribution:");
        alert.setContentText("avec succes");
        alert.showAndWait();
    }

    public static void showGrantError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur d'attribution:");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public static void showUpdateAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("modification:");
        alert.setContentText("avec succes");
        alert.showAndWait();
    }

    public static void showUpdateError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur de modification:");
        alert.setContentText(error);
        alert.showAndWait();
    }
    public static void showDelete() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Suppression:");
        alert.setContentText("avec succes");
        alert.showAndWait();
    }
    public static void showDeleteError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Suppression:");
        alert.setContentText(error);
        alert.showAndWait();
    }
}
