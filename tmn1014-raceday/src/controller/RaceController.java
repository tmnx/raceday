/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_INFO;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_OVER_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE_PARTICIPANTS;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_MESSAGE;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_START_LOAD;

import controller.actions.LoopMode;
import controller.actions.PlayPauseMode;
import controller.actions.ToggleAction;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;
import model.PropertyChangeEnabledRaceControls;

/**
 * Display the appearance of all GUI controller elements.
 * 
 * @author Minh Nguyen
 * @version 5 November 2018
 */
public class RaceController extends JFrame {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -8729096318195096491L;
       
    /**
     * Padding for the border.
     */
    private static final int PADDING = 5;
    
    /**
     * Amount in Pixels for the Horizontal margin.
     */
    private static final int HORIZONATAL_MARGIN = 10;
    
    /**
     * Amount in Pixels for the Vertical margin.
     */
    private static final int VERTICALL_MARGIN = 10;
    
    /**
     * The title of the window.
     */
    private static final String TITLE = "Race Day!";
    
    /**
     * Load Race text for the menu button.
     */
    private static final String BUTTON_TEXT_LOAD_RACE = "Load Race...";
    
    /**
     * Exit text for the menu button.
     */
    private static final String BUTTON_TEXT_EXIT = "Exit";
    
    /**
     * Restart text for the menu button.
     */
    private static final String BUTTON_TEXT_RESTART = "Restart";
    
    /**
     * Play text for the menu button.
     */
    private static final String BUTTON_TEXT_PLAY = "Play";
    
    /**
     * Pause text for the menu button.
     */
    private static final String BUTTON_TEXT_PAUSE = "Pause";
    
    /**
     * Times one text for the menu button.
     */
    private static final String BUTTON_TEXT_TIMES_ONE = "Times One";
    
    /**
     * Times four text for the menu button.
     */
    private static final String BUTTON_TEXT_TIMES_FOUR = "Times Four";
    
    /**
     * Single Race text for the menu button.
     */
    private static final String BUTTON_TEXT_SINGLE_RACE = "Single Race";
    
    /**
     * Looped Race text for the menu button.
     */
    private static final String BUTTON_TEXT_LOOP_RACE = "Loop Race";
    
    /**
     * Clear text for the menu button.
     */
    private static final String BUTTON_TEXT_CLEAR = "Clear";
    
    /**
     * Text for the file menu.
     */
    private static final String FILE_MENU = "File";
    
    /**
     * Text for the controls menu.
     */
    private static final String CONTROLS_MENU = "Controls";
    
    /**
     * Text for the help menu.
     */
    private static final String HELP_MENU = "Help";
    
    /**
     * Text for the About menu item.
     */
    private static final String ABOUT_ITEM = "About...";
    
    /**
     * Text for the About menu item.
     */
    private static final String RACE_INFO_ITEM = "Race Info...";
    
    /**
     * The icon of the Title bar and the About.
     */
    private static final ImageIcon ICON_TITLE = new ImageIcon("./images/sports_race_flag.png");
    
    /**
     * The Restart text for the restart button.
     */
    private static final String ICON_RESTART = "images/ic_restart.png";
    
    /**
     * The Play text for the play/pause button.
     */
    private static final String ICON_PLAY = "images/ic_play.png";
    
    /**
     * The Play text for the play/pause button.
     */
    private static final String ICON_PAUSE = "images/ic_pause.png";
        
    /**
     * The Times One text for the times one/times four button.
     */
    private static final String ICON_TIMES_ONE = "images/ic_one_times.png";
    
    /**
     * The Times Four text for the times one/times four button.
     */
    private static final String ICON_TIMES_FOUR = "images/ic_four_times.png";
    
    /**
     * The Repeat text for the the repeat/color repeat button.
     */
    private static final String ICON_REPEAT = "images/ic_repeat.png";
    
    /**
     * The colored Repeat text for the repeat/color repeat button.
     */
    private static final String ICON_REPEAT_COLOR = "images/ic_repeat_color.png";
    
    /**
     * The Clear text for the clear button.
     */
    private static final String ICON_CLEAR = "images/ic_clear.png";
    
    /**
     * Amount of milliseconds between each call to the timer.
     */
    private static final int TIMER_FREQUENCY = 30;
    
    /**
     * Value for regular multiplier.
     */
    private static final int SPEED_REGULAR = 1;

    /**
     * Value for fast multiplier.
     */
    private static final int SPEED_FAST = 4;
    
    /**
     * About information of the application.
     */
    private static final String ABOUT_INFORMATION =
                    "Minh Nguyen\nAutumn 2018\nTCSS 305 Assignment 5";
    
