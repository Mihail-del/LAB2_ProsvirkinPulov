/*
 * File: Breakout.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Class Leader: Both
 *
 * This file is a main one in the game of Breakout.
 * Last update: 20:14 | 16.11.2025
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Breakout extends GraphicsProgram {
    /** ============== CONSTANTS ============== */
    //  Width and height of application window in pixels
    public static int APPLICATION_WIDTH = 500;
    public static int APPLICATION_HEIGHT = 700;
    public static int APPLICATION_PADDING = 30;
    public static int APPLICATION_TOP_PADDING = 50;
    private static int SETTING_WINDOW_WIDTH = 2*APPLICATION_WIDTH+ 3* APPLICATION_PADDING;
    private static int SETTING_WINDOW_HEIGHT = APPLICATION_HEIGHT + 2* APPLICATION_PADDING;

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

    //  Speed of the ball
    public static int BALL_GENERAL_SPEED = 3;
    public static int BALL_SPEED_X = BALL_GENERAL_SPEED; // X axis
    public static int BALL_SPEED_Y = -BALL_GENERAL_SPEED; // Y axis

    //  Paused delay for the ball in milliseconds
    public static int BALL_PAUSE = 5;

    //number of lives
    public static int LIVES = 3;

    //  Offset of the top brick row from the top
    public static double BRICK_Y_OFFSET = (int) Math.round(BRICK_SEP);

    /** ===== VARIABLES ====== */
    private boolean StartMenuEnabled = false;
    private boolean waitingContinue = false;
    private boolean isGameStarted = false;
    private boolean gameWon;
    private boolean isEndScreenActive = false;
    private boolean playAgainClicked = false;

    private ArrayList<GOval> particlesList = new ArrayList<GOval>();
    private ArrayList<Double> particleSpeedsX = new ArrayList<Double>();
    private ArrayList<Double> particleSpeedsY = new ArrayList<Double>();
    private ArrayList<GImage> hearts = new ArrayList<GImage>();
    private int actionSlider = 0;
    private int livesLeft;      //
    private int bricksLeft;


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
    GRect gamingField;
    GImage playAgain;
    GImage mainMenu;

    GSlider sliderBallSpeed;
    GLabel ballSpeedValueLbl;

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
        while (true) {
            resetAllConsts();
            configureAppMenu();
            waitForContinue();
            boolean keepPlaying = true;
            while (keepPlaying) {
                configureApp();
                playGame();
                playAgain();
                isEndScreenActive = true;
                while (isEndScreenActive) {
                    pause(50);
                }
                if (playAgainClicked) {
                    removeAll();
                } else {
                    keepPlaying = false;
                    removeAll();
                }
            }
        }
    }
    // play the main game
    private void playGame() {
        while (isGameStarted){
            ball.move(BALL_SPEED_X, BALL_SPEED_Y);
            pause(BALL_PAUSE);
            Object collider = checkBallSensors();
            if (collider != null) {
                createBrickParticles((GObject) collider);
                BALL_SPEED_Y = -BALL_SPEED_Y;
                bricksLeft = bricksLeft - 1;

                if (bricksLeft == 0){
                    isGameStarted = false; // game stopped
                    gameWon = true;
                }
            }

            if (ball.getX()+BALL_SPEED_X <= APPLICATION_PADDING) {
                BALL_SPEED_X = -BALL_SPEED_X;
            }
            if (ball.getX() + 2 * BALL_RADIUS + BALL_SPEED_X>= APPLICATION_PADDING + APPLICATION_WIDTH) {
                BALL_SPEED_X = -BALL_SPEED_X;
            }
            if (ball.getY() +BALL_SPEED_Y <= APPLICATION_TOP_PADDING) {
                BALL_SPEED_Y = -BALL_SPEED_Y;
            }
            if ((ball.getX() > gamePaddle.getX()-PADDLE_WIDTH/2.0-BALL_RADIUS && ball.getX() < gamePaddle.getX()+PADDLE_WIDTH/2.0+BALL_RADIUS)  && (ball.getY() < gamePaddle.getY()-PADDLE_HEIGHT && ball.getY() >= gamePaddle.getY()-PADDLE_HEIGHT-BALL_GENERAL_SPEED)) {
                BALL_SPEED_Y = -BALL_SPEED_Y;
            }
            if (ball.getY() >= APPLICATION_TOP_PADDING + APPLICATION_HEIGHT - 2 * BALL_RADIUS) {
                ball.setLocation(ball.getX(), APPLICATION_TOP_PADDING + APPLICATION_HEIGHT - 2 * BALL_RADIUS);// just to check
                livesLeft --;
                GImage heartToRemove = hearts.get(livesLeft);
                for (int i = 0; i < 4; i++) {    // heart lost animation
                    heartToRemove.setVisible(false);
                    pause(100);
                    heartToRemove.setVisible(true);
                    pause(100);
                }
                remove(heartToRemove);
                for (int i = 0; i < 4; i++) {    // ball lost animation
                    ball.setVisible(false);
                    pause(100);
                    ball.setVisible(true);
                    pause(100);
                }
                remove(ball);
                if (livesLeft > 0) {
                    gameBall(BALL_RADIUS);
                    calcSpeedX();
                    BALL_SPEED_Y = -BALL_GENERAL_SPEED;
                    pause(3000);
                }
                else {
                    isGameStarted = false;
                    gameWon = false;
                }
            }

            new Thread(() -> moveParticles()).start();
        }
    }

    private void playAgain() {
        if (!gameWon) {
        GImage lostGame = new GImage( "lostGame.png");
        lostGame.setSize(400, 150);
        double x = getWidth()/2.0-lostGame.getWidth()/2.0;
        double y = getHeight()/2.5-lostGame.getHeight()/2.0;
        lostGame.setLocation(x, y);
        add(lostGame);}
        else {
            GImage wonGame = new GImage( "wonGame.png");
            wonGame.setSize(400, 150);
            double x = getWidth()/2.0-wonGame.getWidth()/2.0;
            double y = getHeight()/2.5-wonGame.getHeight()/2.0;
            wonGame.setLocation(x, y);
            add(wonGame);
        }

        playAgain = new GImage("playAgain.png");
        playAgain.setSize(240,70);
        playAgain.setLocation(getWidth()/2.0 - playAgain.getWidth()/2.0, getHeight()/2.0 + playAgain.getHeight());
        add(playAgain);

        mainMenu = new GImage("mainMenu.png");
        mainMenu.setSize(240,70);
        mainMenu.setLocation(getWidth()/2.0 - mainMenu.getWidth()/2.0, playAgain.getY() + playAgain.getHeight() + 10);
        add(mainMenu);

    }

    /** ===== CREATING PARTICLES ===== */
    private void moveParticles() {
        for (int i = particlesList.size()-1; i >= 0; i--) {
            GOval particle = (GOval) particlesList.get(i);
            double dx = particleSpeedsX.get(i);
            double dy = particleSpeedsY.get(i);
            particle.move(dx, dy);
            particleSpeedsY.set(i, dy + 0.1);
            if (particle.getY() > getHeight()*2.0/3.0) {
                remove(particle);
                particlesList.remove(i);
                particleSpeedsX.remove(i);
                particleSpeedsY.remove(i);
            }
        }
    }

    //animation for destroying bricks
    private void createBrickParticles(GObject brick){
        double x = brick.getX()+BRICK_WIDTH/2.0;
        double y = brick.getY()+BRICK_HEIGHT/2.0;
        Color color = brick.getColor();
        remove(brick);
        for (int i = 0; i < 10; i++) {
            double newX = x + (Math.random() - 0.5) * 10;
            double newY = y + (Math.random() - 0.5) * 10;

            GOval particle = new GOval(newX, newY, 5, 5);
            particle.setColor(color);
            particle.setFillColor(color);
            particle.setFilled(true);
            add(particle);

            particleSpeedsX.add((Math.random() - 0.5)*4);
            particleSpeedsY.add((Math.random() - 0.5)*4);
            particlesList.add(particle);
        }


    }

    /** ===== CHECK BALL COLISIONS WITH OBJECTS ===== */

    // More accurate collision check without arrays
    private GObject checkBallSensors() {
        double x = ball.getX();
        double y = ball.getY();
        double d = 2 * BALL_RADIUS;
        double r = BALL_RADIUS;

        GObject obj;
        boolean objectForAction;

        obj = getElementAt(x, y); // top-left corner
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x + d, y); // top-right corner
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x, y + d); // bottom-left corner
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x + d, y + d); // bottom-right corner
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x + r, y); // top-middle
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x + r, y + d); // bottom-middle
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x, y + r); // left-middle
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x + d, y + r); // right-middle
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        obj = getElementAt(x + r, y + r); // center
        objectForAction = obj != null && obj != ball && obj != gamePaddle && obj != gamingField && !hearts.contains(obj) && !particlesList.contains(obj);
        if (objectForAction) return obj;

        return null;
    }




    /** ============== APP CONFIGURATION ============== */
    // main game
    private void configureApp() {
        removeAll();
        particlesList.clear();
        particleSpeedsX.clear();
        particleSpeedsY.clear();
        livesLeft = LIVES;
        calcSpeedX();
        isGameStarted = true;
        setBackground(settingsColor);
        setSize(APPLICATION_WIDTH+2* APPLICATION_PADDING, APPLICATION_HEIGHT+ APPLICATION_PADDING + APPLICATION_TOP_PADDING);
        setSize(2*(APPLICATION_WIDTH+2* APPLICATION_PADDING)-getWidth(), 2*(APPLICATION_HEIGHT+APPLICATION_PADDING + APPLICATION_TOP_PADDING)-getHeight());

        // Gaming field
        gamingField = new GRoundRect(APPLICATION_PADDING, APPLICATION_TOP_PADDING, APPLICATION_WIDTH, APPLICATION_HEIGHT);
        gamingField.setFilled(true);
        gamingField.setColor(bgColor);
        gamingField.setFillColor(bgColor);
        add(gamingField);

        // Paddle
        gamePaddle = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        add(gamePaddle, APPLICATION_WIDTH/2.0,APPLICATION_HEIGHT - PADDLE_PADDING - PADDLE_HEIGHT + APPLICATION_TOP_PADDING);
        gameBricks(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
        gameBall(BALL_RADIUS);

        // Heart frame
        GRoundRect heartFrame = new GRoundRect(APPLICATION_PADDING, APPLICATION_TOP_PADDING*0.1, (APPLICATION_TOP_PADDING*0.6 + APPLICATION_PADDING *0.3)*3+APPLICATION_PADDING *0.3, APPLICATION_TOP_PADDING*0.8);
        heartFrame.setFilled(true);
        heartFrame.setColor(bgColor);
        heartFrame.setFillColor(bgColor);
        add(heartFrame);

        hearts.clear();
        for (int i = 0; i<livesLeft; i++){
            GImage heart = new GImage("heart.png");
            heart.setSize(APPLICATION_TOP_PADDING *0.6, APPLICATION_TOP_PADDING *0.6);
            double x = APPLICATION_PADDING *1.3 + (heart.getWidth() + APPLICATION_PADDING *0.3)* i;
            double y = APPLICATION_TOP_PADDING *0.2;
            heart.setLocation(x, y);
            hearts.add(heart);
            add(heart);
        }


    }

    // Ball for game
    private void gameBall(int ballRadius) {
        double x = (APPLICATION_WIDTH+ APPLICATION_PADDING) / 2.0 - BALL_RADIUS;
        double y = APPLICATION_HEIGHT + APPLICATION_TOP_PADDING - PADDLE_PADDING - PADDLE_HEIGHT/2.0 - (4 * BALL_RADIUS);
        ball = new GOval(x, y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);

        ball.setFilled(true);
        ball.setColor(ballColor);
        ball.setFillColor(ballColor);
        add(ball);
    }


    //building bricks in the main game
    private void gameBricks(double bricksColumns, double bricksRows, double bricksWidth, double bricksHeight) {
        bricksLeft = 0;
        for (int i = 0; i < bricksColumns; i++) {
            for (int j = 0; j < bricksRows; j++) {

                double x = BRICK_SEP + APPLICATION_PADDING + i * (bricksWidth + BRICK_SEP);
                double y = BRICK_Y_OFFSET + APPLICATION_TOP_PADDING + j * (bricksHeight + BRICK_SEP);

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
                bricksLeft++;
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
        maketDraw();
        paddleSettings();
        bricksColumnsSettings();
        bricksRowsSettings();
        bricksPaddingSettings();
        ballSpeedSettings();

        preview();
        saveStartMenu();
    }

    private void calcSpeedX(){
        BALL_SPEED_X = (int) Math.round(Math.random()*2 +1);
        if ( Math.round(Math.random()*2) >= 1)
            BALL_SPEED_X = -BALL_SPEED_X;
    }

    /** ============== PRESSED MOUSE ACTIONS ============== */
    public void mousePressed(MouseEvent e) {
        if(StartMenuEnabled) {
            if (e.getX() >= APPLICATION_PADDING - 10 && e.getX() <= APPLICATION_PADDING + APPLICATION_WIDTH + 10) {
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

                if (e.getY() >= SETTING_WINDOW_HEIGHT * 0.82 - 20 && e.getY() <= SETTING_WINDOW_HEIGHT * 0.82 + 20) {
                    actionSlider = 6;
                    System.out.println(6);
                }
            }

            if (e.getX() >= APPLICATION_PADDING +APPLICATION_WIDTH*0.25 && e.getX() <= APPLICATION_PADDING +APPLICATION_WIDTH*0.75){
                if (e.getY() >= SETTING_WINDOW_HEIGHT*0.92- APPLICATION_PADDING && e.getY() <= SETTING_WINDOW_HEIGHT- APPLICATION_PADDING) {
                    System.out.println("save");
                    StartMenuEnabled  = false;
                    waitingContinue = false;
                }
            }
        } else if (isEndScreenActive) {
            GObject obj = getElementAt(e.getX(), e.getY());
            if (obj == playAgain) {
                playAgainClicked = true;
                isEndScreenActive = false;
            }
            else if (obj == mainMenu) {
                playAgainClicked = false;
                isEndScreenActive = false;

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
            if (e.getX() >= APPLICATION_PADDING && e.getX() <= APPLICATION_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 1) {
                    sliderPaddleWidth.sliderMove(e.getX(), 7);
                    PADDLE_WIDTH = (e.getX() - APPLICATION_PADDING) / 2 + 10;
                    paddleWidthValueLbl.setLabel(PADDLE_WIDTH + "");
                    paddleWidthValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - paddleWidthValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.2);

                    remove(paddlePreview);
                    paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
                    add(paddlePreview, gameFramePreviewer.getX() + APPLICATION_WIDTH / 2.0, gameFramePreviewer.getY() + APPLICATION_HEIGHT - PADDLE_PADDING - paddlePreview.getHeight());
                }
            }

            // PADDLE PADDING
            if (e.getX() >= APPLICATION_PADDING && e.getX() <= APPLICATION_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 2) {
                    sliderPaddlePadding.sliderMove(e.getX(), 7);
                    PADDLE_PADDING = (e.getX() - APPLICATION_PADDING) / 20 + 10;
                    paddlePaddingValueLbl.setLabel(PADDLE_PADDING + "");
                    paddlePaddingValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - paddlePaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.3);

                    remove(paddlePreview);
                    paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
                    add(paddlePreview, gameFramePreviewer.getX() + APPLICATION_WIDTH / 2.0, gameFramePreviewer.getY() + APPLICATION_HEIGHT - PADDLE_PADDING - paddlePreview.getHeight());
                }
            }

            // BRICKS COLUMNS NUMBER
            if (e.getX() >= APPLICATION_PADDING && e.getX() <= APPLICATION_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 3) {
                    sliderBricksColumns.sliderMove(e.getX(), 7);
                    int one_section = APPLICATION_WIDTH/19;
                    NBRICKS_PER_ROW = (e.getX() - APPLICATION_PADDING) / one_section+1;
                    BRICK_WIDTH = (APPLICATION_WIDTH - (NBRICKS_PER_ROW+1) * BRICK_SEP) / NBRICKS_PER_ROW;
                    bricksColumnsValueLbl.setLabel(NBRICKS_PER_ROW + "");
                    bricksColumnsValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - bricksColumnsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.45);

                    remove(bricksPreview);
                    bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
                    add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);                }
            }

            // BRICKS ROWS NUMBER
            if (e.getX() >= APPLICATION_PADDING && e.getX() <= APPLICATION_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 4) {
                    sliderBricksRows.sliderMove(e.getX(), 7);
                    int one_section = APPLICATION_WIDTH/9;
                    NBRICK_ROWS = (e.getX() - APPLICATION_PADDING) / one_section+1;
                    if (NBRICK_ROWS>10)  NBRICK_ROWS=10;
                    bricksRowsValueLbl.setLabel(NBRICK_ROWS + "");
                    bricksRowsValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - bricksRowsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.55);

                    remove(bricksPreview);
                    bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
                    add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);                }
            }

            // BRICKS TOP PADDING NUMBER
            if (e.getX() >= APPLICATION_PADDING && e.getX() <= APPLICATION_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 5) {
                    sliderBricksPadding.sliderMove(e.getX(), 7);
                    int one_section = APPLICATION_WIDTH/70;
                    BRICK_Y_OFFSET = (e.getX() - APPLICATION_PADDING) / one_section + BRICK_SEP;
                    if (BRICK_Y_OFFSET>70)  BRICK_Y_OFFSET=70;
                    bricksPaddingValueLbl.setLabel((int) Math.round(BRICK_Y_OFFSET) + "");
                    bricksPaddingValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - bricksPaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.65);

                    remove(bricksPreview);
                    bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
                    add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);
                }
            }

            // BALL SPEED
            if (e.getX() >= APPLICATION_PADDING && e.getX() <= APPLICATION_PADDING + APPLICATION_WIDTH) {
                if (actionSlider == 6) {
                    sliderBallSpeed.sliderMove(e.getX(), 7);
                    int one_section = APPLICATION_WIDTH/9;
                    BALL_GENERAL_SPEED = (int) Math.round((e.getX() - APPLICATION_PADDING) / one_section + 1);
                    BALL_SPEED_Y = -BALL_GENERAL_SPEED;
                    if (BRICK_Y_OFFSET>10)  BRICK_Y_OFFSET=10;
                    ballSpeedValueLbl.setLabel(BALL_GENERAL_SPEED + "");
                    ballSpeedValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - ballSpeedValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.80);
                }
            }
        }
    }


    /** ============== MOVING MOUSE ACTIONS WITH PADDLE ============== */
    public void mouseMoved(MouseEvent e){
        if (isGameStarted){
            double newX = e.getX();
            double currentY = gamePaddle.getY();

            if (newX < APPLICATION_PADDING + gamePaddle.getWidth()/2){
                newX= APPLICATION_PADDING + gamePaddle.getWidth()/2;
            }
            if (newX > APPLICATION_WIDTH+ APPLICATION_PADDING - gamePaddle.getWidth()/2) {
                newX = APPLICATION_WIDTH+ APPLICATION_PADDING - gamePaddle.getWidth()/2;
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

    /* ===== MAKET FRAMES ===== */
    private void maketDraw(){
        GRoundRect sectionOne = new GRoundRect(APPLICATION_PADDING *0.6, SETTING_WINDOW_HEIGHT*0.15, APPLICATION_WIDTH+ APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.2);
        sectionOne.setFilled(true);
        sectionOne.setFillColor(bgColor);
        add(sectionOne);

        GRoundRect sectionTwo = new GRoundRect(APPLICATION_PADDING *0.6, SETTING_WINDOW_HEIGHT*0.4, APPLICATION_WIDTH+ APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.3);
        sectionTwo.setFilled(true);
        sectionTwo.setFillColor(bgColor);
        add(sectionTwo);

        GRoundRect sectionThree = new GRoundRect(APPLICATION_PADDING *0.6, SETTING_WINDOW_HEIGHT*0.75, APPLICATION_WIDTH+ APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.1);
        sectionThree.setFilled(true);
        sectionThree.setFillColor(bgColor);
        add(sectionThree);
    }

    /* ===== PADDLE SETTINGS AND PREVIEW ===== */
    private void paddleSettings(){
        // WIDTH
        GLabel paddleWidthLbl = new GLabel("Paddle width");
        paddleWidthLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddleWidthLbl.setColor(Breakout.fontColor);
        paddleWidthLbl.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.2);
        add(paddleWidthLbl);

        paddleWidthValueLbl = new GLabel(PADDLE_WIDTH+"");
        paddleWidthValueLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddleWidthValueLbl.setColor(Breakout.fontColor);
        paddleWidthValueLbl.setLocation(APPLICATION_PADDING +APPLICATION_WIDTH-paddleWidthValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.2);
        add(paddleWidthValueLbl);

        sliderPaddleWidth = new GSlider(APPLICATION_WIDTH, 3, 14, PADDLE_WIDTH);
        sliderPaddleWidth.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.22);
        add(sliderPaddleWidth);

        // BOTTOM PADDING
        GLabel paddlePaddingLbl = new GLabel("Paddle bottom padding");
        paddlePaddingLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddlePaddingLbl.setColor(Breakout.fontColor);
        paddlePaddingLbl.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.3);
        add(paddlePaddingLbl);

        paddlePaddingValueLbl = new GLabel(PADDLE_PADDING+"");
        paddlePaddingValueLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        paddlePaddingValueLbl.setColor(Breakout.fontColor);
        paddlePaddingValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - paddlePaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.3);
        add(paddlePaddingValueLbl);

        sliderPaddlePadding = new GSlider(APPLICATION_WIDTH, 3, 14, PADDLE_PADDING);
        sliderPaddlePadding.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.32);
        add(sliderPaddlePadding);
    }

    /* ===== BRICKS COLUMNS SETTINGS AND PREVIEW ===== */
    private void bricksColumnsSettings(){
        GLabel brickColumnsLbl = new GLabel("Columns number");
        brickColumnsLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        brickColumnsLbl.setColor(Breakout.fontColor);
        brickColumnsLbl.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.45);
        add(brickColumnsLbl);

        bricksColumnsValueLbl = new GLabel(NBRICKS_PER_ROW+"");
        bricksColumnsValueLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        bricksColumnsValueLbl.setColor(Breakout.fontColor);
        bricksColumnsValueLbl.setLocation(APPLICATION_PADDING +APPLICATION_WIDTH-bricksColumnsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.45);
        add(bricksColumnsValueLbl);

        sliderBricksColumns = new GSlider(APPLICATION_WIDTH, 3, 14, APPLICATION_WIDTH*0.5- APPLICATION_PADDING);
        sliderBricksColumns.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.47);
        add(sliderBricksColumns);
    }

    /* ===== BRICKS ROWS SETTINGS AND PREVIEW ===== */
    private void bricksRowsSettings(){
        GLabel brickRowsLbl = new GLabel("Rows number");
        brickRowsLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        brickRowsLbl.setColor(Breakout.fontColor);
        brickRowsLbl.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.55);
        add(brickRowsLbl);

        bricksRowsValueLbl = new GLabel(NBRICK_ROWS+"");
        bricksRowsValueLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        bricksRowsValueLbl.setColor(Breakout.fontColor);
        bricksRowsValueLbl.setLocation(APPLICATION_PADDING +APPLICATION_WIDTH-bricksRowsValueLbl.getWidth(), SETTING_WINDOW_HEIGHT*0.55);
        add(bricksRowsValueLbl);

        sliderBricksRows = new GSlider(APPLICATION_WIDTH, 3, 14, APPLICATION_WIDTH*0.5- APPLICATION_PADDING);
        sliderBricksRows.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.57);
        add(sliderBricksRows);
    }

    /* ===== BRICKS Y-OFFSET SETTINGS AND PREVIEW ===== */
    private void bricksPaddingSettings(){
        GLabel brickPaddingLbl = new GLabel("Bricks top padding");
        brickPaddingLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        brickPaddingLbl.setColor(Breakout.fontColor);
        brickPaddingLbl.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.65);
        add(brickPaddingLbl);

        bricksPaddingValueLbl = new GLabel((int) Math.round(BRICK_Y_OFFSET)+"");
        bricksPaddingValueLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        bricksPaddingValueLbl.setColor(Breakout.fontColor);
        bricksPaddingValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - bricksPaddingValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.65);
        add(bricksPaddingValueLbl);

        sliderBricksPadding = new GSlider(APPLICATION_WIDTH, 3, 14, -APPLICATION_PADDING +7);
        sliderBricksPadding.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.67);
        add(sliderBricksPadding);
    }

    /* ===== BALL SPEED ===== */
    private void ballSpeedSettings(){
        GLabel ballSpeedLbl = new GLabel("Ball speed");
        ballSpeedLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        ballSpeedLbl.setColor(Breakout.fontColor);
        ballSpeedLbl.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.8);
        add(ballSpeedLbl);

        ballSpeedValueLbl = new GLabel((int) Math.round(BALL_GENERAL_SPEED)+"");
        ballSpeedValueLbl.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        ballSpeedValueLbl.setColor(Breakout.fontColor);
        ballSpeedValueLbl.setLocation(APPLICATION_PADDING + APPLICATION_WIDTH - ballSpeedValueLbl.getWidth(), SETTING_WINDOW_HEIGHT * 0.8);
        add(ballSpeedValueLbl);

        sliderBallSpeed = new GSlider(APPLICATION_WIDTH, 3, 14, APPLICATION_WIDTH*0.3- APPLICATION_PADDING);
        sliderBallSpeed.setLocation(APPLICATION_PADDING, SETTING_WINDOW_HEIGHT*0.82);
        add(sliderBallSpeed);
    }






    /* ===== SAVE BUTTON ===== */
    private void saveStartMenu(){
        GRect savebtn = new GRect(APPLICATION_PADDING +APPLICATION_WIDTH*0.25, SETTING_WINDOW_HEIGHT*0.92- APPLICATION_PADDING, APPLICATION_WIDTH*0.5, SETTING_WINDOW_HEIGHT*0.08);
        savebtn.setFilled(true);
        savebtn.setFillColor(sliderBallColor);
        savebtn.setColor(sliderBallColor);
        add(savebtn);

        GLabel savebtnLabel = new GLabel("Save settings");
        savebtnLabel.setFont("Monospaced-"+(int) Math.round(APPLICATION_WIDTH*0.05));
        savebtnLabel.setColor(Breakout.bgColor);
        savebtnLabel.setLocation(savebtn.getX()+(savebtn.getWidth()-savebtnLabel.getWidth())*0.5, savebtn.getY()+(savebtn.getHeight()+savebtnLabel.getHeight())*0.42);
        add(savebtnLabel);
    }

    /* ===== PREVIEW ===== */
    private void preview(){
        gameFramePreviewer = new GRoundRect(APPLICATION_WIDTH + 2* APPLICATION_PADDING, APPLICATION_PADDING, APPLICATION_WIDTH, APPLICATION_HEIGHT);
        gameFramePreviewer.setFilled(true);
        gameFramePreviewer.setFillColor(Breakout.bgColor);
        add(gameFramePreviewer);

        paddlePreview = new GPaddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        add(paddlePreview, gameFramePreviewer.getX() + APPLICATION_WIDTH / 2.0, gameFramePreviewer.getY() + APPLICATION_HEIGHT - PADDLE_PADDING - paddlePreview.getHeight());

        bricksPreview = new GBricksPreview(NBRICKS_PER_ROW, NBRICK_ROWS, BRICK_WIDTH, BRICK_HEIGHT);
        add(bricksPreview, gameFramePreviewer.getX()+BRICK_SEP, gameFramePreviewer.getY()+BRICK_Y_OFFSET);
    }

    /** ===== RESET All CONSTANTS ===== */
    private void resetAllConsts(){
        PADDLE_WIDTH = 60;
        PADDLE_HEIGHT = 10;
        PADDLE_PADDING = 10;
        NBRICKS_PER_ROW = 10;
        NBRICK_ROWS = 5;
        BRICK_SEP = 10;
        BRICK_WIDTH = (APPLICATION_WIDTH - (NBRICKS_PER_ROW+1) * BRICK_SEP) / NBRICKS_PER_ROW;
        BRICK_HEIGHT = 15;
        BALL_RADIUS = 10;
        BALL_GENERAL_SPEED = 3;
        BALL_SPEED_X = BALL_GENERAL_SPEED; // X axis
        BALL_SPEED_Y = -BALL_GENERAL_SPEED; // Y axis
        BALL_PAUSE = 5;
        LIVES = 3;
        BRICK_Y_OFFSET = (int) Math.round(BRICK_SEP);

        /* ===== VARIABLES ====== */
        StartMenuEnabled = false;
        waitingContinue = false;
        isGameStarted = false;
        isEndScreenActive = false;
        playAgainClicked = false;

        actionSlider = 0;
    }
}
