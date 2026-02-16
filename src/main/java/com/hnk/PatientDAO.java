package com.hnk;

import com.hnk.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientDAO {

    public static ObservableList<Patient> getAll() throws Exception {

        ObservableList<Patient> liste = FXCollections.observableArrayList();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM patients");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Patient(
                        rs.getInt("idPatient"),
                        rs.getString("nomPatient"),
                        rs.getString("prenomPatient")
                ));
            }
        }

        return liste;
    }

    /**
     * Récupère le nom complet du patient par son ID
     */
    public static String getNomById(int patientId) {
        String nomComplet = "Patient inconnu";

        String sql = "SELECT nomPatient, prenomPatient FROM patients WHERE idPatient = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);

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
}
