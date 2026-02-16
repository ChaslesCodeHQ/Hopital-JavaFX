package com.hnk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionRendezVousController {

    @FXML
    private Button btnAddRdv;

    @FXML
    private Button btnAddRdvs;


    @FXML
    public void initialize() {
        // Action du bouton pour ouvrir le formulaire
        btnAddRdv.setOnAction(event -> openRendezVousForm());
        btnAddRdvs.setOnAction(event -> openRendezVousList());
    }

    private void openRendezVousList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listeRendezVous.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un rendez-vous");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Empêche d'interagir avec la fenêtre principale
            stage.showAndWait(); // Attend que l'utilisateur ferme le formulaire

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openRendezVousForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RendezVousForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un rendez-vous");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Empêche d'interagir avec la fenêtre principale
            stage.showAndWait(); // Attend que l'utilisateur ferme le formulaire

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
