package com.hnk;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

public class ExamensController {

    @FXML private ComboBox<Patient> comboPatient;
    @FXML private ComboBox<String> comboType;
    @FXML private DatePicker datePicker;
    @FXML private TextArea txtResultat;

    @FXML private TableView<Examens> tableExamens;
    @FXML private TableColumn<Examens, Integer> colId;
    @FXML private TableColumn<Examens, String> colType;
    @FXML private TableColumn<Examens, LocalDate> colDate;
    @FXML private TableColumn<Examens, String> colStatut;

    private String selectedFilePath;

    // ================= INITIALIZATION =================

    @FXML
    public void initialize() {

        // Charger patients dans ComboBox
        try {
            comboPatient.setItems(PatientDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur chargement patients");
        }

        // Types examens
        comboType.getItems().addAll(
                "Analyse sanguine",
                "Scanner",
                "IRM",
                "Radiographie"
        );

        // Configuration TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeExamen"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("datePrescription"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        loadTable();
    }

    // ================= PRESCRIRE EXAMEN =================

    @FXML
    private void handlePrescrire() {

        try {

            Patient patient = comboPatient.getValue();

            if (patient == null) {
                showError("Veuillez sélectionner un patient !");
                return;
            }

            if (comboType.getValue() == null) {
                showError("Veuillez sélectionner un type d'examen !");
                return;
            }

            if (datePicker.getValue() == null) {
                showError("Veuillez choisir une date !");
                return;
            }

            int idPatient = patient.getIdPatient(); // ✅ On prend uniquement l'ID

            Examens examen = new Examens(
                    idPatient,
                    comboType.getValue(),
                    datePicker.getValue()
            );

            ExamensDAO.save(examen);

            showInfo("Examen prescrit avec succès !");
            clearForm();
            loadTable();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de l'enregistrement !");
        }
    }

    // ================= JOINDRE FICHIER =================

    @FXML
    private void handleJoindreFichier() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            selectedFilePath = file.getAbsolutePath();
            showInfo("Fichier sélectionné !");
        }
    }

    // ================= AJOUTER RESULTAT =================

    @FXML
    private void handleAjouterResultat() {

        Examens selected = tableExamens.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Veuillez sélectionner un examen !");
            return;
        }

        if (txtResultat.getText().isEmpty()) {
            showError("Veuillez saisir le résultat !");
            return;
        }

        try {

            ExamensDAO.ajouterResultat(
                    selected.getId(),
                    txtResultat.getText(),
                    selectedFilePath
            );

            showInfo("Résultat ajouté avec succès !");
            txtResultat.clear();
            selectedFilePath = null;
            loadTable();

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur lors de l'ajout du résultat !");
        }
    }

    // ================= LOAD TABLE =================

    private void loadTable() {

        try {
            ObservableList<Examens> list = ExamensDAO.getAll();
            tableExamens.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur chargement examens !");
        }
    }

    // ================= CLEAR FORM =================

    private void clearForm() {
        comboPatient.setValue(null);
        comboType.setValue(null);
        datePicker.setValue(null);
    }

    // ================= ALERTS =================

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
