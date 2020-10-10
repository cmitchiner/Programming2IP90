/**
 * CoolTreeDingus -- part of HA RandomArtist
 * example of a complicated Dingus
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 4th October 2020
 */

import java.awt.Graphics;

public class CoolTreeDingus extends Dingus {
    protected int rotationAngle, length;
    final int LEVEL = 5;
    int x1, y1;  

    public CoolTreeDingus(int maxX, int maxY) {
        super(maxX, maxY);
        x1 = random.nextInt(maxX);
        y1 = random.nextInt(80); //Since these get so big it cant be higher than 80 or else it will be off the screen
        length = random.nextInt(((13 + 10)) - 10); //The length from top to bottom also called height
        rotationAngle = random.nextInt((360) + 1); //If the angle is negative the tree is rightside UP
                                                //90 = rightsideUp 
                                                //180 = turned to the side
    }
    // Recursive method to draw the fractal tree. Summed up, this draws a trunk, then splits off
    // into two separate trees, which draws "trunks" (branches) from the base of the previous "tree"
    // to a new endpoint, repeating 5 times until the base case is reached and no further branching happens.
    private void drawTree(Graphics g, int x1, int y1, double rotationAngle, int length) {
        if (length == 0) {
            return;
        } 
        int x2 = x1 + (int) (Math.cos(Math.toRadians(rotationAngle)) * length * 10.0);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(rotationAngle)) * length * 10.0);
        g.drawLine(x1, y1, x2, y2);                          //This draws a line from the base of our tree to the top. 
        drawTree(g, x2, y2, rotationAngle - 20, length - 1); // splits the tree and draws a line from the new base, from the
                                                             // left of the top of the old base (at length = 0, base = trunk)
        drawTree(g, x2, y2, rotationAngle + 20, length - 1); // splits the tree and draws a line from the new base, from the
                                                             // right of the top of the old base (at length = 0, base = trunk)
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        drawTree(g, x1, y1, -rotationAngle, length); //Negative rotationAngle so our trees are not upside down
    }
}