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
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import javax.swing.JTable;

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
    private boolean clickAllowed;
    
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
        gameView = new GameView(this);
        lobby = new Lobby();
        
        lobby.setVisible(false);
        gameView.setVisible(false);
        
        /*
        lobby.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getComponent().getName().equals("gameList") && e.getClickCount() >= 2){
                    JList list = (JList) e.getSource();
                    int index = list.locationToIndex(e.getPoint());
                    
                    // TODO - Process clicked entry and establish the game
                }
                else if(e.getComponent().getName().equals("exitButton")){
                    mainMenu.setVisible(true);
                    mainMenu.setVisible(false);
                }
            }
        });
        
        gameView.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(clickAllowed && e.getComponent().getName().equals("gameGrid")){
                    clickAllowed = false;
                    processMoves(e);
                }
            }
        });
        */
        
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
                
            }
        }
        
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
        
        if (gameView.ownsButton(sourceButton)){
            // TODO Implement what to do when button is pressed on Column
            
        }
    }
     
    // TASKS TO DO WHILE RUNNING
    private void processMoves(MouseEvent e){
        JTable table = (JTable) e.getSource();
        int column = table.columnAtPoint(e.getPoint());

        gameBoard.addToken(column, new Token(currentColor));
        
        // TODO - Send move to opponent.
        
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
                boolean waitingOnOpponent = true;

                while(waitingOnOpponent){
                // TODO - Wait for opponents move and process it.
                }

                clickAllowed = true;
            }
        }).start();
    }
}
