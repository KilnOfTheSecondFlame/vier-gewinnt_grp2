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

/**
 *
 * @author Pascal
 */
public class Server {
    // Attributes
    private int port;
    
    
    /**
     * Default constructor
     */
    public Server(){
        this.port = 44444;
    }
    
    /**
     * Opens a socket on which the server listens for clients which want to join
     * @throws java.io.IOException
     */
    public void openConnection() throws IOException{
        
    }
    
    /**
     * Announces the server in the local subnet (/24) until a client connects
     * @throws java.io.IOException
     */
    public void announceGame() throws IOException{
        
    }
    
}
