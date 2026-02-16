package com.hnk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RendezVousService {

    // ------------------------- Liste complète des rendez-vous -------------------------
    public List<RendezVousList> getRendezVous() {
        List<RendezVousList> list = new ArrayList<>();

        String sql = """
            SELECT r.idRendezVous, r.idPatient, r.idMedecin, r.dateRendezVous, r.heureRendezVous,
                   r.motif, r.statut, r.commentaire, r.lu,
                   p.nomPatient, p.photo
            FROM rendez_vous r
            JOIN patients p ON r.idPatient = p.idPatient
            ORDER BY r.dateRendezVous DESC
        """;

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RendezVousList r = new RendezVousList();

                r.setIdRendezVous(rs.getInt("idRendezVous"));
                r.setId(rs.getInt("idPatient"));
                r.setMedecin(String.valueOf(rs.getInt("idMedecin")));
                r.setNomPatient(rs.getString("nomPatient"));
                r.setPhotoPath(rs.getString("photo"));

                Date dateSQL = rs.getDate("dateRendezVous");
                if (dateSQL != null) {
                    r.setDate(dateSQL.toLocalDate());
                }

                Time timeSQL = rs.getTime("heureRendezVous");
                if (timeSQL != null) {
                    r.setHeure(timeSQL.toLocalTime());
                }

                r.setMotif(rs.getString("motif"));
                r.setStatut(rs.getString("statut"));
                r.setCommentaire(rs.getString("commentaire"));
                r.setLu(rs.getBoolean("lu"));

                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ------------------------- Récupérer les rendez-vous d'aujourd'hui -------------------------
    public List<RendezVousList> getAujourdHui() {
        LocalDate today = LocalDate.now();
        return getRendezVous().stream()
                .filter(r -> r.getDate() != null && r.getDate().equals(today))
                .toList();
    }

    // ------------------------- Récupérer les rendez-vous urgents -------------------------
    public List<RendezVousList> getUrgents() {
        return getRendezVous().stream()
                .filter(r -> r.getStatut() != null && r.getStatut().equalsIgnoreCase("urgent"))
                .toList();
    }

    // ------------------------- Marquer un rendez-vous comme lu -------------------------
    public void marquerCommeLu(int idRendezVous) {
        String sql = "UPDATE rendez_vous SET lu = true WHERE idRendezVous = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idRendezVous);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------- Modifier le statut d'un rendez-vous -------------------------
    public void modifierRendezVous(int id, String nouveauStatut) {
        String sql = "UPDATE rendez_vous SET statut = ? WHERE idRendezVous = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Le statut du rendez-vous a été modifié avec succès !");
            } else {
                System.out.println("Aucun rendez-vous trouvé avec l'id " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------- Supprimer un rendez-vous -------------------------
    public void supprimerRendezVous(int id) {
        String sql = "DELETE FROM rendez_vous WHERE idRendezVous = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getAllStatuts() {

        ObservableList<String> statuts = FXCollections.observableArrayList();

        String sql = "SELECT \n" +
                "    TRIM(BOTH \"'\" FROM SUBSTRING_INDEX(SUBSTRING_INDEX(COLUMN_TYPE, \"ENUM(\", -1), \")\", 1)) AS enum_values\n" +
                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE TABLE_SCHEMA = 'hopital'\n" +
                "  AND TABLE_NAME = 'rendez_vous'\n" +
                "  AND COLUMN_NAME = 'statut';\n";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String enumValues = rs.getString("Type");
                // enum('EN_ATTENTE','CONFIRMER','ANNULER','TERMINER')

                enumValues = enumValues.replace("enum(", "")
                        .replace(")", "")
                        .replace("'", "");

                String[] values = enumValues.split(",");

                for (String v : values) {
                    statuts.add(v.trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return statuts;
    }

    public String getStatutById(int idRdv) {
        String sql = "SELECT statut FROM rendez_vous WHERE idRendezVous = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Définir l'ID du rendez-vous
            ps.setInt(1, idRdv);

            // Exécuter la requête
            ResultSet rs = ps.executeQuery();

            // Si on a trouvé le rendez-vous, retourner son statut
            if (rs.next()) {
                return rs.getString("statut");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retourner null si aucun rendez-vous trouvé
        return null;
    }


    // ------------------------- Classe interne pour la connexion à la base -------------------------
    public static class Database {

        private static final String URL = "jdbc:mysql://localhost:3306/hopital";
        private static final String USER = "root";
        private static final String PASSWORD = "";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
}
