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
 */
package View;

import java.awt.*;
import javax.swing.*;
import Model.GameBoard;
import Controller.GameController;
import java.util.ArrayList;

/**
 * Shows the GameView, depicts the current state of the game and shows the playername and its score.
 * @author Melissa Beck
 */
public class GameView {
    // todo: get the data of the following
    private String currentPlayer;
    private String opponent;
    // private int currentPlayerScore;
    // private int opponentScore;
    private int xsize = 7;
    private int ysize = 8;
    private ArrayList<JButton> buttons;
    private GameBoard gameBoard;
    private CustomLabel[][] fields;
    private JFrame gameFrame;

    public static void main(String[] args) {
        // GameView gameView = new GameView();
    }
    
    /**
     * Creates an instance of the GameView.
     */
    public GameView(final GameController gameController) {
        // public GameView(final GameBoard gameBoard) {
        // this.gameBoard = gameBoard;
        // xsize = gameBoard.getWidth();
        // ysize = gameBoard.getHeight();
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
            buttons.get(i).addActionListener(gameController);
            buttons.get(i).setActionCommand("Gam");
        }

        // adding the fields
        for (int column = 0; column < ysize; column++) {
            for (int row = 0; row < xsize; row++) {
                fields[row][column] = new CustomLabel(Color.lightGray);
                fields[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                fields[row][column].setOpaque(true);
                fields[row][column].setBackground(Color.blue);
                gameGrid.add(fields[row][column]);
            }
        }

        JLabel lineLabel = new JLabel(" – ");
        // JLabel scoreTable = new JLabel("Score: " + currentPlayer + " " + currentPlayerScore + " – " + opponent + " " + opponentScore);

        JLabel currentPlayerLabel = new JLabel("" + currentPlayer);    
        currentPlayerLabel.setOpaque(true);
        currentPlayerLabel.setBackground(new Color(255, 0, 0, 40));
        // todo: only set border if it's my turn
        currentPlayerLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));

        JLabel opponentLabel = new JLabel("" + opponent);
        opponentLabel.setOpaque(true);
        opponentLabel.setBackground(new Color(255, 255, 0, 40));
        // todo: only set border if it's your turn
        opponentLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.yellow));

        northPanel.add(currentPlayerLabel);
        northPanel.add(lineLabel);
        northPanel.add(opponentLabel);

        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(gameGrid, BorderLayout.CENTER);
        // contentPane.add(scoreTable, BorderLayout.SOUTH);
        gameGrid.setLayout(new GridLayout(ysize + 1, xsize + 1));
        
        gameFrame.setSize(500, 600);
        gameFrame.setVisible(true);
    }
    
//    public void actionPerformed(ActionEvent e) {
//        Object o = e.getSource();
//        int index = 0;
//        if (buttons.contains(o)) {
//            index = buttons.indexOf(o);
//        }
//        setToken(index, 7, Color.RED);
//        // GameController.updateGameboard(index, 7, Color.RED);
//    }

    /**
     * Set the Token in the Gamboard.
     *
     * @param row The specific row to update
     * @param column The specific column to update
     * @param color The specific color from the player to update
     */
    public void setToken(int row, int column, Color color) {
        fields[row][column].updateColor(color);
    }

    /**
     * Get the buttons of the GameBoard.
     *
     * @return an ArrayList with the JButtons in it.
     */
    public ArrayList<JButton> getButtons() {
        return buttons;
    }
    
    public JFrame getGameFrame(){
        return this.gameFrame;
    }
}