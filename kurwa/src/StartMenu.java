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
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class StartMenu extends GraphicsProgram {
    // StartMenu(){}


    // drawing the menu
    public void run(){
        setBackground(Breakout.bgColor);
        setSize(Breakout.APPLICATION_WIDTH, Breakout.APPLICATION_HEIGHT);
        setSize(2*(Breakout.APPLICATION_WIDTH)-getWidth(), 2*(Breakout.APPLICATION_HEIGHT)-getHeight());

        header();
        paddleSettings();
    }

    /** ===== HEADER ===== */
    private void header(){
        GImage image = new GImage("../images/gameSettings.png");
        image.setSize(Breakout.APPLICATION_WIDTH*0.5, Breakout.APPLICATION_WIDTH*9/16.0);
        add(image);
    }

    private void paddleSettings(){

    }
}
