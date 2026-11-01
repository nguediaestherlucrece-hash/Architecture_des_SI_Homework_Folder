package models;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Utilisateur() {
    }

    public Utilisateur(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public Utilisateur(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    @Override
    public String toString() {
        return nom + " <" + email + ">";
    }
}
