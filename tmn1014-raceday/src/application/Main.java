/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package application;

import controller.RaceController;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.PropertyChangeEnabledRaceControls;
import model.Race;
import view.RaceView;

/**
 * Runs RaceControllerGUI by instantiating and starting the RaceControllerGUI.
 * 
 * @author Minh Nguyen
 * @version 6 November 2018
 */
public final class Main {
    
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private Main() {
        throw new IllegalStateException();
    }
    
    /**
     * The main method to kick off the GUI.
     * 
     * @param theArgs is the command line arguments.
     */
    public static void main(final String[] theArgs) {
        
        // Set the Metal Look and Feel.
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        // Turn off metal's use of bold fonts.
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        final PropertyChangeEnabledRaceControls race = new Race();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RaceController(race).start();
                new RaceView(race).startRaceView();
            }
        });
    }
}
