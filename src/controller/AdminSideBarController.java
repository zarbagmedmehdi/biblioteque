package controller;

import javafx.event.ActionEvent;
import sample.Main;

import java.io.IOException;

public class AdminSideBarController {

    public void gotoDashboard(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/administrateur/dashboard.fxml", this.getClass());
    }

    public void goToEmploye(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/administrateur/ajouterEmploye.fxml", this.getClass());
    }
    public void goToEtudiant(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/administrateur/ajouterEtudiant.fxml", this.getClass());
    }
}
