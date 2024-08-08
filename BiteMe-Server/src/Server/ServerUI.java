package Server;

import java.io.IOException;

import gui.ServerPortFrameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerUI extends Application {

    final public static int DEFAULT_PORT = 5555;
    
    public static EchoServer sv;
    

    public static ServerPortFrameController aFrame;
    

    public static void main(String args[]) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	
    	sv = new EchoServer(DEFAULT_PORT);
    	
        aFrame = new ServerPortFrameController(); // create ServerFrame
        try {
            aFrame.start(primaryStage);
            //runServer(DEFAULT_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method is responsible for the creation of 
     * the server instance (there is no UI in this phase).
     *
     * @param args[0] The port number to listen on.  Defaults to 5555 
     *          if no argument is entered.
     */
	public static void runServer(int defaultPort)
	{
//		 int port = 0; //Port to listen on
//
//	        try
//	        {
//	        	port = defaultPort; //Set port to 5555
//	          
//	        }
//	        catch(Throwable t)
//	        {
//	        	System.out.println("ERROR - Could not connect!");
//	        }
//	        
	       // sv.setPort(port);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	          System.out.println("Server listenning for clients!");

	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
	
	public static void stopServer() throws IOException
	{
		sv.close();
		System.out.println("Server Disconnected");
	}

}
