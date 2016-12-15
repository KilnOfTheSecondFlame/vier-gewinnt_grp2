/*
 * Class created by Pascal Baumann. Hochschule Luzern - Informatik. Copyright 2016
 */
package Controller;

import View.Lobby;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darkmessage
 */
public class ConnectivityControllerCommandTest {
    ConnectivityController cc;
    Lobby lobby;
    Boolean stopListing;
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    public void test() throws IOException{
        System.out.println("Please choose a player name:");
        String playerName = in.readLine();
        
        lobby = new Lobby();
        cc = new ConnectivityController(lobby, playerName);
        cc.searchAndAnnounce();
        
        stopListing = false;
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(!stopListing){
                    System.out.println("Available games, type <<stop>> to stop searching:");
                    
                    for(String s:lobby.getGameListArray()){
                        System.out.println(s);
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConnectivityControllerCommandTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
        
        while(true){
            if(in.readLine().equalsIgnoreCase("stop")){
                stopListing = true;
                break;
            }
        }
        
        System.out.println("<<connect>> to your opponent or <<receive>> a move");
        String next = in.readLine();
        if(next.equals("connect")){
            System.out.println("Please provide the IP-Adress.");
            String connectTo = in.readLine();
            cc.connectTo(connectTo);
            System.out.println("Successfully connected. <<send>> or <<receive>>");
            next = in.readLine();
        }
        
        if(next.equalsIgnoreCase("send")){
            System.out.println("Which column do you want to send?");
            int index = Integer.valueOf(in.readLine());
            cc.sendMove(index);
        } else if(next.equalsIgnoreCase("receive")){
            int index = cc.receiveMove();
            System.out.println("Received Move: " + index);
        }
    }
    
    public static void main(String[] args) throws IOException {
        new ConnectivityControllerCommandTest().test();
    }
}
