/*
 * File: GBricksPreview.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Class Leader: Mykhailo
 *
 * This file is a constructor of a bricks for preview page.
 */

import acm.graphics.GCompound;
import acm.graphics.GRoundRect;

import java.awt.*;

public class GBricksPreview extends GCompound {
    public GBricksPreview(double bricksColumns, double bricksRows, double bricksWidth, double bricksHeight) {
        for (int i = 0; i < bricksColumns; i++) {
            for (int j = 0; j < bricksRows; j++) {
                GRoundRect roundRect = new GRoundRect(i*(bricksWidth+Breakout.BRICK_SEP), j*(bricksHeight+Breakout.BRICK_SEP), bricksWidth, bricksHeight);
                Color brickColor;
                if(j==0) brickColor = Breakout.brickOneColor;
                else if(j==1) brickColor = Breakout.brickTwoColor;
                else if(j==2) brickColor = Breakout.brickThreeColor;
                else if(j==3) brickColor = Breakout.brickFourColor;
                else if(j==4) brickColor = Breakout.brickFiveColor;
                else if(j==5) brickColor = Breakout.brickSixColor;
                else if(j==6) brickColor = Breakout.brickSevenColor;
                else if(j==7) brickColor = Breakout.brickEightColor;
                else if(j==8) brickColor = Breakout.brickNineColor;
                else brickColor = Breakout.brickTenColor;

                roundRect.setColor(brickColor);
                roundRect.setFillColor(brickColor);
                roundRect.setFilled(true);
                add(roundRect);
            }
        }
    }
}
