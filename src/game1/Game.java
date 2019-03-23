package game1;

import java.util.ArrayList;
import java.util.List;
import Controls.BasicKeys;
import Controls.ShootInRange;
import ENEMY.EnemyShip;
import Player.BasicShip;
import Powerups.AddHunderedHealth;
import Powerups.HealthPowerup;
import Powerups.MissilePowerup;
import static game1.Constants.DELAY;


public class Game {
    public static int N_INITIAL_ASTEROIDS = 5;
    public static List<GameObject> asteroids;
    public static BasicShip ship;
    public static EnemyShip enemyship;
    public static BasicKeys ctrl;
    public static boolean endgame = false, pause = false,running = true, score_added = false;
    public static List<GameObject> alive;
    public static int lives = 3,level =1, score = 0 , changeinscore = 0, meteorites = 0,enemies = 0,missiles = 0;
    public static Wormhole wormhole;



    // setup game with new controller
    public  Game() {
        ctrl = new BasicKeys();
        setup();

    }


    public static void main(String[] args) throws Exception {
        Game game = new Game();
        BasicView view = new BasicView(game);
        new JEasyFrame(view).addKeyListener(ctrl);
        // run the game
        while (running) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    //return score
    public static int getScore(){return score;}

    //increase score
    public static void incScore(){
        score += 5;
        addlives();
    }

    //remove lives from player on death.
    public static void removeLives(){

        if(ship.dead && lives > 0) {
            ship = new BasicShip(ctrl);
            alive.add(ship);
            lives -= 1;
        }else if(lives <= 0 && ship.dead&&!score_added){
            SCORES_TO_FILE.added();
            score_added = true;
            endgame = true;
        }
    }


    public static void addlives(){

        if(score-changeinscore%100 == 0 && lives < 3){
            changeinscore = score;
            lives += 1;
        }
    }

    // advance to next level.
    public static void levelup(){

        if(meteorites == 0 && !ship.dead && enemies == 0 ){
            N_INITIAL_ASTEROIDS += 2;
            double powerup = Math.random()*100;
            double enemyspawnchance = Math.random()*100;
            for(int t = 0; t < N_INITIAL_ASTEROIDS;t++){
                asteroids.add(BasicAsteroid.makeRandomAsteroid());
            }

            asteroids.remove(wormhole);
            wormhole = new Wormhole();
            asteroids.add(wormhole);
            enemyship = new EnemyShip(new ShootInRange());

            //spawn powerups
            //+100 lives, chance = 2%
            if((int)powerup%50 == 0){
                asteroids.add(new AddHunderedHealth());
                System.out.println("100 Health");
            }
            //Missiles, chance = 33%
            else if((int)powerup%3 == 0){
                asteroids.add(new MissilePowerup());
                System.out.println("missile");
            }
            //+1 lives, chance = 25%
            else if((int)powerup%4 == 0){
                System.out.println("Health gain");
                asteroids.add(new HealthPowerup());
            }

            //spawn enemy ship, chance = 50%
            if ((int)enemyspawnchance%2 == 0){
            asteroids.add(enemyship);
            }
            level++;
        }
    }



    //reset game
    public static void setup(){
        lives = 3;
        level =1;
        score = 0;
        changeinscore = 0;
        N_INITIAL_ASTEROIDS = 15;
        wormhole = new Wormhole();
        ship = new BasicShip(ctrl);
        ship.dead = false;
        meteorites = 0;
        enemyship = new EnemyShip(new ShootInRange());
        asteroids = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            asteroids.add(BasicAsteroid.makeRandomAsteroid());
        }

        asteroids.add(wormhole);
        //asteroids.add(ship2);

        if((int)(Math.random()*100)%2 == 0){
        asteroids.add(enemyship);
        }

        score_added = false;
        asteroids.add(ship);
        endgame = false;
        pause = false;

    }


    //Game update.
    public void update() {

        if(!endgame && !pause){

        alive = new ArrayList<>();

        synchronized (Game.class){

        for (GameObject a : asteroids) {

            if(!a.dead){
                removeLives();
                alive.add(a);
            }
        }

            if(EnemyShip.Ebullet !=  null ){
                alive.add(EnemyShip.Ebullet);
                EnemyShip.Ebullet = null;
            }
        }


        if(BasicShip.bullet !=  null ){
            alive.add(BasicShip.bullet);
            BasicShip.bullet = null;
        }

            if(BasicShip.missile !=  null ){
                alive.add(BasicShip.missile);
                BasicShip.missile = null;
            }


        meteorites = 0;
        enemies = 0;
        for (GameObject a: alive){
            if(a.getClass() == BasicAsteroid.class)meteorites++;
            if(a.getClass() == EnemyShip.class)enemies++;

            a.update();
        }

        synchronized (Game.class){
            for(GameObject a : alive){

                for(GameObject b : alive){
                  a.collisionHandling(b);
                }
            }
        }

            synchronized (Game.class) {
                asteroids.clear();
                asteroids.addAll(alive);
                levelup();
            }
        }
    }
}
