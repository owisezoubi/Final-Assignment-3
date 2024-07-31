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
import javafx.scene.Node;
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
        // Assuming restaurantsInfo is already populated
        populateGridView();
    }
    
    

    private void populateGridView() {
        int column = 0;
        int row = 0;

        for (final Restaurant restaurant : ChatClient.restaurantsInfo) {
            Button restaurantButton = new Button(restaurant.getRestaurant_name());

            restaurantButton.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                	// closing current page
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.close();

					// opening new page
					RestaurantMenuPageController RMP = new RestaurantMenuPageController();
					Stage primaryStage = new Stage();
					try {
						RMP.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}                }
            });

            gridViewRestaurantList.add(restaurantButton, column, row);

            column++;
            if (column > 2) { // 3 restaurants per row
                column = 0;
                row++;
            }
        }
    }
	
	

	
	
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
