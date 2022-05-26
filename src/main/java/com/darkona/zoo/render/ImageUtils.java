package com.darkona.zoo.render;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.world.WorldThing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static com.darkona.zoo.simulation.Simulation.SCALE;

public class ImageUtils {
    public static Image loadImage(String filePath){
        try{
            URL url = ImageUtils.class.getResource(filePath);
            return ImageIO.read(Objects.requireNonNull(url));
        }catch (IOException e){
            System.out.println("Could not load image from path: " + filePath);
        }catch (NullPointerException f){
            System.out.println("Filepath points to null image.");
        }
        return null;
    }

    public static void drawImage(Image image, WorldThing thing, Graphics graphics, int u, int v, Size size){
        int aX = thing.getPosition().x * SCALE;
        int aY = thing.getPosition().y * SCALE;
        int bX = aX + thing.getSize().width * SCALE;
        int bY = aY + thing.getSize().height * SCALE;
        graphics.drawImage(image, aX, aY, bX, bY, u, v,  u + size.width, v + size.height,null);
    }
}
