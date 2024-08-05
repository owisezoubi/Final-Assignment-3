package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.OrdersReport;
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
    private TableView<OrdersReport> ordersReportTableView;

    @FXML
    private TableColumn<OrdersReport, String> dateTableColumn;

    @FXML
    private TableColumn<OrdersReport, String> mainDishTableColumn;

    @FXML
    private TableColumn<OrdersReport, String> saladTableColumn;

    @FXML
    private TableColumn<OrdersReport, String> drinkTableColumn;

    @FXML
    private TableColumn<OrdersReport, String> dessertTableColumn;

    @FXML
    private Label branchnameLabel;

    @FXML
    private Label restaurantnameLabel;

    @FXML
    private Label OrdersReportLabel;

    @FXML
    private Button finishOrdersReportButton;
    
    @FXML
    private Label messageOrdersReportPage;
    
    private Stage stage;

    private ObservableList<OrdersReport> ordersReportList = FXCollections.observableArrayList();

    @FXML
    void finishOrdersReportButtonOnClickAction(ActionEvent event) throws Exception {
    	// closing current page
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // opening BranchManagerHomePage
        TypesOfReportsPageController TORP = new TypesOfReportsPageController();
        Stage newStage = new Stage();
        TORP.start(newStage);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        restaurantnameLabel.setText("Restaurant Name: " +ChatClient.chosenRestaurantReport.getRestaurant_name());
        branchnameLabel.setText("Restaurant Name: " +ChatClient.chosenRestaurantReport.getBranch());

        ArrayList<String> msg = new ArrayList<>();
        msg.add(0, "Get OrdersReport Info");
        msg.add(1, ChatClient.chosenRestaurantReport.getRestaurant_name());
        msg.add(2, ChatClient.chosenRestaurantReport.getMonth());
        msg.add(3, ChatClient.chosenRestaurantReport.getYear());
        ClientUI.chat.accept(msg);

        ArrayList<OrdersReport> ordersInfo = (ArrayList<OrdersReport>) ChatClient.inputList;
        if (ordersInfo.isEmpty()) {
        	messageOrdersReportPage.setText("There are no orders for the specified restaurant on the given date.");
        }
        System.out.println("Result from Server, in OrdersReportPageController: " + ordersInfo);

        // Set up the TableView columns
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        mainDishTableColumn.setCellValueFactory(new PropertyValueFactory<>("mainDish"));
        saladTableColumn.setCellValueFactory(new PropertyValueFactory<>("salad"));
        drinkTableColumn.setCellValueFactory(new PropertyValueFactory<>("drink"));
        dessertTableColumn.setCellValueFactory(new PropertyValueFactory<>("dessert"));

        // Populate the TableView with data from the server
        ordersReportList.addAll(ordersInfo);
        ordersReportTableView.setItems(ordersReportList);
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
