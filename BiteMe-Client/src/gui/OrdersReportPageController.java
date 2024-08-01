package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Order;
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
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class OrdersReportPageController implements Initializable {

    @FXML
    private TableView<?> ordersReportTableView;

    @FXML
    private Label branchnameLabel;

    @FXML
    private Label restaurantnameLabel;

    @FXML
    private Label OodersReportLabel;

    @FXML
    private Button finishOrdersReportButton;
    
    

    @FXML
    void finishOrdersReportButtonOnClickAction(ActionEvent event) throws Exception {

    	
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();

    	TypesOfReportsPageController TORP = new TypesOfReportsPageController();
    	Stage primaryStage = new Stage();
    	TORP.start(primaryStage);
    }



	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		 FXMLLoader loader = new FXMLLoader();
	     Parent root = loader.load(getClass().getResource("/gui/OrdersReportPage.fxml").openStream());
	     Scene scene = new Scene(root);
	     primaryStage.setTitle("IPConnectionPage");
	     primaryStage.setScene(scene);
	     primaryStage.show();
	        
	       // scene.getStylesheets().add(getClass().getResource("/gui/BranchManagerHomePage.css").toExternalForm());
		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// getting the restaurants info from DB, for easily usage in customer's interface
		ArrayList<String> msg = new ArrayList<>();
		msg.add(0, "Get Orders Info");
		//need to send month and restaurant name 

		ClientUI.chat.accept(msg);
		ChatClient.OrdersInfo = (ArrayList<Order>) ChatClient.inputList;

		System.out.println("result from Server, in OrdersReportPageController: " + ChatClient.OrdersInfo);
		
	}
    
    

}
