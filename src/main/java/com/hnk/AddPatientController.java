package com.hnk;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class AddPatientController {

    @FXML private TextField matriculeField;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private DatePicker dateNaisField;
    @FXML private TextField adresseField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> etatField;
    @FXML private TextField professionField;
    @FXML private DatePicker dateEnregistrementField;
    @FXML private Button saveButton;

    private Patients currentPatient; // Pour la modification
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        etatField.getItems().addAll("Actif", "Inactif");
        dateEnregistrementField.setValue(LocalDate.now());
    }

    // ==================================
    // PRÉ-REMPLIR LES DONNÉES POUR MODIFICATION
    // ==================================
    public void setPatientData(Patients patient) {
        this.currentPatient = patient;
        this.isEditMode = true;

        matriculeField.setText(patient.getMatricule());
        nomField.setText(patient.getNom());
        prenomField.setText(patient.getPrenom());
        dateNaisField.setValue(patient.getDateNais());
        adresseField.setText(patient.getAdresse());
        phoneField.setText(patient.getTelephone());
        etatField.setValue(patient.getEtat());
        professionField.setText(patient.getProfession());
        dateEnregistrementField.setValue(patient.getDateEnregistrement());

        saveButton.setText("Modifier");
    }

    // ==================================
    // SAUVEGARDER (INSERT OU UPDATE)
    // ==================================
    @FXML
    private void savePatient() {
        if (validateFields()) {
            if (isEditMode) {
                updatePatient();
            } else {
                insertPatient();
            }
        }
    }

    // ==================================
    // VALIDATION
    // ==================================
    private boolean validateFields() {
        if (matriculeField.getText().isEmpty() ||
                nomField.getText().isEmpty() ||
                prenomField.getText().isEmpty() ||
                dateNaisField.getValue() == null ||
                adresseField.getText().isEmpty() ||
                phoneField.getText().isEmpty() ||
                etatField.getValue() == null ||
                professionField.getText().isEmpty() ||
                dateEnregistrementField.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // ==================================
    // AJOUTER PATIENT
    // ==================================
    private void insertPatient() {
        String sql = "INSERT INTO patients (matriculePatient, nomPatient, prenomPatient, dateNais, adresse, phone, etatSante, profession, dateEnregistrement) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hopital_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matriculeField.getText());
            stmt.setString(2, nomField.getText());
            stmt.setString(3, prenomField.getText());
            stmt.setDate(4, Date.valueOf(dateNaisField.getValue()));
            stmt.setString(5, adresseField.getText());
            stmt.setString(6, phoneField.getText());
            stmt.setString(7, etatField.getValue());
            stmt.setString(8, professionField.getText());
            stmt.setDate(9, Date.valueOf(dateEnregistrementField.getValue()));

            stmt.executeUpdate();

            showSuccess("Patient ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================================
    // MODIFIER PATIENT
    // ==================================
    private void updatePatient() {
        String sql = "UPDATE patients SET matriculePatient=?, nomPatient=?, prenomPatient=?, dateNais=?, adresse=?, phone=?, etatSante=?, profession=?, dateEnregistrement=? WHERE idPatient=?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hopital", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matriculeField.getText());
            stmt.setString(2, nomField.getText());
            stmt.setString(3, prenomField.getText());
            stmt.setDate(4, Date.valueOf(dateNaisField.getValue()));
            stmt.setString(5, adresseField.getText());
            stmt.setString(6, phoneField.getText());
            stmt.setString(7, etatField.getValue());
            stmt.setString(8, professionField.getText());
            stmt.setDate(9, Date.valueOf(dateEnregistrementField.getValue()));
            stmt.setInt(10, currentPatient.getId());

            stmt.executeUpdate();

            showSuccess("Patient modifié avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================================
    // ALERT ET FERMETURE FORMULAIRE
    // ==================================
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();

        // Fermer le formulaire
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
