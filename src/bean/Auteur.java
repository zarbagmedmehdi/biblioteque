package bean;

public class Auteur {
    private long id_aut;
    private String nom_auteur;

    public Auteur() {
    }

    public Auteur(String nom_auteur) {
        this.nom_auteur = nom_auteur;
    }

    public Auteur(long id_aut, String nom_auteur) {
        this.id_aut = id_aut;
        this.nom_auteur = nom_auteur;
    }

    public long getId_aut() {
        return id_aut;
    }

    public void setId_aut(long id_aut) {
        this.id_aut = id_aut;
    }

    public String getNom_auteur() {
        return nom_auteur;
    }

    public void setNom_auteur(String nom_auteur) {
        this.nom_auteur = nom_auteur;
    }

    @Override
    public String toString() {
        return nom_auteur;
    }
}
