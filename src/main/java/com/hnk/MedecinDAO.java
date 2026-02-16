package com.hnk;

import com.hnk.Medecin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MedecinDAO {

    public static ObservableList<Medecin> getAll() {
        ObservableList<Medecin> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM medecins";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Medecin(
                        rs.getInt("idMedecin"),
                        rs.getString("matricule"),
                        rs.getString("nomMedecin"),
                        rs.getString("prenomMedecin"),
                        rs.getString("specialite")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Récupère le nom complet du médecin par son ID
     */
    public static String getNomById(int medecinId) {
        String nomComplet = "Médecin inconnu";

        String sql = "SELECT nomMedecin, prenomMedecin FROM medecins WHERE idMedecin = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, medecinId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nomComplet = rs.getString("nomMedecin") + " " + rs.getString("prenomMedecin");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nomComplet;
    }
}
