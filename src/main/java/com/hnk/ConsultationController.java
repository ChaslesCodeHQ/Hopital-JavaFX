package com.hnk;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class ConsultationController {

    @FXML private ComboBox<RendezVousItem> rdvCombo;
    @FXML private TextArea motifArea;
    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<String> statutCombo;

    private Collection<RendezVousItem> rdvList = FXCollections.observableArrayList();

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hopital",
                "root", ""
        );
    }

    // Classe interne pour stocker id et afficher dans ComboBox
    private static class RendezVousItem {
        int idRdv;
        String display;

        RendezVousItem(int id, String display) {
            this.idRdv = id;
            this.display = display;
        }

        @Override
        public String toString() {
            return display;
        }
    }

    // 🔹 Initialisation du formulaire
    @FXML
    private void initialize() {
        loadRendezVous();
        initDefaults();
    }

    // 🔹 Charger les rendez-vous disponibles
    private void loadRendezVous() {
        rdvCombo.getItems().clear();

        try (Connection conn = getConnection()) {
            String sql = """
                SELECT r.idRendezVous, p.nomPatient, p.prenomPatient, m.nomMedecin, r.dateRendezVous, r.heureRendezVous
                FROM rendez_vous r
                JOIN patients p ON r.idPatient = p.idPatient
                JOIN medecins m ON r.idMedecin = m.idMedecin
                WHERE r.statut='CONFIRME'
            """;

            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                int idRdv = rs.getInt("idRendezVous");
                String display = rs.getString("nomPatient") + " " +
                        rs.getString("prenomPatient") + " - Dr. " +
                        rs.getString("nomMedecin") + " - " +
                        rs.getDate("dateRendezVous") + " " +
                        rs.getTime("heureRendezVous");

                rdvList.add(new RendezVousItem(idRdv, display));
            }

            rdvCombo.setItems(FXCollections.observableArrayList(rdvList));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Initialisation des valeurs par défaut
    private void initDefaults() {
        typeCombo.getItems().addAll("Générale", "Spécialisée", "Urgence", "Suivi");
        statutCombo.getItems().addAll("CONFIRME", "URGENT", "TERMINE");
    }

    // 🔹 Enregistrer consultation
    @FXML
    private void saveConsultation() {

        if (rdvCombo.getValue() == null) {
            showAlert("Veuillez sélectionner un rendez-vous.");
            return;
        }

        if (motifArea.getText().isEmpty() || typeCombo.getValue() == null || statutCombo.getValue() == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Récupérer idRendezVous sélectionné
        RendezVousItem selected = rdvCombo.getSelectionModel().getSelectedItem();
        int idRdv = selected.idRdv;

        try (Connection conn = getConnection()) {

            // 🔹 Vérifier si consultation existe déjà
            PreparedStatement check = conn.prepareStatement(
                    "SELECT idConsultation FROM consultation WHERE idRendezVous=?"
            );
            check.setInt(1, idRdv);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                showAlert("Une consultation existe déjà pour ce rendez-vous !");
                return;
            }

            // 🔹 Récupérer idPatient et idMedecin depuis le RDV
            PreparedStatement psRdv = conn.prepareStatement(
                    "SELECT idPatient, idMedecin FROM rendez_vous WHERE idRendezVous=?"
            );
            psRdv.setInt(1, idRdv);
            ResultSet rsRdv = psRdv.executeQuery();

            int idPatient = 0, idMedecin = 0;
            if (rsRdv.next()) {
                idPatient = rsRdv.getInt("idPatient");
                idMedecin = rsRdv.getInt("idMedecin");
            }

            // 🔹 Insérer consultation
            String sqlInsert = """
                INSERT INTO consultation
                (idRendezVous, idPatient, idMedecin,
                 dateConsultation, heureConsultation,
                 motifConsultation, typeConsultation)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sqlInsert);

            ps.setInt(1, idRdv);
            ps.setInt(2, idPatient);
            ps.setInt(3, idMedecin);
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.setTime(5, Time.valueOf(LocalTime.now().withSecond(0)));
            ps.setString(6, motifArea.getText());
            ps.setString(7, typeCombo.getValue());

            ps.executeUpdate();

            // 🔹 Mettre le RDV au statut choisi
            PreparedStatement update = conn.prepareStatement(
                    "UPDATE rendez_vous SET statut=? WHERE idRendezVous=?"
            );
            update.setString(1, statutCombo.getValue());
            update.setInt(2, idRdv);
            update.executeUpdate();

            showAlert("Consultation enregistrée avec succès !");

            // Réinitialiser le formulaire
            motifArea.clear();
            rdvCombo.getSelectionModel().clearSelection();
            typeCombo.getSelectionModel().clearSelection();
            statutCombo.getSelectionModel().clearSelection();
            loadRendezVous(); // recharge les RDV disponibles

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
