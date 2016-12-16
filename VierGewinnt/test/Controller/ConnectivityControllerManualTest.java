/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       16.12.2016  RS20161216_01   Created the class and implemented its methods. 
 */
package Controller;

import View.Lobby;
import java.awt.EventQueue;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JOptionPane;

/**
 *
 * @author Darkmessage
 */
public class ConnectivityControllerManualTest {
    private ActionListener actionListener;
    private ConnectivityController cc;
    private Lobby lobby;
    private boolean exitLobby;
    
    public ConnectivityControllerManualTest(){
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equalsIgnoreCase("exit")){
                    int chosenOption = JOptionPane.showConfirmDialog(lobby, "You clicked <<Exit>>. Do you really wanna quit?", "Are you sure?", JOptionPane.YES_NO_OPTION);
                    
                    if(chosenOption == JOptionPane.YES_OPTION){
                        lobby.setVisible(false);
                        exitLobby = true;
                    }
                }
            }
        };
        
        exitLobby = false;
        lobby = new Lobby(actionListener);
        
        cc = new ConnectivityController(lobby, "Testplayer 1");
        
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                lobby.setVisible(true);
            }
        });
    }
    
    private void test() throws IOException{
        cc.searchAndAnnounce();
        
        while(!exitLobby && !cc.isConnected()){
            
        }
        
        if(cc.isConnected()){
            JOptionPane.showMessageDialog(lobby, "You are now connected to " + cc.getOpponentName());
            
            new Thread(new Runnable(){
                @Override
                public void run(){
                    int received = 0;
                    while(true){
                        try {
                            received = cc.receiveMove();
                            JOptionPane.showMessageDialog(lobby, "You received a move: " + received);
                        } catch (IOException ex) {
                            Logger.getLogger(ConnectivityControllerManualTest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
            
            while(true){
               String send = JOptionPane.showInputDialog("What number do you want to send?");
               
               cc.sendMove(Integer.valueOf(send));
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        new ConnectivityControllerManualTest().test();
    }
}