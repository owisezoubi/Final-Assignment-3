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
    
    @FXML
    private ComboBox<String> branchComboBox;

    private Stage stage;

    @FXML
    void showReportButtonOnClickAction(ActionEvent event) throws Exception {
        String selectedMonthReport = monthComboBox.getValue();
        String selectedYearReport = yearComboBox.getValue();
        String selectedRestaurant = restaurantNameCombobox.getValue();
        String selectedReportType = reportTypeComboBox.getValue();

        if (selectedReportType == null) {
            messageTypesOfReportsPageLabel.setText("Please choose a report type.");
        } else if (selectedRestaurant == null) {
            messageTypesOfReportsPageLabel.setText("Please choose a restaurant name.");
        } else if (selectedYearReport == null) {
            messageTypesOfReportsPageLabel.setText("Please choose a report year.");
        } else if (selectedMonthReport == null) {
            messageTypesOfReportsPageLabel.setText("Please choose a report month.");
        } else {
            ChatClient.chosenRestaurantReport = new RestaurantReport(selectedMonthReport, selectedYearReport,
                    selectedRestaurant, ChatClient.user.getHome_branch());

            System.err.println("selectedReportType = " + selectedReportType);

            switch (selectedReportType) {
                case "Orders Report":
                    // closing current page
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    // opening new page
                    OrdersReportPageController ORP = new OrdersReportPageController();
                    Stage newStage1 = new Stage();
                    ORP.start(newStage1);
                    break;
                case "Income Report":
                    // closing current page
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    // opening new page
                    IncomeReportPageController IRP = new IncomeReportPageController();
                    Stage newStage2 = new Stage();
                    IRP.start(newStage2);
                    break;
                case "Performance Report":
                    // closing current page
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    // opening new page
                    PerformanceReportPageController PRP = new PerformanceReportPageController();
                    Stage newStage3 = new Stage();
                    PRP.start(newStage3);
                    break;
                default:
                    System.out.println("TypesOfReportsPage - undefined report type");
                    break;
            }
        }
    }

    @FXML
    void backToBranchManagerHomePageButtonOnClickAction(ActionEvent event) throws Exception {
    	if(ChatClient.user.getUser_type().equals("branch_manager")) {
        // closing current page
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // opening BranchManagerHomePage
        BranchManagerHomePageController BMHP = new BranchManagerHomePageController();
        Stage newStage = new Stage();
        BMHP.start(newStage);
    	}
    	else {
    		   // closing current page
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // opening BranchManagerHomePage
            CEOHomePageController CHP = new CEOHomePageController();
            Stage newStage = new Stage();
            CHP .start(newStage);
    	}
    }

//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // Ensure ComboBox is initialized before adding items
//    	String branch;
//    	if(ChatClient.user.getUser_type().equals("branch_manager"))
//    		branch=ChatClient.user.getHome_branch();
//    	else {
//    		branch=ChatClient.branch;
//
//    	}
//        if (yearComboBox != null) {
//            yearComboBox.getItems().addAll("2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
//        }
//
//        if (reportTypeComboBox != null) {
//            reportTypeComboBox.getItems().addAll("Orders Report", "Income Report", "Performance Report");
//        }
//
//        if (monthComboBox != null) {
//            monthComboBox.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
//        }
//
//        // Populate restaurantNameCombobox with restaurant names
//        if (restaurantNameCombobox != null) {
//        	
//            for (Restaurant restaurant : ChatClient.restaurantsInfo) {
//            	String branchName=ChatClient.user.getUser_type().equals("branch_manager")? ChatClient.user.getHome_branch() :ChatClient.branch;           	
//            	if(branchName.equals(restaurant.getHome_branch())) {
//                restaurantNameCombobox.getItems().add(restaurant.getRestaurant_name());
//            	}
//            	
//            }
//        }
//    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initially hide the restaurant combo box until a branch is selected
        restaurantNameCombobox.setVisible(false);

        // Setup branch combo box
        if (ChatClient.user.getUser_type().equals("branch_manager")) {
        	String branch;
        	if(ChatClient.user.getHome_branch().equals("1"))
        		branch="North";
        	else if (ChatClient.user.getHome_branch().equals("2"))
        		branch="South";
        	else 
				branch="Central";
            branchComboBox.getItems().add(branch);
        } 
        else {
            branchComboBox.getItems().addAll("North", "South", "Central");
        }

        // Set up listeners to update restaurant names based on selected branch
        branchComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Selected Branch: " + newVal);  // Debugging statement
            updateRestaurantCombobox(newVal);
        });

        // Populate other comboboxes
        if (yearComboBox != null) {
            yearComboBox.getItems().addAll("2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
        }

        if (reportTypeComboBox != null) {
            reportTypeComboBox.getItems().addAll("Orders Report", "Income Report", "Performance Report");
        }

        if (monthComboBox != null) {
            monthComboBox.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        }
    }

    private void updateRestaurantCombobox(String selectedBranch) {
        restaurantNameCombobox.getItems().clear(); // Clear existing items

        if (selectedBranch != null) {
            restaurantNameCombobox.setVisible(true); // Show the combobox if a branch is selected
            String branch;
            if (selectedBranch.equals("North")) {
    			branch="1";
    		}
    		else if (selectedBranch.equals("South")) {
    			branch="2";
    		}
    		else {
    			branch="3";
    		}
            // Filter and add restaurants from the selected branch
            for (Restaurant restaurant : ChatClient.restaurantsInfo) {
                if (restaurant.getHome_branch().equals(branch)) {
                    restaurantNameCombobox.getItems().add(restaurant.getRestaurant_name());
                    System.out.println("Added Restaurant: " + restaurant.getRestaurant_name());  // Debugging statement
                }
            }
        } else {
            restaurantNameCombobox.setVisible(false); // Hide the combobox if no branch is selected
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
