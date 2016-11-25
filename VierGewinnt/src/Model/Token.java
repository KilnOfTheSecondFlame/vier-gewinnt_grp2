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

import java.awt.Color;

/**
 * Represents a Token which can be placed on the {@code GameBoard}.
 * @author Rico Scheller
 */
public class Token {
   private Color color;
   
   /**
    * Creates an instance of the token.
    * @param color The color of the token.
    */
   public Token(Color color){
       this.color = color;
   }
   
   /**
    * Retrieves the {@code Color} of the token.
    * @return The {@code Color} of the token.
    */
   public Color getColor(){
       return color;
   }
}
