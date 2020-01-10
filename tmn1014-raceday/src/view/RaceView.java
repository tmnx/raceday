/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_MESSAGE;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_INFO;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_PARTICIPANTS;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.PropertyChangeEnabledRaceControls;

/**
 * Creates a view for the Race. It displays the race track,
 * racers, and the leader board.
 * 
 * @author Minh Nguyen
 * @version 3 December 2018
 */
public class RaceView extends JFrame {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -8801467414080294887L;
    
    /**
     * The title of the frame.
     */
    private static final String TITLE = "Race Track";
    
    /**
     * The icon of the Title bar.
     */
    private static final ImageIcon ICON_TITLE =
                    new ImageIcon("./images/sports_race_flag.png");
    
    /**
     * The participant text for the status panel.
     */
    private static final String PARTICIPANT_TEXT = "Participant: ";
    
    /**
     * The track panel that display the race.
     */
    private TrackPanel myTrackPanel;
    
    /**
     * The leader board panel.
     */
    private LeaderboardPanel myLeaderboardPanel;
    
    /**
     * The status bar panel.
     */
    private TimeStatusPanel myTimeStatus;
    
    /**
     * The panel to hold time status.
     */
    private JPanel myStatusPanel;
    
    /**
     * The panel to hold the participant status.
     */
    private JLabel myParticipantStatus;
    
    /**
     * The constructor to initiates fields and JFrame.
     * 
     * @param theRace is the race control.
     */
    public RaceView(final PropertyChangeEnabledRaceControls theRace) {
        // Overloading the JFrame's constructor to set the title of frame.
        super(TITLE);
        myTimeStatus = new TimeStatusPanel();
        myParticipantStatus = new JLabel(PARTICIPANT_TEXT);
        myStatusPanel = new JPanel();
        myTrackPanel = new TrackPanel(myParticipantStatus);
        myLeaderboardPanel = new LeaderboardPanel(myParticipantStatus);
        
        addListeners(theRace);
    }
    
    /**
     * Start the Race View Graphical User Interface.
     */
    public void startRaceView() {
        
        // Set the icon of the title bar.
        setIconImage(ICON_TITLE.getImage());
        
        // Set the background of the frame to white.
        getContentPane().setBackground(Color.WHITE);
        
        // Set up components.
        setUpComponents();
        
        pack();
        
        // Frame cannot be resized.
        setResizable(false);
        
        // Set Frame to visible.
        setVisible(true);
    }
    
    /**
     * Set up and layout components.
     */
    private void setUpComponents() {
        // Set up status bar layout / components:
        myStatusPanel.setLayout(new BorderLayout());
        myStatusPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,
                                                               1,
                                                               true));
        myStatusPanel.add(myTimeStatus, BorderLayout.EAST);
        myStatusPanel.add(myParticipantStatus, BorderLayout.WEST);
        
        // Add to main Frame:
        add(myStatusPanel, BorderLayout.SOUTH);
        add(myTrackPanel, BorderLayout.CENTER);
        add(myLeaderboardPanel, BorderLayout.EAST);
    }
    
    /**
     * Add all PropertyChangeListener.
     * 
     * @param theRace is the race control.
     */
    private void addListeners(final PropertyChangeEnabledRaceControls theRace) {
        theRace.addPropertyChangeListener(PROPERTY_MESSAGE, myTrackPanel);
        theRace.addPropertyChangeListener(PROPERTY_MESSAGE, myLeaderboardPanel);
        theRace.addPropertyChangeListener(PROPERTY_RACE_INFO, myTrackPanel);
        theRace.addPropertyChangeListener(PROPERTY_RACE_PARTICIPANTS, myTrackPanel);
        theRace.addPropertyChangeListener(PROPERTY_RACE_PARTICIPANTS, myLeaderboardPanel);
        theRace.addPropertyChangeListener(PROPERTY_TIME, myTimeStatus);
    }

}
