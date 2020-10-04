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
        radius = 150;
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(x, y, radius, radius);
        g.fillOval(x+40, y+50, 15, 15);
        g.fillOval(x+100, y+50, 15, 15);
        g.drawArc(x+50, y+90, radius-100, radius-130, 180, 180);
    }
}
