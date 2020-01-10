/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package controller.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * An implementation of Action that will toggle between two states.
 * 
 * @author Minh Nguyen
 * @author Charles Bryan
 * @version 25 November 2018
 */
public class ToggleAction extends AbstractAction {

    
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = 8173117560174606146L;
    
    /**
     * The text to use when the toggle is true.
     */
    private final String myFirstText;
    
    /**
     * The text to use when the toggle is false.
     */
    private final String mySecondText;
    
    /**
     * The icon to use when the toggle is true.
     */
    private final String myFirstIcon;
    
    /**
     * The icon to use when the toggle is false.
     */
    private final String mySecondIcon;
    
    /**
     * The behavior to run when the toggle is true.
     */
    private final Runnable myFirstAction;
    
    /**
     * The behavior to run when the toggle is false.
     */
    private final Runnable mySecondAction;
    
    /**
     * A flag for Action toggling.
     */
    private boolean myFlag;
    
    /**
     * Creates a ToggleAction.
     * 
     * @param theFirstText the text of this Action in the original state.
     * @param theSecondText the text of this Action in the toggle state.
     * @param theFirstIcon the icon of this Action in the original state.
     * @param theSecondIcon the icon of this Action in the toggle state.
     * @param theFirstAction the behavior of this Action in the original state.
     * @param theSecondAction the behavior of this Action in the toggle state.
     */
    public ToggleAction(final String theFirstText,
                        final String theSecondText,
                        final String theFirstIcon,
                        final String theSecondIcon,
                        final Runnable theFirstAction,
                        final Runnable theSecondAction) {
        
        super(theFirstText);
        
        myFirstText = theFirstText;
        mySecondText = theSecondText;
        myFirstIcon = theFirstIcon;
        mySecondIcon = theSecondIcon;
        myFirstAction = theFirstAction;
        mySecondAction = theSecondAction;
        
        setIcon(new ImageIcon(theFirstIcon));
        
        myFlag = true;
    }
    
    /**
     * Look at the status of the toggle and correctly toggle the state.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
       
        if (myFlag) {
            mySecondAction.run();
            putValue(Action.NAME, mySecondText);
            setIcon(new ImageIcon(mySecondIcon));
        } else {
            myFirstAction.run();
            putValue(Action.NAME, myFirstText);
            setIcon(new ImageIcon(myFirstIcon));
        }
        
        myFlag = !myFlag;
    }
    
    /**
     * Helper method to set the Icon to both the Large and Small Icon values.
     * 
     * @param theIcon the icon to set for this Action 
     */
    protected void setIcon(final ImageIcon theIcon) {
        final ImageIcon icon = (ImageIcon) theIcon;
        
        // Set the large icon:
        final Image largeImage =
            icon.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        putValue(Action.LARGE_ICON_KEY, largeIcon);
        
        // Set the small icon:
        final Image smallImage =
            icon.getImage().getScaledInstance(12, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon smallIcon = new ImageIcon(smallImage);
        putValue(Action.SMALL_ICON, smallIcon);
    }

}
