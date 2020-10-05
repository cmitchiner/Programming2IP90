/**
 * HeptagonDingus -- part of HA RandomArtist
 * example of a complicated Dingus
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 4th October 2020
 */
import java.awt.Graphics;

public class HeptagonDingus extends Dingus {
    protected boolean filled; //true: filled, false: outline
    
    int[] xPoints = new int[7];
    int[] yPoints = new int[7];
    int beginX;
    int beginY;
    int radius;


    public HeptagonDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the PolygonDingus properties, i.e., radius and filledness
        beginX = random.nextInt(maxX);
        beginY = random.nextInt(maxY);
        radius = random.nextInt(maxX/4);
        filled = random.nextBoolean();
        //We basically use Trigonometry and Calc in order to divide Circles into heptagon, this
        //same method could be changed for any sided polygon with equal sides

        xPoints[0] = beginX;
        yPoints[0] = beginY - radius;
        
        xPoints[1] = (int) (beginX + radius*Math.cos(Math.toRadians(360/7)));
        yPoints[1] = (int) (beginY - radius*Math.sin(Math.toRadians(360/7)));
        
        xPoints[2] = (int) (beginX + radius*Math.cos(Math.toRadians((360/7)*2-90)));
        yPoints[2] = (int) (beginY + radius*Math.sin(Math.toRadians((360/7)*2-90)));
        
        xPoints[3] = (int) (beginX + radius*Math.cos(Math.toRadians((360/7)*3-90)));
        yPoints[3] = (int) (beginY + radius*Math.sin(Math.toRadians((360/7)*3-90)));
        
        xPoints[4] = (int) (beginX - radius*Math.cos(Math.toRadians((360/7)*3-90)));
        yPoints[4] = (int) (beginY + radius*Math.sin(Math.toRadians((360/7)*3-90)));
        
        xPoints[5] = (int) (beginX - radius*Math.cos(Math.toRadians((360/7)*2-90)));
        yPoints[5] = (int) (beginY + radius*Math.sin(Math.toRadians((360/7)*2-90)));
        
        xPoints[6] = (int) (beginX - radius*Math.cos(Math.toRadians((360/7))));
        yPoints[6] = (int) (beginY - radius*Math.sin(Math.toRadians((360/7))));
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled)
            g.fillPolygon(xPoints, yPoints, 7);
        else
            g.drawPolygon(xPoints, yPoints, 7);
    }
}