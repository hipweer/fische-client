package window;

import frame.GameObject;
import frame.ObjectId;
import object.Player;

import java.awt.*;
import java.util.LinkedList;


public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();


    private GameObject tempObject;

    public void tick(){

        for (int i = 0; i <object.size() ; i++) {
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }
    public void render(Graphics g){
        for (int i = 0; i <object.size() ; i++) {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }
    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public void respawn(GameObject object){
        int tempSpieler = object.getSpieler();
        this.removeObject(object);
        addObject(new Player(200,200,this,ObjectId.Player, tempSpieler));
    }

    public GameObject getObject(ObjectId id){
        GameObject tempObject = null;

        for (int i = 0; i <object.size() ; i++) {
            tempObject = object.get(i);
            if(tempObject.getId()== id){
                return tempObject;
            }
        }

        return tempObject;
    }

    public int countBullets(int spieler){
        int bulletCount = 0;
        for(int i = 0; i < object.size();i++){
            if(object.get(i).getId() == ObjectId.Bullet && object.get(i).getSpieler()== spieler){
                bulletCount ++;
            }
        }
        return bulletCount;
    }

}
