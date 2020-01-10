/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package controller.actions;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_OVER_TIME;

import controller.SliderPanel;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Creates a toggle action between play and pause mode.
 * 
 * @author Minh Nguyen
 * @version 30 November 2018
 */
public class PlayPauseMode extends ToggleAction implements PropertyChangeListener {
    
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -2529822496191390186L;
    
    /**
     * The name of the play button.
     */
    private final String myPlayButtonName;
    
    /**
     * The name of the pause button.
     */
    private final String myPauseButtonName;
    
    /**
     * The string of the pause icon.
     */
    private final String myPauseIcon;

    /**
     * The string of the play icon.
     */
    private final String myPlayIcon;
    
    /**
     * The slider panel.
     */
    private final SliderPanel mySliderPanel;
    
    /**
     * The behavior to run when the toggle is true.
     */
    private final Runnable myPauseAction;
    
    /**
     * The behavior to run when the toggle is false.
     */
    private final Runnable myPlayAction;
    
    /**
     * A flag for Action toggling.
     */
    private boolean myFlag;



    /**
     * Constructs a play/pause race mode.
     * Create action to toggle between play and pause.
     * 
     * @param theSliderPanel is the slider panel.
     * @param thePlayText is the text of the play button.
     * @param thePauseText is the text of the pause button.
     * @param thePlayIcon is the play icon string.
     * @param thePauseIcon is the pause icon string.
     * @param theFirstAction is the pause action.
     * @param theSecondAction is the play action.
     */
    public PlayPauseMode(final SliderPanel theSliderPanel,
                         final String thePlayText,
                         final String thePauseText,
                         final String thePlayIcon,
                         final String thePauseIcon,
                         final Runnable theFirstAction,
                         final Runnable theSecondAction) {
        
        // Create action to toggle between play and pause.
        super(thePlayText,
              thePauseText,
              thePlayIcon,
              thePauseIcon,
              theFirstAction,
              theSecondAction);
        
        myPlayButtonName = thePlayText;
        myPauseButtonName = thePauseText;
        myPlayIcon = thePlayIcon;
        myPauseIcon = thePauseIcon;
        myPauseAction = theFirstAction;
        myPlayAction = theSecondAction;
        mySliderPanel = theSliderPanel;
        myFlag = true;
        
    }

    /**
     * When there is no more time left to advance, the play icon is displayed.
     * The pause action is running.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_OVER_TIME.equals(theEvent.getPropertyName())) {
            final AbstractButton b = new JButton(this);
            // Programmatically click to toggle to pause mode.
            b.doClick();
        }
    }
    
    /**
     * Override the parent's actionPerformed method.
     * When the play action is running, the slider will be disabled.
     * Otherwise, the slider will be enabled when paused.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        
        if (myFlag) {
            myPlayAction.run();
            putValue(Action.NAME, myPauseButtonName);
            setIcon(new ImageIcon(myPauseIcon));
            mySliderPanel.getComponent(0).setEnabled(false);
        } else {
            myPauseAction.run();
            putValue(Action.NAME, myPlayButtonName);
            setIcon(new ImageIcon(myPlayIcon));
            mySliderPanel.getComponent(0).setEnabled(true);
        }
        
        myFlag = !myFlag;
    }

}
