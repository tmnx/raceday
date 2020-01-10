/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package model.messages;

/**
 * This class creates the Telemetry message object whose parent class is
 * Abstract Message.
 * 
 * @author Minh Nguyen
 * @version 17 November 2018
 */
public final class TelemetryMessage extends AbstractMessage {
    
    /**
     * The racer ID in this message.
     */
    private final int myRacerID;
    
    /**
     * The distance traveled this lap by the racer ID.
     */
    private final double myDistance;
    
    /**
     * The current lap number.
     */
    private final int myLap;
    
    /**
     * The constructor to create a Telemetry Message.
     * 
     * @param theTimeStamp is the time stamp of the message.
     * @param theRacerID is the racer ID in this message.
     * @param theDistance is the distance traveled in this lap.
     * @param theLap is the lap number.
     * @param theString is the string representation of the message.
     */
    public TelemetryMessage(final int theTimeStamp,
                            final int theRacerID,
                            final double theDistance,
                            final int theLap,
                            final String theString) {
        
        // Calls the parent constructor to create a Telemetry message.
        super(theTimeStamp, theString);
        
        myRacerID = theRacerID;
        myDistance = theDistance;
        myLap = theLap;
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
     * Returns the distance traveled in this lap.
     * 
     * @return myDistance is the distance traveled in this lap.
     */
    public double getDistance() {
        return myDistance;
    }
    
    /**
     * Returns the lap number.
     * 
     * @return myLap is the lap number.
     */
    public int getLap() {
        return myLap;
    }

}
