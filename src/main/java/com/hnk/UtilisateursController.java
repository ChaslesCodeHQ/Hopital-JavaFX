package com.hnk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class UtilisateursController {

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> colId;

    @FXML
    private TableColumn<User, String> colMatricule;

    @FXML
    private TableColumn<User, String> colNom;

    @FXML
    private TableColumn<User, String> colRole;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configurer les colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("roles"));

        // Charger les utilisateurs depuis la base de données
        loadUsers();
    }

    private void loadUsers() {
        String url = "jdbc:mysql://localhost:3306/hopital_db";
        String user = "root";
        String password = "";

        String query = "SELECT id, matricule, nom, roles FROM user";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                userList.add(new User(
                        rs.getInt("id"),
                        rs.getString("matricule"),
                        rs.getString("nom"),
                        rs.getString("roles")
                ));
            }

            tableUsers.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


