
/*
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part PlayingField
 * 
 * @author Charles Mitchiner (1574531)
 * @author Samir Saidi (1548735)
 * assignment group 97
 * 
 * assignment copyright Kees Huizing
 */
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import javax.swing.*;
import java.util.*;




class PlayingField extends JPanel implements ActionListener, ChangeListener, MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = -3799783618101669910L; //Added by VSCode to prevent warning

    private static final int GRID_SIZE = 50;
    private static final int GRID_MIN = 0;
    private static final int GRID_MAX = 49;
    private static final int defectionMin = 0;
    private static final int defectionMax = 300;
    private static final int defectionInit = 150;
    private static final int frequencyMin = 0;
    private static final int frequencyMax = 60;
    private static final int frequencyInit = 1;

    private GridBagConstraints gbc; //Panel Manager

    private Patch[][] grid = new Patch[GRID_SIZE][GRID_SIZE]; // creates a grid of Patches based off the Grid Size assigned


    private double alpha = 1.5; // default value for the defection award factor (DAF)

    private JButton resetButton, goPauseButton, buttonSource, alternativeUpdate; // buttons for Go/Pause, Reset and the button source

    private JSlider defectionAwardFactor, frequency; // sliders to change DAF and frequency between board updates

    private JLabel frequencyLabel, defectionLabel; // labels to insert in our application for both slide
    
    private JPanel graphPanel, sliderPanel, labelPanel, buttonPanel; // adding panels for each component

    private Timer t; // Timer for our intervals

    private JLabel[][] gridLayout; //Our grid of JPanels to match the grid of patches

    private ActionListener stepTimerListener; 

    private boolean whichUpdateRule = false; //False is default rule, true is alternative rule
    
    // random number genrator
    private static final long SEED = 37L; // seed for random number generator; any number goes
    public static final Random random = new Random(SEED);
    
    
    public PlayingField() {
        setPreferredSize(new Dimension(800, 800)); //Set size of main panel
        gbc = new GridBagConstraints(); //Setup gridBagConstraints
        setLayout(new GridBagLayout());
        graphPanel = new JPanel(new GridLayout(50, 50)); //Intalize sub panels
        sliderPanel = new JPanel(new GridLayout(1, 2));
        labelPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel = new JPanel(new GridLayout(1, 2));
        initalizeTimer(1000);                           //Intalize timer
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Patch(); // fill up the grid array with new Patches, randomized strategies
            }
        }
    }

    //Sets up the step loop to go until the pause button is clicked and intilazes a new timer with this listener
    void initalizeTimer(int time) {
        stepTimerListener = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                step(); // starts a new step
                graphPanel.removeAll(); // removes the current graph
                buildCells();           // rebuilds it based off the new changes
                graphPanel.revalidate();
                if (goPauseButton.getText().equals("GO")) {
                    t.stop(); // stops the loop once you hit pause
                }
            }
        };
        t = new Timer(time,stepTimerListener);
    }

    /**
     * calculate and execute one step in the simulation
     */
    public void step() {
                double score = 0; //Variable to hold calculated score
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j < 50; j++) {
                        grid[i][j].clearNeighbors(); //Make sure this patch's neighbor list is clear before adding
                        checkNeighbors(i, j);       //Fill this patch[i][j] neighbor list
                        score = 0;                  //Reset score for looping purposes
                        if (grid[i][j].isCooperating()) {
                            for (int c = 0; c < grid[i][j].getNeighborsArraySize(); c++) {
                                if (grid[i][j].getNeighbor(c).isCooperating()) { //The score of a cooperating patch is amount of neighbors who also cooperate
                                    score++;
                                }
                            }
                        } else { //Defecting
                            for (int c = 0; c < grid[i][j].getNeighborsArraySize(); c++) {
                                if (grid[i][j].getNeighbor(c).isCooperating()) { //Calculate cooperating neighbors
                                    score++;
                                }
                            }
                            score = score * getAlpha(); //All cooperating neighbors times defection award factor
                        }
                        grid[i][j].setScore(score); //Set this patch's score
                    }
                }
                //After we reach this line, the scores for every single patch have been computed
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) {
                        if (!whichUpdateRule) {
                            grid[i][j].setPreviousStrategy(grid[i][j].isCooperating());
                            grid[i][j].chooseStrategyBasedOnNeighbors(); //Now change strategy based on neighbors
                        } else {
                            grid[i][j].setPreviousStrategy(grid[i][j].isCooperating());
                            grid[i][j].chooseStrategyBasedOnNeighborsAlternative(); //Change strategy using alternative method
                        }
                    }
                }
            }

    void checkNeighbors(int i, int j) { // adds neighbors while checking for any issues (in edge cases where a patch only has 3 or 5 neighbors)
        try {
            Patch topLeft = grid[i-1][j-1];
            grid[i][j].addNeighbor(topLeft);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MIN && j == GRID_MIN) { //Top left corner 
                Patch topLeft = grid[GRID_MAX][GRID_MAX]; //Calculate the topLeft neighbor for the topLeft corner
                grid[i][j].addNeighbor(topLeft);
            } else if (i == GRID_MAX && j == GRID_MIN) { //bottom left corner
                Patch topLeft = grid[GRID_MAX-1][GRID_MAX];
                grid[i][j].addNeighbor(topLeft); //Calculate the topLeft neighbor for the bottomLeft corner
            } else if (i == GRID_MIN && j == GRID_MAX) { //Top right corner
                Patch topLeft = grid[GRID_MAX][GRID_MAX-1]; //Calculate the topLeft neighbor for the topRight corner
                grid[i][j].addNeighbor(topLeft);
            } else if (j == GRID_MIN) { //Left edge
                for (int iterate = i-1; iterate < 3+(i-1); iterate++) {
                    Patch topLeft = grid[iterate][GRID_MAX]; //Calculates all neighbors for a patch on the left edge
                    grid[i][j].addNeighbor(topLeft);
                }
            } else if (i == GRID_MIN) { //Top edge
                for (int iterate = j-1; iterate < 3+(j-1); iterate++) { //Calculates all neighbors for a patch on the top edge
                    Patch topLeft = grid[GRID_MAX][iterate];
                    grid[i][j].addNeighbor(topLeft);
                }
            }
        }
        try {
            Patch topMiddle = grid[i-1][j];
            grid[i][j].addNeighbor(topMiddle);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MIN && j == GRID_MIN) { //Top left corner
                Patch topMiddle = grid[GRID_MAX][GRID_MIN]; //Calculates the topMiddle neighbor for the top left corner
                grid[i][j].addNeighbor(topMiddle);
            } else if (i == GRID_MIN && j == GRID_MAX) {//Top right corner
                Patch topMiddle = grid[GRID_MAX][GRID_MAX]; //Calculates the topMiddle neighbor for the top right corner
                grid[i][j].addNeighbor(topMiddle);
            }
        }
        try {
            Patch topRight = grid[i-1][j+1];
            grid[i][j].addNeighbor(topRight);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MIN && j == GRID_MIN) {//Top left corner
                Patch topRight = grid[GRID_MAX][GRID_MIN+1]; //Calculates the topRight neighbor for the top left corner
                grid[i][j].addNeighbor(topRight);
            } else if (i == GRID_MAX && j == GRID_MAX) {//bottom right corner
                Patch topRight = grid[GRID_MAX-1][GRID_MIN]; //Calcuates the topRight neighbor for the bottom right corner
                grid[i][j].addNeighbor(topRight);
            } else if (i == GRID_MIN && j == GRID_MAX) { //Top right corner
                Patch topRight = grid[GRID_MAX][GRID_MIN]; //Calcuates the topRight neighbor for the top right corner
                grid[i][j].addNeighbor(topRight);
            }
        }
        try {
            Patch middleLeft = grid[i][j-1];
            grid[i][j].addNeighbor(middleLeft);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MIN && j == GRID_MIN) { //Top left corner
                Patch middleLeft = grid[GRID_MIN][GRID_MAX]; //Calcuates the middleLeft neighbor for the top left  corner
                grid[i][j].addNeighbor(middleLeft);
            } else if (i == GRID_MAX && j == GRID_MIN) {//bottom left corner
                Patch middleLeft = grid[GRID_MAX][GRID_MAX]; //Calcuates the middleLeft neighbor for the bottom left corner
                grid[i][j].addNeighbor(middleLeft);
            }
        }
        try {
            Patch middleRight = grid[i][j+1];
            grid[i][j].addNeighbor(middleRight);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MIN && j == GRID_MAX) { //Top right corner
                Patch middleRight = grid[GRID_MIN][GRID_MIN];   //Calcuates the middleRight neighbor for the top right  corner
                grid[i][j].addNeighbor(middleRight);
            } else if (i == GRID_MAX && j == GRID_MAX) {//bottom right corner
                Patch middleRight = grid[GRID_MAX][GRID_MIN]; //Calcuates the middleRight neighbor for the bottom right corner
                grid[i][j].addNeighbor(middleRight);
            }
        }
        try {
            Patch bottomLeft = grid[i+1][j-1];
            grid[i][j].addNeighbor(bottomLeft);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MIN && j == GRID_MIN) {//Top left corner
                Patch bottomLeft = grid[GRID_MIN+1][GRID_MAX]; //Calcuates the bottomLeft neighbor for the top left corner
                grid[i][j].addNeighbor(bottomLeft);
            } else if (i == GRID_MAX && j == GRID_MAX){ //bottom right corner
                Patch bottomLeft = grid[GRID_MIN][GRID_MAX-1]; //Calcuates the bottomLeft neighbor for the bottom right  corner
                grid[i][j].addNeighbor(bottomLeft);
            } else if (i == GRID_MAX && j == GRID_MIN){ //bottem left corner
                Patch bottomLeft = grid[GRID_MIN][GRID_MAX];//Calcuates the bottomLeft neighbor for the bottom left corner
                grid[i][j].addNeighbor(bottomLeft);
            }
        }
        try {
            Patch bottomMiddle = grid[i+1][j];
            grid[i][j].addNeighbor(bottomMiddle);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MAX && j == GRID_MAX){ //bottom right corner
                Patch bottomMiddle = grid[GRID_MIN][GRID_MAX]; //Calcuates the bottoMiddle neighbor for the bottom right corner
                grid[i][j].addNeighbor(bottomMiddle);
            } else if (i == GRID_MAX && j == GRID_MIN) { //bottem left corner
                Patch bottomMiddle = grid[GRID_MIN][GRID_MIN]; //Calcuates the bottoMiddle neighbor for the bottom left corner
                grid[i][j].addNeighbor(bottomMiddle);
            }
        }
        try {
            Patch bottomRight = grid[i+1][j+1];
            grid[i][j].addNeighbor(bottomRight);
        } catch (IndexOutOfBoundsException e) {
            if (i == GRID_MIN && j == GRID_MAX){ //Top right corner
                Patch bottomRight = grid[GRID_MIN+1][GRID_MIN]; //Calcuates the bottomRight neighbor for the top right corner
                grid[i][j].addNeighbor(bottomRight);
            } else if (i == GRID_MAX && j == GRID_MAX) { //bottom right corner
                Patch bottomRight = grid[GRID_MIN][GRID_MIN]; //Calcuates the bottomRight neighbor for the bottom right corner
                grid[i][j].addNeighbor(bottomRight);
            } else if (i == GRID_MAX && j == GRID_MIN){ //bottem left corner
                Patch bottomRight = grid[GRID_MIN][GRID_MIN+1]; //Calcuates the bottomRight neighbor for the bottom left corner
                grid[i][j].addNeighbor(bottomRight);
            } else if (j == GRID_MAX){ //Right edge
                for (int iterate = i-1; iterate < 3+(i-1); iterate++) {
                    Patch topLeft = grid[iterate][GRID_MIN]; //Calculates all neighbors for a patch on the right edge
                    grid[i][j].addNeighbor(topLeft);
                }
            } else if (i == GRID_MAX){ //Bottom edge
                for (int iterate = j-1; iterate < 3+(j-1); iterate++) {
                    Patch topLeft = grid[GRID_MIN][iterate]; //Calculates all neighbors for a patch on the bottom edge
                    grid[i][j].addNeighbor(topLeft);
                }
            }
        }
    }

    public void setAlpha(double alpha) { // Alpha mutator

       this.alpha = alpha;
    }

    public double getAlpha() { // Alpha accessor
        return alpha;
    }

    // return grid as 2D array of booleans
    // true for cooperators, false for defectors
    // precondition: grid is rectangular, has non-zero size and elements are
    // non-null
    public boolean[][] getGrid() {
        boolean[][] resultGrid = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                resultGrid[x][y] = grid[x][y].isCooperating();
            }
        }
        return resultGrid;
    }

    // sets grid according to parameter inGrid
    // a patch should become cooperating if the corresponding
    // item in inGrid is true
    public void setGrid(boolean[][] inGrid) {
        inGrid = getGrid(); // creates a new boolean array based off the current grid of patches' strategy 
        for (int i = 0; i < 50; i++) {
            for (int j = 0; i < 50; j++) {
                if (inGrid[i][j] == true) {
                    grid[i][j].setCooperating(true); // if that patch is cooperating in inGrid, set its corresponding patch
                                                     // in the regular grid to also cooperate
                }
            }
        }
    }

    public void resetGrid() {
        Patch[][] gridReset = new Patch[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                gridReset[i][j] = new Patch(); // makes a new randomized grid
            }
        }
        grid = gridReset.clone(); // sets the randomized grid we just made as the new/current grid
    }

    //Builds the on screen graph of patches as JLabels
    public void buildCells() { 
        graphPanel.setPreferredSize(new Dimension(500,500));
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridLayout = new JLabel[50][50];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                gridLayout[i][j] = new JLabel();
                gridLayout[i][j].addMouseListener(this); //Add a mouse listener to every single patch
                gridLayout[i][j].setOpaque(true);

                if (grid[i][j].isCooperating() == grid[i][j].getPreviousStrategy()) {
                    if (grid[i][j].isCooperating()) {
                        gridLayout[i][j].setBackground(Color.blue); //Blue for Cooperating
                    } else {
                        gridLayout[i][j].setBackground(Color.red); //Red for defecting
                    }
                }
                else if (grid[i][j].isCooperating() != grid[i][j].getPreviousStrategy()) {
                    if (grid[i][j].isCooperating()) {
                        gridLayout[i][j].setBackground(Color.cyan); //Cyan for just changed to cooperating
                    } else {
                        gridLayout[i][j].setBackground(Color.magenta); //Magenta for just changed to defecting
                    }
                }
                gridLayout[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); //Outline of boxes
                graphPanel.add(gridLayout[i][j]);
            }
        }
        gbc.gridy = 0; //Top Panel in PlayingField, hence gridy = 0
        add(graphPanel, gbc);
    }
    //Builds the labels for the sliders, and there numerical output
    public void buildLabels() {
        double valueF = frequency.getValue();
        frequencyLabel = new JLabel("Frequency: 0" + valueF + " seconds"); //Label for frequency
        frequencyLabel.setHorizontalAlignment(JLabel.RIGHT);            //Set alignment
        frequencyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        double valueA = defectionAwardFactor.getValue();
        valueA /= 100;
        defectionLabel = new JLabel("Defection: " + valueA);
        defectionLabel.setHorizontalAlignment(JLabel.LEFT); //Set alignment
        defectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelPanel.add(defectionLabel);
        labelPanel.add(frequencyLabel);

        gbc.gridy = 1; //Middle Panel in playingField hence, gridy = 1
        add(labelPanel, gbc);
    }

    //Builds sliders and setsup HashTabels for their labels
    public void buildSlider() {
        defectionAwardFactor = new JSlider(JSlider.HORIZONTAL, defectionMin, defectionMax, defectionInit); //Slider for alpha
        frequency = new JSlider(JSlider.HORIZONTAL, frequencyMin, frequencyMax, frequencyInit); //Slider for frequency

        Hashtable<Integer, JLabel> labelTableDefection = new Hashtable<Integer, JLabel>(); //Set Labels for Defection Slider
        labelTableDefection.put(0, new JLabel("0.0"));
        labelTableDefection.put(100, new JLabel("1.0"));
        labelTableDefection.put(200, new JLabel("2.0"));
        labelTableDefection.put(300, new JLabel("3.0"));

        Hashtable<Integer, JLabel> labelTableFrequency = new Hashtable<Integer, JLabel>(); //Set Labels for Frequency Slider
        labelTableFrequency.put(0, new JLabel("0"));
        labelTableFrequency.put(10, new JLabel("10"));
        labelTableFrequency.put(20, new JLabel("20"));
        labelTableFrequency.put(30, new JLabel("30"));
        labelTableFrequency.put(40, new JLabel("40"));
        labelTableFrequency.put(50, new JLabel("50"));
        labelTableFrequency.put(60, new JLabel("60"));

        //Setup Tick spacing and labels for both sliders
        defectionAwardFactor.setMajorTickSpacing(100); 
        defectionAwardFactor.setPaintTicks(true);
        defectionAwardFactor.setPaintLabels(true);
        defectionAwardFactor.setLabelTable(labelTableDefection);
        defectionAwardFactor.setAlignmentX(Component.LEFT_ALIGNMENT);

        frequency.setMajorTickSpacing(10);
        frequency.setPaintTicks(true);
        frequency.setPaintLabels(true);
        frequency.setLabelTable(labelTableFrequency);
        frequency.setAlignmentX(Component.RIGHT_ALIGNMENT);

        defectionAwardFactor.addChangeListener(this); //Set ChangeListeners to this classes stateChanged method 
        frequency.addChangeListener(this);
        
        sliderPanel.add(defectionAwardFactor);
        sliderPanel.add(frequency);
        gbc.gridy = 2;  
        add(sliderPanel,gbc);
    }

    //Builds our three buttons and attaches listeners to them
    public void buildButtons() {
        resetButton = new JButton("Reset");
        goPauseButton = new JButton("GO");
        alternativeUpdate = new JButton("AlternativeUpdate");
        goPauseButton.addActionListener(this); //Set ActionListeners to this classes actionPerformed method
        resetButton.addActionListener(this);
        alternativeUpdate.addActionListener(this);
        buttonPanel.add(resetButton);
        buttonPanel.add(goPauseButton);
        buttonPanel.add(alternativeUpdate);
        gbc.gridy = 3;          //Add them in the third panel in PlayingField
        add(buttonPanel,gbc);
    }

         
    //Button action Listener
    @Override
    public void actionPerformed(ActionEvent e) {
        if ( "GO".equals(e.getActionCommand()) ) { // when go is pressed
            buttonSource = goPauseButton;
            buttonSource.setText("PAUSE");
            t.stop();
            initalizeTimer(1000*frequency.getValue());
            t.start();
            
            
        } else if ("PAUSE".equals(e.getActionCommand())) {
            t.stop();
            buttonSource = (JButton) e.getSource();
            buttonSource.setText("GO"); // turns the button to a go button
            //Start stepping
        } else if ("Reset".equals(e.getActionCommand())) {
            if (t.isRunning()) {
                t.stop();
            }
            whichUpdateRule = false;
            graphPanel.removeAll(); // removes the graph, labels, slider and buttons
            labelPanel.removeAll();
            sliderPanel.removeAll();
            buttonPanel.removeAll();
            resetGrid();           // resets the grid then rebuilds it
            buildCells();
            buildSlider();
            buildLabels();
            buildButtons();
            setAlpha(1.5); //Reset local alpha variable
            graphPanel.revalidate();
            labelPanel.revalidate();
            sliderPanel.revalidate();
            buttonPanel.revalidate();
            revalidate();
        } else if ("AlternativeUpdate".equals(e.getActionCommand())) {
            alternativeUpdate.setText("DefaultUpdate");
            whichUpdateRule = true;
        } else if ("DefaultUpdate".equals(e.getActionCommand())) {
            alternativeUpdate.setText("AlternativeUpdate");
            whichUpdateRule = false;
        }
    }

    //Slider Change listener
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider sliderSource = (JSlider) e.getSource();
        if (sliderSource == defectionAwardFactor) { //stateChanged for defection
            double value = sliderSource.getValue();
            value = value/100;
            setAlpha(value);
            defectionLabel.setText("Defection: " + value);
        } else {                                   //stateChanged for frequency
            double value = sliderSource.getValue();
            if (value < 10){
            frequencyLabel.setText("Frequency: 0" + value + " seconds");
            } else {
            frequencyLabel.setText("Frequency: " + value + " seconds");
            }
        }
    }
    //Mouse listener for extra credit
    @Override
    public void mousePressed(MouseEvent e) {
        JLabel label = (JLabel)e.getSource();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (gridLayout[i][j] == label){ //Loop through the grid of Jpanels, if one of the clicked panels is on the grid
                    grid[i][j].toggleStrategy(); //Change its strategy
                    if (grid[i][j].isCooperating()) {//Change its color
                        gridLayout[i][j].setBackground(Color.BLUE);
                    } else {
                        gridLayout[i][j].setBackground(Color.RED);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}