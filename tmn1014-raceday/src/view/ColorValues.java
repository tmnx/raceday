/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package view;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * A class of constant for the use of a list of colors.
 * 
 * @author Minh Nguyen
 * @version 4 December 2018
 */
public final class ColorValues {
    
    /**
     * The list of colors to pick from. Colors to represent participants.
     */
    public static final List<Color> COLORS = Arrays.asList(Color.YELLOW,
                                                  Color.CYAN,
                                                  Color.BLUE,
                                                  Color.RED,
                                                  Color.MAGENTA,
                                                  Color.PINK,
                                                  Color.ORANGE,
                                                  Color.GREEN,
                                                  Color.DARK_GRAY,
                                                  Color.GRAY,
                                                  Color.LIGHT_GRAY,
                                                  new Color(102, 51, 0),
                                                  new Color(102, 0, 153),
                                                  new Color(0, 0, 153),
                                                  new Color(0, 102, 0));
    
    /**
     * A private constructor to prevent instantiation of this class.
     */
    private ColorValues() {
        
    }
    
}
