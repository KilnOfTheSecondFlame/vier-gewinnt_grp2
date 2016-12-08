/*
* Class created by Melissa Beck. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* M. Beck       02.12.2016  MB20161202_01   Created the class    
* M. Beck       08.12.2016  MB20161206_01   Implemented the GUI without functionality      
*/

package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Shows the Lobby: the user can choose the game.
 * @author Melissa Beck
 */
public class Lobby {
    private JList<String> gameList;
    private JButton exitButton;
    private ArrayList gameListArray; 
    
    /**
    * Creates an instance of the Lobby.
    */
    public Lobby() {
        JFrame frame = new JFrame();
               
        gameListArray = new ArrayList<>();
        gameListArray.add("Pascal Baumann");
        gameListArray.add("Rico Schneller");
        gameListArray.add("Melissa Beck");
        gameListArray.add("Max Mustermann");
        gameListArray.add("Melanie Musterfrau");
        gameList = new JList(gameListArray.toArray());
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(gameList);
 
        exitButton = new JButton("exit");
        JPanel buttonPane = new JPanel();
        buttonPane.add(exitButton);
 
        frame.add(listScrollPane, BorderLayout.CENTER);
        frame.add(buttonPane, BorderLayout.PAGE_END);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
    
//    public static void main(String [ ] args) {
//        Lobby lobby = new Lobby();
//    }

}
