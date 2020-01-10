package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates a time label.
 * 
 * @author Charles Bryan
 * @author Minh Nguyen
 * @version Autumn 2015
 */
public class TimePanel extends JPanel implements PropertyChangeListener {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8385732728740430466L;
    
    /** Padding for the border around the label. */
    private static final int PADDING = 5;
    
    /** Padding for the border around the label. */
    private static final Dimension MARGIN = new Dimension(0, 15);
    
    /** A label to display time. */
    private final JLabel myLabel;
    
    /**
     * Construct a Time Panel. 
     */
    public TimePanel() {
        super();
        myLabel = new JLabel(Time.formatTime(0));
        setupComponents();
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            myLabel.setText(Time.formatTime((Integer) theEvent.getNewValue()));
        }
    }
    
    /**
     * Setup and layout components. 
     */
    private void setupComponents() {
        setLayout(new BorderLayout());
        JPanel margin = new JPanel();
        margin.setPreferredSize(MARGIN);
        add(margin, BorderLayout.NORTH);
        margin = new JPanel();
        margin.setPreferredSize(MARGIN);
        add(margin, BorderLayout.SOUTH);
        
        final JPanel content = new JPanel();
        myLabel.setBorder(BorderFactory.createCompoundBorder(
                                 BorderFactory.createEtchedBorder(), 
                                 BorderFactory.createEmptyBorder(PADDING, 
                                                                 PADDING, 
                                                                 PADDING, 
                                                                 PADDING)));
        content.add(myLabel);
        add(content, BorderLayout.CENTER);
    }

}
