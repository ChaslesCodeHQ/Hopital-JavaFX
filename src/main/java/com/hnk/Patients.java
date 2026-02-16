package com.hnk;

import java.time.LocalDate;

public class Patients {

    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private LocalDate dateNais;
    private String adresse;
    private String telephone;
    private String etat;
    private String profession;
    private LocalDate dateEnregistrement;

    public Patients(int id, String matricule, String nom, String prenom,
                    LocalDate dateNais, String adresse, String telephone,
                    String etat, String profession, LocalDate dateEnregistrement) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNais = dateNais;
        this.adresse = adresse;
        this.telephone = telephone;
        this.etat = etat;
        this.profession = profession;
        this.dateEnregistrement = dateEnregistrement;
    }

    public int getId() { return id; }
    public String getMatricule() { return matricule; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNais() { return dateNais; }
    public String getAdresse() { return adresse; }
    public String getTelephone() { return telephone; }
    public String getEtat() { return etat; }
    public String getProfession() { return profession; }
    public LocalDate getDateEnregistrement() { return dateEnregistrement; }
}
