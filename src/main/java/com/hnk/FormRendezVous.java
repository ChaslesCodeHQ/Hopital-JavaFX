package com.hnk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;

public class FormRendezVous {

    @FXML private ComboBox<Patient> cbPatient;
    @FXML private ComboBox<Medecin> cbMedecin;
    @FXML private DatePicker dpDate;
    @FXML private TextField tfHeure;
    @FXML private TextField tfMotif;
    @FXML private ComboBox<String> statut;
    @FXML private TextField commentaire;

    @FXML
    public void initialize() throws Exception {

        // Charger patients
        cbPatient.setItems(PatientDAO.getAll());

        // Charger medecins
        cbMedecin.setItems(MedecinDAO.getAll());

        // Charger statut ENUM
        statut.setItems(FXCollections.observableArrayList(
                "EN_ATTENTE",
                "CONFIRME",
                "ANNULE",
                "TERMINER"
        ));
    }

    @FXML
    private void saveRendezVous(ActionEvent event) {

        Patient patient = cbPatient.getValue();
        Medecin medecin = cbMedecin.getValue();
        LocalDate date = dpDate.getValue();
        String heureText = tfHeure.getText();
        String motif = tfMotif.getText();
        String statutValue = statut.getValue();
        String commentaireValue = commentaire.getText();

        // Validation simple
        if (patient == null || medecin == null || date == null ||
                heureText.isEmpty() || statutValue == null) {

            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        try {
            LocalTime heure = LocalTime.parse(heureText); // format HH:mm

            String sql = "INSERT INTO rendez_vous " +
                    "(idPatient, idMedecin, dateRendezVous, heureRendezVous, motif, statut, commentaire) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = Database.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, patient.getIdPatient());
                ps.setInt(2, medecin.getIdMedecin());
                ps.setDate(3, java.sql.Date.valueOf(date));
                ps.setTime(4, java.sql.Time.valueOf(heure));
                ps.setString(5, motif);
                ps.setString(6, statutValue);
                ps.setString(7, commentaireValue);

                ps.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Rendez-vous enregistré avec succès !");
                clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur BD", "Erreur lors de l'enregistrement.");
        }
    }

    private void clearForm() {
        cbPatient.setValue(null);
        cbMedecin.setValue(null);
        dpDate.setValue(null);
        tfHeure.clear();
        tfMotif.clear();
        statut.setValue(null);
        commentaire.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
