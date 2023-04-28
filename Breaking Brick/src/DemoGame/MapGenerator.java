package DemoGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    public int map[][];  //that will contain all the bricks
    public int brickWidth;
    public int brickHeight;

    // this creates the brick of size 3X7
    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = 1;   // Value set 1 for show all bricks in the panel.
            }
        }

        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    // this sets the value of brick to 0 if it is hit by the ball
    public void setBrick(int value, int r, int c) {
        map[r][c] = value;
    }
    // this draws the brick
    public void draw(Graphics2D g) {

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {

                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);   //(X position, Y position, Height, Width)

                    g.setColor(Color.black);   // to border the bricks
                    g.setStroke(new BasicStroke(3));  // to differenciate the bricks
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }
}