package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
	
	
	//the function update the "isLoggedIn" to all users to zero when exiting - logging out
	public static void LogOutAllAccounts() throws SQLException {
		PreparedStatement ps;

		ps = connection.prepareStatement("UPDATE Assignment3_DB.users SET IsLoggedIn = ? ");
		ps.setInt(1, 0);
		ps.executeUpdate();
	}
	
}
