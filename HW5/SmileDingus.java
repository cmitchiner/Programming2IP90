/**
 * CircleDingus -- part of HA RandomArtist
 * example of a very simple Dingus
 * @author huub
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
        g.drawArc(x, y, radius, radius,0, 360); //Outside of face
        g.fillArc(x + (x/10), y + (y/5), radius/10, radius/10, 0, 360); //Left eye
        g.fillArc(x + (x/5), y + (y/5), radius/10, radius/10, 0, 360); //Right eye
        g.drawArc(x, y, radius, radius, 180, 180);
    }
}
