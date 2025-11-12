import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;


public class GPaddle extends GCompound {
    public GPaddle(double width, double height) {
        GRect rect = new GRect(0,0, width*5/6, height);
        rect.setColor(Breakout.paddleColor);
        rect.setFilled(true);
        rect.setFillColor(Breakout.paddleColor);
        add(rect, width*1/12, height/2);

        GOval oval1 = new GOval(0,0, width/6, height);
        oval1.setColor(Breakout.paddleColor);
        oval1.setFilled(true);
        oval1.setFillColor(Breakout.paddleColor);
        add(oval1, 0, height/2);

        GOval oval2 = new GOval(0,0, width/6, height);
        oval2.setColor(Breakout.paddleColor);
        oval2.setFilled(true);
        oval2.setFillColor(Breakout.paddleColor);
        add(oval2, width*10/12, height/2);
    }
}
