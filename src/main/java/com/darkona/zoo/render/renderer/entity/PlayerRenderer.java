package com.darkona.zoo.render.renderer.entity;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.render.renderer.Renderer;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class PlayerRenderer implements Renderer {

    private final Image image;
    public PlayerRenderer(){
        this.image = ImageUtils.loadImage("/animal/pc.png");
    }


    @Override
    public void render(Graphics graphics, WorldThing thing) {
        ImageUtils.drawImage(image, thing, graphics, 5,5, new Size(15,15));
    }

    @Override
    public Image getSprite(Graphics graphics) {
        return this.image;
    }
}
