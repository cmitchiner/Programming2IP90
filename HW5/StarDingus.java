/**
 * StarDingus -- part of HA RandomArtist
 * example of a complicated Dingus
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 4th October 2020
 */

import java.awt.Graphics;

class StarDingus extends Dingus {
    protected int radius;
    protected boolean filled; //true: filled, false: outline
    private int h;
    private int[] xPoints = new int[10];
    private int[] yPoints = new int[10];

    public StarDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the StarDingus properties, i.e., radius and filledness
        h = random.nextInt(maxY/4);
        fillCordinateArray();
        filled = random.nextBoolean();
    }
    public void fillCordinateArray() {
        //This assigns points to the array xPoints and yPoints that will form the shape of a star
        //We calculate both points of a line, hence why we have have 10 indicies for a 5 pointed star
        //We use a constant angle for each tip of the traingle so they keep the same angle
        //And then we modify that angle for the points in between the tips of the triangle
        double angle = 36;
        xPoints[0] = (int) (x - 0.5*h*Math.tan(Math.toRadians(angle)));
        yPoints[0] = (int) (y - 0.5*h);
        
        xPoints[1] = (int) (x);
        yPoints[1] = (int) (y - 1.5*h);
        
        xPoints[2] = (int) (x + 0.5*h*Math.tan(Math.toRadians(angle)));
        yPoints[2] = (int) (y - 0.5*h);
        
        xPoints[3] = (int) (x + 1.5*h*Math.sin(Math.toRadians(angle*2)));
        yPoints[3] = (int) (y - 1.5*h*Math.cos(Math.toRadians(angle*2)));
        
        xPoints[4] = (int) (x + ((0.5*h)/Math.cos(Math.toRadians(angle)))*Math.cos(Math.toRadians(angle/2)));
        yPoints[4] = (int) (y + ((0.5*h)/Math.cos(Math.toRadians(angle)))*Math.sin(Math.toRadians(angle/2)));
        
        xPoints[5] = (int) (x + 1.5*h*Math.cos(Math.toRadians(angle + (angle/2))));
        yPoints[5] = (int) (y + 1.5*h*Math.sin(Math.toRadians(angle + (angle/2))));
        
        xPoints[6] = (int) x;
        yPoints[6] = (int) (y + (0.5*h)/Math.cos(Math.toRadians(angle)));
        
        xPoints[7] = (int) (x - 1.5*h*Math.cos(Math.toRadians(angle + (angle/2))));
        yPoints[7] = (int) (y + 1.5*h*Math.sin(Math.toRadians(angle + (angle/2))));
        
        xPoints[8] = (int) (x - ((0.5*h)/Math.cos(Math.toRadians(angle)))*Math.cos(Math.toRadians(angle/2)));
        yPoints[8] = (int) (y + ((0.5*h)/Math.cos(Math.toRadians(angle)))*Math.sin(Math.toRadians(angle/2)));
        
        xPoints[9] = (int) (x - 1.5*h*Math.sin(Math.toRadians(angle*2)));
        yPoints[9] = (int) (y - 1.5*h*Math.cos(Math.toRadians(angle*2)));

    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled) {
            g.fillPolygon(xPoints, yPoints, 10); //Draw 5 lines with 2 points each, hence 10 points
        } else {
            g.drawPolygon(xPoints, yPoints, 10); //Draw 5 lines with 2 points each, hence 10 points
        }
    }
}
