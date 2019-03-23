package game1;

import Controls.BasicController;
import Utils.Vector2D;

import java.awt.*;

import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

public class Wormhole extends GameObject {

    private double radius = 30;
    private Vector2D direction;
    private BasicController cont;

    public Wormhole(){
        double posx =  Math.random()*FRAME_WIDTH;
        double posy =  Math.random()*FRAME_HEIGHT;
        position = new Vector2D(posx,posy);
        velocity = new Vector2D(0,0);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        direction = new Vector2D(0,-1) ;
        super.radius = radius;

    }

    @Override
    public void update(){
        super.update();
    }


    @Override
    public void hit(){

    }


    @Override
    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other)) {
           other.position.set(Math.random()*FRAME_WIDTH,Math.random()*FRAME_HEIGHT);
        }
    }

            @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(138,42,226));
        double px = position.x, py = position.y;
        //g.fillOval((int) (px - radius),  (int)(py - radius), 2 *(int) radius, 2 *(int) radius);
        g.drawImage(Constants.Wormhole,(int) (px - radius),  (int)(py - radius), null);
    }
}
