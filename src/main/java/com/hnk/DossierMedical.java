package com.hnk;

import java.time.LocalDate;

public class DossierMedical {

    private int id;
    private int patientId;
    private LocalDate dateConsultation;

    private String symptomes;
    private String antecedentsMedicaux;
    private String antecedentsFamiliaux;
    private String allergies;
    private String traitementsEnCours;

    private double temperature;
    private String tensionArterielle;
    private double poids;
    private double taille;
    private int frequenceCardiaque;

    public DossierMedical(int patientId, LocalDate dateConsultation,
                          String symptomes, String antecedentsMedicaux,
                          String antecedentsFamiliaux, String allergies,
                          String traitementsEnCours, double temperature,
                          String tensionArterielle, double poids,
                          double taille, int frequenceCardiaque) {

        this.patientId = patientId;
        this.dateConsultation = dateConsultation;
        this.symptomes = symptomes;
        this.antecedentsMedicaux = antecedentsMedicaux;
        this.antecedentsFamiliaux = antecedentsFamiliaux;
        this.allergies = allergies;
        this.traitementsEnCours = traitementsEnCours;
        this.temperature = temperature;
        this.tensionArterielle = tensionArterielle;
        this.poids = poids;
        this.taille = taille;
        this.frequenceCardiaque = frequenceCardiaque;
    }

    public int getPatientId() { return patientId; }
    public LocalDate getDateConsultation() { return dateConsultation; }
    public String getSymptomes() { return symptomes; }
    public String getAntecedentsMedicaux() { return antecedentsMedicaux; }
    public String getAntecedentsFamiliaux() { return antecedentsFamiliaux; }
    public String getAllergies() { return allergies; }
    public String getTraitementsEnCours() { return traitementsEnCours; }
    public double getTemperature() { return temperature; }
    public String getTensionArterielle() { return tensionArterielle; }
    public double getPoids() { return poids; }
    public double getTaille() { return taille; }
    public int getFrequenceCardiaque() { return frequenceCardiaque; }
}
