/*
 * TCSS 305 - Autumn 2018
 * Assignment 5 - Race Day
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import model.messages.LeaderboardMessage;
import model.messages.LineCrossingMessage;
import model.messages.Message;
import model.messages.TelemetryMessage;

/**
 * Informs PropertyChangeListeners when methods defined in 
 * RaceControls mutate the state of the Race. The class further define
 * more Properties that are listened.
 * 
 * @author Minh Nguyen
 * @author Charles Bryan
 * @version 5 November 2018
 */
public class Race implements PropertyChangeEnabledRaceControls {
    
    /**
     * The delimiter for the Scanner.
     */
    private static final String DELIMITER = ":";
    
    /**
     * The beginning of header messages.
     */
    private static final String HASHTAG = "#";
    
    /**
     * The notation for Telemetry message.
     */
    private static final String TELEMETRY = "$T";
    
    /**
     * The notation for Leader board message.
     */
    private static final String LEADERBOARD = "$L";
    
    /**
     * The notation for Finish Line Crossing message.
     */
    private static final String FINISH_LINE_CROSSING = "$C";
    
    /**
     * The RACE information header message format.
     */
    private static final String RACE_HEADER = "#RACE";
    
    /**
     * The TRACK information header message format.
     */
    private static final String TRACK_HEADER = "#TRACK";
    
    /**
     * The WIDTH information header message format.
     */
    private static final String WIDTH_HEADER = "#WIDTH";
    
    /**
     * The HEIGHT information header message format.
     */
    private static final String HEIGHT_HEADER = "#HEIGHT";
    
    /**
     * The DISTANCE information header message format.
     */
    private static final String DISTANCE_HEADER = "#DISTANCE";
    
    /**
     * The TIME information header message format.
     */
    private static final String TIME_HEADER = "#TIME";
    
    /**
     * The PARTICIPANTS information header message format.
     */
    private static final String PARTICIPANTS_HEADER = "#PARTICIPANTS";
    
    /**
     * The number of tokens in the race information header messages.
     */
    private static final int HEADER_TOKENS = 2;
    
    /**
     * The number of tokens in the racer information messages.
     */
    private static final int RACER_TOKENS = 3;
    
    /**
     * The limit of the racer ID is non-negative integer less than 100.
     */
    private static final int RACER_ID_LIMIT = 100;
    
    /**
     * The default starting time.
     */
    private static final int DEFAULT_START_TIME = 0;
    
    /**
     * The time gap / error consideration of the race total time.
     * Since the timer frequency is every 30 ms.
     */
    private static final int TIME_GAP = 30;
    
    /**
     * Manager for the Property Change Listeners.
     */
    private final PropertyChangeSupport myPropertyChangeSupport;
    
    /**
     * Stores the current time.
     */
    private int myTime;
    
    /**
     * Stores the total time of the race.
     */
    private int myTotalRaceTime;
    
    /**
     * To store messages of the race at certain time stamp.
     */
    private Map<Integer, ArrayList<Message>> myMessages;
    
    /**
     * To store racers of the race.
     */
    private Map<Integer, Racer> myRacers;
    
    /**
     * The race information string.
     */
    private String myHeaderMessages;
    
    /**
     * Construct a Race object with the start time at zero.
     */
    public Race() {
        super();

        myPropertyChangeSupport = new PropertyChangeSupport(this);
        myTime = DEFAULT_START_TIME;
        myMessages = new HashMap<>();
        myRacers = new LinkedHashMap<>();
        myHeaderMessages = "";
    }
    
    /**
     * Advances the race's internal clock by 1 millisecond.
     * All registered listeners will be notified of both the time change and
     * any messages that occur during this advance.
     * Note: if the race is advanced beyond its total amount of time, all
     * registered listeners will be notified that no time remains.
     */
    @Override
    public void advance() {
        advance(1);
    }

