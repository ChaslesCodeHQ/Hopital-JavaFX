package com.hnk;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVousList {

    private int idRendezVous;    // id du rendez-vous
    private int idPatient;        // id du patient
    private String nomPatient;
    private String photo;
    private LocalDate date;
    private LocalTime heure;
    private String motif;
    private int idMedecin;
    private String statut;
    private String commentaire;   // urgent, normal, termine
    private boolean lu;

    // Constructeur complet
    public RendezVousList(int idRendezVous, int idPatient, String nomPatient, int idMedecin,
                          String photo, LocalDate date, LocalTime heure,
                          String motif, String statut, String commentaire, boolean lu) {
        this.idRendezVous = idRendezVous;
        this.idPatient = idPatient;
        this.nomPatient = nomPatient;
        this.idMedecin = idMedecin;
        this.photo = photo;
        this.date = date;
        this.heure = heure;
        this.motif = motif;
        this.statut = statut;
        this.commentaire = commentaire;
        this.lu = lu;
    }

    // Constructeur vide
    public RendezVousList() {
    }

    // ======= GETTERS =======
    public int getIdRendezVous() {
        return idRendezVous;
    }

    public int getId() {
        return idPatient;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public String getPhotoPath() {
        return photo;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public String getMotif() {
        return motif;
    }

    public int getIdMedecin() {
        return idMedecin;
    }

    public String getStatut() {
        return statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public boolean isLu() {
        return lu;
    }

    // ======= SETTERS =======
    public void setIdRendezVous(int idRendezVous) {
        this.idRendezVous = idRendezVous;
    }

    public void setId(int idPatient) {
        this.idPatient = idPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public void setPhotoPath(String photo) {
        this.photo = photo;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setMedecin(String medecinId) {
        try {
            this.idMedecin = Integer.parseInt(medecinId);
        } catch (NumberFormatException e) {
            this.idMedecin = 0;
        }
    }

    public void setIdMedecin(int idMedecin) {
        this.idMedecin = idMedecin;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
}
