package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RestaurantHomePageController implements Initializable{

    @FXML
    private Button updateOrderIsReadyRestaurantButton;

    @FXML
    private Button confirmOrdersRestaurantButton;

    @FXML
    private Button logoutRestaurantButton;

    @FXML
    private Label greetingsRestaurantLabel;

    @FXML
    void confirmOrdersRestaurantButtonOnClickAction(ActionEvent event) {

    }

    @FXML
    void logoutRestaurantButtonOnClickAction(ActionEvent event) {

    }

    @FXML
    void updateOrderIsReadyRestaurantButtonOnClickAction(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/RestaurantHomePage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("IPConnectionPage");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/RestaurantHomePage.css").toExternalForm());
    }

}
