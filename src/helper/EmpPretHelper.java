package helper;

import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpPretHelper {
     private SimpleStringProperty cne ;
      private SimpleStringProperty titre;
       private SimpleStringProperty date_emprunt;
       private SimpleStringProperty date_retour;
      private SimpleStringProperty date_retour_limit;

    public EmpPretHelper(ResultSet s) throws SQLException {
        this.cne =new SimpleStringProperty(s.getString("cne"));
        this.titre = new SimpleStringProperty(s.getString("titre"));
        this.date_emprunt = new SimpleStringProperty(s.getString("DATE_EMPREUNT"));
        this.date_retour = new SimpleStringProperty(s.getString("date_retour"));
        this.date_retour_limit = new SimpleStringProperty(s.getString("date_retour_limite"));
        toString();
    }

    public String getCne() {
        return cne.get();
    }

    public SimpleStringProperty cneProperty() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne.set(cne);
    }

    public String getTitre() {
        return titre.get();
    }

    public SimpleStringProperty titreProperty() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    public String getDate_emprunt() {
        return date_emprunt.get();
    }

    public SimpleStringProperty date_empruntProperty() {
        return date_emprunt;
    }

    public void setDate_emprunt(String date_emprunt) {
        this.date_emprunt.set(date_emprunt);
    }

    public String getDate_retour() {
        return date_retour.get();
    }

    public SimpleStringProperty date_retourProperty() {
        return date_retour;
    }

    public void setDate_retour(String date_retour) {
        this.date_retour.set(date_retour);
    }

    public String getDate_retour_limit() {
        return date_retour_limit.get();
    }

    public SimpleStringProperty date_retour_limitProperty() {
        return date_retour_limit;
    }

    @Override
    public String toString() {
        return "EmpPretHelper{" +
                "cne=" + cne +
                ", titre=" + titre +
                ", date_emprunt=" + date_emprunt +
                ", date_retour=" + date_retour +
                ", date_retour_limit=" + date_retour_limit +
                '}';
    }

    public void setDate_retour_limit(String date_retour_limit) {
        this.date_retour_limit.set(date_retour_limit);
    }
}
