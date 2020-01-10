package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_INFO;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import javax.swing.JSlider;
import model.PropertyChangeEnabledRaceControls;

/**
 * An inner class that creates a slider panel.
 * 
 * @author Minh Nguyen
 * @version 28 November 2018
 */
public class SliderPanel extends JPanel implements PropertyChangeListener {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = 7812388078014590705L;
    
    /**
     * The value for the major tick mark.
     */
    private static final int MAJOR_TICKS = 60000;
    
    /**
     * The value for the minor tick mark.
     */
    private static final int MINOR_TICKS = 10000;
    
    /**
     * The index of total time in race info array.
     */
    private static final int TIME_INDEX = 5;
    
    /**
     * Holds a slider.
     */
    private final JSlider mySlider;
    
    /**
     * The Race object of this class controls.
     */
    private final PropertyChangeEnabledRaceControls myRace;

    /**
     * Construct a slider panel.
     * 
     * @param theRace is the race to be controlled.
     */
    public SliderPanel(final PropertyChangeEnabledRaceControls theRace) {
        super();
        
        myRace = theRace;
        mySlider = new JSlider(0, 0, 0);
        
        setUpComponents();
        
    }
    
    /**
     * The slider is enabled when the race is completely loaded. 
     * The slider value is adjusted when the time changes.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String propertyName = theEvent.getPropertyName();
 
        if (PROPERTY_TIME.equals(propertyName)) {
            mySlider.setValue((Integer) theEvent.getNewValue());
        } else if (PROPERTY_RACE_INFO.equals(propertyName)) {
            final String[] raceInfo = ((String) theEvent.getNewValue()).split(":");
            final int totalRaceTime = Integer.parseInt(raceInfo[TIME_INDEX]);
            mySlider.setEnabled(true);
            mySlider.setMaximum(totalRaceTime);
            setMajorMinorTicks(totalRaceTime);
        } 
    }
    
    /**
     * Set up and layout components.
     * @return 
     */
    private void setUpComponents() {
        setLayout(new BorderLayout());
        
        mySlider.addChangeListener(theEvent -> myRace.moveTo(mySlider.getValue()));
        mySlider.setEnabled(false);
        add(mySlider, BorderLayout.CENTER);
    }
    
    /**
     * Set major and minor ticks for slider.
     * 
     * @param theTotalTime is the total time of the race.
     */
    private void setMajorMinorTicks(final int theTotalTime) {
        mySlider.setMajorTickSpacing(MAJOR_TICKS);
        mySlider.setMinorTickSpacing(MINOR_TICKS);
        mySlider.setPaintTicks(true);
    }
}