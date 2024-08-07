package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import common.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestaurantMenuPageController implements Initializable {

    @FXML
    private Label menuLabel;

    @FXML
    private Button finishChoosingButton;

    @FXML
    private Button backToRestaurantsListPageButton;

    @FXML
    private TabPane categoriesGridViewMenuPage;

    @FXML
    private Tab saladsTabViewLabel;

    @FXML
    private GridPane saladsGridView;

    @FXML
    private Tab mainDishTabViewLabel;

    @FXML
    private GridPane mainDishGridView;

    @FXML
    private Tab dessertTabViewLabel;

    @FXML
    private GridPane dessertGridView;

    @FXML
    private Tab drinksTabViewLabel;

    @FXML
    private GridPane drinksGridView;

    private ArrayList<Item> salads = new ArrayList<>();
    private ArrayList<Item> mainDishes = new ArrayList<>();
    private ArrayList<Item> desserts = new ArrayList<>();
    private ArrayList<Item> drinks = new ArrayList<>();

    @FXML
    void backToRestaurantsListPageButtonOnClickAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        try {
            OrderRestaurantListPageController ORLHP = new OrderRestaurantListPageController();
            Stage primaryStage = new Stage();
            ORLHP.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void finishChoosingOrderButtonOnClickAction(ActionEvent event) throws Exception {
        // Implement the functionality to finalize the order
    	
    	
    	// Closing current page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        // Opening new page
        OrderTypePageController OTP = new OrderTypePageController();
        Stage primaryStage = new Stage();
        OTP.start(primaryStage);
    
    	
    	System.out.println(ChatClient.cart);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	ChatClient.cart = new ArrayList<>();
    	
    	categorizeItemsByCategory();
    }

    private void categorizeItemsByCategory() {
        // Retrieve the list of items from the chosen restaurant's menu
        List<Item> allItems = ChatClient.choosenRestaurantMenu;

        // Categorize items based on their category
        for (Item item : allItems) {
            switch (item.getCategory().toLowerCase()) {
                case "salad":
                    salads.add(item);
                    break;
                case "main dish":
                    mainDishes.add(item);
                    break;
                case "dessert":
                    desserts.add(item);
                    break;
                case "drink":
                    drinks.add(item);
                    break;
                default:
                    System.out.println("Unknown category for item: " + item.getItem_name());
                    break;
            }
        }

        // Populate the GridPanes with the categorized items
        populateGridPane(saladsGridView, salads);
        populateGridPane(mainDishGridView, mainDishes);
        populateGridPane(dessertGridView, desserts);
        populateGridPane(drinksGridView, drinks);
    }

    private void populateGridPane(GridPane gridPane, ArrayList<Item> items) {
        int column = 0;
        int row = 0;

        for (final Item item : items) {
            VBox itemBox = new VBox();
            itemBox.setSpacing(5); // Space between elements in VBox

            Label itemNameLabel = new Label(item.getItem_name());
            Label itemDescriptionLabel = new Label(item.getDescription());
            Label itemPriceLabel = new Label("Price: " + item.getItem_price());

            itemBox.getChildren().addAll(itemNameLabel, itemDescriptionLabel, itemPriceLabel);

            Button itemButton = new Button();
            itemButton.setGraphic(itemBox); // Set the VBox as the graphic of the Button
            itemButton.setOnAction(event -> {
                try {
                	ChatClient.chosenItemInOrder = item;
                	
                    // opening Additions page
                    AdditionsPageController AP = new AdditionsPageController();
                    Stage primaryStage = new Stage();
                    AP.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Ensure the button resizes to fit the content
            itemButton.setMaxWidth(Double.MAX_VALUE);
            itemButton.setMaxHeight(Double.MAX_VALUE);
            GridPane.setFillWidth(itemButton, true);
            GridPane.setFillHeight(itemButton, true);

            gridPane.add(itemButton, column, row);

            column++;
            if (column > 2) { // 3 items per row
                column = 0;
                row++;
            }
        }
    }


    

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/RestaurantMenuPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Restaurant Menu Page");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add(getClass().getResource("/gui/RestaurantMenuPage.css").toExternalForm());
    }
}
