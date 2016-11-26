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
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
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
    private int announcementPort;
    private boolean notConnected = true;
    private Socket clientSocket;
    
    // Constants
    private final int ANNOUNCE_WAIT = 5000;
    private final Runnable announceGame;
    // Length of the Announcement messages
    private final int DATALENGTH = 1024;
    
    /**
     * Default constructor
     */
    public Server(){
        this.port = 44444;
        this.announcementPort = 44445;
        // Inner class, so we can have a thread listening for connection attempts, and one announcing the server
        announceGame = () -> {
            try {
                // Open the socket to be used for sending announcement messages
                DatagramSocket announcerSocket = new DatagramSocket(this.announcementPort);
                // While we haven't had a client connection, send messages continously
                while (!notConnected){
                    Server.this.announceGame(announcerSocket);
                    
                    try {
                        Thread.sleep(ANNOUNCE_WAIT);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        
    }
    
    @Override
    public void run() {
        try {
            openConnection();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            System.out.println(acceptedConnection);
            notConnected = false;
            this.clientSocket = acceptedConnection;
        }
        catch (Exception ex){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Announces the server in the local subnet until a client connects
     * @throws java.io.IOException
     */
    public void announceGame(final DatagramSocket announceSocket) throws IOException{
        byte[] sendData = new byte[DATALENGTH];
        
        String dataPayload = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName())[1].toString() + " " + this.port;
        // TODO remove before shipping
        System.out.println("This is whats sent:" + dataPayload);
        Inet6Address multicastGroup = (Inet6Address) Inet6Address.getByAddress(new byte[]{
            (byte) 0xFF, 0x02,
            0x00, 0x00,
            0x00, 0x00,
            0x00, 0x00, 
            0x00, 0x00, 
            0x00, 0x00,
            0x00, 0x00,
            0x00, 0x01
        });
        System.out.println(multicastGroup);
        
    }
}
