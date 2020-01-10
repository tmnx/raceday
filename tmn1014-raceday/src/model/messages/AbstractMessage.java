/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package model.messages;

/**
 * The parent class of the type of messages (Leader board,
 * Telemetry, Finish line crossing).
 * 
 * @author Minh Nguyen
 * @version 17 November 2018
 */
public abstract class AbstractMessage implements Message {
    
    /**
     * The current time stamp of the message.
     */
    private final int myTimeStamp;
    
    /**
     * A string representation of the message.
     */
    private final String myString;
    
    /**
     * A constructor to create different types of messages.
     * 
     * @param theTimeStamp is the time stamp of the current message.
     * @param theString is the string representation of the message.
     */
    public AbstractMessage(final int theTimeStamp,
                           final String theString) {
        
        myTimeStamp = theTimeStamp;
        myString = theString;
    }

    /**
     * Returns the current time stamp of the message.
     * 
     * @return myTimeStamp is the time stamp of the current message.
     */
    @Override
    public int getTimestamp() {
        return myTimeStamp;
    }
    
    /**
     * Returns a string representation of the message.
     * Different types of messages will have different representations.
     */
    @Override
    public String toString() {
        return myString;
    }
}
