package helper;

import bean.Employe;
import javafx.beans.property.SimpleStringProperty;
import service.EmployeService;

public class EmpItemHelper {
    private EmployeService es=new EmployeService();
    private SimpleStringProperty nom;
    private SimpleStringProperty adresse;
    private SimpleStringProperty statut;
    private SimpleStringProperty affectation;
    private SimpleStringProperty login;
    private SimpleStringProperty pasword;

    public EmpItemHelper(Employe e) {
        this.nom=new SimpleStringProperty(e.getNom());
        this.adresse=new SimpleStringProperty(e.getAdresse());
        this.statut=new SimpleStringProperty(e.getStatut());
        this.affectation=new SimpleStringProperty(e.getAffectation());
        this.login=new SimpleStringProperty(e.getLogin());
        this.pasword=new SimpleStringProperty(e.getPasword());
    }

    public EmployeService getEs() {
        return es;
    }

    public void setEs(EmployeService es) {
        this.es = es;
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

    public String getStatut() {
        return statut.get();
    }

    public SimpleStringProperty statutProperty() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut.set(statut);
    }

    public String getAffectation() {
        return affectation.get();
    }

    public SimpleStringProperty affectationProperty() {
        return affectation;
    }

    public void setAffectation(String affectation) {
        this.affectation.set(affectation);
    }

    public String   getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPasword() {
        return pasword.get();
    }

    public SimpleStringProperty paswordProperty() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword.set(pasword);
    }
}
