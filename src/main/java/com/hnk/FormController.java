package com.hnk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormController {

    // Champs du formulaire
    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private Label errorLabel;

    // Bouton login (OBLIGATOIRE car tu l’utilises pour récupérer le Stage)
    @FXML
    private Button btnLogin;

    @FXML
    private void handleLogin() {

        // Récupération des valeurs saisies
        String user = userField.getText();
        String pass = passField.getText();

        // Vérification simple des champs
        if (user.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        // Paramètres de connexion MySQL
        String url = "jdbc:mysql://localhost:3306/hopital";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {

            // Requête SQL sécurisée
            String query = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, user);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();

            // ✅ Si utilisateur trouvé
            if (rs.next()) {
// Charger le dashboard
                Parent root = FXMLLoader.load(
                        getClass().getResource("DashboardView.fxml")
                );

// Récupérer la fenêtre actuelle (celle du login)
                Stage stage = (Stage) btnLogin.getScene().getWindow();

// Récupérer la largeur et la hauteur actuelles de la fenêtre
                double currentWidth = stage.getWidth();
                double currentHeight = stage.getHeight();

// Créer la nouvelle scène avec la même taille que la fenêtre actuelle
                Scene scene = new Scene(root, currentWidth, currentHeight);

// Appliquer la scène au Stage existant
                stage.setScene(scene);
                stage.setTitle("Dashboard Médical");

// Centrer la fenêtre sur l’écran (optionnel)
                stage.centerOnScreen();

// Afficher le Stage
                stage.show();

            } else {
                // Login incorrect
                errorLabel.setText("Identifiants incorrects ❌ .");
            }

        } catch (SQLException e) {
            errorLabel.setText("Erreur de connexion à la base de données.");
            e.printStackTrace();
        } catch (Exception e) {
            errorLabel.setText("Erreur lors du chargement du dashboard.");
            e.printStackTrace();
        }
    }
}
