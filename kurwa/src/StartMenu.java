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

public class StartMenu extends GraphicsProgram {
    // StartMenu(){}

    /** ===== LOCAL CONSTANTS ====== */
    private static final int SETTING_WIDTH = 300;
    private static final int SETTING_PADDING = 30;

    private static final int WIDTH = Breakout.APPLICATION_WIDTH + SETTING_WIDTH + 2*SETTING_PADDING;
    private static final int HEIGHT = Breakout.APPLICATION_HEIGHT + 2*SETTING_PADDING;

    // drawing the menu
    public void run(){
        setBackground(Breakout.bgColor);
        setSize(WIDTH, HEIGHT);
        setSize(2*WIDTH-getWidth() + 300, 2*HEIGHT-getHeight());

        header();
        paddleSettings();

        preview();
    }

    /** ===== HEADER ===== */
    private void header(){
        GImage image = new GImage("images/gameSettings.png");
        double imageWidth = WIDTH*0.8;
        image.setSize(imageWidth, imageWidth*0.1);
        image.setLocation((WIDTH-imageWidth)/2,HEIGHT*0.05);
        add(image);
    }

    private void paddleSettings(){
        GLabel paddleLbl = new GLabel("Paddle Width");

    }







    private void preview(){
        GRect gameFramePreviewer = new GRect(SETTING_WIDTH+ SETTING_PADDING, SETTING_PADDING, Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        gameFramePreviewer.setFilled(true);
        gameFramePreviewer.setFillColor(Breakout.bgColor);
        add(gameFramePreviewer);

    }
}
