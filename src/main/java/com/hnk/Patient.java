package com.hnk;

public class Patient {

    private int idPatient;
    private String nom;
    private String prenom;

    public Patient(int idPatient, String nom, String prenom) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getIdPatient() {
        return idPatient;
    }


    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
