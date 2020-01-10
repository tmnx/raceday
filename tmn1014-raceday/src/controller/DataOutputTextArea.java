/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_MESSAGE;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_INFO;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_START_LOAD;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JTextArea; 
import model.messages.Message;

/**
 * Creates a Data Output Text Area.
 * 
 * @author Minh Nguyen
 * @version 27 November 2018
 */
public class DataOutputTextArea extends JTextArea implements PropertyChangeListener {

    /**
     *  A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -6098549775869809404L;
    
    /**
     * Padding for the border.
     */
    private static final int PADDING = 5;
    
    /**
     * The rows value of the text area.
     */
    private static final int TEXT_AREA_ROWS = 10;
    
    /**
     * The columns value of the text area.
     */
    private static final int TEXT_AREA_COLS = 50;
    
    /**
     * The finished loading race file string.
     */
    private static final String FINISHED_LOADING_TEXT =
                    "The race file is finished loading.\nThank you for your patient!";
    
    /**
     * A new line character.
     */
    private static final String NEWLINE = "\n";
    
    /**
     * Construct a Data Output Text Area.
     */
    public DataOutputTextArea() {
        super(TEXT_AREA_ROWS, TEXT_AREA_COLS);
        
        setUpComponents();
    }
    
    /**
     * Display messages to text area when it hears property message changed.
     * Also, it display that the race is finished loading.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String propertyName = theEvent.getPropertyName();
        
        if (PROPERTY_MESSAGE.equals(propertyName)) {
            final Message message = (Message) (theEvent.getNewValue());
            this.append(message.toString());
            this.append(NEWLINE);
        } else if (PROPERTY_RACE_INFO.equals(propertyName)) {
            this.append(FINISHED_LOADING_TEXT);
            this.append(NEWLINE);
            System.out.println(FINISHED_LOADING_TEXT);
        } else if (PROPERTY_START_LOAD.equals(propertyName)) {
            System.out.println(theEvent.getNewValue());
        }
    }
    
    /**
     * Set up the text area.
     */
    private void setUpComponents() {
        
        // Cannot edit text area.
        this.setEditable(false);
        
        // Create border for the text area.
        this.setBorder(BorderFactory.
                                   createCompoundBorder(BorderFactory.
                                                        createEtchedBorder(),
                                                        BorderFactory.
                                                        createEmptyBorder(PADDING,
                                                                          PADDING,
                                                                          PADDING,
                                                                          PADDING)));
        this.setCaretPosition(this.getDocument().getLength());
        
    }

}

