package com.hnk;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GrapheController {

    @FXML
    private AreaChart<String, Number> rdvChart;

    @FXML
    private NumberAxis yAxis;

    private static final String URL = "jdbc:mysql://localhost:3306/hopital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @FXML
    public void initialize() {

        // Configuration axe Y (1 unité par graduation)
        yAxis.setAutoRanging(false);
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);

        loadData();
    }

    private void loadData() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Rendez-vous");

        int maxValue = 0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("""
                     SELECT MONTH(dateRendezVous) AS mois,
                            COUNT(*) AS total
                     FROM rendez_vous
                     GROUP BY MONTH(dateRendezVous)
                     ORDER BY mois
                     """);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int mois = rs.getInt("mois");
                int total = rs.getInt("total");

                if (total > maxValue) {
                    maxValue = total;
                }

                XYChart.Data<String, Number> data =
                        new XYChart.Data<>(getMonthName(mois), total);

                series.getData().add(data);
            }

            rdvChart.getData().add(series);

            // Définir limites dynamiques
            yAxis.setLowerBound(0);
            yAxis.setUpperBound(maxValue + 2);

            // Ajouter Tooltips (après que les nodes soient créés)
            rdvChart.applyCss();
            rdvChart.layout();

            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getNode() != null) {
                    Tooltip tooltip = new Tooltip(
                            data.getXValue() + " : " + data.getYValue() + " RDV"
                    );
                    Tooltip.install(data.getNode(), tooltip);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMonthName(int month) {
        String[] months = {
                "Jan", "Fév", "Mar", "Avr", "Mai", "Juin",
                "Juil", "Août", "Sep", "Oct", "Nov", "Déc"
        };
        return months[month - 1];
    }
}
