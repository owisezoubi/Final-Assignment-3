package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.Oneway;

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
import gui.ServerPortFrameController;

public class DataBaseControl {

	private static Connection internalConnection;
    private static Connection externalConnection;
    private static Connection connection;
	
	
	
	// the function handle the connection to the Internal DataBase
	public static Connection connectToInternalDB() throws Exception {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Internal Driver definition succeed");
		} catch (Exception ex) {
			System.out.println("Internal Driver definition failed");
		}

		try {
			
			String internalDatabaseLink = "jdbc:mysql://localhost/" + ServerPortFrameController.DBInfo.get(0) + "?serverTimezone=IST";
			String user = "root";
			String password = ServerPortFrameController.DBInfo.get(2);
	
			internalConnection = DriverManager.getConnection(internalDatabaseLink, user, password);
			System.out.println("Internal SQL DataBase connected successfully");
			return internalConnection;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}

	
	// the function handle the connection to the External DataBase
	public static Connection connectToExternalDB() throws Exception {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("External Driver definition succeed");
		} catch (Exception ex) {
			System.out.println("External Driver definition failed");
		}
		try {
			
			String externalDatabaseLink = "jdbc:mysql://localhost/" + ServerPortFrameController.DBInfo.get(1) + "?serverTimezone=IST";
			String user = "root";
			String password = ServerPortFrameController.DBInfo.get(2);
			
			externalConnection = DriverManager.getConnection(externalDatabaseLink, user, password);
			System.out.println("External SQL DataBase connected successfully");
			return externalConnection;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	
	
	 // the function to disconnect from the Internal DataBase
    public static void disconnectFromInternalDB() {
        if (internalConnection != null) {
            try {
            	internalConnection.close();
                System.out.println("Internal SQL DataBase disconnected successfully");
            } catch (SQLException ex) {
                System.out.println("Failed to disconnect from Internal SQL DataBase");
                ex.printStackTrace();
            }
        }
    }

    // the function to disconnect from the External DataBase
    public static void disconnectFromExternalDB() {
        if (externalConnection != null) {
            try {
                externalConnection.close();
                System.out.println("External SQL DataBase disconnected successfully");
            } catch (SQLException ex) {
                System.out.println("Failed to disconnect from External SQL DataBase");
                ex.printStackTrace();
            }
        }
    }
    
    
    // ensuring that the Internal Connection is open
    private static void ensureInternalConnection() throws Exception {
        if (internalConnection == null || internalConnection.isClosed()) {
            internalConnection = connectToInternalDB();
        }
    }
	
	
	
	//the function update the "isLoggedIn" to all users to zero when exiting - logging out
    public static void LogOutAllAccounts() throws Exception {
        PreparedStatement ps = null;
        try {
            if (connection == null || connection.isClosed()) {
                // Assuming connectToInternalDB is the desired connection method
                connection = connectToInternalDB();
            }
            ps = connection.prepareStatement("UPDATE Assignment3_DB.users SET IsLoggedIn = ?");
            ps.setInt(1, 0);
            ps.executeUpdate();
            System.out.println("All accounts logged out successfully.");
        } catch (SQLException ex) {
            System.out.println("Failed to log out all accounts.");
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        

    }

	
    public static ArrayList<String> getUserNameLogin(String username) throws Exception {
        String sql = "SELECT user_name, password, is_logged_in, user_type, home_branch FROM users WHERE user_name = ?";
        ArrayList<String> result = new ArrayList<>();
        result.add("Error"); // Default message

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String isLoggedIn = rs.getString("is_logged_in");
                String userType = rs.getString("user_type");
                String homeBranch = rs.getString("home_branch");
                
                result.set(0, "UserFound");
                result.add(username);
                result.add(storedPassword);
                result.add(isLoggedIn);
                result.add(userType);
                result.add(homeBranch);
            } else {
                result.set(0, "NotExist");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            result.set(0, "Error");
        }
        
        
        System.out.println("DB class: " + result.toString());
        
        return result;
              
    }
	
	
 // Update the is_logged_in status to 1 (logged in)
    public static void UserLoggedIn(String username) throws Exception {
    	ensureInternalConnection();
    	
        String sql = "UPDATE users SET is_logged_in = 1 WHERE user_name = ?";
        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            System.out.println("User " + username + " logged in successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating login status for user " + username);
            System.out.println(e.getMessage());
        }
    }
    
    

