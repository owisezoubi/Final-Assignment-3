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
    void LoginButtonOnClickAction(ActionEvent event) {

    }

    @FXML
    void ClearFieldsButtonOnClickAction(ActionEvent event) {
    	userNameTextField.clear();
    	passwordField.clear();
    }

    @FXML
    void exitButtonOnClickAction(ActionEvent event) {
    	System.exit(0);

    }
	

}
