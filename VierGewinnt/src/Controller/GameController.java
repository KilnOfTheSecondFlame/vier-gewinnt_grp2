/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       01.12.2016  RS20161201_01   Created the class and implemented its methods.  
* P. Baumann        15.12.2016  PB20161215_01   Changed the MouseListeners to ActionListeners; Implemented the handling of the ActionEvents
*/

package Controller;

import Model.*;
import View.GameView;
import View.Lobby;
import View.MainMenu;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

/**
 *
 * @author Rico Scheller
 */
public class GameController implements ActionListener{
    private final int GAMEBOARDWIDTH = 10;
    private final int GAMEBOARDHEIGHT = 10;
    private final Color[] COLORS = {Color.RED, Color.BLACK};
    
    private Color currentColor = COLORS[0];
    private Player currentPlayer;
    private boolean ourMove = true;
    
    private MainMenu mainMenu;
    private Lobby lobby;
    private GameView gameView;
    private Player self, opponent;
    private GameBoard gameBoard;
    private ConnectivityController connectivityController;
    
    // START THE GAME
    public static void main(String[] args) {
        GameController gameController = new GameController();
    }
    
    // Constructor
    public GameController(){
        mainMenu = new MainMenu(this);
        gameBoard = new GameBoard(GAMEBOARDWIDTH, GAMEBOARDHEIGHT);
        gameView = new GameView(this, GAMEBOARDWIDTH, GAMEBOARDHEIGHT);
        lobby = new Lobby();
        //gameView.setVisible(false);
        
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                mainMenu.setVisible(true);
            }
        });
    }
    
    // ACTION LISTENER
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton)e.getSource();
        if (mainMenu.ownsButton(sourceButton)){
            if (e.getActionCommand().equals("multiplayer")){
                
                // Hide the MainMenu
                mainMenu.setVisible(false);
                
                // Show the lobby
                lobby.setVisible(true);
                
                // Set the playerName
                String name = mainMenu.getName();
                if (name.equals("")){
                    name = "Player" + (int)Math.floor(Math.random()*100000);
                }
                
                // Create the connectivityController
                connectivityController = new ConnectivityController(lobby, name);
                connectivityController.searchAndAnnounce();
                
                boolean isConnected = false;
                while (!isConnected){
                    try {
                        //isConnected = connectivityController.getConnected();
                        this.wait(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        /*
        if (lobby.ownsButton(sourceButton)){
            if (e.getActionCommand().equals("exit")){
                
                // Hide lobby
                lobby.setVisible(false);
                // Show MainMenu
                mainMenu.setVisible(true);
                
                // Delete the reference to the connectivityController
                connectivityController = null;
            }
        }
        */
        
        if (gameView.ownsButton(sourceButton)){
            String actionCommand = e.getActionCommand();
            if (actionCommand.matches("button[0-9]+")){
                int column = Integer.parseInt(actionCommand.substring(6)) + 1;
                if (ourMove){
                    processMoves(column);
                }
            }
        }
    }
     
    // TASKS TO DO WHILE RUNNING
    private void processMoves(final int column){
        int row = gameBoard.addToken(column-1, new Token(currentColor));
        if (row>=0){
            gameView.updateGameBoard(row, column, currentColor);
            try {
                connectivityController.sendMove(column);
            } catch (IOException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(currentColor == COLORS[0]){
                currentColor = COLORS[1];
            }
            else{
                currentColor = COLORS[0];
            }

            if(currentPlayer == self){
                currentPlayer = opponent;
            }
            else{
                currentPlayer = self;
            }

            if(gameBoard.isGameWon()){
                // TODO - Option for a rematch
                JOptionPane.showMessageDialog(gameView.getGameFrame(), "Congratulations. You have won this round!", "Woohoo", PLAIN_MESSAGE);
            }

            new Thread(new Runnable(){
                @Override
                public void run(){
                    // Set ourMove to false to stop accepting new column input
                    ourMove = !ourMove;
                    try {
                        int move = connectivityController.receiveMove();
                        processMoves(column);
                        // Free the buttonHandling of gameView again
                        ourMove = !ourMove;
                    } catch (IOException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
        else {
            JOptionPane.showMessageDialog(gameView.getGameFrame(), "Column is full!\n Please choose another column", "Illegal!", PLAIN_MESSAGE);
        }
    }
}
