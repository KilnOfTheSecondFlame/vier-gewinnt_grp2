/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*/

package Model;

import java.awt.Color;

/**
 *
 * @author Rico Scheller
 */
public class Token {
   private Color color;
   
   public Token(Color color){
       this.color = color;
   }
   
   public Color getColor(){
       return color;
   }
}
