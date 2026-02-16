package com.hnk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class NavBarControle {

    @FXML
    private Label notifIcon;   // 🔔 l'icône sur laquelle on clique

    @FXML
    private Label notifBadge;  // le badge de notifications

    private final NbreRendezVous rendezVousService = new NbreRendezVous();

    @FXML
    public void initialize() {
        chargerNombreNotifications();

        // Ajouter le clic sur l'icône pour ouvrir ger.fxml
        notifIcon.setOnMouseClicked(event -> openGerPage());
    }

    private void chargerNombreNotifications() {
        int total = rendezVousService.getNombreRendezVous();
        notifBadge.setText(String.valueOf(total));
        notifBadge.setVisible(total > 0); // cache le badge si 0
    }

    private void openGerPage() {
        try {
            URL fxmlUrl = getClass().getResource("/com/hnk/listeRendezVous.fxml");
            if (fxmlUrl == null) {
                System.err.println("Fichier listeRendezVous.fxml introuvable !");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Pane root = loader.load();

            // Scene avec taille plus grande (ex : 900x700)
            Scene scene = new Scene(root, 600, 400);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestion");
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

