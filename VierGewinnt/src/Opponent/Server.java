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
* P. Baumann       03.12.2016  PB20161203_01   Modified ports for announcement
* R. Scheller      10.12.2016  RS20161210_01   Added method to check if someone has connected to the server.
*/


package Opponent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
    private boolean notConnected = true;
    private boolean isConnected = false;    // Since notConnected is used in stopServer(), we can't use it to test if someone has connected. Signature: RS20161210_01
    private Socket clientSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private String playerName;
        
    // Constants
    private final int ANNOUNCE_WAIT = 5000;
    private final int ANNOUNCEMENT_SEND_PORT = 44445;
    private final int ANNOUNCEMENT_RECEIVE_PORT = 44446;
    private final String SEND_SUCCESSFUL_MESSAGE = "Successful";
    
    private final Runnable announceGame;
    
    /**
     * Constructor for class
     * @param playerName
     */
    public Server(final String playerName){
        this.playerName = playerName;
        this.port = 44444;

        // Inner class, so we can have a thread listening for connection attempts, and one announcing the server
        announceGame = () -> {
            try {
                // Open the socket to be used for sending announcement messages
                DatagramSocket announcerSocket = new DatagramSocket(Server.this.ANNOUNCEMENT_SEND_PORT);
                // While we haven't had a client connection, send messages continously
                while (notConnected){
                    Server.this.announceGame(announcerSocket);
                    
                    try {
                        Thread.sleep(ANNOUNCE_WAIT);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }catch (IOException ex) {
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
            
            // TODO Remove - debug
            System.out.println(sendMove(2));
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
        new Thread(this.announceGame).start();
        Socket acceptedConnection = serverSocket.accept();
        // Setting this disables the announcement messages
        notConnected = false;
        this.clientSocket = acceptedConnection;
        // Thanks to https://systembash.com/a-simple-java-tcp-server-and-tcp-client/
        inFromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        outToClient = new DataOutputStream(this.clientSocket.getOutputStream());
    
        // Indicate that a connection is established. Signature: RS20161210_01
        isConnected = true;
    }
    
    /**
     * Announces the server in the local subnet until a client connects
     * @param announceSocket DatagramSocket on which to listen for lobbies
     * @throws java.io.IOException
     */
    private void announceGame(final DatagramSocket announceSocket) throws IOException{
        byte[] sendData;        
        /* TODO Review whats sent to the clients - also centralise this?
                Because a change here makes it necessary to change the handling in Opponent.Client
        */
        String dataPayload = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName())[1].toString() + "/" + this.playerName + " " + this.port;
        // TODO remove before shipping
        System.out.println("This is whats sent:" + dataPayload);
        // We have to provide the multicast address FF02::FC (All Nodes link local) in byte sequence
        InetAddress multicastGroup = InetAddress.getByName("FF02::FC");
        
        // Prepare data in Datagramm
        sendData = dataPayload.getBytes();
        // Create the actual packet
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, multicastGroup, ANNOUNCEMENT_RECEIVE_PORT);
        // Send the packet over UDP
        announceSocket.send(sendPacket);
    }
    
    /**
     * Sends a move to the client
     * @param column The column in which the token shall be placed as an int
     * @return true if the message was received
     * @throws IOException 
     */
    public boolean sendMove(int column) throws IOException{
        boolean result = false;
        this.outToClient.writeInt(column);
        this.outToClient.flush();
        String receiveMessage = this.inFromClient.readLine();
        if (receiveMessage.equals(SEND_SUCCESSFUL_MESSAGE)) result = true;
        return result;
    }
    
    /** 
     * Receives a move from the client
     * @return The column in which the client wants to place a token as an integer
     * @throws java.io.IOException
     */
    public int receiveMove() throws IOException{
        char[] receiveBuf = new char[4];
        this.inFromClient.read(receiveBuf);
        int receivedMove =  0;
        for (int i = 0; i < 4; i++){
            int shift = (3-i)*8;
            receivedMove += ((byte) receiveBuf[i]) << shift;
        }
        this.outToClient.writeChars(SEND_SUCCESSFUL_MESSAGE + "\n");
        this.outToClient.flush();
        return receivedMove;
    }
    
    /**
     * Stops the server from announcing it's presence
     */
    public void stopServer(){
        this.notConnected = false;
        Thread.currentThread().interrupt();
    }
    
    // Signature: RS20161210_01
    /** 
     * Checks if someone has connected to this server.
     * @return true if a connection has been established, false otherwise.
     */
    public boolean isConnected(){
        return isConnected;
    }
}