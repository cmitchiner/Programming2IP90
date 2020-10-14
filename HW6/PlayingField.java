
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

import java.awt.*;
import javax.swing.*;
import java.util.*;




class PlayingField extends JPanel implements ActionListener, ChangeListener {

    private static final long serialVersionUID = 1L;

    private static final int GRID_SIZE = 50;

    private GridBagConstraints gbc;
    
    private static final int defectionMin = 0;
    private static final int defectionMax = 300;
    private static final int defectionInit = 100;

    private Patch[][] grid = new Patch[GRID_SIZE][GRID_SIZE]; // creates a grid of Patches based off the Grid Size assigned

    private double alpha = 1.5; // default value for the defection award factor (DAF)

    private JButton resetButton, goPauseButton, buttonSource; // buttons for Go/Pause, Reset and the button source

    private JSlider defectionAwardFactor, frequency; // sliders to change DAF and frequency between board updates

    private JLabel frequencyLabel, defectionLabel; // labels to insert in our application for both slide
    
    private JPanel graphPanel, sliderPanel, labelPanel, buttonPanel; // adding panels for each component

    private Timer t; // Timer for our intervals

    private JLabel[][] gridLayout;
    private ActionListener actionBoy;
    
    // random number genrator
    private static final long SEED = 37L; // seed for random number generator; any number goes
    public static final Random random = new Random(SEED);
    
    
    public PlayingField() {
        setPreferredSize(new Dimension(800, 800));
        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        graphPanel = new JPanel(new GridLayout(50, 50));
        sliderPanel = new JPanel(new GridLayout(1, 2));
        labelPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel = new JPanel(new GridLayout(1, 2));
        initalizeTimer(1000);
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Patch(); // fill up the grid array with new Patches, randomized strategies
            }
        }
    }

    void initalizeTimer(int time)
    {
        actionBoy = new ActionListener(){
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
        t = new Timer(time,actionBoy);
    }

    /**
     * calculate and execute one step in the simulation
     */
    public void step() {
                double score = 0;
                t.start();
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
                        grid[i][j].chooseStrategyBasedOnNeighbors(); //Now change strategy based on neighbors
                    }
                }
            }

    void checkNeighbors(int i, int j) { // adds neighbors while checking for any issues (in edge cases where a patch only has 3 or 5 neighbors)
        try {
            Patch topLeft = grid[i-1][j-1];
            grid[i][j].addNeighbor(topLeft);
        } catch (IndexOutOfBoundsException e) {
            //Dont add
        }
        try {
            Patch topMiddle = grid[i-1][j];
            grid[i][j].addNeighbor(topMiddle);
        } catch (IndexOutOfBoundsException e) {
            //Dont add
        }
        try {
            Patch topRight = grid[i-1][j+1];
            grid[i][j].addNeighbor(topRight);
        } catch (IndexOutOfBoundsException e) {
            //Dont add
        }
        try {
            Patch middleLeft = grid[i][j-1];
            grid[i][j].addNeighbor(middleLeft);
        } catch (IndexOutOfBoundsException e) {
            //Dont add
        }
        try {
            Patch middleRight = grid[i][j+1];
            grid[i][j].addNeighbor(middleRight);
        } catch (IndexOutOfBoundsException e) {
            //Dont add
        }
        try {
            Patch bottomLeft = grid[i+1][j-1];
            grid[i][j].addNeighbor(bottomLeft);
        } catch (IndexOutOfBoundsException e) {
            //Dont add
        }
        try {
            Patch bottomMiddle = grid[i+1][j];
            grid[i][j].addNeighbor(bottomMiddle);
        } catch (IndexOutOfBoundsException e) {
            //Neighbor doesnt exist so do nothing
        }
        try {
            Patch bottomRight = grid[i+1][j+1];
            grid[i][j].addNeighbor(bottomRight);
        } catch (IndexOutOfBoundsException e) {
            //Neighbor doesnt exist so do nothing
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
                if (inGrid[i][j] == true)
                {
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
    
    public void buildCells() { 
        graphPanel.setPreferredSize(new Dimension(500,500));
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridLayout = new JLabel[50][50];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                gridLayout[i][j] = new JLabel();
                gridLayout[i][j].setOpaque(true);
                if (grid[i][j].isCooperating()) {
                    gridLayout[i][j].setBackground(Color.blue); //Cooperating
                } else {
                    gridLayout[i][j].setBackground(Color.red); //Defecting
                }
                gridLayout[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); //Outline of boxes
                //gridLayout[i][j].setPreferredSize(new Dimension(1, 1));
                graphPanel.add(gridLayout[i][j]);
            }
        }
        gbc.gridy = 0; //First Panel in PlayingField
        add(graphPanel, gbc);
    }

    public void buildLabels() {
        double valueF = frequency.getValue();
        frequencyLabel = new JLabel("Frequency: 0" + valueF + " seconds");
        frequencyLabel.setHorizontalAlignment(JLabel.RIGHT);
        frequencyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        double valueA = defectionAwardFactor.getValue();
        valueA /= 100;
        defectionLabel = new JLabel("Defection: " + valueA);
        defectionLabel.setHorizontalAlignment(JLabel.LEFT);
        defectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelPanel.add(defectionLabel);
        labelPanel.add(frequencyLabel);

        gbc.gridy = 1;
        add(labelPanel, gbc);
    }

    public void buildSlider() {
        defectionAwardFactor = new JSlider(JSlider.HORIZONTAL, defectionMin, defectionMax, defectionInit);
        frequency = new JSlider(JSlider.HORIZONTAL, 0, 60, 1);

        Hashtable<Integer, JLabel> labelTableDefection = new Hashtable<Integer, JLabel>();; //Set Labels for Defection Slider
        labelTableDefection.put(0, new JLabel("0.0"));
        labelTableDefection.put(100, new JLabel("1.0"));
        labelTableDefection.put(200, new JLabel("2.0"));
        labelTableDefection.put(300, new JLabel("3.0"));

        Hashtable<Integer, JLabel> labelTableFrequency = new Hashtable<Integer, JLabel>();; //Set Labels for Frequency Slider
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

    public void buildButtons() {
        resetButton = new JButton("Reset");
        goPauseButton = new JButton("GO");
        goPauseButton.addActionListener(this); //Set ActionListeners to this classes actionPerformed method
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);
        buttonPanel.add(goPauseButton);
        gbc.gridy = 3;          //Add them in the third panel in PlayingField
        add(buttonPanel,gbc);
    }

         

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
            if (t.isRunning())
            {
                t.stop();
            }
            graphPanel.removeAll(); // removes the graph, labels, slider and buttons
            labelPanel.removeAll();
            sliderPanel.removeAll();
            buttonPanel.removeAll();
            resetGrid();           // resets the grid then rebuilds it
            buildCells();
            buildSlider();
            buildLabels();
            buildButtons();
            revalidate();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider sliderSource = (JSlider) e.getSource();
        if (sliderSource == defectionAwardFactor){
            double value = sliderSource.getValue();
            value = value/100;
            setAlpha(value);
            defectionLabel.setText("Defection: " + value);
        } else {
            double value = sliderSource.getValue();
            if (value < 10)
            frequencyLabel.setText("Frequency: 0" + value + " seconds");
            else
            frequencyLabel.setText("Frequency: " + value + " seconds");
        }
    }
}