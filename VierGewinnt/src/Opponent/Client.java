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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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
    
    //CONSTANTS
    // Stores server with their hostname and on which port they are listening
    private final HashMap<String, Integer> servers;
    // Length of the Announcement messages
    private final int DATALENGTH = 1024;
    // Port where announcement are received
    private final int ANNOUNCEMENT_RECEIVE_PORT = 44446;
    
    
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
    public void searchGames(final DatagramSocket gameListenerSocket){
        byte[] receiveData = new byte[DATALENGTH];
        DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            System.out.println("Opponent.Client.searchGames() - Receiving...");
            gameListenerSocket.receive(receivedPacket);
            System.out.println("UDP datagram received");
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
            serverport = Integer.parseInt(messageHandling[1].trim());
        } catch (java.lang.NumberFormatException ex){
            System.out.println(ex.getMessage());
            if (messageHandling[1].trim().equals("44444")) serverport = 44444;
            else serverport = 0;
        }
        
        synchronized(servers){
            servers.putIfAbsent(server, serverport);
        }
    }
    
    /** 
     * Connects to the specified server.
     */
    public void connect(){
        
    }

    @Override
    public void run() {
        try {
            System.out.println("Open UDP Socket on " + ANNOUNCEMENT_RECEIVE_PORT);
            DatagramSocket announcementSocket = new DatagramSocket(ANNOUNCEMENT_RECEIVE_PORT, InetAddress.getByName("::"));
            while (isReceiving){
                searchGames(announcementSocket);
                // TODO remove before shipping
                System.out.println("List of servers:");
                servers.keySet().forEach((server) -> {
                    System.out.println(server + " " + servers.get(server));
                });
            }
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    // TODO Remove before shipping
    public HashMap<String, Integer> getServers() {
        return servers;
    }
}
