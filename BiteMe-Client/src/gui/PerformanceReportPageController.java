package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PerformanceReportPageController {
	public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/gui/PerformanceReportPage.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Performance Report Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
