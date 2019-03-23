package game1;

import ResourceManagers.ImageManager;
import java.awt.*;
import java.io.IOException;

public class Constants {
    public static Dimension Screensize = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int FRAME_HEIGHT = (int)Screensize.getHeight();
    public static final int FRAME_WIDTH = (int)Screensize.getWidth();
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    public static final int DELAY = 40;  // in milliseconds
    public static final double DT = DELAY / 1000.0;  // in seconds

    public static Image ASTEROID1, MILKYWAY1,Missile,Wormhole;
    static {
        try {

            ASTEROID1 = ImageManager.loadImage("asteroid_tiny");
            MILKYWAY1 = ImageManager.loadImage("background (2)");
            Missile = ImageManager.loadImage("EnemyProjectile1").getScaledInstance(30,30,0);
            Wormhole = ImageManager.loadImage("warpgate_1").getScaledInstance(70,70,0);

        } catch (IOException e) { e.printStackTrace(); }
    }
}