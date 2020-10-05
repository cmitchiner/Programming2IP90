/**
 * PolygonDingus -- part of HA RandomArtist
 * example of a somewhat complex Dingus
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 4th October 2020
 */
import java.awt.Graphics;

public class PolygonDingus extends Dingus {
    protected boolean filled; //true: filled, false: outline
    
    int numPoints = Math.abs(random.nextInt(15));
    int[] xPoint = new int[numPoints];
    int[] yPoint = new int[numPoints];


    public PolygonDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the PolygonDingus properties, i.e., radius and filledness
        for (int i = 0; i < numPoints; i++){
            xPoint[i] = (random.nextInt(maxX));
            yPoint[i] = (random.nextInt(maxY));
        }
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled)
            g.fillPolygon(xPoint, yPoint, numPoints);
            
        else
            g.drawPolygon(xPoint, yPoint, numPoints);
    }
}