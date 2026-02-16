package com.hnk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ElementsController {

    @FXML
    private Button Dossier;

    @FXML
    private Button Examens;

    @FXML
    private Button Ordonance;

    @FXML
    public void initialize() {
        // Action du bouton pour ouvrir le formulaire
        Dossier.setOnAction(event -> openDossierForm());
        Examens.setOnAction(event -> openExamenForm());
        Ordonance.setOnAction(event -> openOrdonanceForm());

    }

    private void openOrdonanceForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("prescriptionForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Examen");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Taille fixe
            stage.setWidth(600);
            stage.setHeight(500);

            // Empêche le redimensionnement
            stage.setResizable(false);

            // Modale
            stage.initModality(Modality.APPLICATION_MODAL);

            // Centre la fenêtre à l'écran
            stage.centerOnScreen();

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openDossierForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DossierMedicalForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Examen");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Taille fixe
            stage.setWidth(600);
            stage.setHeight(500);

            // Empêche le redimensionnement
            stage.setResizable(false);

            // Modale
            stage.initModality(Modality.APPLICATION_MODAL);

            // Centre la fenêtre à l'écran
            stage.centerOnScreen();

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openExamenForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("examens.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Examen");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Taille fixe
            stage.setWidth(600);
            stage.setHeight(500);

            // Empêche le redimensionnement
            stage.setResizable(true);

            // Modale
            stage.initModality(Modality.APPLICATION_MODAL);

            // Centre la fenêtre à l'écran
            stage.centerOnScreen();

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
