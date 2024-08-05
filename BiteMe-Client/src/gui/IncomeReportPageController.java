package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.IncomeOrdersDetails;
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

public class IncomeReportPageController implements Initializable {

    @FXML
    private TableView<IncomeOrdersDetails> incomeReportTableView;

    @FXML
    private TableColumn<IncomeOrdersDetails, String> dateTableColumn;

    @FXML
    private TableColumn<IncomeOrdersDetails, Integer> NumberOfOrdersTableColumn;

    @FXML
    private TableColumn<IncomeOrdersDetails, Double> totalSalesRevenueTableColumn;

    @FXML
    private Label incomeReportLabel;

    @FXML
    private Label restaurantnameLabel;

    @FXML
    private Label branchnameLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Button finishIncomeReportButton;
    
    @FXML
    private Label messageIncomeReportPage;

    private Stage stage;
    
    private ObservableList<IncomeOrdersDetails> incomeOrderDetailsList = FXCollections.observableArrayList();

    @FXML
    void finishIncomeReportButtonOnClickAction(ActionEvent event) throws Exception {
    	 // closing current page
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // opening BranchManagerHomePage
        TypesOfReportsPageController TORP = new TypesOfReportsPageController();
        Stage newStage = new Stage();
        TORP.start(newStage);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/IncomeReportPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Income Report Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restaurantnameLabel.setText("Restaurant Name: " +ChatClient.chosenRestaurantReport.getRestaurant_name());
        branchnameLabel.setText("Restaurant Name: " +ChatClient.chosenRestaurantReport.getBranch());

        ArrayList<String> msg = new ArrayList<>();
        msg.add(0, "Get IncomeReport Info");
        msg.add(1, ChatClient.chosenRestaurantReport.getRestaurant_name());
        msg.add(2, ChatClient.chosenRestaurantReport.getMonth());
        msg.add(3, ChatClient.chosenRestaurantReport.getYear());
        ClientUI.chat.accept(msg);

        ArrayList<IncomeOrdersDetails> ordersInfo = (ArrayList<IncomeOrdersDetails>) ChatClient.inputList;
        if (ordersInfo.isEmpty()) {
        	messageIncomeReportPage.setText("There are no orders for the specified restaurant on the given date.");
        }
        System.out.println("Result from Server, in IncomeReportPageController: " + ordersInfo);

        // Set up the TableView columns
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        NumberOfOrdersTableColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        totalSalesRevenueTableColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Populate the TableView with data from the server
        incomeOrderDetailsList.addAll(ordersInfo);
        incomeReportTableView.setItems(incomeOrderDetailsList);
    }
}
