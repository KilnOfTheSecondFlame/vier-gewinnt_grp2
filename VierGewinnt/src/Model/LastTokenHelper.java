/*
* Class created by Rico Scheller. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       15.12.2016  RS20161215_01   Created the class, implemented its methods and added JavaDoc.   
*/

package Model;

/**
 * Represents the last added Token and its position.
 * @author Rico Scheller
 */
public class LastTokenHelper {
    public Token token;
    public int column;
    public int row;
    
    /**
     * Creates an instance of {@code LastTokenHelper}.
     * @param token The Token.
     * @param column The Token's column.
     * @param row The Token's row;
     */
    public LastTokenHelper(Token token, int column, int row){
        this.token = token;
        this.column = column;
        this.row = row;
    }
}
