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


import javax.swing.*;


class PrisonersDilemma {

    JFrame frame;


    PlayingField playingField;
    Patch patch = new Patch();


    void buildGUI() {
        SwingUtilities.invokeLater(() -> {

            playingField = new PlayingField();

            frame = new JFrame("Prisoner’s Dilemma Game Simulation"); // Title of Window

            frame.add(playingField); // Add it to the panel

            playingField.buildCells(); // Setup the cells

            playingField.buildSlider(); // Setup the Slider

            playingField.buildLabels(); //Setup  Slider Labels

            playingField.buildButtons(); // Setup Buttons

            // Finish setting up the frame
            playingField.revalidate();
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        (new PrisonersDilemma()).buildGUI();
    }
}
