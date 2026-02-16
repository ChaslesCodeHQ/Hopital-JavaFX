package com.hnk;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.*;

public class NbreMedecins {

    @FXML
    private Label lblNombre;

    @FXML
    public void initialize() {
        int total = getNombreMedecins();
        lblNombre.setText(String.valueOf(total));
    }


    public class Database {

        private static final String URL = "jdbc:mysql://localhost:3306/hopital";
        private static final String USER = "root";
        private static final String PASSWORD = "";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    /**
     * Méthode pour compter le nombre d'utilisateurs dans la table 'utilisateurs'
     */
    private int getNombreMedecins() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS medecins FROM medecins";

        try (Connection conn = Database.getConnection(); // <-- à adapter selon ta classe DB
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("medecins");
                System.out.println(count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}

