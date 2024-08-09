package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.ChatIF;
import common.Item;
import common.Order;
import common.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OrderRestaurantListPageController implements Initializable {
	
	@FXML
	private ComboBox<String> BranchRestaurantsComboBox;

	@FXML
	private Label RestaurantslistLabel;

	@FXML
	private Button backtoHomePageButton;

	@FXML
	private GridPane gridViewRestaurantList;

	@FXML
	void backtoHomePageButtonOnClickAction(ActionEvent event) throws Exception {
		// closing current page
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

		// opening new page
		CustomerHomePageController CHP = new CustomerHomePageController();
		Stage primaryStage = new Stage();
		CHP.start(primaryStage);             
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// empty the cart
    	ChatClient.cart = new ArrayList<>();
    	
    	
    	// Populate the ComboBox with branch options
        BranchRestaurantsComboBox.setItems(FXCollections.observableArrayList("North", "South", "Center"));

        // Set the initial value based on the customer's home branch
        String homeBranch = ChatClient.customer.getHome_branch();
        BranchRestaurantsComboBox.setValue(getBranchNameFromCode(homeBranch));
    	
    	
        // restaurantsInfo is already populated
        populateGridView(homeBranch);
    }
    
    @FXML
    void handleBranchSelection(ActionEvent event) {
        String selectedBranch = BranchRestaurantsComboBox.getValue();
        String branchCode = getBranchCodeFromName(selectedBranch);
        populateGridView(branchCode);
    }

    private String getBranchCodeFromName(String branchName) {
        switch (branchName) {
            case "North":
                return "1";
            case "South":
                return "2";
            case "Center":
                return "3";
            default:
                return "1"; // Default to North if unknown
        }
    }

    
    private String getBranchNameFromCode(String branchCode) {
        switch (branchCode) {
            case "1":
                return "North";
            case "2":
                return "South";
            case "3":
                return "Center";
            default:
                return "Unknown";
        }
    }
    


    private void populateGridView(String branchCode) {
        gridViewRestaurantList.getChildren().clear(); // Clear previous content

        int column = 0;
        int row = 0;

        for (final Restaurant restaurant : ChatClient.restaurantsInfo) {
            // Only show restaurants that match the selected branch
            if (restaurant.getHome_branch().equals(branchCode)) {
                Button restaurantButton = new Button(restaurant.getRestaurant_name());

                restaurantButton.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent event) {
                        ChatClient.chosenRestaurantByCustomer = restaurant;
                        System.out.println("Chosen restaurant: " + ChatClient.chosenRestaurantByCustomer);

                        // Getting the restaurant's menu info from DB
                        ArrayList<String> msg = new ArrayList<>();
                        msg.add("Get Restaurant Menu Info");
                        msg.add(ChatClient.chosenRestaurantByCustomer.getMenu_id());

                        ClientUI.chat.accept(msg);
                        ChatClient.choosenRestaurantMenu = (ArrayList<Item>) ChatClient.inputList;

                        try {
                            // Close current page and open new page
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.close();

                            RestaurantMenuPageController RMP = new RestaurantMenuPageController();
                            Stage primaryStage = new Stage();
                            RMP.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                gridViewRestaurantList.add(restaurantButton, column, row);

                column++;
                if (column > 2) { // 3 restaurants per row
                    column = 0;
                    row++;
                }
            }
        }
    }

	
	

	
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/OrderRestaurantListPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("OrderRestaurantListPage");
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.getStylesheets().add(getClass().getResource("/gui/OrderRestaurantListPage.css").toExternalForm());
	}

}
