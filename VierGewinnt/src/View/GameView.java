/*
 * Class created by Melissa Beck. Hochschule Luzern - Informatik. Copyright 2016
 *
 * Change log:
 *
 * Who               When        Signature       What
 * ------------------------------------------------------------------------------------------------------------------
 * M. Beck       01.12.2016  MB20161201_01   Created the class, implemented method stubs and added JavaDoc.
 * M. Beck       06.12.2016  MB20161206_01   Implemented the GUI without functionality
 * M. Beck       08.12.2016  MB20161208_01   Implemented the GUI without functionality
 * M. Beck       11.12.2016  MB20161211_01   Implemented the setting of a token
 * M. Beck       15.12.2016  MB20161215_01   Changed the northpanel and tried out event listeners
 * M. Beck       16.12.2016  MB20161216_01   few changes
*/
package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Shows the GameView, depicts the current state of the game and shows the playername.
 * @author Melissa Beck
 */
public class GameView implements ButtonFinder{    
    private String serverName;
    private String clientName;
    // private int serverScore;
    // private int clientScore;
    private ArrayList<JButton> buttons;
    private CustomLabel[][] fields;
    private JFrame gameFrame;
    private JLabel serverLabel;
    private JLabel clientLabel;
    private JButton exitButton;
    private int updateCounter;

    /**
     * Creates an instance of the GameView.
     * @param listener for listening of the events
     * @param boardWidth the width of the GameBoard
     * @param boardHeight the height of the GameBoard
     */
    public GameView(final ActionListener listener, final int boardWidth, final int boardHeight) {
        int xsize = boardWidth;
        int ysize = boardHeight;
        gameFrame = new JFrame("Vier gewinnt");
        Container contentPane = gameFrame.getContentPane();
        JPanel gameGrid = new JPanel();
        JPanel northPanel = new JPanel();
        fields = new CustomLabel[xsize][ysize];
        buttons = new ArrayList<>();

        // adding the buttons
        for (int i = 0; i < xsize; i++) {
            buttons.add(new JButton("" + (i + 1)));
            gameGrid.add(buttons.get(i));
            buttons.get(i).addActionListener(listener);
            buttons.get(i).setActionCommand("button"+ i);
        }

        // adding the fields
        for (int row = 0; row < xsize; row++) { 
            for (int column = 0; column < ysize; column++) {
                fields[row][column] = new CustomLabel(Color.lightGray);
                fields[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                fields[row][column].setOpaque(true);
                fields[row][column].setBackground(Color.blue);
                gameGrid.add(fields[row][column]);
            }
        }
        
        JLabel lineLabel = new JLabel(" – ");
        // JLabel scoreTable = new JLabel("Score: " + serverName + " " + serverScore + " – " + clientName + " " + clientScore);

        serverLabel = new JLabel("" + serverName);    
        serverLabel.setOpaque(true);
        serverLabel.setBackground(new Color(255, 0, 0, 40));
        serverLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));

        clientLabel = new JLabel("" + clientName);
        clientLabel.setOpaque(true);
        clientLabel.setBackground(new Color(255, 255, 0, 40));
        
        exitButton = new JButton("exit");
        JPanel exitPane = new JPanel();
        exitPane.add(exitButton);
        exitButton.addActionListener(listener);
        exitButton.setActionCommand("exit");

        northPanel.add(serverLabel);
        northPanel.add(lineLabel);
        northPanel.add(clientLabel);

        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(gameGrid, BorderLayout.CENTER);
        contentPane.add(exitPane, BorderLayout.SOUTH);
        // contentPane.add(scoreTable, BorderLayout.SOUTH);
        gameGrid.setLayout(new GridLayout(ysize + 1, xsize + 1));
        
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setSize(600, 700);
    }
    
     /**
     * Updates the Gamboard with the token and the marking for the current player.
     * @param row the row of the placed token
     * @param column the column of the placed token
     * @param color the color of the placed token (only use Color.red or Color.yellow)
     */
    public void updateGameBoard(int row, int column, Color color) {
        fields[row][column-1].updateColor(color);
        if((updateCounter % 2) == 1) {
            serverLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
            clientLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.yellow));
        } else {
            clientLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.yellow));
            serverLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.red));
        }
        updateCounter++;
    }

    public void setPlayerNames(String firstName, String lastName) {
        this.serverName = firstName;
        this.clientName = lastName;
    }

    
    public JFrame getGameFrame(){
        return this.gameFrame;
    }

    @Override
    public boolean ownsButton(JButton button) {
        return (buttons.contains(button) || button.equals(exitButton));
    }
    
    public void setVisible(final boolean modVis){
        gameFrame.setVisible(modVis);
    }
}
