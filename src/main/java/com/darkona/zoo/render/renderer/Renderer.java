package com.darkona.zoo.render.renderer;

import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public interface Renderer {

    void render(Graphics graphics, WorldThing thing);
    Image getSprite(Graphics graphics);
}
