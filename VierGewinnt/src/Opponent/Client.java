/*
* Class created by Pascal Baumann. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* P. Baumann        25.11.2016  PB20161125_01   Created the class, implemented method stubs and added JavaDoc.                   
*/


package Opponent;

import java.util.HashMap;

/**
 *
 * @author Pascal
 */
public class Client {
    
    // Stores server with their hostname and on which port they are listening
    private HashMap<String, Integer> servers;
    
    /**
     * Default constructor without parameters
     */
    public Client(){
        
    }
    
    /**
     * Searches for games in the local subnet (/24) through listening for broadcast messages from servers. Notes the found games in his attribute servers.
     */
    public void searchGames(){
        
    }
    
    /** 
     * Connects to the specified server.
     */
    public void connect(){
        
    }
    
}
