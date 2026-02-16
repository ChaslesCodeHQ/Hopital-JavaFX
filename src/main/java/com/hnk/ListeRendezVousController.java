package com.hnk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class ListeRendezVousController {

    @FXML
    private ListView<RendezVousList> rdvListView;

    @FXML
    private Button supprimerBtn;

    @FXML
    private Button modifierBtn;

    private final RendezVousService service = new RendezVousService();

    // ------------------------- Initialisation -------------------------
    @FXML
    public void initialize() {
        loadAll();
        configureListView();
        configureButtons();
    }

    // ------------------------- Configurer le ListView -------------------------
    private void configureListView() {
        rdvListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(RendezVousList rdv, boolean empty) {
                super.updateItem(rdv, empty);
                if (empty || rdv == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                // Photo
                ImageView photo = new ImageView();
                photo.setFitWidth(40);
                photo.setFitHeight(40);
                photo.setPreserveRatio(true);
                try {
                    if (rdv.getPhotoPath() != null && !rdv.getPhotoPath().isEmpty()) {
                        photo.setImage(new Image("file:" + rdv.getPhotoPath()));
                    } else {
                        throw new Exception("Photo vide");
                    }
                } catch (Exception e) {
                    URL defaultImg = getClass().getResource("/images/default.png");
                    if (defaultImg != null) {
                        photo.setImage(new Image(defaultImg.toExternalForm()));
                    }
                }
                photo.setClip(new Circle(20, 20, 20));

                // Nom du patient
                Label nom = new Label(rdv.getNomPatient() != null ? rdv.getNomPatient() : "Inconnu");
                nom.setStyle(!rdv.isLu() ? "-fx-font-weight: bold" : "");

                // Date et heure
                String dateText = (rdv.getDate() != null ? rdv.getDate().toString() : "Date inconnue") +
                        " " + (rdv.getHeure() != null ? rdv.getHeure().toString() : "Heure inconnue");
                Label date = new Label(dateText);

                // Statut
                Circle statutIcon = new Circle(5);
                String statut = rdv.getStatut() != null ? rdv.getStatut() : "normal";
                statutIcon.setFill(
                        switch (statut.toLowerCase()) {
                            case "ANNULE" -> Color.RED;
                            case "TERMINER" -> Color.GREEN;
                            default -> Color.ORANGE;
                        }
                );

                // Layout
                VBox info = new VBox(nom, date);
                info.setSpacing(3);

                HBox row = new HBox(statutIcon, photo, info);
                row.setSpacing(10);
                row.getStyleClass().add("rdv-row");

                if (!rdv.isLu()) {
                    row.setStyle("-fx-background-color: #e3f2fd;");
                }

                setGraphic(row);

                // Ouvrir modal au clic sur la ligne
                setOnMouseClicked(e -> openDetailModal(rdv));
            }
        });
    }

    // ------------------------- Configurer les boutons -------------------------
    private void configureButtons() {
        supprimerBtn.setOnAction(e -> supprimerSelection());
        modifierBtn.setOnAction(e -> modifierSelection());
    }

    // ------------------------- Modale détails rendez-vous -------------------------
    private void openDetailModal(RendezVousList rdv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hnk/detailRendezVous.fxml"));
            Pane root = loader.load();

            DetailRendezVousController controller = loader.getController();
            controller.setData(rdv);

            // Marquer comme lu
            service.marquerCommeLu(rdv.getIdRendezVous());
            rdv.setLu(true);
            rdvListView.refresh();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Détails du rendez-vous");
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------------- Confirmation -------------------------
    private boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        return alert.showAndWait().filter(bt -> bt == okButton).isPresent();
    }

    // ------------------------- Supprimer le rendez-vous sélectionné -------------------------
    @FXML
    private void supprimerSelection() {
        RendezVousList rdv = rdvListView.getSelectionModel().getSelectedItem();
        if (rdv == null) {
            new Alert(Alert.AlertType.WARNING, "Aucun rendez-vous sélectionné.", ButtonType.OK).showAndWait();
            return;
        }

        if (showConfirmation("Voulez-vous vraiment supprimer ce rendez-vous ?")) {
            service.supprimerRendezVous(rdv.getIdRendezVous());
            rdvListView.getItems().remove(rdv);
        }
    }

    // ------------------------- Modifier le statut du rendez-vous sélectionné -------------------------
    @FXML
    private void modifierSelection() {
        RendezVousList rdv = rdvListView.getSelectionModel().getSelectedItem();
        if (rdv == null) {
            new Alert(Alert.AlertType.WARNING, "Aucun rendez-vous sélectionné.", ButtonType.OK).showAndWait();
            return;
        }

        // Créer la modale
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Modifier le statut");

        ComboBox<String> comboStatut = new ComboBox<>();
        comboStatut.getItems().addAll("EN_ATTENTE", "CONFIRME", "ANNULE", "TERMINER");
        comboStatut.setValue(rdv.getStatut());

        Button btnValider = new Button("Valider");
        btnValider.setOnAction(e -> {
            String nouveauStatut = comboStatut.getValue();
            if (nouveauStatut.equals(rdv.getStatut())) {
                modal.close();
                return;
            }

            if (showConfirmation("Voulez-vous vraiment modifier le statut de ce rendez-vous ?")) {
                service.modifierRendezVous(rdv.getIdRendezVous(), nouveauStatut);
                rdv.setStatut(nouveauStatut);
                rdvListView.refresh();
                modal.close();
            }
        });

        VBox layout = new VBox(10, new Label("Choisir le nouveau statut :"), comboStatut, btnValider);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        modal.setScene(new Scene(layout, 300, 150));
        modal.showAndWait();
    }

    // ------------------------- Chargement des rendez-vous -------------------------
    @FXML
    private void loadAll() {
        rdvListView.getItems().setAll(service.getRendezVous());
    }

    @FXML
    private void loadToday() {
        rdvListView.getItems().setAll(service.getAujourdHui());
    }

    @FXML
    private void loadUrgent() {
        rdvListView.getItems().setAll(service.getUrgents());
    }
}
