package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.Sessions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void gotoDashboard(ActionEvent event) throws IOException {
        Main.forward(event, "../sample/dashboard.fxml", this.getClass());
    }

    @FXML
    protected void goToAjoutPassword(ActionEvent event) throws IOException {
        Main.forward(event, "../sample/ajouterPassword.fxml", this.getClass());
    }

    @FXML
    protected void goToAjoutNote(ActionEvent event) throws IOException {
        Main.forward(event, "../sample/ajouterNote.fxml", this.getClass());
    }

    @FXML
    protected void goToQuestionRep(ActionEvent event) throws IOException {
        Main.forward(event, "../sample/questionRepo.fxml", this.getClass());
    }

    @FXML
    protected void goToGestionPassword(ActionEvent event) throws IOException {
        Main.forward(event, "../sample/chercherPassword.fxml", this.getClass());
    }

    @FXML
    protected void disconnect(ActionEvent event) throws IOException {
        Sessions.clear();
        Main.forward(event, "../sample/main.fxml", this.getClass());
    }


}