    /**
     * The JOption pane title for race information.
     */
    private static final String RACE_INFORMATION_TEXT = "Race Information";
    
    /**
     * The error message when load an invalid race file.
     */
    private static final String ERROR_TEXT = "Error loading file.";
    
    /**
     * The title of JOptionPane for displaying error message.
     */
    private static final String ERROR_TITLE = "Error!";
    
    /**
     * The JOption pane title for about.
     */
    private static final String ABOUT_TEXT = "About";
    
    /**
     * The title of the data output stream tab.
     */
    private static final String DATA_TAB_TITLE = "Data Output Stream";
    
    /**
     * The title of the race participants tab.
     */
    private static final String PARTICIPANTS_TAB_TITLE = "Race Participants";
        
    /**
     * The Race object of this class controls.
     */
    private PropertyChangeEnabledRaceControls myRace;
    
    /**
     * The Actions for the tool bar and the controls menu.
     */
    private final List<Action> myActions;

    /**
     * The timer to control how often to advance the Race object.
     */
    private Timer myTimer;
    
    /**
     * The Menu Bar of the GUI.
     */
    private JMenuBar myMenuBar;
    
    /**
     * A tool bar for the control options.
     */
    private JToolBar myToolBar;
    
    /**
     * A Tabbed Pane for data output stream and race participants.
     */
    private JTabbedPane myTabbedPane;
    
    /**
     * The center panel of the frame.
     */
    private JPanel myCenterPanel;
    
    /**
     * A file chooser for user to select a race file.
     */
    private JFileChooser myFileChooser;
    
    /**
     * The current directory for the file open dialog.
     */
    private File myDirectory;
    
    /**
     * The time multiplier.
     */
    private int myMutiplier;
    
    /**
     * The timer panel.
     */
    private TimePanel myTimePanel;
    
    /**
     * The race information message object.
     */
    private RaceInfo myRaceInformationMessage;
    
    /**
     * The data output text area.
     */
    private DataOutputTextArea myDataTextArea;
    
    /**
     * The slider panel.
     */
    private SliderPanel mySliderPanel;
    
    /**
     * The loop mode for the controls menu and the tool bar.
     */
    private LoopMode myLoopMode;
    
    /**
     * The play / pause mode for the controls.
     */
    private PlayPauseMode myPlayPauseMode;
    
    
    /**
     * The race participants tab to select race participants.
     */
    private RaceParticipantsCheckboxes myRaceParticipantsCheckboxes;
    
    /**
     * The constructor to initializes fields and JFrame.
     * 
     * @param theRace is the Race object of this class controls.
     */
    public RaceController(final PropertyChangeEnabledRaceControls theRace) {
        // Overloading the JFrame's constructor to set the JFrame title.
        super(TITLE);
        
        myRace = theRace;
        myTimer = new Timer(TIMER_FREQUENCY, this::handleTimer);
        instantiateComponents();
        
        myActions = new ArrayList<>();
        
        createActions();
    }
    
    /**
     * Start the Graphical User Interface.
     */
    public void start() {        
        // Close the operation when exit frame.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the icon of the title bar.
        setIconImage(ICON_TITLE.getImage());

        addListeners();

        // Set up the components and layouts:
        myCenterPanel.setLayout(new BorderLayout());
        // Create empty border for the center panel.
        myCenterPanel.setBorder(BorderFactory.
                                createEmptyBorder(PADDING, VERTICALL_MARGIN,
                                                  HORIZONATAL_MARGIN, VERTICALL_MARGIN));
        setUpMenuBar();
        setUpToolBar();
        setUpSliderTimer();
        setUpTabbedPane();
        
        // Add center panel to the main frame.
        add(myCenterPanel, BorderLayout.CENTER);
        
        pack();
        
        // Frame cannot be resized.
        setResizable(false);
        
        // Set Frame to visible.
        setVisible(true);
    }
    
