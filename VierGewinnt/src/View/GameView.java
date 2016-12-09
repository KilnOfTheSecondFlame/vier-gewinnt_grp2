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
*/

package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Shows the GameView, depicts the current state of the game and shows the playername and its score.
 * @author Melissa Beck
 */
public class GameView {    
    // todo: get the data of the following
    private String currentPlayer;
    private String opponent;
    private int currentPlayerScore;
    private int opponentScore;
    // the xsize and the ysize are not very dynamic
    private int xsize = 7;
    private int ysize = 6;
    
    /**
    * Creates an instance of the GameView.
    */
    public GameView(final int sxSize, final int sySize) {
        xsize = sxSize;
        ysize = sySize;
        JFrame frame = new JFrame("Vier gewinnt");        
        Container contentPane = frame.getContentPane();
        JPanel gameGrid = new JPanel();
        JLabel[][] fields = new JLabel[xsize][ysize];
        JButton[] buttons = new JButton[xsize];

        // adding the buttons
        for (int i = 0; i < xsize; i++) {
            buttons[i] = new JButton("" + (i + 1));
            gameGrid.add(buttons[i]);
        }
        
        // adding the fields
        for (int column = 0; column < ysize; column++) {
            for (int row = 0; row < xsize; row++) {
                fields[row][column] = new JLabel();
                fields[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                fields[row][column].setBorder(new LineBorder(Color.blue, 3));
                gameGrid.add(fields[row][column]);
            }
        }

        JLabel memberName = new JLabel("Your name is: " + currentPlayer);
        JLabel scoreTable = new JLabel("Score: " + currentPlayer + " " + currentPlayerScore + " – " + opponent + " " + opponentScore);
        
        contentPane.add(memberName, BorderLayout.PAGE_START);
        contentPane.add(gameGrid, BorderLayout.CENTER);
        contentPane.add(scoreTable, BorderLayout.PAGE_END);
        gameGrid.setLayout(new GridLayout(xsize, ysize + 1));
        
        frame.pack();
        frame.setVisible(true);
    }
    
//    public static void main(String [ ] args) {
//        GameView view = new GameView();
//    }
}