package com.hnk;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;

import java.sql.*;
import java.time.LocalDate;
import java.util.function.Predicate;

public class TablePatient {

    @FXML private TableView<Patients> tableUsers;
    @FXML private TextField searchField;
    @FXML private Pagination pagination;
    @FXML private ComboBox<Integer> pageSizeCombo;
    @FXML private Button btnAddPatient;

    private ObservableList<Patients> masterData = FXCollections.observableArrayList();
    private FilteredList<Patients> filteredData;
    private int rowsPerPage = 10;

    @FXML
    public void initialize() {

        createColumns();
        loadPatients();

        filteredData = new FilteredList<>(masterData, p -> true);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(createPredicate(newVal));
            updatePagination();
        });

        pageSizeCombo.getItems().addAll(5, 10, 20, 50);
        pageSizeCombo.setValue(10);

        pageSizeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            rowsPerPage = newVal;
            updatePagination();
        });

        updatePagination();

        // Ajout Patient
        btnAddPatient.setOnAction(e -> openAddPatientForm());
    }

    // =========================
    // OUVRIR FORMULAIRE AJOUT
    // =========================
    @FXML
    private void openAddPatientForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPatient.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Patient");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadPatients();
            updatePagination();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // CREATION COLONNES
    // =========================
    private void createColumns() {
        tableUsers.getColumns().clear();

        TableColumn<Patients, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Patients, String> colMatricule = new TableColumn<>("Matricule");
        colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));

        TableColumn<Patients, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Patients, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Patients, LocalDate> colDateNais = new TableColumn<>("Date Naissance");
        colDateNais.setCellValueFactory(new PropertyValueFactory<>("dateNais"));

        TableColumn<Patients, String> colAdresse = new TableColumn<>("Adresse");
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        TableColumn<Patients, String> colPhone = new TableColumn<>("Téléphone");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn<Patients, String> colEtat = new TableColumn<>("Etat");
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colEtat.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String etat, boolean empty) {
                super.updateItem(etat, empty);
                if (empty || etat == null) {
                    setText(null); setStyle("");
                } else {
                    setText(etat);
                    if (etat.equalsIgnoreCase("Actif")) {
                        setStyle("-fx-background-color:#d4edda; -fx-text-fill:#155724; -fx-alignment:CENTER; -fx-background-radius:15;");
                    } else {
                        setStyle("-fx-background-color:#f8d7da; -fx-text-fill:#721c24; -fx-alignment:CENTER; -fx-background-radius:15;");
                    }
                }
            }
        });

        TableColumn<Patients, String> colProfession = new TableColumn<>("Profession");
        colProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));

        TableColumn<Patients, LocalDate> colDateEnreg = new TableColumn<>("Enregistrement");
        colDateEnreg.setCellValueFactory(new PropertyValueFactory<>("dateEnregistrement"));

        TableColumn<Patients, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit = new Button("✏");
            private final Button btnDelete = new Button("🗑");
            private final HBox pane = new HBox(5, btnEdit, btnDelete);

            {
                btnEdit.setStyle("-fx-background-color:#2196F3; -fx-text-fill:white;");
                btnDelete.setStyle("-fx-background-color:#f44336; -fx-text-fill:white;");

                btnEdit.setOnAction(e -> {
                    Patients patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPatient.fxml"));
                        Scene scene = new Scene(loader.load());

                        AddPatientController controller = loader.getController();
                        controller.setPatientData(patient);

                        Stage stage = new Stage();
                        stage.setTitle("Modifier Patient");
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();

                        loadPatients();
                        updatePagination();

                    } catch (Exception ex) { ex.printStackTrace(); }
                });

                btnDelete.setOnAction(e -> {
                    Patients patient = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer le patient ?", ButtonType.OK, ButtonType.CANCEL);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            deletePatient(patient);
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        tableUsers.getColumns().addAll(colId, colMatricule, colNom, colPrenom, colDateNais,
                colAdresse, colPhone, colEtat, colProfession, colDateEnreg, actionCol);
    }

    // RECHERCHE
    private Predicate<Patients> createPredicate(String searchText) {
        if (searchText == null || searchText.isEmpty()) return p -> true;
        String lower = searchText.toLowerCase();
        return p -> p.getNom().toLowerCase().contains(lower)
                || p.getPrenom().toLowerCase().contains(lower)
                || p.getTelephone().toLowerCase().contains(lower)
                || p.getMatricule().toLowerCase().contains(lower);
    }

    // PAGINATION
    private void updatePagination() {
        int totalPages = (int)Math.ceil((double) filteredData.size()/rowsPerPage);
        pagination.setPageCount(totalPages==0?1:totalPages);
        pagination.setPageFactory(this::createPage);
    }

    private TableView<Patients> createPage(int pageIndex) {
        int fromIndex = pageIndex*rowsPerPage;
        int toIndex = Math.min(fromIndex+rowsPerPage, filteredData.size());
        tableUsers.setItems(FXCollections.observableArrayList(filteredData.subList(fromIndex,toIndex)));
        return tableUsers;
    }

    // DELETE
    private void deletePatient(Patients patients) {
        String sql = "DELETE FROM patients WHERE idPatient=?";
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hopital_db", "root",""
        ); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patients.getId());
            stmt.executeUpdate();
            masterData.remove(patients);
            updatePagination();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // LOAD
    private void loadPatients() {
        masterData.clear();
        String query = "SELECT * FROM patients";
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hopital_db", "root",""
        ); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                masterData.add(new Patients(
                        rs.getInt("idPatient"),
                        rs.getString("matriculePatient"),
                        rs.getString("nomPatient"),
                        rs.getString("prenomPatient"),
                        rs.getDate("dateNais") != null ? rs.getDate("dateNais").toLocalDate() : null,
                        rs.getString("adresse"),
                        rs.getString("phone"),
                        rs.getString("etatSante"),
                        rs.getString("profession"),
                        rs.getDate("dateEnregistrement") != null ? rs.getDate("dateEnregistrement").toLocalDate() : null
                ));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
