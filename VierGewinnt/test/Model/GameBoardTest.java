/*
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* R. Scheller       25.11.2016  RS20161125_01   Created the class and the test cases for the gameboard.   
* R. Scheller       15.12.2016  RS20161215_01   Added new testing methods.
 */
package Model;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Darkmessage
 */
public class GameBoardTest {
    
    public GameBoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isColumnFull method, of class GameBoard.
     */
    @Test
    public void testIsColumnFullTrue() {
        System.out.println("isColumnFull \t\tTrue");
        int column = 0;
        GameBoard instance = new GameBoard(5, 3);
        boolean expResult = true;
        instance.addToken(column, new Token(Color.BLACK));
        instance.addToken(column, new Token(Color.BLACK));
        instance.addToken(column, new Token(Color.BLACK));
        boolean result = instance.isColumnFull(column);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isColumnFull method, of class GameBoard.
     */
    @Test
    public void testIsColumnFullFalse() {
        System.out.println("isColumnFull \t\tFalse");
        int column = 0;
        GameBoard instance = new GameBoard(5, 3);
        boolean expResult = false;
        instance.addToken(column, new Token(Color.BLACK));
        instance.addToken(column, new Token(Color.BLACK));
        boolean result = instance.isColumnFull(column);
        assertEquals(expResult, result);
    }

    /**
     * Test of addToken method, of class GameBoard.
     */
    @Test
    public void testAddToken() {
        System.out.println("addToken");
        int column = 0;
        Token token = new Token(Color.BLACK);
        GameBoard instance = new GameBoard(5, 5);
        int expResult = 9;
        int result = instance.addToken(column, token);
        assertEquals(expResult, result);
    }

    /**
     * Test of isGameWon method, of class GameBoard.
     */
    @Test
    public void testIsGameWonTrueVertical() {
        System.out.println("isGameWon \t\tTrue \tVertical");
        GameBoard instance = new GameBoard(5, 5);
        boolean expResult = true;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.BLACK));
        boolean result = instance.isGameWon();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isGameWon method, of class GameBoard.
     */
    @Test
    public void testIsGameWonFalse() {
        System.out.println("isGameWon \t\tFalse");
        GameBoard instance = new GameBoard(5, 5);
        boolean expResult = false;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.BLACK));
        boolean result = instance.isGameWon();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isGameWon method, of class GameBoard.
     */
    @Test
    public void testIsGameWonTrueHorizontal() {
        System.out.println("isGameWon \t\tTrue \tHorizontal");
        GameBoard instance = new GameBoard(5, 5);
        boolean expResult = true;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));
        boolean result = instance.isGameWon();
        assertEquals(expResult, result);
    }    
    
    /**
     * Test of isGameWon method, of class GameBoard.
     */
    @Test
    public void testIsGameWonFalseDifferentColor() {
        System.out.println("isGameWon \t\tFalse \tDifferent Color");
        GameBoard instance = new GameBoard(5, 5);
        boolean expResult = false;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.RED));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.RED));
        instance.addToken(4, new Token(Color.BLACK));
        boolean result = instance.isGameWon();
        assertEquals(expResult, result);
    }  
    
    /**
     * Test of isGameWon method, of class GameBoard.
     */
    @Test
    public void testIsGameWonTrueDiagonalFromUpperLeftToLowerRight() {
        System.out.println("isGameWon \t\tTrue \tDiagonal From Upper Left To Lower Right");
        GameBoard instance = new GameBoard(5, 5);
        boolean expResult = true;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.RED));
        instance.addToken(1, new Token(Color.RED));
        instance.addToken(2, new Token(Color.RED));
        instance.addToken(3, new Token(Color.RED));
        instance.addToken(1, new Token(Color.RED));
        instance.addToken(1, new Token(Color.RED));
        instance.addToken(2, new Token(Color.RED));
        boolean result = instance.isGameWon();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isGameWon method, of class GameBoard.
     */
    @Test
    public void testIsGameWonTrueDiagonalFromLowerLeftToUpperRight() {
        System.out.println("isGameWon \t\tTrue \tDiagonal From Lower Left To Upper Right");
        GameBoard instance = new GameBoard(5, 5);
        boolean expResult = true;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.RED));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.RED));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.RED));
        instance.addToken(4, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));

        boolean result = instance.isGameWon();
        assertEquals(expResult, result);
    }
    
    
    // START Added new methods. Signature: RS20161215_01
    /**
     * Test of getLastToken method, of class GameBoard.
     */
    @Test
    public void testLastTokenColor() {
        GameBoard instance = new GameBoard(5, 5);
        Color expResult = Color.BLACK;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.RED));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.RED));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.RED));
        instance.addToken(4, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));

        Color result = instance.getLastToken().token.getColor();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getLastToken method, of class GameBoard.
     */
    @Test
    public void testLastTokenColumn() {
        GameBoard instance = new GameBoard(5, 5);
        int expResult = 4;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.RED));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.RED));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.RED));
        instance.addToken(4, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));

        int result = instance.getLastToken().column;
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getLastToken method, of class GameBoard.
     */
    @Test
    public void testLastTokenRow() {
        GameBoard instance = new GameBoard(5, 5);
        int expResult = 2;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.RED));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.RED));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.RED));
        instance.addToken(4, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));

        int result = instance.getLastToken().row;
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isGameBoardFull method, of class GameBoard.
     */
    @Test
    public void testIsGameBoardFullFalse() {
        GameBoard instance = new GameBoard(5, 5);
        boolean expResult = false;
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.RED));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.RED));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(3, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.RED));
        instance.addToken(4, new Token(Color.BLACK));
        instance.addToken(4, new Token(Color.BLACK));

        boolean result = instance.isGameBoardFull();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isGameBoardFull method, of class GameBoard.
     */
    @Test
    public void testIsGameBoardFullTrue() {
        GameBoard instance = new GameBoard(3, 2);
        boolean expResult = true;
        instance.addToken(0, new Token(Color.BLACK));
        instance.addToken(0, new Token(Color.RED));
        instance.addToken(1, new Token(Color.BLACK));
        instance.addToken(1, new Token(Color.RED));
        instance.addToken(2, new Token(Color.BLACK));
        instance.addToken(2, new Token(Color.BLACK));

        boolean result = instance.isGameBoardFull();
        assertEquals(expResult, result);
    }
    // END Added new methods. Signature: RS20161215_01
}
