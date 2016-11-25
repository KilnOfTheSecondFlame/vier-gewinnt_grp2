/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*/

package Model;

/**
 *
 * @author Rico Scheller
 */
public class Player {
    private String name;
    private int score;
    
    public Player(String name){
        this.name = name;
        this.score = 0;
    }
    
    public String getName(){
        return name;
    }
    
    public int getScore(){
        return score;
    }
    
    public void incrementScore(){
        score++;
    }
    
    public void setScore(int score){
        this.score = score;
    }
}
