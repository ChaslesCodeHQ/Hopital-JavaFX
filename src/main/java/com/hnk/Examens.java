package com.hnk;

import java.time.LocalDate;

public class Examens {

    private int id;
    private int idPatient;
    private String typeExamen;
    private LocalDate datePrescription;
    private String resultat;
    private String fichierPath;
    private String statut;

    public Examens() {}

    public Examens(int idPatient, String typeExamen, LocalDate datePrescription) {
        this.idPatient = idPatient;
        this.typeExamen = typeExamen;
        this.datePrescription = datePrescription;
        this.statut = "Prescrit";
    }

    public Examens(int id, int idPatient, String typeExamen,
                   LocalDate datePrescription, String resultat,
                   String fichierPath, String statut) {
        this.id = id;
        this.idPatient = idPatient;
        this.typeExamen = typeExamen;
        this.datePrescription = datePrescription;
        this.resultat = resultat;
        this.fichierPath = fichierPath;
        this.statut = statut;
    }

    public int getId() { return id; }
    public int getIdPatient() { return idPatient; }
    public String getTypeExamen() { return typeExamen; }
    public LocalDate getDatePrescription() { return datePrescription; }
    public String getResultat() { return resultat; }
    public String getFichierPath() { return fichierPath; }
    public String getStatut() { return statut; }


}
