/*
* Class created by Pascal Baumann. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* P. Baumann        25.11.2016  PB20161125_01   Created the class, implemented method stubs and added JavaDoc.                   
* P. Baumann        26.11.2016  PB20161126_01   Started implemenation of searchGames()   
* P. Baumann        03.12.2016  PB20161203_01   Finished implementation of searchGames()
*/


package Opponent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pascal Baumann
 */
public class Client implements Runnable{
    // Attributes
    private boolean isReceiving = true;
    private Socket serverSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer ;
    
    //CONSTANTS
    // Stores server with their hostname and on which port they are listening
    private final HashMap<String, Integer> servers;
    // Length of the Announcement messages
    private final int DATALENGTH = 1024;
    // Port where announcement are received
    private final int ANNOUNCEMENT_RECEIVE_PORT = 44446;
    private final String SEND_SUCCESSFUL_MESSAGE = "Successful";
    
    
    /**
     * Default constructor without parameters
     */
    public Client(){
        this.servers = new HashMap<>();
    }
    
    /**
     * Searches for games in the local subnet through listening for broadcast messages from servers. Notes the found games in his attribute servers.
     * @param gameListenerSocket Socket on which the client listens for Server announcement messages
     */
    public void searchGames(final MulticastSocket gameListenerSocket){
        byte[] receiveData = new byte[DATALENGTH];
        DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
        
        try {
            gameListenerSocket.receive(receivedPacket);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Handling of received packets
        String receiveMessage = new String(receivedPacket.getData());
        String[] messageHandling = receiveMessage.split(" ");
        String server = messageHandling[0].trim();
        Integer serverport;
        // We don't want our thread to hardcrash when IntParsing fails
        try{
            // Apparently parseInt does not like wihtespace... Hence we use trim()
            serverport = Integer.parseInt(messageHandling[1].trim());
        } catch (java.lang.NumberFormatException ex){
            System.out.println(ex.getMessage());
            if (messageHandling[1].trim().equals("44444")) serverport = 44444;
            else serverport = 0;
        }
        System.out.println("Server: " + server + " & Serverport: " + serverport);
        synchronized(servers){
            servers.putIfAbsent(server, serverport);
        }
    }
    
    /** 
     * Connects to the specified server.
     * @param serverAddress IPv6 address of server
     * @param serverPort Port number of server
     * @throws java.io.IOException If the client can't open a socket
     */
    public void connect(InetAddress serverAddress, int serverPort) throws IOException{
        this.serverSocket = new Socket(serverAddress, serverPort);
        // Thanks to https://systembash.com/a-simple-java-tcp-server-and-tcp-client/
        this.outToServer = new DataOutputStream(this.serverSocket.getOutputStream());
        this.inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
        // Stop searching for games
        this.isReceiving = false;
    }
    
    /**
     * Sends a move to the server (the column in which the token should be placed)
     * @param column The column in which the token shall be placed as an int
     * @return true if the message was received
     * @throws IOException 
     */
    public boolean sendMove(int column) throws IOException{
        boolean result = false;
        this.outToServer.writeInt(column);
        this.outToServer.flush();
        String receiveMessage = this.inFromServer.readLine();
        if (receiveMessage.equals(SEND_SUCCESSFUL_MESSAGE)) result = true;
        return result;
    }
    
    /**
     * Receives a move from the server
     * @return The column in which the server wants to place a token as an integer
     * @throws IOException 
     */
    public int receiveMove() throws IOException{
        char[] receiveBuf = new char[4];
        this.inFromServer.read(receiveBuf);
        int receivedMove =  0;
        for (int i = 0; i < 4; i++){
            int shift = (3-i)*8;
            receivedMove += ((byte) receiveBuf[i]) << shift;
        }
        this.outToServer.writeChars(SEND_SUCCESSFUL_MESSAGE + "\n");
        this.outToServer.flush();
        return receivedMove;
    }
    
    /**
     * Starts the Client to search for open Servers
     * 
     */
    @Override
    public void run() {
        try {
            System.out.println("Open UDP Socket on " + ANNOUNCEMENT_RECEIVE_PORT);
            MulticastSocket announcementSocket = new MulticastSocket(ANNOUNCEMENT_RECEIVE_PORT);
            try {
                announcementSocket.joinGroup(InetAddress.getByName("FF02::FC"));
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (isReceiving){
                searchGames(announcementSocket);
                
                // TODO remove before shipping
                System.out.println("List of servers:");
                servers.keySet().forEach((server) -> {
                    System.out.println(server + " " + servers.get(server));
                });
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    public HashMap<String, Integer> getServers() {
        return servers;
    }
 
    /**
     * Stops the client from searching open games
     */
    public void stopClient(){
        this.isReceiving = false;
    }
}
