package com.hnk;

import com.hnk.DossierMedical;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class DossierMedicalDAO {

    public static void save(DossierMedical d) throws Exception {

        String sql = "INSERT INTO dossier_medical " +
                "(idPatient, dateConsulter, symptomes, antecedentsMedicaux, " +
                "antecedentsFamiliaux, allergies, traitementsEnCours, " +
                "temperature, tensionArterielle, poids, taille, frequenceCardiaque) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getPatientId());
            ps.setDate(2, Date.valueOf(d.getDateConsultation()));
            ps.setString(3, d.getSymptomes());
            ps.setString(4, d.getAntecedentsMedicaux());
            ps.setString(5, d.getAntecedentsFamiliaux());
            ps.setString(6, d.getAllergies());
            ps.setString(7, d.getTraitementsEnCours());
            ps.setDouble(8, d.getTemperature());
            ps.setString(9, d.getTensionArterielle());
            ps.setDouble(10, d.getPoids());
            ps.setDouble(11, d.getTaille());
            ps.setInt(12, d.getFrequenceCardiaque());

            ps.executeUpdate();
        }
    }
}
