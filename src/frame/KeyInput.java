package frame;

import object.Bullet;
import window.Game;
import window.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter{
    Game game;
    Handler handler;
    int v1 = 0;
    int s1 = 0;
    int spieler;

    public KeyInput(Handler handler, Game game, int spieler){
        this.handler = handler;
        this.game = game;
        this.spieler = spieler;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        GameObject player = handler.getObject(ObjectId.Player);



        if(spieler == 1) {
            if (key == KeyEvent.VK_D) {
                player.setVelX(5);
                player.setFacing(1);
                v1 = 1;
            }
            if (key == KeyEvent.VK_A) {
                player.setVelX(-5);
                player.setFacing(-1);
                v1 = 2;
            }
            if (key == KeyEvent.VK_W && !player.isJumping()) {
                player.setJumping(true);
                player.setVelY(-10);

            }

            if (key == KeyEvent.VK_SPACE) {
                if(handler.countBullets(1)<5){
                if (player.getFacing() == 1) {

                        handler.addObject(new Bullet(player.getDamage(), player.getX() + player.getWidth() + 4,
                             player.getY() + player.getHeight() / 2, ObjectId.Bullet, 15, player.getSpieler()));

                    } else {
                        handler.addObject(new Bullet(player.getDamage(), player.getX(),
                                player.getY() + player.getHeight() / 2, ObjectId.Bullet, -15, player.getSpieler()));
                             }

                    }
            }
        }

        if(key == KeyEvent.VK_C ){
            try {
                player.setConnected(true);
                game.connect();
            }catch (Exception b){
                System.out.print(b);
            }
        }

        if(key == KeyEvent.VK_T ){
            player.setLive(-2);
        }

        if(key == KeyEvent.VK_ESCAPE){
            System.exit(1);
        }


    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        GameObject player = handler.getObject(ObjectId.Player);

        if(spieler == 1) {
            if (key == KeyEvent.VK_D && v1 == 1) player.setVelX(0);
            if (key == KeyEvent.VK_A && v1 == 2) player.setVelX(0);
        }

    }

}
