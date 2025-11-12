/*
 * File: Breakout.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Section Leader: Both
 *
 * This file is a main one in the game of Breakout.
 * Last update: 19:51
 */

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {
    /** ============== CONSTANTS ============== */
    //  Width and height of application window in pixels
    public static int APPLICATION_WIDTH = 500;
    public static int APPLICATION_HEIGHT = 700;
    public static int SETTING_PADDING = 30;
    private static int SETTING_WINDOW_WIDTH = 2*APPLICATION_WIDTH+ 3*SETTING_PADDING;
    private static int SETTING_WINDOW_HEIGHT = Breakout.APPLICATION_HEIGHT + 2*SETTING_PADDING;

    /** ============== CONSTANTS ============== */

    //  Dimensions of the paddle
    public static int PADDLE_WIDTH = 60;
    public static int PADDLE_HEIGHT = 10;

    //  Offset of the paddle up from the bottom
    public static int PADDLE_Y_OFFSET = 30;

    //  Number of bricks per row
    public static int NBRICKS_PER_ROW = 10;

    //  Number of rows of bricks
    public static int NBRICK_ROWS = 10;

    //  Separation between bricks
    public static int BRICK_SEP = 4;

    //  Width of a brick
    public static int BRICK_WIDTH =
            (Breakout.APPLICATION_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    //  Height of a brick
    public static int BRICK_HEIGHT = 8;

    //  Radius of the ball in pixels
    public static int BALL_RADIUS = 10;

    //  Offset of the top brick row from the top
    public static int BRICK_Y_OFFSET = 70;

    //  Number of turns
    public static int NTURNS = 3;

    /** ===== VARIABLES ====== */
    private boolean StartMenuEnabled = false;
    private int actionSlider = 0;

    GRect gameFramePreviewer;

    GPaddle paddlePreview;
    GSlider sliderPaddleWidth;
    GLabel paddleWidthValueLbl;

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
        addMouseListeners();
        configureAppMenu();

        //StartMenu startMenu = new StartMenu();
        //startMenu.draw();
    }

    /** ============== APP CONFIGURATION ============== */
    private void configureAppMenu() {
        setBackground(bgColor);
        setSize(SETTING_WINDOW_WIDTH, SETTING_WINDOW_HEIGHT);
        setSize(2*SETTING_WINDOW_WIDTH-getWidth(), 2*SETTING_WINDOW_HEIGHT-getHeight());
        StartMenuEnabled = true;
        header();
        paddleSettings();
        preview();
    }

    public void mousePressed(MouseEvent e) {
        if(StartMenuEnabled) {
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (e.getY() >= SETTING_WINDOW_HEIGHT * 0.22 - 20 && e.getY() <= SETTING_WINDOW_HEIGHT * 0.22 + 20) {
                    actionSlider = 1;
                    System.out.println(1);
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
        actionSlider = 0;
        System.out.println(0);
    }

    public void mouseDragged(MouseEvent e) {
        if(StartMenuEnabled) {
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 1) {
                    sliderPaddleWidth.sliderMove(e.getX(), 7);
                    PADDLE_WIDTH = (e.getX() - SETTING_PADDING) / 2 + 10;
                    paddleWidthValueLbl.setLabel(PADDLE_WIDTH + "");
                    paddleWidthValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - paddleWidthValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.2);

                    remove(paddlePreview);
                    paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
                    add(paddlePreview, gameFramePreviewer.getX() + Breakout.APPLICATION_WIDTH / 2.0, gameFramePreviewer.getY() + Breakout.APPLICATION_HEIGHT - PADDLE_Y_OFFSET);
                }
            }
        }
    }










    /** ===== HEADER ===== */
    private void header(){
        GImage image = new GImage("images/gameSettings.png");
        double imageWidth = APPLICATION_WIDTH *0.8;
        image.setSize(imageWidth, imageWidth*0.1);
        image.setLocation((APPLICATION_WIDTH -imageWidth)/2, SETTING_WINDOW_HEIGHT *0.05);
        add(image);
    }

    /** ===== HEADER ===== */
    private void paddleSettings(){
        GLabel paddleWidthLbl = new GLabel("Paddle width");
        paddleWidthLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddleWidthLbl.setColor(Breakout.fontColor);
        paddleWidthLbl.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.2);
        add(paddleWidthLbl);

        paddleWidthValueLbl = new GLabel(PADDLE_WIDTH+"");
        paddleWidthValueLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddleWidthValueLbl.setColor(Breakout.fontColor);
        paddleWidthValueLbl.setLocation(SETTING_PADDING+APPLICATION_WIDTH-paddleWidthValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.2);
        add(paddleWidthValueLbl);

        sliderPaddleWidth = new GSlider(APPLICATION_WIDTH, 3, 14, PADDLE_WIDTH);
        sliderPaddleWidth.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.22);
        add(sliderPaddleWidth);
    }






    /** ===== PREVIEW ===== */
    private void preview(){
        gameFramePreviewer = new GRect(APPLICATION_WIDTH + 2*SETTING_PADDING, SETTING_PADDING, Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        gameFramePreviewer.setFilled(true);
        gameFramePreviewer.setFillColor(Breakout.bgColor);
        add(gameFramePreviewer);

        paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        add(paddlePreview, gameFramePreviewer.getX()+Breakout.APPLICATION_WIDTH/2.0, gameFramePreviewer.getY()+Breakout.APPLICATION_HEIGHT-PADDLE_Y_OFFSET);
    }
}
