package com.darkona.zoo.render.renderer;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class TerrainRenderer implements Renderer{

    private static final Image image = ImageUtils.loadImage("/terrain/ttt.png");

    public TerrainRenderer(){}

    @Override
    public void render(Graphics graphics, WorldThing thing, int u, int v, Size size) {
        ImageUtils.drawImage(image, thing, graphics, u, v, size);
    }

}
