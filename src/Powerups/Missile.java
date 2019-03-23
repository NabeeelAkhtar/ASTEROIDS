package Powerups;

import Player.Bullet;
import Utils.Vector2D;
import game1.Constants;
import game1.Game;
import game1.GameObject;
import game1.Wormhole;

import java.awt.*;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

public class Missile extends GameObject {
    public int Radius2;
    public GameObject shooter;
    double speedinit = 100.0;


    public Missile(Vector2D position, Vector2D velocity, int Radius1, int Radius2, GameObject shooter){

        this.position = position;
        this.velocity = velocity.addScaled(Game.ship.direction,speedinit);
        super.radius = Radius1;
        this.Radius2 = Radius2;
        this.shooter = shooter;
        this.dead = false;

    }

    @Override
    public void update(){
        position.addScaled(velocity,DT);
        if(this.position.mag() < 0 || this.position.mag() > Math.sqrt(FRAME_HEIGHT*FRAME_HEIGHT+FRAME_WIDTH*FRAME_WIDTH)){
            this.dead = true;
        }
    }

    @Override
    public boolean overlap(GameObject other){
        double diffx = this.position.x - other.position.x;
        double diffy = this.position.y - other.position.y;
        double detect = diffx*diffx + diffy*diffy ;
        return detect < (this.radius + other.radius)*(this.radius + other.radius);
    }

    @Override
    public void collisionHandling(GameObject other) {

        if (other.getClass() == this.shooter.getClass()) {
            // ship's own bullets will not harm it

        }else if(other.getClass() == Wormhole.class && this.overlap(other)){

        } else if (this.getClass() != other.getClass() && this.overlap(other) && other.getClass() != Missile.class) {
            this.hit();
            other.hit();
        }
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(Color.ORANGE);
        g.drawImage(Constants.Missile,(int)(position.x - radius),(int)(position.y-radius),null);
    }
}
