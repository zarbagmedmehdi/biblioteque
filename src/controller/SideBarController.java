package controller;

import javafx.event.ActionEvent;
import sample.Main;

import java.io.IOException;

public class SideBarController {
    public void gotoDashboard(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/dashboard.fxml", this.getClass());
    }

    public void goToCreateOuvrage(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/createOuvrage.fxml", this.getClass());
    }

    public void goToPreterOuvrage(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/ListOuvrage.fxml", this.getClass());
    }

    public void disconnect(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/dashboard.fxml", this.getClass());

    }

    public void gotoListAuteur(ActionEvent actionEvent) throws IOException {
        Main.forward(actionEvent, "../view/ListAuteur.fxml", this.getClass());
    }

    public void gotoRecupererOuvrage(ActionEvent actionEvent)throws IOException {
        Main.forward(actionEvent, "../view/recupererOuvrage.fxml", this.getClass());

    }
}