    // Update the is_logged_in status to 0 (logged out)
    public static void UserLoggedOut(String username) throws Exception {
    	ensureInternalConnection();
    	
        String sql = "UPDATE users SET is_logged_in = 0 WHERE user_name = ?";
        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            System.out.println("User " + username + " logged out successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating login status for user " + username);
            System.out.println(e.getMessage());
        }
    }


    
    
    // Method to get user information by username
    public static ArrayList<String> getLoginUserInfo(String username) throws Exception {
        ArrayList<String> userInfo = new ArrayList<String>();
        String sql = "SELECT id, user_name, password, is_logged_in, user_type, home_branch FROM users WHERE user_name = ?";
        
        try (Connection conn = connectToInternalDB(); // Ensure connection is established
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	userInfo.add(null);
                	userInfo.add(rs.getString("id"));
                	userInfo.add(username);
                	userInfo.add(rs.getString("password"));
                	userInfo.add(rs.getString("is_logged_in"));
                	userInfo.add(rs.getString("user_type"));
                	userInfo.add(rs.getString("home_branch"));
                    
                    
                    
                } else {
                    System.out.println("User not found: " + username);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user information: " + e.getMessage());
        }
        
        return userInfo;
    }
    
    // Method to get restaurant info by login-username
    public static ArrayList<String> getRestaurantInfo(String username) throws Exception {
    	ensureInternalConnection();
    	
        ArrayList<String> restaurantInfo = new ArrayList<>();
        String sql = "SELECT id, user_name, password, is_logged_in, user_type, home_branch, restaurant_name, menu_id, phone_number, email FROM restaurants WHERE user_name = ?";
        
        try (Connection conn = connectToInternalDB(); // Ensure connection is established
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	restaurantInfo.add("Restaurant Found");
                    restaurantInfo.add(rs.getString("id"));
                    restaurantInfo.add(rs.getString("user_name"));
                    restaurantInfo.add(rs.getString("password"));
                    restaurantInfo.add(rs.getString("is_logged_in"));
                    restaurantInfo.add(rs.getString("user_type"));
                    restaurantInfo.add(rs.getString("home_branch"));
                    restaurantInfo.add(rs.getString("restaurant_name"));
                    restaurantInfo.add(rs.getString("menu_id"));
                    restaurantInfo.add(rs.getString("phone_number"));
                    restaurantInfo.add(rs.getString("email"));
                    
                } else {
                    System.out.println("Restaurant not found: " + username);
                	restaurantInfo.add("Restaurant Not Found");

                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving restaurant information: " + e.getMessage());
            restaurantInfo.add("Error");
        }
        
        return restaurantInfo;
    }
    
    

    // method to get all the restaurants info 
	public static ArrayList<Restaurant> getRestaurantsInfo() throws Exception {
			ensureInternalConnection();// Ensure connection is established
		
	        ArrayList<Restaurant> restaurantList = new ArrayList<>();
	        String sql = "SELECT * FROM restaurants";
	
	        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {
	
	            while (rs.next()) {
	                String id = rs.getString("id");
	                String userName = rs.getString("user_name");
	                String password = rs.getString("password");
	                String is_logged_in = rs.getString("is_logged_in");
	                String user_type = rs.getString("user_type");
	                String home_branch = rs.getString("home_branch");
	                String restaurant_name = rs.getString("restaurant_name");
	                String menu_id = rs.getString("menu_id");
	                String phone_number = rs.getString("phone_number");
	                String email = rs.getString("email");

	                
	                
	
	                Restaurant restaurant = new Restaurant(id, userName, password, is_logged_in, user_type, home_branch, restaurant_name, menu_id, phone_number, email);
	                restaurantList.add(restaurant);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error retrieving restaurant information: " + e.getMessage());
	        }
	
	        return restaurantList;
	    }

    
	
	
	// Method to get restaurant's menu by menu_id
	public static ArrayList<Item> getRestaurantMenu(String menuId) throws Exception {
	    ensureInternalConnection(); // Ensure connection is established
	    
	    ArrayList<Item> menuItems = new ArrayList<>();
	    String sql = "SELECT item_id, item_name, description, price, category " +
	                 "FROM menu_items " +
	                 "WHERE menu_id = ?";

	    try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
	        pstmt.setString(1, menuId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                String itemId = rs.getString("item_id");
	                String itemName = rs.getString("item_name");
	                String description = rs.getString("description");
	                String price = rs.getString("price");
	                String category = rs.getString("category");

	                Item item = new Item(itemId, itemName, description, price, category);
	                menuItems.add(item);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving menu items: " + e.getMessage());
	    }
	    
	    return menuItems;
	}


	
	
	// Method to get additions based on a category
	public static ArrayList<String> getAdditionsForCategory(String category) throws Exception {
	    ensureInternalConnection(); // Ensure connection is established

	    ArrayList<String> additions = new ArrayList<>();
	    String sql = "SELECT a.addition_name " +
	                 "FROM category_additions ca " +
	                 "JOIN additions a ON ca.addition_id = a.addition_id " +
	                 "WHERE ca.category = ?";

	    try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
	        pstmt.setString(1, category);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                String additionName = rs.getString("addition_name");
	                additions.add(additionName);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving additions for category: " + e.getMessage());
	    }

	    return additions;
	}

	
	// getting customer info 
	public static Customer getCustomerInfo(String user_id) throws Exception {
	    ensureInternalConnection(); // Ensure connection is established

		
	    Customer customer = null;
	    String sql = "SELECT id, user_name, password, is_logged_in, user_type, home_branch, first_name, last_name, " +
	                 "phone_number, email, credit_card_number, credit_card_cvv, credit_card_month, credit_card_year, " +
	                 "is_eligible_for_refund, customer_type FROM customers WHERE id = ?";

	    try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
	        pstmt.setString(1, user_id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                // Create a Customer object with the retrieved data
	                customer = new Customer(
	                    rs.getString("id"),
	                    rs.getString("user_name"),
	                    rs.getString("password"),
	                    rs.getString("is_logged_in"),
	                    rs.getString("user_type"),
	                    rs.getString("home_branch"),
	                    rs.getString("first_name"),
	                    rs.getString("last_name"),
	                    rs.getString("phone_number"),
	                    rs.getString("email"),
	                    rs.getString("credit_card_number"),
	                    rs.getString("credit_card_cvv"),
	                    rs.getString("credit_card_month"),
	                    rs.getString("credit_card_year"),
	                    rs.getString("is_eligible_for_refund"),
	                    rs.getString("customer_type")
	                );
	            } else {
	                System.out.println("Customer not found with ID: " + user_id);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving customer information: " + e.getMessage());
	    }

	    return customer;
	}

	// Method to generate a new order_id
	public static String generateNewOrderId() throws Exception {
		ensureInternalConnection(); // Ensure connection is established

		String newOrderId = null;
		String sql = "SELECT MAX(order_id) AS max_order_id FROM orders";

		try (PreparedStatement pstmt = internalConnection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				String maxOrderId = rs.getString("max_order_id");
				if (maxOrderId != null) {
					// Extract the numeric part of the order_id
					int maxIdNumber = Integer.parseInt(maxOrderId.substring(1));
					// Increment the number and format it
					int newIdNumber = maxIdNumber + 1;
					newOrderId = String.format("O%03d", newIdNumber);
				} else {
					// If no orders exist, start with O001
					newOrderId = "O001";
				}
			}
		} catch (SQLException e) {
			System.out.println("Error generating new order_id: " + e.getMessage());
		}

		return newOrderId;
	}
	
	public static ArrayList<String> saveOrder(ArrayList<String> current_order) throws Exception {
		ensureInternalConnection();
		
		ArrayList<String> savingOrderStatuStrings = new ArrayList<String>();
		
        String sql = "INSERT INTO orders (order_id, restaurant_id, user_id, date, desired_time, arrival_time, " +
                     "price, restaurant_confirmed_receiving, customer_confirmed_receiving, is_ready, is_late, " +
                     "order_type, order_receiving_method, delivery_address, recipient_name, recipient_phone_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {

            pstmt.setString(1, current_order.get(1));
            pstmt.setString(2, current_order.get(2));
            pstmt.setString(3, current_order.get(3));
            pstmt.setString(4, current_order.get(4));
            pstmt.setString(5, current_order.get(5));
            pstmt.setString(6, current_order.get(6));
            pstmt.setString(7, current_order.get(7));
            pstmt.setString(8, current_order.get(8));
            pstmt.setString(9, current_order.get(9));
            pstmt.setString(10, current_order.get(10));
            pstmt.setString(11, current_order.get(11));
            pstmt.setString(12, current_order.get(12));
            pstmt.setString(13, current_order.get(13));
            pstmt.setString(14, current_order.get(14));
            pstmt.setString(15, current_order.get(15));
            pstmt.setString(16, current_order.get(16));

            pstmt.executeUpdate();
            System.out.println("DataBaseControl: Order saved successfully.");
            savingOrderStatuStrings.add("Order Saved");
            
        } catch (SQLException e) {
            System.out.println("Error saving order: " + e.getMessage());
            savingOrderStatuStrings.add("Order Not Saved");

            throw e; // Re-throw the exception to handle it at a higher level if needed
        }
        return savingOrderStatuStrings;
    }
	
	// save order's data to order_category
	public static void saveCategoryQuantity(ArrayList<String> data) throws Exception {
        ensureInternalConnection();

        String orderId = data.get(1);
        String category = data.get(2);
        String quantity = data.get(3);

        String query = "INSERT INTO order_category (order_id, category, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, orderId);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, quantity);

            preparedStatement.executeUpdate();
            
            System.out.println("Category's Quantity saved in \"order_category\" successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Category's Quantity: failed to save in sql");
        }
    }
	
	// get data from table orders, for the OrdersReport
	public static ArrayList<OrdersReport> getOrdersReport(String restaurantName, String month, String year) throws Exception {
	    ensureInternalConnection(); // Ensure connection is established

	    ArrayList<OrdersReport> ordersList = new ArrayList<>();
	    String sql = "SELECT o.date, " +
	            "SUM(CASE WHEN oc.category = 'main dish' THEN oc.quantity ELSE 0 END) AS mainDish, " +
	            "SUM(CASE WHEN oc.category = 'salad' THEN oc.quantity ELSE 0 END) AS salad, " +
	            "SUM(CASE WHEN oc.category = 'drink' THEN oc.quantity ELSE 0 END) AS drink, " +
	            "SUM(CASE WHEN oc.category = 'dessert' THEN oc.quantity ELSE 0 END) AS dessert " +
	            "FROM orders o " +
	            "JOIN order_category oc ON o.order_id = oc.order_id " +
	            "JOIN restaurants r ON o.restaurant_id = r.id " +
	            "WHERE r.restaurant_name = ? AND MONTH(o.date) = ? AND YEAR(o.date) = ? " +
	            "GROUP BY o.date " +
	            "ORDER BY o.date";

	    try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
	        pstmt.setString(1, restaurantName);
	        pstmt.setInt(2, Integer.parseInt(month)); // month as integer
	        pstmt.setInt(3, Integer.parseInt(year));  // year as integer
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                String date = rs.getString("date");
	                String mainDish = rs.getString("mainDish");
	                String salad = rs.getString("salad");
	                String drink = rs.getString("drink");
	                String dessert = rs.getString("dessert");

	                OrdersReport order = new OrdersReport(date, mainDish, salad, drink, dessert);
	                ordersList.add(order);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving OrdersReport: " + e.getMessage());
	    }

	    return ordersList;
	}

	
	
	// Add the new method to get order stats
    public static ArrayList<IncomeOrdersDetails> getOrderStatsForMonthYear(String restaurantName, String month, String year) throws Exception {
        ensureInternalConnection();

        ArrayList<IncomeOrdersDetails> orderStatsList = new ArrayList<>();
        String sql = "SELECT DATE(date) as order_date, " +
                     "COUNT(order_id) as number_of_orders, " +
                     "SUM(price) as total_price " +
                     "FROM orders o " +
                     "JOIN restaurants r ON o.restaurant_id = r.id " +
                     "WHERE r.restaurant_name = ? " +
                     "AND MONTH(STR_TO_DATE(o.date, '%Y-%m-%d')) = ? " +
                     "AND YEAR(STR_TO_DATE(o.date, '%Y-%m-%d')) = ? " +
                     "GROUP BY DATE(o.date)";

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setString(1, restaurantName);
            pstmt.setString(2, month);
            pstmt.setString(3, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String orderDate = rs.getString("order_date");
                    int numberOfOrders = rs.getInt("number_of_orders");
                    double totalPrice = rs.getDouble("total_price");
                    IncomeOrdersDetails orderStats = new IncomeOrdersDetails(orderDate, numberOfOrders, totalPrice);
                    orderStatsList.add(orderStats);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order stats: " + e.getMessage());
        }
        return orderStatsList;
    }
	
	
    
    
    public static ArrayList<PerformanceReport> getLatePerformanceReport(String restaurantName, String month, String year) throws Exception {
        ensureInternalConnection(); // Ensure connection is established

        ArrayList<PerformanceReport> performanceList = new ArrayList<>();
        String sql = "SELECT " +
                     "o.date, " +
                     "SUM(CASE WHEN o.is_late = '1' THEN 1 ELSE 0 END) AS lateOrders, " +
                     "SUM(CASE WHEN o.is_late = '0' THEN 1 ELSE 0 END) AS notLateOrders " +
                     "FROM orders o " +
                     "JOIN restaurants r ON o.restaurant_id = r.id " +
                     "WHERE r.restaurant_name = ? " +
                     "AND MONTH(STR_TO_DATE(o.date, '%Y-%m-%d')) = ? " +
                     "AND YEAR(STR_TO_DATE(o.date, '%Y-%m-%d')) = ? " +
                     "GROUP BY o.date " +
                     "ORDER BY o.date";

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setString(1, restaurantName);
            pstmt.setInt(2, Integer.parseInt(month)); // month as integer
            pstmt.setInt(3, Integer.parseInt(year));  // year as integer
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String date = rs.getString("date");
                    int lateOrders = rs.getInt("lateOrders");
                    int notLateOrders = rs.getInt("notLateOrders");

                    PerformanceReport report = new PerformanceReport(date, lateOrders, notLateOrders);
                    performanceList.add(report);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving PerformanceReport: " + e.getMessage());
        }

        return performanceList;
    }
	
	
	
    
    
	
 // Method to get quarterly orders report for specified months and year
    public static ArrayList<QuarterlyOrdersReport> getQuarterlyOrdersReport(String branchId, String year, String month1, String month2, String month3) throws Exception {
        ensureInternalConnection();
        System.out.println("Branch ID: " + branchId);
        System.out.println("Year: " + year);
        System.out.println("Months: " + month1 + ", " + month2 + ", " + month3);

        ArrayList<QuarterlyOrdersReport> quarterlyOrdersReportList = new ArrayList<>();
        String sql = "SELECT r.restaurant_name, " +
                     "SUM(CASE WHEN MONTH(o.date) = ? THEN 1 ELSE 0 END) AS ordersMonth1, " +
                     "SUM(CASE WHEN MONTH(o.date) = ? THEN 1 ELSE 0 END) AS ordersMonth2, " +
                     "SUM(CASE WHEN MONTH(o.date) = ? THEN 1 ELSE 0 END) AS ordersMonth3 " +
                     "FROM orders o JOIN restaurants r ON o.restaurant_id = r.id " +
                     "WHERE r.home_branch = ? AND YEAR(o.date) = ? AND MONTH(o.date) IN (?, ?, ?) " +
                     "GROUP BY r.restaurant_name";

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(month1));
            pstmt.setInt(2, Integer.parseInt(month2));
            pstmt.setInt(3, Integer.parseInt(month3));
            pstmt.setString(4, branchId);
            pstmt.setInt(5, Integer.parseInt(year));
            pstmt.setInt(6, Integer.parseInt(month1));
            pstmt.setInt(7, Integer.parseInt(month2));
            pstmt.setInt(8, Integer.parseInt(month3));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String restaurantName = rs.getString("restaurant_name");
                    int ordersMonth1 = rs.getInt("ordersMonth1");
                    int ordersMonth2 = rs.getInt("ordersMonth2");
                    int ordersMonth3 = rs.getInt("ordersMonth3");
                    QuarterlyOrdersReport report = new QuarterlyOrdersReport(restaurantName, ordersMonth1, ordersMonth2, ordersMonth3);
                    quarterlyOrdersReportList.add(report);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving quarterly orders report: " + e.getMessage());
        }

        return quarterlyOrdersReportList;
    }

    // function to get QuarterlyRevenueReport data orders
    public static ArrayList<QuarterlyRevenueReport> getQuarterlyRevenueReport(String branchId, String year, String month1, String month2, String month3) throws Exception {
        ensureInternalConnection();
        System.out.println("Branch ID: " + branchId);
        System.out.println("Year: " + year);
        System.out.println("Months: " + month1 + ", " + month2 + ", " + month3);

        ArrayList<QuarterlyRevenueReport> quarterlyRevenueReportList = new ArrayList<>();
        String sql = "SELECT r.restaurant_name, " +
                     "SUM(CASE WHEN MONTH(o.date) = ? THEN o.price ELSE 0 END) AS revenueMonth1, " +
                     "SUM(CASE WHEN MONTH(o.date) = ? THEN o.price ELSE 0 END) AS revenueMonth2, " +
                     "SUM(CASE WHEN MONTH(o.date) = ? THEN o.price ELSE 0 END) AS revenueMonth3 " +
                     "FROM orders o JOIN restaurants r ON o.restaurant_id = r.id " +
                     "WHERE r.home_branch = ? AND YEAR(o.date) = ? AND MONTH(o.date) IN (?, ?, ?) " +
                     "GROUP BY r.restaurant_name";

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(month1));
            pstmt.setInt(2, Integer.parseInt(month2));
            pstmt.setInt(3, Integer.parseInt(month3));
            pstmt.setString(4, branchId);
            pstmt.setInt(5, Integer.parseInt(year));
            pstmt.setInt(6, Integer.parseInt(month1));
            pstmt.setInt(7, Integer.parseInt(month2));
            pstmt.setInt(8, Integer.parseInt(month3));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String restaurantName = rs.getString("restaurant_name");
                    double revenueMonth1 = rs.getDouble("revenueMonth1");
                    double revenueMonth2 = rs.getDouble("revenueMonth2");
                    double revenueMonth3 = rs.getDouble("revenueMonth3");
                    QuarterlyRevenueReport report = new QuarterlyRevenueReport(restaurantName, revenueMonth1, revenueMonth2, revenueMonth3);
                    quarterlyRevenueReportList.add(report);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving quarterly revenue report: " + e.getMessage());
        }

        return quarterlyRevenueReportList;
    }
    
	
}
