/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package model;

import java.beans.PropertyChangeListener;

/**
 * Defines behaviors allowing PropertyChangeListeners to be added or removed from a 
 * RaceControls object. Implementing classes should inform PropertyChangeListeners
 * when methods defined in RaceControls mutate the state of the Race. 
 * 
 * Defines a set of Properties that may be listened too. Implementing class may further define
 * more Properties.
 * 
 * @author Charles Bryan
 * @author Minh Nguyen
 * @version 26 November 2018
 *
 */
public interface PropertyChangeEnabledRaceControls extends RaceControls {
    
    /**
     * A property of the time.
     */
    String PROPERTY_TIME = "The time has changed.";
    
    /**
     * A property of the message.
     */
    String PROPERTY_MESSAGE = "The message has changed.";
    
    /**
     * A property of the header message.
     */
    String PROPERTY_RACE_INFO = "The race information has changed.";
    
    /**
     * A property of starting to load race file.
     */
    String PROPERTY_START_LOAD = "Start loading the race file.";

    /**
     * A property of the maximum time of the race.
     */
    String PROPERTY_OVER_TIME = "The time is over the maximum time.";
    
    /**
     * A property of the the race participants.
     */
    String PROPERTY_RACE_PARTICIPANTS = "Race participants has changed.";
    
    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for 
     * all properties. The same listener object may be added more than once, and will be 
     * called as many times as it is added. If listener is null, no exception is thrown and 
     * no action is taken.
     * 
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);
    
    
    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only 
     * when a call on firePropertyChange names that specific property. The same listener object
     * may be added more than once. For each property, the listener will be invoked the number 
     * of times it was added for that property. If propertyName or listener is null, no 
     * exception is thrown and no action is taken.
     * 
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a 
     * PropertyChangeListener that was registered for all properties. If listener was added 
     * more than once to the same event source, it will be notified one less time after being 
     * removed. If listener is null, or was never added, no exception is thrown and no action 
     * is taken.
     * 
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);
    
    /**
     * Remove a PropertyChangeListener for a specific property. If listener was added more than
     * once to the same event source for the specified property, it will be notified one less 
     * time after being removed. If propertyName is null, no exception is thrown and no action 
     * is taken. If listener is null, or was never added for the specified property, no 
     * exception is thrown and no action is taken.
     * 
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String thePropertyName, 
                                      PropertyChangeListener theListener);
}
