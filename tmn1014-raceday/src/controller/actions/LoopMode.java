/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package controller.actions;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_OVER_TIME;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import model.PropertyChangeEnabledRaceControls;

/**
 * Creates a toggle action between the single race and loop race mode.
 * 
 * @author Minh Nguyen
 * @version 29 November 2018
 */
public class LoopMode extends ToggleAction implements PropertyChangeListener {
    
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -6291477656951200726L;
    
    /**
     * The status of loop race mode (true = on / false = off).
     */
    private static boolean myLoopOn;
    
    /**
     * The play pause button.
     */
    private final AbstractButton myPlayPauseButton;
    
    /**
     * The race object to be controlled.
     */
    private final PropertyChangeEnabledRaceControls myRace;

    /**
     * Constructs a single/loop race mode.
     * Create Action to toggle between single race and loop race.
     * 
     * @param theRace is the race to be controlled.
     * @param thePlayPauseButton is the play pause mode.
     * @param theSingleRaceText is the text for single race button.
     * @param theLoopRaceText is the text for the loop race button.
     * @param theSingleRaceIcon is the icon text for the single race mode.
     * @param theLoopRaceIcon is the icon text for the loop race mode.
     */
    public LoopMode(final PropertyChangeEnabledRaceControls theRace,
                    final PlayPauseMode thePlayPauseButton,
                    final String theSingleRaceText,
                    final String theLoopRaceText,
                    final String theSingleRaceIcon,
                    final String theLoopRaceIcon) {
        
        // Create Action to toggle between single race and loop race.
        super(theSingleRaceText,
              theLoopRaceText,
              theSingleRaceIcon,
              theLoopRaceIcon,
            () -> myLoopOn = false,
            () -> myLoopOn = true);
        
        // Default loop mode is false.
        myLoopOn = false;
        
        myRace = theRace;
        myPlayPauseButton = new JButton(thePlayPauseButton);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if (PROPERTY_OVER_TIME.equals(theEvent.getPropertyName()) && isLoop()) {
            // reset the time to zero and the slider to value 0.
            myRace.moveTo(0);
            // Programmatically click to toggle button to play mode.
            myPlayPauseButton.doClick();
        }
    }
    
    /**
     * Returns whether the loop mode is selected or not.
     * 
     * @return if the loop mode is selected.
     */
    private boolean isLoop() {
        return myLoopOn;
    }

}
