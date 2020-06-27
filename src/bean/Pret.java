package bean;

import java.util.Date;

public class Pret {
    private long id_pret;
    private Date date_emprunt;
    private Date date_retour;
    private Date date_retour_limite;
    private Employe employe;
    private Etudiant etudiant;
    private Ouvrage ouvrage;

    public Pret() {
    }

    public Pret(long id_pret, Date date_emprunt, Date date_retour, Date date_retour_limite, Ouvrage ouvrage) {
        this.id_pret = id_pret;
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
        this.date_retour_limite = date_retour_limite;
        this.ouvrage = ouvrage;
    }

    public Pret(long id_pret, Date date_emprunt, Date date_retour, Date date_retour_limite) {
        this.id_pret = id_pret;
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
        this.date_retour_limite = date_retour_limite;
    }

    public Pret(Date date_emprunt, Date date_retour_limite) {
        this.date_emprunt = date_emprunt;
        this.date_retour_limite = date_retour_limite;
    }

    public Pret(Date date_emprunt, Date date_retour, Date date_retour_limite, Employe employe, Etudiant etudiant, Ouvrage ouvrage) {
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
        this.date_retour_limite = date_retour_limite;
        this.employe = employe;
        this.etudiant = etudiant;
        this.ouvrage = ouvrage;
    }

    public long getId_pret() {
        return id_pret;
    }

    public void setId_pret(long id_pret) {
        this.id_pret = id_pret;
    }

    public Date getDate_emprunt() {
        return date_emprunt;
    }

    public void setDate_emprunt(Date date_emprunt) {
        this.date_emprunt = date_emprunt;
    }

    public Date getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(Date date_retour) {
        this.date_retour = date_retour;
    }

    public Date getDate_retour_limite() {
        return date_retour_limite;
    }

    public void setDate_retour_limite(Date date_retour_limite) {
        this.date_retour_limite = date_retour_limite;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    @Override
    public String toString() {
        return "Pret{" +
                "id_pret=" + id_pret +
                ", date_emprunt=" + date_emprunt +
                ", date_retour=" + date_retour +
                ", date_retour_limite=" + date_retour_limite +
                ", employe=" + employe +
                ", etudiant=" + etudiant +
                ", ouvrage=" + ouvrage +
                '}';
    }
}
