package frame;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Adamskie on 30.10.2014.
 */
public abstract class GameObject {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float damage;
    protected boolean falling = true;
    protected boolean hit = false;
    protected boolean jumping = false;
    protected int live;
    protected boolean dead = false;

    protected ObjectId id;
    protected int facing;
    protected boolean connected;
    protected int spieler;
    protected LinkedList<GameObject> object;


    protected float velX = 0;
    protected float velY = 0;

    public GameObject(float x, float y,ObjectId id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> object);
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public float getX(){return x;}
    public float getY(){return y;}
    public void setX(float x){this.x = x;}
    public void setY(float y){this.y = y;}

    public float getVelX(){return velX;}
    public float getVelY(){return velY;}
    public void setVelX(float velX){this.velX = velX;}
    public void setVelY(float velY){this.velY = velY;}

    public void setLive(int live) {
        this.live += live;
        System.out.print(live);
    }
    public int getLive() {
        return live;
    }

    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getSpieler() {
        return spieler;
    }
    public void setSpieler(int spieler) {
        this.spieler = spieler;
    }

    public float getDamage() {
        return damage;
    }
    public void setDamage(float damage) {
        this.damage = damage;
    }

    public boolean hit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
        if(live < 0)setDead(true);
    }

    public int getFacing() {
        return facing;
    }
    public void setFacing(int facing) {
        this.facing = facing;
    }

    public boolean isJumping() {
        return jumping;
    }
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    public boolean isFalling() {
        return falling;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public ObjectId getId(){return id;}



}
