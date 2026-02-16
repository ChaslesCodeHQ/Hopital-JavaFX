package com.hnk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ExamensDAO {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hopital_db",
                "root",
                ""
        );
    }

    public static void save(Examens e) throws SQLException {

        String sql = "INSERT INTO examens (idPatient, typeExamen, datePrescription, statut) VALUES (?, ?, ?, ?)";

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, e.getIdPatient());
        ps.setString(2, e.getTypeExamen());
        ps.setDate(3, Date.valueOf(e.getDatePrescription()));
        ps.setString(4, e.getStatut());

        ps.executeUpdate();
        con.close();
    }

    public static void ajouterResultat(int id, String resultat, String fichierPath) throws SQLException {

        String sql = "UPDATE examens SET resultat=?, fichierPath=?, statut='Terminé' WHERE id=?";

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, resultat);
        ps.setString(2, fichierPath);
        ps.setInt(3, id);

        ps.executeUpdate();
        con.close();
    }

    public static ObservableList<Examens> getAll() throws SQLException {

        ObservableList<Examens> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM examens";

        Connection con = getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            list.add(new Examens(
                    rs.getInt("id"),
                    rs.getInt("idPatient"),
                    rs.getString("typeExamen"),
                    rs.getDate("datePrescription").toLocalDate(),
                    rs.getString("resultat"),
                    rs.getString("fichierPath"),
                    rs.getString("statut")
            ));
        }

        con.close();
        return list;
    }
}
