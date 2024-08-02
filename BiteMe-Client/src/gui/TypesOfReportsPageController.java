package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import common.Restaurant;
import common.RestaurantReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TypesOfReportsPageController implements Initializable {

    @FXML
    private Button showReportButton;

    @FXML
    private Label reportTypesLabel;

    @FXML
    private Button backToBranchManagerHomePageButton;
    
    @FXML
    private ComboBox<String> restaurantNameCombobox;
    
    @FXML
    private Label messageTypesOfReportsPageLabel;
    
    @FXML
    private ComboBox<String> reportTypeComboBox;
    
    @FXML
    private ComboBox<String> monthComboBox;
    
    @FXML
    private ComboBox<String> yearComboBox;

    private Stage stage;

    @FXML
    void showReportButtonOnClickAction(ActionEvent event) throws Exception {
        String selectedmonthReport = monthComboBox.getValue();
        String selectedYearReport = yearComboBox.getValue();
        String selectedRestaurant = restaurantNameCombobox.getValue();
        String selectedReportType = reportTypeComboBox.getValue();
        ChatClient.chosenRestaurantReport = new RestaurantReport(selectedmonthReport, selectedYearReport,
                selectedRestaurant, ChatClient.user.getHome_branch());
        
        if (selectedReportType == null) {
			messageTypesOfReportsPageLabel.setText("please choose a report's type");
		} else if (selectedRestaurant == null) {
 			messageTypesOfReportsPageLabel.setText("please choose a restaurant's name");
 		} else if (selectedYearReport == null) {
			messageTypesOfReportsPageLabel.setText("please choose a report's year");
		} else if (selectedmonthReport == null) {
			messageTypesOfReportsPageLabel.setText("please choose a report's month");
		} else {
        
			
			System.err.println("selectedReportType = " + selectedReportType);
	        
	        switch (selectedReportType) {
	        case "Orders Report":
	            // closing current page
	            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            stage.close();
	            // opening new page
	            OrdersReportPageController ORP = new OrdersReportPageController();
	            Stage newStage = new Stage();
	            ORP.start(newStage);
	            break;
	        case "Income Report":
	            // closing current page
	            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            stage.close();
	            // opening new page
	            IncomeReportPageController IRP = new IncomeReportPageController();
	            newStage = new Stage();
	            IRP.start(newStage);
	            break;
	        case "Performance Report":
	            // closing current page
	            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            stage.close();
	            // opening new page
	            PerformanceReportPageController PRP = new PerformanceReportPageController();
	            newStage = new Stage();
	            PRP.start(newStage);
	            break;
	           
	            
	            
	        default:
	        	   System.out.println("TypeOfReportsPage - undefined report's type");
	        	   break;
	           
			
	        }
        }
    }

    @FXML
    void backToBranchManagerHomePageButtonOnClickAction(ActionEvent event) throws Exception {
        // closing current page
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // opening BranchManagerHomePage
        BranchManagerHomePageController BMHP = new BranchManagerHomePageController();
        Stage newStage = new Stage();
        BMHP.start(newStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ensure ComboBox is initialized before adding items
        if (yearComboBox != null) {
            yearComboBox.getItems().addAll("2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
        }
        
        if (reportTypeComboBox != null) {
            reportTypeComboBox.getItems().addAll("Orders Report", "Income Report", "Performance Report");
        }

        if (monthComboBox != null) {
            monthComboBox.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        }
        
        // Populate restaurantNameCombobox with restaurant names
        if (restaurantNameCombobox != null) {
            for (Restaurant restaurant : ChatClient.restaurantsInfo) {
                restaurantNameCombobox.getItems().add(restaurant.getRestaurant_name());
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/TypesOfReportsPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Types Of Reports Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
