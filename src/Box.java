import javax.swing.*;
import java.awt.*;

public class Box extends JComponent {
    private Color color;
    private int locationX;
    public int locationY;
    public static final int initial_speed = 100000;
    public static int speed = initial_speed;
    public boolean isGood = false;
    public boolean isBlue = false;

    public Box(int x) {
        locationX = x;
        locationY = 0;
        color = new Color(0x00FF00);
        if (Math.random() < 0.1) {
            isGood = true;
            color = Color.PINK;
        }
        if (Math.random() < 0.5) {
            isGood = true;
            isBlue = true;
            color = Color.BLUE;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(locationX, locationY, 30, 30);
        if (isGood)
            g.fillRect(locationX, locationY, 30, 30);
    }

    public void move() {
        locationY += speed++ / 10000;
    }

    public int xB() {
        return locationX;
    }

    public int yB() {
        return locationY;
    }
}
