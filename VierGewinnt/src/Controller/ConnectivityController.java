/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       09.12.2016  RS20161209_01   Created the class and implemented its methods. 
* R. Scheller       15.12.2016  RS20161215_01   Added some changes.
* R. Scheller       16.12.2016  RS20161216_01   Added interactivity with the lobby.
*/

package Controller;

import Opponent.Client;
import Opponent.Server;
import View.Lobby;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map.Entry;
import javax.swing.JList;

/**
 * Represents the controller which handles the connections between the players.
 * @author Rico Scheller
 */
public class ConnectivityController{
    private Client client;
    private Server server;
    private final Lobby lobby;
    private final Thread serverThread;
    private final Thread clientThread;
    private Thread waitForConnect;
    private boolean connected;          // Signature: RS20161216_01
    private String opponentName;        // Signature: RS20161216_01
    private boolean isClient;
    private final MouseAdapter mouseAdapter;  // Signature: RS20161216_01
    
    /**
     * Creates an instance of the ConntectivityController.
     * @param lobby The GUI-object where the available connections are to be displayed.
     * @param playerName The name of the player who searches a match.
     */
    public ConnectivityController(Lobby lobby, String playerName){
        this.lobby = lobby;
        client = new Client();
        server = new Server(playerName);
        clientThread = new Thread(client);
        serverThread = new Thread(server);
        connected = false;     // Signature: RS20161216_01
        isClient = false;       // Signature: PB20161218_01
        
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    JList list = (JList) e.getSource();
                    String selectedConnection = (String)list.getSelectedValue();
                    
                    if(selectedConnection != null && !selectedConnection.isEmpty()){
                        try {
                            connectTo(selectedConnection);
                        } catch (IOException ex) {
                            Logger.getLogger(ConnectivityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }
            }
        };
        
        lobby.registerMouseAdapter(mouseAdapter); // Signature: RS20161216_01
    }
    
    /**
     * Starts broadcasting messages which announce an open game.
     * Simultaneously listens to open games which can be joined.
     * This method interacts with the {@code lobby} by refreshing its gameListArray and calling the repaint method on it.
     */
    public void searchAndAnnounce(){
        serverThread.start();
        clientThread.start();
        
        waitForConnect =  new Thread(new Runnable(){
            @Override
            public void run(){
                while(server != null && !server.isConnected()){
                    try {
                        lobby.getGameListArray().clear();
                        
                        for(Entry<String, Integer> entry :client.getServers().entrySet()){
                            lobby.getGameListArray().add(entry.getKey() + " " + entry.getValue());
                        }
                        
                        lobby.repaint();
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConnectivityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if(server != null){
                   // Someone connected to us thus we are the server.
                    client = null; 
//                    opponentName = server.getOpponentName();
                    connected = true;
                }
            }
        });
        waitForConnect.start();
    }
    
    /**
     * Connects to a selected host.
     * @param opponent The String with the information about the opponent.
     * @throws IOException 
     */
    public void connectTo(String opponent) throws IOException{
        String[] strings = opponent.split("/");
        String[] nameAndPort = strings[2].split(" ");
        
        if(client != null){
            client.connect(InetAddress.getByName(strings[1].split("%")[0]), Integer.valueOf(nameAndPort[1]));   
            
            // We connected to someone thus we are the client.
            client.stopClient();        // Stop the client thread. Signature: RS20161215_01
            server = null;
            
            isClient = true;
            opponentName = nameAndPort[0];
            connected = true;
        }
    }
    
    /**
     * Sends a move which has been made to the opponent.
     * @param column The column in which a new Token has been added.
     * @return true if the move could be sent, false otherwise.
     * @throws IOException 
     */
    public boolean sendMove(int column) throws IOException{
        if(client != null){ 
            return client.sendMove(column); 
        } 
        else if(server != null){ 
            return server.sendMove(column); 
        }
        
        return false;
    }
    
    /**
     * Waits until a move from the opponent has been made.
     * @return The row in which the opponent added a new Token. -1 if nobody is connected.
     * @throws IOException 
     */
    public int receiveMove() throws IOException{
        if(client != null){
            return client.receiveMove();
        } else if(server != null){
            return server.receiveMove();
        }
        
        return -1;
    }
    
    /** Signature: RS20161216_01
     * Checks if there is a connection to another computer.
     * @return true if moves can be sent and received, false otherwise.
     */
    public boolean isConnected(){
        return connected;
    }
    
    /**
     * Return the name of the connected player.
     * @return the opponent name.
     */
    public String getOpponentName(){
        return opponentName;
    }
    
    /**
     * Checks if we are the client or the server
     * @return true if we are the client
     */
    public boolean isClient(){
        return isClient;
    }
}
