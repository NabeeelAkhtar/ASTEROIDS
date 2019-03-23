package Player;
import Powerups.AddHunderedHealth;
import Powerups.HealthPowerup;
import Powerups.Missile;
import Powerups.MissilePowerup;
import ResourceManagers.SoundManager;
import Utils.Vector2D;
import Controls.*;
import game1.Game;
import game1.GameObject;
import game1.Wormhole;

import java.awt.*;
import java.awt.geom.AffineTransform;
import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;


public class BasicShip extends GameObject {
    public static final int RADIUS = 8;
    private int[] XP = {0,-1,0,1}; //sets the position of the ship
    public int[] YP = {-1,1,0,1};
    public int[] XPTHRUST = {0,-1,0,1}; //sets position of thrust
    public int[] YPTHRUST = {3,1,0,1};

    public static Bullet bullet = null;
    public static Missile missile = null;

    private boolean thrusting(){
        return this.ctrl.action().thrust > 0;
    }




    // rotation velocity in radians per second
    public static final double STEER_RATE = 2* Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 100;

    // constant speed loss factor
    public static final double DRAG = 0.1;

    public static final Color COLOR = Color.cyan;


    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;


    // controller which provides an Action object in each frame
    private BasicController ctrl;

    public BasicShip(BasicController ctrl) {
        this.ctrl = ctrl;
        position = new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2);
        velocity = new Vector2D();
        direction = new Vector2D(0,-1) ;
        super.radius = RADIUS;

    }


    @Override
    public void update() {
        synchronized (Game.class) {

            Action act = this.ctrl.action();

            direction.rotate(act.turn * STEER_RATE * DT);

            velocity.addScaled(direction, act.thrust * MAG_ACC * DT);

            velocity.subtract(DRAG, DRAG);


            position.addScaled(velocity, DT);
            position.wrap(FRAME_WIDTH, FRAME_HEIGHT);

        if(act.shoot && Game.missiles >= 1){
            mkMissile();
            act.shoot = false;
            Game.missiles -= 1;

        }else if (act.shoot) {
                mkBullet();
                act.shoot = false;
            }
        }
    }


    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        double DRAWING_SCALE = RADIUS;
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (thrusting()) {
            g.setColor(Color.orange);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }



    @Override
    public void collisionHandling(GameObject other) {

        if (other.getClass() == Missile.class) {  // ship's own bullets will not harm it
            Missile b = (Missile) other;
            if (b.shooter == this) {
            } else {
                this.hit();
                other.hit();
            }
        }else if (other.getClass() == Bullet.class) {  // ship's own bullets will not harm it
            Bullet b = (Bullet)other;
            if (b.shooter == this){}
            else{this.hit();
            other.hit();}
        }else if(other.getClass() == Wormhole.class && this.overlap(other)){
        } else if (this.getClass() != other.getClass() && this.overlap(other) && other.getClass() != Bullet.class && other.getClass() != MissilePowerup.class
                && other.getClass() != HealthPowerup.class && other.getClass() != AddHunderedHealth.class) {
            this.hit();
            other.hit();
        }
    }

    private void mkBullet() {
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity) , 5.0);
        bullet.shooter = this;
        Game.asteroids.add(bullet);
        SoundManager.fire();
    }

    private void mkMissile() {
        missile = new Missile(new Vector2D(position), new Vector2D(velocity) , 30,10,this);
        Game.asteroids.add(missile);
        SoundManager.fire();
    }
}

