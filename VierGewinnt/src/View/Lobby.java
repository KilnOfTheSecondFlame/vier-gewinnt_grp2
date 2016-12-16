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
*/

package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
    
    /**
    * Creates an instance of the Lobby.
    */
    public Lobby(final ActionListener listener) {
        lobbyFrame = new JFrame();
               
        gameListArray = new ArrayList<>();
        setGameListArray();
        
        gameList = new JList(gameListArray.toArray());
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(gameList);
 
        exitButton = new JButton("exit");
        JPanel buttonPane = new JPanel();
        buttonPane.add(exitButton);
        exitButton.addActionListener(listener);
        exitButton.setActionCommand("exitbutton");
        
        lobbyFrame.add(listScrollPane, BorderLayout.CENTER);
        lobbyFrame.add(buttonPane, BorderLayout.PAGE_END);
        lobbyFrame.setSize(200, 200);
        lobbyFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public void setGameListArray() {
        gameListArray.add("Pascal Baumann");
        gameListArray.add("Rico Schneller");
        gameListArray.add("Melissa Beck");
        gameListArray.add("Max Mustermann");
        gameListArray.add("Melanie Musterfrau");
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
    
}
