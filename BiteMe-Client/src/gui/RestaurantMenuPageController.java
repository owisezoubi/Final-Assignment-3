package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class RestaurantMenuPageController implements Initializable {

    @FXML
    private ListView<?> MenuListView;

    @FXML
    private Label menuLabel;

    @FXML
    private Button finishChoosingButton;

    @FXML
    private Button chooseOptionalAdditionsButton;

    @FXML
    private Button backToRestaurantsListPageButton;

    @FXML
    void backToRestaurantsListPageButtonOnClickAction(ActionEvent event) {
    	// closing current page
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();

    	// opening new page
    	OrderRestaurantListPageController ORLHP = new OrderRestaurantListPageController();
    	Stage primaryStage = new Stage();
    	try {
    		ORLHP.start(primaryStage);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}                
    }

    @FXML
    void chooseOptionalAdditionsButtonOnClickAction(ActionEvent event) {

    }

    @FXML
    void finishChoosingOrderButtonOnClickAction(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/RestaurantMenuPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("CustomerHomePageController");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/RestaurantMenuPage.css").toExternalForm());
    }

}
