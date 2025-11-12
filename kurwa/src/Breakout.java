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

        //StartMenu startMenu = new StartMenu();
        //startMenu.draw();
    }

    /** ============== APP CONFIGURATION ============== */
    private void configureApp() {
        setBackground(bgColor);
        setSize(Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        setSize(2*(Breakout.APPLICATION_WIDTH)-getWidth(), 2*(Breakout.APPLICATION_HEIGHT)-getHeight());
    }
}
