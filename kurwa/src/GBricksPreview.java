import acm.graphics.GCompound;
import acm.graphics.GRoundRect;

import java.awt.*;

public class GBricksPreview extends GCompound {
    public GBricksPreview(double bricksColumns, double bricksRows, double bricksWidth, double bricksHeight) {
        for (int i = 0; i < bricksColumns; i++) {
            for (int j = 0; j < bricksRows; j++) {
                GRoundRect roundRect = new GRoundRect(i*(bricksWidth+Breakout.BRICK_SEP), j*(bricksHeight+Breakout.BRICK_SEP), bricksWidth, bricksHeight);
                roundRect.setColor(Color.black);
                roundRect.setFillColor(Color.black);
                roundRect.setFilled(true);
                add(roundRect);
            }
        }
    }
}
