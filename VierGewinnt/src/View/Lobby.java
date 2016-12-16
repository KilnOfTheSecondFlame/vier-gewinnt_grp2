/*
* Class created by Melissa Beck. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* M. Beck       02.12.2016  MB20161202_01   Created the class    
* M. Beck       08.12.2016  MB20161206_01   Implemented the GUI without functionality   
* R. Scheller   09.12.2016  RS20161209_01   Added few changes.
* M. Beck       16.12.2016  MB20161216_01   Added ActionListener to exitButton and implemented method ownsbutton  
* R. Scheller   16.12.2016  RS20161216_01   Added MouseListener to gameList. Implemented repaint method. Delete things which were only present for testing purposes.
*/

package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

/**
 * Shows the Lobby: the user can choose the game.
 * @author Melissa Beck
 */
public class Lobby extends JFrame implements ButtonFinder{ // Extended from JFrame. Signatur: RS20161209_01
    private JList<String> gameList;
    private ArrayList<String> gameListArray;    // Included Generics. Signatur: RS20161209_01
    private JButton exitButton;
    private JFrame lobbyFrame;
    DefaultListModel<String> listModel;         // Signature: RS20161216_01
    
    /**
    * Creates an instance of the Lobby.
     * @param listener The ActionListener which will be handling the button events.
    */
    public Lobby(final ActionListener listener) {
        lobbyFrame = new JFrame();
               
        gameListArray = new ArrayList<>();
        
        listModel = new DefaultListModel<>();       // Signature: RS20161216_01
        
        gameList = new JList(listModel);  // Neu wird das listModel der JList übergeben. Signature: RS20161216_01
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(gameList);
 
        exitButton = new JButton("exit");
        JPanel buttonPane = new JPanel();
        buttonPane.add(exitButton);
        exitButton.addActionListener(listener);
        exitButton.setActionCommand("exit");
        
        lobbyFrame.add(listScrollPane, BorderLayout.CENTER);
        lobbyFrame.add(buttonPane, BorderLayout.PAGE_END);
        lobbyFrame.setSize(200, 200);
        lobbyFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public ArrayList<String> getGameListArray(){
        return gameListArray;
    }
    
    public JList<String> getGameList(){
        return gameList;
    }
    
    @Override
    public void setVisible(final boolean modVis){
        lobbyFrame.setVisible(modVis);
    }

    @Override
    public boolean ownsButton(JButton button) {
        return button.equals(exitButton);
    }  
    
    // Signature: RS20161216_01
    @Override
    public void repaint(){
        listModel.clear();
        
        for(String s:gameListArray){
            listModel.addElement(s);
        }
    }
    
    /** Signature: RS20161216_01
     * Registers the MouseAdapter for MouseEvents which happen on JList.
     * @param mouseAdapter The MouseAdapter which will be handling the MouseEvents from the JList. 
     */
    public void registerMouseAdapter(MouseAdapter mouseAdapter){
        gameList.addMouseListener(mouseAdapter);
    }
}
