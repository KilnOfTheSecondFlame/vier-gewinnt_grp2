/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opponent;

import java.net.DatagramSocket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pascal
 */
public class ClientTest {
    
    public ClientTest() {
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
     * Test of searchGames method, of class Client.
     */
    @Test
    public void testSearchGames() {
        System.out.println("searchGames");
        DatagramSocket gameListenerSocket = null;
        Client instance = new Client();
        instance.searchGames(gameListenerSocket);
        assertEquals(instance.getServers().size(), 1);
    }

    /**
     * Test of connect method, of class Client.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        Client instance = new Client();
        instance.connect();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class Client.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        Client instance = new Client();
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
