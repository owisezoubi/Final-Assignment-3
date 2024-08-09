package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class CustomerOrderConfirmationPageController implements Initializable {

    @FXML
    private TableView<Order> ordersTableView;

    @FXML
    private TableColumn<Order, String> orderIdColumn;

    @FXML
    private TableColumn<Order, String> restaurantNameColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, String> orderPriceColumn;

    @FXML
    private TableColumn<Order, String> orderTypeColumn;

    @FXML
    private TableColumn<Order, String> receivingMethodColumn;

    @FXML
    private TableColumn<Order, String> addressColumn;

    @FXML
    private TableColumn<Order, Void> actionColumn; // Use Void for button column

    @FXML
    private Label confirmOrdersPageLabel;

    @FXML
    private Button backButton;

    @FXML
    void BackButtonOnClickAction(ActionEvent event) throws Exception {
        // Going back to home page of customer
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        CustomerHomePageController CHP = new CustomerHomePageController();
        Stage primaryStage = new Stage();
        CHP.start(primaryStage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	// send message to server with customerID to get the orders that he did
    	ArrayList<String> getOrdersToConfirmMessageArrayList = new ArrayList<String>();
    	
    	getOrdersToConfirmMessageArrayList.add("Get Customer Orders To Confirm");
    	getOrdersToConfirmMessageArrayList.add(ChatClient.customer.getId());
    	
    	ClientUI.chat.accept(getOrdersToConfirmMessageArrayList);
    	
        // Initialize the columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant_name")); // Update with actual name if available
        itemsColumn.setCellValueFactory(new PropertyValueFactory<>("items")); // Custom implementation needed
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("order_price"));
        orderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("order_type"));
        receivingMethodColumn.setCellValueFactory(new PropertyValueFactory<>("receiving_method"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Create a custom cell factory for the action column
        actionColumn.setCellFactory(new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(TableColumn<Order, Void> param) {
                return new TableCell<Order, Void>() {
                    private final Button confirmButton = new Button("Confirm");

                    {
                        confirmButton.setOnAction(event -> {
                            // Implement confirmation logic here
                            Order order = getTableView().getItems().get(getIndex());
                            confirmOrder(order);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : confirmButton);
                        setText(null);
                    }
                };
            }
        });

        // Load orders
        loadOrders();
    }

    private void loadOrders() {
        ArrayList<Order> ordersList = getOrdersFromChatClient(); // Replace with actual method to fetch orders
        ObservableList<Order> ordersObservableList = FXCollections.observableArrayList(ordersList);
        ordersTableView.setItems(ordersObservableList);
    }

    private ArrayList<Order> getOrdersFromChatClient() {
        // Placeholder method to simulate fetching orders from ChatClient
        // Implement actual logic to fetch and return orders
        return new ArrayList<>();
    }

    private void confirmOrder(Order order) {
        // Implement the logic for confirming the order
        System.out.println("Order confirmed: " + order.getOrder_id());
    }
    
    
    
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/CustomerOrderConfirmationPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("CustomerOrderConfirmationPage");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/CustomerOrderConfirmationPage.css").toExternalForm());
    }
}
