package com.hnk;

import com.hnk.DossierMedicalDAO;
import com.hnk.PatientDAO;
import com.hnk.DossierMedical;
import com.hnk.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DossierMedicalController {

    @FXML private ComboBox<Patient> comboPatient;
    @FXML private DatePicker dateConsultation;
    @FXML private TextArea symptomes;
    @FXML private TextArea antecedentsMedicaux;
    @FXML private TextArea antecedentsFamiliaux;
    @FXML private TextArea allergies;
    @FXML private TextArea traitementsEnCours;
    @FXML private TextField temperature;
    @FXML private TextField tensionArterielle;
    @FXML private TextField poids;
    @FXML private TextField taille;
    @FXML private TextField frequenceCardiaque;

    @FXML
    public void initialize() {
        try {
            comboPatient.setItems(PatientDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave() {

        try {
            Patient patient = comboPatient.getValue();

            if (patient == null) {
                showAlert("Veuillez sélectionner un patient !");
                return;
            }

            DossierMedical dossier = new DossierMedical(
                    patient.getIdPatient(),
                    dateConsultation.getValue(),
                    symptomes.getText(),
                    antecedentsMedicaux.getText(),
                    antecedentsFamiliaux.getText(),
                    allergies.getText(),
                    traitementsEnCours.getText(),
                    Double.parseDouble(temperature.getText()),
                    tensionArterielle.getText(),
                    Double.parseDouble(poids.getText()),
                    Double.parseDouble(taille.getText()),
                    Integer.parseInt(frequenceCardiaque.getText())
            );

            DossierMedicalDAO.save(dossier);

            showAlert("Dossier médical enregistré avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'enregistrement !");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
