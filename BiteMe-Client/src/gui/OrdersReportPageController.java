package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.OrderDetails;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrdersReportPageController implements Initializable {

    @FXML
    private TableView<OrderDetails> ordersReportTableView;

    @FXML
    private TableColumn<OrderDetails, String> OrderIdTableColumn;

    @FXML
    private TableColumn<OrderDetails, String> MainDishTableColumn;

    @FXML
    private TableColumn<OrderDetails, String> DrinksTableColumn;

    @FXML
    private TableColumn<OrderDetails, String> SaladTableColumn;

    @FXML
    private TableColumn<OrderDetails, String> DessertTableColumn;
    
    @FXML
    private Label branchnameLabel;

    @FXML
    private Label restaurantnameLabel;

    @FXML
    private Label OodersReportLabel;

    @FXML
    private Button finishOrdersReportButton;

    private ObservableList<OrderDetails> orderDetailsList = FXCollections.observableArrayList();

    @FXML
    void finishOrdersReportButtonOnClickAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        TypesOfReportsPageController TORP = new TypesOfReportsPageController();
        Stage primaryStage = new Stage();
        TORP.start(primaryStage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> msg = new ArrayList<>();
        msg.add(0, "Get OrdersReport Info");
        msg.add(1, ChatClient.chosenRestaurantReport.getRestaurant_name());
        msg.add(2, ChatClient.chosenRestaurantReport.getMonth());
        msg.add(3, ChatClient.chosenRestaurantReport.getYear());
        ClientUI.chat.accept(msg);
        
        ArrayList<OrderDetails> OrdersInfo = (ArrayList<OrderDetails>) ChatClient.inputList;
        System.out.println("result from Server, in OrdersReportPageController: " + OrdersInfo);

        // Set up the TableView columns
        OrderIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        MainDishTableColumn.setCellValueFactory(new PropertyValueFactory<>("mainDish"));
        DrinksTableColumn.setCellValueFactory(new PropertyValueFactory<>("drinks"));
        SaladTableColumn.setCellValueFactory(new PropertyValueFactory<>("salad"));
        DessertTableColumn.setCellValueFactory(new PropertyValueFactory<>("dessert"));

        // Populate the TableView with data from the server
        orderDetailsList.addAll(OrdersInfo);
        ordersReportTableView.setItems(orderDetailsList);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/OrdersReportPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Orders Report Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}