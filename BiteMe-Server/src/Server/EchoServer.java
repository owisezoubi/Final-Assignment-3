package Server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

import common.User;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  public DataBaseControl dbBaseControl;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
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
			//e.printStackTrace();
		}
	}

	
	
	private void UpdateClient(InetAddress HostName, String IP, String Status) {
		ServerUI.aFrame.UpdateClient(HostName, IP, Status);
	}
  
  //Instance methods ************************************************
  
	
	
	
	
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client)	{
	    
	    
	    int flag = 0;
		ArrayList<String> inputList = (ArrayList<String>) msg;
		
		System.out.println("Message received: " + msg + " from " + client);
		
		switch (inputList.get(0)) {
		
			case "Get Login Validation":
			
				try {
					//inputList.remove(0);
				
					System.out.println("EchoServer: " + inputList.toString());
				
					ArrayList<String> resultList = DataBaseControl.getUserNameLogin(inputList.get(1));
				
					System.out.println("result: " + resultList.toString());
				
					this.sendToAllClients(resultList);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			break;

			
			// changing is_logged_in to 1 after logging in
			case "User Logged In":
			try {
				
				System.out.println("UserLoggedIn: " + inputList.toString());
				
				DataBaseControl.UserLoggedIn(inputList.get(1));
				
				
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
			
			
		default:
			break;
		}
	}

    
	
	
	
	
	
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()	{
		System.out.println("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()	{
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  
  
}
//End of EchoServer class
