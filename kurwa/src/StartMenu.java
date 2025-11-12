/*
 * File: StatMenu.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Section Leader: Mykhailo Pulov
 *
 * This file is a menu that loads on start to set CONST in the game.
 * Last update: 15:09
 */

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;

public class StartMenu extends GraphicsProgram {
    // StartMenu(){}
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
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    //  Height of a brick
    public static int BRICK_HEIGHT = 8;

    //  Radius of the ball in pixels
    public static int BALL_RADIUS = 10;

    //  Offset of the top brick row from the top
    public static int BRICK_Y_OFFSET = 70;

    //  Number of turns
    public static int NTURNS = 3;


    /** ===== LOCAL CONSTANTS ====== */
    private int SETTING_WIDTH = Breakout.APPLICATION_WIDTH;
    private static int SETTING_PADDING = 30;

    private int SETTING_WINDOW_WIDTH = Breakout.APPLICATION_WIDTH + SETTING_WIDTH + 3*SETTING_PADDING;
    private int SETTING_WINDOW_HEIGHT = Breakout.APPLICATION_HEIGHT + 2*SETTING_PADDING;

    /** ===== LOCAL VARIABLES ====== */
    private int actionSlider = 0;

    GSlider sliderPaddleWidth;
    GLabel paddleValueLbl;


    // drawing the menu
    public void run(){
        setBackground(Breakout.settingsColor);
        setSize(SETTING_WINDOW_WIDTH, SETTING_WINDOW_HEIGHT);
        setSize(2*SETTING_WINDOW_WIDTH-getWidth(), 2*SETTING_WINDOW_HEIGHT-getHeight());

        header();
        paddleSettings();

        addMouseListeners();
        preview();
    }

    public void mousePressed(MouseEvent e) {
        if (e.getX()>=SETTING_PADDING && e.getX()<=SETTING_PADDING+SETTING_WIDTH) {
            if (e.getY()>=SETTING_WINDOW_HEIGHT*0.22-20 && e.getY()<=SETTING_WINDOW_HEIGHT*0.22+20) {
                actionSlider = 1;
                System.out.println(1);
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
        actionSlider = 0;
        System.out.println(0);
    }

    public void mouseDragged(MouseEvent e) {
        if (e.getX()>=SETTING_PADDING && e.getX()<=SETTING_PADDING+SETTING_WIDTH) {
            if (actionSlider == 1) {
                sliderPaddleWidth.sliderMove(e.getX(), 7);
                PADDLE_WIDTH = (e.getX()-SETTING_PADDING)/2+10;
                paddleValueLbl.setLabel(PADDLE_WIDTH+"");
                paddleValueLbl.setLocation(SETTING_PADDING+SETTING_WIDTH-paddleValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.2);
            }
        }
    }










    /** ===== HEADER ===== */
    private void header(){
        GImage image = new GImage("images/gameSettings.png");
        double imageWidth = SETTING_WIDTH *0.8;
        image.setSize(imageWidth, imageWidth*0.1);
        image.setLocation((SETTING_WIDTH -imageWidth)/2, SETTING_WINDOW_HEIGHT *0.05);
        add(image);
    }

    /** ===== HEADER ===== */
    private void paddleSettings(){
        GLabel paddleWidthLbl = new GLabel("Paddle width");
        paddleWidthLbl.setFont("Monospased-"+(int) Math.round(SETTING_WIDTH*0.05));
        paddleWidthLbl.setColor(Breakout.fontColor);
        paddleWidthLbl.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.2);
        add(paddleWidthLbl);

        paddleValueLbl = new GLabel("0");
        paddleValueLbl.setFont("Monospased-"+(int) Math.round(SETTING_WIDTH*0.05));
        paddleValueLbl.setColor(Breakout.fontColor);
        paddleValueLbl.setLocation(SETTING_PADDING+SETTING_WIDTH-paddleValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.2);
        add(paddleValueLbl);

        sliderPaddleWidth = new GSlider(SETTING_WIDTH, 3, 14);
        sliderPaddleWidth.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.22);
        add(sliderPaddleWidth);
    }






    /** ===== PREVIEV ===== */
    private void preview(){
        GRect gameFramePreviewer = new GRect(SETTING_WIDTH + 2*SETTING_PADDING, SETTING_PADDING, Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        gameFramePreviewer.setFilled(true);
        gameFramePreviewer.setFillColor(Breakout.bgColor);
        add(gameFramePreviewer);

    }



    /** ====== GETTERS AND SETTERS ====== */
    public static int getSETTING_PADDING() {
        return SETTING_PADDING;
    }

    public static int getPaddleWidth() {
        return PADDLE_WIDTH;
    }

    public static int getPaddleHeight() {
        return PADDLE_HEIGHT;
    }

    public static int getPaddleYOffset() {
        return PADDLE_Y_OFFSET;
    }

    public static int getNbricksPerRow() {
        return NBRICKS_PER_ROW;
    }

    public static int getNbrickRows() {
        return NBRICK_ROWS;
    }

    public static int getBrickSep() {
        return BRICK_SEP;
    }

    public static int getBrickWidth() {
        return BRICK_WIDTH;
    }

    public static int getBrickHeight() {
        return BRICK_HEIGHT;
    }

    public static int getBallRadius() {
        return BALL_RADIUS;
    }

    public static int getBrickYOffset() {
        return BRICK_Y_OFFSET;
    }

    public static int getNTURNS() {
        return NTURNS;
    }
}
