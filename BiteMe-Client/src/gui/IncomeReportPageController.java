package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class IncomeReportPageController  implements Initializable{

    @FXML
    private TableColumn<?, ?> incomeReportTableView;

    @FXML
    private Label incomeReportLabel;

    @FXML
    private Label restaurantnameLabel;

    @FXML
    private Label branchnameLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Button finishIncomeReportButton;
    
    @FXML
    void finishIncomeReportButtonOnClickAction(ActionEvent event) {

    }

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		 FXMLLoader loader = new FXMLLoader();
	        Parent root = loader.load(getClass().getResource("/gui/IncomeReportPage.fxml").openStream());
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

				System.out.println("result from Server, in IncomeReportPageController: " + ChatClient.OrdersInfo);
		
	}
    
    
    
    

}
