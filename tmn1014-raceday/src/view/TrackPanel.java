/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_MESSAGE;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_INFO;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_PARTICIPANTS;
import static view.ColorValues.COLORS;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import model.Racer;
import model.messages.Message;
import model.messages.TelemetryMessage;
import track.VisibleRaceTrack;

/**
 * A demo of Observable/Observer. 
 * 
 * @author Charles Bryan
 * @author Minh Nguyen
 * @version Autumn 2015
 */
public class TrackPanel extends JPanel implements PropertyChangeListener {
    
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = 2551406901656440913L;

    /** The size of the Race Track Panel. */
    private static final Dimension TRACK_SIZE = new Dimension(500, 400);

    /** The x and y location of the Track. */
    private static final int OFF_SET = 40;

    /** The stroke width in pixels. */
    private static final int STROKE_WIDTH = 20;

    /** The size of participants moving around the track. */
    private static final int OVAL_SIZE = 15;
    
    /**
     * The number to keep ratio of the race track.
     */
    private static final int RATIO = 84;

    /**
     * The title of border.
     */
    private static final String TITLE = "Race Track";
    
    /** The visible track. */
    private VisibleRaceTrack myTrack;

    /**
     * A map of all racerIDs and the shapes associated with them.
     */
    private final Map<Integer, Ellipse2D> myCirclesMap;
    
    /**
     * A map of all racer and the shapes associated with them.
     * This is used for mouse listeners.
     */
    private final Map<Racer, Ellipse2D> myRacerCircleMap;
    
    /**
     * The participant status label.
     */
    private JLabel myParticipantStatus;
    
    /**
     * Construct a Track Panel.
     * 
     * @param theParticipantStatus is the participant status label.
     */
    public TrackPanel(final JLabel theParticipantStatus) {
        super();
        myTrack = new VisibleRaceTrack(0, 0, 0, 0, 0);
        myCirclesMap = new LinkedHashMap<>();
        myRacerCircleMap = new LinkedHashMap<>();
        myParticipantStatus = theParticipantStatus;
        addMouseListener(new TrackPanelMouseAdapter());
        
        setUpComponents();
    }

    /**
     * Paints the VisibleTrack with a single ellipse moving around it.
     * 
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw track:
        if (myTrack != null) {
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH));
            g2d.draw(myTrack);
        }
        
        // Draw ellipses:
        final List<Ellipse2D> circles = new ArrayList<>(myCirclesMap.values());
        for (int i = 0; i < circles.size(); i++) {
            g2d.setPaint(COLORS.get(i));
            g2d.setStroke(new BasicStroke(1));
            g2d.fill(circles.get(i));
        }
    }
    
    /**
     * When race is finished loading, create a race track.
     * Create circles for race participants. As telemetry messages
     * are being updated, the circles will move around the track.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        final String propertyName = theEvent.getPropertyName();
        
        if (PROPERTY_RACE_INFO.equals(propertyName)) {
            final String[] raceInfo = 
                            ((String) (theEvent.getNewValue())).split(":");
            final int indexHeight = 3;
            final int width = Integer.parseInt(raceInfo[2]) * RATIO;
            final int height = Integer.parseInt(raceInfo[indexHeight]) * RATIO;
            final int x = OFF_SET;
            final int y = (int) TRACK_SIZE.getHeight()  / 2 - height / 2;
            final int length = Integer.parseInt(raceInfo[raceInfo.length - 2]);
            
            // Create a new Track:
            myTrack = new VisibleRaceTrack(x, y, width, height, length);
            
            repaint();
            
        } else if (PROPERTY_RACE_PARTICIPANTS.equals(propertyName)) { 
            //Clear out any existing shapes in list.
            myCirclesMap.clear();
            // Get the new value.
            final Object newValue = theEvent.getNewValue();
            // Check if the new value is a map:
            if (newValue instanceof Map<?, ?>) {
                // Get a list of racers from map:
                final Collection<Racer> racers =
                                new ArrayList<>(((Map<Integer, Racer>) newValue).values());
                
                createEllipse(racers);
            }
        } else if (PROPERTY_MESSAGE.equals(propertyName)) {
            // Get the new value.
            final Message message = (Message) (theEvent.getNewValue());
            // Check if message is a telemetry message.
            if (message instanceof TelemetryMessage) {
                // Cast message to telemetry message.
                final TelemetryMessage telMessage = (TelemetryMessage) message;
                final double currentDistance = telMessage.getDistance();
                final Point2D currentPoint =
                                myTrack.getPointAtDistance(currentDistance);
                // Set new location of circle and repaint.
                myCirclesMap.get(telMessage.getRacerID()).
                    setFrame(currentPoint.getX() - OVAL_SIZE / 2, 
                             currentPoint.getY() - OVAL_SIZE / 2, 
                             OVAL_SIZE, 
                             OVAL_SIZE);
                repaint();
            }
            
        }
    }
    
    /**
     * Setup and layout components pre-race load.
     */
    private void setUpComponents() {
        setPreferredSize(TRACK_SIZE);
        setBorder(new TitledBorder(TITLE));
        // set the panel to be transparent.
        setOpaque(false);
    }
    
    /**
     * A helper method to create ellipse.
     * Then, add shapes to list of all ellipses.
     * 
     * @param theList is the list of all racers.
     */
    private void createEllipse(final Collection<Racer> theList) {
        // Iterate over list of racers.
        for (final Racer r : theList) {
            // Get the starting point of the racer from starting distance.
            final Point2D start = 
                            myTrack.getPointAtDistance(r.getStartingDistance());
            // Create a new circle.
            final Ellipse2D circle = new Ellipse2D.Double(start.getX() - OVAL_SIZE / 2,
                                                          start.getY() - OVAL_SIZE / 2,
                                                          OVAL_SIZE,
                                                          OVAL_SIZE);
            
            
            
            // Put racer ID as key and circle as value to map of all circles.
            myCirclesMap.put(r.getMyRacerID(), circle);
            myRacerCircleMap.put(r, circle);
        }
        repaint();
    }
    
    /**
     * A mouse adapter that adds mouse listeners.
     * 
     * @author Minh Nguyen
     * @version 7 December 2018
     */
    class TrackPanelMouseAdapter extends MouseAdapter {
        
        /**
         * The text separator.
         */
        private static final String TEXT_SEPARATOR = " ---------";
        
        /**
         * The participant hash tag text.
         */
        private static final String PARTICIPANT_TEXT = "Participant: #";
        
        @Override
        public void mouseClicked(final MouseEvent theEvent) {

            super.mouseClicked(theEvent);
            
            // Check if the point clicked is inside any Ellipse2D objects in the track.
            for (Map.Entry<Racer, Ellipse2D> entry : myRacerCircleMap.entrySet()) {
                final Racer k = entry.getKey();
                final Ellipse2D v = entry.getValue();
                // If point is inside the Ellipse2D, update the status bar.
                if (v.contains(theEvent.getPoint())) {
                    final StringBuilder builder = new StringBuilder();
                    
                    builder.append(PARTICIPANT_TEXT);
                    builder.append(k.getMyRacerID());
                    builder.append(TEXT_SEPARATOR);
                    builder.append(k.getRacerName());
                    builder.append(TEXT_SEPARATOR);
                    
                    // Set text for the participant status label.
                    myParticipantStatus.setText(builder.toString());
                }
            }
        }
        
    }
}