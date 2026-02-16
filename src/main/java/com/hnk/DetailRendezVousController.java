package com.hnk;

import com.hnk.RendezVousList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetailRendezVousController {

    @FXML private Label lblNom;
    @FXML private Label lblMedecin;
    @FXML private Label lblDate;
    @FXML private Label lblHeure;
    @FXML private Label lblMotif;
    @FXML private Label lblStatut;

    public void setData(RendezVousList rdv) {
        lblNom.setText(rdv.getNomPatient());
        lblMedecin.setText("Médecin : " + rdv.getIdMedecin());
        lblDate.setText("Date : " + rdv.getDate());
        lblHeure.setText("Heure : " + rdv.getHeure());
        lblMotif.setText("Motif : " + rdv.getMotif());
        lblStatut.setText("Statut : " + rdv.getStatut());
    }

    @FXML
    private void close() {
        ((Stage) lblNom.getScene().getWindow()).close();
    }
}
