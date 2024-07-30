package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.ChatIF;
import common.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OrderRestaurantListPageController implements Initializable {
	
	
	private ObservableList<String> restaurantData = FXCollections.observableArrayList();
    private ArrayList<String> selectedRestaurant;
	

    @FXML
    private Label RestaurantslistLabel;

    @FXML
    private Button chooseRestaurantButton;

    @FXML
    private Button backtoHomePageButton;

    @FXML
    private GridPane gridViewRestaurantList;

    @FXML
    void backtoHomePageButtonOnClickAction(ActionEvent event) {

    }

    @FXML
    void chooseRestaurantButtonOnClickAction(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		
	}
	
	
	private void loadRestaurantData() {
		ArrayList<String> getRestaurantNames = new ArrayList<String>();
		
		getRestaurantNames.add(0, "Get Restaurants Info");

		ClientUI.chat.accept(getRestaurantNames);
		
		
        //ArrayList<Restaurant> restaurantsArrayList = ChatClient.inputList;
        
    }
	
	
//	private void populateGridPane() {
//        int row = 0;
//        int column = 0;
//
//        // Set column and row constraints if needed
//        gridViewRestaurantList.setHgap(10);
//        gridViewRestaurantList.setVgap(10);
//        gridViewRestaurantList.setPadding(new Insets(10));
//
//        for (Restaurant restaurant : restaurantData) {
//            Button restaurantButton = new Button(restaurant.getName());
//            restaurantButton.setPrefSize(100, 30);
//            restaurantButton.setOnAction(event -> {
//                selectedRestaurant = restaurant;
//                handleChooseRestaurant();
//            });
//
//            gridViewRestaurantList.add(restaurantButton, column, row);
//            column++;
//
//            if (column > 1) { // Adjust number of columns
//                column = 0;
//                row++;
//            }
//        }
//    }
	
	
	
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/OrderRestaurantListPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("IPConnectionPage");
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.getStylesheets().add(getClass().getResource("/gui/OrderRestaurantListPage.css").toExternalForm());
	}

}
