package com.hnk;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private VBox asideBar; // zone où on inclut l'AsideBar

    private AsideBarController asideController;

    @FXML
    public void initialize() {

        try {
            // Charger l'AsideBar et récupérer son controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hnk/AsideBar.fxml"));
            Node asideNode = loader.load();
            asideController = loader.getController();
            asideController.setDashboard(this); // passer la référence du dashboard

            // Remplacer le VBox de l'AsideBar dans le BorderPane
            asideBar.getChildren().setAll(asideNode);


        } catch (IOException e) {
            e.printStackTrace();
        }

        // Page par défaut
        loadPage("Home.fxml");
    }

    // Méthode pour changer le contenu central
    public void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hnk/" + fxmlFile));
            Node view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Erreur de chargement : " + fxmlFile);
            e.printStackTrace();
        }
    }
}
