/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       25.11.2016  RS20161125_01   Created the class, implemented its methods and added JavaDoc.   
* R. Scheller       15.12.2016  RS20161215_01   Added getLastToken().
* R. Scheller       15.12.2016  RS20161215_02   Added isGameBoardFull().
*/

package Model;

import java.awt.Color;

/**
 * Represents a gameboard with tokens on it.
 * Can determine if the game has been won or if a column is already filled.
 * @author Rico Scheller
 */
public class GameBoard {
    private Token[][] grid;             // Structure:   0 1 2 .. CONSTWIDTH-1
                                        //              1
                                        //              2
                                        //              ..
                                        //              CONSTHEIGHT-1
    private boolean gameIsWon;
    private LastTokenHelper lastToken;  // Signature: RS20161215_01
    private final int CONSTWIDTH;
    private final int CONSTHEIGHT;
    
    /**
     * Creates an instance of the gameboard. 
     * The gameboard will have {@code width} * {@code height} cells.
     * @param width Number of horizontal cells where Tokens can be placed.
     * @param height Number of vertical cells where Tokens can be placed.
     */
    public GameBoard(int width, int height){
        grid = new Token[width][height];
        CONSTWIDTH = width;
        CONSTHEIGHT = height;
        gameIsWon = false;
    }
    
    /**
     * Checks if a column is already full.
     * @param column The zero-based index of the column to test.
     * @return true if a Token cannot be inserted into this column, false otherwise.
     */
    public boolean isColumnFull(int column){
        if(column >= CONSTWIDTH || grid[column][0] != null){
            return true;
        } else{
            return false;
        }
    }
    
    /**
     * Adds the {@code token} to the gameboard in the specified {@code column}.
     * This will also update the status of the board if the played {@code Token} lead to a win.
     * @param column The zero-based index of the column where the {@code token} gets added.
     * @param token The {@code Token} to be added to the gameboard.
     * @return true if the {@code token} has been added, false otherwise.
     */
    public int addToken(int column, Token token){
        if(isColumnFull(column) || token == null) return -1;
        
        int row;
        
        // Search for already existing Tokens in this row and insert the new one a row above them.
        for(row = CONSTHEIGHT-1;row >= 0; row--){
            if(grid[column][row] == null){
                grid[column][row] = token;
                break;
            }
        }
        
        lastToken = new LastTokenHelper(token, column, row);        // Signature: RS20161215_01
        gameIsWon = checkIfWon(column, row, token.getColor());
        
        return row;
    }
    
    /**
     * Checks if the winning condition has occured.
     * @param column The zero-based index of the last added {@code Token}.
     * @param row The zero-based index of the last added {@code Token}.
     * @param color {@code Color} of the last added {@code Token}
     * @return true if the last added {@code Token} won the game, false otherwise.
     */
    private boolean checkIfWon(final int column, final int row, final Color color){
        
        return  checkIfWonHorizontal(column, row, color) ||
                checkIfWonVertical(column, row, color) ||
                checkIfWonDiagonalFromUpperLeftToLowerRight(column, row, color) ||
                checkIfWonDiagonalFromLowerLeftToUpperRight(column, row, color);
    }
    
