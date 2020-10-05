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
    private int middleX, middleY, h;
    private int[] xPoints = new int[10];
    private int[] yPoints = new int[10];

    public StarDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the CircleDingus properties, i.e., radius and filledness
        middleX = random.nextInt(maxX);
        middleY = random.nextInt(maxY);
        h = random.nextInt(maxY/4);
        
        createCoordinates();
        filled = random.nextBoolean();
    }
    public void createCoordinates() {
        //Uses a very similar technique to the heptagonDingus, we can make a star from triangles and circles
        
        xPoints[0] = (int) (middleX - 0.5*h*Math.tan(Math.toRadians(36)));
        yPoints[0] = (int) (middleY - 0.5*h);
        
        xPoints[1] = (int) (middleX);
        yPoints[1] = (int) (middleY - 1.5*h);
        
        xPoints[2] = (int) (middleX + 0.5*h*Math.tan(Math.toRadians(36)));
        yPoints[2] = (int) (middleY - 0.5*h);
        
        xPoints[3] = (int) (middleX + 1.5*h*Math.sin(Math.toRadians(72)));
        yPoints[3] = (int) (middleY - 1.5*h*Math.cos(Math.toRadians(72)));
        
        xPoints[4] = (int) (middleX + ((0.5*h)/Math.cos(Math.toRadians(36)))*Math.cos(Math.toRadians(18)));
        yPoints[4] = (int) (middleY + ((0.5*h)/Math.cos(Math.toRadians(36)))*Math.sin(Math.toRadians(18)));
        
        xPoints[5] = (int) (middleX + 1.5*h*Math.cos(Math.toRadians(54)));
        yPoints[5] = (int) (middleY + 1.5*h*Math.sin(Math.toRadians(54)));
        
        xPoints[6] = (int) middleX;
        yPoints[6] = (int) (middleY + (0.5*h)/Math.cos(Math.toRadians(36)));
        
        xPoints[7] = (int) (middleX - 1.5*h*Math.cos(Math.toRadians(54)));
        yPoints[7] = (int) (middleY + 1.5*h*Math.sin(Math.toRadians(54)));
        
        xPoints[8] = (int) (middleX - ((0.5*h)/Math.cos(Math.toRadians(36)))*Math.cos(Math.toRadians(18)));
        yPoints[8] = (int) (middleY + ((0.5*h)/Math.cos(Math.toRadians(36)))*Math.sin(Math.toRadians(18)));
        
        xPoints[9] = (int) (middleX - 1.5*h*Math.sin(Math.toRadians(72)));
        yPoints[9] = (int) (middleY - 1.5*h*Math.cos(Math.toRadians(72)));

    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled) {
            g.fillPolygon(xPoints, yPoints, 10);
        } else {
            g.drawPolygon(xPoints, yPoints, 10);
        }
    }
}
