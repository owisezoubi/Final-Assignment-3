package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    void logoutRestaurantButtonOnClickAction(ActionEvent event) throws Exception {

    	String userName = ChatClient.user.getUser_name();
    	
    	ArrayList<String> msg = new ArrayList<>();
		msg.add(0, "User Logged Out");
		msg.add(1, userName);

		ClientUI.chat.accept(msg);
    	
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();

    	LoginPageController LIF = new LoginPageController();
    	Stage primaryStage = new Stage();
    	LIF.start(primaryStage);
    	
    }

    @FXML
    void updateOrderIsReadyRestaurantButtonOnClickAction(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		ArrayList<String> restaurantInfoArrayList = new ArrayList<String>();
		
		
		System.out.println("restaurant username: " + ChatClient.user.getUser_name());
		
		// send to server to get restaurant info after a successfully log in
		restaurantInfoArrayList.add(0, "Get Restaurant Info");
		restaurantInfoArrayList.add(1, ChatClient.user.getUser_name());
		ClientUI.chat.accept(restaurantInfoArrayList);

		// getting restaurant info after login
		restaurantInfoArrayList = ChatClient.inputList;

		ChatClient.restaurant = new Restaurant(restaurantInfoArrayList.get(1), restaurantInfoArrayList.get(2),
				restaurantInfoArrayList.get(3), restaurantInfoArrayList.get(4), restaurantInfoArrayList.get(5),
				restaurantInfoArrayList.get(6), restaurantInfoArrayList.get(7), null, restaurantInfoArrayList.get(9), restaurantInfoArrayList.get(10));
		
		
		
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
