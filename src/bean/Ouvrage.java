package bean;

public class Ouvrage {
    private long id_ouv;
    private String titre;
    private String editeur;
    private int annee;
    private String domaine;
    private int stock;
    private String site;
    private Auteur auteur;

    public Ouvrage() {
    }

    public Ouvrage(String titre) {
        this.titre = titre;
    }

    public Ouvrage( String titre, String editeur, int annee, String domaine, int stock, String site, Auteur auteur) {
        this.titre = titre;
        this.editeur = editeur;
        this.annee = annee;
        this.domaine = domaine;
        this.stock = stock;
        this.site = site;
        this.auteur = auteur;
    }

    public Ouvrage(long id_ouv, String titre, String editeur, int annee, String domaine, int stock, Auteur auteur) {
        this.id_ouv = id_ouv;
        this.titre = titre;
        this.editeur = editeur;
        this.annee = annee;
        this.domaine = domaine;
        this.stock = stock;
        this.auteur = auteur;
    }

    public Ouvrage(String titre, String editeur, int annee, String domaine, int stock) {
        this.titre = titre;
        this.editeur = editeur;
        this.annee = annee;
        this.domaine = domaine;
        this.stock = stock;
    }

    public Ouvrage(long id_ouv, String titre, String editeur, int annee, String domaine, int stock) {
        this.id_ouv = id_ouv;
        this.titre = titre;
        this.editeur = editeur;
        this.annee = annee;
        this.domaine = domaine;
        this.stock = stock;
    }

    public Ouvrage(String titre, String editeur, int annee, String domaine, int stock, Auteur auteur) {
        this.titre = titre;
        this.editeur = editeur;
        this.annee = annee;
        this.domaine = domaine;
        this.stock = stock;
        this.auteur = auteur;
    }

    public long getId_ouv() {
        return id_ouv;
    }

    public void setId_ouv(long id_ouv) {
        this.id_ouv = id_ouv;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return titre;
    }
}
