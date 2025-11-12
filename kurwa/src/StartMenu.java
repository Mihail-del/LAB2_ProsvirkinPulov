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
    private int SETTING_WIDTH = 400;
    private int SETTING_PADDING = 30;

    private int SETTING_WINDOW_WIDTH = Breakout.APPLICATION_WIDTH + SETTING_WIDTH + 2*SETTING_PADDING;
    private int SETTING_WINDOW_HEIGHT = Breakout.APPLICATION_HEIGHT + 2*SETTING_PADDING;


    // drawing the menu
    public void run(){
        setBackground(Breakout.settingsColor);
        setSize(SETTING_WINDOW_WIDTH, SETTING_WINDOW_HEIGHT);
        setSize(2*SETTING_WINDOW_WIDTH-getWidth(), 2*SETTING_WINDOW_HEIGHT-getHeight());

        header();
        paddleSettings();

        preview();
    }

    /** ===== HEADER ===== */
    private void header(){
        GImage image = new GImage("images/gameSettings.png");
        double imageWidth = SETTING_WIDTH *0.8;
        image.setSize(imageWidth, imageWidth*0.1);
        image.setLocation((SETTING_WIDTH -imageWidth)/2, SETTING_WINDOW_HEIGHT *0.05);
        add(image);
    }

    private void paddleSettings(){
        GLabel paddleLbl = new GLabel("Paddle width");
        paddleLbl.setFont("Monospased-"+(int) Math.round(SETTING_WIDTH*0.05));
        paddleLbl.setColor(Breakout.fontColor);
        paddleLbl.setLocation(SETTING_PADDING, SETTING_WINDOW_HEIGHT*0.2);
        add(paddleLbl);
    }







    private void preview(){
        GRect gameFramePreviewer = new GRect(SETTING_WIDTH + SETTING_PADDING, SETTING_PADDING, Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        gameFramePreviewer.setFilled(true);
        gameFramePreviewer.setFillColor(Breakout.bgColor);
        add(gameFramePreviewer);

    }
}
