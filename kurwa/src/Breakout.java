/*
 * File: Breakout.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Class Leader: Both
 *
 * This file is a main one in the game of Breakout.
 * Last update: 10:30 | 14.11.2025
 */

import acm.graphics.*;
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
    private static int SETTING_WINDOW_HEIGHT = APPLICATION_HEIGHT + 2*SETTING_PADDING;

    /** ============== CONSTANTS ============== */

    //  Dimensions of the paddle
    public static int PADDLE_WIDTH = 60;
    public static int PADDLE_HEIGHT = 10;

    //  Offset of the paddle up from the bottom
    public static int PADDLE_PADDING = 10;

    //  Number of bricks per row
    public static int NBRICKS_PER_ROW = 10;

    //  Number of rows of bricks
    public static int NBRICK_ROWS = 5;

    //  Separation between bricks
    public static double BRICK_SEP = 10;

    //  Width of a brick
    public static double BRICK_WIDTH = (APPLICATION_WIDTH - (NBRICKS_PER_ROW+1) * BRICK_SEP) / NBRICKS_PER_ROW;

    //  Height of a brick
    public static double BRICK_HEIGHT = 15;

    //  Radius of the ball in pixels
    public static int BALL_RADIUS = 10;

    //  Speed of the ball X axis
    public static int BALL_SPEED_X = 3;

    //  Speed of the ball Y axis
    public static int BALL_SPEED_Y = -3;

    //  Paused delay for the ball in milliseconds
    public static int BALL_PAUSE = 5;

    //  Offset of the top brick row from the top
    public static double BRICK_Y_OFFSET = (int) Math.round(BRICK_SEP);

    //  Number of turns
    public static int NTURNS = 3;

    /** ===== VARIABLES ====== */
    private boolean StartMenuEnabled = false;
    private boolean waitingContinue = false;
    private boolean isGameStarted = false;


    private int actionSlider = 0;

    GRect gameFramePreviewer;

    GPaddle paddlePreview;
    GPaddle gamePaddle;
    GOval ball;
    GSlider sliderPaddleWidth;
    GLabel paddleWidthValueLbl;
    GSlider sliderPaddlePadding;
    GLabel paddlePaddingValueLbl;


    GBricksPreview bricksPreview;
    GSlider sliderBricksColumns;
    GLabel bricksColumnsValueLbl;
    GSlider sliderBricksRows;
    GLabel bricksRowsValueLbl;
    GSlider sliderBricksPadding;
    GLabel bricksPaddingValueLbl;

    /** ============== COLOR PALETTE ============== */
    public static Color bgColor = new Color(37, 51, 61);
    public static Color settingsColor = new Color(59, 82, 97);
    public static Color fontColor = new Color(254, 239, 207);
    public static Color sliderColor = new Color(92, 125, 149);
    public static Color sliderBallColor = new Color(142, 167, 188);
    public static Color paddleColor = new Color(172, 102, 86);
    public static Color ballColor = new Color(250, 233, 204);

    public static Color brickOneColor = new Color(214, 155, 132);
    public static Color brickTwoColor = new Color(214, 178, 132);
    public static Color brickThreeColor = new Color(203, 214, 132);
    public static Color brickFourColor = new Color(132, 214, 146);
    public static Color brickFiveColor = new Color(132, 214, 195);
    public static Color brickSixColor = new Color(132, 195, 214);
    public static Color brickSevenColor = new Color(132, 139, 214);
    public static Color brickEightColor = new Color(147, 132, 214);
    public static Color brickNineColor = new Color(214, 132, 213);
    public static Color brickTenColor = new Color(214, 132, 157);




    /** ============== RUN ============== */
    public void run() {
        addMouseListeners();
        configureAppMenu();
        waitForContinue();

        configureApp();
        waitForClick();
        pause(110);
        playGame();
    }
    // play the main game
    private void playGame() {
        isGameStarted = true;
        while (isGameStarted){
            ball.move(BALL_SPEED_X, BALL_SPEED_Y);
            pause(BALL_PAUSE);
            checkBallSensors();
            if (ball.getX() + 2*BALL_RADIUS >= getWidth() || ball.getX() <= 0) {
                BALL_SPEED_X = -BALL_SPEED_X;
            }
            else if (ball.getY() <= 0) {
                BALL_SPEED_Y = -BALL_SPEED_Y;
            }

            else if (ball.getY() >= getHeight() - PADDLE_PADDING - PADDLE_HEIGHT) {
                isGameStarted = false;
                //stop the game
            }

        }


    }

    private Object checkBallSensors() {
        //not finished, I wanna sleep
        return null;
    }

    /** ============== APP CONFIGURATION ============== */
    // main game
    private void configureApp() {
        removeAll();
        setBackground(bgColor);
        setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        setSize(2*APPLICATION_WIDTH-getWidth(), 2*APPLICATION_HEIGHT-getHeight());
        gamePaddle = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        add(gamePaddle, APPLICATION_WIDTH/2.0,APPLICATION_HEIGHT - PADDLE_PADDING - PADDLE_HEIGHT);
        gameBricks(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
        gameBall(BALL_RADIUS);
    }

    //ball for game
    private void gameBall(int ballRadius) {
        double x = APPLICATION_WIDTH / 2.0 - BALL_RADIUS;
        double y = APPLICATION_HEIGHT - PADDLE_PADDING - PADDLE_HEIGHT/2.0 - (2 * BALL_RADIUS);
        ball = new GOval(x, y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);

        ball.setFilled(true);
        ball.setColor(ballColor);
        ball.setFillColor(ballColor);
        add(ball);
    }


    //building bricks in the main game
    private void gameBricks(double bricksColumns, double bricksRows, double bricksWidth, double bricksHeight) {
        for (int i = 0; i < bricksColumns; i++) {
            for (int j = 0; j < bricksRows; j++) {

                double x = BRICK_SEP + i * (bricksWidth + BRICK_SEP);
                double y = BRICK_Y_OFFSET + j * (bricksHeight + BRICK_SEP);

                GRoundRect roundRect = new GRoundRect(x, y, bricksWidth, bricksHeight);
                Color brickColor;
                if(j==0) brickColor = brickOneColor;
                else if(j==1) brickColor = brickTwoColor;
                else if(j==2) brickColor = brickThreeColor;
                else if(j==3) brickColor = brickFourColor;
                else if(j==4) brickColor = brickFiveColor;
                else if(j==5) brickColor = brickSixColor;
                else if(j==6) brickColor = brickSevenColor;
                else if(j==7) brickColor = brickEightColor;
                else if(j==8) brickColor = brickNineColor;
                else brickColor = brickTenColor;

                roundRect.setColor(brickColor);
                roundRect.setFillColor(brickColor);
                roundRect.setFilled(true);
                add(roundRect);
            }
        }
    }


    // start menu
    private void configureAppMenu() {
        removeAll();
        setBackground(settingsColor);
        setSize(SETTING_WINDOW_WIDTH, SETTING_WINDOW_HEIGHT);
        setSize(2*SETTING_WINDOW_WIDTH-getWidth(), 2*SETTING_WINDOW_HEIGHT-getHeight());
        StartMenuEnabled = true;
        header();
        paddleSettings();
        bricksColumnsSettings();
        bricksRowsSettings();
        bricksPaddingSettings();

        preview();
        saveStartMenu();
    }

    /** ============== PRESSED MOUSE ACTIONS ============== */
    public void mousePressed(MouseEvent e) {
        if(StartMenuEnabled) {
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (e.getY() >= SETTING_WINDOW_HEIGHT * 0.22 - 20 && e.getY() <= SETTING_WINDOW_HEIGHT * 0.22 + 20) {
                    actionSlider = 1;
                    System.out.println(1);
                }

                if (e.getY() >= SETTING_WINDOW_HEIGHT * 0.32 - 20 && e.getY() <= SETTING_WINDOW_HEIGHT * 0.32 + 20) {
                    actionSlider = 2;
                    System.out.println(2);
                }

                if (e.getY() >= SETTING_WINDOW_HEIGHT * 0.47 - 20 && e.getY() <= SETTING_WINDOW_HEIGHT * 0.47 + 20) {
                    actionSlider = 3;
                    System.out.println(3);
                }

                if (e.getY() >= SETTING_WINDOW_HEIGHT * 0.57 - 20 && e.getY() <= SETTING_WINDOW_HEIGHT * 0.57 + 20) {
                    actionSlider = 4;
                    System.out.println(4);
                }

                if (e.getY() >= SETTING_WINDOW_HEIGHT * 0.67 - 20 && e.getY() <= SETTING_WINDOW_HEIGHT * 0.67 + 20) {
                    actionSlider = 5;
                    System.out.println(5);
                }
            }

            if (e.getX() >= SETTING_PADDING+APPLICATION_WIDTH*0.25 && e.getX() <= SETTING_PADDING+APPLICATION_WIDTH*0.75){
                if (e.getY() >= SETTING_WINDOW_HEIGHT*0.92-SETTING_PADDING && e.getY() <= SETTING_WINDOW_HEIGHT-SETTING_PADDING) {
                    System.out.println("save");
                    StartMenuEnabled  = false;
                    waitingContinue = false;
                }
            }
        }
    }

    /** ============== RELEASED MOUSE ACTIONS ============== */
    public void mouseReleased(MouseEvent e) {
        if(StartMenuEnabled) {
            actionSlider = 0;
            System.out.println(0);
        }
    }

    /** ============== DRAGGED MOUSE ACTIONS ============== */
    public void mouseDragged(MouseEvent e) {
        if(StartMenuEnabled) {
            // PADDLE WIDTH
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 1) {
                    sliderPaddleWidth.sliderMove(e.getX(), 7);
                    PADDLE_WIDTH = (e.getX() - SETTING_PADDING) / 2 + 10;
                    paddleWidthValueLbl.setLabel(PADDLE_WIDTH + "");
                    paddleWidthValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - paddleWidthValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.2);

                    remove(paddlePreview);
                    paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
                    add(paddlePreview, gameFramePreviewer.getX() + APPLICATION_WIDTH / 2.0, gameFramePreviewer.getY() + APPLICATION_HEIGHT - PADDLE_PADDING - paddlePreview.getHeight());
                }
            }

            // PADDLE PADDING
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 2) {
                    sliderPaddlePadding.sliderMove(e.getX(), 7);
                    PADDLE_PADDING = (e.getX() - SETTING_PADDING) / 20 + 10;
                    paddlePaddingValueLbl.setLabel(PADDLE_PADDING + "");
                    paddlePaddingValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - paddlePaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.3);

                    remove(paddlePreview);
                    paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
                    add(paddlePreview, gameFramePreviewer.getX() + APPLICATION_WIDTH / 2.0, gameFramePreviewer.getY() + APPLICATION_HEIGHT - PADDLE_PADDING - paddlePreview.getHeight());
                }
            }

            // BRICKS COLUMNS NUMBER
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 3) {
                    sliderBricksColumns.sliderMove(e.getX(), 7);
                    int one_section = APPLICATION_WIDTH/19;
                    NBRICKS_PER_ROW = (e.getX() - SETTING_PADDING) / one_section+1;
                    BRICK_WIDTH = (APPLICATION_WIDTH - (NBRICKS_PER_ROW+1) * BRICK_SEP) / NBRICKS_PER_ROW;
                    bricksColumnsValueLbl.setLabel(NBRICKS_PER_ROW + "");
                    bricksColumnsValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - bricksColumnsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.45);

                    remove(bricksPreview);
                    bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
                    add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);                }
            }

            // BRICKS ROWS NUMBER
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 4) {
                    sliderBricksRows.sliderMove(e.getX(), 7);
                    int one_section = APPLICATION_WIDTH/9;
                    NBRICK_ROWS = (e.getX() - SETTING_PADDING) / one_section+1;
                    if (NBRICK_ROWS>10)  NBRICK_ROWS=10;
                    bricksRowsValueLbl.setLabel(NBRICK_ROWS + "");
                    bricksRowsValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - bricksRowsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.55);

                    remove(bricksPreview);
                    bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
                    add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);                }
            }

            // BRICKS TOP PADDING NUMBER
            if (e.getX() >= SETTING_PADDING && e.getX() <= SETTING_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 5) {
                    sliderBricksPadding.sliderMove(e.getX(), 7);
                    int one_section = APPLICATION_WIDTH/70;
                    BRICK_Y_OFFSET = (e.getX() - SETTING_PADDING) / one_section + BRICK_SEP;
                    if (BRICK_Y_OFFSET>70)  BRICK_Y_OFFSET=70;
                    bricksPaddingValueLbl.setLabel((int) Math.round(BRICK_Y_OFFSET) + "");
                    bricksPaddingValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - bricksPaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.65);

                    remove(bricksPreview);
                    bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
                    add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);
                }
            }
        }
    }


    /** ============== MOVING MOUSE ACTIONS WITH PADDLE ============== */
    public void mouseMoved(MouseEvent e){
        if (!StartMenuEnabled){
            double newX = e.getX() - gamePaddle.getWidth() / 2.0;;
            double currentY = gamePaddle.getY();

            if (newX < 0 + gamePaddle.getWidth()/2){
                newX=0 + gamePaddle.getWidth()/2;
            }
            if (newX > getWidth() - gamePaddle.getWidth()/2) {
                newX = getWidth() - gamePaddle.getWidth()/2;
            }

            gamePaddle.setLocation(newX, currentY);
        }
    }


    /** ============== WAITING TO CONTINUE ============== */
    private void waitForContinue() {
        waitingContinue = true;
        while (waitingContinue) {
            pause(50);
        }
    }











    /** ============== START MENU ============== */
    /* ===== HEADER ===== */
    private void header(){
        GImage image = new GImage("images/gameSettings.png");
        double imageWidth = APPLICATION_WIDTH *0.8;
        image.setSize(imageWidth, imageWidth*0.1);
        image.setLocation((APPLICATION_WIDTH -imageWidth)/2, SETTING_WINDOW_HEIGHT *0.05);
        add(image);
    }

    /* ===== PADDLE SETTINGS AND PREVIEW ===== */
    private void paddleSettings(){
        // WIDTH
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

        // BOTTOM PADDING
        GLabel paddlePaddingLbl = new GLabel("Paddle bottom padding");
        paddlePaddingLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddlePaddingLbl.setColor(Breakout.fontColor);
        paddlePaddingLbl.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.3);
        add(paddlePaddingLbl);

        paddlePaddingValueLbl = new GLabel(PADDLE_PADDING+"");
        paddlePaddingValueLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddlePaddingValueLbl.setColor(Breakout.fontColor);
        paddlePaddingValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - paddlePaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.3);
        add(paddlePaddingValueLbl);

        sliderPaddlePadding = new GSlider(APPLICATION_WIDTH, 3, 14, PADDLE_PADDING);
        sliderPaddlePadding.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.32);
        add(sliderPaddlePadding);
    }

    /* ===== BRICKS COLUMNS SETTINGS AND PREVIEW ===== */
    private void bricksColumnsSettings(){
        GLabel brickColumnsLbl = new GLabel("Columns number");
        brickColumnsLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        brickColumnsLbl.setColor(Breakout.fontColor);
        brickColumnsLbl.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.45);
        add(brickColumnsLbl);

        bricksColumnsValueLbl = new GLabel(NBRICKS_PER_ROW+"");
        bricksColumnsValueLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        bricksColumnsValueLbl.setColor(Breakout.fontColor);
        bricksColumnsValueLbl.setLocation(SETTING_PADDING+APPLICATION_WIDTH-bricksColumnsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.45);
        add(bricksColumnsValueLbl);

        sliderBricksColumns = new GSlider(APPLICATION_WIDTH, 3, 14, APPLICATION_WIDTH*0.5-SETTING_PADDING);
        sliderBricksColumns.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.47);
        add(sliderBricksColumns);
    }

    /* ===== BRICKS ROWS SETTINGS AND PREVIEW ===== */
    private void bricksRowsSettings(){
        GLabel brickRowsLbl = new GLabel("Rows number");
        brickRowsLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        brickRowsLbl.setColor(Breakout.fontColor);
        brickRowsLbl.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.55);
        add(brickRowsLbl);

        bricksRowsValueLbl = new GLabel(NBRICK_ROWS+"");
        bricksRowsValueLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        bricksRowsValueLbl.setColor(Breakout.fontColor);
        bricksRowsValueLbl.setLocation(SETTING_PADDING+APPLICATION_WIDTH-bricksRowsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.55);
        add(bricksRowsValueLbl);

        sliderBricksRows = new GSlider(APPLICATION_WIDTH, 3, 14, APPLICATION_WIDTH*0.5-SETTING_PADDING);
        sliderBricksRows.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.57);
        add(sliderBricksRows);
    }

    /* ===== BRICKS Y-OFFSET SETTINGS AND PREVIEW ===== */
    private void bricksPaddingSettings(){
        GLabel brickPaddingLbl = new GLabel("Bricks top padding");
        brickPaddingLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        brickPaddingLbl.setColor(Breakout.fontColor);
        brickPaddingLbl.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.65);
        add(brickPaddingLbl);

        bricksPaddingValueLbl = new GLabel((int) Math.round(BRICK_Y_OFFSET)+"");
        bricksPaddingValueLbl.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        bricksPaddingValueLbl.setColor(Breakout.fontColor);
        bricksPaddingValueLbl.setLocation(SETTING_PADDING + APPLICATION_WIDTH - bricksPaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.65);
        add(bricksPaddingValueLbl);

        sliderBricksPadding = new GSlider(APPLICATION_WIDTH, 3, 14, -SETTING_PADDING);
        sliderBricksPadding.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.67);
        add(sliderBricksPadding);
    }






    /* ===== SAVE BUTTON ===== */
    private void saveStartMenu(){
        GRect savebtn = new GRect(SETTING_PADDING+APPLICATION_WIDTH*0.25, SETTING_WINDOW_HEIGHT*0.92-SETTING_PADDING, APPLICATION_WIDTH*0.5, SETTING_WINDOW_HEIGHT*0.08);
        savebtn.setFilled(true);
        savebtn.setFillColor(sliderBallColor);
        savebtn.setColor(sliderBallColor);
        add(savebtn);

        GLabel savebtnLabel = new GLabel("Save settings");
        savebtnLabel.setFont("Monospased-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        savebtnLabel.setColor(Breakout.bgColor);
        savebtnLabel.setLocation(savebtn.getX()+(savebtn.getWidth()-savebtnLabel.getWidth())*0.5, savebtn.getY()+(savebtn.getHeight()+savebtnLabel.getHeight())*0.42);
        add(savebtnLabel);
    }

    /* ===== PREVIEW ===== */
    private void preview(){
        gameFramePreviewer = new GRect(APPLICATION_WIDTH + 2*SETTING_PADDING, SETTING_PADDING, Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        gameFramePreviewer.setFilled(true);
        gameFramePreviewer.setFillColor(Breakout.bgColor);
        add(gameFramePreviewer);

        paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        add(paddlePreview, gameFramePreviewer.getX() + APPLICATION_WIDTH / 2.0, gameFramePreviewer.getY() + APPLICATION_HEIGHT - PADDLE_PADDING - paddlePreview.getHeight());

        bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
        add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);
    }
}
