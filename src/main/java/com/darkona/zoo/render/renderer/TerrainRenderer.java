package com.darkona.zoo.render.renderer;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class TerrainRenderer implements Renderer{

    private final int u;
    private final int v;
    private final Size size;

    private static final Image image = ImageUtils.loadImage("/terrain/ttt.png");

    public TerrainRenderer(int u, int v, Size size){

        this.u =u;
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