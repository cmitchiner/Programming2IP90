/*

 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part PrisonersDilemma
 * 
 * @author Charles Mitchiner (1574531)
 * @author Samir Saidi (1548735)
 * assignment group 97
 * 
 * assignment copyright Kees Huizing
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

class PrisonersDilemma extends JFrame {

    JFrame frame;

    JButton resetButton;
    JButton goPauseButton;
    JSlider defectionAwardFactor;

    PlayingField playingField;

    Hashtable<Integer, JLabel> labelTable;
    JLabel sliderValue;

    JLabel[][] gridLayout;
    

    private static final int defectionMin = 0;
    private static final int defectionMax = 300;
    private static final int defectionInit = 150;
    
    void buildGUI() {
        SwingUtilities.invokeLater( () -> {

                playingField = new PlayingField();  

                frame = new JFrame("Prisoner’s Dilemma Game"); //Title of Window
                
                playingField.setLayout(new GridLayout(50,50)); //set the grid as a 50 x 50 matrix
                frame.add(playingField); //Add it to the panel

                buildCells(); //Setup the cells

                buildSlider(); //Setup the Slider

                buildButtons(); //Setup Buttons

                //Finish setting up the frame
                playingField.revalidate();
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        } );
    }
    
    public void buildSlider(){

        defectionAwardFactor = new JSlider(JSlider.VERTICAL,defectionMin, defectionMax, defectionInit);
        labelTable = new Hashtable<Integer, JLabel>();

        labelTable.put(0, new JLabel("0.0"));
        labelTable.put(50, new JLabel("0.5"));
        labelTable.put(100, new JLabel("1.0"));
        labelTable.put(150, new JLabel("1.5"));
        labelTable.put(200, new JLabel("2.0"));
        labelTable.put(250, new JLabel("2.5"));
        labelTable.put(300, new JLabel("3.0"));

        defectionAwardFactor.setMajorTickSpacing(50);
        defectionAwardFactor.setMinorTickSpacing(25);
        defectionAwardFactor.setPaintTicks(true);
        defectionAwardFactor.setPaintLabels(true);
        defectionAwardFactor.setLabelTable(labelTable);

        sliderValue = new JLabel(" α : 1.50");
        defectionAwardFactor.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e)
            {
                double value = defectionAwardFactor.getValue();
                DecimalFormat df = new DecimalFormat("0.00##");
                String result = df.format(value/100);

                sliderValue.setText(" α : " + result);
            }
        });

        frame.add(defectionAwardFactor, BorderLayout.WEST);
        frame.add(sliderValue, BorderLayout.EAST);
    }
    public void buildCells()
    {
        int i;
        gridLayout = new JLabel[50][50];
        for (i = 0; i < 50; i++) {
            for (int j = 0; j < gridLayout.length; j++) {
                gridLayout[i][j] = new JLabel();
                gridLayout[i][j].setOpaque(true);
                gridLayout[i][j].setBackground(Color.white);
                gridLayout[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                //gridLayout[i][j].setPreferredSize(new Dimension(1, 1));
                playingField.add(gridLayout[i][j]);
            }
        }
    }
    public void buildButtons()
    {
        resetButton = new JButton("Reset");
        goPauseButton = new JButton("GO");
        goPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (goPauseButton.getText() == "GO"){
                    goPauseButton.setText("PAUSE");
                    //Pause the game
                }
                else
                {
                    goPauseButton.setText("GO");
                    //Start the game
                }
            }
        });
        resetButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Reset the Program
            }

        });
        frame.add(resetButton, BorderLayout.SOUTH);
        frame.add(goPauseButton, BorderLayout.NORTH);
    }

    
    
    public static void main( String[] a ) {
        (new PrisonersDilemma()).buildGUI();
    }
}
