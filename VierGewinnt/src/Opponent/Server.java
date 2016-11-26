/*
* Class created by Pascal Baumann. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* Pascal Baumann    25.11.2016  PB20161125_01   Created the class, implemented method stubs and added JavaDoc.                   
*/


package Opponent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pascal
 */
public class Server implements Runnable{
    // Attributes
    private int port;
    private boolean notConnected = true;
    private Socket clientSocket;
    
    // Constants
    private final int ANNOUNCE_WAIT = 5000;
    private final Runnable announceGame;
    
    /**
     * Default constructor
     */
    public Server(){
        this.port = 44444;
        // Inner class, so we can have a thread listening for connection attempts, and one announcing the server
        announceGame = () -> {
            try {
                while (!notConnected){
                    Server.this.announceGame();
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        
    }
    
    /**
     * Opens a socket on which the server listens for clients which want to join
     * @throws java.io.IOException
     */
    public void openConnection() throws IOException{
        // TODO Remove console messages
        System.out.println("Trying to open socket on port " + port);
        
        ServerSocket serverSocket = new ServerSocket(port);
        
        new Thread(announceGame).start();
        
        try (Socket acceptedConnection = serverSocket.accept()){
            notConnected = false;
            this.clientSocket = acceptedConnection;

        }
        catch (Exception ex){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Announces the server in the local subnet (/24) until a client connects
     * @throws java.io.IOException
     */
    private void announceGame() throws IOException{
        
    }

    @Override
    public void run() {
        try {
            openConnection();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
