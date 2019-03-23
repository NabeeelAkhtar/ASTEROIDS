package Player;

import Utils.Vector2D;
import game1.Game;
import game1.GameObject;

import java.awt.*;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

public class Bullet extends GameObject {
    double speedinit = 100.0;
    static GameObject shooter = null;

    public Bullet(Vector2D position, Vector2D velocity,Double radius){

        this.position = position;
        this.velocity = velocity.addScaled(Game.ship.direction,speedinit);
        super.radius = radius;
    }

    @Override
    public void collisionHandling(GameObject other) {

        if (other.getClass() == BasicShip.class) {

            if (shooter == other){}
            else{this.hit(); other.hit();}
        }else if (this.getClass() != other.getClass() && this.overlap(other) && other.getClass() != BasicShip.class&&other.dead) {
            this.hit();
            other.hit();
            Game.incScore();
        }
    }

    @Override
    public void update(){

        position.addScaled(velocity, DT);

        if(this.position.mag() < 0 || this.position.mag() > Math.sqrt(FRAME_HEIGHT*FRAME_HEIGHT+FRAME_WIDTH*FRAME_WIDTH)){
            this.dead = true;
        }

    }


    @Override
    public void draw(Graphics2D g) {

        g.setColor(Color.RED);
        g.fillRect((int)position.x,(int)position.y,5,5);
    }
}
