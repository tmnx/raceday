/*
 * TCSS 305 - Autumn 2018
 *  Assignment 5 - Race Day
 */
package model.messages;

/**
 * An interface to define behaviors of a Message object.
 * 
 * @author Minh Nguyen
 * @version 17 November 2018
 */
public interface Message {
    
    /**
     * Returns the current time stamp.
     * 
     * @return the time stamp of the current message.
     */
    int getTimestamp();
    
    /**
     * Returns a string representation of the message.
     * Different types of messages will have different representations.
     * 
     * @return the string representation of the message.
     */
    @Override
    String toString();

}
