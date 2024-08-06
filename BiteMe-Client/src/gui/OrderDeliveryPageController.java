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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrderDeliveryPageController implements Initializable {

    @FXML
    private Label orderDeliveryPageLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

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
    private TableColumn<ChosenItem, Void> deleteColumn;

    @FXML
    private Label recievingMethodLabel;

    @FXML
    private ComboBox<String> recievingMethodComboBox;

    @FXML
    private Label deliveryTypeLabel;

    @FXML
    private ComboBox<String> deliveryTypeComboBox;

    @FXML
    private Label totalPriceValueLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label DeliveryAddressLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label orderNameLabel;

    @FXML
    private Label deliveryPriceLabel;

    @FXML
    private TextField deliveryAddressTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField orderNameTextField;
    
    @FXML
    private Label numberOfParticipantsLabel;

    @FXML
    private ComboBox<String> numberOfParticipantsComboBox;

    public String deliveryMethodString;
    
    @FXML
    void BackButtonOnClickAction(ActionEvent event) throws Exception {
        // Closing current page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
        // Opening new page
        OrderTypePageController OTP = new OrderTypePageController();
        Stage primaryStage = new Stage();
        OTP.start(primaryStage);
    }

    @FXML
    void NextButtonOnClickAction(ActionEvent event) throws Exception {
        // Validate required fields
        if ((deliveryTypeComboBox.isDisabled() && deliveryTypeComboBox.getValue() == null) ||
        	(numberOfParticipantsComboBox.isVisible() && numberOfParticipantsComboBox.getValue() == null) ||
            (!deliveryAddressTextField.isDisabled() && deliveryAddressTextField.getText().isEmpty()) ||
             phoneNumberTextField.getText().isEmpty() ||
             orderNameTextField.getText().isEmpty()) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        } else if (ChatClient.cart.isEmpty()) {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Cart is Empty, please go back to Menu Page and add an Item.");
            alert.showAndWait();
            return;
		}
        
        System.out.println("ComboBox: " + recievingMethodComboBox.getValue());
        deliveryMethodString = "";

        // Updating order_recieving_method to current order
        if ("Pick Up".equals(recievingMethodComboBox.getValue())) {
            deliveryMethodString = recievingMethodComboBox.getValue();
        } else {
            deliveryMethodString = recievingMethodComboBox.getValue() + ", " + deliveryTypeComboBox.getValue();
        }
        
        ChatClient.currentOrder.setOrder_recieving_method(deliveryMethodString);

        // Calculate delivery price
        if ("Shared".equals(deliveryTypeComboBox.getValue())) {
            int numParticipants = Integer.parseInt(numberOfParticipantsComboBox.getValue());
            if (numParticipants == 1) {
                deliveryPriceLabel.setText("+ 25 ₪");
            } else if (numParticipants == 2) {
                deliveryPriceLabel.setText("+ 20 ₪");
            } else if (numParticipants >= 3) {
                deliveryPriceLabel.setText("+ 15 ₪");
            }
        } else {
            deliveryPriceLabel.setText("");
        }

        // Update total price
        updateTotalPrice(null);

        System.out.println("currentOrder: " + ChatClient.currentOrder);

        // Can't move to next page if cart is empty
        if (ChatClient.cart.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null); // You can set a header text if needed
            alert.setContentText("The Cart is Empty.\nPlease get back to Menu Page and choose an Item.");

            // Show the alert dialog
            alert.showAndWait();
        } else {
            // Closing current page
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Opening new page
            OrderConfirmationPageController OCP = new OrderConfirmationPageController();
            Stage primaryStage = new Stage();
            OCP.start(primaryStage);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//hiding elements
    	numberOfParticipantsLabel.setVisible(false);
        numberOfParticipantsComboBox.setVisible(false);
        deliveryPriceLabel.setText("");
    	
    	
        // Initialize TableView columns
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

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
                                    setText("");
                                }
                            } else {
                                setText("");
                            }
                        }
                    }
                };
            }
        });

        deleteColumn.setCellFactory(new Callback<TableColumn<ChosenItem, Void>, TableCell<ChosenItem, Void>>() {
            @Override
            public TableCell<ChosenItem, Void> call(TableColumn<ChosenItem, Void> param) {
                return new TableCell<ChosenItem, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            ChosenItem chosenItem = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(chosenItem);
                            ChatClient.cart.remove(chosenItem);
                            updateTotalPrice(chosenItem); 
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : deleteButton);
                        setText(null);
                    }
                };
            }
        });

        // Load cart items into TableView
        ObservableList<ChosenItem> cartItems = FXCollections.observableArrayList(ChatClient.cart);
        cartTableView.setItems(cartItems);

        // Update total price
        updateTotalPrice(null);

        // Configure receiving Method ComboBox
        recievingMethodComboBox.getItems().addAll("Pick Up", "Delivery");
        recievingMethodComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            handleRecievingMethodChange(newVal);
        });

        // Configure Delivery Type ComboBox
        deliveryTypeComboBox.getItems().addAll("Regular", "Shared", "Robot");
        deliveryTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            handleDeliveryTypeChange(newVal);
        });

        // Configure Number of Participants ComboBox
        ObservableList<String> participantOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= 20; i++) {
            participantOptions.add(String.valueOf(i));
        }
        numberOfParticipantsComboBox.setItems(participantOptions);
        numberOfParticipantsComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            handleNumberOfParticipantsChange(newVal);
        });
    }

    private void handleRecievingMethodChange(String newMethod) {
        if ("Pick Up".equals(newMethod)) {
            disableDeliveryFields(true);
            deliveryPriceLabel.setText("");
        } else if ("Delivery".equals(newMethod)) {
            disableDeliveryFields(false);
        }
    }

    private void disableDeliveryFields(boolean disable) {
        deliveryAddressTextField.setDisable(disable);
        numberOfParticipantsComboBox.setDisable(disable);
        deliveryTypeComboBox.setDisable(disable);
        
        if (disable) {
            deliveryAddressTextField.setStyle("-fx-background-color: #d3d3d3;"); // Greyed out background
            numberOfParticipantsComboBox.setStyle("-fx-background-color: #d3d3d3;"); // Greyed out background
            deliveryTypeComboBox.setStyle("-fx-background-color: #d3d3d3;"); // Greyed out background
        } else {
            deliveryAddressTextField.setStyle("-fx-background-color: white;");
            numberOfParticipantsComboBox.setStyle("-fx-background-color: white;");
            deliveryTypeComboBox.setStyle("-fx-background-color: white;");
        }
    }

    private void handleDeliveryTypeChange(String newType) {
    	
    	
    	
    	
        if ("Shared".equals(newType)) {
        	
        	numberOfParticipantsComboBox.setValue(null);
        	
            numberOfParticipantsLabel.setVisible(true);
            numberOfParticipantsComboBox.setVisible(true);
        } else {
            numberOfParticipantsLabel.setVisible(false);
            numberOfParticipantsComboBox.setVisible(false);
            deliveryPriceLabel.setText("");
        }
        
        if ("Regular".equals(newType)) {
			deliveryPriceLabel.setText("+ 25 ₪");
		} 
    }

    private void handleNumberOfParticipantsChange(String newValue) {
		if ("Shared".equals(deliveryTypeComboBox.getValue())) {
			if (newValue != null) {
				int numParticipants = Integer.parseInt(newValue);
				if (numParticipants == 1) {
					deliveryPriceLabel.setText("+ 25 ₪");
				} else if (numParticipants == 2) {
					deliveryPriceLabel.setText("+ 20 ₪");
				} else if (numParticipants >= 3) {
					deliveryPriceLabel.setText("+ 15 ₪");
				}
			}
		}
    }

    private void updateTotalPrice(ChosenItem deletedItem) {
        double totalPrice = 0.0;
        try {
            totalPrice = Double.parseDouble(ChatClient.currentOrder.getPrice());
        } catch (NumberFormatException e) {
            System.err.println("Invalid total price format: " + e.getMessage());
        }

        // If an item is deleted, subtract its price from the total
        if (deletedItem != null) {
            try {
                double itemPrice = Double.parseDouble(deletedItem.getItem().getItem_price());
                totalPrice -= itemPrice;
            } catch (NumberFormatException e) {
                System.err.println("Invalid item price format: " + e.getMessage());
            }
        }

        // Update the total price based on the delivery type
        if (deliveryPriceLabel.getText().contains("25 ₪")) {
            totalPrice += 25;
        } else if (deliveryPriceLabel.getText().contains("20 ₪")) {
            totalPrice += 20;
        } else if (deliveryPriceLabel.getText().contains("15 ₪")) {
            totalPrice += 15;
        }

        // Update the total price label
        totalPriceValueLabel.setText(String.format("%.2f ₪", totalPrice));

        // Update the currentOrder with the new total price
        ChatClient.currentOrder.setPrice(String.valueOf(totalPrice));
    }

    
    
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrderDeliveryPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Order Delivery Page");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add(getClass().getResource("/gui/OrderDeliveryPage.css").toExternalForm());
    }
    
}



