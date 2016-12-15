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
*/

package View;

import java.awt.*;
import javax.swing.*;
import Model.GameBoard;

/**
 * Shows the GameView, depicts the current state of the game and shows the playername and its score.
 * @author Melissa Beck
 */
public class GameView implements ButtonFinder{    
    // todo: get the data of the following
    private String currentPlayer;
    private String opponent;
    private int currentPlayerScore;
    private int opponentScore;
    private int xsize = 7;
    private int ysize = 8;
    private JButton[] buttons;
    private GameBoard gameBoard;
    private CustomLabel[][] fields;
    private JFrame gameFrame;
    
    /**
    * Creates an instance of the GameView.
    */
    public GameView() {
    // public GameView(final GameBoard gameBoard) {
        // this.gameBoard = gameBoard;
        // xsize = gameBoard.getWidth();
        // ysize = gameBoard.getHeight();
        gameFrame = new JFrame("Vier gewinnt");
        Container contentPane = gameFrame.getContentPane();
        JPanel gameGrid = new JPanel();
        fields = new CustomLabel[xsize][ysize];
        buttons = new JButton[xsize];

        // adding the buttons
        for (int i = 0; i < xsize; i++) {
            buttons[i] = new JButton("" + (i + 1));
            gameGrid.add(buttons[i]);
        }
        
        // adding the fields
        for (int column = 0; column < ysize; column++) {
            for (int row = 0; row < xsize; row++) {
                fields[row][column] = new CustomLabel(Color.RED);
                fields[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                fields[row][column].setOpaque(true);
                fields[row][column].setBackground(Color.blue);
                gameGrid.add(fields[row][column]);
            }
        }
        
        JLabel memberName = new JLabel("Your name is: " + currentPlayer);
        JLabel scoreTable = new JLabel("Score: " + currentPlayer + " " + currentPlayerScore + " – " + opponent + " " + opponentScore);
        
        contentPane.add(memberName, BorderLayout.PAGE_START);
        contentPane.add(gameGrid, BorderLayout.CENTER);
        contentPane.add(scoreTable, BorderLayout.PAGE_END);
        gameGrid.setLayout(new GridLayout(ysize + 1, xsize + 1));
        
        gameFrame.setSize(500, 600);
        gameFrame.setVisible(true);
    }
    
     /**
     * Updates the Token in the Gamboard.
     * @param row The specific row to update
     * @param column The specific column to update
     * @param color The specific color from the player to update
     */
    public void updateGameboard(int row, int column, Color color) {
        fields[row][column].updateColor(color);
    }
    
    /**
     * Get the buttons of the GameBoard.
     * @return an array with the JButtons in it.
     */
    public JButton[] getButtons() {
        return buttons;
    }
    
    public JFrame getGameFrame(){
        return this.gameFrame;
    }

    @Override
    public boolean ownsButton(JButton button) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
