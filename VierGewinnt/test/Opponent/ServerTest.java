/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opponent;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Pascal
 */
public class ServerTest {
    
    public ServerTest() {
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
     * Test of run method, of class Server.
     * @throws java.io.IOException
     */
    @Test
    public void testRun() throws IOException {
        System.out.println("run");
        Server instance = new Server();
        new Thread(instance).start();
        Socket socket = new Socket(Inet6Address.getByName("::1"), 44444);
    }
}
