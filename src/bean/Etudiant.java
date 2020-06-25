package bean;

public class Etudiant {
    private long id_etu;
    private String CNE;
    private String nom;
    private String adresse;
    private String universite;
    private String cursus;
    private int nb_emprunts;
    private boolean sanctionne;

    public Etudiant() {
    }

    public Etudiant(long id_etu, String CNE, String nom, String adresse, String universite, String cursus, int nb_emprunts, boolean sanctionne) {
        this.id_etu = id_etu;
        this.CNE = CNE;
        this.nom = nom;
        this.adresse = adresse;
        this.universite = universite;
        this.cursus = cursus;
        this.nb_emprunts = nb_emprunts;
        this.sanctionne = sanctionne;
    }
    public Etudiant(String CNE, String nom, String adresse, String cursus, int nb_emprunts, boolean sanctionne) {
        this.CNE = CNE;
        this.nom = nom;
        this.adresse = adresse;
        this.cursus = cursus;
        this.nb_emprunts = nb_emprunts;
        this.sanctionne = sanctionne;
    }


    public String getCNE() {
        System.out.println("bean"+CNE);

        return CNE;
    }

    public void setCNE(String CNE) {
        this.CNE = CNE;
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

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getCursus() {
        return cursus;
    }

    public void setCursus(String cursus) {
        this.cursus = cursus;
    }

    public int getNb_emprunts() {
        return nb_emprunts;
    }

    public void setNb_emprunts(int nb_emprunts) {
        this.nb_emprunts = nb_emprunts;
    }

    public boolean isSanctionne() {
        return sanctionne;
    }

    public void setSanctionne(boolean sanctionne) {
        this.sanctionne = sanctionne;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id_etu=" + id_etu +
                ", CNE='" + CNE + '\'' +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", universite='" + universite + '\'' +
                ", cursus='" + cursus + '\'' +
                ", nb_emprunts=" + nb_emprunts +
                ", sanctionne=" + sanctionne +
                '}';
    }
}
