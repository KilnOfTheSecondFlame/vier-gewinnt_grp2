/*
* Class created by Pascal Baumann. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* P. Baumann        25.11.2016  PB20161125_01   Created the class, implemented method stubs and added JavaDoc.                   
* P. Baumann        26.11.2016  PB20161126_01   Started implemenation of searchGames()                  
*/


package Opponent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pascal Baumann
 */
public class Client implements Runnable{
    
    // Stores server with their hostname and on which port they are listening
    private final HashMap<String, Integer> servers;
    
    // Length of the Announcement messages
    private final int DATALENGTH = 1024;
    
    /**
     * Default constructor without parameters
     */
    public Client(){
        servers = new HashMap<>();
    }
    
    /**
     * Searches for games in the local subnet through listening for broadcast messages from servers. Notes the found games in his attribute servers.
     * @param gameListenerSocket Socket on which the client listens for Server announcement messages
     */
    public void searchGames(final DatagramSocket gameListenerSocket){
        byte[] receiveData = new byte[DATALENGTH];
        DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            gameListenerSocket.receive(receivedPacket);
            // TODO Handling of announcement message
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // TODO Remove when handling is implemented
        String server = "::1";
        Integer port = 44444;
                
        synchronized(servers){
            servers.putIfAbsent(server, port);
        }
    }
    
    /** 
     * Connects to the specified server.
     */
    public void connect(){
        
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // TODO Remove before shipping
    public HashMap<String, Integer> getServers() {
        return servers;
    }
}
