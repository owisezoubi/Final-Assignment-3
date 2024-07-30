package gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.ClientInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Server.DataBaseControl;
import Server.EchoServer;
import Server.ServerUI;

public class ServerPortFrameController implements Initializable {

    final public static int DEFAULT_PORT = 5555;

	public static ArrayList<String> DBInfo = new ArrayList<>();

    private static ObservableList<ClientInfo> clients = FXCollections.observableArrayList();
    private static ArrayList<ClientInfo> clientArrayList = new ArrayList<ClientInfo>();

    @FXML
    private TableView<ClientInfo> ClientsTableView;

    @FXML
    private TableColumn<ClientInfo, String> guestNameColumn;

    @FXML
    private TableColumn<ClientInfo, String> ipColumn;

    @FXML
    private TableColumn<ClientInfo, String> statusTabelColumn;

    @FXML
    private TextField serverIpTextField;

    @FXML
    private TextField dBInternalTextField;

    @FXML
    private TextField dBExternalTextField;

    @FXML
    private TextField PasswordTextField;

    @FXML
    private Label serverIpLabel;

    @FXML
    private Label dBInternalLabel;

    @FXML
    private Label dBExternalLabel;

    @FXML
    private Button startServerButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button disconnectServerButton;

    @FXML
    private Label serverLabel;

    @FXML
    private Label messageLabelServerPort;

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            serverIpTextField.setText(InetAddress.getLocalHost().getHostAddress().toString());
            serverIpTextField.setEditable(false);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        dBInternalTextField.setText("Assignment3_DB");
        dBExternalTextField.setText("BiteMe_DB");
        PasswordTextField.setText("Aa123456");

        DBInfo.add(dBInternalTextField.getText());
        DBInfo.add(dBExternalTextField.getText());
        DBInfo.add(PasswordTextField.getText());

        disconnectServerButton.setDisable(true);

        guestNameColumn.setCellValueFactory(new PropertyValueFactory<ClientInfo, String>("hostName"));
        ipColumn.setCellValueFactory(new PropertyValueFactory<ClientInfo, String>("ip"));
        statusTabelColumn.setCellValueFactory(new PropertyValueFactory<ClientInfo, String>("Status"));
        ClientsTableView.setItems(clients);
    }

    @FXML
    private void ExitButtonOnClickAction(ActionEvent event) throws Exception {
        System.exit(0);
    }

    @FXML
    private void StartServerButtonOnClickAction(ActionEvent event) throws Exception {
    	
    	
        // Fetch the credentials from the text fields
        String internalDB = dBInternalTextField.getText().trim();
        String externalDB = dBExternalTextField.getText().trim();
        String password = PasswordTextField.getText().trim();
        
        // Check if any of the text fields are empty
        if (internalDB.isEmpty() || externalDB.isEmpty() || password.isEmpty()) {
            messageLabelServerPort.setText("All fields must be filled out.");
            messageLabelServerPort.setTextFill(Paint.valueOf("red"));
            messageLabelServerPort.setMaxWidth(Double.MAX_VALUE);
            messageLabelServerPort.setAlignment(Pos.CENTER);
            return;
        }

        
        // Start the server
        ServerUI.runServer(ServerUI.DEFAULT_PORT);
        
        
        DBInfo.set(0, internalDB);
        DBInfo.set(1, externalDB);
        DBInfo.set(2, password);

        boolean internalConnected = false;
        boolean externalConnected = false;

        
        
        
        try {
            if (DataBaseControl.connectToInternalDB() != null) {
                internalConnected = true;
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the internal database.");
            e.printStackTrace();
        }

        try {
            if (DataBaseControl.connectToExternalDB() != null) {
                externalConnected = true;
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the external database.");
            e.printStackTrace();
        }

        if (internalConnected && externalConnected) {
        	
        	

        	
            disconnectServerButton.setDisable(false);
            messageLabelServerPort.setText("Connected to both databases successfully");
            messageLabelServerPort.setTextFill(Paint.valueOf("green"));
        } else {
            disconnectServerButton.setDisable(true);
            messageLabelServerPort.setTextFill(Paint.valueOf("red"));

            if (!internalConnected) {
                messageLabelServerPort.setText("There is no Internal DB Name found or Incorrect Password");
                messageLabelServerPort.setMaxWidth(Double.MAX_VALUE);
                messageLabelServerPort.setAlignment(Pos.CENTER);
            }
            if (!externalConnected) {
                messageLabelServerPort.setText("There is no External DB Name found or Incorrect Password");
            }
            
            if (!internalConnected && !externalConnected) {
                messageLabelServerPort.setText("There is no DB Names found or Incorrect Password");

			}
        }
    }


    @FXML
    void disconnectServerButtonOnClickAction(ActionEvent event) throws Exception {
    	clients.removeAll();
        ClientsTableView.getItems().clear();
        DataBaseControl.disconnectFromInternalDB();
        DataBaseControl.disconnectFromExternalDB();
        ServerUI.stopServer();
        disconnectServerButton.setDisable(true);
        messageLabelServerPort.setText("Server disconnected.");
        messageLabelServerPort.setTextFill(Paint.valueOf("black")); // Reset color
        System.out.println("Server disconnected.");
        
    }

    public void UpdateClient(InetAddress Host, String IP, String Status) {
        javafx.application.Platform.runLater(() -> {
            ClientInfo client = new ClientInfo(Host.getHostName(), IP, Status);
            clients.add(client);
        });
    }
}
