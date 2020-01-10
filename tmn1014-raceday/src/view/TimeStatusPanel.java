/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import controller.Time;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The status bar displays the current time of the race simulation.
 * 
 * @author Minh Nguyen
 * @version 6 December 2018
 */
public class TimeStatusPanel extends JPanel implements PropertyChangeListener {
    
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -5155732954812272990L;
    
    /**
     * The time text for to display the time of race.
     */
    private static final String TIME_TEXT = "Time: ";
    
    /**
     * The time text font.
     */
    private static final Font TIME_FONT = new Font("Courier", Font.PLAIN, 12);
    
    /**
     * A label to display time.
     */
    private final JLabel myLabel;
    
    /**
     * Constructs the status bar of the current time of the race simulation.
     */
    public TimeStatusPanel() {
        super();
        myLabel = new JLabel(TIME_TEXT + Time.formatTime(0));
        setupComponents();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            myLabel.setText(TIME_TEXT + Time.formatTime((Integer) theEvent.getNewValue()));
        }
        
    }
    
    /**
     * Setup and layout components. 
     */
    private void setupComponents() {
        myLabel.setFont(TIME_FONT);

        final JPanel panel = new JPanel();       
        panel.add(myLabel);
        add(panel);
    }
}
