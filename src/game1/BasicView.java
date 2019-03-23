package game1;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import static game1.Game.endgame;
import static game1.Game.pause;

public class BasicView extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;
    Image im = Constants.MILKYWAY1;


    private Game game;
    public static AffineTransform bgTransf;
    public BasicView(Game game) {
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }

    @Override
    public void paintComponent(Graphics g0) {


        try{
            Font f1 = Font.createFont(Font.TRUETYPE_FONT,new File("FONTS/CREBER.ttf"));

        if(!endgame && !pause){
            Graphics2D g = (Graphics2D) g0;
            g.drawImage(im, bgTransf,null);


            synchronized (Game.class){
                for (GameObject aster : Game.asteroids){
                    aster.draw(g);
                }
            }
            g.setColor(Color.green);
            g.setFont(f1.deriveFont(20f));
            g.drawString("SCORE:" + Game.score,570,20);
            g.drawString("LIVES:" + Game.lives,FRAME_WIDTH/2+90,20);
            g.drawString("LEVEL:" + Game.level,FRAME_WIDTH/2,20);
            g.drawString("MISSILES: "+ Game.missiles, 10,20);

        }else if(pause && !endgame){


        }else if(endgame && !pause){
            ArrayList<Integer> scores = SCORES_FROM_FILE.LISTOSCORE();
            Collections.sort(scores);
            int high = scores.get(scores.size() - 1);
            g0.setColor(Color.black);
            g0.clearRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
            g0.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
            g0.setColor(Color.green);
            g0.setFont(f1.deriveFont(40f));
            g0.drawString("SCORE:" + Game.score,570,(FRAME_HEIGHT/2) + 40);
            if(high == Game.score ){
                g0.drawString("NEW HIGHSCORE !!!",570,(FRAME_HEIGHT/2)-40);
            }else{
                g0.drawString("GAME OVER !!",570,(FRAME_HEIGHT/2)-40);
            }
            g0.drawString("LEVEL:" + Game.level,570,(FRAME_HEIGHT/2));
            g0.drawString("PRESS ESC TO QUIT OR ENTER TO RESTART", FRAME_WIDTH/2- 400,FRAME_HEIGHT-40);
            g0.setColor(Color.YELLOW);
            g0.drawString("HIGHSCORE:" + high,570,(FRAME_HEIGHT/2)+80);
        }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("SOMETHING WENT WRONG!!");
            System.exit(0);}
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
