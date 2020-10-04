import java.awt.Graphics;
import java.util.ArrayList;

public class PolygonDingus extends Dingus {
    protected boolean filled; //true: filled, false: outline
    ArrayList<Integer> xPoints = new ArrayList<>();
    ArrayList<Integer> yPoints = new ArrayList<>();
    int numPoints = Math.abs(random.nextInt(50));


    public PolygonDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the PolygonDingus properties, i.e., radius and filledness
        for (int i = 1; i < numPoints; i++){
            xPoints.add(random.nextInt(maxX/2));
            yPoints.add(random.nextInt(maxX/3));
        }
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        int[] xPoint = new int[numPoints];
        int[] yPoint = new int[numPoints];
        for (int n = 0; n < xPoint.length; n++){
            xPoint[n] = xPoints.get(n).intValue();
        }
        for (int t = 0; t < yPoint.length; t++){
            yPoint[t] = yPoints.get(t).intValue();
        }
        g.setColor(color);
        if (filled)
            g.fillPolygon(xPoint, yPoint, numPoints);
        else
            g.drawPolygon(xPoint, yPoint, numPoints);
    }
}