import java.awt.Graphics;

public class RectangleDingus extends Dingus {
    protected int length, width;
    protected boolean filled; //true: filled, false: outline

    public RectangleDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the RectangleDingus properties, i.e., radius and filledness
        length = random.nextInt(maxX/2);
        width = random.nextInt(maxY/3);
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled)
            g.fillRect(x, y, length, width);
        else
            g.drawRect(x, y, length, width);
    }
}