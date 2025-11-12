import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;


public class GSlider extends GCompound {
    GRect body;
    GOval ball;

    public  GSlider(double width, double height, double diameter) {
        GRect body = new GRect(0, 0, width, height);
        body.setFilled(true);
        body.setFillColor(Breakout.sliderColor);
        body.setColor(Breakout.sliderColor);
        add(body);

        GOval ball = new GOval(- diameter /2.0, - diameter /2.0, diameter, diameter);
        ball.setFilled(true);
        ball.setFillColor(Breakout.sliderBallColor);
        ball.setColor(Breakout.sliderBallColor);
        add(ball);
    }
}
