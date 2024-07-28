package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import client.ChatClient;
import client.ClientUI;
import common.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController implements Initializable{
	
	public static User user;

    @FXML
    private TextField userNameTextField;

    @FXML
    private Button clearfieldsTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label PasswordLabel;
    
    @FXML
    private Label messageLoginPage;

    @FXML
    private Button exitButton;

    @FXML
    private PasswordField passwordField;
    
    
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    
    
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/LoginPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("IPConnectionPage");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/LoginPage.css").toExternalForm());
    }
    
    
    @FXML
    void LoginButtonOnClickAction(ActionEvent event) throws Exception {
    	
    	String userName = userNameTextField.getText().trim();
    	String password = passwordField.getText().trim();
;    	
    	if (userName.isEmpty() || userName.isEmpty()) { // handling empty input
			messageLoginPage.setText("please fill the fields");
		}
    	else {
    		ArrayList<String> msg = new ArrayList<>();
    		msg.add(0, "Get Login Validation");
    		msg.add(1, userName);
    		msg.add(2, password);
    		
    		ClientUI.chat.accept(msg);
    		ArrayList<String> result = ChatClient.inputList;
    		
    		
    		
    		switch (result.get(0)) {
			case "NotExist":
				messageLoginPage.setText("Username Not Exist");
				break;

			case "UserFound":
				
				if (result.get(2).equals(password) == false) {
					
					System.out.println("username found after if statement:     correctPass: " + result.get(2) + "        inputPass: " + password);
					
					messageLoginPage.setText("Invalid password");
				} else if (Integer.parseInt(result.get(3)) == 0) {
					switch (result.get(4)) {
					case "customer":
						
						ArrayList<String> userLoggedInMessage = new ArrayList<>();
						userLoggedInMessage.add(0, "User Logged In");
						userLoggedInMessage.add(1, userName);
			    		
						
						ClientUI.chat.accept(userLoggedInMessage);
			
						
						System.out.println("passwords are equal");
						
						

						
						Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		            	stage.close();

		            	CustomerHomePageController LIF = new CustomerHomePageController();
		            	Stage primaryStage = new Stage();
		            	LIF.start(primaryStage);

						
						
						break;

					case "restaurant":
						
						break;
						
					case "certified worker":
	
						break;
	
					case "branch manager":
	
						break;
					
					case "CEO":
						
						break;
						
					default:
						break;
					}
				} else {
					messageLoginPage.setText("Username is already logged in");
				}
				
				break;
				
			default:
				messageLoginPage.setText("Something went wrong");
				break;
			}
		}
    	
    	

    }

    @FXML
    void ClearFieldsButtonOnClickAction(ActionEvent event) {
    	userNameTextField.clear();
    	passwordField.clear();
    }

    @FXML
    void exitButtonOnClickAction(ActionEvent event) {
		System.out.println("Close Login Page");
    	ClientUI.chat.client.quit();
    	System.exit(0);

    }
	

}
