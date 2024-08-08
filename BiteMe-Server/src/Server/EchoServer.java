package Server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

import common.Customer;
import common.IncomeOrdersDetails;
import common.Item;
import common.Order;
import common.OrdersReport;
import common.PerformanceReport;
import common.QuarterlyOrdersReport;
import common.QuarterlyRevenueReport;
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
		case "Get New Order Id":

			try {
				System.out.println("EchoServer: ---> before getting new ID: " + inputList.toString());
				ArrayList<String> orderNewId = new ArrayList<String>(); 
				orderNewId.add(DataBaseControl.generateNewOrderId());

				System.out.println("EchoServer: ---> after getting new ID: " + orderNewId);

				this.sendToAllClients(orderNewId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		// getting restaurants menu after choosing in Order process
		case "Save Order":

			try {
				
				ArrayList<String> current_order = (ArrayList<String>) msg;
				
				System.out.println("before Saving Order: " + current_order.get(0));
				
				ArrayList<String> savingOrderStatuStrings = DataBaseControl.saveOrder(current_order);

				System.out.println("after Saving Order");

				this.sendToAllClients(savingOrderStatuStrings);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		// getting restaurants menu after choosing in Order process
		case "Save Category Quantity":

			try {

				ArrayList<String> current_order = (ArrayList<String>) msg;

				System.out.println("before Saving Category Quantity: " + current_order.get(0));

				DataBaseControl.saveCategoryQuantity(current_order);

				System.out.println("after Saving Category Quantity");

				this.sendToAllClients(null);
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

		// getting customers info
		case "Get Customer Refund Status":
			try {
				System.out.println("EchoServer:  before Get Customer's Info: " + inputList.toString());
				Customer customer = DataBaseControl.getCustomerInfo(inputList.get(1));

				System.out.println("EchoServer:  after Get Customer's Info: " + customer);

				
				this.sendToAllClients(customer);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			break;
			
		// getting msg from client - retrieving data for OrderReport
		case "Get OrdersReport Info":
			try {
				System.out.println("Get OrderReport Info: " + inputList.toString());
				ArrayList<OrdersReport> OrdersInfo = null;
				OrdersInfo = DataBaseControl.getOrdersReport(inputList.get(1), inputList.get(2), inputList.get(3));
				System.out.println("EchoServer: " + OrdersInfo.toString());

				this.sendToAllClients(OrdersInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "Get IncomeReport Info":
			try {
				System.out.println("Get IncomeReport Info: " + inputList.toString());
				ArrayList<IncomeOrdersDetails> OrdersInfo = null;
				OrdersInfo = DataBaseControl.getOrderStatsForMonthYear(inputList.get(1), inputList.get(2),
						inputList.get(3));
				System.out.println("EchoServer: " + OrdersInfo.toString());

				this.sendToAllClients(OrdersInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "Get PerformanceReport Info":
			try {
				System.out.println("Get PerformanceReport Info: " + inputList.toString());
				ArrayList<PerformanceReport> OrdersInfo = null;
				OrdersInfo = DataBaseControl.getLatePerformanceReport(inputList.get(1), inputList.get(2),
						inputList.get(3));
				System.out.println("EchoServer: " + OrdersInfo.toString());

				this.sendToAllClients(OrdersInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "Get QuarterlyOrdersReport Info":
			try {
				System.out.println("Get QuarterlyOrdersReport Info" + inputList.toString());
				ArrayList<QuarterlyOrdersReport> ordersInfo = null;
				ordersInfo = DataBaseControl.getQuarterlyOrdersReport(inputList.get(1), inputList.get(2),
						inputList.get(3), inputList.get(4), inputList.get(5));
				System.out.println("EchoServer: " + ordersInfo.toString());
				this.sendToAllClients(ordersInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "Get QuarterlyRevenueReport Info":
			try {
				System.out.println("Get QuarterlyRevenueReport Info " + inputList.toString());
				ArrayList<QuarterlyRevenueReport> ordersInfo = null;
				ordersInfo = DataBaseControl.getQuarterlyRevenueReport(inputList.get(1), inputList.get(2),
						inputList.get(3), inputList.get(4), inputList.get(5));
				System.out.println("EchoServer: " + ordersInfo.toString());
				this.sendToAllClients(ordersInfo);
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
