/*
 * File: Breakout.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Section Leader: Both
 *
 * This file is a main one in the game of Breakout.
 * Last update: 15:42
 */

import acm.program.*;

import java.awt.*;

public class Breakout extends GraphicsProgram {
    /** ============== CONSTANTS ============== */
    //  Width and height of application window in pixels
    public static int APPLICATION_WIDTH = 500;
    public static int APPLICATION_HEIGHT = 700;

    public int PADDLE_WIDTH = 60;
    public int PADDLE_HEIGHT = 10;

    //  Offset of the paddle up from the bottom
    public int PADDLE_Y_OFFSET = 30;

    //  Number of bricks per row
    public int NBRICKS_PER_ROW = 10;

    //  Number of rows of bricks
    public int NBRICK_ROWS = 10;

    //  Separation between bricks
    public int BRICK_SEP = 4;

    //  Width of a brick
    public int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    //  Height of a brick
    public int BRICK_HEIGHT = 8;

    //  Radius of the ball in pixels
    public int BALL_RADIUS = 10;

    //  Offset of the top brick row from the top
    public int BRICK_Y_OFFSET = 70;

    //  Number of turns
    public int NTURNS = 3;

    /** ============== COLOR PALETTE ============== */
    public static Color bgColor = new Color(37, 51, 61);
    public static Color settingsColor = new Color(59, 82, 97);
    public static Color fontColor = new Color(254, 239, 207);
    public static Color sliderColor = new Color(92, 125, 149);
    public static Color sliderBallColor = new Color(142, 167, 188);
    public static Color paddleColor = new Color(172, 102, 86);
    public static Color ballColor = new Color(250, 233, 204);




    /** ============== RUN ============== */
    public void run() {
        configureApp();
        getAllConsts();

        //StartMenu startMenu = new StartMenu();
        //startMenu.draw();
    }

    /** ============== APP CONFIGURATION ============== */
    private void configureApp() {
        setBackground(bgColor);
        setSize(Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        setSize(2*(Breakout.APPLICATION_WIDTH)-getWidth(), 2*(Breakout.APPLICATION_HEIGHT)-getHeight());
    }

    /** ============== GETTING CONSTANTS FROM StartMenu ============== */
    private void getAllConsts() {
        //
    }
}
