/**
 * SmileDingus -- part of HA RandomArtist
 * example of a somewhat simple Dingus
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 4th October 2020
 */

import java.awt.Graphics;

class SmileDingus extends Dingus {
    protected int radius;
    protected boolean filled; //true: filled, false: outline

    public SmileDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the CircleDingus properties, i.e., radius and filledness
        radius = random.nextInt(maxX/4);
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(x, y, radius, radius); //This is the outside of the circle, prints at x, y with a determined radius
        g.fillOval((int) (x + radius * .3), (int) (y + radius * .3), (int) (radius * .1), (int) (radius * .1)); //Left  eye
        g.fillOval((int) (x + radius * .6), (int) (y + radius * .3), (int) (radius * .1), (int)(radius * .1)); //Right eye, same as left but higher x coord
        g.drawArc((int) (x + radius * .35), (int) (y + radius * .45), radius/3, radius/3, 180, 180); //Smile, arc must be 180 a half circle
    }
}
