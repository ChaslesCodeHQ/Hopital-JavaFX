package com.hnk;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ModifierStatutController {

    @FXML
    private ComboBox<String> statutCombo;

    @FXML
    private Button btnValider;

    private int idRdv;
    private RendezVousService service;

    /**
     * Initialisation du contrôleur avec le service et l'ID du rendez-vous
     */
    public void setRdv(int idRdv, RendezVousService service) {
        this.idRdv = idRdv;
        this.service = service;

        // Remplir la ComboBox avec les statuts exacts
        statutCombo.getItems().setAll(service.getAllStatuts());

        // Sélectionner le statut actuel du rendez-vous
        String currentStatut = service.getStatutById(idRdv);
        if (currentStatut != null) {
            statutCombo.getSelectionModel().select(currentStatut);
        }

    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur Valider
     */

}