    /**
     * Instantiate and add Actions to the list of actions.
     */
    private void createActions() {
        
        // Create Action for restart.
        final ToggleAction restart = new ToggleAction(BUTTON_TEXT_RESTART,
            BUTTON_TEXT_RESTART,
            ICON_RESTART,
            ICON_RESTART,
            () -> resetTimer(),
            () -> resetTimer());

        // Creates Action to toggle between regular(times one) and fast(times four).
        final ToggleAction regularFast = new ToggleAction(BUTTON_TEXT_TIMES_ONE,
            BUTTON_TEXT_TIMES_FOUR,
            ICON_TIMES_ONE,
            ICON_TIMES_FOUR,
            () -> myMutiplier = SPEED_REGULAR,
            () -> myMutiplier = SPEED_FAST);

        // Create Action for clear.
        final ToggleAction clear = new ToggleAction(BUTTON_TEXT_CLEAR,
            BUTTON_TEXT_CLEAR,
            ICON_CLEAR,
            ICON_CLEAR,
            () -> myDataTextArea.setText(""),
            () -> myDataTextArea.setText(""));
        
        // Add actions to list.
        myActions.add(restart);
        myActions.add(myPlayPauseMode);
        myActions.add(regularFast);
        myActions.add(clear);
        myActions.add(myLoopMode);
        
    }
    
    /**
     * Instantiate components in the GUI.
     */
    private void instantiateComponents() {
        myMenuBar = new JMenuBar();
        myToolBar = new JToolBar();
        myCenterPanel = new JPanel();
        myTabbedPane = new JTabbedPane();
        myDirectory = new File(".");
        myFileChooser = new JFileChooser(myDirectory);
        myMutiplier = 1;
        myTimePanel = new TimePanel();
        myRaceInformationMessage = new RaceInfo();
        myDataTextArea = new DataOutputTextArea();
        mySliderPanel = new SliderPanel(myRace);
        myRaceParticipantsCheckboxes = new RaceParticipantsCheckboxes(myRace);
        
        myPlayPauseMode = new PlayPauseMode(mySliderPanel,
            BUTTON_TEXT_PLAY,
            BUTTON_TEXT_PAUSE,
            ICON_PLAY,
            ICON_PAUSE,
            () -> myTimer.stop(),
            () -> myTimer.start());
        
        myLoopMode = new LoopMode(myRace,
                                  myPlayPauseMode,
                                  BUTTON_TEXT_SINGLE_RACE,
                                  BUTTON_TEXT_LOOP_RACE,
                                  ICON_REPEAT,
                                  ICON_REPEAT_COLOR);
        
    }
    
    /**
     * Set up the Menu Bar and add it to the main Frame.
     */
    private void setUpMenuBar() {
        
        // Create Menus:
        setUpFileMenu();
        setUpControlsMenu();
        setUpHelpMenu();
      
        setJMenuBar(myMenuBar);
    }
    
