/*
 * File: StatMenu.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Section Leader: Both
 *
 * This file is a main one in the game of Breakout.
 *
 * Last update: 15:09
 */

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class StartMenu extends GraphicsProgram {
    StartMenu(){}


    // drawing the menu
    public static void draw(){
        GRect rect = new GRect(0,0,400,400);
        rect.setFilled(true);
        //add(rect);
    }
}
