/*
* Class created by Pascal Baumann. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* P. Baumann       25.11.2016  PB20161125_01   Created the class, implemented method stubs and added JavaDoc.  
* P. Baumann       26.11.2016  PB20161126_01   Implemented openConnection()
* P. Baumann       26.11.2016  PB20161126_02   Started implementation of announceGame()
* P. Baumann       02.12.2016  PB20161202_01   Finished implementation of announceGame()
*/


package Opponent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pascal Baumann
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
    
    /**
     * Starts the client thread
     */
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
        
        // Starts the Announcement Thread
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
     * @param announceSocket DatagramSocket on which to listen for lobbies
     * @throws java.io.IOException
     */
    private void announceGame(final DatagramSocket announceSocket) throws IOException{
        byte[] sendData = new byte[DATALENGTH];
        
        /* TODO Review whats sent to the clients - also centralise this?
                Because a change here makes it necessary to change the handling in Opponent.Client
        */
        String dataPayload = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName())[1].toString() + " " + this.port;
        // TODO remove before shipping
        System.out.println("This is whats sent:" + dataPayload);
        // We have to provide the multicast address FF02::1 (All Nodes link local) in byte sequence
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
        
        // Prepare data in Datagramm
        sendData = dataPayload.getBytes();
        // Create the actual packet
        DatagramPacket sendPacket = new DatagramPacket(sendData, DATALENGTH, multicastGroup, announcementPort);
        // Send the packet over UDP
        announceSocket.send(sendPacket);
    }
}
