package com.hnk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVousDAO {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hopital_db",
                "root",
                ""
        );
    }

    // Récupérer tous les rendez-vous
    public static ObservableList<RendezVous> getAll() {
        ObservableList<RendezVous> list = FXCollections.observableArrayList();
        String sql = """
            SELECT r.idRendezVous, r.idPatient, r.idMedecin, r.dateRendezVous, r.heureRendezVous,
                   r.motif, r.statut, r.commentaire,
                   p.nomPatient, p.prenomPatient, m.nomMedecin
            FROM rendez_vous r
            JOIN patients p ON r.idPatient = p.idPatient
            JOIN medecins m ON r.idMedecin = m.idMedecin
        """;

        try (Connection conn = getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {

            while (rs.next()) {
                RendezVous rdv = new RendezVous(
                        rs.getInt("idRendezVous"),
                        rs.getInt("idPatient"),
                        rs.getInt("idMedecin"),
                        rs.getDate("dateRendezVous").toLocalDate(),
                        rs.getTime("heureRendezVous").toLocalTime(),
                        rs.getString("motif"),
                        rs.getString("statut"),
                        rs.getString("commentaire")
                );
                rdv.setPatient(rs.getString("nomPatient") + " " + rs.getString("prenomPatient"));
                rdv.setMedecin(rs.getString("nomMedecin"));
                list.add(rdv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Insérer un rendez-vous
    public static boolean insert(RendezVous rdv) {
        String sql = """
            INSERT INTO rendez_vous
            (idPatient, idMedecin, dateRendezVous, heureRendezVous, motif, statut, commentaire)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rdv.getIdPatient());
            ps.setInt(2, rdv.getIdMedecin());
            ps.setDate(3, Date.valueOf(rdv.getDate()));
            ps.setTime(4, Time.valueOf(rdv.getHeure()));
            ps.setString(5, rdv.getMotif());
            ps.setString(6, rdv.getStatut());
            ps.setString(7, rdv.getCommentaire());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mettre à jour le statut
    public static boolean updateStatut(int idRdv, String statut) {
        String sql = "UPDATE rendez_vous SET statut=? WHERE idRendezVous=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ps.setInt(2, idRdv);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
