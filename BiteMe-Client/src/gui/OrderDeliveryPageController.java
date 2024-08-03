package gui;

import java.net.URL;
import java.util.ResourceBundle;

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

public class OrderDeliveryPageController implements Initializable {

	@FXML
	private Label orderDeliveryPageLabel;

	@FXML
	private Button nextButton;

	@FXML
	private Button backButton;

	@FXML
	void BackButtonOnClickAction(ActionEvent event) throws Exception{
		 // Closing current page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Opening new page
        OrderTypePageController OTP = new OrderTypePageController();
        Stage primaryStage = new Stage();
        OTP.start(primaryStage);
	}

	@FXML
	void NextButtonOnClickAction(ActionEvent event) {

	}

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrderDeliveryPage.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Restaurant Menu Page");
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.getStylesheets().add(getClass().getResource("/gui/OrderDeliveryPage.css").toExternalForm());
	}
}
