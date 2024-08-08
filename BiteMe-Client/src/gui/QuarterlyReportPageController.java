package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.QuarterlyOrdersReport;
import common.QuarterlyRevenueReport;
import javafx.event.ActionEvent;  // Corrected import for JavaFX
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;


public class QuarterlyReportPageController implements Initializable {

    @FXML
    private Label quarterlyReportLabel;

    @FXML
    private Button backToCEOHomePage;

    @FXML
    private Button showReportButton;

    @FXML
    private ComboBox<String> quarterlyReportcombobox;

    @FXML
    private ComboBox<String> yearQuarterlyReportcombobox;

    @FXML
    private BarChart<String, Number> totalOrdersPerRestaurantBarChart;  // Assuming the chart types

    @FXML
    private Label totalOrdersPerRestaurantLabel;

    @FXML
    private BarChart<String, Number> totalRevenuePerRestaurantBarChart; // Assuming the chart types

    @FXML
    private Label totalRevenuePerRestaurantLabel;

    @FXML
    private Label messageQuarterlyReport;
    
    @FXML
    private Button compraeCeoButton;

    private Stage stage;
    
    @FXML
    private CategoryAxis xAxisForOrders;

    @FXML
    private NumberAxis yAxisForOrders;
    
    @FXML
    private ComboBox<String> branchComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (quarterlyReportcombobox != null) {
            quarterlyReportcombobox.getItems().addAll("January, February, and March", "April, May, and June.", "July, August, and September.", "October, November, and December.");
        }

        if (yearQuarterlyReportcombobox != null) {
            yearQuarterlyReportcombobox.getItems().addAll("2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
        }

      if (branchComboBox != null) {
    	  branchComboBox.getItems().addAll("North", "South", "Central");
    }

        // Set properties for orders bar chart
//        if (xAxisForOrders != null) {
//            xAxisForOrders.setTickLabelRotation(-45); // Rotate labels to prevent overlap
//        }

        // Disable animations for both bar charts to avoid update delays that can cause overlap
        totalOrdersPerRestaurantBarChart.setAnimated(false);
        totalRevenuePerRestaurantBarChart.setAnimated(false);
    }


    
    
    @FXML
    void compraeCEOButtonOnClickAction(ActionEvent event) throws Exception {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();
    	// opening BranchManagerHomePage
    	CompareQuarterPageController CQP = new CompareQuarterPageController();
    	Stage newStage = new Stage();
    	CQP.start(newStage);
    }
    
    
	@FXML
    void showReportButtonOnClickAction(ActionEvent event) {
        String selectedYear = yearQuarterlyReportcombobox.getValue();
        String selectedQuarter = quarterlyReportcombobox.getValue();
    	String selectedBranch=branchComboBox.getValue();
    	
    	totalOrdersPerRestaurantBarChart.getData().clear();
        totalRevenuePerRestaurantBarChart.getData().clear();
        messageQuarterlyReport.setText("");
        
        if (selectedQuarter == null) {
            messageQuarterlyReport.setText("Please choose a Quarter");
            return;
        }
        if (selectedYear == null) {
            messageQuarterlyReport.setText("Please choose a year");
            return;
        }
        if (selectedBranch== null) {
        	messageQuarterlyReport.setText("Please choose a branch.");
        	return;
        }
        String branch;
   		if (selectedBranch.equals("North")) 
   			branch="1";
   		
   		else if (selectedBranch.equals("South")) 
   			branch="2";
   		
   		else 
   			branch="3";
   		
        String[] months = getQuarterMonths(selectedQuarter);
        ArrayList<String> msgOrdersReport = new ArrayList<>();
        msgOrdersReport.add("Get QuarterlyOrdersReport Info");
        msgOrdersReport.add(branch);
        msgOrdersReport.add(selectedYear);
        msgOrdersReport.addAll(Arrays.asList(months));
        ClientUI.chat.accept(msgOrdersReport);
        ArrayList<QuarterlyOrdersReport> ordersInfo = (ArrayList<QuarterlyOrdersReport>) ChatClient.inputList;
        
       
        ArrayList<String> msgRevenueReport = new ArrayList<>();
        msgRevenueReport.add("Get QuarterlyRevenueReport Info");
        msgRevenueReport.add(branch);
        msgRevenueReport.add(selectedYear);
        msgRevenueReport.addAll(Arrays.asList(months));
        ClientUI.chat.accept(msgRevenueReport);
        ArrayList<QuarterlyRevenueReport> ordersRevenueInfo = (ArrayList<QuarterlyRevenueReport>) ChatClient.inputList;
        
        if (ordersInfo.isEmpty()|| ordersRevenueInfo.isEmpty() ) {
            messageQuarterlyReport.setText("There are no orders for the specified period.");
            return;
        }
 
        populateBarChartforOrdersReport(ordersInfo,months);
        populateBarChartforRevenueReport(ordersRevenueInfo,months);

	}
	
    private void populateBarChartforOrdersReport(ArrayList<QuarterlyOrdersReport> ordersInfo,String[] months) {
    	 XYChart.Series<String, Number> series1 = new XYChart.Series<>();
         series1.setName(months[0]);  // e.g., "January"
         XYChart.Series<String, Number> series2 = new XYChart.Series<>();
         series2.setName(months[1]);  // e.g., "February"
         XYChart.Series<String, Number> series3 = new XYChart.Series<>();
         series3.setName(months[2]);  // e.g., "March"

         for (QuarterlyOrdersReport report : ordersInfo) {
             // Add data to each series based on the restaurant name and orders for each month
             series1.getData().add(new XYChart.Data<>(report.getRestaurantName(), report.getOrdersMonth1()));
             series2.getData().add(new XYChart.Data<>(report.getRestaurantName(), report.getOrdersMonth2()));
             series3.getData().add(new XYChart.Data<>(report.getRestaurantName(), report.getOrdersMonth3()));
         }
         totalOrdersPerRestaurantBarChart.getData().addAll(series1, series2, series3);
    }
    
    private void populateBarChartforRevenueReport(ArrayList<QuarterlyRevenueReport> ordersInfo,String[] months) {
   	 XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(months[0]);  // e.g., "January"
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(months[1]);  // e.g., "February"
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName(months[2]);  // e.g., "March"

        for (QuarterlyRevenueReport report : ordersInfo) {
            // Add data to each series based on the restaurant name and orders for each month
            series1.getData().add(new XYChart.Data<>(report.getRestaurantName(), report.getRevenueMonth1()));
            series2.getData().add(new XYChart.Data<>(report.getRestaurantName(), report.getRevenueMonth2()));
            series3.getData().add(new XYChart.Data<>(report.getRestaurantName(), report.getRevenueMonth3()));
        }
        totalRevenuePerRestaurantBarChart.getData().addAll(series1, series2, series3);
   }
    	  
    private String[] getQuarterMonths(String quarter) {
        switch (quarter) {
            case "January, February, and March":
                return new String[]{"1", "2", "3"};
            case "April, May, and June.":
                return new String[]{"4", "5", "6"};
            case "July, August, and September.":
                return new String[]{"7", "8", "9"};
            case "October, November, and December.":
                return new String[]{"10", "11", "12"};
            default:
                return null;
        }
    }
    
    @FXML
    void backToCEOHomePageOnClickAction(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Opening CEOHomePage
        CEOHomePageController ceoHomePageController = new CEOHomePageController();
        Stage newStage = new Stage();
        ceoHomePageController.start(newStage);
    }
    
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/QuarterlyReportPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Quarterly Report Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
