package helper;

import bean.Etudiant;
import javafx.beans.property.SimpleStringProperty;

public class EtudInfoHelper {
    private SimpleStringProperty cne;
    private SimpleStringProperty nom;
    private SimpleStringProperty adresse;
    private SimpleStringProperty cursus;
    private SimpleStringProperty nb_emprunts;
    private SimpleStringProperty sanctionne;

    public EtudInfoHelper(Etudiant e) {
        this.cne =new SimpleStringProperty(e.getCNE());
        this.nom =new SimpleStringProperty(e.getNom());
        this.adresse =new SimpleStringProperty(e.getAdresse());
        this.cursus =new SimpleStringProperty(e.getCursus());
        this.nb_emprunts =new SimpleStringProperty(""+e.getNb_emprunts());

        String val="";
        if(e.isSanctionne()==true) val="oui";else val="non";
        this.sanctionne =new SimpleStringProperty(val);
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

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public String getCursus() {
        return cursus.get();
    }

    public SimpleStringProperty cursusProperty() {
        return cursus;
    }

    public void setCursus(String cursus) {
        this.cursus.set(cursus);
    }

    public String getNb_emprunts() {
        return nb_emprunts.get();
    }

    public SimpleStringProperty nb_empruntsProperty() {
        return nb_emprunts;
    }

    public void setNb_emprunts(String nb_emprunts) {
        this.nb_emprunts.set(nb_emprunts);
    }

    public String getSanctionne() {
        return sanctionne.get();
    }

    public SimpleStringProperty sanctionneProperty() {
        return sanctionne;
    }

    public void setSanctionne(String sanctionne) {
        this.sanctionne.set(sanctionne);
    }
}
