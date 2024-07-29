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
    	
    	// init arraylists for sending messages
    	ArrayList<String> userLoggedInMessage = new ArrayList<>();
    	
    	userLoggedInMessage.add(0, null);
    	userLoggedInMessage.add(1, null);
    	
    	ArrayList<String> userLoggedInInfo = new ArrayList<>();
    	
    	userLoggedInInfo.add(0, null);
    	userLoggedInInfo.add(1, null);
    	
    	
    	ArrayList<String> userInfoArrayList;
    	
    	
    	Stage stage, primaryStage;
    	
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
						
						// send to server to update is_logged_in to 1 
						userLoggedInMessage.set(0, "User Logged In");
						userLoggedInMessage.set(1, userName);
						ClientUI.chat.accept(userLoggedInMessage);
			
						
						
						// send to server to get user info after a successfully log in
						userLoggedInInfo.set(0, "Get User Info");
						userLoggedInInfo.set(1, userName);
						ClientUI.chat.accept(userLoggedInInfo);
						
						
						// getting User info after login
						userInfoArrayList = ChatClient.inputList;
						
						ChatClient.user = new User(userInfoArrayList.get(1), userInfoArrayList.get(2), userInfoArrayList.get(3), userInfoArrayList.get(4),userInfoArrayList.get(5), userInfoArrayList.get(6), userInfoArrayList.get(7), userInfoArrayList.get(8), userInfoArrayList.get(9), userInfoArrayList.get(10));
						
						// closing current page
						stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		            	stage.close();

		            	// opening new page
		            	CustomerHomePageController LIF = new CustomerHomePageController();
		            	primaryStage = new Stage();
		            	LIF.start(primaryStage);

						
						
						break;

					case "restaurant":
						
						
						
						
						
						
						
						
						
						break;
						
					case "certified worker":
						
						
						// send to server to update is_logged_in to 1 
						userLoggedInMessage.set(0, "User Logged In");
						userLoggedInMessage.set(1, userName);
						ClientUI.chat.accept(userLoggedInMessage);
			
						
						
						// send to server to get user info after a successfully log in
						userLoggedInInfo.set(0, "Get User Info");
						userLoggedInInfo.set(1, userName);
						ClientUI.chat.accept(userLoggedInInfo);
						
						
						// getting User info after login
						userInfoArrayList = ChatClient.inputList;
						
						ChatClient.user = new User(userInfoArrayList.get(1), userInfoArrayList.get(2), userInfoArrayList.get(3), userInfoArrayList.get(4),userInfoArrayList.get(5), userInfoArrayList.get(6), userInfoArrayList.get(7), userInfoArrayList.get(8), userInfoArrayList.get(9), userInfoArrayList.get(10));
						
						// closing current page
						stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		            	stage.close();

		            	// opening new page
		            	CertifiedWorkerHomePageController CHPC = new CertifiedWorkerHomePageController();
		            	primaryStage = new Stage();
		            	CHPC.start(primaryStage);
						
						
	
						break;
	
					case "branch manager":
						
						// send to server to update is_logged_in to 1 
						userLoggedInMessage.set(0, "User Logged In");
						userLoggedInMessage.set(1, userName);
						ClientUI.chat.accept(userLoggedInMessage);
			
						
						
						// send to server to get user info after a successfully log in
						userLoggedInInfo.set(0, "Get User Info");
						userLoggedInInfo.set(1, userName);
						ClientUI.chat.accept(userLoggedInInfo);
						
						
						// getting User info after login
						userInfoArrayList = ChatClient.inputList;
						
						ChatClient.user = new User(userInfoArrayList.get(1), userInfoArrayList.get(2), userInfoArrayList.get(3), userInfoArrayList.get(4),userInfoArrayList.get(5), userInfoArrayList.get(6), userInfoArrayList.get(7), userInfoArrayList.get(8), userInfoArrayList.get(9), userInfoArrayList.get(10));
						
						// closing current page
						stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		            	stage.close();

		            	// opening new page
		            	BranchManagerHomePageController BMHP = new BranchManagerHomePageController();
		            	primaryStage = new Stage();
		            	BMHP.start(primaryStage);
	
						break;
					
					case "CEO":
						
						// send to server to update is_logged_in to 1 
						userLoggedInMessage.set(0, "User Logged In");
						userLoggedInMessage.set(1, userName);
						ClientUI.chat.accept(userLoggedInMessage);
			
						
						
						// send to server to get user info after a successfully log in
						userLoggedInInfo.set(0, "Get User Info");
						userLoggedInInfo.set(1, userName);
						ClientUI.chat.accept(userLoggedInInfo);
						
						
						// getting User info after login
						userInfoArrayList = ChatClient.inputList;
						
						ChatClient.user = new User(userInfoArrayList.get(1), userInfoArrayList.get(2), userInfoArrayList.get(3), userInfoArrayList.get(4),userInfoArrayList.get(5), userInfoArrayList.get(6), userInfoArrayList.get(7), userInfoArrayList.get(8), userInfoArrayList.get(9), userInfoArrayList.get(10));
						
						// closing current page
						stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		            	stage.close();

		            	// opening new page
		            	CEOHomePageController CHP = new CEOHomePageController();
		            	primaryStage = new Stage();
		            	CHP.start(primaryStage);
						
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