    private boolean checkIfWonHorizontal(int column, int row, Color color){
        int count = 1;
        int columnLeft = column-1;
        int columnRight = column+1;
        boolean searchSide1 = true;
        boolean searchSide2 = true;
        
        while(true){
           if(searchSide1 && columnLeft >= 0){              // Left side can be checked.
               if(grid[columnLeft][row] != null && grid[columnLeft][row].getColor().equals(color)){
                   count++;
                   columnLeft--;
                   if(count == 4) return true;
               } else{
                   searchSide1 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide1 = false;
           }
           
           if(searchSide2 && columnRight < CONSTWIDTH){     // Right side can be checked.
               if(grid[columnRight][row] != null && grid[columnRight][row].getColor().equals(color)){
                   count++;
                   columnRight++;
                   if(count == 4) return true;
               } else{
                   searchSide2 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide2 = false;
           }
           
           if(!searchSide1 && !searchSide2) break;
        }
        
        return false;
    }
    
    private boolean checkIfWonVertical(int column, int row, Color color){
        int count = 1;
        int rowHigh = row-1;
        int rowLow = row+1;
        boolean searchSide1 = true;
        boolean searchSide2 = true;
        
        while(true){
           if(searchSide1 && rowHigh >= 0){                 // Higher rows can be checked.
               if(grid[column][rowHigh] != null && grid[column][rowHigh].getColor().equals(color)){
                   count++;
                   rowHigh--;
                   if(count == 4) return true;
               } else{
                   searchSide1 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide1 = false;
           }
           
           if(searchSide2 && rowLow < CONSTHEIGHT){         // Lower rows can be checked.
               if(grid[column][rowLow] != null && grid[column][rowLow].getColor().equals(color)){
                   count++;
                   rowLow++;
                   if(count == 4) return true;
               } else{
                   searchSide2 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide2 = false;
           }
           
           if(!searchSide1 && !searchSide2) break;
        }
        
        return false;
    }
    
    private boolean checkIfWonDiagonalFromUpperLeftToLowerRight(int column, int row, Color color){
        int count = 1;
        int columnLeft = column-1;
        int columnRight = column+1;
        int rowHigh = row-1;
        int rowLow = row+1;
        boolean searchSide1 = true;
        boolean searchSide2 = true;
        
        while(true){
           if(searchSide1 && rowHigh >= 0 && columnLeft >= 0){                 // Higher rows to the left can be checked.
               if(grid[columnLeft][rowHigh] != null && grid[columnLeft][rowHigh].getColor().equals(color)){
                   count++;
                   rowHigh--;
                   columnLeft--;
                   if(count == 4) return true;
               } else{
                   searchSide1 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide1 = false;
           }
           
           if(searchSide2 && rowLow < CONSTHEIGHT && columnRight < CONSTWIDTH){         // Lower rows to the right can be checked.
               if(grid[columnRight][rowLow] != null && grid[columnRight][rowLow].getColor().equals(color)){
                   count++;
                   rowLow++;
                   columnRight++;
                   if(count == 4) return true;
               } else{
                   searchSide2 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide2 = false;
           }
           
           if(!searchSide1 && !searchSide2) break;
        }
        
        return false;
    }
    
    private boolean checkIfWonDiagonalFromLowerLeftToUpperRight(int column, int row, Color color){
        int count = 1;
        int columnLeft = column-1;
        int columnRight = column+1;
        int rowHigh = row-1;
        int rowLow = row+1;
        boolean searchSide1 = true;
        boolean searchSide2 = true;
        
        while(true){
           if(searchSide1 && rowHigh >= 0 && columnRight < CONSTWIDTH){                 // Higher rows to the right can be checked.
               if(grid[columnRight][rowHigh] != null && grid[columnRight][rowHigh].getColor().equals(color)){
                   count++;
                   rowHigh--;
                   columnRight++;
                   if(count == 4) return true;
               } else{
                   searchSide1 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide1 = false;
           }
           
           if(searchSide2 && rowLow < CONSTHEIGHT && columnLeft >= 0){         // Lower rows to the left can be checked.
               if(grid[columnLeft][rowLow] != null && grid[columnLeft][rowLow].getColor().equals(color)){
                   count++;
                   rowLow++;
                   columnLeft--;
                   if(count == 4) return true;
               } else{
                   searchSide2 = false;                     // Found a different color, so stop searching this side.
               }
           } else{
               searchSide2 = false;
           }
           
           if(!searchSide1 && !searchSide2) break;
        }
        
        return false;
    }

    /**
     * Checks if the game is won.
     * @return true if a player has won the game, false otherwise.
     */
    public boolean isGameWon(){
        return gameIsWon;
    }
    
    /**
     * Gives a view of the {@code Tokens} on the board.
     * @return A clone of the tokens on the board.
     */
    public Token[][] getBoard(){
        return grid.clone();
    }
    
    /** Signature: RS20161215_01
     * Gets the last added {@code Token} and its position.
     * @return The last added Token and its position.
     */
    public LastTokenHelper getLastToken(){
        return lastToken;
    }
    
    /** Signature: RS20161215_02
     * Checks if the {@code GameBoard} is full.
     * @return true if no Token can be placed anymore, false otherwise.
     */
    public boolean isGameBoardFull(){
        for(int i=0; i < CONSTWIDTH; i++){
            if(!isColumnFull(i)){
                return false;
            }
        }
        
        return true;
    }
}
