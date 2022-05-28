package com.darkona.zoo.render.renderer.entity;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.render.renderer.Renderer;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class ChickenRenderer implements Renderer {

    private static final Image image = ImageUtils.loadImage("/animal/chicken.png");
    private final int u;
    private final int v;
    private final Size size;

    public ChickenRenderer(int u, int v, Size size){
        this.u = u;
        this.v = v;
        this.size = size;
    }
    @Override
    public void render(Graphics graphics, WorldThing thing) {
        ImageUtils.drawImage(image, thing, graphics, u, v, size);
    }

    @Override
    public Image getSprite(Graphics graphics) {
        return image;
    }
}
