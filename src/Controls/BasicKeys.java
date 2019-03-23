package Controls;
import java.awt.event.*;

import ResourceManagers.SoundManager;
import game1.*;

import static game1.Game.endgame;
import static game1.Game.pause;
public class BasicKeys extends KeyAdapter implements BasicController  {
    Action action;
    public BasicKeys() {
        action = new Action();
    }

    public Action action() {
        // this is defined to comply with the standard interface
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 1;
                SoundManager.startThrust();
                action.shoot = false;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                action.shoot = false;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = +1;
                action.shoot =false;
                break;
            case KeyEvent.VK_SPACE:
                // this is done so that the user cannot take out all the asteroids
                // and enemies by going round and shooting at the same time.
                if (action.turn == 0){
                action.shoot = true;}else action.shoot = false;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case  KeyEvent.VK_TAB:
                if(!endgame && !pause) pause = true;
                //if(!endgame && pause ) pause = false;
                break;
            case KeyEvent.VK_ENTER:
                if(endgame) {
                    Game.setup();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        // please add appropriate event handling code
        SoundManager.stopThrust();
        action.shoot = false;
        action.turn = 0;
        action.thrust = 0;
        Game.ship.velocity.set(0,0);
    }
}
