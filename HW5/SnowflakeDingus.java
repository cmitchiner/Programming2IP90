/**
 * SnowflakeDingus -- part of HA RandomArtist
 * example of a complicated Dingus
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 4th October 2020
 */

import java.awt.Graphics;

public class SnowflakeDingus extends Dingus {
    protected int length, width;
    protected boolean filled;
    final int LEVEL = 5;
    int x1 = random.nextInt(maxX);
    int x5 = random.nextInt(maxX);
    int y1 = random.nextInt(maxY);
    int y5 = random.nextInt(maxY);    

    public SnowflakeDingus(int maxX, int maxY){
        super(maxX, maxY);
    }

    private void koch(Graphics g, int LEVEL, int x1, int y1, int x5, int y5){
        int dX, dY, x2, y2, x3, y3, x4, y4;
        if (LEVEL == 0){
 
            g.drawLine(x1, y1, x5, y5);
        }
        else{
              dX = x5 - x1;
              dY = y5 - y1;

              x2 = x1 + dX / 3;
              y2 = y1 + dY / 3;

              x3 = (int) (0.5 * (x1+x5) + Math.sqrt(3) * (y1-y5)/6);
              y3 = (int) (0.5 * (y1+y5) + Math.sqrt(3) * (x5-x1)/6);

              x4 = x1 + 2 * dX /3;
              y4 = y1 + 2 * dY /3;

              koch(g,LEVEL-1, x1, y1, x2, y2);
              koch(g,LEVEL-1, x2, y2, x3, y3);
              koch(g,LEVEL-1, x3, y3, x4, y4);
              koch(g,LEVEL-1, x4, y4, x5, y5);
          }
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        koch(g, LEVEL, x1, y1, x5, y5);
        koch(g, LEVEL, x1+260, y1, x5-130, y5-260);
        koch(g, LEVEL, x1+130, y1 - 260, x5-260, y5);
    }
}