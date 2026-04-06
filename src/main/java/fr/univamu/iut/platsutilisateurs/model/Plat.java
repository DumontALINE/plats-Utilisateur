package fr.univamu.iut.platsutilisateurs.model;

public class Plat {
    private int id;
    private String nom;
    private String description;
    private double prix;

    /**
     * Constructeur de la classe Plat
     * @param id L'identifiant unique du plat
     * @param nom Le nom du plat
     * @param description Une description du plat
     * @param prix Le prix du plat
     */
    public Plat(int id, String nom, String description, double prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    /**
     * Getter pour l'identifiant du plat
     * @return L'identifiant du plat
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant du plat
     * @param id L'identifiant à définir pour le plat
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter pour le nom du plat
     * @return Le nom du plat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter pour le nom du plat
     * @param nom Le nom à définir pour le plat
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter pour la description du plat
     * @return La description du plat
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter pour la description du plat
     * @param description La description à définir pour le plat
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter pour le prix du plat
     * @return Le prix du plat
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Setter pour le prix du plat
     * @param prix Le prix à définir pour le plat
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }
}
