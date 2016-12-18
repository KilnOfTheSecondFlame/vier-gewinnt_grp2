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
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Shows the MainMenu: the user can choose a multiplayer game and enter his name.
 * @author Melissa Beck
 */
public class MainMenu implements ButtonFinder{
    private JLabel enterNameLabel;
    private JTextField userName;
    private JButton multiplayerButton;
    private JFrame mainMenuFrame;

    /**
    * Creates an instance of the MainMenu.
     * @param listener
    */
    public MainMenu(final ActionListener listener) {
        mainMenuFrame = new JFrame();
        Container contentPane = mainMenuFrame.getContentPane();
        
        enterNameLabel = new JLabel("Enter your name here");
        userName = new JTextField(20);
        multiplayerButton = new JButton("Multiplayer");
        
        multiplayerButton.addActionListener(listener);
        multiplayerButton.setActionCommand("multiplayer");

        contentPane.setLayout(new GridLayout(0,1,0,10));
        contentPane.add(enterNameLabel);
        contentPane.add(userName);
        contentPane.add(multiplayerButton);
        
        mainMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainMenuFrame.setResizable(false);
        mainMenuFrame.setSize(300, 120);
        mainMenuFrame.setVisible(true);
        
        mainMenuFrame.setTitle("Main Menu");
        mainMenuFrame.setLocationRelativeTo(null);
    }
    
    public void setVisible(final boolean modVis){
        mainMenuFrame.setVisible(modVis);
    }

    @Override
    public boolean ownsButton(final JButton button) {
        return button.equals(multiplayerButton);
    }
    
    public String getName(){    // Signatur: PB20161215_01
        return userName.getText();
    }
}
