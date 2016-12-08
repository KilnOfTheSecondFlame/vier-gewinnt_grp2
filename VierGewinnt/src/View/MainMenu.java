/*
* Class created by Melissa Beck. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* M. Beck       01.12.2016  MB20161201_01   Created the class
* M. Beck       08.12.2016  MB20161208_01   Implemented the GUI without functionality
*/

package View;

import java.awt.*;
import javax.swing.*;

/**
 * Shows the MainMenu: the user can choose a multiplayer game and enter his name.
 * @author Melissa Beck
 */
public class MainMenu {
    private JLabel enterNameLabel;
    private JTextField userName;
    private JButton multiplayerButton;

    /**
    * Creates an instance of the MainMenu.
    */
    public MainMenu() {
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        
        enterNameLabel = new JLabel("Enter your name here");
        userName = new JTextField(20);
        multiplayerButton = new JButton("Multiplayer");

        contentPane.setLayout(new GridLayout(0,1,0,10));
        contentPane.add(enterNameLabel);
        contentPane.add(userName);
        contentPane.add(multiplayerButton);
        
        frame.setResizable(false);
        frame.setSize(200, 120);
        frame.setVisible(true);
    }
    
//    public static void main(String [ ] args) {
//        MainMenu mainMenu = new MainMenu();
//    }
    
}
