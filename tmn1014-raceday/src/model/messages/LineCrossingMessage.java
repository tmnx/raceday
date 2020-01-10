/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package model.messages;

/**
 * This class creates the Line Crossing Message object whose parent class
 * is Abstract Message.
 * 
 * @author Minh Nguyen
 * @version 17 November 2018
 */
public final class LineCrossingMessage extends AbstractMessage {
    
    /**
     * The racer ID in this message.
     */
    private final int myRacerID;
    
    /**
     * The new lap that the racer just started.
     */
    private final int myNewLap;
    
    /**
     * If the racer is now finished with the race or not.
     */
    private final boolean myIsFinished;
    
    /**
     * A constructor to create a line crossing message.
     * 
     * @param theRacerID is the racer ID.
     * @param theTimeStamp is the time stamp of the message.
     * @param theNewLap is the lap the racer just started.
     * @param theIsFinished determines if the racer is finished with the race.
     * @param theString is the string representation of the message.
     */
    public LineCrossingMessage(final int theTimeStamp,
                               final int theRacerID,
                               final int theNewLap,
                               final boolean theIsFinished,
                               final String theString) {
        // Calls the parent constructor to create line crossing message.
        super(theTimeStamp, theString);
        
        myRacerID = theRacerID;
        myNewLap = theNewLap;
        myIsFinished = theIsFinished;
    }
    
    /**
     * Returns the racer ID.
     * 
     * @return myRacerID is the racer ID in this message.
     */
    public int getRacerID() {
        return myRacerID;
    }
    
    /**
     * Returns the lap the racer just started.
     * 
     * @return myNewLap is the lap the racer just started.
     */
    public int getNewLap() {
        return myNewLap;
    }
    
    /**
     * Returns true if the racer is finished with the race or
     * false if it's just another lap.
     * 
     * @return myNewLap is the lap the racer just started.
     */
    public boolean getIsFinished() {
        return myIsFinished;
    }

}
