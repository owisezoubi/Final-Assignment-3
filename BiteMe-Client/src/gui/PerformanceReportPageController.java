package gui;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.PerformanceReport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PerformanceReportPageController implements Initializable {

    @FXML
    private Label reportTypeLabel;

    @FXML
    private Label reportDateLabel;

    @FXML
    private BarChart<String, Number> HistogramBarChart;

    @FXML
    private Label showtheDateLabel;

    @FXML
    private Label restaurantnameLabel;

    @FXML
    private Label branchnameLabel;
    

    @FXML
    private Label messagePerformanceReportPage;
    
    private Stage stage;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        restaurantnameLabel.setText("Restaurant Name: " + ChatClient.chosenRestaurantReport.getRestaurant_name());
        branchnameLabel.setText("Branch: " + ChatClient.chosenRestaurantReport.getBranch());
        System.out.println("before sending the msg to the server.");

        ArrayList<String> msg = new ArrayList<>();
        msg.add(0, "Get PerformanceReport Info");
        msg.add(1, ChatClient.chosenRestaurantReport.getRestaurant_name());
        msg.add(2, ChatClient.chosenRestaurantReport.getMonth());
        msg.add(3, ChatClient.chosenRestaurantReport.getYear());
        ClientUI.chat.accept(msg);

        ArrayList<PerformanceReport> ordersInfo = (ArrayList<PerformanceReport>) ChatClient.inputList;
        if (ordersInfo.isEmpty()) {
            messagePerformanceReportPage.setText("There are no orders for the specified restaurant on the given date.");
        } else {
            populateBarChart(ordersInfo);
        }
        System.out.println("Result from Server, in PerformanceReportPageController: " + ordersInfo);
    }


    @FXML
    void  backToTypesOfReportsPageButtonOnClickAction(ActionEvent event) throws Exception {
        
    	// closing current page
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // opening BranchManagerHomePage
        TypesOfReportsPageController TORP1 = new TypesOfReportsPageController();
        Stage newStage = new Stage();
        TORP1.start(newStage);
    }
    
    private void populateBarChart(ArrayList<PerformanceReport> ordersInfo) {
        XYChart.Series<String, Number> lateSeries = new XYChart.Series<>();
        lateSeries.setName("Late Orders");
        XYChart.Series<String, Number> notLateSeries = new XYChart.Series<>();
        notLateSeries.setName("Not Late Orders");

        for (PerformanceReport report : ordersInfo) {
            lateSeries.getData().add(new XYChart.Data<>(report.getDate(), report.getLateOrders()));
            notLateSeries.getData().add(new XYChart.Data<>(report.getDate(), report.getNotLateOrders()));
        }

        HistogramBarChart.getData().addAll(lateSeries, notLateSeries);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/PerformanceReportPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Performance Report Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}