/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_PARTICIPANTS;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import model.PropertyChangeEnabledRaceControls;
import model.Racer;

/**
 * Creates a race participants tab, which contains check boxes of racers name.
 * 
 * @author Minh Nguyen
 * @version 30 November 2018
 */
public class RaceParticipantsCheckboxes extends JPanel implements PropertyChangeListener {
    
    /**
     * A generated serial version UID for object Serialization. 
     */
    private static final long serialVersionUID = 1492035919988266697L;
    
    /**
     * Padding for the border.
     */
    private static final int PADDING = 5;
    
    /**
     * The Race object of this class controls.
     */
    private final PropertyChangeEnabledRaceControls myRace;
    
    /**
     * A list of all check boxes.
     */
    private List<JCheckBox> myCheckBoxes;
    
    /**
     * A panel of all check boxes.
     */
    private JPanel myCheckBoxPanel;
    
    /**
     * Constructs a race participants tab.
     * 
     * @param theRace is the race object to be controlled.
     */
    public RaceParticipantsCheckboxes(final PropertyChangeEnabledRaceControls theRace) {
        super();
        
        myRace = theRace;
        myCheckBoxes = new ArrayList<>();
        myCheckBoxPanel = new JPanel();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if (PROPERTY_RACE_PARTICIPANTS.equals(theEvent.getPropertyName())) {
            
            setLayout(new BorderLayout());
            
            //Clear out information from previous race.
            myCheckBoxes.clear();
            myCheckBoxPanel.removeAll();
            
            // Format the check box panel.
            myCheckBoxPanel.setLayout(new BoxLayout(myCheckBoxPanel, BoxLayout.Y_AXIS));

            // Add check all check box first:
            final JCheckBox selectAll = new JCheckBox("Select All", true);
            selectAll.addActionListener(event -> checkAllBox(selectAll.isSelected()));
            myCheckBoxPanel.add(selectAll);

            final Object newValue = theEvent.getNewValue();
            
            // Check if the new value is a map:
            if (newValue instanceof Map<?, ?>) {
                
                // Get a list of racers from map:
                final Collection<Racer> racers =
                                new ArrayList<>(((Map<Integer, Racer>) newValue).values());
                
                // Add check boxes of racer's name to check box panel:
                for (final Racer r : racers) {
                    final JCheckBox checkbox = new JCheckBox(r.getRacerName(), true);
                    final int racerID = r.getMyRacerID();
                    
                    // Add changeLister to check box:
                    checkbox.addChangeListener(event -> 
                        myRace.toggleParticipant(racerID, checkbox.isSelected()));
                    
                    // add check box to list of check boxes.
                    myCheckBoxes.add(checkbox);
                    // add check box to check box panel.
                    myCheckBoxPanel.add(checkbox);
                }
            }
        }
        
        setUpComponents();
    }
    
    /**
     * Set up and layout components.
     */
    private void setUpComponents() {

        // Create border for the panel.
        this.setBorder(BorderFactory.
                                   createCompoundBorder(BorderFactory.
                                                        createEtchedBorder(),
                                                        BorderFactory.
                                                        createEmptyBorder(PADDING,
                                                                          PADDING,
                                                                          PADDING,
                                                                          PADDING)));
        // Add check box to main panel.
        add(myCheckBoxPanel, BorderLayout.WEST);
    }
    
    /**
     * Check all box if the select all box is selected (true).
     * If false, deselect all check boxes.
     * 
     * @param theChecked if select all box is selected.
     */
    private void checkAllBox(final boolean theChecked) {
        
        if (!theChecked) {
            deselectAllBox();
        } else {
            for (final JCheckBox b : myCheckBoxes) {
                b.setSelected(true);
            }
        }
    }
    
    /**
     * Deselect all check box if selectAll is not selected.
     */
    private void deselectAllBox() {
        
        for (final JCheckBox b : myCheckBoxes) {
            b.setSelected(false);
        }
    }

}
