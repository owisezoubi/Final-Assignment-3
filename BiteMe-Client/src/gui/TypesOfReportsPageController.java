package gui;

import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Order;
import common.Restaurant;
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

    private Stage stage, primaryStage;
    

	@FXML
    void showReportButtonOnClickAction(ActionEvent event) throws Exception {
    	String selectedmonthReport=monthComboBox.getValue();
        String selectedRestaurant = restaurantNameCombobox.getValue();
        String selectedReportType = reportTypeComboBox.getValue();
        //check input validation 
        switch(selectedReportType) {
    	case "Orders Report":
    		// closing current page
        	stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	stage.close();
        	// opening new page
        	OrdersReportPageController ORP = new OrdersReportPageController();
        	primaryStage = new Stage();
        	ORP.start(primaryStage);
        	break;
    	case "InCome Report":
    		// closing current page
        	stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	stage.close();
        	// opening new page
        	IncomeReportPageController IRP = new IncomeReportPageController();
        	primaryStage = new Stage();
        	IRP.start(primaryStage);
        	break;
    	case "Performance Report":
    		// closing current page
        	stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	stage.close();
        	// opening new page
        	PerformanceReportPageController PRP = new PerformanceReportPageController();
        	primaryStage = new Stage();
        	PRP.start(primaryStage);
        	break;
    		
    	}
    }
    
    @FXML
    void backToBranchManagerHomePageButtonOnClickAction(ActionEvent event) throws Exception {
    	// closing current page
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

		// opening new page
		BranchManagerHomePageController BMHP = new BranchManagerHomePageController();
		primaryStage = new Stage();
		BMHP.start(primaryStage);
    }

	public void start(Stage primaryStage2) throws Exception {
		FXMLLoader loader = new FXMLLoader();
	    Parent root = loader.load(getClass().getResource("/gui/TypesOfReportsPage.fxml").openStream());
	    Scene scene = new Scene(root);
	    primaryStage.setTitle("IPConnectionPage");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    // scene.getStylesheets().add(getClass().getResource("/gui/BranchManagerHomePage.css").toExternalForm());
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// getting the restaurants info from DB, for displaying them in ComboBox in TypesOfReportsPage
		for (Restaurant restaurant : ChatClient.restaurantsInfo) {
			restaurantNameCombobox.getItems().add(restaurant.getRestaurant_name());
		}  
		reportTypeComboBox.getItems().add("Orders Report");
		reportTypeComboBox.getItems().add("InCome Report");
		reportTypeComboBox.getItems().add("Performance Report");
		reportTypeComboBox.getItems().addAll("01", "02", "03","04","05","06","07","08","09","10","11","12");

	}

}
