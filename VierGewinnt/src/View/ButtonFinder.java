/*
* Interface created by Pascal Baumann. Hochschule Luzern - Informatik. Copyright 2016
*
* Change log:
*
* Who               When        Signature       What
* ------------------------------------------------------------------------------------------------------------------
* P. Baumann        15.12.2016  PB20161215_01   Defined the interface; Defined the method
*/
package View;

import javax.swing.JButton;

/**
 *
 * @author Pascal
 */
public interface ButtonFinder {
    public boolean ownsButton(final JButton button);
}
