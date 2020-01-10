/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package model.messages;

import java.util.ArrayList;
import java.util.List;

/**
 * The class creates the Leader board message object whose parent class is
 * Abstract Message.
 * 
 * @author Minh Nguyen
 * @version 17 Novemner 2018
 */
public final class LeaderboardMessage extends AbstractMessage {
    
    /**
     * A list of racer IDs that is sorted by place.
     */
    private final List<Integer> myListOfRaceIDs;
    
    /**
     * The constructor to create a Leader board message.
     * 
     * @param theTimeStamp is the time stamp of the current message.
     * @param theRacerIDs is the list containing the racer IDs.
     * @param theString is the string representation of the message.
     */
    public LeaderboardMessage(final int theTimeStamp,
                              final List<Integer> theRacerIDs,
                              final String theString) {
        // Calls the parent constructor to create a Leader board message.
        super(theTimeStamp, theString);
        
        myListOfRaceIDs = new ArrayList<>(createListOfRacerIDs(theRacerIDs));
    }
    
    /**
     * Returns a list of the racer IDs sorted by place.
     * 
     * @return list is the list of racer IDs.
     */
    public List<Integer> getRacerIDsList() {
        return myListOfRaceIDs;
    }
    
    /**
     * Returns a List of racer IDs.
     * 
     * @param theRacerIDs is an array of racer IDs.
     * @return list is the list of race IDs.
     */
    private List<Integer> createListOfRacerIDs(final List<Integer> theRacerIDs) {
        final List<Integer> list = new ArrayList<>();
        
        for (final int racerID : theRacerIDs) {
            list.add(racerID);
        }
        
        return list;
    }
    
}
