/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*/

package Model;

import java.awt.Color;

/**
 *
 * @author Rico Scheller
 */
public class GameBoard {
    private Token[][] grid;
    private boolean gameIsWon;
    
    public GameBoard(int width, int height){
        grid = new Token[width][height];
        gameIsWon = false;
    }
    
    public boolean isColumnFull(int column){
        // TODO
        return true;
    }
    
    public void addToken(int column, Token token){
        // TODO
        
        gameIsWon = checkIfWon(column, token.getColor());
    }
    
    private boolean checkIfWon(int column, Color color){
        // TODO
        return true;
    }
    
    public boolean isGameWon(){
        return gameIsWon;
    }
}
