package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.ChosenItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdditionsPageController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button doneAdditionsButton;

    @FXML
    private Label additionsPageLabel;

    @FXML
    private VBox additionsVBox;

    private ArrayList<String> availableAdditions; // List of available additions
    protected ArrayList<String> selectedAdditions; // List to store selected additions
    private ArrayList<String> getAdditionsMSG; // Message to send to server for getting item's relevant additions
    private String category; // Category of the current item

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/AdditionsPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("AdditionsPage");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add(getClass().getResource("/gui/AdditionsPage.css").toExternalForm());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedAdditions = new ArrayList<String>();
        getAdditionsMSG = new ArrayList<String>();
        getAdditionsMSG.add("Get Item's Additions Info");
        category = ChatClient.chosenItemInOrder.getCategory();
        getAdditionsMSG.add(category);

        // Request additions from server
        ClientUI.chat.accept(getAdditionsMSG);

        // Populate availableAdditions from the server response
        availableAdditions = (ArrayList<String>) ChatClient.inputList;

        // Load additions based on category
        loadAdditions();
    }

    // Method to load additions into the VBox as CheckBoxes or RadioButtons
    private void loadAdditions() {
        additionsVBox.getChildren().clear();

        if (category.equalsIgnoreCase("drink")) {
            ToggleGroup toggleGroup = new ToggleGroup();
            for (final String addition : availableAdditions) {
                RadioButton radioButton = new RadioButton(addition);
                radioButton.setToggleGroup(toggleGroup);
                radioButton.setOnAction(event -> {
                    selectedAdditions.clear(); // Clear previous selection
                    selectedAdditions.add(addition);
                });
                additionsVBox.getChildren().add(radioButton);
            }
        } else {
            for (final String addition : availableAdditions) {
                final CheckBox checkBox = new CheckBox(addition);
                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        if (!selectedAdditions.contains(addition)) {
                            selectedAdditions.add(addition);
                        }
                    } else {
                        selectedAdditions.remove(addition);
                    }
                });
                additionsVBox.getChildren().add(checkBox);
            }
        }
    }

    @FXML
    void cancelOnClickAction(ActionEvent event) {
        // Closing the page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void doneAdditionsOnClickAction(ActionEvent event) {
        // Check if the category is "drink" and if a radio button is selected
        if (category.equalsIgnoreCase("drink")) {
            ToggleGroup toggleGroup = getToggleGroup();
            if (toggleGroup.getSelectedToggle() == null) {
                // Show alert if no radio button is selected
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Selection Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select the drink's size.");
                alert.showAndWait();
                return; // Exit the method without proceeding
            }
        }

        // Optionally, handle saving the selected additions or moving to the next step
        System.out.println("Selected additions: " + selectedAdditions);

        System.out.println("chosenItemInOrder: " + ChatClient.chosenItemInOrder);

        System.out.println("selectedAdditions: " + selectedAdditions);

        ChosenItem chosenItem = new ChosenItem(ChatClient.chosenItemInOrder, selectedAdditions);
        ChatClient.cart.add(chosenItem);

        // Close the current window or proceed as needed
        Stage stage = (Stage) doneAdditionsButton.getScene().getWindow();
        stage.close();
    }

    private ToggleGroup getToggleGroup() {
        for (Node node : additionsVBox.getChildren()) {
            if (node instanceof RadioButton) {
                return ((RadioButton) node).getToggleGroup();
            }
        }
        return null; // Return null if no ToggleGroup is found
    }
}
