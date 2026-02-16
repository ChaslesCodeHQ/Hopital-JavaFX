package com.hnk;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consultation {

    private int id;
    private int rendezVousId;
    private int patientId;
    private int medecinId;
    private LocalDate dateConsultation;
    private LocalTime heureConsultation;
    private String motif;
    private String typeConsultation;
    private String statut;

    // -------------------------
    // Constructeur
    // -------------------------
    public Consultation(int rendezVousId, int patientId, int medecinId,
                        LocalDate dateConsultation, LocalTime heureConsultation,
                        String motif, String typeConsultation) {

        this.rendezVousId = rendezVousId;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.dateConsultation = dateConsultation;
        this.heureConsultation = heureConsultation;
        this.motif = motif;
        this.typeConsultation = typeConsultation;
        this.statut = "EN_COURS";
    }

    // -------------------------
    // Getters standards
    // -------------------------
    public int getId() {
        return id;
    }

    public int getRendezVousId() {
        return rendezVousId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getMedecinId() {
        return medecinId;
    }

    public LocalDate getDateConsultation() {
        return dateConsultation;
    }

    public LocalTime getHeureConsultation() {
        return heureConsultation;
    }

    public String getMotif() {
        return motif;
    }

    public String getTypeConsultation() {
        return typeConsultation;
    }

    public String getStatut() {
        return statut;
    }

    // -------------------------
    // Méthodes utilitaires
    // -------------------------

    /**
     * Récupère le nom du médecin pour cette consultation.
     * Remplacer le code par un appel DAO réel si nécessaire.
     */
    public String getNomMedecin() {
        // Placeholder : remplacer par DAO réel
        return MedecinDAO.getNomById(medecinId);
    }

    /**
     * Récupère le nom du patient pour cette consultation.
     * Remplacer le code par un appel DAO réel si nécessaire.
     */
    public String getNomPatient() {
        // Placeholder : remplacer par DAO réel
        return PatientDAO.getNomById(patientId);
    }

    /**
     * Retourne la date de consultation (alias utile pour PrescriptionController)
     */
    public LocalDate getDate() {
        return dateConsultation;
    }
}
