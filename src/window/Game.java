package window;

import frame.KeyInput;
import frame.ObjectId;
import object.Block;
import object.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {


    public static int WIDTH, HEIGHT;

    private static Game gameInstance;

    ////Netzwerk
    public static String host;
    public static int portNumber;
    private static Connection connection;

    private boolean running = false;

    private BufferedImage level = null;
    Thread thread;
    //Object
    Handler handler;
    Camera cam;

    public static Game getGameInstance(){
        if (gameInstance == null){
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public synchronized void start(){
        if(running){
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void connect(){
        connection = Connection.connect();
    }


    private void init(){
        WIDTH = getWidth();
        HEIGHT = getHeight();

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/level.png");

        handler = new Handler();
        cam = new Camera(0,0);
        handler.addObject(new Player(345,234,handler,ObjectId.Player,2));
        LoadImageLevel(level);

        this.addKeyListener(new KeyInput(handler,this,1));

    }

    private void LoadImageLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < h ; xx++) {
            for (int yy = 0; yy < w; yy++) {
                int pixel = image.getRGB(xx,yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255 && green == 255 && blue == 255){
                    handler.addObject(new Block(xx*32,yy*32,ObjectId.Block));
                }
                if(red == 0 && green == 0 && blue == 255){
                    handler.addObject(new Player(xx*32,yy*32,handler,ObjectId.Player,1));
                }
            }
            
        }

    }


    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }



    private void tick(){
        handler.tick();
        cam.tick(handler.getObject(ObjectId.Player));


    }
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        /////////////////////////////////////////////
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());

        g2d.translate(cam.getX(),cam.getY()); //Begin of cam

        handler.render(g);

        g2d.translate(-cam.getX(),-cam.getY()); // End of cam
        ////////////////////////////////////////////
        g.dispose();
        bs.show();
    }

    //////////////////////////////////////////////////////

    public static void main(String args[]){
        prepareServerConnection(args);
        new StartScreen(800,600,"FischiFisch",getGameInstance());


    }


    static private void prepareServerConnection(String[] args) {
        boolean defaultSetup = args.length < 2;
        host = defaultSetup ? "192.168.178.63" : args[0];
        portNumber = defaultSetup ? 63400 : Integer.valueOf(args[1]).intValue();
        System.out.println("Connecting to host=" + host + ", portNumber=" + portNumber);
    }

}
