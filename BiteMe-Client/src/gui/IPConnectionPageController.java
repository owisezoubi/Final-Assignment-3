package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IPConnectionPageController implements Initializable {
    public static ClientController chat;

    private static String IPaddress;

    @FXML
    private Label ipLabel;

    @FXML
    private TextField ipTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Button connectButton;

    @FXML
    private Label ipMessageLabel;

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try {
    		ipTextField.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/IPConnectionPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("IPConnectionPage");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/IPConnectionPage.css").toExternalForm());
    }

    
    
    
    
    @FXML
    public void connectButtonOnClickAction(ActionEvent event) throws Exception {
       
        if (ipTextField.getText().isEmpty()) {
        	ipMessageLabel.setText("please enter ip address");
            return;
        } else {

        	
        	try {
            	ClientUI.chat = new ClientController(IPConnectionPageController.IPaddress, 5555);
            	
            	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            	stage.close();

            	LoginPageController LIF = new LoginPageController();
            	Stage primaryStage = new Stage();
            	LIF.start(primaryStage);

			} catch (IOException e) {
				System.out.println("Error: Can't setup connection!"+ " Terminating client.");
	        	ipMessageLabel.setText("Connection failed - Invalid IP Address");
			}
        	
        	
        	
        }
    }

    
    
   
    
    
    @FXML
    public void exitButtonOnClickAction(ActionEvent event) throws Exception {
        System.out.println("exit Academic Tool");
        System.exit(0);
    }

    
}