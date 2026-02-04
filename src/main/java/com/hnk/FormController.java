package com.hnk;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class FormController {
    @FXML private VBox slidingPane;
    @FXML private Label sideTitle, sideDesc;
    @FXML private Button sideBtn;

    private boolean isLoginSide = true;

    @FXML
    private void handleSideAction() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.6), slidingPane);

        if (isLoginSide) {
            // Déplacement vers la DROITE pour montrer le formulaire d'inscription
            transition.setToX(350);
            sideTitle.setText("Bon Retour !");
            sideDesc.setText("Pour rester connecté avec nous, connectez-vous avec vos infos.");
            sideBtn.setText("S'INSCRIRE");
            isLoginSide = false;
        } else {
            // Déplacement vers la GAUCHE pour montrer le login
            transition.setToX(0);
            sideTitle.setText("Bonjour, Ami !");
            sideDesc.setText("Entrez vos données personnelles pour utiliser toutes les fonctionnalités du site.");
            sideBtn.setText("SE CONNECTER");
            isLoginSide = true;
        }
        transition.play();
    }
}