package com.hnk;

public class Medicament {
    private String nom;
    private String posologie;
    private String duree;

    public Medicament(String nom, String posologie, String duree) {
        this.nom = nom;
        this.posologie = posologie;
        this.duree = duree;
    }

    public String getNom() { return nom; }
    public String getPosologie() { return posologie; }
    public String getDuree() { return duree; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPosologie(String posologie) { this.posologie = posologie; }
    public void setDuree(String duree) { this.duree = duree; }
}
