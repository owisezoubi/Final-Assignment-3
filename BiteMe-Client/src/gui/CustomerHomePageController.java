package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
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

public class CustomerHomePageController implements Initializable {

    @FXML
    private Button orderCustomerButton;

    @FXML
    private Button logoutCustomerButton;

    @FXML
    private Button myOrdersCustomerButton;
    
    @FXML
    private Label greetingsCustomerHomePageLabel;

    @FXML
    void logoutCustomerButtonOnClickAction(ActionEvent event) throws Exception {

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
    void myOrdersCustomerButtonOnClickAction(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		greetingsCustomerHomePageLabel.setText("Welcome Back " + ChatClient.user.getUser_name());
		
	}
	
	
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/CustomerHomePage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("CustomerHomePageController");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/CustomerHomePage.css").toExternalForm());
    }
	

}
