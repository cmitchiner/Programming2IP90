
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
    private static final int defectionMin = 0;
    private static final int defectionMax = 300;
    private static final int defectionInit = 100;
    private static final int frequencyMin = 0;
    private static final int frequencyMax = 60;
    private static final int frequencyInit = 1;
    private static final int defectionTickSpacing = 100;
    private static final int frequencyTickSpacing = 10;

    private GridBagConstraints gbc; //Panel Manager
    private Patch[][] grid = new Patch[GRID_SIZE][GRID_SIZE]; // creates a grid of Patches based off the Grid Size assigned
    private JButton resetButton, goPauseButton, buttonSource, alternativeUpdate; // buttons for Go/Pause, Reset and the button source
    private JSlider defectionAwardFactor, frequency; // sliders to change DAF and frequency between board updates
    private JLabel frequencyLabel, defectionLabel; // labels to insert in our application for both slide
    private JPanel graphPanel, sliderPanel, labelPanel, buttonPanel; // adding panels for each component
    private JLabel[][] gridLayout; //Our grid of JPanels to match the grid of patches

    private ActionListener stepTimerListener; 
    private double alpha; // default value for the defection award factor (DAF)
    private Timer t; // Timer for our intervals
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
        alpha = (double) (defectionInit/100.0);
        initalizeGrid();
        
    }
    void initalizeGrid() {
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
                for (int i = GRID_MIN; i < GRID_SIZE; i++) {
                    for (int j = GRID_MIN; j < GRID_SIZE; j++) {
                        grid[i][j].clearNeighbors(); //Make sure this patch's neighbor list is clear before adding
                        checkNeighbors(i, j);       //Fill this patch[i][j] neighbor list
                        score = 0;                  //Reset score for looping purposes
                        for (int c = 0; c < grid[i][j].getNeighborsArraySize(); c++) {
                            if (grid[i][j].getNeighbor(c).isCooperating()) { //Calculates the amount of cooperating neighbors
                                score++;
                            }
                        }
                        if (!grid[i][j].isCooperating()) { //Score for defecting is how many neighbors are cooperating times alpha
                            score = score * getAlpha();
                        }
                        grid[i][j].setScore(score);
                    }
                }
                //After we reach this line, the scores for every single patch have been computed
                for (int i = GRID_MIN; i < grid.length; i++) {
                    for (int j = GRID_MIN; j < grid.length; j++) {
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

    void checkNeighbors(int row, int col) {
        Patch currentPatch = grid[row][col]; //Store the current patch

        //These methods use the modular operator and the grid size in order to determine 8 neighbors for every patch
        int rowPoint = (row+1)%GRID_SIZE;
        int rowModular = (row-1+GRID_SIZE)%GRID_SIZE;
        int colPoint = (col+1)%GRID_SIZE;
        int colModular = (col-1+GRID_SIZE)%GRID_SIZE;

        //Using are above point calculations, we can now determine all 8 neighbors
        // and add it to currentPatch's list of neighbors
        currentPatch.addNeighbor(grid[rowPoint][col]);
        currentPatch.addNeighbor(grid[rowPoint][colPoint]);
        currentPatch.addNeighbor(grid[rowPoint][colModular]);
        currentPatch.addNeighbor(grid[row][colPoint]);
        currentPatch.addNeighbor(grid[row][colModular]);
        currentPatch.addNeighbor(grid[rowModular][col]);
        currentPatch.addNeighbor(grid[rowModular][colPoint]);
        currentPatch.addNeighbor(grid[rowModular][colModular]);
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
        if (inGrid.length <= grid.length) //Make sure we arnt accessing grid out of bounds
        {
            for (int i = GRID_MIN; i < inGrid.length; i++) {
                for (int j = GRID_MIN; j < inGrid.length; j++) {
                    if (inGrid[i][j]) {
                        grid[i][j].setCooperating(true); // if that patch is cooperating in inGrid, set its corresponding patch true
                    } else {
                        grid[i][j].setCooperating(false); //if patch is defecting in Ingrid, set its corresponding patch false
                    }
                }
            }
        }
    }

    public void resetGrid() {
        Patch[][] gridReset = new Patch[GRID_SIZE][GRID_SIZE];
        for (int i = GRID_MIN; i < GRID_SIZE; i++) {
            for (int j = GRID_MIN; j < GRID_SIZE; j++) {
                gridReset[i][j] = new Patch(); // makes a new randomized grid
            }
        }
        grid = gridReset.clone(); // sets the randomized grid we just made as the new/current grid
    }

    //Builds the on screen graph of patches as JLabels
    public void buildCells() { 
        graphPanel.setPreferredSize(new Dimension(500,500));
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridLayout = new JLabel[GRID_SIZE][GRID_SIZE];
        for (int i = GRID_MIN; i < GRID_SIZE; i++) {
            for (int j = GRID_MIN; j < GRID_SIZE; j++) {
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
                        gridLayout[i][j].setBackground(Color.cyan); //Light blue for just changed to cooperating
                    } else {
                        gridLayout[i][j].setBackground(new Color(255,102,0)); //Orange for just changed to defecting
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
        valueA /= 100.0;
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
        defectionAwardFactor.setMajorTickSpacing(defectionTickSpacing); 
        defectionAwardFactor.setPaintTicks(true);
        defectionAwardFactor.setPaintLabels(true);
        defectionAwardFactor.setLabelTable(labelTableDefection);
        defectionAwardFactor.setAlignmentX(Component.LEFT_ALIGNMENT);

        frequency.setMajorTickSpacing(frequencyTickSpacing);
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
            setAlpha((double)(defectionInit/100.0)); //Reset local alpha variable
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
            value = value/100.0;
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
        for (int i = GRID_MIN; i < GRID_SIZE; i++) {
            for (int j = GRID_MIN; j < GRID_SIZE; j++) {
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