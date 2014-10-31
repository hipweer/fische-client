package object;

import frame.GameObject;
import frame.ObjectId;

import java.awt.*;
import java.util.LinkedList;


public class Bullet extends GameObject {

    private float velX;
    private float damage;
    private int spieler;


    public Bullet(float damage, float x, float y, ObjectId id, float velX, int spieler) {
        super(x, y, id);
        this.damage = damage;
        this.velX = velX;
        this.spieler = spieler;
    }

    private void collision(LinkedList<GameObject> object){

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            if(tempObject.getId() == ObjectId.Block){
                if(tempObject.getBounds().intersects(getBounds())) {

                    object.remove(this);}
            }
            if(tempObject.getId() == ObjectId.Player){
                if(spieler != tempObject.getSpieler() && tempObject.getBounds().intersects(getBounds())) {
                    tempObject.setLive((int) -damage);
                    tempObject.setHit(true);
                    object.remove(this);
                }
            }
            
        }
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
        collision(object);
    }



    public void render(Graphics g) {
        g.setColor(new Color(98, 187, 252));
        g.fillRect((int) x, (int) y, 10, 10);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 10,10);
    }

}
