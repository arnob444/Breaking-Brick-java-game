package DemoGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;


public class GamePlay extends JPanel implements ActionListener, KeyListener {   //JPanel is a container that can hold other Swing components, such as buttons, labels, and text fields. It is often used to group related components together and to organize the layout of a larger GUI.
    // KeyListener for detecting the arrow keys & ActionListener for moving the ball.
    private boolean play = false;
    private int score = 0;
    private int totalBrick = 21;
    private Timer timer;            //time of ball that how fast it should move so timer
    private int delay = 8;          //delay could be used to specify the amount of time (in milliseconds) that should elapse between each update or animation frame
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int playerX = 320;     // position for paddle.this is the starting position of our paddle.
    private MapGenerator map;

    public GamePlay() {
        addKeyListener(this);              //used to register a KeyListener with a Java graphical user interface (GUI) component,such as a text field or a button.
        setFocusable(true);                  //When it is the currently active component that is capable of receiving user input, such as mouse clicks or keyboard events.
        setFocusTraversalKeysEnabled(true);  //used to control whether focus traversal keys are enabled or disabled in a GUI component and can be used to customize the behavior of focus navigation in a Java Swing application.

        timer = new Timer(delay, this); //A Timer object is used to schedule tasks to be executed at a specific time or after a specific delay.
        timer.start();

        map = new MapGenerator(3, 7);
    }

    public void paint(Graphics g) {   //inbuilt method  for draw all things
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //border
        g.setColor(Color.yellow);
//        g.fillRect(0, 0, 692, 3);              //Top
//        g.fillRect(0, 3, 3, 592);              //Left (left - top - width - height)
//        g.fillRect(691, 3, 3, 592);            //Right
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 3, 3, 592);

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //bricks
        map.draw((Graphics2D) g);

        //ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);

        //score
        g.setColor(Color.green);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Score : " + score, 550, 30);


        //game-over
        if (ballposY >= 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GameOver!! Score : " + score, 200, 300);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to Restart!!", 230, 350);

        }

        if (totalBrick <= 0) {

            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won!!, Score : " + score, 200, 300);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to Restart!!", 230, 350);
        }
    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    private void moveRight() {
        play = true;
        playerX += 20; //it should move 20pixel to the right side if I pressed right
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 0)
                playerX = 0;
            else
                moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600)   // We need to check it doesn't go outside the panel.
                playerX = 600;
            else
                moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                score = 0;
                totalBrick = 21;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 320;

                map = new MapGenerator(3, 7);

            }
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        if (play) {

            if (ballposX <= 0) {             //if ball hits the left wall then it bounces back
                ballXdir = -ballXdir;
            }
            if (ballposX >= 670) {           //if ball hits the right wall then it bounces back
                ballXdir = -ballXdir;
            }
            if (ballposY <= 0) {            //if ball hits the top wall then it bounces back
                ballYdir = -ballYdir;
            }

            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
            // for detecting the intersection with the paddle so for detecting it we need to create a rectangle around that ball because for detecting the intersection of two objects we can do it with the rectangle but but we have Oval for ball for this reason we create rectangle around oval.
            Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);

            if (ballRect.intersects(paddleRect)) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {         // 1st map is 28 no line (private MapGenerator map)    2nd map is (public int map[][])
                for (int j = 0; j < map.map[i].length; j++) {   //map.map[0].lenght is the number of columns
                    if (map.map[i][j] > 0) {        //then its detect the intersection

                        int width = map.brickWidth;
                        int height = map.brickHeight;
                        int brickXpos = 80 + j * width;
                        int brickYpos = 50 + i * height;

                        Rectangle brickRect = new Rectangle(brickXpos, brickYpos, width, height);

                        if (ballRect.intersects(brickRect)) {

                            map.setBrick(0, i, j);
                            totalBrick--;
                            score += 5;

                            if (ballposX + 19 <= brickXpos || ballposX + 1 >= brickXpos + width) {                     // (left + right)
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A; //Level
                        }
                    }
                }
            }

            ballposX += ballXdir;  // We need to detect if the ball is touching the top left or right or not so for that We can write this condition
            ballposY += ballYdir;

        }

        repaint(); // recall the paint method and draw everything again. because when We are incrementing the value of moveright() playerX & decreamenting the value of playerX there is no change shown because this paddle doesn't redraw again so i need to redraw this paddle.
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

}
//The KeyListener interface provides three methods that allow the program to respond to keyboard events: keyPressed(), keyReleased(), and keyTyped(). By registering a KeyListener with a GUI component, the program can detect when the user presses, releases, or types a key on the keyboard and respond ACCORDINGLY
// KeyListener for detecting the arrow keys & ActionListener for moving the ball.