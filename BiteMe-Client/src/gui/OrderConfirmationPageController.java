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
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class OrderConfirmationPageController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button confirmOrderButton;

    @FXML
    private TableView<?> cartTableView;

    @FXML
    private Label ordersTypeLabel;

    @FXML
    private Label arrivalTimeLabel;

    @FXML
    private Label orderRecievingMethodLabel;

    @FXML
    private Label orderConfirmationPageLabel;

    @FXML
    private Label recipientNameLabe;

    @FXML
    private Label recipientPhoneNumberLabel;

    @FXML
    private Label totalPriceToPayLabel;

    @FXML
    private Label totalPriceToPayLabelValueLabel;

    @FXML
    private Label ordersTypeValueLabel;

    @FXML
    private Label arrivalTimeValueLabel;

    @FXML
    private Label orderRecievingMethodInfoLabel;

    @FXML
    private Label recipientNameValueLabel;

    @FXML
    private Label recipientPhoneNumberValueLabel;

    @FXML
    void BackButtonOnClickAction(ActionEvent event) {

    }

    @FXML
    void ConfirmOrderOnClickAction(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/OrderConfirmationPage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("OrderConfirmationPageController");
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.getStylesheets().add(getClass().getResource("/gui/OrderConfirmationPage.css").toExternalForm());
	}

}
