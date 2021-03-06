/**
 * Dingus --  part of HA Random Artist
 * abstract class representing an arbitrary shape
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * and Kees (Lecturer)
 * as group number 97
 * Date: 4th October 2020
 */
// TO BE COMPLETED 
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
abstract class Dingus {
    /** random generator to be used by all subclasses of Dingus 
     *  do not change
     */
    Random random = Painting.random;

    /** postion of the shape (upper left corner) **/
    protected int x, y;

    /** color used for drawing this shape **/
    protected Color color;

    /** maximal coordinates; drawing area is (0,0)- (maxX,maxY) **/
    protected int maxX, maxY;

    /**
     * initializes color and position to random values
     *
     * @param maxX, maxY - give maximum values for the position
     */   
     public Dingus(int maxX, int maxY) {
        this.maxX = maxX; this.maxY = maxY;
        // initialize to a random position
        x = random.nextInt(maxX);
        y = random.nextInt(maxY);

        // initialize to a random color
        float r = 0, g = 0, b = 0, a = 0;
        int randomColor = random.nextInt((3) + 1);
        switch(randomColor) {//In order to make our pantings even more unique, we have different color dominant RGB's that
                            //are picked at random
            case 0: //Normal RBG
                r = random.nextFloat(); //Random red color
                g = random.nextFloat(); //Random green color
                b = random.nextFloat(); //Random blue color
                a = random.nextFloat();
                break;
            case 1: //Higher green RGB
                r = random.nextFloat()/2f; 
                g = random.nextFloat(); 
                b = random.nextFloat()/2f;
                a = random.nextFloat(); 
                break;
            case 2: //Higher red RGB
                r = random.nextFloat();
                g = random.nextFloat()/2f;
                b = random.nextFloat()/2f;
                a = random.nextFloat();
                break;
            case 3: //Higher blue RGB
                r = random.nextFloat()/2f; 
                g = random.nextFloat()/2f; 
                b = random.nextFloat(); 
                a = random.nextFloat();
                break;
        }
        color = new Color(r, g ,b, a); //set color variable equal to a random RGB color
        //The last variable a, is our transperency variable
    }

    abstract void draw(Graphics g);
}
