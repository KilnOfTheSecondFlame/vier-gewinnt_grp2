/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opponent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @throws java.io.IOException
     */
    @Test
    public void testSearchGames() throws IOException {
        System.out.println("searchGames");

        MulticastSocket gameListenerSocket = new MulticastSocket(44446);
        gameListenerSocket.joinGroup(InetAddress.getByName("FF02::FC"));

        Client instance = new Client();

        Server Serverinstance = new Server();
        new Thread(Serverinstance).start();

        instance.searchGames(gameListenerSocket);

        HashMap<String, Integer> servers = instance.getServers();

        assertEquals(servers.size(), 1);
    }

    /**
     * Test of connect method, of class Client.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        Client instance = new Client();
        Server serverInstance = new Server();
        new Thread(serverInstance).start();
        try {
            instance.connect(InetAddress.getByName("::1"),44444);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getServers method, of class Client.
     */
    @Test
    public void testGetServers() {
        System.out.println("getServers");
        Client instance = new Client();
        HashMap<String, Integer> expResult = new HashMap<>();
        HashMap<String, Integer> result = instance.getServers();
        assertEquals(expResult, result);
    }
    
}