    /**
     * Set up the file menu.
     */
    private void setUpFileMenu() {
        
        // Create load race item and add action listener to it.
        final JMenuItem loadRaceItem = new JMenuItem(BUTTON_TEXT_LOAD_RACE);
        loadRaceItem.addActionListener(theEvent -> handleLoadRace());
        
        // Create exit item and add action listener to it.
        final JMenuItem exitItem = new JMenuItem(BUTTON_TEXT_EXIT);
        // Terminate the program when user select "Exit".
        exitItem.addActionListener(theEvent ->
                                   dispatchEvent(new WindowEvent(this,
                                                                 WindowEvent.
                                                                 WINDOW_CLOSING)));
        // Create file menu and add item to menu:
        final JMenu fileMenu = new JMenu(FILE_MENU);
        fileMenu.add(loadRaceItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        myMenuBar.add(fileMenu);
    }
    
    /**
     * Set up the controls menu.
     */
    private void setUpControlsMenu() {
        final JMenu controlsMenu = new JMenu(CONTROLS_MENU);
        
        // Add actions to controls menu:
        for (final Action a : myActions) {
            controlsMenu.add(new JMenuItem(a));
        }
        myMenuBar.add(controlsMenu);
    }
    
    /**
     * Set up the help menu.
     */
    private void setUpHelpMenu() {
        final JMenu helpMenu = new JMenu(HELP_MENU);
        
        // Create race info and add action listener to it.
        final JMenuItem raceInfoItem = new JMenuItem(RACE_INFO_ITEM);
        raceInfoItem.setEnabled(false);
        raceInfoItem.addActionListener(theEvent -> showRaceInformation());
        helpMenu.add(raceInfoItem);
        
        // Create about and add action listener to it.
        final JMenuItem aboutItem = new JMenuItem(ABOUT_ITEM);
        aboutItem.addActionListener(theEvent -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        myMenuBar.add(helpMenu);
    }
    
    /**
     * Set up the Tool Bar and add it to the center panel.
     */
    private void setUpToolBar() {
        
        // Add actions to tool bar:
        for (final Action a : myActions) {
            final JButton button = new JButton(a);
            // Hide the text from button and disable button:
            button.setHideActionText(true);
            a.setEnabled(false);
            myToolBar.add(button);
        }
        add(myToolBar, BorderLayout.SOUTH);
    }
    
    /**
     * Set up the Tabbed Pane and add it to the center panel.
     */
    private void setUpTabbedPane() {

        // Add text area to a scroll pane.
        final JScrollPane textAreaScrollPane = 
                        new JScrollPane(myDataTextArea,
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        final JScrollPane participantsTabScrollPane = 
                        new JScrollPane(myRaceParticipantsCheckboxes,
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Always update the text area to be the bottom to display most recent message.
        final DefaultCaret caret = (DefaultCaret) myDataTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // Add tabs:
        myTabbedPane.addTab(DATA_TAB_TITLE, textAreaScrollPane);
        myTabbedPane.addTab(PARTICIPANTS_TAB_TITLE, participantsTabScrollPane);
        
        // Disable participants tab.
        myTabbedPane.setEnabledAt(1, false);
        myCenterPanel.add(myTabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * Add slider and timer panel to the center panel.
     */
    private void setUpSliderTimer() {
        // Create a panel to hold the slider and timer:
        final JPanel panel = new JPanel(new BorderLayout());
        
        // Set border to panel.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 
                                                        VERTICALL_MARGIN, 
                                                        HORIZONATAL_MARGIN, 
                                                        VERTICALL_MARGIN));
        
        panel.add(mySliderPanel, BorderLayout.CENTER);
        panel.add(myTimePanel, BorderLayout.EAST);
        
        myCenterPanel.add(panel, BorderLayout.NORTH);
    }
    
    /**
     * Handle the event of Menu Item Load Race. Opens a file chooser dialog and let
     * user load a race file.
     */
    private void handleLoadRace() {
        // Open a dialog for user to select a file.
        final int choice = myFileChooser.showOpenDialog(this);
        
        // If user chooses a file:
        if (choice == JFileChooser.APPROVE_OPTION) {
            
            try {
                myRace.loadRace(myFileChooser.getSelectedFile()); // try to load race
                // reset the race time.
                resetTimer();
                // when race loaded, enable buttons.
                enableButtons();
            } catch (final IOException e) {
                // If invalid race file format, display error message.
                JOptionPane.showMessageDialog(null, ERROR_TEXT,
                                              ERROR_TITLE , JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Pop up a dialog with information message.
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(null,
                                      ABOUT_INFORMATION,
                                      ABOUT_TEXT,
                                      JOptionPane.INFORMATION_MESSAGE,
                                      ICON_TITLE);
    }
    
    /**
     * Pop up a dialog with file information.
     */
    private void showRaceInformation() {
        JOptionPane.showMessageDialog(null,
                                      myRaceInformationMessage.toString(),
                                      RACE_INFORMATION_TEXT,
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Event handler for the timer.
     * 
     * @param theEvent is the fired event.
     */
    private void handleTimer(final ActionEvent theEvent) {
        myRace.advance(TIMER_FREQUENCY * myMutiplier);
    }

    /**
     * Enable all buttons.
     */
    private void enableButtons() {
        
        // enable tool bar and controls menu.
        for (final Action a : myActions) {
            a.setEnabled(true);
        }
        
        // enable race information button in help menu. 
        myMenuBar.getMenu(2).getMenuComponent(0).setEnabled(true);
        
        // enable race participant tab
        myTabbedPane.setEnabledAt(1, true);
    }
    
    /**
     * Reset the timer to time zero.
     */
    private void resetTimer() {
        myRace.moveTo(0);
    }
    
    /**
     * Add all PropertyChangeListener.
     */
    private void addListeners() {
        myRace.addPropertyChangeListener(PROPERTY_TIME, myTimePanel);
        myRace.addPropertyChangeListener(PROPERTY_TIME, mySliderPanel);
        myRace.addPropertyChangeListener(PROPERTY_RACE_INFO, myDataTextArea);
        myRace.addPropertyChangeListener(PROPERTY_RACE_INFO, myRaceInformationMessage);
        myRace.addPropertyChangeListener(PROPERTY_RACE_INFO, mySliderPanel);
        myRace.addPropertyChangeListener(PROPERTY_OVER_TIME, myLoopMode);
        myRace.addPropertyChangeListener(PROPERTY_OVER_TIME, myPlayPauseMode);
        myRace.addPropertyChangeListener(PROPERTY_MESSAGE, myDataTextArea);
        myRace.addPropertyChangeListener(PROPERTY_RACE_PARTICIPANTS,
                                         myRaceParticipantsCheckboxes);
        myRace.addPropertyChangeListener(PROPERTY_START_LOAD, myDataTextArea);
    }
     
}
