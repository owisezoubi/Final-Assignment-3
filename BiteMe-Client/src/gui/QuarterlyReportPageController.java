package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.IncomeOrdersDetails;
import common.PerformanceReport;
import common.QuarterlyOrdersReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class QuarterlyReportPageController implements Initializable  {

    @FXML
    private Label quarterlyReportLabel;
    
    @FXML
    private Button showReportQuarterlyReportButton;

    @FXML
    private BarChart<String,Number> ordersPerRestaurantBarChart;

    @FXML
    private BarChart<?, ?> revenuePerRestaurantBarChart;

    @FXML
    private Label ordersPerRestaurantLabel;

    @FXML
    private Label revenuePerRestaurantLabel;

    @FXML
    private ComboBox<String> QuarterComboBox;

    @FXML
    private Button backCEOHomePage;
    
    @FXML
    private Label messageforquarterlyReport;
    
    @FXML
    private ComboBox<String> yearQuaterlyReportcombobox;
    
    Stage primaryStage,stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		   if (QuarterComboBox != null) {
			   QuarterComboBox.getItems().addAll("January, February, and March", "April, May, and June.", "July, August, and September.","October, November, and December.");
	        }

		   if (yearQuaterlyReportcombobox != null) {
			   yearQuaterlyReportcombobox.getItems().addAll("2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
	        }

		
	}
    
	@FXML
    void backCEOHomePageOnClickAction(ActionEvent event) throws Exception {
    
	}
	
	@SuppressWarnings({ "unchecked" })
	@FXML
    void showReportQuarterlyReportButtonOnClickAction(ActionEvent event) throws Exception {
		String selectedyearQuaterlyReport =yearQuaterlyReportcombobox.getValue();
		String selectedQuarter =QuarterComboBox.getValue();
		if(selectedQuarter==null)
			messageforquarterlyReport.setText("Please choose a Quarter.");
		else if (selectedyearQuaterlyReport==null) {
			messageforquarterlyReport.setText("Please choose a year.");
		}
		else {
		
			messageforquarterlyReport.setText("i AmbientLight inside the else");
			String firstMonth=null;
			String secondMonth=null;
			String thirdMonth=null;
			if(selectedQuarter.equals("January, February, and March")) {
				firstMonth="1";
				secondMonth="2";
				thirdMonth="3";
			}else if(selectedQuarter.equals("April, May, and June.")) {
				firstMonth="4";
				secondMonth="5";
				thirdMonth="6";	
			}else if (selectedQuarter.equals("July, August, and September.")) {
				firstMonth="7";
				secondMonth="8";
				thirdMonth="9";	
			}else if(selectedQuarter.equals("October, November, and December.")) {
				firstMonth="10";
				secondMonth="11";
				thirdMonth="12";	
			}
			ArrayList<String> getQuarterlyReportInfoMSG = new ArrayList<>();
			getQuarterlyReportInfoMSG.add(0, "Get QuarterlyReport Info");
			getQuarterlyReportInfoMSG.add(1,ChatClient.branch);
			getQuarterlyReportInfoMSG.add(2,firstMonth);
			getQuarterlyReportInfoMSG.add(3,secondMonth);
			getQuarterlyReportInfoMSG.add(4,thirdMonth );
			getQuarterlyReportInfoMSG.add(5,selectedyearQuaterlyReport);
			
			System.err.println("msg from server to client: " + getQuarterlyReportInfoMSG);
			
			ClientUI.chat.accept(getQuarterlyReportInfoMSG);
			
			ArrayList<QuarterlyOrdersReport> ordersInfo = (ArrayList<QuarterlyOrdersReport>) ChatClient.inputList;
			System.err.println("Result from Server, in PerformanceReportPageController: " + ordersInfo);
			
			
	       if (ordersInfo.isEmpty()) {
	    	   System.out.println("is empty");
	    	   messageforquarterlyReport.setText("There are no orders for the specified restaurant on the given date.");
	        } else {
	        	populateBarChart(ordersInfo);
	        	
	        }
	       
		}
	
		
	}
	
    private void populateBarChart(ArrayList<QuarterlyOrdersReport> ordersInfo) {
    	//ordersPerRestaurantBarChart.getData().clear();  // Clear existing data
        XYChart.Series<String, Number> ordersNumberSeries = new XYChart.Series<>();
        ordersNumberSeries.setName("Orders Count");

        for (QuarterlyOrdersReport report : ordersInfo) {
            ordersNumberSeries.getData().add(new XYChart.Data<>(report.getRestaurantName(), report.getNumberOfOrders()));
            
        }
        ordersPerRestaurantBarChart.getData().add(ordersNumberSeries);
    }


	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/QuarterlyReportPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Quarterly Report Page");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
