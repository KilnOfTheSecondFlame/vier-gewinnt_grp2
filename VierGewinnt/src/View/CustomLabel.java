/*
* Class created by Melissa Beck. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* M. Beck       11.12.2016  MB20161211_01   Created the class, implemented methods and added JavaDoc.
*/

package View;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * Creates a JLabel with a Circle in it.
 * @author Melissa
 */
public class CustomLabel extends JLabel {
    Color color;
        
    public CustomLabel(Color color) {
        super();
        this.color = color;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(color);
        g.fillOval(10, 10, 42, 42);
    }
    
    public void updateColor(Color color) {
        this.color = color;
        repaint();
    }
}