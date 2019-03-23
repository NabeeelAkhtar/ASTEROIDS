package Powerups;

import Player.BasicShip;
import Utils.Vector2D;
import game1.Game;
import game1.GameObject;

import java.awt.*;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

public class HealthPowerup extends GameObject {
    int radius = 20;

    public HealthPowerup(){

        position = new Vector2D(FRAME_WIDTH/2,50);
        velocity = new Vector2D(0,25);
        super.radius =radius;
    }

    @Override
    public void collisionHandling(GameObject other) {
        if(other.getClass() == BasicShip.class && this.overlap(other)){
            Game.lives += 1;
            this.hit();
        }
    }

    @Override
    public void update() {
        position.addScaled(velocity,DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawOval((int)position.x,(int)position.y,radius, radius);
    }
}
