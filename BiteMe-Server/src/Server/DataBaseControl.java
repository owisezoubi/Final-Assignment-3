package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        String sql = "SELECT user_name, password, is_logged_in, user_type FROM users WHERE user_name = ?";
        ArrayList<String> result = new ArrayList<>();
        result.add("Error"); // Default message

        try (PreparedStatement pstmt = internalConnection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String isLoggedIn = rs.getString("is_logged_in");
                String userType = rs.getString("user_type");
                
                result.set(0, "UserFound");
                result.add(username);
                result.add(storedPassword);
                result.add(isLoggedIn);
                result.add(userType);
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
        String sql = "SELECT id, user_name, password, is_logged_in, user_type, first_name, last_name, phone_number, home_branch, email FROM users WHERE user_name = ?";
        
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
                	userInfo.add(rs.getString("first_name"));
                	userInfo.add(rs.getString("last_name"));
                	userInfo.add(rs.getString("phone_number"));
                	userInfo.add(rs.getString("home_branch"));
                	userInfo.add(rs.getString("email"));
                    
                    
                    
                } else {
                    System.out.println("User not found: " + username);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user information: " + e.getMessage());
        }
        
        return userInfo;
    }
    
    // Method to get restaurant info by username
    public static ArrayList<String> getRestaurantInfo(String username) throws Exception {
    	ensureInternalConnection();
    	
        ArrayList<String> restaurantInfo = new ArrayList<>();
        String sql = "SELECT restaurant_id, user_name, name, home_branch, menu_id FROM restaurants WHERE restaurant_id = ?";
        
        try (Connection conn = connectToInternalDB(); // Ensure connection is established
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	restaurantInfo.add("Restaurant Found");
                    restaurantInfo.add(rs.getString("restaurant_id"));
                    restaurantInfo.add(rs.getString("user_name"));
                    restaurantInfo.add(rs.getString("name"));
                    restaurantInfo.add(rs.getString("home_branch"));
                    restaurantInfo.add(rs.getString("menu_id"));
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

    
	
}
