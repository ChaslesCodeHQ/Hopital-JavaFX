package com.hnk;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DiagnosticController {

    @FXML private ComboBox<Consultation> comboConsultation;
    @FXML private TextField txtDiagnosticPrincipal;
    @FXML private TextArea txtDiagnosticsSecondaires;
    @FXML private ComboBox<String> comboGravite;
    @FXML private TextArea txtCommentaires;

    @FXML
    public void initialize() {

        comboGravite.getItems().addAll(
                "Faible",
                "Modérée",
                "Grave",
                "Critique"
        );

        try {
            ObservableList<Consultation> consultations =
                    ConsultationDAO.getAllConsultations();

            comboConsultation.setItems(consultations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave() {

        try {

            Consultation selected = comboConsultation.getValue();

            if (selected == null) {
                showAlert("Veuillez sélectionner une consultation !");
                return;
            }

            Diagnostic d = new Diagnostic(
                    selected.getId(),
                    txtDiagnosticPrincipal.getText(),
                    txtDiagnosticsSecondaires.getText(),
                    comboGravite.getValue(),
                    txtCommentaires.getText()
            );

            DiagnosticDAO.save(d);

            showAlert("Diagnostic enregistré avec succès !");

            ((Stage) comboConsultation.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
