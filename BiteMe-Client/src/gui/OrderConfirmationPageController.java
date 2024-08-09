package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.ChosenItem;
import common.Customer;
import common.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
    
    @FXML
    private Label refundStatusMessageLabel;
    
    private ObservableList<ChosenItem> cartItems;
    
    
    private static final String SALAD_CATEGORY = "salad";
    private static final String MAIN_DISH_CATEGORY = "main dish";
    private static final String DESSERT_CATEGORY = "dessert";
    private static final String DRINK_CATEGORY = "drink";

    private ArrayList<String> saladCategoryItems;
    private ArrayList<String> mainDishCategoryItems;
    private ArrayList<String> dessertCategoryItems;
    private ArrayList<String> drinkCategoryItems;

    
    

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
    void ConfirmOrderOnClickAction(ActionEvent event) throws Exception {
        //////////////////////////////////////////////////////
        // Update the currentOrder with the new total price //
        //////////////////////////////////////////////////////
        ChatClient.currentOrder.setPrice(String.valueOf(OrderTypePageController.totalPrice));
        ChatClient.currentOrder.setDesired_time(OrderTypePageController.desired_time);
        
        if (orderAddressValueLabel.getText() == null) {
        	ChatClient.currentOrder.setDelivery_address(null);
		} else {
			ChatClient.currentOrder.setDelivery_address(orderAddressValueLabel.getText());
		}
        
        System.err.println("ChatClient.customer.getIs_eligible_for_refund(): " + ChatClient.customer.getIs_eligible_for_refund());
        
        if (ChatClient.customer.getIs_eligible_for_refund().equals("1")) {
        	
        	// send a message to server for removing the customer privilege of refund
        	ArrayList<String> changeCustomerRefundStatus = new ArrayList<String>();
        	
        	changeCustomerRefundStatus.add("Change Customer Refund Status To Zero");
        	changeCustomerRefundStatus.add(ChatClient.customer.getId());
        	
        	ClientUI.chat.accept(changeCustomerRefundStatus);
		}

        // Sending message to server to get the last order's id and getting back a new one
        ArrayList<String> getOrderNewIdMessage = new ArrayList<String>();
        getOrderNewIdMessage.add("Get New Order Id");
        
        System.err.println("getOrderNewIdMessage: " + getOrderNewIdMessage);
        
        ClientUI.chat.accept(getOrderNewIdMessage);

        @SuppressWarnings("unchecked")
		ArrayList<String> orderNewId = (ArrayList<String>) ChatClient.inputList;
        
        System.err.println("after getting respone from server orderNewId: " + orderNewId.get(0));
        

        ChatClient.currentOrder.setOrder_id(orderNewId.get(0));

        System.err.println("currentOrder: " + ChatClient.currentOrder);

        // Sending message to server to save the new order
        ArrayList<String> saveOrderMessageArrayList = new ArrayList<String>();
        saveOrderMessageArrayList.add("Save Order");
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getOrder_id());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getRestaurant_id());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getUser_id());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getDate());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getDesired_time());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getArrival_time());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getPrice());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getRestaurant_confirmed_receiving());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getCustomer_confirmed_receiving());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getIs_ready());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getIs_late());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getOrder_type());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getOrder_recieving_method());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getDelivery_address());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getRecipient_name());
        saveOrderMessageArrayList.add(ChatClient.currentOrder.getRecipient_phone_number());

        System.out.println("saveOrderMessageArrayList: " + saveOrderMessageArrayList); 
        ClientUI.chat.accept(saveOrderMessageArrayList);
        
        ArrayList<String> orderSavingStatuStrings = (ArrayList<String>) ChatClient.inputList;
        
        
        
