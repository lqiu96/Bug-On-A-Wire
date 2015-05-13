import javax.swing.*;
import java.awt.*;

public class Character extends JComponent {
    private Color color;
    private int locationX;
    private int locationY;
    private int line_number = 1;

    public Character() {
        color = new Color(0x00FF00);
    }

    public void draw(Graphics g, int x, int y) {
        locationX = x;
        locationY = y;
        g.setColor(color);
        g.fillOval(locationX / 5 * line_number - 15, locationY - 75, 30, 30);
    }

    public void moveLeft() {
        if (line_number == 1)
            return;
        line_number--;
    }

    public void moveRight() {
        if (line_number == 4)
            return;
        line_number++;
    }

    public int xCoordinate() {
        return locationX / 5 * line_number - 15;
    }

    public int yCoordinate() {
        return locationY - 75;
    }
}
