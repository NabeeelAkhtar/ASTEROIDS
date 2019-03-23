package Controls;


import Player.BasicShip;
import Utils.Vector2D;
import game1.Game;

//controller for EnemyShip, shoots when player is in range.
public class ShootInRange implements BasicController {
    public static double ShootingDistance = 200;
    private BasicShip ship = Game.ship;

    public Action action = new Action();
    @Override
    public Action action() {

        if(ship.dead){selectTarget(Game.ship);}
            action.turn = 0;
        if(TargetIsInRange()){
            Vector2D vec = new Vector2D(Game.enemyship.direction.subtract(ship.direction));
            Game.enemyship.direction = new Vector2D(vec);
        }
            action.shoot = TargetIsInRange();
            return action;
    }

    private boolean TargetIsInRange(){
        return Game.enemyship.position.dist(ship.position) < ShootingDistance + ship.radius;
    }

    private void selectTarget(BasicShip ship1){
        ship = ship1;
    }
}