//        // Print results for verification
//        System.out.println(saladCategoryItems);
//        System.out.println(mainDishCategoryItems);
//        System.out.println(dessertCategoryItems);
//        System.out.println(drinkCategoryItems);
        
   
        
		if (orderSavingStatuStrings.get(0).equals("Order Saved")) {

			// Create a confirmation alert for leaving the Menu Page
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Order Status");
			alert.setHeaderText("Thank You for ordering ^_^");
			alert.setContentText("Order Saved Successfully.");

			// Customizing buttons
			ButtonType doneButton = new ButtonType("Done");

			alert.getButtonTypes().setAll(doneButton);

			// if order saved in database

			ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

			// Handle the result
			if (result == doneButton) {
				System.out.println("User chose DONE");
				
				
				// Count items by category and store in ArrayLists and send messages to save the ArrayList in the SQL table
		        countItemsByCategory();

				// Close the current page
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();

				// Open OrderRestaurantListPage
				CustomerHomePageController CHP = new CustomerHomePageController();
				Stage primaryStage = new Stage();
				CHP.start(primaryStage);

			}
		}
			else {
				// Create a confirmation alert for leaving the Menu Page
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Order Status");
				alert.setHeaderText("Cannot Save Order Unfortunatly.");
				alert.setContentText("Please try again later.");

				// Customizing buttons
				ButtonType doneButton = new ButtonType("Done");

				alert.getButtonTypes().setAll(doneButton);

				// if order saved in database

				ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

				// Handle the result
				if (result == doneButton) {
					System.out.println("User chose DONE, Order not saved and user still in page");
				}
			}
		

		
	}

	// function to count how much items in each Category, and sending to the server to save in DB
	private void countItemsByCategory() {
		// Initialize counts
		int saladCount = 0;
		int mainDishCount = 0;
		int dessertCount = 0;
		int drinkCount = 0;

		// Count items in the cart
		for (ChosenItem chosenItem : ChatClient.cart) {
			String category = chosenItem.getItemCategory();
			switch (category) {
			case SALAD_CATEGORY:
				saladCount++;
				break;
			case MAIN_DISH_CATEGORY:
				mainDishCount++;
				break;
			case DESSERT_CATEGORY:
				dessertCount++;
				break;
			case DRINK_CATEGORY:
				drinkCount++;
				break;
			default:
				// Handle unknown categories if needed
				break;
			}
		}

		// Prepare ArrayLists with count information
		saladCategoryItems = new ArrayList<>();
		mainDishCategoryItems = new ArrayList<>();
		dessertCategoryItems = new ArrayList<>();
		drinkCategoryItems = new ArrayList<>();

		// sending message to server to save the salad category's quantity
		saladCategoryItems.add(0, "Save Category Quantity");
		saladCategoryItems.add(1, ChatClient.currentOrder.getOrder_id());
		saladCategoryItems.add(2, SALAD_CATEGORY);
		saladCategoryItems.add(3, String.valueOf(saladCount));
		
		System.out.println("saladCategoryItems: " + saladCategoryItems);

		ClientUI.chat.accept(saladCategoryItems);

		// sending message to server to save the main dish category's quantity
		mainDishCategoryItems.add(0, "Save Category Quantity");
		mainDishCategoryItems.add(1, ChatClient.currentOrder.getOrder_id());
		mainDishCategoryItems.add(2, MAIN_DISH_CATEGORY);
		mainDishCategoryItems.add(3, String.valueOf(mainDishCount));

		System.out.println("mainDishCategoryItems: " + mainDishCategoryItems);

		
		ClientUI.chat.accept(mainDishCategoryItems);

		// sending message to server to save the dessert category's quantity
		dessertCategoryItems.add(0, "Save Category Quantity");
		dessertCategoryItems.add(1, ChatClient.currentOrder.getOrder_id());
		dessertCategoryItems.add(2, DESSERT_CATEGORY);
		dessertCategoryItems.add(3, String.valueOf(dessertCount));

		System.out.println("dessertCategoryItems: " + dessertCategoryItems);

		ClientUI.chat.accept(dessertCategoryItems);

		// sending message to server to save the drink category's quantity
		drinkCategoryItems.add(0, "Save Category Quantity");
		drinkCategoryItems.add(1, ChatClient.currentOrder.getOrder_id());
		drinkCategoryItems.add(2, DRINK_CATEGORY);
		drinkCategoryItems.add(3, String.valueOf(drinkCount));
		
		System.out.println("drinkCategoryItems: " + drinkCategoryItems);


		ClientUI.chat.accept(drinkCategoryItems);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Customer refund status: " + ChatClient.customer.getIs_eligible_for_refund());
        System.out.println(ChatClient.currentOrder);

        if (ChatClient.customer.getIs_eligible_for_refund().equals("1")) {
            OrderTypePageController.totalPrice *= 0.5;
            refundStatusMessageLabel.setText(
                    "Because your recent order was provided to you late.\nWe are giving you a 50% discount on this order.\nSorry and Thank You for your understanding :)");
        } else {
            refundStatusMessageLabel.setText("");
        }

        // TODO Auto-generated method stub
        if (!ChatClient.currentOrder.getOrder_recieving_method().contains("Delivery")) {
            orderAddressLabel.setVisible(false);
            orderAddressValueLabel.setVisible(false);
        }

        // Initialize elements with currentOrder values
        ordersTypeValueLabel.setText(ChatClient.currentOrder.getOrder_type() + " Order");
        System.out.println("desiredTime: " + OrderTypePageController.desired_time);
        
        if (OrderTypePageController.desired_time == null) {
            arrivalTimeValueLabel.setText("As soon as when the order is ready :)");
        } else {
            arrivalTimeValueLabel.setText(OrderTypePageController.desired_time);
        }
        
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

        // Convert currentOrder fields to ArrayList<String>
        ArrayList<String> orderFields = convertOrderToArrayList(ChatClient.currentOrder);
        System.out.println("Order Fields: " + orderFields);
    }

    private ArrayList<String> convertOrderToArrayList(Order order) {
        ArrayList<String> fields = new ArrayList<>();
        fields.add(order.getOrder_id());
        fields.add(order.getRestaurant_id());
        fields.add(order.getUser_id());
        fields.add(order.getDate());
        fields.add(order.getDesired_time());
        fields.add(order.getArrival_time());
        fields.add(order.getPrice());
        fields.add(order.getPrice());
        fields.add(order.getRestaurant_confirmed_receiving());
        fields.add(order.getCustomer_confirmed_receiving());
        fields.add(order.getIs_ready());
        fields.add(order.getIs_late());
        fields.add(order.getOrder_type());
        fields.add(order.getOrder_recieving_method());
        fields.add(order.getRecipient_name());
        fields.add(order.getRecipient_phone_number());
        fields.add(order.getDelivery_address());
        return fields;
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
