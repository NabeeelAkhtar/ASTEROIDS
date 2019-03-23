package game1;

import java.awt.*;
import java.util.ArrayList;
import ENEMY.EnemyShip;
import Powerups.AddHunderedHealth;
import Powerups.HealthPowerup;
import Powerups.MissilePowerup;
import ResourceManagers.SoundManager;
import Utils.*;

import static game1.Constants.*;

public class BasicAsteroid extends GameObject{
    public int RADIUS = 12;
    Image im = Constants.ASTEROID1;
    public static final double MAX_SPEED = 100;
    public static final double min = MAX_SPEED*-1;
    public static ArrayList<BasicAsteroid> spawns  = new ArrayList<>();

    public BasicAsteroid(double x, double y, double vx, double vy) {

        position = new Vector2D(x,y);
        velocity = new Vector2D(vx,vy);
        super.radius = RADIUS;
    }




    public static BasicAsteroid makeRandomAsteroid() {
        double velx =  Math.random()*(MAX_SPEED-min)+min;
        double vely =  Math.random()*(MAX_SPEED-min)+min;
        double posx =  Math.random()*FRAME_WIDTH;
        double posy =  Math.random()*FRAME_HEIGHT;



        return new BasicAsteroid(posx,posy,velx,vely);
    }


    public void setRadius(int r){
        this.RADIUS = r;
    }
    @Override
    public void update() {

        position.addScaled(velocity,DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
    }


    @Override
    public void collisionHandling(GameObject other) {
       synchronized (Game.class){
        if (this.getClass() == other.getClass() && this.overlap(other)){
            //this.velocity.subtract(other.position);

        }else if(other.getClass() == Wormhole.class && this.overlap(other)){

        }else if (this.getClass() != other.getClass() && this.overlap(other)&&other.getClass()!= EnemyShip.class &&
                other.getClass() != MissilePowerup.class && other.getClass() != HealthPowerup.class && other.getClass() != AddHunderedHealth.class) {
            this.hit();
        }}
    }


    @Override
    public void hit(){

        synchronized (Game.class) {
            SoundManager.play(SoundManager.bangMedium);
            this.dead = true;
           /* BasicAsteroid spawn1 = makeRandomAsteroid();
            spawn1.setRadius((int)this.radius - 4);
            BasicAsteroid spawn2 = makeRandomAsteroid();
            spawn2.setRadius((int)this.radius - 4);
            spawn2.velocity.mult(-1);
            spawn2.position.add(this.radius,this.radius);

            Game.alive.add(spawn1);
            Game.alive.add(spawn2);
          */
        }
    }


    @Override
    public void draw(Graphics2D g) {

        double px = position.x, py = position.y;
        Image img = Constants.ASTEROID1;
        g.drawImage(img,(int)px-RADIUS,(int)py-RADIUS,null);

    }
}

