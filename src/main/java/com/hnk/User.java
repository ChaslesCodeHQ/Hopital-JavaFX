package com.hnk;

public class User {

    private int id;
    private String matricule;
    private String nom; // nom de l'utilisateur
    private String roles; // rôle (singulier)

    public User(int id, String matricule, String nom, String roles) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.roles = roles;
    }

    // Getters (doivent correspondre aux noms utilisés dans PropertyValueFactory)
    public int getId() { return id; }
    public String getMatricule() { return matricule; }  // <-- nom exact
    public String getNom() { return nom; }              // <-- nom exact
    public String getRoles() { return roles; }           // <-- nom exact

    // Setters
    public void setId(int id) { this.id = id; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public void setNom(String nom) { this.nom = nom; }
    public void setRoles(String roles) { this.roles = roles; }
}