    /**
     * Advances the race's internal clock by 1 millisecond.
     * All registered listeners will be notified of both the time change and
     * any messages that occur during this advance.
     * 
     * Note: if the race is advanced beyond its total amount of time, all
     * registered listeners will be notified that no time remains.
     * 
     * @param theMillisecond is the amount of milliseconds to advance the race.
     */
    @Override
    public void advance(final int theMillisecond) {
        final int currentTime = myTime + theMillisecond;
        if (currentTime >= myTotalRaceTime + TIME_GAP) {
            myPropertyChangeSupport.firePropertyChange(PROPERTY_OVER_TIME,
                                                       myTotalRaceTime,
                                                       currentTime);
        } else {
            changeTime(currentTime);
        }
    }
    
    /**
     * Move the internal clock to the selected milliseconds.
     * All registered listeners will be notified of both the time change.
     * 
     * @param theMillisecond the time to move the race's internal "clock" to.
     * @throws IllegalArgumentException when theMillisecond is negative or greater
     * than the length of the race.
     */
    @Override
    public void moveTo(final int theMillisecond) {

        if (theMillisecond < 0 || theMillisecond > myTotalRaceTime) {
            throw new IllegalArgumentException("Invalid time!");
        } else {
            changeTime(theMillisecond);
        }
    }
    
