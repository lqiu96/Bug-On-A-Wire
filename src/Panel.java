import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Panel extends JPanel implements KeyListener {
    private ArrayList<Box> one, two, three, four;
    private javax.swing.Timer timer, timer2;
    private Line fpl;
    private Character fpc;
    private int score;
    private boolean again;

    public Panel() {
        setLayout(new BorderLayout());
        one = new ArrayList<Box>();
        two = new ArrayList<Box>();
        three = new ArrayList<Box>();
        four = new ArrayList<Box>();

        timer = new javax.swing.Timer(750, new TimerListener());
        timer2 = new javax.swing.Timer(16, new TimerListener2());

        setBackground(Color.BLACK);
        add(fpl = new Line());
        add(fpc = new Character());

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout());
        add(bottom, BorderLayout.SOUTH);

        JButton start = new JButton("Start");
        start.setMnemonic(KeyEvent.VK_S);
        start.addActionListener(new HandleStartButton());

        JButton stop = new JButton("Stop");
        stop.setMnemonic(KeyEvent.VK_T);
        stop.addActionListener(new HandleStopButton());

        JButton restart = new JButton("Restart");
        restart.setMnemonic(KeyEvent.VK_R);
        restart.addActionListener(new HandleRestartButton());

        JButton instructions = new JButton("Instructions");
        instructions.setMnemonic(KeyEvent.VK_I);
        instructions.addActionListener(new InstructionPane());

        JButton highScores = new JButton("High Scores");
        highScores.setMnemonic(KeyEvent.VK_H);
        highScores.addActionListener(new HighScorePane());

        bottom.add(start);
        bottom.add(stop);
        bottom.add(restart);
        bottom.add(instructions);
        bottom.add(highScores);

        again = false;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        fpl.draw(g, getWidth(), getHeight());
        fpc.draw(g, getWidth(), getHeight());
        for (Box fpb : one) {
            fpb.draw(g);
        }
        for (Box fpb2 : two) {
            fpb2.draw(g);
        }
        for (Box fpb3 : three) {
            fpb3.draw(g);
        }
        for (Box fpb4 : four) {
            fpb4.draw(g);
        }
        g.setColor(Color.GREEN);
        g.drawString("Score: " + score, 5, 15);
    }

    private class HandleStartButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timer.start();
            timer2.start();
            if (again) {
                score = 0;
                Box.speed = Box.initial_speed;
            }
        }
    }

    private class HandleStopButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            timer2.stop();
            Box.speed = Box.initial_speed;
        }
    }

    private class HandleRestartButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            timer2.stop();
            int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to restart?", "Restart?", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                score = 0;
                one.clear();
                two.clear();
                three.clear();
                four.clear();
                Box.speed = Box.initial_speed;
                repaint();
                timer.start();
                timer2.start();
                again = true;
            } else
                System.exit(1);
        }
    }

    private class InstructionPane implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            timer2.stop();
            JOptionPane.showMessageDialog(null, "Avoid green boxes\nBlue boxes increase speed\nPink boxes increase score",
                    "Instructions", JOptionPane.INFORMATION_MESSAGE);
            timer.start();
            timer2.start();
        }
    }

    private class HighScorePane implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> highestScores = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader("Highscores.txt"));
                String score;
                while ((score = br.readLine()) != null) {
                    highestScores.add(score);
                }
                for (int i = 0; i < highestScores.size() - 1; i++) {
                    for (int t = i + 1; t < highestScores.size(); t++) {
                        int s = highestScores.get(i).indexOf(" ");
                        int num1 = Integer.parseInt(highestScores.get(i).substring(0, s));
                        s = highestScores.get(t).indexOf(" ");
                        int num2 = Integer.parseInt(highestScores.get(t).substring(0, s));
                        if (num1 < num2) {
                            String temp = highestScores.get(i);
                            highestScores.set(i, highestScores.get(t));
                            highestScores.set(t, temp);
                        }
                    }
                }
                String highScores = "";
                for (int i = 0; i < ((highestScores.size() >= 10) ? 10 : highestScores.size()); i++) {
                    highScores += (i + 1) + ". " + highestScores.get(i) + "\n";
                }
                Object[] options = {"Ok", "Clear High Scores"};
                int n = JOptionPane.showOptionDialog(null, highScores, "Highscores", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (n == 1) {
                    PrintWriter pw;
                    try {
                        pw = new PrintWriter("Highscores.txt");
                        pw.write("");
                        pw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            score += 1000;
            int[] q = {0, 1, 2, 3};
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    if (i == j || Math.random() > 0.5)
                        continue;
                    q[i] ^= q[j];
                    q[j] ^= q[i];
                    q[i] ^= q[j];
                }
            }
            int num = (int) (Math.random() * 4);
            for (int i = 0; i < num; ++i) {
                if (q[i] == 0)
                    one.add(new Box(getWidth() / 5 - 16));
                else if (q[i] == 1)
                    two.add(new Box(getWidth() / 5 * 2 - 15));
                else if (q[i] == 2)
                    three.add(new Box(getWidth() / 5 * 3 - 14));
                else if (q[i] == 3)
                    four.add(new Box(getWidth() / 5 * 4 - 14));
            }
            timer.setDelay(750 / (Box.speed / 70000));
        }
    }

    public boolean collides(ArrayList<Box> boxes) {
        for (Box number : boxes) {
            if (Math.abs(number.xB() - fpc.xCoordinate()) < 30
                    && (number.yB() + 30 >= fpc.yCoordinate() + 15 && number.yB() < fpc.yCoordinate() + 30))
                if (number.isBlue) {
                    Box.speed *= 1.1;
                    number.locationY = 9999999;
                    score *= 1.1;
                    return false;
                } else {
                    if (number.isGood) {
                        number.locationY = 9999999;
                        score += 5000;
                        return false;
                    } else {
                        return true;
                    }
                }
        }
        return false;
    }

    class TimerListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Box anOne : one) {
                anOne.move();
            }
            for (Box aTwo : two) {
                aTwo.move();
            }
            for (Box aThree : three) {
                aThree.move();
            }
            for (Box aFour : four) {
                aFour.move();
            }
            boolean dead = false;
            dead |= collides(one);
            dead |= collides(two);
            dead |= collides(three);
            dead |= collides(four);
            if (dead) {
                timer2.stop();
                timer.stop();
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    BufferedWriter bw = new BufferedWriter(new FileWriter("Highscores.txt", true));
                    bw.write(score + " (" + dateFormat.format(cal.getTime()) + ")");
                    bw.newLine();
                    bw.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
                return;
            }
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            fpc.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            fpc.moveRight();
        }
    }

    public void keyTyped(KeyEvent e) { }
}