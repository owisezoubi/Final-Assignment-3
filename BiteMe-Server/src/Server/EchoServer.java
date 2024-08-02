package Server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

import common.Item;
import common.OrderDetails;
import common.Restaurant;
import common.User;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	public DataBaseControl dbBaseControl;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
	}

	public void clientConnected(ConnectionToClient client) {
		System.out.println("---> Client has Connected");
		try {

			UpdateClient(client.getInetAddress(), client.getInetAddress().getHostAddress(), "Connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clientDisconnected(ConnectionToClient client) {
		System.out.println("---> Client has Disconnected");
		try {

			UpdateClient(client.getInetAddress(), client.getInetAddress().getHostAddress(), "Disconnected");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private void UpdateClient(InetAddress HostName, String IP, String Status) {
		ServerUI.aFrame.UpdateClient(HostName, IP, Status);
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		int flag = 0;
		ArrayList<String> inputList = (ArrayList<String>) msg;

		System.out.println("Message received: " + msg + " from " + client);

		switch (inputList.get(0)) {

		case "Get Login Validation":

			try {
				// inputList.remove(0);

				System.out.println("EchoServer: " + inputList.toString());

				ArrayList<String> resultList = DataBaseControl.getUserNameLogin(inputList.get(1));

				System.out.println("EchoServer: result: " + resultList.toString());

				this.sendToAllClients(resultList);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		// changing is_logged_in to 1 after logging in
		case "User Logged In":
			try {

				// Disabled for continues coding, IMPORTANT TO ABLE IT AGAIN!!!!!!!!
				////////////////////////////////////////////////////////////////////

//				System.out.println("UserLoggedIn: " + inputList.toString());
//				
//				DataBaseControl.UserLoggedIn(inputList.get(1));
//				
//				
				this.sendToAllClients(null);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		// getting user info after login
		case "Get User Info":
			try {

				System.out.println("UserLoggedIn: " + inputList.toString());

				ArrayList<String> userInfo = DataBaseControl.getLoginUserInfo(inputList.get(1));

				System.out.println("EchoServer: " + userInfo.toString());

				this.sendToAllClients(userInfo);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		// get restaurant info - after logging in
		case "Get Restaurant Info":

			try {
				System.out.println("UserLoggedIn: " + inputList.toString());

				ArrayList<String> restaurantInfo = null;

				restaurantInfo = DataBaseControl.getRestaurantInfo(inputList.get(1));

				System.out.println("EchoServer: " + restaurantInfo.toString());

				this.sendToAllClients(restaurantInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		/// get all restaurants info from the the table
		case "Get Restaurants Info":

			try {
				System.out.println("Get Restaurants Info: " + inputList.toString());

				ArrayList<Restaurant> restaurantInfo = null;

				restaurantInfo = DataBaseControl.getRestaurantsInfo();

				System.out.println("EchoServer: " + restaurantInfo.toString());

				this.sendToAllClients(restaurantInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		// changing is_logged_in to 1 after logging in - NOT FINISHED
		case "User Logged Out":
			try {

				System.out.println("UserLoggedOut: " + inputList.toString());

				DataBaseControl.UserLoggedOut(inputList.get(1));

				this.sendToAllClients(null);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		// getting restaurants menu after choosing in Order process
		case "Get Restaurant Menu Info":

			try {
				System.out.println("before getting Restaurant Menu Info: " + inputList.toString());
				ArrayList<Item> restaurantMenuArrayList = DataBaseControl.getRestaurantMenu(inputList.get(1));

				System.out.println("after getting Restaurant Menu Info: " + restaurantMenuArrayList);

				this.sendToAllClients(restaurantMenuArrayList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

			
			
			
			
		// getting restaurants menu after choosing in Order process
		case "Get Item's Additions Info":

			try {
				System.out.println("EchoServer:  before Get Item's Additions Info: " + inputList.toString());
				ArrayList<String> additionsForCategory = DataBaseControl.getAdditionsForCategory(inputList.get(1));

				System.out.println("EchoServer:  after Get Item's Additions Info: " + additionsForCategory);

				this.sendToAllClients(additionsForCategory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

			
			
			
			
			
			
			
			
		// getting msg from client - retrieving data for OrderReport
		case "Get OrdersReport Info":
			try {
				System.out.println("Get OrderReport Info: " + inputList.toString());
				ArrayList<OrderDetails> OrdersInfo = null;
				OrdersInfo = DataBaseControl.getOrdersByMonthYear(inputList.get(1), inputList.get(2), inputList.get(3));
				System.out.println("EchoServer: " + OrdersInfo.toString());

				this.sendToAllClients(OrdersInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		default:
			break;
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

}
//End of EchoServer class
