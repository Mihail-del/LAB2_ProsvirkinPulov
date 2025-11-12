import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;


public class GSlider extends GCompound {
    GRect body;
    GOval ball;

    public GSlider(double width, double height, double diameter) {
        this.body = new GRect(0, -height/2, width, height);
        this.body.setFilled(true);
        this.body.setFillColor(Breakout.sliderColor);
        this.body.setColor(Breakout.sliderColor);
        add(this.body);

        this.ball = new GOval(- diameter/2.0, - diameter/2.0, diameter, diameter);
        this.ball.setFilled(true);
        this.ball.setFillColor(Breakout.sliderBallColor);
        this.ball.setColor(Breakout.sliderBallColor);
        add(this.ball);
    }


    public void sliderMove(double x, double y) {
        this.ball.setLocation(x-StartMenu.getSETTING_PADDING(), -y);
    }
}
