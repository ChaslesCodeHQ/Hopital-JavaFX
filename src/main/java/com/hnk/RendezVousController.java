package com.hnk;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class RendezVousController {

    @FXML private VBox container; // VBox pour notifications
    @FXML private Button btnAddRdv; // bouton ajouter un nouveau RDV


    public class ListeRendezVousController {
        @FXML
        private ListView<RendezVousList> rdvListView;

        @FXML
        private void modifierSelection() {
            // Ici, tu récupères le rendez-vous sélectionné
        }
    }


    @FXML
    public void initialize() {
        loadRendezVousNotifications();

        btnAddRdv.setOnAction(e -> openAddRdvForm());
    }

    // Charger toutes les notifications
    private void loadRendezVousNotifications() {
        container.getChildren().clear();
        ObservableList<RendezVous> rdvList = RendezVousDAO.getAll();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        for (RendezVous rdv : rdvList) {
            HBox ligne = new HBox(10);

            Label lbl = new Label(
                    rdv.getPatient() + " - Dr. " + rdv.getMedecin() +
                            " - " + rdv.getDate() + " " + rdv.getHeure().format(dtf) +
                            " [" + rdv.getStatut() + "]"
            );

            Button modifierBtn = new Button("Modifier");
            modifierBtn.setStyle("-fx-background-color:#1976D2; -fx-text-fill:white;");
            modifierBtn.setUserData(rdv);
            modifierBtn.setOnAction(e -> {
                RendezVous selected = (RendezVous) ((Button) e.getSource()).getUserData();
                openModifierStatutForm(selected);
            });

            ligne.getChildren().addAll(lbl, modifierBtn);
            container.getChildren().add(ligne);
        }
    }

    private void openAddRdvForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("rendezVousForm.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter Rendez-vous");
            stage.showAndWait();
            loadRendezVousNotifications(); // rafraîchir
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openModifierStatutForm(RendezVous rdv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierStatutForm.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));


            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier le statut");
            stage.showAndWait();

            loadRendezVousNotifications(); // rafraîchir après modification
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
