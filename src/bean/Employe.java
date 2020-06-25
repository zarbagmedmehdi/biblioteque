package bean;

public class Employe {
    private long id_pers;
    private String nom;
    private String adresse;
    private String statut;
    private String affectation;
    private String login;
    private String password;

    public Employe() {
    }

    public Employe(String nom, String adresse, String statut, String affectation, String login, String pasword) {
        this.nom = nom;
        this.adresse = adresse;
        this.statut = statut;
        this.affectation = affectation;
        this.login = login;
        this.password = pasword;
    }

    public Employe(long id_pers, String nom, String adresse, String statut, String affectation, String login, String pasword) {
        this.id_pers = id_pers;
        this.nom = nom;
        this.adresse = adresse;
        this.statut = statut;
        this.affectation = affectation;
        this.login = login;
        this.password = pasword;
    }

    public long getId_pers() {
        return id_pers;
    }

    public void setId_pers(long id_pers) {
        this.id_pers = id_pers;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getAffectation() {
        return affectation;
    }

    public void setAffectation(String affectation) {
        this.affectation = affectation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasword() {
        return password;
    }

    public void setPasword(String pasword) {
        this.password = pasword;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id_pers=" + id_pers +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", statut='" + statut + '\'' +
                ", affectation='" + affectation + '\'' +
                ", login='" + login + '\'' +
                ", pasword='" + password + '\'' +
                '}';
    }
}
