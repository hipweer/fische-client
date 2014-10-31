package object;

import frame.GameObject;
import frame.ObjectId;
import window.Connection;
import window.Handler;

import java.awt.*;
import java.util.LinkedList;



public class Player extends GameObject {

    private float width = 32;
    private float height = 34;
    private final float MAX_SPEED = 14;

    private long hitTimer = 0;
    private long hitTimerDiff =0;
    private long hitLength = 1000;


    private int spieler;
    private int live = 100;
    private boolean dead = false;
    private float damage = 5;
    private int facing = 1;
    private float gravity = 0.3f;
    private Handler handler;

    public Player(float x, float y,Handler handler, ObjectId id,int spieler) {
        super(x, y, id);
        this.handler = handler;
        this.spieler = spieler;
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

       if(connected)Connection.getInstance().sendLocation(x,y);

       hitTimer(hit); //Ob getroffen

        if(falling || jumping){
            velY += gravity;
            if(velY > MAX_SPEED)
                velY = MAX_SPEED;
        }
        Collision(object);

    }



    public void render(Graphics g) {
        if(hitTimer != 0){
            g.setColor(Color.white);
            int qua1 = (int)((hitTimerDiff*hitTimerDiff)/1500);
            if(qua1 < 165)
            g.drawRect((int)(x+width/2-qua1),(int)(y+height/2 -qua1),2*qua1,2*qua1);
            int j = (int)((hitTimerDiff*hitTimerDiff)/1900);
            if(j < 165)
                g.drawRect((int)(x+width/2-j),(int)(y+height/2 -j),2*j,2*j);
        }

        g.setColor(new Color(234, 171, 168));
        g.drawRect((int)x,(int)y,(int)width, (int)height);
        g.fillRect((int)x,(int)y,(int)width, (int)height);
        //Lebensanzeige
        float herzX = x,herzY = y;
        for (int i = 0; i < live; i++) {
            g.setColor(new Color(40, 234, 0));
            g.drawRect((int)herzX,(int)herzY,(int)width/100, (int)5);
            g.fillRect((int)herzX,(int)herzY,(int)width/100, (int)5);
            herzX += width/100;
        }


    }
    ////////////////////////////////////////////////////////////////////////
    //Status werte
    public void setLive(int live) {
        this.live += live;
        System.out.print(getLive());
    }
    public int getLive() {
        return live;
    }
    public float getDamage() {

        return damage;
    }
    public void setDamage(float damage) {
        this.damage = damage;
    }

    //Schaden bekommen
    public boolean hit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
        if(live < 0)setDead(true);
    }
    private void hitTimer(boolean hit){
        if(hit && hitTimer == 0)hitTimer = System.nanoTime();

        if(hitTimer != 0){
            hitTimerDiff = (System.nanoTime() - hitTimer) / 1000000;
            if(hitTimerDiff > hitLength){
                hitTimer = 0;
                this.hit = false;
            }
        }
    }
    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
        if(dead)handler.respawn(this);
    }

    ////////////Collison////////////////////////////////////////////////////////

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y, (int)width,(int)height);
    }
    public Rectangle getBoundsBottom() {
        return new Rectangle(((int)x+((int)(width/2)-((int)width/2)/2)),((int)y+((int)height/2)),(int)width/2,(int)height/2);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int) ((int)x+((width/2)-((width/2)/2))),(int)y,(int)width/2,(int)height/2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int)x+width-5),(int)y,5,(int)height-10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x,(int)y+5,5,(int)height-10);
    }

    private void Collision(LinkedList<GameObject> object){
        for (int i = 0; i < object.size() ; i++) {
            GameObject tempObject = object.get(i);

            if(tempObject.getId() == ObjectId.Block){
                //Top
                if(getBoundsTop().intersects(tempObject.getBounds())){
                    y = tempObject.getY() + this.height;
                    velY = 0;
                }

                //Bottom
                if(getBoundsBottom().intersects(tempObject.getBounds())){
                    y = tempObject.getY()- height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                }else falling = true;

                //Right
                if(getBoundsRight().intersects(tempObject.getBounds())){
                    x = tempObject.getX() - this.width;
                    velX = 0;
                }

                //Left
                if(getBoundsLeft().intersects(tempObject.getBounds())){
                    x = tempObject.getX() + this.width+4;
                    velX = 0;
                }
            }

        }
    }

    //Spieler Informationen
    public int getFacing() {
        return facing;
    }
    public void setFacing(int facing) {
        this.facing = facing;
    }
    public int getSpieler() {
        return spieler;
    }

    //Internetlogik
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}

 /*  if(slowDownTimer != 0){
        g.setColor(Color.white);
        g.drawRect(20,60,100,8);
        g.fillRect(20,60,
                (int)(100-100.0*slowDownTimerDiff/slowDownLength),8);
    }

    if(slowDownTimer != 0){
        slowDownTimerDiff = (System.nanoTime() - slowDownTimer) / 1000000;
        if(slowDownTimerDiff > slowDownLength){
            for(int j = 0; j<enemies.size();j++){
                enemies.get(j).setSlow(false);
            }
            slowDownTimer = 0;
        }
    }

    if(waveStartTimer!=0){
        g.setFont(new Font("Verdana", Font.PLAIN,18));
        String s = "-W A V E " + waveNumber + " -";
        int length = (int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        int alpha = (int)(255* Math.sin(3.14*waveStartTimerDiff / waveDelay));
        if(alpha>255)alpha=255;
        g.setColor(new Color(255,255,255,alpha));
        g.drawString(s, WIDTH/2 - length/2,HEIGHT/2);
    }*/
