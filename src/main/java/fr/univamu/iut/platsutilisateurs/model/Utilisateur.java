package fr.univamu.iut.platsutilisateurs.model;

public class Utilisateur {
    public int id;
    public String nom;
    public String prenom;
    public String Email;
    public String address;

    public Utilisateur(int Uid, String nom, String prenom, String UEmail, String Uaddress) {
        this.id = Uid;
        this.nom = nom;
        this.prenom = prenom;
        this.Email = UEmail;
        this.address = Uaddress;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getnom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getprenom() {
        return prenom;
    }
    public void setprenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
