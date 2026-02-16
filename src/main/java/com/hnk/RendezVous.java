package com.hnk;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVous {
    private int idRendezVous;
    private int idPatient;
    private int idMedecin;
    private String patient;
    private String medecin;
    private LocalDate date;
    private LocalTime heure;
    private String motif;
    private String statut;
    private String commentaire;

    public RendezVous(int idRendezVous, int idPatient, int idMedecin, LocalDate date, LocalTime heure,
                      String motif, String statut, String commentaire) {
        this.idRendezVous = idRendezVous;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.date = date;
        this.heure = heure;
        this.motif = motif;
        this.statut = statut;
        this.commentaire = commentaire;
    }

    // Getters et setters
    public int getIdRendezVous() { return idRendezVous; }
    public int getIdPatient() { return idPatient; }
    public int getIdMedecin() { return idMedecin; }
    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }
    public String getMedecin() { return medecin; }
    public void setMedecin(String medecin) { this.medecin = medecin; }
    public LocalDate getDate() { return date; }
    public LocalTime getHeure() { return heure; }
    public String getMotif() { return motif; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getCommentaire() { return commentaire; }
}
