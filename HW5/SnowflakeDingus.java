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
    protected int length, width, xInit;
    protected boolean filled;
    final int LEVEL = 5;
    int x1, x5, y1, y5;  

    public SnowflakeDingus(int maxX, int maxY){
        super(maxX, maxY);
        length = random.nextInt(maxY);
        length = length - length/4;
        width = random.nextInt(maxX);
        xInit = width - length;
        x1 = random.nextInt(maxX);
        x5 = random.nextInt(maxX);
        y1 = random.nextInt(maxY);
        y5 = random.nextInt(maxY);    
        
    }
    // Recursive method to draw the Koch snowflake, which iterates 5 times
    private void snowflakeHelper(Graphics g, int LEVEL, int x1, int y1, int x5, int y5) {
        int difX, difY, x2, y2, x3, y3, x4, y4;
        if (LEVEL == 0){
 
            g.drawLine(x1, y1, x5, y5);
        }
        else{
              difX = x5 - x1;
              difY = y5 - y1;

              x2 = x1 + difX / 3;
              y2 = y1 + difY / 3;

              x3 = (int) (0.5 * (x1+x5) + Math.sqrt(3) * (y1-y5)/6);
              y3 = (int) (0.5 * (y1+y5) + Math.sqrt(3) * (x5-x1)/6);

              x4 = x1 + 2 * difX /3;
              y4 = y1 + 2 * difY /3;

              snowflakeHelper(g,LEVEL-1, x1, y1, x2, y2);
              snowflakeHelper(g,LEVEL-1, x2, y2, x3, y3);
              snowflakeHelper(g,LEVEL-1, x3, y3, x4, y4);
              snowflakeHelper(g,LEVEL-1, x4, y4, x5, y5);
          }
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        snowflakeHelper(g, LEVEL, xInit + 20, length - 20, xInit + length - 20, length - 20);
        snowflakeHelper(g, LEVEL, xInit + length - 20, length - 20, xInit + length/2, 20);
        snowflakeHelper(g, LEVEL, xInit + length/2, 20, xInit + 20, length - 20);
    }
}