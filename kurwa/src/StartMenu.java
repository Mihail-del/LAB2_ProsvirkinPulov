/*
 * File: StatMenu.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Section Leader: Mykhailo Pulov
 *
 * This file is a menu that loads on start to set CONST in the game.
 * Last update: 15:09
 */

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
        GRect rect = new GRect(0,0, Breakout.APPLICATION_WIDTH,Breakout.APPLICATION_HEIGHT);
        rect.setFilled(true);
        add(rect);
    }

    private void paddleSettings(){

    }
}
