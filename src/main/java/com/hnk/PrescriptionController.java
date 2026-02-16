package com.hnk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.print.PrinterJob;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

public class PrescriptionController {

    @FXML
    private ComboBox<Consultation> comboConsultations;

    @FXML
    private TextField txtNomMedicament;

    @FXML
    private TextField txtPosologie;

    @FXML
    private TextField txtDuree;

    @FXML
    private TableView<Medicament> tableMedicaments;
    @FXML
    private TableColumn<Medicament, String> colNom;
    @FXML
    private TableColumn<Medicament, String> colPosologie;
    @FXML
    private TableColumn<Medicament, String> colDuree;

    private ObservableList<Medicament> medicaments = FXCollections.observableArrayList();

    // Consultation sélectionnée
    private Consultation consultationSelectionnee;

    @FXML
    public void initialize() {
        // TableView setup
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPosologie.setCellValueFactory(new PropertyValueFactory<>("posologie"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        tableMedicaments.setItems(medicaments);

        // Charger toutes les consultations disponibles dans le ComboBox
        try {
            comboConsultations.setItems(FXCollections.observableArrayList(ConsultationDAO.getAllConsultations()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        comboConsultations.setPromptText("Sélectionner une consultation");

        // Événement de sélection
        comboConsultations.setOnAction(event -> {
            consultationSelectionnee = comboConsultations.getSelectionModel().getSelectedItem();
            if (consultationSelectionnee != null) {
                System.out.println("Consultation sélectionnée : " + consultationSelectionnee.getId());
            }
        });
    }

    @FXML
    private void handleAjouterMedicament() {
        String nom = txtNomMedicament.getText();
        String posologie = txtPosologie.getText();
        String duree = txtDuree.getText();

        if (!nom.isEmpty() && !posologie.isEmpty() && !duree.isEmpty()) {
            medicaments.add(new Medicament(nom, posologie, duree));
            txtNomMedicament.clear();
            txtPosologie.clear();
            txtDuree.clear();
        }
    }

    @FXML
    private void handleGenererPDF() {
        if (consultationSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune consultation sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une consultation avant de générer l'ordonnance.");
            alert.showAndWait();
            return;
        }

        String nomPatient = consultationSelectionnee.getNomPatient();
        String nomMedecin = consultationSelectionnee.getNomMedecin();
        String dateConsultation = consultationSelectionnee.getDate().toString();

        try {
            // Chemin complet pour éviter les problèmes de répertoire
            String chemin = System.getProperty("user.home") + "\\Documents\\Ordonnance_" + nomPatient + ".pdf";

            Document document = new Document(PageSize.A4, 50, 50, 70, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(chemin));
            document.open();

            // En-tête
            Paragraph header = new Paragraph();
            header.setAlignment(Element.ALIGN_CENTER);
            header.add(new Phrase("ORDONNANCE MÉDICALE\n", new Font(Font.HELVETICA, 18, Font.BOLD)));
            header.add(new Phrase("Patient : " + nomPatient + "\n", new Font(Font.HELVETICA, 12)));
            header.add(new Phrase("Médecin : " + nomMedecin + "\n", new Font(Font.HELVETICA, 12)));
            header.add(new Phrase("Date : " + dateConsultation + "\n\n", new Font(Font.HELVETICA, 12)));
            document.add(header);

            // Tableau des médicaments
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 3, 2});
            Font headFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            table.addCell(new PdfPCell(new Phrase("Médicament", headFont)));
            table.addCell(new PdfPCell(new Phrase("Posologie", headFont)));
            table.addCell(new PdfPCell(new Phrase("Durée (jours)", headFont)));

            for (Medicament m : medicaments) {
                table.addCell(m.getNom());
                table.addCell(m.getPosologie());
                table.addCell(m.getDuree());
            }
            document.add(table);

            // Texte en fond de page ("DrCoto" centré)
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.saveState();
             // bleu médical
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            canvas.beginText();
            canvas.setFontAndSize(bf, 80); // taille grande
            float x = PageSize.A4.getWidth() / 2;
            float y = PageSize.A4.getHeight() / 2;
            canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, "DrCoto", x, y, 0); // 0° rotation
            canvas.endText();
            canvas.restoreState();

            document.close();
            System.out.println("Ordonnance PDF généré pour " + nomPatient);

            // Ouvrir automatiquement le PDF
            File file = new File(chemin);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleImprimerOrdonnance() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(tableMedicaments.getScene().getWindow())) {
            boolean success = job.printPage(tableMedicaments);
            if (success) job.endJob();
        }
    }
}
