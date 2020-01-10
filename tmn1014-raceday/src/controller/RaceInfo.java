/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_INFO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Creates a race information object.
 * 
 * @author Minh Nguyen
 * @version 27 November 2018
 */
public class RaceInfo implements PropertyChangeListener {
    
    /**
     * The separator for formatted.
     */
    public static final String SEPARATOR = ":";
    
     /**
     * A newline character.
     */
    public static final String NEWLINE = "\n";
    
    /**
     * The string of race information.
     */
    private String myRaceInfo;
    
    /**
     * A constructor to make an race information string.
     */
    public RaceInfo() {
        super();
        myRaceInfo = "";
    }
    
    /**
     * When the race is finished loading, the race info is updated.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if (PROPERTY_RACE_INFO.equals(theEvent.getPropertyName())) {
            
            myRaceInfo = (String) theEvent.getNewValue();
        }
    }
    
    @Override
    public String toString() {
        final String[] raceInfo = myRaceInfo.split(SEPARATOR);
        final StringBuilder builder = new StringBuilder();
        
        // append race name
        builder.append(raceInfo[0]);
        
        // append track type
        builder.append(NEWLINE);
        builder.append("Track type: ");
        builder.append(raceInfo[1]);
        
        // append total time
        builder.append(NEWLINE);
        builder.append("Total time: ");
        builder.append(Time.formatTime(Long.parseLong(raceInfo[raceInfo.length - 1])));
        
        // append lap distance
        builder.append(NEWLINE);
        builder.append("Lap distance: ");
        builder.append(raceInfo[raceInfo.length - 2]);
        
        return builder.toString();
    }

}
