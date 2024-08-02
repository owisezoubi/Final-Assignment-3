package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IncomeReportPageController {
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/IncomeReportPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Income Report Page");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add(getClass().getResource("/gui/BranchManagerHomePage.css").toExternalForm());
    }
	
	
}
