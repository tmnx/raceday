/*
 * TCSS 305 - Autumn 2018
 * 
 */
package model;

/**
 * Creates a racer object. A racer object contains the ID of the race,
 * the racer's name, the starting distance, and the enabled status of the racer.
 * 
 * @author Minh Nguyen
 * @version 29 November 2018
 */
public class Racer {
    
    /**
     * The racer ID.
     */
    private final int myRacerID;
    
    /**
     * The name of the racer.
     */
    private final String myName;
    
    /**
     * The starting distance of the racer.
     */
    private final double myStartDistance;
    
    /**
     * The enable / disable status of the racer.
     * Racer is by default enabled.
     */
    private boolean myEnabled;
    
    /**
     * Constructs a Racer object.
     * 
     * @param theRacerID is the racer ID number.
     * @param theName is the name of the racer.
     * @param theStartingDistance is the starting distance of the racer.
     */
    public Racer(final int theRacerID,
                 final String theName,
                 final double theStartingDistance) {
        
        myRacerID = theRacerID;
        myName = theName;
        myStartDistance = theStartingDistance;
        myEnabled = true;
    }
    
    /**
     * Get the current enabled status of the racer.
     * 
     * @return myEnabled is the enabled status of the racer.
     */
    public boolean isEnabled() {
        return myEnabled;
    }
    
    /**
     * Get the starting distance of the racer.
     * 
     * @return myStartDistance is the starting distance of racer.
     */
    public double getStartingDistance() {
        return myStartDistance;
    }
    
    /**
     * Get the name of the racer.
     * 
     * @return myName is the name of the racer.
     */
    public String getRacerName() {
        return myName;
    }
    
    
    /**
     * Set the enable status of the racer to the boolean value.
     * 
     * @param theFlag is the true/false enabled status of the racer.
     */
    public void setEnabled(final boolean theFlag) {
        myEnabled = theFlag;
    }
    
    /**
     * Returns the racer's ID.
     * 
     * @return myRacerID is the racer's ID
     */
    public int getMyRacerID() {
        return myRacerID;
    }

}
