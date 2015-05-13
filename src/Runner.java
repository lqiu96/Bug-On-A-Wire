import javax.swing.*;
import java.awt.*;

public class Runner {
    public static void main(String[] args) {
        JFrame f = new JFrame("Bug on a wire");
        Toolkit tk = f.getToolkit();
        Dimension size = tk.getScreenSize();
        f.setBounds(size.width / 4, 0, size.width / 2, size.height - 100);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container pane = f.getContentPane();
        pane.setLayout(new BorderLayout());
        Panel p = new Panel();
        pane.add(p, BorderLayout.CENTER);
        pane.setPreferredSize(new Dimension(500, 500));
        f.setResizable(false);
        f.setVisible(true);
    }
}
