package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.Oneway;

import common.Item;
import common.OrderDetails;
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// method to get OrdersReport 
    public static ArrayList<OrderDetails> getOrdersByMonthYear(String restaurantName, String month, String year) throws Exception {
        ensureInternalConnection();

        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT o.order_id, o.date, d.category, d.quantity, d.unit_price " +
                     "FROM orders o " +
                     "JOIN order_details d ON o.order_id = d.order_id " +
                     "JOIN restaurants r ON o.restaurant_id = r.id " +
                     "WHERE r.restaurant_name = ? " +
                     "AND DATE_FORMAT(STR_TO_DATE(o.date, '%Y-%m-%d'), '%Y') = ? " +
                     "AND DATE_FORMAT(STR_TO_DATE(o.date, '%Y-%m-%d'), '%m') = ?";

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setString(1, restaurantName);
            pstmt.setString(2, year);
            pstmt.setString(3, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String orderId = rs.getString("order_id");
                    String date = rs.getString("date");
                    String category = rs.getString("category");
                    String quantity = rs.getString("quantity");
                    String unitPrice = rs.getString("unit_price");

                    OrderDetails orderDetails = new OrderDetails(orderId, date, category, quantity, unitPrice);
                    orderDetailsList.add(orderDetails);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order details: " + e.getMessage());
            
            return null;
        }

        return orderDetailsList;
    }
    

    
    
	
}
