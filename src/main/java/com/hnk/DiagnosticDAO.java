package com.hnk;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DiagnosticDAO {

    public static void save(Diagnostic d) throws Exception {

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO diagnostic " +
                "(id_consultation, diagnostic_principal, diagnostics_secondaires, niveau_gravite, commentaires_medecin) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, d.getIdConsultation());
        ps.setString(2, d.getDiagnosticPrincipal());
        ps.setString(3, d.getDiagnosticsSecondaires());
        ps.setString(4, d.getNiveauGravite());
        ps.setString(5, d.getCommentairesMedecin());

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
