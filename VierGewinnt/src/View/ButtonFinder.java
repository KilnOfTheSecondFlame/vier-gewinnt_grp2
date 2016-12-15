package View;

import javax.swing.JButton;

/**
 *
 * @author Pascal
 */
public interface ButtonFinder {
    /**
     * Returns true if the implementing class owns the passed JButton
     * @param button
     * @return 
     */
    public boolean ownsButton(final JButton button);
}
