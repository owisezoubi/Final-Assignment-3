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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrderTypePageController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private ComboBox<String> ordersTypeComboBox;

    @FXML
    private Label priceTextField;

    @FXML
    private ComboBox<String> arrivalTimeInHoursComboBox;

    @FXML
    private ComboBox<String> arrivalTimeInMinutesComboBox;

    @FXML
    private TableView<ChosenItem> cartView;

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

    private ObservableList<ChosenItem> cartItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        // Add delete button column
        deleteColumn.setCellFactory(new Callback<TableColumn<ChosenItem, Void>, TableCell<ChosenItem, Void>>() {
            @Override
            public TableCell<ChosenItem, Void> call(TableColumn<ChosenItem, Void> param) {
                return new TableCell<ChosenItem, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            ChosenItem chosenItem = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(chosenItem);
                            ChatClient.cart.remove(chosenItem); // Remove from ChatClient.cart
                            updateTotalPrice(); // Update total price after removal
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

        // Initialize cart items and load into TableView
        cartItems = FXCollections.observableArrayList(ChatClient.cart);
        cartView.setItems(cartItems);

        // Update total price initially
        updateTotalPrice();
        
        ordersTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
        	updateTotalPrice();
        	ChatClient.currentOrder.setOrder_type(newVal.toLowerCase());
        	
        });

        // Configure ComboBoxes for order type and arrival times
        configureOrderTypeComboBox();
        configureArrivalTimeComboBoxes();
    }

    private void configureOrderTypeComboBox() {
        ordersTypeComboBox.getItems().addAll("Normal", "Early");
    }

    private void configureArrivalTimeComboBoxes() {
        // Populate minutes combo box
        for (int i = 0; i < 60; i++) {
            arrivalTimeInMinutesComboBox.getItems().add(String.format("%02d", i));
        }
        arrivalTimeInMinutesComboBox.getSelectionModel().selectFirst();

        ordersTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            arrivalTimeInHoursComboBox.getItems().clear();
            int currentHour = java.time.LocalTime.now().getHour();
            if ("Normal".equals(newVal)) {
                for (int i = currentHour; i < 24; i++) {
                    arrivalTimeInHoursComboBox.getItems().add(String.format("%02d", i));
                }
            } else if ("Early".equals(newVal)) {
                for (int i = currentHour + 2; i < 24; i++) {
                    arrivalTimeInHoursComboBox.getItems().add(String.format("%02d", i));
                }
            }
            arrivalTimeInHoursComboBox.getSelectionModel().selectFirst();
        });
    }

    private void updateTotalPrice() {

        double totalPrice = 0.0;
        for (ChosenItem item : cartItems) {
            String itemPriceStr = item.getItemPrice();
            if (itemPriceStr != null && !itemPriceStr.isEmpty()) {
                try {
                    totalPrice += Double.parseDouble(itemPriceStr);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid price format for item: " + itemPriceStr);
                }
            } else {
                System.err.println("Item price is null or empty for item: " + item.getItemName());
            }
        }
        
        
        System.out.println("total price before discount: " + totalPrice);
        
     
        if ("Early".equals(ordersTypeComboBox.getValue())) {
            totalPrice *= 0.9; // Apply 10% discount
            System.out.println("entered");
        }
        System.out.println("total price after discount: " + totalPrice);
        
        priceTextField.setText(String.format("%.2f ₪", totalPrice));
        
        ChatClient.currentOrder.setPrice(String.format("%.2f", totalPrice));
        
        System.out.println("total price in ChatClient.currentOrder: " + ChatClient.currentOrder);

    }

    @FXML
    void backButtonOnClickAction(ActionEvent event) throws Exception {
        // Closing current page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Opening new page
        RestaurantMenuPageController RMP = new RestaurantMenuPageController();
        Stage primaryStage = new Stage();
        RMP.start(primaryStage);
    }

    @FXML
    void nextButtonOnClickAction(ActionEvent event) throws Exception {
        if (ChatClient.cart.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("The Cart is Empty.\nPlease get back to Menu Page and choose an Item.");
            alert.showAndWait();
        } else {
            if (ordersTypeComboBox.getValue() == null || arrivalTimeInHoursComboBox.getValue() == null || arrivalTimeInMinutesComboBox.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the data");
                alert.showAndWait();
            } else {
                String orderType = ordersTypeComboBox.getValue();
                ChatClient.currentOrder.setOrder_type(orderType);

                String selectedHour = arrivalTimeInHoursComboBox.getValue();
                String selectedMinute = arrivalTimeInMinutesComboBox.getValue();
                String desiredTime = selectedHour + ":" + selectedMinute;
                ChatClient.currentOrder.setDesired_time(desiredTime);

                
                // Closing current page
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                // Opening new page
                OrderDeliveryPageController ODP = new OrderDeliveryPageController();
                Stage primaryStage = new Stage();
                ODP.start(primaryStage);
            }
        }
        
        String desiredTimeString = arrivalTimeInHoursComboBox + ":" + arrivalTimeInMinutesComboBox;
        
        // load desired_time for currentOrder
        ChatClient.currentOrder.setDesired_time(desiredTimeString);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/OrderTypePage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("OrderTypePage");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add(getClass().getResource("/gui/OrderTypePage.css").toExternalForm());
    }
}
