package com.hnk;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AsideBarController {

    @FXML
    private VBox rootVBox;

    @FXML
    private Button openHome;

    @FXML
    private Button btnPatients;

    @FXML
    private Button btnRendezVous;

    @FXML
    private Button btnMedecins;

    private DashboardController dashboard;

    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    private void openHome() {
        dashboard.loadPage("Home.fxml");
    }

    @FXML
    private void openPatients() {
        dashboard.loadPage("patient.fxml");
    }

    @FXML
    private void openRendezVous() {
        dashboard.loadPage("gestionRendezVous.fxml");
    }

    @FXML
    private void openConsultations() {
        dashboard.loadPage("consultationForm.fxml");
    }

    @FXML
    private void openMedecins() {
        dashboard.loadPage("elementsConsultations.fxml");
    }

    @FXML
    private void openUtilisateurs() {
        dashboard.loadPage("Utilisateurs.fxml");
    }
}
