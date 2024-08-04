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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    void NextButtonOnClickAction(ActionEvent event) {
        // Implement next button logic here
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                            ArrayList<String> additions = chosenItem.getItem_additions();
                            if (additions != null) {
                                setText(String.join(", ", additions));
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
                            updateTotalPrice(); 
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
        updateTotalPrice();

        // Configure Recieving Method ComboBox
        recievingMethodComboBox.getItems().addAll("Pick-Up", "Delivery");
        recievingMethodComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            handleRecievingMethodChange(newVal);
        });

        // Configure Delivery Type ComboBox
        deliveryTypeComboBox.getItems().addAll("Regular", "Shared", "via Robot");
        deliveryTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            handleDeliveryTypeChange(newVal);
        });
    }

    private void handleRecievingMethodChange(String newMethod) {
        if ("Pick-Up".equals(newMethod)) {
            disableDeliveryFields(true);
            deliveryPriceLabel.setText("");
        } else if ("Delivery".equals(newMethod)) {
            disableDeliveryFields(false);
        }
    }

    private void disableDeliveryFields(boolean disable) {
        deliveryAddressTextField.setDisable(disable);
        phoneNumberTextField.setDisable(disable);
        orderNameTextField.setDisable(disable);
        deliveryTypeComboBox.setDisable(disable);
        
        if (disable) {
            deliveryAddressTextField.setStyle("-fx-text-fill: gray;");
            phoneNumberTextField.setStyle("-fx-text-fill: gray;");
            orderNameTextField.setStyle("-fx-text-fill: gray;");
            deliveryTypeComboBox.setStyle("-fx-text-fill: gray;");
        } else {
            deliveryAddressTextField.setStyle("");
            phoneNumberTextField.setStyle("");
            orderNameTextField.setStyle("");
            deliveryTypeComboBox.setStyle("");
        }
    }

    private void handleDeliveryTypeChange(String deliveryType) {
        if ("Regular".equals(deliveryType)) {
            deliveryPriceLabel.setText("+ 25 ₪");
        } else if ("Shared".equals(deliveryType)) {
            deliveryPriceLabel.setText("");
        } else if ("via Robot".equals(deliveryType)) {
            deliveryPriceLabel.setText("");
        }
    }

    private void updateTotalPrice() {
        double totalPrice = ChatClient.cart.stream()
                .mapToDouble(item -> Double.parseDouble(item.getItemPrice()))
                .sum();
        if (deliveryPriceLabel.getText().contains("25 ₪")) {
            totalPrice += 25;
        }
        totalPriceValueLabel.setText(String.format("%.2f ₪", totalPrice));
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
