package window;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BufferedImageLoader {

    private BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (Exception e){ }
        return image;
    }
}
