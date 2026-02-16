package com.hnk;

public class Medecin {

    private int idMedecin;
    private String matricule, nomMedecin, prenomMedecin, specialite;

    public Medecin(int idMedecin, String matricule, String nomMedecin, String prenomMedecin, String specialite) {
        this.idMedecin = idMedecin;
        this.matricule = matricule;
        this.nomMedecin = nomMedecin;
        this.prenomMedecin = prenomMedecin;
        this.specialite = specialite;
    }

    public int getIdMedecin() { return idMedecin; }
    public String getNomComplet() { return "Dr " + nomMedecin + " " + prenomMedecin + " (" + specialite + ")"; }



    @Override
    public String toString() { return getNomComplet(); }
}