    /**
     * Toggles participant information ON/OFF. Only telemetry messages of participants
     * that are toggled on will be displayed. The view should not display a participant
     * who is toggled off.
     * 
     * @param theParticpantID is the ID of the participant to be toggled.
     * @param theToggle determines the ON/OFF status of participant.
     */
    @Override
    public void toggleParticipant(final int theParticpantID, final boolean theToggle) {
        if (myRacers.containsKey(theParticpantID)) {
            myRacers.get(theParticpantID).setEnabled(theToggle);
        }
    }
    
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPropertyChangeSupport.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        
        myPropertyChangeSupport.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPropertyChangeSupport.removePropertyChangeListener(theListener);
        
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        
        myPropertyChangeSupport.removePropertyChangeListener(thePropertyName, theListener);
    }
    
    /**
     * Load a file of the race information and notify all registered listeners of the
     * progress updates during the load.
     * 
     * @param theRaceFile is the file containing the race information.
     * @throws IOException if the file is not in the correct format.
     */
    @Override
    public void loadRace(final File theRaceFile) throws IOException {
        
        // Clear all information from previous race.
        clearPreviousRace();
        
        // hold current header message.
        final StringBuilder headerMessage = new StringBuilder();
        
        // hold the current race participants.
        final Map<Integer, Racer> racerMap = new LinkedHashMap<>();
        
        // the line number of the line "#PARTICIPANTS"
        final int lineParticipants = 6;
        // holds the number of participants (to be determined).
        int numOfParticipants = 0;
        
        // Scanner to read the race file.
        final Scanner input = new Scanner(theRaceFile);
        
        // fire that the file is starting to load.
        myPropertyChangeSupport.firePropertyChange(PROPERTY_START_LOAD,
                                                   null,
                                                   "File is starting to load...");
        
        // Check if the file is empty.
        if (!input.hasNext()) {
            input.close();
            throw new IOException("The file is empty!");
        }
        // Boolean for valid format.
        boolean isValid = false;
        
        // Line counter.
        int i = 0;
        // Update every 1000 line read.
        final int updateTime = 1000;
        
        // Check if the file is in the correct format.
        while (input.hasNextLine()) {
            String oldValue = null;
            if (i % updateTime == 0) {
                final String newValue = "File is loading...Line: " + i;
                myPropertyChangeSupport.firePropertyChange(PROPERTY_START_LOAD,
                                                           oldValue,
                                                           newValue);
                oldValue = newValue;
            }
            
            // Get the current line.
            final String currentLine = input.nextLine();
            // Scanner for current line and delimit on ":"
            final Scanner line = new Scanner(currentLine);
            line.useDelimiter(DELIMITER);
            // The number of tokens on current line.
            final int tokens = new StringTokenizer(currentLine,
                                                   DELIMITER).countTokens();
            
            // Check header information:
            if (i < lineParticipants) {
                isValid = validHeaderInfo(line, i, tokens, headerMessage);
            // Check line "#PARTICIPANTS":
            } else if (i == lineParticipants
                            && PARTICIPANTS_HEADER.equals(line.next())
                            && line.hasNextInt()) {
                numOfParticipants = line.nextInt();
                isValid = tokens == HEADER_TOKENS;
            // Check racer information:
            } else if (i <= lineParticipants + numOfParticipants) {
                isValid = validRacerInfo(currentLine, tokens, racerMap);
            // Check messages:
            } else {
                isValid = determineValidMessageType(line, tokens, currentLine);
            }
            
            // If not valid format, throw exception.
            if (!isValid) {
                input.close();  // close source
                throw new IOException("Invalid format!");
            }
            i++;    // increment line index.
        }
        input.close();  // close source.
        
        firePropertyFinishedLoad(isValid, headerMessage, racerMap);
    }
    
    /**
     * Clear all information from previous race.
     */
    private void clearPreviousRace() {
        myRacers.clear();
        myMessages.clear();
    }
    
    /**
     * Helper method to determine if header message is in the correct format.
     * 
     * @param theLine is the Scanner of the current line.
     * @param theLineNumber is the line number of the current line.
     * @param theNumOfTokens is the number of tokens on the current line.
     * @param theHeaderMessage is the header message to be append to.
     * @return valid determines whether the line is in the correct format.
     */
    private boolean  validHeaderInfo(final Scanner theLine,
                                        final int theLineNumber,
                                        final int theNumOfTokens,
                                        final StringBuilder theHeaderMessage) {
        
        // The line number of each race information.
        final int lineRace = 0;
        final int lineTrack = 1;
        final int lineWidth = 2;
        final int lineHeight = 3;
        final int lineDistance = 4;
        final int lineTime = 5;
        
        boolean valid = theNumOfTokens == HEADER_TOKENS;
        
        if (valid) {
            if (theLineNumber == lineRace) {
                valid = RACE_HEADER.equals(theLine.next());
                theHeaderMessage.append(theLine.next());
                theHeaderMessage.append(DELIMITER);
            } else if (theLineNumber == lineTrack) {
                valid = TRACK_HEADER.equals(theLine.next());
                theHeaderMessage.append(theLine.next());
                theHeaderMessage.append(DELIMITER);
            } else if (theLineNumber == lineWidth) {
                valid = WIDTH_HEADER.equals(theLine.next());
                theHeaderMessage.append(theLine.next());
                theHeaderMessage.append(DELIMITER);
            } else if (theLineNumber == lineHeight) {
                valid = HEIGHT_HEADER.equals(theLine.next());
                theHeaderMessage.append(theLine.next());
                theHeaderMessage.append(DELIMITER);
            } else if (theLineNumber == lineDistance) {
                valid = DISTANCE_HEADER.equals(theLine.next());
                theHeaderMessage.append(theLine.next());
                theHeaderMessage.append(DELIMITER);
            } else if (theLineNumber == lineTime) {
                valid = TIME_HEADER.equals(theLine.next());
                if (theLine.hasNextInt()) {
                    myTotalRaceTime = theLine.nextInt();
                    theHeaderMessage.append(Integer.toString(myTotalRaceTime));
                }
            }
        }
        return valid;
    }
    
    /**
     * Helper method to determine if the racer message is in the correct format.
     * 
     * @param theLine is String of the current line.
     * @param theNumOfTokens is the number of tokens on the current line.
     * @param theRacerMap is a map containing the current racers.
     * @return valid determines whether the line is in the correct format.
     */
    private boolean validRacerInfo(final String theLine,
                                   final int theNumOfTokens,
                                   final Map<Integer, Racer> theRacerMap) {
        
        boolean valid = theLine.startsWith(HASHTAG) && theNumOfTokens == RACER_TOKENS;
        int racerID = 0;
        String racerName = "";
        
        if (valid) {
            // new string without #
            final String newLine = theLine.substring(1);
            // scanner on new string
            final Scanner newCurrentLine = new Scanner(newLine);
            // delimited on ":"
            newCurrentLine.useDelimiter(DELIMITER);
            
            // Check format:
            valid = newCurrentLine.hasNextInt();
            if (valid) {
                racerID = newCurrentLine.nextInt();
                racerName = newCurrentLine.next();
                valid = newCurrentLine.hasNextDouble();
                if (valid) {
                    final double startDistance = newCurrentLine.nextDouble();
                    final Racer racer = new Racer(racerID,
                                                  racerName,
                                                  startDistance);
                    theRacerMap.put(racerID, racer);
                }
            }
            newCurrentLine.close(); // close source.
        }
        
        return valid;
    }
    
    /**
     * Determine if the message is one of the correct message type:
     * telemetry, leader board, or finish line crossing.
     * 
     * @param theLine is the Scanner of the current line.
     * @param theNumOfTokens is the number of tokens.
     * @param theCurrentString is the current string.
     * @return valid determines if message is one of the valid types.
     */
    private boolean determineValidMessageType(final Scanner theLine,
                                              final int theNumOfTokens,
                                              final String theCurrentString) {
        boolean valid = false;
        // Get the message type ($L, $T, $C).
        final String messageNotation = theLine.next();
        
        // Determine which type of message and if it's the correct format:
        if (TELEMETRY.equals(messageNotation)) {
            valid = validTelemetryMessage(theLine, theNumOfTokens, theCurrentString);
        } else if (LEADERBOARD.equals(messageNotation)) {
            valid = validLeaderboadMessage(theLine, theNumOfTokens, theCurrentString);
        } else if (FINISH_LINE_CROSSING.equals(messageNotation)) {
            valid = validFinishLineCrossingMessage(theLine, theNumOfTokens, theCurrentString);
        }
        
        return valid;
    }
    
    /**
     * Helper method to determine if the telemetry messages are in the correct format.
     * 
     * @param theLine is the Scanner of the current line.
     * @param theNumOfTokens is the number of tokens on the current line.
     * @param theCurrentString is the current string.
     * @return valid determines whether the line is in the correct format.
     */
    private boolean validTelemetryMessage(final Scanner theLine,
                                          final int theNumOfTokens,
                                          final String theCurrentString) {
        
        final int timestampIndex = 1;
        final int racerIDIndex = 2;
        final int lapIndex = 4;
        
        int timestamp = 0;
        int racerID = 0;
        double distance = 0.0;
        int lap = 0;
        
        boolean valid = true;

        for (int i = 1; i < theNumOfTokens && valid; i++) {
            valid = theLine.hasNextLong();
            if (valid) {
                if (i == timestampIndex) {
                    timestamp = theLine.nextInt();
                } else if (i == racerIDIndex) {
                    racerID = theLine.nextInt();
                    valid = racerID > -1 && racerID < RACER_ID_LIMIT;
                } else if (i == lapIndex) {
                    lap = theLine.nextInt();
                }
            } else if (theLine.hasNextDouble()) {
                distance = theLine.nextDouble();
                valid = true;
            }
        }
        // Add message to a list of messages using time stamp as key:
        if (valid) {
            final Message telemetryMessage = new TelemetryMessage(timestamp,
                                                                  racerID,
                                                                  distance,
                                                                  lap,
                                                                  theCurrentString);
            addMessage(telemetryMessage, timestamp);
        }
        
        return valid;
    }
    /**
     * Helper method to determine if the finish line crossing
     * messages are in the correct format.
     * 
     * @param theLine is the Scanner of the current line.
     * @param theNumOfTokens is the number of tokens on the current line.
     * @param theCurrentString is the current string.
     * @return valid determines whether the line is in the correct format.
     */
    private boolean validFinishLineCrossingMessage(final Scanner theLine,
                                                   final int theNumOfTokens,
                                                   final String theCurrentString) {
        boolean valid = true;
        int timestamp = 0;
        int racerID = 0;
        int newLap = 0;
        boolean isFinished = false;

        for (int i = 1; i < theNumOfTokens && valid; i++) {
            if (i < theNumOfTokens - 1) {
                valid = theLine.hasNextLong();
                if (i == 1) {
                    timestamp = theLine.nextInt();
                } else if (i == 2) {
                    racerID = theLine.nextInt();
                } else {
                    newLap = theLine.nextInt();
                }
            } else {
                valid = theLine.hasNextBoolean();
                isFinished = theLine.nextBoolean();
            }
        }
        // Add message to a list of messages using time stamp as key:
        if (valid) {
            final Message lineCrossingMessage = new LineCrossingMessage(timestamp,
                                                                        racerID,
                                                                        newLap,
                                                                        isFinished,
                                                                        theCurrentString);
            addMessage(lineCrossingMessage, timestamp);
        }
        
        return valid;
    }
    
    /**
     * Helper method to determine if the leader board messages are in the correct format.
     * 
     * @param theLine is the Scanner of the current line.
     * @param theNumOfTokens is the number of tokens on the current line.
     * @param theCurrentString is the current string.
     * @return valid determines whether the line is in the correct format.
     */
    private boolean validLeaderboadMessage(final Scanner theLine,
                                           final int theNumOfTokens,
                                           final String theCurrentString) {
        boolean valid = true;
        int timestamp = 0;
        final List<Integer> listOfRacerIDs = new ArrayList<>();

        for (int i = 1; i < theNumOfTokens && valid; i++) {
            valid = theLine.hasNextLong();
            if (i == 1) {
                timestamp = theLine.nextInt();
            } else {
                listOfRacerIDs.add(theLine.nextInt());
            }
        }
        // Add message to a list of messages using time stamp as key:
        if (valid) {
            final Message leaderboardMessage = new LeaderboardMessage(timestamp,
                                                                      listOfRacerIDs,
                                                                      theCurrentString);
            addMessage(leaderboardMessage, timestamp);
        }
        
        return valid;
    }
    
    /**
     * Helper method to add the message to list if the time stamp already exist.
     * Else, the new time stamp will be put with the list of the current message.
     * 
     * @param theMessage is the message to be added.
     * @param theTimeStamp is the time stamp of the message.
     */
    private void addMessage(final Message theMessage,
                            final int theTimeStamp) {
        
        if (myMessages.containsKey(theTimeStamp)) {
            myMessages.get(theTimeStamp).add(theMessage);
        } else {
            final ArrayList<Message> list = new ArrayList<>();
            list.add(theMessage);
            myMessages.put(theTimeStamp, list);
        }
        
    }
    
    /**
     * Helper method to change the value of time.
     * Notify property time and property message.
     * 
     * @param theMillisecond is the time to change to.
     */
    private void changeTime(final int theMillisecond) {
        final int oldTime = myTime;
        myTime = theMillisecond;
        myPropertyChangeSupport.firePropertyChange(PROPERTY_TIME,
                                                   oldTime, myTime);
        
        if (oldTime < myTime) {
            changeMessageForward(oldTime);
        } else if (oldTime > myTime) {
            changeMessageBackward(oldTime);
        }
    }
    
    /**
     * Helper method to change the value of the header message
     * and notify property header message.
     * 
     * @param theHeaderMessage is the header message to change to.
     */
    private void changeRaceInfo(final StringBuilder theHeaderMessage) {
        final String oldHeaderMessages = myHeaderMessages;
        myHeaderMessages = theHeaderMessage.toString();
        myPropertyChangeSupport.firePropertyChange(PROPERTY_RACE_INFO,
                                                   oldHeaderMessages,
                                                   myHeaderMessages);
    }
    
    /**
     * 
     * @param theRacerMap is a map containing the current racers.
     */
    private void changeRaceParticipants(final Map<Integer, Racer> theRacerMap)  {
        final Map<Integer, Racer> oldParticipants = myRacers;
        myRacers = theRacerMap;
        myPropertyChangeSupport.firePropertyChange(PROPERTY_RACE_PARTICIPANTS,
                                                   oldParticipants,
                                                   myRacers);
    }
    
    /**
     * Helper method to change the message and notify property message.
     * 
     * @param theOldTime is the old time of the race.
     */
    private void changeMessageForward(final int theOldTime) {

        final List<Message> messages = new ArrayList<>();
        
        // Check messages between the time:
        for (int i = theOldTime; i < myTime && myMessages.containsKey(i); i++) {
            // Get a list of messages at certain time stamp.
            final List<Message> messagesList = myMessages.get(i);
            checkMessages(messagesList, messages);
        }
        fireMessageProperty(messages);
    }
    
    /**
     * Helper method to change the message in reverse and notify property message.
     * 
     * @param theOldTime is the time of the race.
     */
    private void changeMessageBackward(final int theOldTime) {

        // List of messages string to fire change.
        final List<Message> messages = new ArrayList<>();
        
        // Check messages between the time:
        for (int i = theOldTime; i > myTime && myMessages.containsKey(i); i--) {
            // Get a list of messages at certain time stamp.
            
            final List<Message> messagesList = myMessages.get(i);
            checkMessages(messagesList, messages);
        }
        fireMessageProperty(messages);
    }
    
    /**
     * Helper method to change the last message and notify the property message.
     * 
     * @param theTimeStamp is the last time stamp.
     * @param theList is the list of string of messages.
     */
    private void changeLastMessage(final int theTimeStamp,
                                   final List<Message> theList) {
        
        // Get a list of messages at last time stamp.
        final List<Message> messagesList = myMessages.get(theTimeStamp);
        
        for (int j = 0; j < messagesList.size(); j++) {
            final Message currentMessage = messagesList.get(j);
            // Check if message is a telemetry message.
            if (currentMessage instanceof TelemetryMessage) {
                // Cast to telemetry message:
                final TelemetryMessage telMessage = (TelemetryMessage) currentMessage;
                if (myRacers.get(telMessage.getRacerID()).isEnabled()) {
                    theList.add(messagesList.get(j));
                }
            }
        }
    }
    
    /**
     * A helper method to iterate over a list of messages.
     * Determines which message types and returns a list of
     * message strings to fire property change.
     * 
     * @param theList is the list of messages to be checked.
     * @param theStrings is a list of message strings.
     * @return messages is a list of message strings.
     */
    private List<Message> checkMessages(final List<Message> theList,
                                       final List<Message> theStrings) {
        
        for (int j = 0; j < theList.size(); j++) {
            final Message currentMessage = theList.get(j);
            // Check if message is a telemetry message.
            if (currentMessage instanceof TelemetryMessage) {
                // Cast to telemetry message:
                final TelemetryMessage telMessage = (TelemetryMessage) currentMessage;
                if (myRacers.containsKey(telMessage.getRacerID())
                                && myRacers.get(telMessage.getRacerID()).isEnabled()) {
                    theStrings.add(theList.get(j));
                }
            } else if (currentMessage instanceof LeaderboardMessage) {
                theStrings.add(theList.get(j));
            } else {
                // Cast to line crossing message:
                final LineCrossingMessage lineMessage =
                                (LineCrossingMessage) currentMessage;
                if (myRacers.containsKey(lineMessage.getRacerID())
                                && myRacers.get(lineMessage.getRacerID()).isEnabled()) {
                    theStrings.add(theList.get(j));
                }
            }
            if (currentMessage.getTimestamp() == myTotalRaceTime - 1) {
                changeLastMessage(currentMessage.getTimestamp() + 1, theStrings);
            }
        }
        return theStrings;
    }
    
    /**
     * Helper method to fire PROPERTY_MESSAGE.
     * 
     * @param theList is a list of all the string of messages.
     */
    private void fireMessageProperty(final List<Message> theList) {
        
        Message oldMessage = null;
        for (int i = 0; i < theList.size(); i++) {
            final Message newMessage = theList.get(i);
            myPropertyChangeSupport.firePropertyChange(PROPERTY_MESSAGE,
                                                       oldMessage,
                                                       newMessage);
            oldMessage = newMessage;
        }
    }
    
    /**
     * Helper method to determine if race file is valid.
     * If valid, fire property changes.
     * 
     * @param theValid is valid status of file.
     * @param theHeaderMessage is the header message.
     * @param theMap is the map of racers.
     */
    private void firePropertyFinishedLoad(final boolean theValid,
                                          final StringBuilder theHeaderMessage,
                                          final Map<Integer, Racer> theMap) {
        if (theValid) {
            changeRaceInfo(theHeaderMessage);
            changeRaceParticipants(theMap);
        }
    }

}
