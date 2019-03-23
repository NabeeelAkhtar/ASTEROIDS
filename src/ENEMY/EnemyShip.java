package ENEMY;

import Controls.Action;
import Controls.BasicController;
import ResourceManagers.SoundManager;
import Utils.Vector2D;
import game1.BasicAsteroid;
import game1.Game;
import game1.GameObject;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Constants.*;

public class EnemyShip extends GameObject {
    public Vector2D direction;
    public int radius = 8;
    private BasicController con;
    public static ENEMY_BULLET Ebullet = null;
    private int[] XP = {0,-1,0,1}; //sets the position of the ship
    public int[] YP = {-1,1,0,1};
    private int  shootdelay = 300;
    private long time_since_shot;


    public EnemyShip(BasicController con){
        this.con = con;
        position = new Vector2D(Math.random()*FRAME_WIDTH,Math.random()*FRAME_HEIGHT);

        velocity = new Vector2D(60,0);
        direction = new Vector2D(0,-1);
        super.radius = radius;
    }

    @Override
    public void hit(){
        synchronized (Game.class){
        Game.score += 20;
        this.dead = true;}
    }

    @Override
    public void update(){

        double x = Math.random()*100;
        Action act = this.con.action();

        if(this.position.y < Game.ship.position.y){
            direction = new Vector2D(0,1);
        }else if(this.position.y > Game.ship.position.y){
            direction = new Vector2D(0,-1);
        }else if(this.position.y == Game.ship.position.y && this.position.x < Game.ship.position.x ){
            direction = new Vector2D(1,0);
        }else if(this.position.y == Game.ship.position.y && this.position.x > Game.ship.position.x ){
            direction = new Vector2D(-1,0);
        }


        position.addScaled(velocity,DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);

        if (act.shoot) {

            if (System.currentTimeMillis()-time_since_shot > shootdelay){
                time_since_shot = System.currentTimeMillis();
                mkEBullet();
                //act.shoot = false;
            }
        }

        //SoundManager.extraShip();
        SoundManager.play(SoundManager.saucerBig);
    }

    public boolean getoverlap(){
        return abouttooverlap(Game.ship);
    }


    @Override
    public boolean overlap(GameObject other){
        double diffx = this.position.x - other.position.x;
        double diffy = this.position.y - other.position.y;
        double detect = diffx*diffx + diffy*diffy ;
        return detect < (this.radius + other.radius)*(this.radius + other.radius);
    }


    private boolean collisionconditions(GameObject other){
        return (this.getClass() != other.getClass() && other.getClass() != BasicAsteroid.class &&
                other.getClass() != ENEMY_BULLET.class && this.overlap(other));
    }


    @Override
    public void collisionHandling(GameObject other) {
        if (collisionconditions(other)) {
            this.hit();
            other.hit();
        }
    }


    @Override
    public void draw(Graphics2D g) {

        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        double DRAWING_SCALE = radius;
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(Color.red);
        g.fillPolygon(XP,YP,XP.length);
        g.setTransform(at);
    }


    public void mkEBullet() {
        Ebullet = new ENEMY_BULLET(new Vector2D(this.position), new Vector2D(velocity) , 5.0,this);
        Game.asteroids.add(Ebullet);
        SoundManager.fire();
    }
}
