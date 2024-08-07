package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import common.ChosenItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrderConfirmationPageController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button confirmOrderButton;

    @FXML
    private TableView<ChosenItem> cartTableView;
    
    @FXML
    private TableColumn<ChosenItem, String> itemNameColumn;

    @FXML
    private TableColumn<ChosenItem, String> itemPriceColumn;

    @FXML
    private TableColumn<ChosenItem, String> itemCategoryColumn;

    @FXML
    private TableColumn<ChosenItem, String> itemAdditionsColumn;


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
    private Label orderAddressLabel;

    @FXML
    private Label orderAddressValueLabel;
    
    
    private ObservableList<ChosenItem> cartItems;

    @FXML
    void BackButtonOnClickAction(ActionEvent event) throws Exception {
    	// Subtracting delivery fee if user going back to OrderDeliveryPage
    	OrderTypePageController.totalPrice -= OrderDeliveryPageController.deliveryFee;
    	
    	 // Closing current page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
        // Opening new page
        OrderDeliveryPageController ODP = new OrderDeliveryPageController();
        Stage primaryStage = new Stage();
        ODP.start(primaryStage);
    }

	@FXML
	void ConfirmOrderOnClickAction(ActionEvent event) {
		//////////////////////////////////////////////////////
		// Update the currentOrder with the new total price //
		//////////////////////////////////////////////////////
		ChatClient.currentOrder.setPrice(String.valueOf(OrderTypePageController.totalPrice));

		
		 System.err.println("currentOrder: " + ChatClient.currentOrder);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if (!ChatClient.currentOrder.getOrder_recieving_method().contains("Delivery")) {
			orderAddressLabel.setVisible(false);
			orderAddressValueLabel.setVisible(false);
			
		}
		
		//initialize elements with currentOrder values
		ordersTypeValueLabel.setText(ChatClient.currentOrder.getOrder_type() + " Order");
		arrivalTimeValueLabel.setText(ChatClient.currentOrder.getDesired_time());
		orderRecievingMethodInfoLabel.setText(ChatClient.currentOrder.getOrder_recieving_method());
		orderAddressValueLabel.setText(ChatClient.currentOrder.getDelivery_address());
		
		
		
		recipientNameValueLabel.setText(ChatClient.currentOrder.getRecipient_name());
		recipientPhoneNumberValueLabel.setText(ChatClient.currentOrder.getRecipient_phone_number());
		totalPriceToPayLabelValueLabel.setText(OrderTypePageController.totalPrice + " ₪");
		
		
		
		
		// Initialize TableView columns
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        // Custom cell factory for additions column
        itemAdditionsColumn.setCellValueFactory(new PropertyValueFactory<>("itemAdditions"));
        itemAdditionsColumn.setCellFactory(new Callback<TableColumn<ChosenItem, String>, TableCell<ChosenItem, String>>() {
            @Override
            public TableCell<ChosenItem, String> call(TableColumn<ChosenItem, String> param) {
                return new TableCell<ChosenItem, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null) {
                            setText(null);
                        } else {
                            ChosenItem chosenItem = (ChosenItem) getTableRow().getItem();
                            if (chosenItem != null) {
                                ArrayList<String> additions = chosenItem.getItem_additions();
                                if (additions != null) {
                                    setText(String.join(", ", additions));
                                } else {
                                    setText(""); // Handle the case where additions are null
                                }
                            } else {
                                setText(""); // Handle the case where chosenItem is null
                            }
                        }
                    }
                };
            }
        });


        // Initialize cart items and load into TableView
        cartItems = FXCollections.observableArrayList(ChatClient.cart);
        cartTableView.setItems(cartItems);

		
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
