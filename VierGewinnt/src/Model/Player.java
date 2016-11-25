/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       25.11.2016  RS20161125_01   Created the class, implemented its methods and added JavaDoc.   
*/

package Model;

/**
 * Represents a player.
 * @author Rico Scheller
 */
public class Player {
    private String name;
    private int score;
    
    /**
     * Creates an instance of the player.
     * @param name The name of the player.
     */
    public Player(String name){
        this.name = name;
        this.score = 0;
    }
    
    /**
     * Retrieves the player's name.
     * @return The name of the player.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Retrieves the player's score.
     * @return The score of the player.
     */
    public int getScore(){
        return score;
    }
    
    /**
     * Increments the player's score by 1.
     */
    public void incrementScore(){
        score++;
    }
    
    /**
     * Sets the player's score.
     * @param score The score of the player.
     */
    public void setScore(int score){
        this.score = score;
    }
}
