/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       16.12.2016  RS20161216_01   Created the class and implemented its methods. 
 */
package View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Darkmessage
 */
public class LobbyManualTest {
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(lobby, "Exit clicked.");
        }
    };
    
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getClickCount() == 2){
                JOptionPane.showMessageDialog(lobby, "Double click.");
            }
        }
    };
    
    protected Lobby lobby = new Lobby(actionListener);
    
    public LobbyManualTest(){
        lobby.registerMouseAdapter(mouseAdapter);
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                lobby.setVisible(true);
            }
        });
    }
    
    public static void main(String[] args) throws InterruptedException {
        LobbyManualTest lobbyTest = new LobbyManualTest();
        
        Thread.sleep(5000);
        
        lobbyTest.lobby.getGameListArray().clear();
        lobbyTest.lobby.getGameListArray().add("Erster Eintrag");
        lobbyTest.lobby.getGameListArray().add("Zweiter Eintrag");
        lobbyTest.lobby.getGameListArray().add("Dritter Eintrag");
        lobbyTest.lobby.repaint();
    }
    
}
