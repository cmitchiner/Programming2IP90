/**
 * CircleDingus -- part of HA RandomArtist
 * example of a very simple Dingus
 * @author huub
 */

import java.awt.Graphics;

class StarDingus extends Dingus {
    protected int radius;
    protected boolean filled; //true: filled, false: outline
    private int midX, midY, height;
    private int[] coordinateX = new int[10];
    private int[] coordinateY = new int[10];

    public StarDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the CircleDingus properties, i.e., radius and filledness
        midX = random.nextInt(maxX);
        midY = random.nextInt(maxY);
        height = random.nextInt(maxY/4);
        
        createCoordinates();
    }
    public void createCoordinates() {
        
        coordinateX[0] = (int) (midX - 0.5*height*Math.tan(Math.toRadians(36)));
        coordinateY[0] = (int) (midY - 0.5*height);
        
        coordinateX[1] = (int) (midX);
        coordinateY[1] = (int) (midY - 1.5*height);
        
        coordinateX[2] = (int) (midX + 0.5*height*Math.tan(Math.toRadians(36)));
        coordinateY[2] = (int) (midY - 0.5*height);
        
        coordinateX[3] = (int) (midX + 1.5*height*Math.sin(Math.toRadians(72)));
        coordinateY[3] = (int) (midY - 1.5*height*Math.cos(Math.toRadians(72)));
        
        coordinateX[4] = (int) (midX + ((0.5*height)/Math.cos(Math.toRadians(36)))*Math.cos(Math.toRadians(18)));
        coordinateY[4] = (int) (midY + ((0.5*height)/Math.cos(Math.toRadians(36)))*Math.sin(Math.toRadians(18)));
        
        coordinateX[5] = (int) (midX + 1.5*height*Math.cos(Math.toRadians(54)));
        coordinateY[5] = (int) (midY + 1.5*height*Math.sin(Math.toRadians(54)));
        
        coordinateX[6] = (int) midX;
        coordinateY[6] = (int) (midY + (0.5*height)/Math.cos(Math.toRadians(36)));
        
        coordinateX[7] = (int) (midX - 1.5*height*Math.cos(Math.toRadians(54)));
        coordinateY[7] = (int) (midY + 1.5*height*Math.sin(Math.toRadians(54)));
        
        coordinateX[8] = (int) (midX - ((0.5*height)/Math.cos(Math.toRadians(36)))*Math.cos(Math.toRadians(18)));
        coordinateY[8] = (int) (midY + ((0.5*height)/Math.cos(Math.toRadians(36)))*Math.sin(Math.toRadians(18)));
        
        coordinateX[9] = (int) (midX - 1.5*height*Math.sin(Math.toRadians(72)));
        coordinateY[9] = (int) (midY - 1.5*height*Math.cos(Math.toRadians(72)));

    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled) {
            g.fillPolygon(coordinateX, coordinateY, 10);
        } else {
            g.drawPolygon(coordinateX, coordinateY, 10);
        }
    }
}
