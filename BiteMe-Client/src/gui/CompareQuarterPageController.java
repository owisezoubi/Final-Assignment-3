package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.QuarterlyOrdersReport;
import common.QuarterlyRevenueReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CompareQuarterPageController implements Initializable{

    @FXML
    private Button showFirstReportButton;

    @FXML
    private ComboBox<String> YearCompareComboBox1;

    @FXML
    private ComboBox<String> QuarterCompareComboBox1;

    @FXML
    private ComboBox<String> QuarterCompareComboBox2;

    @FXML
    private ComboBox<String> YearCompareComboBox2;
    
    @FXML
    private ComboBox<String> branchComboBox1;

    @FXML
    private ComboBox<String> branchComboBox2;

    @FXML
    private Button showSecondReportButton;

    @FXML
    private BarChart<String, Number> firstTotalOrdersBarChart;

    @FXML
    private BarChart<String, Number> firstTotalRevenueBarChart;

    @FXML
    private BarChart<String, Number> secondTotalOrdersBarChart;

    @FXML
    private BarChart<String, Number> secondTotalRevenueBarChart;

    @FXML
    private Label compareReportLabel;

    @FXML
    private Label messageForComparePageLabel;
    
    @FXML
    private Button backToQuarterlyReportPageButton;

    private Stage stage;
    
    @FXML
    void backToQuarterlyReportPageButtonOnClickAction(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Opening CEOHomePage
        QuarterlyReportPageController QRC = new QuarterlyReportPageController();
        Stage newStage = new Stage();
        QRC.start(newStage);
    }
    
    @FXML
    void showFirstReportButtonOnClickAction(ActionEvent event) {
        firstTotalOrdersBarChart.getData().clear();
        firstTotalRevenueBarChart.getData().clear();
        messageForComparePageLabel.setText("");
        
    	String selectedYear = YearCompareComboBox1.getValue();
        String selectedQuarter = QuarterCompareComboBox1.getValue();
        String selectedBranch=branchComboBox1.getValue();
        String branch;
        if (selectedQuarter == null) {
        	messageForComparePageLabel.setText("Please choose a Quarter for the first report.");
            return;
        }
        if (selectedYear == null) {
        	messageForComparePageLabel.setText("Please choose a year for the first report.");
            return;
        }
    	if (selectedBranch== null) {
   		 messageForComparePageLabel.setText("Please choose a branch.");
   		 return;
    	}
    	if (selectedBranch.equals("North")) {
    		branch="1";
    	}
    	else if (selectedBranch.equals("South")) {
    		branch="2";
    	}
    	else {
    		branch="3";
    	}
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
        	messageForComparePageLabel.setText("There are no orders for the specified period.");
            return;
        }
       

        firstTotalOrdersBarChart.setAnimated(false);
        firstTotalRevenueBarChart.setAnimated(false);
        populateBarChartforOrdersReport(ordersInfo,months,firstTotalOrdersBarChart);
        populateBarChartforRevenueReport(ordersRevenueInfo,months,firstTotalRevenueBarChart);
    }
    

    @FXML
    void showSecondReportButtonOnClickAction(ActionEvent event) {
        secondTotalOrdersBarChart.getData().clear();
        secondTotalRevenueBarChart.getData().clear();
        messageForComparePageLabel.setText("");
        
    	String selectedYear = YearCompareComboBox2.getValue();
        String selectedQuarter = QuarterCompareComboBox2.getValue();
        String selectedBranch=branchComboBox2.getValue();
        String branch;
        if (selectedQuarter == null) {
        	messageForComparePageLabel.setText("Please choose a Quarter for the second report.");
            return;
        }
        if (selectedYear == null) {
        	messageForComparePageLabel.setText("Please choose a yearfor the second report.");
            return;
        }
        if (selectedBranch== null) {
      		 messageForComparePageLabel.setText("Please choose a branch.");
      		 return;
       	}
       	if (selectedBranch.equals("North")) {
       		branch="1";
       	}
       	else if (selectedBranch.equals("South")) {
       		branch="2";
       	}
       	else {
       		branch="3";
       	}
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
        	messageForComparePageLabel.setText("There are no orders for the specified period.");
            return;
        }
        secondTotalOrdersBarChart.setAnimated(false);
        secondTotalRevenueBarChart.setAnimated(false);
        populateBarChartforOrdersReport(ordersInfo,months,secondTotalOrdersBarChart);
        populateBarChartforRevenueReport(ordersRevenueInfo,months,secondTotalRevenueBarChart);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (QuarterCompareComboBox1 != null) {
			QuarterCompareComboBox1.getItems().addAll("January, February, and March", "April, May, and June.", "July, August, and September.", "October, November, and December.");
        }

        if (YearCompareComboBox1 != null) {
        	YearCompareComboBox1.getItems().addAll("2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
        }
        
        if (branchComboBox1 != null) {
        	branchComboBox1.getItems().addAll("North", "South", "Central");
        }
        
        if (QuarterCompareComboBox2 != null) {
			QuarterCompareComboBox2.getItems().addAll("January, February, and March", "April, May, and June.", "July, August, and September.", "October, November, and December.");
        }

        if (YearCompareComboBox2 != null) {
        	YearCompareComboBox2.getItems().addAll("2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
        }
        
        if (branchComboBox2 != null) {
        	branchComboBox2.getItems().addAll("North", "South", "Central");
        }
		
	}
	
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/CompareQuarterPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Compare Quarter Page");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //scene.getStylesheets().add(getClass().getResource("/gui/CompareQuarterPage.css").toExternalForm());
    }
	private void populateBarChartforOrdersReport(ArrayList<QuarterlyOrdersReport> ordersInfo,String[] months, BarChart<String,Number> totalOrdersBarChart) {
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
        totalOrdersBarChart.getData().addAll(series1, series2, series3);
   }
   
   private void populateBarChartforRevenueReport(ArrayList<QuarterlyRevenueReport> ordersInfo,String[] months,BarChart<String,Number> totalRevenueBarChart) {
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
       totalRevenueBarChart.getData().addAll(series1, series2, series3);
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
}
