/*
 * part of HA Random artist
 * TO BE COMPLETED
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * and Kees (Lecturer)
 * as group number 97
 * Date: 4th October 2020
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class Painting extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*---- Randomness ----*/
    /** you can change the variable SEED if you like
     *  the same program with the same SEED will generate exactly the same sequence of pictures
     */
    final static long SEED = 37; // seed for the random number generator; any number works

    /** do not change the following two lines **/
    final static Random random = new Random( SEED ); // random generator to be used by all classes
    int numberOfRegenerates = 0;

    int maxX = 850;
    int maxY = 450;
    

   /*---- Screenshots ----
    * do not change
    */
    char current = '0';
    String filename = "randomshot_"; // prefix
    
   /*---- Dinguses ----*/
    ArrayList<Dingus> shapes = new ArrayList<Dingus>(); 

    public Painting() {
        setPreferredSize(new Dimension(800, 450)); // make panel 800 by 450 pixels.
    }

    @Override
    protected void paintComponent(Graphics g) { // draw all your shapes
        super.paintComponent(g);     // clears the panel
        // draw all shapes
        for (Dingus d : shapes) {
            d.draw(g);
        }
        
    }

    /**
     * reaction to button press
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ( "Regenerate".equals(e.getActionCommand()) ) {
            regenerate();
            repaint();
        } else { // screenshot
            saveScreenshot( this, filename+current++ ); // ++ to show of compact code :-)
        }
    }

    void regenerate() {
        numberOfRegenerates++; // do not change
        
        // clear the shapes list
        shapes.clear();

        // create random shapes
       // int bufferSize = random.nextInt(200); This could be used to make a picture with much more shapes

        int bufferSize = random.nextInt(20); //Buffersize is added to a constant 10 in our forloop below,
                                            //So since buffersize is between 0 and 20, our range is from 10 shapes to 30 shapes
                                            //By changing bufferSize, we can have thousands of shapes
        for (int i = 0; i < 10 + bufferSize; i++) {
            int nextShapeForSwitch = random.nextInt(5);//Random int to pick which of the 5 shapes to pick from
            switch(nextShapeForSwitch) {
                case 0: //shapes.add(new PolygonDingus(maxX, maxY));
                    break;
                case 1: //shapes.add(new StarDingus(maxX, maxY));
                    break;
                case 2: //shapes.add(new CoolTreeDingus(maxX, maxY));
                    break;
                case 3: //shapes.add(new SnowflakeDingus(maxX, maxY));
                    break;
                case 4: //shapes.add(new SmileDingus(maxX, maxY));
                    break;
            }
        }
        shapes.add(new StarDingus(maxX, maxY));
    }

    /** 
     * saves a screenshot of a Component on disk
     *  overides existing files
     *
     * @param component - Component to be saved
     * @param name - filename of the screenshot, followed by a sequence number
     * 
     * do not change
     */
    void saveScreenshot(Component component, String name) {
        String randomInfo = ""+SEED+"+"+ (numberOfRegenerates-1); //minus 1 because the initial picture should not count
        System.out.println( SwingUtilities.isEventDispatchThread() );
        BufferedImage image =
            new BufferedImage(
                              component.getWidth(),
                              component.getHeight(),
                              BufferedImage.TYPE_INT_RGB
                             );
        // call the Component's paint method, using
        // the Graphics object of the image.
        Graphics graphics = image.getGraphics();
        component.paint( graphics ); // alternately use .printAll(..)
        graphics.drawString(randomInfo, 0, component.getHeight());
        
        try {
            ImageIO.write(image, "PNG", new File(name+".png"));
            System.out.println( "Saved screenshot as "+ name );
        } catch( IOException e ) {
            System.out.println( "Saving screenshot failed: "+e );
        }
    }
    
}