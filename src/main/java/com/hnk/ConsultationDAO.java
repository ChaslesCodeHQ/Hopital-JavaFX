package com.hnk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultationDAO {

    /**
     * Récupère le nom complet du patient à partir de l'ID de consultation
     */
    public static String getNomPatient(int idConsultation) {
        String nomComplet = "Patient inconnu";

        String sql = "SELECT p.nomPatient, p.prenomPatient " +
                "FROM patients p " +
                "JOIN consultation c ON p.idPatient = c.patientId " +
                "WHERE c.idConsultation = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idConsultation);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nomComplet = rs.getString("nomPatient") + " " + rs.getString("prenomPatient");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nomComplet;
    }

    /**
     * Récupère toutes les consultations comme objets Consultation
     * Prêt à remplir un ComboBox
     */
    public static ObservableList<Consultation> getAllConsultations() {
        ObservableList<Consultation> liste = FXCollections.observableArrayList();

        String sql = "SELECT * FROM consultation";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consultation c = new Consultation(
                        rs.getInt("idRendezVous"),
                        rs.getInt("idPatient"),
                        rs.getInt("idMedecin"),
                        rs.getDate("dateConsultation").toLocalDate(),
                        rs.getTime("heureConsultation").toLocalTime(),
                        rs.getString("motifConsultation"),
                        rs.getString("typeConsultation")
                );
                liste.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }
}
