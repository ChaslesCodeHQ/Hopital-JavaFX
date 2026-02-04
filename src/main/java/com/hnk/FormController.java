package com.hnk;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Duration;

public class FormController {
    @FXML private TextField nameField;
    @FXML private Button submitBtn;

    // 1. Animation de secouement (en cas d'erreur)
    private void shakeNode(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

    // 2. Animation d'apparition (Fade In)
    @FXML
    public void initialize() {
        // Cette méthode s'exécute au chargement de la fenêtre
        FadeTransition ft = new FadeTransition(Duration.millis(1500), nameField.getParent());
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    @FXML
    protected void handleSubmit() {
        if (nameField.getText().isEmpty()) {
            shakeNode(nameField); // On secoue le champ si c'est vide !
        } else {
            // Animation de pulsation du bouton au clic
            ScaleTransition st = new ScaleTransition(Duration.millis(100), submitBtn);
            st.setToX(1.1);
            st.setToY(1.1);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.play();
        }
    }
}