package fr.univamu.iut.platsutilisateurs.model;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String addresse;

    /**
     * Constructeur de la classe Utilisateur
     * @param id L'identifiant unique de l'utilisateur
     * @param nom Le nom de l'utilisateur
     * @param prenom Le prénom de l'utilisateur
     * @param email L'adresse email de l'utilisateur
     * @param addresse L'adresse de l'utilisateur
     */
    public Utilisateur(int id, String nom, String prenom, String email, String addresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.addresse = addresse;
    }

    /**
     * Getter pour l'identifiant de l'utilisateur
     * @return L'identifiant de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant de l'utilisateur
     * @param id L'identifiant à définir pour l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter pour le nom de l'utilisateur
     * @return Le nom de l'utilisateur
     */
    public String getnom() {
        return nom;
    }

    /**
     * Setter pour le nom de l'utilisateur
     * @param nom Le nom à définir pour l'utilisateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter pour le prénom de l'utilisateur
     * @return Le prénom de l'utilisateur
     */
    public String getprenom() {
        return prenom;
    }

    /**
     * Setter pour le prénom de l'utilisateur
     * @param prenom Le prénom à définir pour l'utilisateur
     */
    public void setprenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter pour l'adresse email de l'utilisateur
     * @return L'adresse email de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter pour l'adresse email de l'utilisateur
     * @param email L'adresse email à définir pour l'utilisateur
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter pour l'adresse de l'utilisateur
     * @return L'adresse de l'utilisateur
     */
    public String getAddress() {
        return addresse;
    }

    /**
     * Setter pour l'adresse de l'utilisateur
     * @param address L'adresse à définir pour l'utilisateur
     */
    public void setAddress(String address) {
        this.addresse = address;
    }
}
