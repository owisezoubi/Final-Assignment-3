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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CEOHomePageController implements Initializable {

    @FXML
    private Button compraeCeoButton;

    @FXML
    private Button logoutCeoButton;

    @FXML
    private Button showReportCeoButton;

    @FXML
    private Label greetingsCEOLabel;

    @FXML
    private Button quarterlyReportButton;
    
    @FXML
    private ComboBox<String> BranchComboBox;
    
    @FXML
    private Label messageBranchLabel;

   
   
    
    @FXML
    void quarterlyReportButtonOnClickAction(ActionEvent event) throws Exception {
    	
 	String selectedBranch=BranchComboBox.getValue();
    	if(selectedBranch==null)
    		messageBranchLabel.setText("please choose a branch");
    	else {
    	if (selectedBranch.equals("North"))
    		ChatClient.branch="1";
    	else if (selectedBranch.equals("South"))
    		ChatClient.branch="2";
    	else 
    		ChatClient.branch="3";

    	// closing current page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // opening BranchManagerHomePage
        QuarterlyReportPageController QRC = new QuarterlyReportPageController();
        Stage newStage = new Stage();
        QRC.start(newStage);
    	}
    } 
    
    
    @FXML
    void compraeCEOButtonOnClickAction(ActionEvent event) {

    }

    @FXML
    void logoutCEOButtonOnClickAction(ActionEvent event) throws Exception {
    	
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
    void showReportCEOButtonOnClickAction(ActionEvent event) throws Exception {
    	
    	String selectedBranch=BranchComboBox.getValue();
    	if (selectedBranch== null) {
   		 messageBranchLabel.setText("Please choose a branch.");
        }
    	else {
    		if (selectedBranch.equals("North")) {
    			ChatClient.branch="1";
    		}
    		else if (selectedBranch.equals("South")) {
    			ChatClient.branch="2";
    		}
    		else {
    			ChatClient.branch="3";
    		}
    		// closing current page
    		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		stage.close();

    		// opening BranchManagerHomePage
    		TypesOfReportsPageController Ceo = new TypesOfReportsPageController();
    		Stage newStage = new Stage();
    		Ceo.start(newStage);
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

        if (BranchComboBox != null) {
        	BranchComboBox.getItems().addAll("North", "South", "Central");
        }

      
		
	}
	
	
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/CEOHomePage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("IPConnectionPage");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/CEOHomePage.css").toExternalForm());
    }

}