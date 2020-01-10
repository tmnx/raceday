/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_MESSAGE;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_PARTICIPANTS;
import static view.ColorValues.COLORS;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Racer;
import model.messages.LeaderboardMessage;

/**
 * @author Minh Nguyen
 * @version 4 December 2018
 */
public class LeaderboardPanel extends JPanel implements PropertyChangeListener {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -3816468087498289635L;
    
    /**
     * The size of Leader Board Panel.
     */
    private static final Dimension PANEL_SIZE = new Dimension(200, 400);
    
    /**
     * The size of the participants' labels.
     */
    private static final Dimension LABEL_SIZE = new Dimension(180, 20);
    
    /**
     * The padding around the panel.
     */
    private static final int PADDING = 5;
    
    /**
     * The thickness of the line border for participants' labels.
     */
    private static final int THICKNESS = 3;
    
    /**
     * The text separator.
     */
    private static final String TEXT_SEPARATOR = " ---------";
    
    /**
     * The participant hash tag text.
     */
    private static final String PARTICIPANT_TEXT = "Participant: #";
    
    /**
     * A map of racerID to racer's label.
     */
    private Map<Integer, JLabel> myLabelsMap;
    
    /**
     * A map of racer to racer's label.
     */
    private Map<Racer, JLabel> myRacerLabelMap;
    
    /**
     * The participant status label.
     */
    private JLabel myParticipantStatus;

    /**
     * Constructs a leader board panel.
     * 
     * @param theParticipantStatus is the participant status label.
     */
    public LeaderboardPanel(final JLabel theParticipantStatus) {
        super();
        myLabelsMap = new HashMap<>();
        myRacerLabelMap = new HashMap<>();
        myParticipantStatus = theParticipantStatus;
        setUpComponents();
    }
    
    /**
     * Listen to the PROPERTY_MESSAGE.
     * When the leader board messages change, the leader board panel adjust to it.
     * The leader board panel displays the racers' names in order of first to last.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        final String propertyName = theEvent.getPropertyName();
        
        // Check if the new value is a map.
        if (PROPERTY_RACE_PARTICIPANTS.equals(propertyName)
                        && theEvent.getNewValue() instanceof Map<?, ?>) {
            // Get a list of racers from map:
            final List<Racer> racers =
                            new ArrayList<>(((Map<Integer, Racer>) theEvent.
                                              getNewValue()).values());
            // Remove existing leader board information from main panel.
            removeAll();
            createLeaderBoard(racers);
            
        // Check if new value is a leader board message.
        } else if (PROPERTY_MESSAGE.equals(propertyName)
                        && theEvent.getNewValue() instanceof LeaderboardMessage) {
            
            final LeaderboardMessage leadMessage = 
                            (LeaderboardMessage) (theEvent.getNewValue());
            updateLeaderBoard(leadMessage.getRacerIDsList());
        }
        
    }
    
    /**
     * A helper method to create the leader board.
     * It displays the list of racer names in order of
     * first to last.
     * 
     * @param theList is a list of racers.
     */
    private void createLeaderBoard(final List<Racer> theList) {
        // Remove existing leader board information from main panel.
        removeAll();
        myLabelsMap.clear();
        
        // Keep track of the color in list.
        int i = 0;
        
        // Iterate over a list of racers and create label for each one:
        for (final Racer r : theList) {
            // Label w/ racer ID and name.
            final JLabel label = 
                            new JLabel(r.getMyRacerID() + ": " + r.getRacerName());
            label.setPreferredSize(LABEL_SIZE);
            label.setBorder(BorderFactory.createLineBorder(COLORS.get(i),
                                                           THICKNESS));
            
            // Add mouse listener to label:
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent theEvent) {
                    final StringBuilder builder = new StringBuilder();
                    builder.append(PARTICIPANT_TEXT);
                    builder.append(r.getMyRacerID());
                    builder.append(TEXT_SEPARATOR);
                    builder.append(r.getRacerName());
                    builder.append(TEXT_SEPARATOR);
                    myParticipantStatus.setText(builder.toString());
                }
            });
            
            // Put racer ID as key and label as value in map.
            myLabelsMap.put(r.getMyRacerID(), label);
            myRacerLabelMap.put(r, label);
            
            // Add label to main panel.
            add(label);
            i++;
        }
        
        validate();
    }
    
    /**
     * Update the leader board from the leader board message information.
     * 
     * @param theList is the list of racer IDs.
     */
    private void updateLeaderBoard(final List<Integer> theList) {
        // Remove existing leader board information from main panel.
        removeAll();
        
        // Iterate over list and add label to main panel.
        for (final int i : theList) {
            if (myLabelsMap.containsKey(i)) {
                add(myLabelsMap.get(i));
            }
        }
        
        validate();
    }
    
    /**
     * Set up and layout components for pre-race load.
     */
    private void setUpComponents() {
        setPreferredSize(PANEL_SIZE);
        setBorder(BorderFactory.createEmptyBorder(PADDING, 
                                                  PADDING, 
                                                  PADDING, 
                                                  PADDING));
        setOpaque(false);
    }
    
}
