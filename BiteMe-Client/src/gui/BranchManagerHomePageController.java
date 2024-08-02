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

public class BranchManagerHomePageController implements Initializable {

    @FXML
    private Button addNewCustomerButton;

    @FXML
    private Button showReportBranchManagerButton;

    @FXML
    private Button logoutBranchManagerButton;

    @FXML
    private Label greetingsBranchManagerLabel;

    @FXML
    void addNewCustomerBranchManagerOnClickAction(ActionEvent event) {

    }

    @FXML
    void logoutBranchManagerOnClickAction(ActionEvent event) throws Exception {
    	
    	String userName = ChatClient.user.getUser_name();
    	
    	ArrayList<String> msg = new ArrayList<>();
		msg.add(0, "User Logged Out");
		msg.add(1, userName);

		ClientUI.chat.accept(msg);
    	
		// logging out - getting back to loginPage
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();

    	LoginPageController LIF = new LoginPageController();
    	Stage primaryStage = new Stage();
    	LIF.start(primaryStage);
    	
    }

    @FXML
    void showReportBranchManagerOnClickAction(ActionEvent event) throws Exception {
    	
    	// closing current page and opening TypeOfReportPage
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();

    	TypesOfReportsPageController TOFP = new TypesOfReportsPageController();
    	Stage primaryStage = new Stage();
    	TOFP.start(primaryStage);
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/BranchManagerHomePage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("IPConnectionPage");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/BranchManagerHomePage.css").toExternalForm());
    }

}
